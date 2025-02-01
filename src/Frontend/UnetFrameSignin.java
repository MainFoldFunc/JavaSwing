import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnetFrameSignin extends JFrame implements ActionListener {
  Button SignIn;
  Button LogIn;
  InputFrame LoginInput;
  InputFrame PassInput;

  public UnetFrameSignin() {
    this.setSize(800, 800);
    this.setTitle("Sign for UnetEngine");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(31, 33, 60));
    this.setLayout(null);
    this.setLocationRelativeTo(null);
    
    LoginInput = new InputFrame("Login", 250, 200, 300, 50);
    this.add(LoginInput);

    PassInput = new InputFrame("Password", 250, 300, 300, 50);
    this.add(PassInput);

    LogIn = new Button("Login", 300, 500, 200, 50);
    LogIn.addActionListener(this); // ✅ Now correctly using `this`
    this.add(LogIn);

    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == LogIn) {
      if (LoginInput.getText().equals("")|| PassInput.getText().equals("")) {
        System.out.println("You need password and login");
      } else {
        String Login = LoginInput.getText();
        String Password = PassInput.getText();
        parseToDatabaseFirstSignin(Login, Password);
      }

      LoginInput.setText("");
      PassInput.setText("");
    }
  }
}

