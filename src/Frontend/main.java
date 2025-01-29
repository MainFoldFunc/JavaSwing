import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
  static class FrameLogin extends JFrame implements ActionListener {
    Button buttonSubmit;
    Button buttonSignInLogin;
    InputForm loginLabel;
    InputForm passLabel;
    CustomLabel textLabel;

    public FrameLogin() {
      this.setSize(800, 800);
      this.setTitle("Login to bank");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.getContentPane().setBackground(new Color(134, 131, 158));
      this.setLayout(null);
      this.setLocationRelativeTo(null);

      buttonSubmit = new Button("Submit");
      buttonSubmit.addActionListener(this);
      buttonSubmit.setBounds(250, 500, 300, 75);
      this.add(buttonSubmit);

      buttonSignInLogin = new Button("Don't have an account?");
      buttonSignInLogin.addActionListener(this);
      buttonSignInLogin.setBounds(250, 600, 300, 75);
      this.add(buttonSignInLogin);

      loginLabel = new InputForm(250, 300, 300, 75);
      this.add(loginLabel);

      passLabel = new InputForm(250, 400, 300, 75);
      this.add(passLabel);

      textLabel = new CustomLabel("Log in");
      this.add(textLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonSubmit) {
        String login = loginLabel.getText();
        String pass = passLabel.getText();
        if (checkIfCorrect(login, pass)) {
          System.out.println("Logged in successfully");
        } else {
          System.out.println("Incorrect login credentials");
        }
      } else if (e.getSource() == buttonSignInLogin) {
        this.dispose();
        FrameSignIn signInFrame = new FrameSignIn();
        signInFrame.setVisible(true);
      }
    }
  }

  static class FrameSignIn extends JFrame implements ActionListener {
    Button buttonSubmitSignIn;
    InputForm loginLabelSignIn;
    InputForm passLabelSignIn;
    CustomLabel textLabelSignIn;

    public FrameSignIn() {
      this.setSize(800, 800);
      this.setTitle("Sign Up to bank");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.getContentPane().setBackground(new Color(134, 131, 158));
      this.setLayout(null);
      this.setLocationRelativeTo(null);

      buttonSubmitSignIn = new Button("Submit");
      buttonSubmitSignIn.addActionListener(this);
      buttonSubmitSignIn.setBounds(250, 500, 300, 75);
      this.add(buttonSubmitSignIn);

      loginLabelSignIn = new InputForm(250, 300, 300, 75);
      this.add(loginLabelSignIn);

      passLabelSignIn = new InputForm(250, 400, 300, 75);
      this.add(passLabelSignIn);

      textLabelSignIn = new CustomLabel("Sign up");
      this.add(textLabelSignIn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonSubmitSignIn) {
        if (loginLabelSignIn.getText().isEmpty() || passLabelSignIn.getText().isEmpty()) {
          System.out.println("You cannot login without params");
        } else {
          try {
            String login = loginLabelSignIn.getText();
            String password = passLabelSignIn.getText();

            URL urlServer = new URL("http://localhost:42069/signIn");
            HttpURLConnection connection = (HttpURLConnection) urlServer.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            String jsonInput = String.format("{ \"login\": \"%s\", \"password\": \"%s\" }", login, password);

            try (OutputStream os = connection.getOutputStream()) {
              byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
              os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
  }

  static boolean checkIfCorrect(String login, String password) {
    String pathToFile = "Users" + File.separator + "user" + login + ".txt";
    File usrFile = new File(pathToFile);
    if (usrFile.exists()) {
      try (Scanner filescan = new Scanner(usrFile)) {
        String corrlogin = filescan.nextLine().trim();
        String corrpass = filescan.nextLine().trim();
        return login.equals(corrlogin) && password.equals(corrpass);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  static class Button extends JButton {
    public Button(String text) {
      super(text);
      this.setBackground(new Color(99, 104, 150));
      this.setForeground(new Color(218, 217, 179));
      this.setFont(new Font("Arial", Font.BOLD, 20));
    }
  }

  static class InputForm extends JTextField {
    public InputForm(int x, int y, int width, int height) {
      this.setBounds(x, y, width, height);
      this.setForeground(new Color(218, 217, 179));
      this.setBackground(new Color(99, 104, 150));
      this.setFont(new Font("Arial", Font.BOLD, 25));
    }
  }

  static class CustomLabel extends JLabel {
    public CustomLabel(String text) {
      super(text);
      this.setForeground(new Color(218, 217, 179));
      this.setFont(new Font("Arial", Font.BOLD, 50));
      this.setVerticalAlignment(JLabel.CENTER);
      this.setHorizontalAlignment(JLabel.CENTER);
      this.setBounds(250, 25, 300, 100);
    }
  }

  public static void main(String[] args) {
    FrameLogin frame = new FrameLogin();
    frame.setVisible(true);
  }
}

