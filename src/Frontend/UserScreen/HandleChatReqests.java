package UserScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HandleChatReqests {
  public List<String> handleSearchUsers(String login) {
    List<String> goodReqests = new ArrayList<>();
    HttpURLConnection conn = null;

    try {
      URL url = new URL("http://localhost:8080/searchReqests");
      conn = (HttpURLConnection) url.OpenConnection();

      conn.setReqestMethod("POST");
      conn.setReqestProperty("Content-Type", "application/json; charset-utf-8");
      conn.setReqestProperty("Accept", "application/json");
      conn.setDoOutput(true);

      String jsonInputString = String.format(
        "{\"login\": \"%s\"}",
        login.replace("\"", "\\\"")
      );

      try(OutputStream os = conn.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Message for chat reqest sent");
      }

      int responseCode = conn.getResponseCode();
      System.out.println("Response code from the website is: " + responseCode);

      if (responseCode = HttpURLConnection.HTTP_OK) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while((line = br.readLine()) !+ null) {
            resposne.append(line);
          }

          String responseBody = response.toString();
          System.out.println("Server response from HandleChatReqests: \n" + responseBody);

          goodReqests = parseUsersFromJson(responseBody);
          
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return goodReqests;
  }

  private List<String> parseUsersFromJson(String json) {
    List<String> users = new ArrayList<>();

    int start = json.indexOf("[");
    int end = json.indexOf("]");

    if (start != -1 && end != -1) {
      String userArray = json.substring(start + 1, end);
      String[] userItems = userArray.split(",");

      for(String user : userItems) {
        user = user.trim().replace("\"", "");
        if (!user.isEmpty()) {

          users.add(user);
        }
      }
    }
    return users;
  }
}
