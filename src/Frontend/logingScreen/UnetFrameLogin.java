package logingScreen;

import AdminFrame.AdminFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnetFrameLogin extends JFrame implements ActionListener {

  private Button signInButton;
  private Button logInButton;
  private InputFrame loginInput;
  private InputFrame passInput;

  public UnetFrameLogin() {
    // Frame settings
    this.setSize(800, 800);
    this.setTitle("Login for UnetEngine");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(31, 33, 60));
    this.setLayout(null);
    this.setLocationRelativeTo(null);

    // Login input using custom InputFrame
    loginInput = new InputFrame("Login", 250, 200, 300, 50);
    this.add(createLabel("Login:", 250, 170, 300, 30));
    this.add(loginInput);

    // Password input using custom InputFrame
    passInput = new InputFrame("Password", 250, 300, 300, 50);
    this.add(createLabel("Password:", 250, 270, 300, 30));
    this.add(passInput);

    // Login button using custom Button class
    logInButton = new Button("Login", 300, 500, 200, 50);
    logInButton.addActionListener(this);
    this.add(logInButton);

    // Sign-in button using custom Button class
    signInButton = new Button("Sign Up", 300, 600, 200, 50);
    signInButton.addActionListener(this);
    this.add(signInButton);

    this.setVisible(true);
  }

  private JLabel createLabel(String text, int x, int y, int width, int height) {
    JLabel label = new JLabel(text);
    label.setBounds(x, y, width, height);
    label.setForeground(Color.WHITE);
    label.setFont(new Font("Arial", Font.BOLD, 16));
    return label;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == logInButton) {
      handleLogin();
    } else if (e.getSource() == signInButton) {
      new UnetFrameSignin();
    }
  }

  private void handleLogin() {
    String loginText = loginInput.getText();
    String passwordText = passInput.getText(); // Use getText() for InputFrame

    if (loginText.isEmpty() || passwordText.isEmpty()) {
      JOptionPane.showMessageDialog(this, "You need to enter both login and password.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    parseFromDatabaseLogin parser = new parseFromDatabaseLogin();
    boolean loggedIn = parser.loginCheck(loginText, passwordText);

    AdminChieck adminCheck = new AdminChieck();
    boolean isAdmin = adminCheck.loginCheck(loginText, passwordText);

    if (loggedIn && isAdmin) {
      System.out.println(("Logged in as an Admin user from UnetFrameLogin"));
      new AdminFrame();
      this.dispose(); // Close login window
    } else if (loggedIn) {
      JOptionPane.showMessageDialog(this, "Logged in as a regular user.", "Success", JOptionPane.INFORMATION_MESSAGE);
      // new UserFrameUnet();
      this.dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Incorrect login or password.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    loginInput.setText("");
    passInput.setText("");
  }

}

