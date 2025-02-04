package searchUsers

import (
  "database/sql"
  "encoding/json"
  "fmt"
  "log"
  "net/http"
  "time"

  _ "github.com/go-sql-driver/mysql"
)

type Credentials struct {
  Login string `json:"login"`
}

type Response struct {
  GoodUsers []string `json:"goodUsers"`
}

var db *sql.DB

func init() {
  var err error
  db, err = sql.Open("mysql", "go_user:strong_password@tcp(localhost:3306)/my_go_app")
  if err != nil {
    log.Fatal("Database connection failed: ", err)
  }

  db.SetMaxOpenConns(25)
  db.SetMaxIdleConns(25)
  db.SetConnMaxLifetime(5 * time.Minute)

  err = db.Ping()
  if err != nil {
    log.Fatal("Database ping failed")
  }

  fmt.Println("Connected to MySQL Database")
}

func SearchUsers(w http.ResponseWriter, r *http.Request) {
  fmt.Println("Handler searchUsers called")

  if r.Method != http.MethodPost {
    http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
    return
  }

  var creds Credentials
  if err := json.NewDecoder(r.Body).Decode(&creds); err != nil {
    http.Error(w, "Invalid request payload", http.StatusBadRequest)
    return
  }

  if creds.Login == "" {
    http.Error(w, "Login can't be empty", http.StatusBadRequest)
    return
  }

  // Query the database for usernames that contain the login string
  query := "SELECT login FROM users WHERE login LIKE ?"
  rows, err := db.Query(query, "%"+creds.Login+"%")
  if err != nil {
    http.Error(w, "Database query error", http.StatusInternalServerError)
    return
  }
  defer rows.Close()

  var goodUsers []string
  for rows.Next() {
    var username string
    if err := rows.Scan(&username); err != nil {
      log.Println("Error scanning row:", err)
      continue
    }
    goodUsers = append(goodUsers, username)
  }

  // Send the response as JSON
  response := Response{GoodUsers: goodUsers}
  w.Header().Set("Content-Type", "application/json")
  json.NewEncoder(w).Encode(response)
}

