import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

class AdminAdder {
  public static void main(String[] args) {
    HashMap<String, Integer> admins = new HashMap<>();
    Scanner scan = new Scanner(System.in);
    Scanner scanLogin = new Scanner(System.in);
    String exit = "";

    // Collect admin emails
    while (exit.equals("")) {
      System.out.println("Enter admin e-mail: ");
      String login = scanLogin.nextLine();
      admins.put(login, 1);  // 1 is the value associated with each email (admin: int)

      System.out.println("Press Enter to continue or type anything to exit.");
      exit = scan.nextLine();  // Let the user press Enter to continue, or type anything to stop the loop
    }

    scan.close();
    scanLogin.close();

    // Sending a POST request for each admin
    try {
      String urlString = "http://localhost:8080/AdminCheck";  // Ensure this URL is correct
      for (String adminEmail : admins.keySet()) {
        // Create the JSON data for the admin
        String jsonInputString = "{\"admin\": 1, \"login\": \"" + adminEmail + "\"}";  // admin is an integer, login is the email string

        // Open the connection and set up the POST request
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Send the request
        try (OutputStream os = conn.getOutputStream()) {
          byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
          os.write(input, 0, input.length);
        }

        // Handle the response (optional)
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code for " + adminEmail + ": " + responseCode);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

