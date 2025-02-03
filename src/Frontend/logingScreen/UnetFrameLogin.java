import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnetFrameLogin extends JFrame implements ActionListener {

  Button SignIn;
  Button LogIn;
  InputFrame LoginInput;
  InputFrame PassInput;

  public UnetFrameLogin() {
    this.setSize(800, 800);
    this.setTitle("Login for UnetEngine");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(31, 33, 60));
    this.setLayout(null);
    this.setLocationRelativeTo(null);

    LoginInput = new InputFrame("Login", 250, 200, 300, 50);
    this.add(LoginInput);

    PassInput = new InputFrame("Password", 250, 300, 300, 50);
    this.add(PassInput); // ✅ Fixed: Now correctly adding PassInput

    LogIn = new Button("Login", 300, 500, 200, 50);
    LogIn.addActionListener(this);
    this.add(LogIn);

    SignIn = new Button("Sign in", 300, 600, 200, 50); // ✅ Fixed: Adjusted Y position
    SignIn.addActionListener(this);
    this.add(SignIn); // ✅ Fixed: Now correctly adding SignIn

    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == LogIn) {
      handleLogin();
    } else if (e.getSource() == SignIn) {
      new UnetFrameSignin(); 
    }
  }

  private void handleLogin() {
    String loginText = LoginInput.getText();
    String passwordText = PassInput.getText();
    
    if (loginText.isEmpty() || passwordText.isEmpty()) {
      System.out.println("You need login and password");
      return; // Prevent further processing if fields are empty
    }

    System.out.println(loginText);
    System.out.println(passwordText);

    parseFromDatabaseLogin parser = new parseFromDatabaseLogin();
    boolean loggedIn = parser.loginCheck(loginText, passwordText);

    AdminChieck adminChieck = new AdminChieck();
    boolean isAdmin = adminChieck.loginCheck(loginText, passwordText);

    System.out.println("Admin permission: " + isAdmin);
    System.out.println("Logged in succesful: " + loggedIn);

    if (loggedIn && isAdmin) {
      System.out.println("Logged in as an Admin user");
      // new AdminFrameUnet();
    } else if (loggedIn) {
      System.out.println("Logged in as a normal user");
      // new UserFrameUnet();
    } else {
      System.out.println("Login or password not correct");
    }

    LoginInput.setText("");
    PassInput.setText("");
  }

}

