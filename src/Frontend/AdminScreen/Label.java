package AdminScreen;

import javax.swing.*;
import java.awt.*;

class Label extends JLabel {
  public Label(int x, int y, int width, int height) {
    this.setBackground(new Color(117, 69, 109));
    this.setForeground(new Color(233, 52, 94));
    this.setBorder(BorderFactory.createLineBorder(new Color(233, 52, 94), 5));
    this.setBounds(x, y, width, height);
    this.setOpaque(true);  // Make the background visible
  }
}

