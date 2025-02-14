package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserScreen extends JFrame implements ActionListener {
  public InputFrame SearchUsers;
  public Button SendSearchUsers;
  public JPanel ResultArea;
  public JPanel ChatReqestsArea;
  public String selectedUser;  // Stores the selected user
  public String selectedReqest;
  public String userS;         // Stores the username passed in the constructor
  public String ReqestS;
  public Button chatReqests;

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

    SendSearchUsers = new Button("Search", 310, 100, 150, 50);
    SendSearchUsers.addActionListener(this);
    this.add(SendSearchUsers);

    chatReqests = new Button("Check for reqests", 500, 100, 400, 50);
    chatReqests.addActionListener(this);
    this.add(chatReqests);

    // Placeholder panel (empty at start)
    ResultArea = new JPanel();
    ResultArea.setBounds(100, 200, 400, 600);
    ResultArea.setBackground(new Color(117, 69, 109));
    this.add(ResultArea);

    ChatReqestsArea = new JPanel();
    ChatReqestsArea.setBounds(500, 200, 400, 600);
    ChatReqestsArea.setBackground(new Color(117, 69, 109));
    this.add(ChatReqestsArea);

    this.setVisible(true);
  }

  // This method will be called from UserListPanel when a user is selected
  public void setSelectedUser(String user) {
    this.selectedUser = user;
    System.out.println("Selected User: " + selectedUser);
  }
  public void setSelectedChatReqest(String user) {
    this.selectedReqest = user;
    System.out.println("User Reqests: " + selectedReqest);
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
    else if (e.getSource() == chatReqests) {
      HandlChatReqests handle = new HandlChatReqests();
      List<String> ChatReqests = handle.handleChatReqests();

      this.remove(ChatReqestsArea);
      ChatReqestsArea = new UserChatReqestsArea(500, 200, 400, 600, ChatReqests, this);
      this.add(ChatReqestsArea);
      this.revalidate();
      this.repaint();
    }
  }
}

