package routeSignIn

import (
  "fmt"
  "net/http"
)

func SigninHandler(w http.ResponseWriter, r *http.Request) {
  fmt.Fprintf(w, "Singin Logic will be here")
}
