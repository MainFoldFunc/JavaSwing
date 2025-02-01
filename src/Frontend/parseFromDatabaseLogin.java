import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class parseFromDatabaseLogin {

  public boolean loginCheck(String login, String password) {
    HttpURLConnection connection = null;

    try {
      URL url = new URL("http://localhost:8080/loginCheck");  // Adjust endpoint if needed
      connection = (HttpURLConnection) url.openConnection();

      // Set up request properties
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoOutput(true);

      // Create JSON payload
      String jsonInputString = String.format(
        "{\"login\": \"%s\", \"password\": \"%s\"}",
        login.replace("\"", "\\\""),
        password.replace("\"", "\\\"")
      );

      // Send request body
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Message send");
      }

      // Read response
      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);

      if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 OK means user exists
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
            response.append(line);
          }
          String responseBody = response.toString();
          System.out.println("Server Response: " + responseBody);

          // Assuming the server responds with a JSON like: {"status": "success"}
          return responseBody.contains("\"status\":\"success\"");
        }
      } else {
        System.err.println("Login failed: Invalid credentials or user not found.");
        return false;
      }

    } catch (Exception e) {
      System.err.println("Request failed: " + e.getMessage());
      e.printStackTrace();
      return false;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}

