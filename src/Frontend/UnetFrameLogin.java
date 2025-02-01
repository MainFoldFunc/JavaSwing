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
      if (LoginInput.getText().equals("") || PassInput.getText().equals("") ) {
        System.out.println("You need login and password");
      } else {
        String Login = LoginInput.getText();
        String Password = PassInput.getText();
        System.out.println(Login);
        System.out.println(Password);

        parseFromDatabaseLogin parser = new parseFromDatabaseLogin();
        boolean LoggedIn = parser.loginCheck(Login, Password);
        
        if (LoggedIn) {
          System.out.println("Logged in succesfully");
          // new UnetFrame();
        }
      }

      LoginInput.setText("");
      PassInput.setText("");



    }
    else if (e.getSource() == SignIn) {
      new UnetFrameSignin(); 
    }
  }
}

