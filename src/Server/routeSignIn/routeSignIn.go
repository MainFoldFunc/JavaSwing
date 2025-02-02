package routeSignIn

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"time"

	_ "github.com/go-sql-driver/mysql"
	"github.com/go-sql-driver/mysql" // Import the MySQL driver properly
)

type Credentials struct {
	Login    string `json:"login"`
	Password string `json:"password"`
	Admin    int `json:"admin"`
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

	fmt.Println("Connected to MySQL database")

	createTableQuery := `
	CREATE TABLE IF NOT EXISTS users (
		id INT AUTO_INCREMENT PRIMARY KEY,
		login VARCHAR(255) NOT NULL UNIQUE,
		password VARCHAR(255) NOT NULL,
		admin VARCHAR(255) NOT NULL,
		created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
	);
	`
	_, err = db.Exec(createTableQuery)
	if err != nil {
		log.Fatal("Failed to create table:", err)
	}
	fmt.Println("Ensured table 'users' exists")
}

func SignInHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var creds Credentials
	if err := json.NewDecoder(r.Body).Decode(&creds); err != nil {
		http.Error(w, "Invalid request format", http.StatusBadRequest)
		return
	}

	if creds.Login == "" || creds.Password == "" {
		http.Error(w, "Login and password cannot be empty", http.StatusBadRequest)
		return
	}

	// Insert user credentials into database (no hashing)
	_, err := db.Exec("INSERT INTO users (login, password, admin) VALUES (?, ?, ?)", creds.Login, creds.Password, creds.Admin)
	if err != nil {
		// Check if the error is a duplicate entry (MySQL error code 1062)
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
	json.NewEncoder(w).Encode(Response{Message: fmt.Sprintf("User %s registered successfully!", creds.Login)})
}

