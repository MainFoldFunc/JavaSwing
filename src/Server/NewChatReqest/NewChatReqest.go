package NewChatReqest

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
  UserS  string `json:"userS"`
  UserR  string `json:"userR"`
  Accept bool   `json:"accept"`
}

type Response struct {
  Message string `json:"message"`
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
    log.Fatal("Database connection failed: ", err)
  }

  fmt.Println("Connected to MySQL database")

  createTableQuery := `
  CREATE TABLE IF NOT EXISTS chatRequests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userS VARCHAR(255) NOT NULL,
    userR VARCHAR(255) NOT NULL,
    accepted BOOLEAN NOT NULL
  );
  `
  _, err = db.Exec(createTableQuery)
  if err != nil {
    log.Fatal("Failed to create a table: ", err)
  } else {
    fmt.Println("Ensured that the table exists")
  }
}

func NewChatReqest(w http.ResponseWriter, r *http.Request) {
  if r.Method != http.MethodPost {
    http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
    return
  }

  var creds Credentials
  if err := json.NewDecoder(r.Body).Decode(&creds); err != nil {
    http.Error(w, "Invalid request format", http.StatusBadRequest)
    return
  }

  if creds.UserS == "" || creds.UserR == "" {
    http.Error(w, "Credentials can't be empty", http.StatusBadRequest)
    return
  }

  _, err := db.Exec("INSERT INTO chatRequests (userS, userR, accepted) VALUES (?, ?, ?)", creds.UserS, creds.UserR, creds.Accept)
  if err != nil {
    if mysqlErr, ok := err.(*mysql.MySQLError); ok && mysqlErr.Number == 1062 {
      http.Error(w, "User already exists", http.StatusConflict)
    } else {
      http.Error(w, "Failed to save credentials", http.StatusInternalServerError)
    }
    log.Printf("Database insert error: %v", err)
    return
  }

  w.Header().Set("Content-Type", "application/json")
  w.WriteHeader(http.StatusCreated)
  json.NewEncoder(w).Encode(Response{Message: fmt.Sprintf("Chat request from user: %s registered successfully", creds.UserS)})
}

