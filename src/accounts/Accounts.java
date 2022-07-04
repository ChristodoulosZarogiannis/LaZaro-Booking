package accounts;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents the structures that holds the information about
 * the users of our app.
 * <p>
 * HashMap helps us to have unique usernames. Other classes use this class
 * to check if a username is valid.
 * <p>
 * The methods of this class are all static. Only one object of this class
 * will be created during start-up of the program.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */

public class Accounts implements Serializable {
    /**
     * A HashMap that stores the accounts of the registered users in our app.
     * The key is the username of the other user e.g. "partner2" and the value is an {@link Account} object which represent the user and his/her info.
     */
    private static HashMap<String, Account> accounts;
    /**
     * A HashMap that stores the accounts of the registered users in our app.
     * The key is the username of the other user e.g. "partner2" and the value is an {@link Account} object which represent the user and his/her info.
     */
    private HashMap<String, Account> accountsO;

    /**
     * Static UID for serialization of the class.
     */
    @Serial
    private static final long serialVersionUID = -8807081298321571385L;

//    public Accounts() {
//        accounts = new HashMap<>();
//
//        Account admin = new Admin("chriszaro", "12345", "Christodoulos Zarogiannis", "chrizaro@csd.auth.gr", "6975424242", "fakeAddress", "admin");
//        accounts.put("chriszaro", admin);
//
//        Account guest1 = new Guest("guest1", "guest1", "fakeGuest", "fakeguest1@fakedomain.gr", "6900000001", "fakeAddress", "guest");
//        accounts.put("guest1", guest1);
//
//        Account partner = new Partner("lazar", "p", "Lazarr", "lazar@csd.auth.gr", "6912345678", "Puerto Rico", "partner");
//        accounts.put("lazar", partner);
//
//        Account par = new Partner("jack", "j", "Jack", "jac@csd.auth.gr", "6912345679", "Buenos Aires", "partner");
//        accounts.put("jack", par);
//
//        Account guest2 = new Guest("Petran", "passwd1", "Peter", "petran@csd.auth.gr", "6987654321", "Miami, Florida", "guest");
//        accounts.put("Petran", guest2);
//    }

    /**
     * Method for initializing the static field used for storing accounts.
     *
     * @param test temporary object
     */
    public static void initialize(Accounts test) {
        accounts = test.accountsO;
    }

    /**
     * Method for saving the static field used for storing accounts.
     *
     * @return Accounts object to get the non static HashMap
     */
    static Accounts closing() {
        Accounts test = new Accounts();
        test.accountsO = accounts;
        return test;
    }

    /**
     * Method for deleting an account
     * @param username the username of the user we want to delete
     */
    static void deleteAccount(String username){
        accounts.remove(username);
    }

    /**
     * @param username a String with the username of the user's password we want
     * @return if there is an account with this username, it returns
     * the password, else it returns null
     */
    static String getPassword(String username) {
        Account user = accounts.get(username);
        if (user != null)
            return user.getPass();
        else
            return null;
    }

    /**
     * @param username - the username that is checked if it is exists
     * @return true if the username exists in the HashMap, false if it doesn't
     */
    static boolean accountExists(String username) {
        return accounts.get(username) != null;
    }

    /**
     * @param newAccount the new account approved by an admin
     */
    static void addAccount(Account newAccount) {
        accounts.put(newAccount.getUsername(), newAccount);
    }

    /**
     * @param username a String with the username of the user we want to get his/her Account object
     * @return the Account object of this user
     */
    public static Account getAccount(String username) {
        return accounts.get(username);
    }

    /**
     * @return the static HashMap structure that stores the accounts
     */
    static HashMap getAccounts() {
        return accounts;
    }
}
