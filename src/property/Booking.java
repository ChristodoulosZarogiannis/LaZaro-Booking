package property;

import date.Date;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents a booking. A booking can be placed by a {@link accounts.Guest}
 * at a specific {@link Property}.
 *
 * @author Lazaros Gogos
 * @version 2021.12.9
 */
public class Booking implements Serializable {

    @Serial
    private static final long serialVersionUID = 6495155203702802479L;
    /**
     * Booking Universal Unique Identifier
     */
    private final BookingUUID bookingUUID;
//    private int firstDay, length;
//    private int year, month, day;
    /**
     * A hashmap that contains all the fields of a booking
     */
    private HashMap<String, Integer> fields;

    /**
     * The guest's name
     */
    private String guest;

    /**
     * The property that was booked in this booking object
     */
    private Property property;

    /**
     * The constructor of a booking
     *
     * @param guest    the guest's name
     * @param property the {@link Property} that is booked
     * @param y        the year of this booking
     * @param m        the month of this booking
     * @param d        the day this booking starts
     * @param length   the duration of this booking, counted in days
     */
    public Booking(String guest, Property property, int y, int m, int d, int length) {
        this(guest, property, Date.getDay(y, m, d), length);
//        year = y;
//        month = m;
//        day = d;
        fields.put("year", y);
        fields.put("month", m);
        fields.put("day", d);
    }

    /**
     * A private constructor, that should not be used directly.
     *
     * @param guest    the guest's name
     * @param property the property that this booking will be placed at
     * @param firstDay the first day of the booking, counted linearly via the {@link Date} class
     * @param length   the duration of the booking, counted in days
     */
    private Booking(String guest, Property property, int firstDay, int length) {
        this.guest = guest;
        this.fields = new HashMap<>();
//        this.firstDay = firstDay;
        this.property = property;
//        this.length = length;
        bookingUUID = new BookingUUID(guest, firstDay, length); // XXX Super important line, must NOT be ommitted !!
        fields.put("firstDay", firstDay);
        fields.put("length", length);
        fields.put("price", length * Integer.parseInt(property.getPricePerDay()));
    }

    /**
     * Get the day that this booking starts, which represents a linear day count, where day 1 is considered 1/1/2021.
     *
     * @return an integer of the day this booking starts.
     */
    int getFirstDay() {
        return fields.get("firstDay");
    }



        /**
         * Get the duration of this booking, counted in days.
         *
         * @return an integer that represents the duration of this booking, counted in days
         */
    public int getLength() {
        return fields.get("length");
    }

    /**
     * Get the guest that placed this booking
     *
     * @return a {@link String} of the guest's username
     */
    public String getGuest() {
        return guest;
    }

    /**
     * Get the uuid of this booking
     *
     * @return a {@link BookingUUID} of this booking's uuid
     */
    public BookingUUID getBookingUUID() {
        return bookingUUID;
    }

    /**
     * Get the year of this boooking.
     *
     * @return an integer greater than 2021
     */
    public int getYear() {
        return fields.get("year");
    }

    /**
     * Get the month of this boooking.
     *
     * @return an integer in [1,12]
     */
    public int getMonth() {
        return fields.get("month");
    }

    /**
     * Get the day this boooking starts.
     *
     * @return an integer in [1,31]
     */
    public int getDay() {
        return fields.get("day");
    }

    public int getPrice() {
        return fields.get("price");
    }

    /*
     * Method for getting the details of this booking.
     * Does not actually print anything, instead it returns a string
     * that can be printed, or used in any other way.
     *
     * @return a string will the booking and its detials.
     */
/*
    String printBookingDetails() {
        StringBuilder builder = new StringBuilder();

        builder.append("This booking was booked by: ");
        builder.append(guest);
        builder.append(".\n The property's name is: ");
        builder.append(property.getName());
        builder.append(".\n Its id is: ");
        builder.append(bookingUUID.getUUID());
        builder.append(".\n It starts at ");
        builder.append(getDay());
        builder.append("/");
        builder.append(getMonth());
        builder.append("/");
        builder.append(getYear());
        builder.append(" and lasts ");
        builder.append(getLength());
        builder.append(" days. \n");

        return builder.toString();
    }
*/

    /**
     * For two bookings to be equal, they must have the same {@link BookingUUID} id.
     *
     * @return whether two bookings are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
//        return bookingUUID.getUUID() == booking.getBookingUUID().getUUID();
        return bookingUUID.equals(booking.bookingUUID);
//        return firstDay == booking.firstDay && length == booking.length && guest.equals(booking.guest);
    }

    /**
     * Get the hashcode of given booking, which essentially is the {@link BookingUUID}.
     *
     * @return an integer representing the current booking uuid
     */
    @Override
    public int hashCode() {
        return bookingUUID.getUUID();
    }

    /**
     * Get the property that was booked in this booking
     *
     * @return The {@link Property} that was booked in this booking
     */
    public Property getProperty() {
        return property;
    }
}
