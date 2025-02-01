import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnetFramePostLogScreen extends JFrame implements ActionListener {

  public UnetFramePostLogScreen () {
    this.setSize(1920, 1080);
    this.setTitle("Unet");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(31, 33, 60));
    this.setLayout(null);
    this.setLocationRelativeTo(null);

  }

    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO: Add logic
    }
}
