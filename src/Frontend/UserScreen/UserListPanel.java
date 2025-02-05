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
      Button chatButton = new Button("Chat", 0, 0, 100, 30);
      chatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Set selected user in the parent window
          parentWindow.setSelectedUser(user);  // Call method from UserScreen
          
          // Print the selected user and userS from the parent window
          System.out.println("Selected User from parentWindow: " + parentWindow.selectedUser);
          System.out.println("UserS from parentWindow: " + parentWindow.userS);
          NewChatReqest req = new NewChatReqest();
          boolean err = req.newChatRequest(parentWindow.userS, parentWindow.selectedUser);
          if (err != true) {
            System.out.println("Something went wrong in the NewChatReqest");
          }else {
            System.out.println("Everything is ok you can proceed");
          }
        }
      });

      this.add(userLabel);
      this.add(chatButton);
    }
  }
}

