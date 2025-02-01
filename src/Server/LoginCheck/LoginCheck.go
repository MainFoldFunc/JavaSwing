package LoginCheck

import (
  "database/sql"
  "encoding/json"
  "fmt"
  "log"
  "net/http"
  "time"

  _ "github.com/go-sql-driver/mysql"
  "golang.org/x/crypto/bcrypt"
)

type Credentials struct {
  Login    string `json:"login"`
  Password string `json:"password"`
}

type Response struct {
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

  // Securely query for the stored password hash
  var storedHash string
  err := db.QueryRow("SELECT password_hash FROM users WHERE login = ?", creds.Login).Scan(&storedHash)
  if err != nil {
    if err == sql.ErrNoRows {
      http.Error(w, "Invalid login credentials", http.StatusUnauthorized)
    } else {
      http.Error(w, "Database error", http.StatusInternalServerError)
    }
    return
  }

  // Compare the provided password with the stored hash
  if bcrypt.CompareHashAndPassword([]byte(storedHash), []byte(creds.Password)) != nil {
    http.Error(w, "Invalid login credentials", http.StatusUnauthorized)
    return
  }

  response := Response{Message: "Login successful"}
  w.Header().Set("Content-Type", "application/json")
  json.NewEncoder(w).Encode(response)
}

