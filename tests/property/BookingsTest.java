package property;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookingsTest {
    private Booking b, b2;
    private Property p;
    private String partner, guest;
    private int y, m, d, length, price, maxDaysOfBooking;

    @Before
    public void setUp() throws Exception {
        Bookings.initialize();
        guest = "guest";
        partner = "partner";
        y = 2023;
        m = 4;
        d = 28;
        length = 9;
        price = 560;
        maxDaysOfBooking = 15;

        p = new Property(partner, "Aleria caves", "A luxurious villa",
                "Pyrgos, Naxos", "Villa",
                String.valueOf(price), String.valueOf(maxDaysOfBooking));
        b = new Booking(guest, p, y, m, d, length);
        Bookings.addBooking(b);
        b2 = new Booking(guest, p, y, m - 2, d, length); // not to be added to the bookings hashmap
    }

    @Test
    public void addBooking() {
        assertEquals(Bookings.getBooking(b.getBookingUUID()), b);
        assertNotEquals(Bookings.getBooking(b.getBookingUUID()), b2);
    }

    @Test
    public void getBookingsOfPartner() {
/*
        ArrayList<Booking> arrayList = new ArrayList<>();
        arrayList.add(b);
*/
        assertEquals(Bookings.getBookingsOfPartner(partner).get(0), b);
    }

    @Test
    public void getCancelledBookingsOfPartner() {
        assertEquals(Bookings.getCancelledBookingsOfPartner(partner).size(), 0);
    }

    @Test
    public void getBookingsOfGuest() {
        assertEquals(Bookings.getBookingsOfGuest(guest).get(0), b);
    }

    @Test
    public void getCancelledBookingsOfGuest() {
        assertEquals(Bookings.getCancelledBookingsOfGuest(guest).size(), 0);
    }

    @Test
    public void getBookingsOfProperty() {
        assertEquals(Bookings.getBookingsOfProperty(p).get(0), b);
    }

    @Test
    public void getCancelledBookingsOfProperty() {
        assertEquals(Bookings.getCancelledBookingsOfProperty(p).size(), 0);
    }

    @Test
    public void bookingExists() {
        assertEquals(Bookings.getBooking(b.getBookingUUID()), b);
    }

    @Test
    public void cancelBooking() {
        Bookings.cancelBooking(b.getBookingUUID().getUUID());
        // if the cancellation was done correctly
        assertTrue(Bookings.getCancelledBookings().contains(b));
    }

    @Test
    public void getBooking() {
        Bookings.addBooking(b);
        assertEquals(Bookings.getBooking(b.getBookingUUID()), b);
    }

    @Test
    public void getBookings() {
        assertNotNull(Bookings.getBookings());
    }

    @Test
    public void getCancelledBookingsHashMap() {
        assertNotNull(Bookings.getCancelledBookingsHashMap());
    }

    @Test
    public void getCancelledBookings() {
        assertNotNull(Bookings.getCancelledBookings());
    }
}