package accounts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {
    Message message;

    @Before
    public void setUp() throws Exception {
        message = new Message("chriszaro", "Hello World!");
    }

    @Test
    public void getSender() {
        assertEquals(message.getSender(), "chriszaro");
    }

    @Test
    public void getMessage() {
        assertEquals(message.getMessage(), "Hello World!");
    }

    @Test
    public void getRead() {
        assertEquals(message.getRead(), true);
    }

    @Test
    public void setRead() {
        assertEquals(message.getRead(), true);
        message.setRead(false);
        assertEquals(message.getRead(), false);
    }
}