package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

class UserListPanel extends JPanel {
  public UserListPanel(int x, int y, int width, int height, List<String> userList) {
    this.setLayout(new GridLayout(0, 2, 10, 10)); // Two columns: username | button
    this.setBounds(x, y, width, height);
    this.setBackground(new Color(117, 69, 109));

    for (String user : userList) {
      JLabel userLabel = new JLabel(user);
      userLabel.setForeground(new Color(233, 52, 94));
      userLabel.setFont(new Font("Arial", Font.BOLD, 20));

      // Button with ActionListener
      Button chatButton = new Button("Chat", 0, 0, 100, 30);
      chatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          System.out.println("User selected: " + user); // Print the username
        }
      });

      this.add(userLabel);
      this.add(chatButton);
    }
  }
}

