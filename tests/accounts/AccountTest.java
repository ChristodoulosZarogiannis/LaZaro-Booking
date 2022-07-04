package accounts;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    Admin account;
    @Before
    public void setUp() throws Exception {
        account = new Admin("testuser", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");
    }

    @Test
    public void setEmail() {
        account.setEmail("newemail@csd.auth.gr");
        assertEquals(account.getEmail(), "newemail@csd.auth.gr");
    }

    @Test
    public void setPhone() {
        account.setPhone("6999999999");
        assertEquals(account.getPhone(), "6999999999");
    }

    @Test
    public void setAddress() {
        account.setAddress("Karamanli 20");
        assertEquals(account.getAddress(), "Karamanli 20");
    }

    @Test
    public void getUsername() {
        assertEquals(account.getUsername(), "testuser");
    }

    @Test
    public void getPass() {
        assertEquals(account.getPass(), "testpass");
    }

    @Test
    public void getEmail() {
        assertEquals(account.getEmail(), "testemail@csd.auth.gr");
    }

    @Test
    public void getPhone() {
        assertEquals(account.getPhone(), "6988888888");
    }

    @Test
    public void getName() {
        assertEquals(account.getName(), "Test Name");
    }

    @Test
    public void getAddress() {
        assertEquals(account.getAddress(), "Kalvoy 60");
    }

    @Test
    public void getRole() {
        assertEquals(account.getRole(), "admin");
    }

    @Test
    public void getNewMessageNotification() {
        Guest account2 = new Guest("testuser2", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");

        Message message = new Message("testuser", "Happy Holidays");

        if (account.getMessages().get("testuser2") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account.getMessages().put("testuser2", newArray);
        }
        account.addMessageToSender("testuser2", message);

        if (account2.getMessages().get("testuser") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account2.getMessages().put("testuser", newArray);
        }
        account2.addMessageToRecipient("testuser", message);

        assertEquals(account2.getNewMessageNotification(), true);
    }

    @Test
    public void getMessages() {
        Guest account2 = new Guest("testuser2", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");

        Message message = new Message("testuser", "Happy Holidays");

        if (account.getMessages().get("testuser2") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account.getMessages().put("testuser2", newArray);
        }
        account.addMessageToSender("testuser2", message);

        if (account2.getMessages().get("testuser") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account2.getMessages().put("testuser", newArray);
        }
        account2.addMessageToRecipient("testuser", message);

        ArrayList<Message> me = (ArrayList<Message>) account2.getMessages().get("testuser");
        assertEquals(me.get(0).getSender(), "testuser");
        assertEquals(me.get(0).getMessage(), "Happy Holidays");
    }

    @Test
    public void addMessageToRecipient() {
        Guest account2 = new Guest("testuser2", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");

        Message message = new Message("testuser", "Happy Holidays");

        if (account2.getMessages().get("testuser") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account2.getMessages().put("testuser", newArray);
        }
        account2.addMessageToRecipient("testuser", message);

        ArrayList<Message> me = (ArrayList<Message>) account2.getMessages().get("testuser");
        assertEquals(me.get(0).getSender(), "testuser");
        assertEquals(me.get(0).getMessage(), "Happy Holidays");
    }

    @Test
    public void addMessageToSender() {
        Guest account2 = new Guest("testuser2", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");

        Message message = new Message("testuser", "Happy Holidays");

        if (account.getMessages().get("testuser2") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account.getMessages().put("testuser2", newArray);
        }
        account.addMessageToSender("testuser2", message);

        ArrayList<Message> me = (ArrayList<Message>) account.getMessages().get("testuser2");
        assertEquals(me.get(0).getSender(), "testuser");
        assertEquals(me.get(0).getMessage(), "Happy Holidays");
    }

    @Test
    public void showConversation() {
        Guest account2 = new Guest("testuser2", "testpass", "Test Name", "testemail@csd.auth.gr", "6988888888", "Kalvoy 60", "admin");

        Message message = new Message("testuser", "Happy Holidays");

        if (account.getMessages().get("testuser2") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account.getMessages().put("testuser2", newArray);
        }
        account.addMessageToSender("testuser2", message);

        if (account2.getMessages().get("testuser") == null) {
            ArrayList<Message> newArray = new ArrayList<>();
            account2.getMessages().put("testuser", newArray);
        }
        account2.addMessageToRecipient("testuser", message);

        StringBuilder builder = new StringBuilder();
        builder.append("testuser");
        builder.append(": ");
        builder.append("Happy Holidays");
        builder.append("\n");

        assertEquals(account.showConversation("testuser2"), String.valueOf(builder));
    }
}