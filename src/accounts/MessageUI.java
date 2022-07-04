package accounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class MessageUI extends JDialog {

    /**
     * Text area for new message
     */
    private JTextArea textArea;

    /**
     * a String with the username of the connected user
     */
    private final String connectedUsername;

    /**
     * a String with the recipient's username
     */
    private final String recipient;


    /**
     * Default Constructor
     * @param connectedUser a String with the username of the connected user
     * @param recipient a String with the username of the recipient of the message
     */
    MessageUI(String connectedUser, String recipient){
        connectedUsername = connectedUser;
        this.recipient=recipient;
        setTitle("New Message");
        setModal(false);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(0,1));
        setSize(300, 150);

        textArea = new JTextArea();
        add(textArea);
        JButton sendButton = new JButton("Send");
        add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSend();
            }
        });
        setVisible(true);
    }

    /**
     * This method implements the function of send message button.
     */
    private void onSend() {
        if (!textArea.getText().isBlank())
            Messenger.sent(recipient, Messenger.newMessage(connectedUsername, textArea.getText()));
        dispose();
    }
}
