package logingScreen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class Button extends JButton {
  public Button(String text, int x, int y, int width, int height) {
    this.setText(text);
    this.setBackground(new Color(118, 71, 113));
    this.setForeground(Color.WHITE);
    this.setFont(new Font("Arial", Font.PLAIN, 20));
    this.setBounds(x, y, width, height);
  }
}
