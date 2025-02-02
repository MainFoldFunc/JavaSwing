import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharset;
import java.util.HashMap;
import java.util.Scanner;

class AdminAdder {
  public static void main(String[], args) {
    HashMap<String, int> admins = new HashMap<String, int>();
    Scanner scan = new Scanner(System.in);
    Scanner scanLogin = new Scanner(System.in);
    String exit = "";
    while (String exit.equals("")) {
     System.out.println("Enter admin e-mail: ");
     String login = scanLogin.nextLine();
     admins.put(login, 1);

     exit = scan.NextLine();
    }
    scan.close();
    scanLogin.close();
    try {
      String urlString = "localhost:8080/AdminCheck";
      URL url = new URL(urlString);
      HttpURLConnection conn = (HttpURLConnection) url.OpenConnection();
      conn.setRequestMethod("POST");
      conn.setReqestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      String jsonInputString = "{\"admin\": \}" 
      
    }
  }
}
