package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class UserScreen extends JFrame implements ActionListener {
    private InputFrame SearchUsers;
    private Button SendSearchUsers;

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

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SendSearchUsers) {
            String UserToFind = SearchUsers.getText();
            SearchUsers.setText("");

            // Fetch users
            List<String> CorrectUsersList = handleSearchUsers(); // Ensure this method exists!

            // Print user list
            for (int i = 0; i < CorrectUsersList.size(); i++) {
                System.out.println(CorrectUsersList.get(i));
            }
        }
    }

    // Dummy method to return users (Implement your own logic here)
    private List<String> handleSearchUsers() {
        // Example: Returning a static list of user names
        return List.of("Alice", "Bob", "Charlie");
    }
}

