package property;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * This class contains all the bookings of every property.
 * It is essentially a data structure, a list of bookings, and aims to assist the booking process with its tools.
 * It provides utilities such as adding, searching, deleting and editing of bookings.
 *
 * @author Lazaros Gogos
 * @version 2022.1.13
 */
public class Bookings implements Serializable {
    @Serial
    private static final long serialVersionUID = -7995082149563314862L;

    //    public static HashMap<BookingUUID, Booking> bookingsToBeApproved;

    /**
     * HashMap that contains all the bookings, by all users
     */
    private static HashMap<BookingUUID, Booking> cancelledBookings;

    /**
     * HashMap that contains all the cancelled bookings, by all users
     */
    private static HashMap<BookingUUID, Booking> bookings;
    /**
     * A {@link HashMap} of the bookings, used for saving them to a file
     */
    private HashMap<BookingUUID, Booking> serializedBookings;

    /**
     * Initializes bookings and cancelled bookings hash maps
     * with already existing data (from a serialized file for example)
     */
    public static void initialize(Bookings b) {
        bookings = b.serializedBookings;
//        System.out.println(bookings.size());
        cancelledBookings = b.serializedCancelledBookings;
    }

    /**
     * A {@link HashMap} of the cancelled bookings, used for saving them to a file
     */
    private HashMap<BookingUUID, Booking> serializedCancelledBookings;

    /**
     * Initializes bookings and cancelled bookings as empty hash maps
     */
    public static void initialize() {
        bookings = new HashMap<>();
//        System.out.println(bookings.size());
        cancelledBookings = new HashMap<>();
    }

    /**
     * Static method that must be called when it's time to write the updated
     * bookings to a file.
     *
     * @return an instance of this class, where the bookings and the cancelled bookings
     * {@link HashMap}s can be saved.
     */
    public static Bookings close() {
        Bookings b = new Bookings();
        b.serializedBookings = bookings;
        b.serializedCancelledBookings = cancelledBookings;
        return b;
    }

    // initializer

/*
    static {
        bookings = new HashMap<>();
        Property p = new Property("lazar", "avida", "desc", "address", "villa", "20", "5");
        Booking b = new Booking("lazar", p, 2024, 5, 2, 4);
        Booking b2 = new Booking("lazar", p, 2025, 5, 2, 4);
        bookings.put(b.getBookingUUID(), b);
        bookings.put(b2.getBookingUUID(), b2);

        cancelledBookings = new HashMap<>();
    }
*/

    /**
     * Constructor cannot be called
     */
    private Bookings() {
//        booked = new HashSet<>();
    }

/*

    public static HashMap<BookingUUID, Booking> getBookings() {
        return bookings;
    }
*/
/*

    public static Booking removeBooking(Booking b) {
        return bookings.remove(b.getBookingUUID());
    }
*/

    /**
     * Adds booking to the bookings list waiting for approval
     *
     * @param b the booking to be added
     */
    public static void addBooking(Booking b) {
        if (cancelledBookings.containsKey(b.getBookingUUID())) { // re book the property at given date
            Booking booking = cancelledBookings.remove(b.getBookingUUID());
            /* These variables are here for readability puproses. */
//            BookingUUID id = booking.getBookingUUID();
//            int firstDay = booking.getFirstDay();
//            int length = booking.getLength();
//            String acc = booking.getGuest();
//            bookings.get(id).getProperty().book(acc, firstDay, length, id);
            bookings.put(booking.getBookingUUID(), booking);
            return;
        }
        bookings.put(b.getBookingUUID(), b);
    }

    /**
     * Get a string which contains info about the connected partner's bookings
     *
     * @param partner the connected guest's username
     * @return a string containing info about this user's bookings
     */
    public static ArrayList<Booking> getBookingsOfPartner(String partner) {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = bookings.entrySet().iterator();
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            Booking b = e.getValue();
            if (b.getProperty().getPartner().equals(partner))
                arrayList.add(b);

        }
        return arrayList;
    }

    /**
     * Method for getting the cancelled bookings of a partner
     *
     * @param partner the partner whose bookings are cancelled
     * @return an {@link ArrayList<Booking>} with the cancelled bookings of a partner
     */
    public static ArrayList<Booking> getCancelledBookingsOfPartner(String partner) {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = cancelledBookings.entrySet().iterator();
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            Booking b = e.getValue();
            if (b.getProperty().getPartner().equals(partner))
                arrayList.add(b);

        }
        return arrayList;
    }


    /**
     * Get a string which contains info about the connected guest's bookings
     *
     * @param guest the connected guest's username
     * @return a string containing info about this user's bookings
     */
    public static ArrayList<Booking> getBookingsOfGuest(String guest) {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = bookings.entrySet().iterator();
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            Booking b = e.getValue();
            if (b.getGuest().equals(guest))
                arrayList.add(b);
        }
        return arrayList;
    }

    /**
     * Method for getting the cancelled bookings of a guest
     *
     * @param guest the guest whose bookings are cancelled
     * @return an {@link ArrayList<Booking>} with the cancelled bookings of a guest
     */
    public static ArrayList<Booking> getCancelledBookingsOfGuest(String guest) {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = cancelledBookings.entrySet().iterator();
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            Booking b = e.getValue();
            if (b.getGuest().equals(guest))
                arrayList.add(b);
        }
        return arrayList;
    }

    /**
     * A method that returns a list of the bookings of a certain property
     *
     * @param property the property that is booked
     * @return an {@link ArrayList} with all the bookings of this property
     */
    public static ArrayList<Booking> getBookingsOfProperty(Property property) {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = bookings.entrySet().iterator();
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            Booking b = e.getValue();
            if (b.getProperty().equals(property))
                arrayList.add(b);
        }
        return arrayList;
    }

    /**
     * A method that returns a list of the cancelled bookings of a certain property
     *
     * @param property the property that was formerly booked
     * @return an {@link ArrayList} with all the cancelled bookings of this property
     */
    public static ArrayList<Booking> getCancelledBookingsOfProperty(Property property) {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = cancelledBookings.entrySet().iterator();
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            Booking b = e.getValue();
            if (b.getProperty().equals(property))
                arrayList.add(b);
        }
        return arrayList;
    }
    /*
     * Returns a <b>copy</b> of all bookings that need to be approved of
     *
     * @return a linked list

    public static HashMap<BookingUUID, Booking> getBookingsWaitingForApproval() {
        return bookingsToBeApproved;
    }
*/
    /*
     * Approve all bookings !

    public static void approveAllBookings() {
        Iterator<Map.Entry<BookingUUID, Booking>> it;
        it = bookingsToBeApproved.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<BookingUUID, Booking> e = it.next();
            approveBooking(e.getKey());
        }
    }
*/

    /**
     * Method to determine whether a booking exists, based on UUID
     *
     * @param id the {@link BookingUUID} to look for
     * @return whether the booking exists or not
     */
    public static boolean bookingExists(BookingUUID id) {
        return bookings.containsKey(id);
    }

/*
    public static boolean bookingUUIDExistsAndIsNotApproved(BookingUUID id) {
        return bookingsToBeApproved.containsKey(id);
    }
*/

    /*
     * Approve of booking
     *
     * @param id The booking uuid for approval

    public static Booking approveBooking(BookingUUID id) {
        if (!bookingsToBeApproved.containsKey(id)) // if it doesn't exist
            return null;
        // else, remove it from the waiting list
        Booking b = bookingsToBeApproved.remove(id);

        // finally, add it to the official bookings
        if (b.getProperty().book(b.getBookingUUID(), b.getFirstDay(), b.getLength())) {
            bookings.put(b.getBookingUUID(), b);
            return b;
        }
        return null;
    }
*/

    /*
     * Cancel the booking, whether it's been approved or not
     *
     * @param b The booking for removal

    private static void cancelBooking(Booking b) {
        if (bookings.containsValue(b)) {
            bookings.get(b.getBookingUUID()).getProperty().cancelBookingAt(b.getBookingUUID(), b.getFirstDay(), b.getLength());
            bookings.remove(b.getBookingUUID());
        }
    }
*/

    /**
     * The method that gets called when a booking is to be cancelled. It moves the booking from the bookings hashmap to
     * the cancelledBookings {@link HashMap}
     *
     * @param id the booking uuid, give as an integer
     */
    public static void cancelBooking(int id) {
        BookingUUID uuid = new BookingUUID(id);
        int firstDay = bookings.get(uuid).getFirstDay();
        int length = bookings.get(uuid).getLength();
        bookings.get(uuid).getProperty().cancelBookingAt(uuid, firstDay, length);
        Booking cancelled = bookings.remove(uuid);
        cancelledBookings.put(uuid, cancelled);
    }

    /**
     * Method that returns a placed booking, given the {@link BookingUUID}
     *
     * @param uuid the {@link BookingUUID} that is given upon placing a booking
     * @return a {@link Booking} containing info about the booking
     */
    public static Booking getBooking(BookingUUID uuid) throws NullPointerException {
        if (!bookingExists(uuid))
            throw new NullPointerException("This booking does not exist!");
        return bookings.get(uuid);
    }

    /**
     * Methods that returns the {@link HashMap} which contains all the bookings.
     *
     * @return a {@link HashMap} which contains all the bookings made in laZaro booking
     */
    public static HashMap<BookingUUID, Booking> getBookings() {
        return bookings;
    }

    /**
     * A method for getting the cancelled bookings hashmap
     *
     * @return the cancelled bookings {@link HashMap<BookingUUID, Booking>}
     */
    public static HashMap<BookingUUID, Booking> getCancelledBookingsHashMap() {
        return cancelledBookings;
    }

    /**
     * A method to get the cancelled bookings, instantiated as a {@link Collection<Booking>}
     *
     * @return a {@link Collection<Booking>} of all bookings
     */
    public static Collection<Booking> getCancelledBookings() {
        return cancelledBookings.values();
    }
/*    public static void clearBookings() {
        bookingsToBeApproved.clear();
        bookings.clear();
//        booked.clear();
    }*/
}
