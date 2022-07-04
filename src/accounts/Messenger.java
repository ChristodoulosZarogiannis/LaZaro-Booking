package accounts;

import java.util.ArrayList;

/**
 * This class represents the messenger system of this application. It creates new messages
 * and sends them to the users that are a having a conversation. If a conversation doesn't exist,
 * it creates one.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class Messenger {
    /**
     * Method for creating a new message object.
     *
     * @param from    a String with the username of the user who sends the message
     * @param message a String with the message
     * @return a Message object which contains two Strings, username and message
     */
    public static Message newMessage(String from, String message) {
        Message m = new Message(from, message);
        return m;
    }

    /**
     * Method for adding the message object to both user's inbox.
     *
     * @param to the user the message is addressed to
     * @param m  a String with the message
     */
    public static void sent(String to, Message m) {
        String from = m.getSender();

        Account sender = Accounts.getAccount(from);
        Account receiver = Accounts.getAccount(to);

        if (sender.getMessages().get(to) == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            sender.getMessages().put(to, newArray);
        }
        sender.addMessageToSender(to, m);

        if (receiver.getMessages().get(from) == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            receiver.getMessages().put(from, newArray);
        }
        receiver.addMessageToRecipient(from, m);
    }
}
