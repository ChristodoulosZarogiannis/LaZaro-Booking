package property;

import date.Date;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * A Property is owned by an Partner. It can be booked, listed as available or not,
 * edited by the Partner, edited or deleted by an Admin. Before adding it needs to be
 * approved of an Admin.
 *
 * @author Lazaros Gogos
 * @version 2021.12.08
 */
public class Property implements Serializable {
    @Serial
    private static final long serialVersionUID = -8388481999928493557L;
    /*
     * Property Universal Unique Identifier
     */
//    private final PropertyUUID propertyUUID;
    // mandatory variables
//    private final String name;
//    private final String partner;

    //    private float pricePerDay;
    /**
     * The facilities a property has. A facility can be a balcony, a mini bar, a parking spot, WiFi signal, etc
     */
    private HashMap<String, String> facilities;
    //    private PropertyFacilities facilities;
    //    private PropertyRating rating;
//    private PropertyDetails details;
    /**
     * The details are the property's name, address, description, partner's name, price per day, maximum days of booking or its type
     */
    private HashMap<String, String> details;
    /**
     * This HashSet contains all the days this property is booked, and it's used to determine whether it can be booked or not.
     */
    private HashSet<Integer> booked;

    /**
     * A LinkedList containing all the bookings (their UUIDs essentially) that have been placed at this property
     */
    private LinkedList<BookingUUID> allBookings;

    /**
     * Method to remove a booking from this property, given the {@link BookingUUID}
     *
     * @param id the {@link BookingUUID} to look for
     */
    public void removeBookingsByUUID(BookingUUID id) {
        int firstDay = Bookings.getBooking(id).getFirstDay();
        int length = Bookings.getBooking(id).getLength();
        cancelBookingAt(id, firstDay, length);
    }

    /**
     * the images {@link ArrayList} that holds all images of this specific property
     */
    private ArrayList<ImageIcon> images;

    /**
     * The Property constructor, where a new property is created
     *
     * @param partner          the owner's name
     * @param name             the name of this property
     * @param description      this property's description
     * @param address          the address of this propery
     * @param propertyType     the type of this property, e.g. Villa, Home or Apartment
     * @param pricePerDay      the price per day of this property
     * @param maxDaysOfBooking the maximum number of days this property can be booked for
     */
    public Property(String partner, String name, String description, String address, String propertyType, String pricePerDay, String maxDaysOfBooking) {
        images = new ArrayList<>();
        booked = new HashSet<>();
        details = new HashMap<>();
        this.facilities = new HashMap<>();
        allBookings = new LinkedList<>();

//        facilities = new PropertyFacilities(this);
        this.details.put("address", address);
        this.details.put("description", description);
        this.details.put("name", name);
        this.details.put("partner", partner);
        this.details.put("pricePerDay", pricePerDay);
        this.details.put("type", propertyType);
        this.details.put("maxDaysOfBooking", maxDaysOfBooking);
//        this.imagesTotal = 0;
    }


    /**
     * Book current property from any given date (> 12.12.2020) and for <b>length</b> days
     *
     * @param acc    the connected user's name
     * @param y      The year of the booking
     * @param m      The month of the booking
     * @param d      The day of the booking
     * @param length The days the booking will last
     * @param uuid   the uuid of the booking
     * @return whether the property was booked or not
     */
    public boolean book(String acc, int y, int m, int d, int length, BookingUUID uuid) {
        return book(acc, Date.getDay(y, m, d), length, uuid);
    }


    /**
     * Book current property from day <b>d</b> and for <b>length</b> days.
     * This method finalizes the booking process.
     *
     * @param guest  the guest's name
     * @param d      The first day of the booking
     * @param length The days the booking will last
     * @param uuid   the uuid of the booking
     * @return true if the booking was successful, false if the property was already booked in the set of given dates
     */
    boolean book(String guest, int d, int length, BookingUUID uuid) {
        if (length < 0 || d < 0 || length > getMaxDaysOfBooking())
            return false;
        if (isBookedAt(d, length)) // TODO Use throw exception ?
            // if the property is booked on any day inside the wanted days
            return false;


        // add dates to the booked dates
        for (int i = d; i < d + length; i++) {
            booked.add(i);
        }
        allBookings.add(uuid);
        return true;
    }

    /**
     * A method that cancels a booking from the property, given its uuid
     *
     * @param uuid     the booking's uuid
     * @param firstDay the first day this booking starts
     * @param length   the duration of this booking
     */
    void cancelBookingAt(BookingUUID uuid, int firstDay, int length) {
        for (int i = firstDay; i < firstDay + length; i++)
            booked.remove(i);
        allBookings.remove(uuid);
    }

    /**
     * Method that returns whether this property is booked at given space in time or not
     *
     * @param d      the first day of a booking
     * @param length how long the booking lasts
     * @return whether the property is booked or not
     */
    public boolean isBookedAt(int d, int length) {
        for (int j = d; j < d + length; j++)
            // if-f any of the given dates is already booked to another person (overlapping, that is), exit
            if (booked.contains(j))
                return true;
        return false;
    }

    /**
     * A method that frees the property from memory
     */
    void deleteProperty() {
        for (BookingUUID id : allBookings) {
            Bookings.getBookings().remove(id);
        }
        allBookings.clear();
        booked.clear();
        facilities.clear();
        details.clear();
        images.clear();
    }

    /**
     * Method that returns the property's name
     *
     * @return a {@link String} containing the property's name
     */
    public String getName() {
        return details.get("name");
    }

    /**
     * Method that returns the property's address
     *
     * @return a {@link String} containing the property's address
     */
    public String getAddress() {
        return details.get("address");
    }

    /**
     * Method that sets the property's address
     *
     * @param address a {@link String} containing the property's address
     */
    public void setAddress(String address) {
        details.put("address", address);
    }

    /**
     * Method that returns the property's description
     *
     * @return a {@link String} containing the property's description
     */
    public String getDescription() {
        return details.get("description");
    }

    /**
     * Method that sets the property's description
     *
     * @param description a {@link String} containing the property's description
     */
    public void setDescription(String description) {
        details.put("description", description);
    }

    /**
     * Method that returns the property's type
     *
     * @return a {@link String} containing the property's type
     */
    public String getPropertyType() {
        return details.get("type");
    }

    /**
     * Method that sets the property's property type
     *
     * @param propertyType a {@link String} containing the property's property type
     */
    public void setPropertyType(String propertyType) {
        details.put("type", propertyType);
    }

    /**
     * Method that returns the property's owner
     *
     * @return a {@link String} containing the property's owner
     */
    public String getPartner() {
        return details.get("partner");
    }

    /**
     * Method that returns the property's maximum number of days it can be booked
     *
     * @return an {@link Integer} containing the property's maximum number of days it can be booked
     */
    public int getMaxDaysOfBooking() {
        return Integer.parseInt(details.get("maxDaysOfBooking"));
    }

    /**
     * Method that sets the property's maximum number of days it can be booked
     *
     * @param maxDaysOfBooking the maximum number of days this property can be booked
     */
    public void setMaxDaysOfBooking(String maxDaysOfBooking) {
        details.put("maxDaysOfBooking", maxDaysOfBooking);
    }
/*

    public HashMap<String, String> getDetails() {
        return details;
    }
*/

    /*
     * Method for getting all the bookings of a property.
     * Does not actually print anything, instead it returns a string
     * that can be printed, or used in any other way.
     *
     * @return a string with all the bookings and their details.
     */
/*
    public String printPropertyDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("About this property: \n");
        builder.append(getDescription());
        builder.append(".\nIts price per day is: ");
        builder.append(getPricePerDay());
        builder.append(" euros.\nThis property is located at: ");
        builder.append(getAddress());
        builder.append(".\nThe owner is: ");
        builder.append(getPartner());
        builder.append(".\nThis room can be booked for a maximum of: ");
        builder.append(getMaxDaysOfBooking());
        builder.append(" days.\n");
//        builder.append(". Its rating is:");
//        builder.append(rating.getRating());
        builder.append(printPropertyFacilities());
        return builder.toString();
    }
*/

    /**
     * A getter of the price per day of the property
     *
     * @return a string containing the price per day
     */
    public String getPricePerDay() {
        return details.get("pricePerDay");
    }

    /**
     * A setter of the price per day of the property
     *
     * @param pricePerDay the price per day
     */
    public void setPricePerDay(String pricePerDay) {
        details.put("pricePerDay", pricePerDay);
    }

//    public LinkedList<BookingUUID> getAllBookings() {
//        return allBookings;
//    }

    /*
     * Method for getting all the bookings of a property.
     * Does not actually print anything, instead it returns a string
     * that can be printed, or used in any other way.
     *
     * @return a string with all the bookings and their details.
     */
/*
    public String printAllBookings() {
        StringBuilder builder = new StringBuilder();
        Iterator<BookingUUID> it;
        it = allBookings.iterator();
        builder.append("Its bookings are: \n");
        while (it.hasNext()) {
            BookingUUID uuid = it.next();
            builder.append(Bookings.getBooking(uuid).printBookingDetails());
            builder.append("\n");
        }
        builder.append("\n");
        return builder.toString();
    }
*/

    /**
     * A method that returns a {@link HashMap} containing all facilities
     *
     * @return a {@link HashMap} which contains all facilities of the property
     */
    public HashMap<String, String> getFacilities() {
        return facilities;
    }

    /**
     * A method for adding a facility to the property
     *
     * @param facility the facility's name
     * @param value    the facility's value
     */
    public void addFacility(String facility, String value) {
        facilities.put(facility, value);
    }

    /**
     * A method for editing a facility of the property
     *
     * @param facility the facility's name
     * @param value    the facility's value
     */
    public void editFacility(String facility, String value) {
        facilities.put(facility, value);
    }

    /**
     * A method for removing a facility from the property
     *
     * @param facility the facility's name
     */
    public void removeFacility(String facility) {
        facilities.remove(facility);
    }

    /**
     * A method for checking whether a facility exists in the property
     *
     * @param facility the facility's name
     * @return whether given facility exists or not
     */
    public boolean facilityExists(String facility) {
        return facilities.containsKey(facility);
    }

    /**
     * Method to get the value of a facility, based on its name
     *
     * @param facilityName the name of the facility we're searching its value for
     * @return the {@link String} value of this facility
     */
    public String getFacilityValue(String facilityName) {
        return facilities.get(facilityName);
    }

    /*
     * Method for getting all the available facilities of a property
     * Does not actually print anything, instead it returns a string
     * that can be printed, or used in any other way.
     *
     * @return a string will all facilities and their status.
     */
/*
    private String printPropertyFacilities() {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> it;
        it = facilities.entrySet().iterator();
        if (!facilities.isEmpty()) {
            builder.append("Its facilities are:\n");
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                builder.append(e.getKey());
                builder.append(": ");
                builder.append(e.getValue());
                builder.append("\n");
            }
        }
        return builder.toString();
    }
*/

    /**
     * Method to get all the images of this property
     *
     * @return an {@link ArrayList} with all the images, stored as {@link ImageIcon}s
     */
    public ArrayList<ImageIcon> getImages() {
        return images;
    }

    /**
     * A method that deletes all previous images and stores the new ones given as a parameter
     *
     * @param a the new images of this property, given as an {@link ArrayList}
     */
    public void setImages(ArrayList<ImageIcon> a) {
        images.clear();
        images.addAll(a);
    }

    /**
     * Method to add an image to the images of this property
     *
     * @param image an {@link ImageIcon} to add to the images of this property
     */
    public void addImage(ImageIcon image) {
        images.add(image);
    }

    /**
     * Method to add multiple images to this property
     *
     * @param a the images to be added to the already existing images of this property
     */
    public void addImages(ArrayList<ImageIcon> a) {
        images.addAll(a);
    }

    /**
     * Method for removing an image from this property
     *
     * @param image the image to be removed
     */
    public void removeImage(ImageIcon image) {
        if (images.isEmpty())
            return;
        images.remove(image);
    }

    /**
     * Method for removing an image from this property, given its index
     *
     * @param image the index of this image in the images {@link ArrayList} of this property
     */
    public void removeImage(int image) {
        if (images.isEmpty())
            return;
        images.remove(image);
    }

    /**
     * Method for getting the amount of images this property has
     *
     * @return the number of this property's images
     */
    public int getImagesTotal() {
        return images.size();
    }

    /**
     * Method to test if this property is equal to another. Two properties are the same, if their name is the same, and only that
     *
     * @param o another property
     * @return whether two properties' names are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
//        return propertyUUID.getUUID() == property.propertyUUID.getUUID();
        return getName().equals(property.getName());
    }

    /**
     * Method for hashing the name of this property
     *
     * @return the hash code of this property's name
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }


}
