package accounts;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class Guest extending class {@link Account} - an account of a client in a booking application.
 * <p>
 * A "Guest" represents a registered user in our application. Guests can search for rooms to rent,
 * book and cancel rooms and see their profile.
 * <p>
 * It contains information about his/her login/authentication credentials and personal
 * information like phone, address etc.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class Guest extends Account implements Serializable {

    /**
     * Static UID for serialization of the class.
     */
    @Serial
    private static final long serialVersionUID = 1588517300584832941L;

    /**
     * Creates an {@link Account} type Guest with the constructor of the super class.
     *
     * @param username a String with the username selected by the user during registration
     * @param password a String with the password selected by the user during registration
     * @param name     a String with the account's owner name
     * @param email    a String with the account's owner email
     * @param phone    a String with the account's owner phone
     * @param address  a String with the account's owner home/business address
     * @param role     a String with user's role in the app
     */
    Guest(String username, String password, String name, String email, String phone, String address, String role) {
        super(username, password, name, email, phone, address, role);
    }
}
