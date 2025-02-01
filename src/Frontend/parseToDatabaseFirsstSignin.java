import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class parseToDatabaseFirsstSignin{
    public void parseToDatabaseFirsstSignin(String Login, String Password) {
        try {
            // Define the endpoint URL
            URL url = new URL("http://localhost:8080");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set up the POST request
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true); // Enable output stream

            // JSON data to send
            String jsonInputString = "{\"login\": Login, \"password\": Password}";

            // Write the JSON data to the output stream
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the HTTP response code
            int status = con.getResponseCode();
            System.out.println("Response Code: " + status);

            // Read the response body
            try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    status >= 200 && status < 300 ? con.getInputStream() : con.getErrorStream(),
                    StandardCharsets.UTF_8
                )
            )) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response Body: " + response);
            }

            // Disconnect the connection
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
