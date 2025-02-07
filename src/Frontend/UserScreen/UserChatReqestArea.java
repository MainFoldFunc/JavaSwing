package UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

class UserChateReqestArea exteds JPanel {
  private UserScreen parentWindow;

  public UserChateReqestArea(int x, int y, int width, int height, List<String> ReqestList, UserScreen parentWindow) {
    this.parentWindow = parentWindow;
    this.setLayout(new GridLayout(0, 2, 10, 10));
    this.setBound(x, y, width, height);
    this.setBackground(new Color(117, 69, 109);

    for (String reqest : ReqestList) {
      JLabel reqestLabel = new JLAbel(reqest);
      reqestLabel.setForeground(new Color(233, 52, 94));
      reqestLabel.setFont(new Font("Arial", Font.BOLD, 20));

      Button AcceptChatButton = new Button("Chat", 0, 0, 500, 30);
      AcceptChatButton.addActionListener(new ActionListener() {
        parentWindow.setPerformed(ActionEvent e) {
          parentWindow.setSelectedUser(ReqestList);

          System.out.println("Selected reqest from parentWindow: " + parentWindow.setSelectedChatReqest);
          System.out.println("UserS from pawrentWindow: " + parentWindow.ReqestS);
          ReqestAccepted acc = new reqestAccepted(); // TODO: Change the name of the function to lowercase
          boolean err = req.reqestAccepted(parentWindow.setSelectedChatReqest, parentWindow.ReqestS);
          if (err != true) {
            System.out.println("Something went wrong while accepting conversation reqest");
          }else {
            System.out.println("Reqest Accepted...");
          }
          
        }
      });
      this.add(reqestLabel);
      this.add(AcceptChatButton);
    }
  }
}
