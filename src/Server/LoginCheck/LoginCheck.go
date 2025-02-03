package LoginCheck

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
  Login    string `json:"login"`
  Password string `json:"password"`
}

type Response struct {
  Status  bool   `json:"status"`
  Message string `json:"message"`
}

var db *sql.DB

func init() {
  var err error
  db, err = sql.Open("mysql", "go_user:strong_password@tcp(localhost:3306)/my_go_app")
  if err != nil {
    log.Fatal("Database connection failed:", err)
  }

  db.SetMaxOpenConns(25)
  db.SetMaxIdleConns(25)
  db.SetConnMaxLifetime(5 * time.Minute)

  err = db.Ping()
  if err != nil {
    log.Fatal("Database ping failed:", err)
  }

  fmt.Println("Connected to MySQL Database")
}

func LoginCheckHandler(w http.ResponseWriter, r *http.Request) {
  fmt.Println("Handler Login check called")
  if r.Method != http.MethodPost {
    http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
    return
  }

  var creds Credentials
  if err := json.NewDecoder(r.Body).Decode(&creds); err != nil {
    http.Error(w, "Invalid JSON input", http.StatusBadRequest)
    return
  }

  if creds.Login == "" || creds.Password == "" {
    http.Error(w, "Login and password cannot be empty", http.StatusBadRequest)
    return
  }

  var exists bool
  err := db.QueryRow("SELECT EXISTS(SELECT 1 FROM users WHERE login = ? AND password = ?)", creds.Login, creds.Password).Scan(&exists)

  if err != nil {
    http.Error(w, "Database error", http.StatusInternalServerError)
    return
  }

  var response Response
  if exists {
    response = Response{Status: true, Message: "success"}
  } else {
    response = Response{Status: false, Message: "failure"}
  }

  w.Header().Set("Content-Type", "application/json")
  json.NewEncoder(w).Encode(response)
  fmt.Println("Response sent: ", response)
}

