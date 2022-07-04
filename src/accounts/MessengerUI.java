package accounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class MessengerUI extends JDialog {
    /**
     * a String with the username of the connected user
     */
    private final String connectedUsername;

    /**
     * the account of the connected user
     */
    private final Account connectedAccount;

    /**
     * Default Constructor. Creates the JDialog frame.
     * @param aString a String with the username of the connected user
     */
    MessengerUI(String aString) {
        connectedUsername = aString;
        connectedAccount = Accounts.getAccount(connectedUsername);

        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth()/3;
        int height = (int)size.getHeight()/3;
        setLocation(width, height);

        setTitle("Messenger");
        setModal(false);
        setResizable(false);
        setLayout(new GridLayout(0, 2));

        JButton newMessageButton = new JButton("New");
        newMessageButton.addActionListener(e -> onNew());
        add(newMessageButton);
        JButton fakeButton = new JButton("");
        fakeButton.setEnabled(false);
        add(fakeButton);

        createMessages();

        pack();
        setVisible(true);
    }

    /**
     * This method fills the frame with the contacts and the last messages.
     */
    private void createMessages(){
        ViewButtonsListener listener = new ViewButtonsListener(connectedUsername);

        HashMap<String, ArrayList> messages = connectedAccount.getMessages();
        if (!messages.keySet().isEmpty()) {
            int counter = 0;
            JTextArea[] textAreas = new JTextArea[messages.keySet().size()];
            JButton[] viewButtons = new JButton[messages.keySet().size()];
            JPanel[] panels = new JPanel[messages.keySet().size()];
            for (String x : messages.keySet()) {
                StringBuilder builder = new StringBuilder();
                builder.append(x);
                builder.append("\n");
                ArrayList<Message> array = messages.get(x);
                int lastElement = array.size() - 1;
                builder.append("Last message: ");
                builder.append(array.get(lastElement).getSender());
                builder.append(" said\n'");
                builder.append(array.get(lastElement).getMessage());
                builder.append("'");
                textAreas[counter] = new JTextArea(String.valueOf(builder));
                textAreas[counter].setEditable(false);

                panels[counter] = new JPanel();
                panels[counter].add(textAreas[counter]);
                add(panels[counter]);

                viewButtons[counter] = new JButton("View messages with "+x);
                viewButtons[counter].addActionListener(listener);
                if (!array.get(lastElement).getRead())
                    viewButtons[counter].setBackground(Color.YELLOW);
                add(viewButtons[counter]);

                counter++;
            }
        }
    }


    /**
     * This method implements the function of the new message button.
     */
    private void onNew() {
        String to = JOptionPane.showInputDialog("To:");
        while (!Accounts.accountExists(to) && to!=(null)){
            JOptionPane.showMessageDialog(null, "User doesn't exist", "Invalid Input",
                    JOptionPane.INFORMATION_MESSAGE);
            to = JOptionPane.showInputDialog("To:");
        }
        if (to != null)
            new MessageUI(connectedUsername, to);
    }
}
