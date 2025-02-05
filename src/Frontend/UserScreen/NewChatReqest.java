package UserScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NewChatReqest {
  public boolean newChatRequest(String userS, String userR) {
    HttpURLConnection conn = null;

    try {
      URL url = new URL("http://localhost:8080/newChatReqest");
      conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      conn.setRequestProperty("Accept", "application/json");
      conn.setDoOutput(true);

      // Printing for debugging
      System.out.println("Sending new chat request for userR: " + userR);

      // Remove quotes around true so that Accept is a boolean in JSON
      String jsonInputString = String.format(
          "{\"UserS\": \"%s\", \"UserR\": \"%s\", \"Accept\": true}",
          userS.replace("\"", "\\\""), userR.replace("\"", "\\\"")
      );

      // Sending JSON request
      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Request for new chat sent");
      }

      // Checking the response code
      int responseCode = conn.getResponseCode();
      System.out.println("Response code: " + responseCode);

      if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
        try (BufferedReader br = new BufferedReader(
                 new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
            response.append(line);
          }
          String responseBody = response.toString();
          System.out.println("Response from the server: " + responseBody);
          return true;
        }
      } else {
        System.err.println("Request for conversation failed");
        return false;
      }
    } catch (Exception e) {
      System.err.println("Request failed: " + e.getMessage());
      e.printStackTrace();
      return false;
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
  }
}

