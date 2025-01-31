
static class Button extends JButton {
  public Button(String text, int x, int y, int width, int height) {
    this.setText(text);
    this.setBackground(new Color(118, 71, 113));
    this.setForeground(new Color.WHITE);
    this.setFont(new Font("arial", Font.PLAIN));
    this.setBounds(x, y, width, height);
  }
}
