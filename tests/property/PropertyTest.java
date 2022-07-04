package property;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PropertyTest {
    private Property p;
    private String partner, guest, name, desc, address, type;
    private int price, maxDaysOfBooking, y, m, d, length;
    private Booking b;


    @Before
    public void setUp() throws Exception {
        partner = "Aragorn";
        name = "White Tower";
        desc = "The castle of the white knights";
        address = "Gondor, 18th ave, Middle Earth";
        type = "Castle";
        price = 1600;
        maxDaysOfBooking = 500;
        guest = "Legolas";
        y = 2045;
        m = 6;
        d = 2;
        length = 9;
        p = new Property(partner, name, desc, address, type,
                String.valueOf(price), String.valueOf(maxDaysOfBooking));
        b = new Booking(guest, p, y, m, d, length);
        Bookings.initialize();
    }

    @Test
    public void removeBookingsByUUID() {
        p.book(guest, y, m, d, length, b.getBookingUUID());
        Bookings.addBooking(b);
        p.removeBookingsByUUID(b.getBookingUUID());
        assertFalse(p.isBookedAt(b.getFirstDay(), b.getLength()));
    }

    @Test
    public void book() {
        p.book(guest, y, m, d, length, b.getBookingUUID());
        Bookings.addBooking(b);
        assertTrue(p.isBookedAt(b.getFirstDay(), b.getLength()));
        p.removeBookingsByUUID(b.getBookingUUID());
    }

    @Test
    public void cancelBookingAt() {
        p.book(guest, y, m, d, length, b.getBookingUUID());
        Bookings.addBooking(b);
        p.removeBookingsByUUID(b.getBookingUUID());
        assertFalse(p.isBookedAt(b.getFirstDay(), b.getLength()));
    }

    @Test
    public void isBookedAt() {
        Bookings.addBooking(b);
        p.removeBookingsByUUID(b.getBookingUUID());
        Bookings.cancelBooking(b.getBookingUUID().getUUID());
        assertFalse(p.isBookedAt(b.getFirstDay() - 30, length)); // arbitrary date
    }

    @Test
    public void deleteProperty() {
        String name1 = "Aeandas";
        Property p1 = new Property(partner, name1, desc, address, type,
                String.valueOf(price), String.valueOf(maxDaysOfBooking));
        p1.book(guest, y, m, d, length, b.getBookingUUID());
        p1.deleteProperty();
        // obviously p1 cannot be deleted from memory, so we test if
        // its data (e.g. bookings) have been cleared correctly from memory
        assertFalse(p1.isBookedAt(b.getFirstDay(), b.getLength()));
        assertTrue(p1.getImages().isEmpty());
    }

    @Test
    public void getName() {
        assertEquals(p.getName(), name);
    }

    @Test
    public void getAddress() {
        assertEquals(p.getAddress(), address);
    }

    @Test
    public void setAddress() {
        String newAddress = "new address";
        p.setAddress(newAddress);
        assertEquals(p.getAddress(), newAddress);
        // set to old value
        p.setAddress(address);
    }

    @Test
    public void getDescription() {
        assertEquals(p.getDescription(), desc);
    }

    @Test
    public void setDescription() {
        String newDescription = "new Description";
        p.setDescription(newDescription);
        assertEquals(p.getDescription(), newDescription);
        // set to old value
        p.setDescription(desc);
    }

    @Test
    public void getPropertyType() {
        assertEquals(p.getPropertyType(), type);
    }

    @Test
    public void setPropertyType() {
        String newType = "new Type";
        p.setPropertyType(newType);
        assertEquals(p.getPropertyType(), newType);
        // set to old value
        p.setPropertyType(type);
    }

    @Test
    public void getPartner() {
        assertEquals(p.getPartner(), partner);
    }

    @Test
    public void getMaxDaysOfBooking() {
        assertEquals(p.getMaxDaysOfBooking(), maxDaysOfBooking);
    }

    @Test
    public void setMaxDaysOfBooking() {
        int newMax = 100;
        p.setMaxDaysOfBooking(String.valueOf(newMax));
        assertEquals(p.getMaxDaysOfBooking(), newMax);
        // set to old value
        p.setMaxDaysOfBooking(String.valueOf(maxDaysOfBooking));
    }

    @Test
    public void getPricePerDay() {
        assertEquals(p.getPricePerDay(), String.valueOf(price));
    }

    @Test
    public void setPricePerDay() {
        int newPrice = 100;
        p.setPricePerDay(String.valueOf(newPrice));
        assertEquals(p.getPricePerDay(), String.valueOf(newPrice));
        // set to old value
        p.setPricePerDay(String.valueOf(price));
    }

    @Test
    public void getFacilities() {
        String fName = "facilityName", fValue = "facilityValue";
        p.addFacility(fName, fValue);
        // test if facilities are working properly by adding one
        assertEquals(p.getFacilities().get(fName), fValue);
    }

    @Test
    public void addFacility() {
        String fName = "facilityName", fValue = "facilityValue";
        p.addFacility(fName, fValue);
        assertEquals(p.getFacilities().get(fName), fValue);
    }

    @Test
    public void editFacility() {
        String fName = "facilityName", fOldValue = "facilityValue", fNewValue = "new facility value";
        p.addFacility(fName, fOldValue);
        assertEquals(p.getFacilities().get(fName), fOldValue);
        p.editFacility(fName, fNewValue);
        assertEquals(p.getFacilities().get(fName), fNewValue);
        assertNotEquals(p.getFacilities().get(fName), fOldValue);
    }

    @Test
    public void removeFacility() {
        String fName = "facilityName", fValue = "facilityValue";
        p.addFacility(fName, fValue);
        assertEquals(p.getFacilities().get(fName), fValue);
        p.removeFacility(fName);
        assertFalse(p.facilityExists(fName));
    }

    @Test
    public void facilityExists() {
        String fName = "not existent facility";
        assertFalse(p.facilityExists(fName));
    }

    @Test
    public void getFacilityValue() {
        String fName = "facilityName", fValue = "facilityValue";
        p.addFacility(fName, fValue);
        // test if facilities are working properly by adding one
        assertEquals(p.getFacilityValue(fName), fValue);
    }

    @Test
    public void getImages() {
        p.getImages().clear();
        ImageIcon icon = new ImageIcon("assets" + File.separatorChar + "house1.jpeg");
        p.addImage(icon);
        // test if the image was added correctly
        assertEquals(p.getImages().get(0), icon);
    }

    @Test
    public void setImages() {
        p.getImages().clear();

        ImageIcon icon = new ImageIcon("assets" + File.separatorChar + "house2.jpeg");
        p.addImage(icon);
        ArrayList<ImageIcon> oldImages = new ArrayList<>(p.getImages());

        ArrayList<ImageIcon> images = new ArrayList<>();
        ImageIcon icon2 = new ImageIcon("assets" + File.separatorChar + "house1.jpeg");
        images.add(icon2);
        p.setImages(images);

        assertEquals(p.getImages().get(0), images.get(0));
        assertNotEquals(p.getImages().get(0), oldImages.get(0));

    }

    @Test
    public void addImage() {
        p.getImages().clear();
        ImageIcon icon = new ImageIcon("assets" + File.separatorChar + "house1.jpeg");
        p.addImage(icon);
        // test if the image was added correctly
        assertEquals(p.getImages().get(0), icon);
    }

    @Test
    public void addImages() {
        p.getImages().clear();
        ImageIcon icon = new ImageIcon("assets" + File.separatorChar + "house1.jpeg");
        ImageIcon icon2 = new ImageIcon("assets" + File.separatorChar + "house2.jpeg");
        ArrayList<ImageIcon> images = new ArrayList<>();
        images.add(icon);
        images.add(icon2);
        p.addImages(images);
        // test if the image was added correctly
        assertEquals(p.getImages().get(0), icon);
        assertEquals(p.getImages().get(1), icon2);

    }

    @Test
    public void removeImage() {
        p.getImages().clear();
        ImageIcon icon = new ImageIcon("assets" + File.separatorChar + "house1.jpeg");
        p.addImage(icon);
        assertEquals(p.getImages().get(0), icon);
        p.removeImage(0);
        assertTrue(p.getImages().isEmpty());
    }


    @Test
    public void getImagesTotal() {
        p.getImages().clear();
        assertTrue(p.getImages().isEmpty());
        ImageIcon icon = new ImageIcon("assets" + File.separatorChar + "house1.jpeg");
        p.addImage(icon);
        assertEquals(p.getImagesTotal(), 1);
    }

    @Test
    public void testEquals() {
        Property p2 = new Property(partner, name, desc, address, type, String.valueOf(price), String.valueOf(maxDaysOfBooking));
        assertEquals(p, p2);
    }

    @Test
    public void testHashCode() {
        Property p2 = new Property(partner, name, desc, address, type, String.valueOf(price), String.valueOf(maxDaysOfBooking));
        assertEquals(p.hashCode(), p2.hashCode());
    }
}