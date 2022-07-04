package accounts;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class Partner extending class {@link Account} - an account of a partner in a booking application.
 * <p>
 * A "Partner" represents a registered user in our application. Partner owns rooms that registers and renting
 * in our application. Partner can add rooms, edit existing ones or delete them.
 * He can also see his/her profile.
 * <p>
 * It contains information about his/her login/authentication credentials and personal
 * information like phone, address etc.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class Partner extends Account implements Serializable {

    /**
     * Static UID for serialization of the class.
     */
    @Serial
    private static final long serialVersionUID = 7863923192700166252L;

    /**
     * Creates an {@link Account} type Partner with the constructor of the super class.
     *
     * @param username a String with username selected by the user during registration
     * @param password a String with the password selected by the user during registration
     * @param name     a String with the account's owner name
     * @param email    a String with the account's owner email
     * @param phone    a String with the account's owner phone
     * @param address  a String with the account's owner home/business address
     * @param role     a String with user's role in the app
     */
    Partner(String username, String password, String name, String email, String phone, String address, String role) {
        super(username, password, name, email, phone, address, role);
    }

}