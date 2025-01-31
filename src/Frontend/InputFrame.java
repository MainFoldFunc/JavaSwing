public class InputFrame extends JTextField {
  public InputFrame() {
    this.setToolTipText("Enter" + text);
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    this.setBounds(x, y, width, height);
    this.setFont(new Font("arial", Font.PLAIN, 30))
  }
}
