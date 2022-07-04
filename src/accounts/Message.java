package accounts;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class creates objects that represent a message in a messenger
 * or email type application. The Message object contains two Strings, one for the
 * message itself and one for the user that sends the message.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
class Message implements Serializable {

    /**
     * Static UID for serialization of the class.
     */
    @Serial
    private static final long serialVersionUID = 8004763306694404974L;

    /**
     * The name of the user who sent the message
     */
    private final String from;
    /**
     * The message itself
     */
    private final String message;

    /**
     * The state of the message, read or unread.
     */
    private boolean read;

    /**
     * Constructor
     *
     * @param from    the username of the user who sends the message
     * @param message a String with the message
     */
    Message(String from, String message) {
        this.read = true;
        this.from = from;
        this.message = message;
    }

    /**
     * @return a String with the sender's username
     */
    String getSender() {
        return from;
    }

    /**
     * @return a String with the message
     */
    String getMessage() {
        return message;
    }

    /**
     * @return true if the message has been read
     */
    Boolean getRead() {
        return read;
    }

    /**
     * method for changing the value of read
     * @param read the new value
     */
    void setRead(boolean read) {
        this.read = read;
    }
}
