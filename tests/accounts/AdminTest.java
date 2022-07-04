package accounts;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static org.junit.Assert.*;

public class AdminTest {
    Accounts accounts;

    @Before
    public void setUp() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files\\accounts.ser"))) {
            accounts = (Accounts) in.readObject();
            Accounts.initialize(accounts);
        }
    }

    @Test
    public void getAccountsToBeApproved() {
        Account account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
        Admin.addAccountToGetApproved(account);

        Object[][] test = (Object[][]) Admin.getAccountsToBeApproved();

        assertEquals(test[0][0], "testuser");
    }

    @Test
    public void newAccountsExist() {
        assertFalse(Admin.newAccountsExist());

        Account account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
        Admin.addAccountToGetApproved(account);

        assertTrue(Admin.newAccountsExist());

        Admin.approveAccounts();

        assertFalse(Admin.newAccountsExist());
    }

    @Test
    public void addAccountToGetApproved() {
        Account account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
        Admin.addAccountToGetApproved(account);

        Object[][] test = (Object[][]) Admin.getAccountsToBeApproved();

        assertEquals(test[0][0], "testuser");
    }

    @Test
    public void approveAccounts() {
        assertFalse(Accounts.accountExists("testuser"));

        Account account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
        Admin.addAccountToGetApproved(account);
        Admin.approveAccounts();

        assertTrue(Accounts.accountExists("testuser"));
    }

    @Test
    public void isNumeric() {
        assertTrue(Admin.isNumeric("123456789"));
        assertFalse(Admin.isNumeric("sdfadfasd"));
    }

    @Test
    public void showAccounts() {
        Object[][] test = (Object[][]) Admin.showAccounts();

        for (int i=0; i<Accounts.getAccounts().size(); i++) {
            assertTrue(Accounts.accountExists((String) test[i][0]));
        }
    }
}