package AdminFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame implements ActionListener {

  private Label StockShow;

  public AdminFrame() {
    this.setSize(1920, 1080);
    this.setTitle("Unet");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(117, 69, 109));
    this.setLayout(null);
    this.setLocationRelativeTo(null);

    // Add text to the label
    StockShow = new Label( 150, 150, 1700, 1000);
    this.add(StockShow);

    System.out.println("The Admin frame is shown");

    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO: Add button logic
  }
}

