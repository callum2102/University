import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private JPanel rootPanel;
    private JTextField userName;
    private JButton LogIn;
    private JList FriendList;
    private JList SuggestedList;
    private JLabel information;

    private String currentUser;
    private int currentID;
    private SocialNetwork friendsNetwork;

    public Main() {
        //Load the data
        friendsNetwork = new SocialNetwork();
        friendsNetwork.Load();

        LogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = userName.getText();
                currentID = friendsNetwork.FindUserID(currentUser);
                if (currentID == -1){
                    information.setText("User Information: Unknown");
                }
                else{
                    information.setText("User Information: User Name:"+currentUser+" User ID:"+ currentID);
                    String[] friends = friendsNetwork.GetMyFriends(currentUser); //changed to currentID, makes more sense
                    FriendList.setListData(friends);

                    String[] recommendations = friendsNetwork.GetRecommended(currentUser);
                    SuggestedList.setListData(recommendations);
                }

            }
        });


        //Clear the input field
        userName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                userName.setText("");
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
