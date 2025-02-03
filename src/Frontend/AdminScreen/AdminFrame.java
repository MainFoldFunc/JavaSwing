import javax.swing.*;
import java.awt.*;
import java.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements ActionListener {

  public adminFrame() {
    this.setSize(1920, 1080);
    this.setTitle("Unet");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(31, 33, 60));
    this.setLayout(null);
    this.setLocationRelativeTo(null);


    @Override
    public voif actionPerformed(ActionEvent e) {
      //TODO: Add logic when buttons und 
    }
  }
}
