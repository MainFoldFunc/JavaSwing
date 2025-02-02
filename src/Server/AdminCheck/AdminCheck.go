package AdminCheck

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
  Admin int    `json:"admin"`  // Changed to int since it's passed as a number in JSON
  Login string `json:"login"`  // Fixed variable name to match JSON structure
}

type Response struct {
  Message string `json:"message"`
}

var db *sql.DB

func init() {
  var err error
  db, err = sql.Open("mysql", "go_user:strong_password@tcp(localhost:3306)/my_go_app")
  if err != nil {
    log.Fatal("Database connection error: ", err)
  }

  db.SetMaxOpenConns(25)
  db.SetMaxIdleConns(25)
  db.SetConnMaxLifetime(5 * time.Minute)

  err = db.Ping()
  if err != nil {
    log.Fatal("Database ping failed: ", err)
  }

  fmt.Println("Connected to the database successfully")
}

func AdminCheck(w http.ResponseWriter, r *http.Request) {
  fmt.Println("Handler AdminCheck called")

  if r.Method != http.MethodPost {
    http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
    return
  }

  var creds Credentials
  if err := json.NewDecoder(r.Body).Decode(&creds); err != nil {
    http.Error(w, "Invalid JSON input", http.StatusBadRequest)
    return
  }

  if creds.Admin == 0 {  // Checking if admin is 0, which may represent invalid data
    http.Error(w, "Admin permission has to be added", http.StatusBadRequest)
    return
  }

  var exists bool
  err := db.QueryRow("SELECT EXISTS(SELECT 1 FROM users WHERE login = ?)", creds.Login).Scan(&exists)
  
  if err != nil {
    http.Error(w, "Database error while checking user", http.StatusInternalServerError)
    return
  }

  if !exists {
    http.Error(w, "User does not exist", http.StatusNotFound)
    return
  }

  response := Response{Message: "Admin permission added successfully"}
  w.Header().Set("Content-Type", "application/json")
  json.NewEncoder(w).Encode(response)
}

