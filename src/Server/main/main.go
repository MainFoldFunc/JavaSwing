package main

import (
  "fmt"
  "net/http"
  "server/routeSignIn"
  "server/LoginCheck"
  "server/AdminCheck"
  "server/searchUsers"
  "server/NewChatReqest" // Keep the original package name
)

func main() {
  fmt.Println("Starting a server at port 8080")

  http.HandleFunc("/signin", routeSignIn.SignInHandler)
  http.HandleFunc("/loginCheck", LoginCheck.LoginCheckHandler)
  http.HandleFunc("/AdminCheck", AdminCheck.AdminCheck)
  http.HandleFunc("/searchUsers", searchUsers.SearchUsers)
  http.HandleFunc("/newChatReqest", NewChatReqest.NewChatReqest)

  err := http.ListenAndServe(":8080", nil)
  if err != nil {
    fmt.Printf("Error while starting the server: %v\n", err)
  }
}

