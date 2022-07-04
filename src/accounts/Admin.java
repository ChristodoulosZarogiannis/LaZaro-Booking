package accounts;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Admin extending class {@link Account} - an account of an administrator in a booking application.
 * <p>
 * An "Admin" represents a registered user in our application. Admin can approve new accounts,
 * send messages to other users, search through other users accounts,  access personal
 * information of other users and search and view reservations of other users.
 * <p>
 * It contains information about his/her login/authentication credentials and personal
 * information like phone, address etc.
 * <p>
 * This class is part of the "laZaro booking" application.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class Admin extends Account implements Serializable {
    /**
     * The accounts waiting for approval
     */
    private static ArrayList<Account> accountsToBeApproved = new ArrayList<>();
    /**
     * The accounts waiting for approval used for initialization
     */
    private ArrayList<Account> accountsToBeApproved0 = new ArrayList<>();

    /**
     * Static UID for serialization of the class.
     */
    @Serial
    private static final long serialVersionUID = -8336344971062919840L;

    /**
     * Creates an {@link Account} type Admin with the constructor of the super class.
     *
     * @param username a String with the username selected by the user during registration
     * @param password a String with the password selected by the user during registration
     * @param name     a String with the account's owner name
     * @param email    a String with the account's owner email
     * @param phone    a String with the account's owner phone
     * @param address  a String with the account's owner home/business address
     * @param role     a String with user's role in the app
     */
    Admin(String username, String password, String name, String email, String phone, String address, String role) {
        super(username, password, name, email, phone, address, role);
    }

    /**
     * Method for initializing the static field used for storing pending accounts.
     * @param test temporary object
     */
    public static void initialize(Admin test) {
        accountsToBeApproved = test.accountsToBeApproved0;
    }

    /**
     * Method for saving the static field used for storing pending accounts.
     * @return Admin object for saving
     */
    static Admin closing() {
        Admin test = new Admin("fake", "fake", "fake", "fake", "fake", "fake", "fake");
        test.accountsToBeApproved0 = accountsToBeApproved;
        return test;
    }

    /**
     * @return a table with the accounts waiting for approval
     */
    static Object[] getAccountsToBeApproved() {
        Object[][] table = new Object[0][];
        if (!accountsToBeApproved.isEmpty()) {
            table = new Object[accountsToBeApproved.size()][];
            int i = 0;
            for (Account x : accountsToBeApproved) {
                table[i] = new Object[6];
                table[i][0] = x.getUsername();
                table[i][1] = x.getPass();
                table[i][2] = x.getEmail();
                table[i][3] = x.getPhone();
                table[i][4] = x.getAddress();
                table[i][5] = x.getRole();
                i++;
            }
        }
        return table;
    }

    /**
     * This method is being used to inform an Admin of new accounts pending to get approved.
     *
     * @return true if there are new accounts, false if there are not.
     */
    public static boolean newAccountsExist() {
        return !accountsToBeApproved.isEmpty();
    }

    /**
     * This method adds a new registered account to the waiting list to get
     * approved by an admin.
     *
     * @param newAccount an {@link Account} object created with the new users credentials
     */
    public static void addAccountToGetApproved(Account newAccount) {
        accountsToBeApproved.add(newAccount);
    }

    /**
     * This method adds every {@link Account} object from inside the ArrayList with the
     * pending to get approved accounts into the HashMap structure with the active accounts.
     */
    static void approveAccounts() {
        for (Account x : accountsToBeApproved)
            Accounts.addAccount(x);
        accountsToBeApproved.clear();
    }

    /**
     * this method helps searchUser and registration
     *
     * @param strNum the string we want to check if it is numeric
     * @return true if the string is numeric, false if it isn't
     */
    static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            long d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * @return a table with all the accounts
     */
    static Object[][] showAccounts() {
        HashMap<String, Account> accounts = Accounts.getAccounts();
        Object[][] table;
        table = new Object[accounts.size()][];
        int i = 0;
        for (String x : accounts.keySet()) {
            table[i] = new Object[6];
            table[i][0] = Accounts.getAccount(x).getUsername();
            table[i][1] = Accounts.getAccount(x).getPass();
            table[i][2] = Accounts.getAccount(x).getEmail();
            table[i][3] = Accounts.getAccount(x).getPhone();
            table[i][4] = Accounts.getAccount(x).getAddress();
            table[i][5] = Accounts.getAccount(x).getRole();
            i++;
        }
        return table;
    }
}