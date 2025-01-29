package main

import (
  "fmt"
  "net/http"
  "server/routeSignIn"
)

func main() {
  
  http.HandleFunc("/signin", routeSignIn.SignInHandler)

  err := http.ListenAndServe(":8080", nil)
  if err != nil {
    fmt.Println("Error while creating a server \n", err)
  }
  fmt.Println("Starting a server at port 8080")
}

