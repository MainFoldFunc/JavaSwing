import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {

  static class FrameLogin extends JFrame implements ActionListener {
    Button buttonSubmit;
    Button buttonSignInLogin;
    InputForm loginLabel;
    InputForm passLabel;
    Label textLabel;

    public FrameLogin() {
      // Frame configuration
      this.setSize(800, 800);
      this.setTitle("Login to bank");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.getContentPane().setBackground(new Color(134, 131, 158));
      this.setLayout(null);
      this.setLocationRelativeTo(null);

      // Button configuration
      buttonSubmit = new Button("Submit");
      buttonSubmit.addActionListener(this);
      buttonSubmit.setBounds(250, 500, 300, 75);
      this.add(buttonSubmit);

      buttonSignInLogin = new Button("Don't have an account?");
      buttonSignInLogin.addActionListener(this);
      buttonSignInLogin.setBounds(250, 600, 300, 75);
      this.add(buttonSignInLogin);

      // Labels configuration
      loginLabel = new InputForm(250, 300, 300, 75);
      this.add(loginLabel);

      passLabel = new InputForm(250, 400, 300, 75);
      this.add(passLabel);

      textLabel = new Label("Log in");
      this.add(textLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonSubmit) {
        String login = loginLabel.getText();
        String pass = passLabel.getText();
        boolean correctData = checkIfCorrect(login, pass);
        if (correctData) {
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
    Label textLabelSignIn;

    public FrameSignIn() {
      this.setSize(800, 800);
      this.setTitle("Sign Up to bank");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.getContentPane().setBackground(new Color(134, 131, 158));
      this.setLayout(null);
      this.setLocationRelativeTo(null);

      // Button configuration
      buttonSubmitSignIn = new Button("Submit");
      buttonSubmitSignIn.addActionListener(this);
      buttonSubmitSignIn.setBounds(250, 500, 300, 75);
      this.add(buttonSubmitSignIn);

      // Labels configuration
      loginLabelSignIn = new InputForm(250, 300, 300, 75);
      this.add(loginLabelSignIn);

      passLabelSignIn = new InputForm(250, 400, 300, 75);
      this.add(passLabelSignIn);

      textLabelSignIn = new Label("Sign up");
      this.add(textLabelSignIn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == buttonSubmitSignIn) {
        String login = loginLabelSignIn.getText().trim();
        String password = passLabelSignIn.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
          JOptionPane.showMessageDialog(this, "Please enter both login and password", "Input Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        String pathToFile = "Users" + File.separator + "user" + login + ".txt";
        File usrFile = new File(pathToFile);

        if (usrFile.exists()) {
          JOptionPane.showMessageDialog(this, "Email already taken", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
          try {
            if (usrFile.createNewFile()) {
              try (FileWriter writeFile = new FileWriter(pathToFile)) {
                writeFile.write(login + "\n");
                writeFile.write(password + "\n");
                System.out.println("Added your data");
              }
            }
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while creating your file", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
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
        String corrpass = filescan.nextLine().trim();
        String corrlogin = filescan.nextLine().trim();
         // debug option //
        System.out.println(corrpass);
        System.out.println(corrlogin);

        boolean returnS = true;

        if(login.equals(corrlogin) && password.equals(corrpass)) {
          returnS = true;
        }
        else {
          returnS = false;
        }
        System.out.println(returnS);
        return returnS;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return false; // File doesn't exist or credentials don't match
  }

  static class Button extends JButton {
    public Button(String text) {
      this.setText(text);
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

  static class Label extends JLabel {
    public Label(String text) {
      this.setText(text);
      this.setForeground(new Color(218, 217, 179));
      this.setFont(new Font("arial", Font.BOLD, 50));
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

