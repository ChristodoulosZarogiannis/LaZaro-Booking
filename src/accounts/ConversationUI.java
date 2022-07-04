package accounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates the GUI of a conversation between two users.
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class ConversationUI extends JDialog {

    /**
     * Creates the GUI of a conversation between two users.
     * @param connectedUsername a String with the username of the connected user
     * @param contact a String with the username of the interlocutor
     */
    ConversationUI(String connectedUsername, String contact) {
        Account connectedAccount = Accounts.getAccount(connectedUsername);
        setTitle("Conversation with " + contact);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new GridLayout(0, 1));
        setSize(300, 150);


        JTextArea textArea = new JTextArea(connectedAccount.showConversation(contact));
        textArea.setEditable(false);
        JScrollPane sp = new JScrollPane(textArea);
        add(sp);
        JButton replyButton = new JButton("Reply");

        replyButton.addActionListener(e -> {
            if (Accounts.accountExists(contact)) {
                new MessageUI(connectedUsername, contact);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "This user doesn't exist any more!", "User doesn't exist", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(replyButton);
        setVisible(true);
    }
}
