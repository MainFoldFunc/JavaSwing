package UserScreen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputFrame extends JTextField {
  public InputFrame(String text, int x, int y, int width, int height) {
    this.setToolTipText(text);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    this.setBackground(new Color(118, 71, 113));
    this.setForeground(new Color(255, 255, 255));
    this.setBounds(x, y, width, height);
    this.setFont(new Font("arial", Font.PLAIN, 30));
  }
}
