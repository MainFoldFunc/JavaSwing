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
  Admin    int    `json:"admin"`    // admin field (to set admin status)
  Login    string `json:"login"`    // Login field
  Password string `json:"password"` // Password field
}

type Response struct {
  Success bool   `json:"success"`
  Message string `json:"message"`
  Admin   int    `json:"admin"`
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

  // Check if credentials match the database and if password is correct
  var dbPassword string
  var adminStatus int
  err := db.QueryRow("SELECT password, admin FROM users WHERE login = ?", creds.Login).Scan(&dbPassword, &adminStatus)

  if err != nil {
    if err == sql.ErrNoRows {
      http.Error(w, "User does not exist", http.StatusNotFound)
    } else {
      http.Error(w, "Database error while checking user", http.StatusInternalServerError)
    }
    return
  }

  if dbPassword != creds.Password {
    http.Error(w, "Incorrect password", http.StatusUnauthorized)
    return
  }

  fmt.Println("Tha hell is admin: %d", adminStatus)
  // Send back the admin status (this should be the same value from the database)
  response := Response{
    Success: true,
    Message: "Login successful",
    Admin:   adminStatus,  // Admin status from database
  }

  w.Header().Set("Content-Type", "application/json")
  json.NewEncoder(w).Encode(response)
}

