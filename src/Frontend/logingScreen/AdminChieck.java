import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AdminChieck {

  // The method to check if login is valid for an admin
  public boolean loginCheck(String login, String password) {
    HttpURLConnection connection = null;

    try {
      // Set up the URL for the API endpoint
      URL url = new URL("http://localhost:8080/AdminCheck");
      connection = (HttpURLConnection) url.openConnection();

      // Set connection properties for POST request with JSON payload
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoOutput(true);

      // Create the JSON payload manually
      String jsonInputString = String.format(
        "{\"admin\": 1, \"login\": \"%s\", \"password\": \"%s\"}",
        login.replace("\"", "\\\""),
        password.replace("\"", "\\\"")
      );

      // Send the request body
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Admin request sent");
      }

      // Read the server response
      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);

      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Read the response content
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
            response.append(line);
          }

          // Parse the response manually (looking for the "admin" field)
          String responseBody = response.toString();
          System.out.println("Server Response: " + responseBody);

          // Check for "admin" key in the response (either 1 or 0)
          if (responseBody.contains("\"admin\":1")) {
            return true;  // Admin is 1
          } else if (responseBody.contains("\"admin\":0")) {
            return false;  // Admin is 0
          } else {
            System.out.println("Tha fuck admin is not?");
          }
        }
      } else {
        System.err.println("Request failed with response code: " + responseCode);
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
    return false;
  }
}

