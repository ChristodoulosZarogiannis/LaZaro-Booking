package accounts;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static org.junit.Assert.*;

public class AccountsTest {
    Accounts accounts;

    @Before
    public void setUp() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files\\accounts.ser"))) {
            accounts = (Accounts) in.readObject();
            Accounts.initialize(accounts);
        }
    }

    @Test
    public void getPassword() {
        assertEquals(Accounts.getPassword("chriszaro"), "12345");
        assertNotEquals(Accounts.getPassword("chriszaro"), "55555");
    }

    @Test
    public void accountExists() {
        assertTrue(Accounts.accountExists("chriszaro"));
        assertFalse(Accounts.accountExists("whatever"));
    }

    @Test
    public void addAccount() {
        Admin account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");

        assertFalse(Accounts.accountExists("testuser"));

        Accounts.addAccount(account);

        assertTrue(Accounts.accountExists("testuser"));
    }

    @Test
    public void getAccount() {
        Admin account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
        Accounts.addAccount(account);

        assertEquals(Accounts.getAccount("testuser"), account);
    }

    @Test
    public void getAccounts() {
        assertEquals(Accounts.getAccounts().get("chriszaro"), Accounts.getAccount("chriszaro"));
    }

    @Test
    public void deleteAccount() {
        Admin account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
        Accounts.addAccount(account);
        assertEquals(Accounts.getAccounts().get("testuser"), account);
        Accounts.deleteAccount("testuser");
        assertNull(Accounts.getAccounts().get("testuser"));
    }
}