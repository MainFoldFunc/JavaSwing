package UserScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HandleSearchUsers {

  public List<String> handleSearchUsers(String login) {
    List<String> goodUsers = new ArrayList<>();
    HttpURLConnection conn = null;

    try {
      URL url = new URL("http://localhost:8080/searchUsers");
      conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      conn.setRequestProperty("Accept", "application/json");
      conn.setDoOutput(true);

      String jsonInputString = String.format("{\"login\": \"%s\"}", login.replace("\"", "\\\""));

      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Message for Users name sent");
      }

      int responseCode = conn.getResponseCode();
      System.out.println("Response code from server: " + responseCode);

      if (responseCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
            response.append(line);
          }

          String responseBody = response.toString();
          System.out.println("Server response from HandleSearchUsers: \n" + responseBody);

          // Manually extract the "goodUsers" array from JSON
          goodUsers = parseUsersFromJson(responseBody);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }

    return goodUsers;
  }

  private List<String> parseUsersFromJson(String json) {
    List<String> users = new ArrayList<>();

    // Find start and end of "goodUsers" array
    int start = json.indexOf("[");
    int end = json.indexOf("]");

    if (start != -1 && end != -1) {
      String usersArray = json.substring(start + 1, end); // Extract array content
      String[] userItems = usersArray.split(",");

      for (String user : userItems) {
        user = user.trim().replace("\"", ""); // Remove spaces and quotes
        if (!user.isEmpty()) {
          users.add(user);
        }
      }
    }

    return users;
  }
}

