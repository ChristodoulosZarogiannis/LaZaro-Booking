package accounts;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Account - an account of a user in a booking application.
 * <p>
 * An "Account" represents a registered user in our application. It contains
 * information about his/her login/authentication credentials and personal
 * information like phone, address etc.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class Account implements Serializable {

    /**
     * Static UID for serialization of the class.
     */
    @Serial
    private static final long serialVersionUID = -4361222698871618370L;

    /**
     * A HashMap that stores the personal information of a user.
     * The key is the type of the information e.g. "name" and the value is e.g. "Papadopoulos Giorgos".
     */
    private HashMap<String, String> credentials;
    /**
     * Whether a new message notification should exist or not
     */
    private boolean newMessageNotification;

    /**
     * A HashMap that stores the messages of a user.
     * The key is the username of the other user e.g. "partner2" and the value is an array with {@link Message} objects.
     */
    private HashMap<String, ArrayList<Message>> messages;

    /**
     * Constructor of an account that gets as parameters the credentials of the user
     *
     * @param username the username selected by the user during registration
     * @param password the password selected by the user during registration
     * @param name     the account's owner name
     * @param email    the account's owner email
     * @param phone    the account's owner phone
     * @param address  the account's owner home/business address
     * @param role     user's role in the app
     */
    Account(String username, String password, String name, String email, String phone, String address, String role) {
        credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        credentials.put("name", name);
        credentials.put("email", email);
        credentials.put("phone", phone);
        credentials.put("address", address);
        credentials.put("role", role);
        messages = new HashMap<>();
        newMessageNotification = false;
    }

    //setters

    /**
     * Method for setting new value to user's email
     * @param newEmail the new email
     */
    void setEmail(String newEmail) {
        credentials.put("email", newEmail);
    }

    /**
     * Method for setting new value to user's phone
     * @param newPhone the new phone number
     */
    void setPhone(String newPhone) {
        credentials.put("phone", newPhone);
    }

    /**
     * Method for setting new value to user's address
     * @param newAddress the new address
     */
    void setAddress(String newAddress) {
        credentials.put("address", newAddress);
    }

    //getters

    /**
     * Method for returning the user's username
     *
     * @return a String with the username registered to this account
     */
    String getUsername() {
        return credentials.get("username");
    }

    /**
     * Method for returning the user's password
     *
     * @return a String with the password registered to this account
     */
    String getPass() {
        return credentials.get("password");
    }

    /**
     * Method for returning the user's email
     *
     * @return a String with the email registered to this account
     */
    String getEmail() {
        return credentials.get("email");
    }

    /**
     * Method for returning the user's phone
     *
     * @return a String with the phone registered to this account
     */
    String getPhone() {
        return credentials.get("phone");
    }

    /**
     * Method for returning the user's name
     *
     * @return a String with the name registered to this account
     */
    String getName() {
        return credentials.get("name");
    }

    /**
     * Method for returning the user's address
     *
     * @return a String with the address registered to this account
     */
    String getAddress() {
        return credentials.get("address");
    }

    /**
     * Method for returning the user's role
     *
     * @return a String with the role of this user
     */
    String getRole() {
        return credentials.get("role");
    }


    /**
     * @return a boolean which is true if there is new message in the inbox
     * of the user
     */
    boolean getNewMessageNotification() {
        return newMessageNotification;
    }

    /**
     * Method for returning the structure (HashMap) with the user's messages
     *
     * @return a HashMap with the messages of the user
     */
    HashMap getMessages() {
        return messages;
    }

    /**
     * Method for adding a message to the conversation with another user
     * the conversation is stored in an array. Each message is stored inside
     * a cell.
     *
     * @param user the user to whoms conversation the message is added, either
     *             the sender or the recipient
     * @param m    - object of accounts.Message, contains two Strings, the sender
     *             of the message and the message
     */
    void addMessageToRecipient(String user, Message m) {
        newMessageNotification = true;
        Message message = new Message(m.getSender(), m.getMessage());
        message.setRead(false);
        messages.get(user).add(message);
    }

    /**
     * This method adds a Message object to the array containing the messages with the user
     *
     * @param user the user this account is communicating with
     * @param m    Object Message containing the sender of the message and the message itself
     */
    void addMessageToSender(String user, Message m) {
        messages.get(user).add(m);
    }

    /**
     * Method for printing the whole conversation with another user
     * @param person - the user with who exchanges messages
     * @return a String with the conversation
     */
    String showConversation(String person) {
        newMessageNotification = false;
        StringBuilder builder = new StringBuilder();
        for (Message x : messages.get(person)) {
            if (!x.getRead())
                x.setRead(true);
            builder.append(x.getSender());
            builder.append(": ");
            builder.append(x.getMessage());
            builder.append("\n");
        }
        return String.valueOf(builder);
    }
}