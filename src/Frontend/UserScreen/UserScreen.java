package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserScreen extends JFrame implements ActionListener {
  private InputFrame SearchUsers;
  private Button SendSearchUsers;
  private JPanel ResultArea; // Now using UserListPanel

  public UserScreen() {
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

    // Placeholder panel (empty at start)
    ResultArea = new JPanel();
    ResultArea.setBounds(100, 200, 400, 600);
    ResultArea.setBackground(new Color(117, 69, 109));
    this.add(ResultArea);

    this.setVisible(true);
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
      ResultArea = new UserListPanel(100, 200, 400, 600, CorrectUsersList);
      this.add(ResultArea);
      this.revalidate();
      this.repaint();
    }
  }
}

