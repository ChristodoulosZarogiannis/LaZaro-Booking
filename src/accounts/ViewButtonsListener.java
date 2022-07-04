package accounts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class ViewButtonsListener implements ActionListener {

    /**
     * a String with the username of the connected user
     */
    private final String connectedUsername;

    /**
     * Constructor of Class that just sets value to connectedUsername
     * @param connected the username of the connected user
     */
    ViewButtonsListener(String connected) {
        connectedUsername = connected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        String lastWord = button.substring(button.lastIndexOf(" ") + 1);
        new ConversationUI(connectedUsername, lastWord);
    }
}
