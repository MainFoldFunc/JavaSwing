package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

class UserListPanel extends JPanel {
  private UserScreen parentWindow;

  // Constructor with UserScreen reference
  public UserListPanel(int x, int y, int width, int height, List<String> userList, UserScreen parentWindow) {
    this.parentWindow = parentWindow;  // Store the reference of parent window
    this.setLayout(new GridLayout(0, 2, 10, 10)); // Two columns: username | button
    this.setBounds(x, y, width, height);
    this.setBackground(new Color(117, 69, 109));

    for (String user : userList) {
      JLabel userLabel = new JLabel(user);
      userLabel.setForeground(new Color(233, 52, 94));
      userLabel.setFont(new Font("Arial", Font.BOLD, 20));

      // Button with ActionListener
      JButton chatButton = new JButton("Chat");
      chatButton.setPreferredSize(new Dimension(100, 30));
      chatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Set selected user in the parent window
          parentWindow.setSelectedUser(user);  // Call method from UserScreen
        }
      });

      this.add(userLabel);
      this.add(chatButton);
    }
  }
}
