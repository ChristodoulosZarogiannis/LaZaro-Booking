package property.gui;

import property.Property;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that contains information of a property,
 */
public class ViewPropertyPanelGUI extends JPanel {

    /**
     * Creates a panel where all information of a property are shown.
     * Info like its name, its address, its availabe facilities, its photographs etc
     *
     * @param connectedUser   the connected user's name
     * @param currentProperty the property of which info are to be shown
     * @param editablePhotos  whether the panel will allow for editable photos of this property
     */
    public ViewPropertyPanelGUI(String connectedUser, Property currentProperty, boolean editablePhotos) {
        // set up panel
        setLayout(new GridLayout(2, 2, 10, 10));
        // declare and initialize variables
        JPanel infoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        // temporary layout
        JPanel gridbagPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel lName, llName,
                lDescription,
                lAddress,
                lPropertyType, llPropertyType,
                lPricePerDay, llPricePerDay,
                lMaxDaysOfBooking, llMaxDaysOfBooking,
                lPartner, llPartner,
                lFacilities;
        JScrollableTextArea taDescription, taAddress;

        lName = new JLabel("Name");
        llName = new JLabel(currentProperty.getName());
        lDescription = new JLabel("Description");
        taDescription = new JScrollableTextArea(currentProperty.getDescription());
        lAddress = new JLabel("Address");
        taAddress = new JScrollableTextArea(currentProperty.getAddress());
        lPropertyType = new JLabel("Property Type");
        llPropertyType = new JLabel(currentProperty.getPropertyType());
        lPricePerDay = new JLabel("Price per day");
        llPricePerDay = new JLabel(currentProperty.getPricePerDay());
        lMaxDaysOfBooking = new JLabel("Maximum days it can be booked");
        llMaxDaysOfBooking = new JLabel(String.valueOf(currentProperty.getMaxDaysOfBooking()));
        lPartner = new JLabel("Owner:");
        llPartner = new JLabel(currentProperty.getPartner());
        lFacilities = new JLabel("Facilities:");
        // set up custom text areas
        taDescription.getTextArea().setLineWrap(true);
        taAddress.getTextArea().setLineWrap(true);
        taDescription.getTextArea().setEditable(false);
        taAddress.getTextArea().setEditable(false);

        //@formatter:off
        infoPanel.add(lName); infoPanel.add(llName);
        infoPanel.add(lDescription); infoPanel.add(taDescription);
        infoPanel.add(lAddress); infoPanel.add(taAddress);
        infoPanel.add(lPropertyType); infoPanel.add(llPropertyType);
        infoPanel.add(lPricePerDay); infoPanel.add(llPricePerDay);
        infoPanel.add(lMaxDaysOfBooking); infoPanel.add(llMaxDaysOfBooking);
        infoPanel.add(lPartner); infoPanel.add(llPartner);
/*

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbagPanel.add(lName, c);

        c.gridx = 0;
        c.gridy = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbagPanel.add(llName);


        c.gridx = 10;
        c. gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbagPanel.add(lDescription, c);

        c.gridx = 10;
        c.gridy = 10;
        c.ipady = 40;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridbagPanel.add(taDescription);
*/
        // add everything to the panel
        add(infoPanel); add(new ImageDisplayPanelGUI(currentProperty.getImages(), editablePhotos));
        add(lFacilities); add(new FacilitiesTablePanelGUI(connectedUser, currentProperty));
        //@formatter:on
    }
}
