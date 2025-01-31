import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnetFrame extends JFrame implements ActionListener {

  Button SignIn;
  Button LogIn;
  InputFrame LoginInput;
  InputFrame Password;

  public UnetFrame() {
    this.setSize(800, 800);
    this.setTitle("Login for UnetEngine");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fixed
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(31, 33, 60)); // Fixed
    this.setLayout(null);
    this.setLocationRelativeTo(null);

    LoginInput = new InputFrame("Login", 250, 200, 300, 50);
    this.add(LoginInput);
    PassInput = new InputFrame("Password", 250, 200, 300, 50);
    this.add(LoginInput);


    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) { // Fixed
    // TODO
  }
}

