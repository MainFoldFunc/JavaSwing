import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnetFrame extends JFrame implements ActionListener {

  Button SignIn;
  Button LogIn;
  InputFrame LoginInput;
  InputFrame PassInput;

  public UnetFrame() {
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
    this.add(LogIn);

    SignIn = new Button("Sign in", 300, 600, 200, 50); // ✅ Fixed: Adjusted Y position
    this.add(SignIn); // ✅ Fixed: Now correctly adding SignIn

    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO
  }
}

