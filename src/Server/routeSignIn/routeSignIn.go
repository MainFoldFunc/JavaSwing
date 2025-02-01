package routeSignIn

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"time"

	"golang.org/x/crypto/bcrypt"
	_ "github.com/go-sql-driver/mysql"
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
	// Replace with your MySQL credentials
	db, err = sql.Open("mysql", "go_user:strong_password@tcp(localhost:3306)/my_go_app")
	if err != nil {
		log.Fatal("Database connection failed:", err)
	}

	// Configure connection pool settings if needed
	db.SetMaxOpenConns(25)
	db.SetMaxIdleConns(25)
	db.SetConnMaxLifetime(5 * time.Minute)

	// Verify connection
	err = db.Ping()
	if err != nil {
		log.Fatal("Database ping failed:", err)
	}

	fmt.Println("Connected to MySQL database")

	// Create the 'users' table if it does not exist
	createTableQuery := `
	CREATE TABLE IF NOT EXISTS users (
		id INT AUTO_INCREMENT PRIMARY KEY,
		login VARCHAR(255) NOT NULL UNIQUE,
		password VARCHAR(255) NOT NULL,
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

	// Basic input validation
	if creds.Login == "" || creds.Password == "" {
		http.Error(w, "Login and password cannot be empty", http.StatusBadRequest)
		return
	}

	// Hash the password before storing
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(creds.Password), bcrypt.DefaultCost)
	if err != nil {
		http.Error(w, "Failed to process password", http.StatusInternalServerError)
		return
	}

	// Insert into database (using hashed password)
	_, err = db.Exec("INSERT INTO users (login, password) VALUES (?, ?)",
		creds.Login,
		string(hashedPassword),
	)
	if err != nil {
		http.Error(w, "Failed to save credentials", http.StatusInternalServerError)
		log.Printf("Database insert error: %v", err)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(Response{Message: fmt.Sprintf("User %s registered successfully!", creds.Login)})
}

