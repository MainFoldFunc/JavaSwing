package routeSignIn

import (
  "fmt"
  "net/http"
  "log"
  "database/sql"
  "encoding/json"
  _ "github.com/go-sql-driver/mysql"
)

type Credentials struct {
  Login string `json:"login"`
  Password string `json:"password"`
}

var db *sql.DB

func init() {
	var err error
	// Replace with your MySQL credentials
	db, err = sql.Open("mysql", "go_user:strong_password@tcp(localhost:3306)/my_go_app")
	if err != nil {
		log.Fatal("Database connection failed:", err)
	}
	
	// Verify connection
	err = db.Ping()
	if err != nil {
		log.Fatal("Database ping failed:", err)
	}
	
	fmt.Println("Connected to MySQL database")
}

func SignInHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	// Parse JSON from request body
	var creds Credentials
	err := json.NewDecoder(r.Body).Decode(&creds)
	if err != nil {
		http.Error(w, "Invalid request format", http.StatusBadRequest)
		return
	}

	// Insert into database
	_, err = db.Exec("INSERT INTO users (login, password) VALUES (?, ?)", 
		creds.Login, 
		creds.Password,
	)
	if err != nil {
		http.Error(w, "Failed to save credentials", http.StatusInternalServerError)
		log.Printf("Database insert error: %v", err)
		return
	}

	w.WriteHeader(http.StatusCreated)
	fmt.Fprintf(w, "User %s registered successfully!", creds.Login)
}
