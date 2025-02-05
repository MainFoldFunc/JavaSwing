package UserScreen;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NewChatReqest {
  public boolean newChatReqest(String userS, Strin userR) {
    HttpURLConnection conn = null;

    try {
      URL url = new URL("http:/localhost:8080/newChatReqest");
      conn = (HttpURLConnection) url.OpenConnection();

      conn.setReqestMethod("POST");
      conn.setReqestProperty("Content-Type", "application/json; charset=utf_8")
      conn.setReqestProperty("Accept", "application/json");
      conn.setDoOutput(true);

      String jsonInputString = String.format(
        "{\"userS\": \"%s\", \"userR\": \"%s\"}",
        userS.replace("\"", "\\\"");
        userR.replace("\"", "\\\"");
      );

      try (OutputStream os = conn.getOutputStream()) {
        byte[] input = json.InputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Reqest for new chat sent");
      }

      int responseCode = conn.getResponseCode();
      System.out.println("Response code: " + responseCode);

      if (responseCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
            response.append(line);
          }
          String responseBody = response.toString();
          System.out.println("Response from a server: " + responseBody);

          return true;
        }
      } else {
        System.err.println("Reqest for Conv failed");
        return false;
      }
    } catch (Exception e) {
      System.err.println("Reqest failed: " + e.getMessage());
      e.printStackTrace();
      return false
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
  }
}


