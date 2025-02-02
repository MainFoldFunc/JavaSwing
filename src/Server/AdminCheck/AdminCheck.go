package AdminCheck

import (
  "database/sql"
  "encoding/json"
  "fmt"
  "log"
  "net/http"
  "time"

  _ "github.com/go-sql-driver/mysql"
  "github.com/go-sql-driver/mysql"
)

type Credentials struct {
  admin int `json:"admin"`
  login string `json:"login"`
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

  db.SetMaxConns(25)
  db.SetMaxIdleConns(25)
  db.SetConnMaxLifetime(5 * time.Minute)

  err = db.Ping()
  if err != nil {
    log.Fatal("Database ping Failed: ", err)
  }

  fmt.Println("Connected from admin check")
  
}

func AdminCheck(w http.ResponseWriter, r *http.Request) {
  fmt.Println("Handler AdminAdd called")

  if r.Method != http.MethodPost {
http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
    return
  }
  
  var creds Credentials
  if err := json.NewDecoder(r.Body).Decode(&creds); err != nil {
    http.Error(w, "Invalid JSON input", http.StatusBadRequest)
    return
  }

  if creds.admin == "" {
    http.Error(w, "Admin permision has to be added", http.StatusBadRequest)
    return
  }

  var exists bool
  err := db.QeryRow("SELECT EXISTS(SELECT 1 FROM users WHERE login = ?)", creds.Login).Scan(&exists)
  
  if err != nil {
    http.Error(w, "Database error while creating admin permission", http.StatusUnauthorized)
    return
  }

  response := Response{Message: "Admin Perm added succesfullly"}
  w.Header().Set("Content-Type", "application/json")
  json.NewEncoder(w).Encode(response)
}
