package property;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class contains all the properties of laZaro booking
 * It is used for property management and handling.
 *
 * @author Lazaros Gogos
 * @version 2021.12.9
 */
public class Properties implements Serializable {

    @Serial
    private static final long serialVersionUID = 8672292764465875511L;
    /**
     * The properties {@link HashMap}, containing all the properties of laZaro booking
     */
    private static HashMap<String, Property> properties;
    private HashMap<String, Property> serializedProperties;

    /**
     * Initializes the properties hash map with the data taken from the serialized file
     */
    public static void initialize(Properties p) {
        properties = p.serializedProperties;
    }

    /**
     * Initializes the properties hash map as an empty hash map
     */
    public static void initialize() {
        properties = new HashMap<>();
    }

    public static Properties close() {
        Properties p = new Properties();
        p.serializedProperties = properties;
        return p;
    }

/*
    /*
     * The Properties constructor, which creates the properties HashMap and adds an a priori existing property named 'homem'

    public Properties() {
        properties = new HashMap<>();

        Property p1 = new Property("lazar", "homem", "descrup", "add", "home", "180", "10");
        Properties.addProperty(p1);
    }
*/


    // initializer of the properties hashmap
/*    static {
        properties = new HashMap<>();
        Property p1 = new Property("lazar", "homem", "descrup", "add", "home", "180", "10");
        ArrayList<ImageIcon> images = new ArrayList<>();

        images.add(new ImageIcon("/home/lazaros/IdeaProjects/mybooking-lazaro/assets/house1.jpeg"));
        images.add(new ImageIcon("/home/lazaros/IdeaProjects/mybooking-lazaro/assets/house2.jpeg"));
        images.add(new ImageIcon("/home/lazaros/IdeaProjects/mybooking-lazaro/assets/house3.jpeg"));
        p1.addImages(images);
        Property p2 = new Property("lazar", "avida", "descropp", "oia", "motel", "20", "2");
        Property p3 = new Property("makhs", "celestis", "described perfectly", "NY", "room", "620", "15");
        Properties.addProperty(p1);
        Properties.addProperty(p2);
        Properties.addProperty(p3);
    }*/

    /**
     * Add the property to the waiting list for approval.
     *
     * @param property the property that is to be added
     */
    public static void addProperty(Property property) {

        if (properties.containsKey(property.getName())) { // if the name is already taken
            return;
        }
        properties.put(property.getName(), property);
    }

    /**
     * Method to delete a given property, whether it's been approved or not
     *
     * @param name the name of the property that is to be deleted
     */
    public static void deleteProperty(String name) {
        if (properties.containsKey(name)) {
            properties.get(name).deleteProperty();
            properties.remove(name);
        }
    }

    /**
     * Check if given name property exists
     *
     * @param name the name of the property
     * @return the property with such name, null if it does not exist
     */
    public static Property getProperty(String name) {
        return properties.get(name);
    }

    /**
     * A method that returns a string containing all the properties owned by the partner specified in the parameter
     *
     * @param partner the partner's username that's used to search for existing properties
     * @return A {@link String} containing all the properties owned by the specified partner
     */
    public static ArrayList<Property> getPropertiesOwnedBy(String partner) {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        ArrayList<Property> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            Property p = (Property) e.getValue();
            if (p.getPartner().contains(partner.toLowerCase())) {
                arrayList.add(p);
            }
        }
        return arrayList;
    }


    /**
     * A method that returns a string containing all the properties at the given address
     *
     * @param address the address the properties must be located at
     * @return A {@link String} containing all the properties at given address
     */
    public static ArrayList<Property> getPropertiesAtAddress(String address) {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        ArrayList<Property> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            Property p = (Property) e.getValue();
            if (p.getAddress().contains(address)) {
                arrayList.add(p);
            }
        }
        return arrayList;
    }

    /**
     * A method that returns a string containing all the properties with the given facility
     *
     * @param facility the facility the properties must have
     * @return A {@link String} containing all the properties with the given facility
     */
    public static ArrayList<Property> getPropertiesWithFacility(String facility) {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        ArrayList<Property> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            Property p = (Property) e.getValue();
            if (p.getFacilities().get(facility) != null) { // if facility exists
                arrayList.add(p);
            }
        }
        return arrayList;
    }

    /*
     * A method that returns a string containing all the properties with the given facility, owned by specific partner
     *
     * @param facility the facility the properties must have
     * @param partner  the owner's name
     * @return A {@link String} containing all the properties with the given facility, owned by specified partner
     */
/*
    public static String getPropertiesWithFacilityOwnedBy(String facility, String partner) {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            Property p = (Property) e.getValue();
            if (p.getFacilities().get(facility) != null && p.getPartner().contains(partner)) { // if facility exists
                builder.append(e.getKey());
                builder.append("\n");
            }
        }
        return builder.toString();
    }
*/

    /**
     * A method that returns a string containing all the properties of specified type
     *
     * @param type the type the properties must be
     * @return A {@link String} containing all the properties of specified type
     */
    public static ArrayList<Property> getPropertiesOfType(String type) {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        ArrayList<Property> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            Property p = (Property) e.getValue();
            if (p.getPropertyType().contains(type)) { // if facility exists
                arrayList.add(p);
            }
        }
        return arrayList;
    }

    /*
     * A method that returns a string containing all the properties of specified type, owned by specific partner
     *
     * @param type    the type the properties must be
     * @param partner the owner's name
     * @return A {@link String} containing all the properties of specified type, owned by specified partner
     */
/*
    public static String getPropertiesOfTypeOwnedBy(String type, String partner) {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        StringBuilder builder = new StringBuilder();
        while (it.hasNext()) {
            Map.Entry e = it.next();
            Property p = (Property) e.getValue();
            if (p.getPropertyType().equals(type) && p.getPartner().equals(partner)) { // if facility exists
                builder.append(e.getKey());
                builder.append("\n");
            }
        }
        return builder.toString();
    }
*/

    /**
     * A method that returns an array list containing all the properties
     *
     * @return An {@link ArrayList} containing all the properties listed in laZaro booking
     */
    public static ArrayList<Property> getAllProperties() {
        Iterator<Map.Entry<String, Property>> it;
        it = properties.entrySet().iterator();
        ArrayList<Property> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, Property> e = it.next();
            Property p = e.getValue();
//            if (p.getFacilities().getFacilities().get(facility) != null) { // if facility exists
            arrayList.add(p);
//            }
        }
        return arrayList;
    }

    /**
     * A method that checks whether given property name exists in the already listed properties
     *
     * @param name the name of the property that might exist
     * @return whether given name exists or not
     */
    public static boolean propertyExists(String name) {
        return properties.containsKey(name);
    }

    /**
     * Method to get the total numberof properties
     *
     * @return the number of properties that exist
     */
    public static int getPropertiesCount() {
        return properties.size();
    }

    /**
     * Method to get the properties {@link HashMap}
     *
     * @return the properties {@link HashMap}
     */
    public static HashMap<String, Property> getProperties() {
        return properties;
    }
}
