package accounts;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MessengerTest {
    Message test;
    Accounts accounts;
    @Before
    public void setUp() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files\\accounts.ser"))) {
            accounts = (Accounts) in.readObject();
            Accounts.initialize(accounts);
        }
        test = Messenger.newMessage("chriszaro", "Hello World!");
    }

    @Test
    public void newMessage() {
        assertEquals(test.getMessage(), "Hello World!");
        assertEquals(test.getSender(), "chriszaro");
    }

    @Test
    public void sent() {
        Messenger.sent("lazarosg", test);

        ArrayList<Message> messages1 = (ArrayList<Message>) Accounts.getAccount("lazarosg").getMessages().get("chriszaro");
        ArrayList<Message> messages2 = (ArrayList<Message>)  Accounts.getAccount("chriszaro").getMessages().get("lazarosg");

        assertEquals(messages1.get(messages1.size()-1).getSender(), "chriszaro");
        assertEquals(messages1.get(messages1.size()-1).getMessage(), "Hello World!");

        assertEquals(messages2.get(messages2.size()-1).getSender(), "chriszaro");
        assertEquals(messages2.get(messages2.size()-1).getMessage(), "Hello World!");
    }
}