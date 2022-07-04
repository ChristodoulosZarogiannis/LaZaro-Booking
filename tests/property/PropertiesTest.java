package property;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesTest {
    private Property p;
    private String partner, name, desc, address, propertyType;
    private int price, maxDays;
    private String fName, fValue;

    @Before
    public void setUp() throws Exception {
        Properties.initialize();
        partner = "partner";
        name = "name";
        desc = "desc";
        address = "address";
        propertyType = "type";
        price = 120;
        maxDays = 10;
        fName = "balcony"; // facility name
        fValue = "exists"; // facility value
        p = new Property(partner, name, desc, address, propertyType, String.valueOf(price), String.valueOf(maxDays));
        p.addFacility(fName, fValue);
        Properties.addProperty(p);

    }

    @Test
    public void initialize() {
        Properties.initialize();
        assertNotNull(Properties.getProperties());
    }


    @Test
    public void addProperty() {
        Properties.addProperty(p);
        assertEquals(Properties.getProperty(name), p);
    }

    @Test
    public void deleteProperty() {
        String name2 = "different name";
        Property p2 = new Property(partner, name2, desc, address, propertyType, String.valueOf(price), String.valueOf(maxDays));
        Properties.addProperty(p2);
        assertEquals(Properties.getProperty(name2), p2);
        Properties.deleteProperty(name2);
        assertNull(Properties.getProperty(name2));
    }

    @Test
    public void getProperty() {
        assertEquals(Properties.getProperty(name), p);
    }

    @Test
    public void getPropertiesOwnedBy() {
        assertEquals(Properties.getPropertiesOwnedBy(partner).get(0), p);
    }

    @Test
    public void getPropertiesAtAddress() {
        assertEquals(Properties.getPropertiesAtAddress(address).get(0), p);
    }

    @Test
    public void getPropertiesWithFacility() {
        assertEquals(Properties.getPropertiesWithFacility(fName).get(0), p);
    }
/*

    @Test
    public void getPropertiesWithFacilityOwnedBy() {
//        String fName = "balcony"; // facility name
        assertEquals(Properties.getPropertiesWithFacilityOwnedBy(fName, partner).get(0), p);
    }
*/

    @Test
    public void getPropertiesOfType() {
        assertEquals(Properties.getPropertiesOfType(propertyType).get(0), p);
        assertTrue(Properties.getPropertiesOfType("non existent type").isEmpty());
    }
/*

    @Test
    public void getPropertiesOfTypeOwnedBy() {
    }
*/

    @Test
    public void getAllProperties() {
        assertEquals(Properties.getAllProperties().get(0), p);
    }

    @Test
    public void propertyExists() {
        assertTrue(Properties.propertyExists(name));
    }

    @Test
    public void getPropertiesCount() {
        assertEquals(Properties.getPropertiesCount(), 1);
    }

    @Test
    public void getProperties() {
        assertEquals(Properties.getProperties().get(name), p);
    }
}