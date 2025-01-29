package main

import (
  "fmt"
  "net/http"
)

func main() {
  PORT := ":8080"

  err := http.ListenAndServe(PORT, nil)

  if err != nil {
    fmt.Println("Error craeting a Server: /n", err)
  }
}
