package UserScreen;

import javax.swing.*;
import java.awt.*;

class TextField extends JTextArea { // Change from JTextField to JTextArea
  public TextField(int x, int y, int width, int height) {
    this.setBounds(x, y, width, height);
    this.setEditable(false);
    this.setBackground(new Color(117, 69, 109));
    this.setFont(new Font("Arial", Font.BOLD, 40)); // Fixed font syntax
    this.setBorder(BorderFactory.createLineBorder(new Color(233, 52, 94), 5));
    this.setForeground(new Color(233, 52, 94));
    this.setLineWrap(true); // Enable text wrapping
    this.setWrapStyleWord(true);
  }
}

