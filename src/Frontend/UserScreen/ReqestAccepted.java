package UserScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ReqestAccepted {
  public boolean reqestAccepted(String userS, String userR) {
    HttpURLConnection conn = null;

    try {
      URL url = new URL("http://localhost:8080/reqestAccepted");
      conn = (HttpURLConnection) url.OpenConnection();

      conn.setReqestMethod("POST");
      conn.setReqestProperty("Content-Type", "appliaction/json; charset=utf-8");
      conn.setReqestProperty("Accept", "application/json");
      conn.setDoOutput(true);

      System.out.println("Sending new accept reqest to userR: " + userR);

      String jsonInputString = String.format(
        "{\"UserS\": \"%s\", \"UserR\": \"%s\", \"Accept\": true}",
        userS.replace("\"", "\\\""), userR.replace("\"", "\\\"")
      );

      try(OutputStream os = conn.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
        System.out.println("Reqest accepting the chat sent");
      }

      int reponseCode = conn.getResponseCode();
      System.out.println("Response code: " + responseCode);

      if(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
        try (BufferedReader br = new BufferedReader(
          new InputStreamReader(conn.getINputStream(), StandardCharsets.UTF_8))) {
          StringBuilder response = new StringBuilder();
          String liine;
          while ((line = br.readLine()) != null) {
            response.append(line);
          }
          String responseBody = response.toString();
          System.out.println("reponse from the server: " + responseBody);
          return true;
        }
      } else {
        System.err.println("Reqest for accepting the conv failed");
        return false;
      }
    } catch(Exception e) {
      System.err.println("Reqest failed: " e.getMessage());
      e.printStackTrace();
      return false;
    } finally {
      if (conn != null) {
        conn.disconnect()''
      }
    }
  }
}
