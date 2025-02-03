import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class parseToDatabaseFirsstSignin {  // Fixed class name (PascalCase)

  // Renamed method to avoid conflict with class name
  public void sendDataToDatabase(String login, String password, int admin) {  
    try {
      URL url = new URL("http://localhost:8080/signin");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();

      // Request setup
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      con.setRequestProperty("Accept", "application/json");
      con.setDoOutput(true);

      // Proper JSON formatting without trailing commas
      String jsonInputString = String.format(
        "{\"login\": \"%s\", \"password\": \"%s\", \"admin\": %d}",  // Removed the quotes around admin
        login, 
        password,
        admin
      );

      // Send the request data
      try (OutputStream os = con.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      int status = con.getResponseCode();
      System.out.println("Response Code: " + status);

      // Read response
      try (BufferedReader br = new BufferedReader(
        new InputStreamReader(
          con.getInputStream(),  // Only read success stream here
          StandardCharsets.UTF_8
        )
      )) {
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
          response.append(line);
        }
        System.out.println("Response: " + response);
      }

      con.disconnect();

    } catch (Exception e) {
      System.err.println("Request failed: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

