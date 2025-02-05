package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserScreen extends JFrame implements ActionListener {
  private InputFrame SearchUsers;
  private JButton SendSearchUsers;
  private JPanel ResultArea;
  private String selectedUser;  // Stores the selected user
  private String userS;         // Stores the username passed in the constructor

  public UserScreen(String UserS) {
    this.userS = UserS;  // Save the parameter for later use

    this.setSize(1920, 1080);
    this.setTitle("Unet");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.getContentPane().setBackground(new Color(117, 69, 109));
    this.setLayout(null);
    this.setLocationRelativeTo(null);

    SearchUsers = new InputFrame("Search for users to chat with", 100, 100, 200, 50);
    this.add(SearchUsers);

    SendSearchUsers = new JButton("Search");
    SendSearchUsers.setBounds(310, 100, 150, 50);
    SendSearchUsers.addActionListener(this);
    this.add(SendSearchUsers);

    // Placeholder panel (empty at start)
    ResultArea = new JPanel();
    ResultArea.setBounds(100, 200, 400, 600);
    ResultArea.setBackground(new Color(117, 69, 109));
    this.add(ResultArea);

    this.setVisible(true);
  }

  // This method will be called from UserListPanel when a user is selected
  public void setSelectedUser(String user) {
    this.selectedUser = user;
    System.out.println("Selected User: " + selectedUser);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == SendSearchUsers) {
      String UserToFind = SearchUsers.getText();
      SearchUsers.setText("");

      HandleSearchUsers search = new HandleSearchUsers();
      List<String> CorrectUsersList = search.handleSearchUsers(UserToFind);

      // Remove old results and add new UserListPanel
      this.remove(ResultArea);
      ResultArea = new UserListPanel(100, 200, 400, 600, CorrectUsersList, this);  // Pass parent window reference
      this.add(ResultArea);
      this.revalidate();
      this.repaint();
    }
  }

  public void sendChatRequest() {
    // Ensure selectedUser is set
    if (selectedUser != null && !selectedUser.isEmpty()) {
      NewChatReqest request = new NewChatReqest();
      boolean status = request.newChatRequest(this.userS, selectedUser);
      if (status) {
        System.out.println("Request sent!");
      } else {
        System.out.println("Something went wrong, try again later");
      }
    } else {
      System.out.println("No user selected. Please select a user from the list.");
    }
  }
}
