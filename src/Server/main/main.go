package main

import (
  "fmt"
  "net/http"
  "server/routeSignIn"
  "server/LoginCheck"
)

func main() {
  
  http.HandleFunc("/signin", routeSignIn.SignInHandler)
  http.HandleFunc("/loginCheck", LoginCheck.LoginCheckHandler)

  err := http.ListenAndServe(":8080", nil)
  if err != nil {
    fmt.Println("Error while creating a server \n", err)
  }
  fmt.Println("Starting a server at port 8080")
}

