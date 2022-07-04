package property;

import date.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BookingTest {
    private Booking booking;
    private String guest, partner;
    private Property property;
    private int y, m, d, length, price, maxDaysOfBooking;
    private Booking b2;
    private Booking b3;

    @Before
    public void setUp() throws Exception {
        // init variables
        guest = "guestName";
        partner = "partnerName";
        y = 2022;
        m = 4;
        d = 25;
        length = 4;
        price = 980;
        maxDaysOfBooking = 5;
        // init property
        property = new Property(partner, "Aleria caves", "A luxurious villa",
                "Loudovikou 29, Xaidari", "Villa", String.valueOf(price),
                String.valueOf(maxDaysOfBooking));
        // add property to Properties
        /*Properties.addProperty(property);*/
        // init bookings
        booking = new Booking(guest, property, y, m, d, length);
        b2 = new Booking(guest, property, y, m, d, length);
        b3 = new Booking(guest, property, y, m, d - 20, length);
        // add to Bookings
/*
        Bookings.addBooking(booking);
        Bookings.addBooking(b2);
        Bookings.addBooking(b3);
*/


    }

    @Test
    public void getFirstDay() {
        assertEquals(booking.getFirstDay(), Date.getDay(y, m, d));
    }

    @Test
    public void getLength() {
        assertEquals(booking.getLength(), length);
    }

    @Test
    public void getGuest() {
        assertEquals(booking.getGuest(), guest);
    }

    @Test
    public void getBookingUUID() {
        assertEquals(booking.getBookingUUID(), new BookingUUID(guest, Date.getDay(y, m, d), length));
    }

    @Test
    public void getYear() {
        assertEquals(booking.getYear(), y);
    }

    @Test
    public void getMonth() {
        assertEquals(booking.getMonth(), m);
    }

    @Test
    public void getDay() {
        assertEquals(booking.getDay(), d);
    }

    @Test
    public void getPrice() {
        assertEquals(booking.getPrice(), price * length);
        assertNotEquals(booking.getPrice(), price);
    }


    @Test
    public void testEquals() {
        assertEquals(booking, b2);
        assertNotEquals(booking, b3);
    }

    @Test
    public void testHashCode() {
        assertEquals(booking, b2);
        assertNotEquals(booking, b3);
    }

    @Test
    public void getProperty() {
        assertEquals(booking.getProperty(), property);
    }
}