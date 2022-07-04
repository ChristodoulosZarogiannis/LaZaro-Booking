package property.gui.partner;

import property.Properties;
import property.Property;
import property.gui.ImageDisplayPanelGUI;
import property.gui.JNumericTextField;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates a new dialog for a user, where they can edit a property
 *
 * @author Lazaros Gogos
 * @version 2021.02.2
 * @see <a href=https://stackoverflow.com/questions/22506331/simple-dropdown-menu-in-java> for more info on dropdown menus</a>
 */
public class EditPropertyDialogGUI extends JDialog {

    //    private JTextField tf;
    /**
     * A numeric text field
     */
    private final JNumericTextField ntf;
    /**
     * The connected user's name
     */
    private final String connectedUser;
    /**
     * The selected property
     */
    private final Property property;

    /**
     * A custom panel for displaying images
     */
    private final ImageDisplayPanelGUI imageDisplayPanel;
    /**
     * The options dropdown
     */
    private final JComboBox<String> dropdown;

    /**
     * Creates a dialog where a partner can edit the selected
     * property
     *
     * @param connectedUser
     * @param property
     */
    public EditPropertyDialogGUI(String connectedUser, Property property) {
        // standard code to set up the dialog
        setModal(true);
        this.connectedUser = connectedUser;
        this.property = property;

        setTitle("Edit Property Dialog");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1220, 220);
        setLayout(new GridLayout(1, 2, 10, 10));
        // declare components
        JPanel infoPanel;
        JButton bDone, bUpdate, bAddEditRemoveFacilites;
        JLabel lChoice = new JLabel("Choice:");

        // init components
        imageDisplayPanel = new ImageDisplayPanelGUI(property.getImages(), true);
        infoPanel = new JPanel(new GridLayout(4, 2));
        bDone = new JButton("Done!");
//        bCancel = new JButton("Cancel");
        bUpdate = new JButton("Update selected");
        bAddEditRemoveFacilites = new JButton("Add/Edit/Remove facilities");
//        tf = new JTextField();
        ntf = new JNumericTextField();
//        ntf.setEditable(false);
        ntf.setAcceptAll(true);
        String[] CHOICES = {"Address", "Description", "Property Type", "Price Per Day", "Max Days Of Booking"};
        dropdown = new JComboBox(CHOICES);
        dropdown.setEditable(false);
        dropdown.addActionListener(l -> updateAcceptedCharactersOfTextFields());
        // add action listeners
        bUpdate.addActionListener(l -> updateValue());
//        bCancel.addActionListener(l -> cancel());
        bDone.addActionListener(l -> done());
        bAddEditRemoveFacilites.addActionListener(l -> editFacility(property.getName()));
        infoPanel.add(lChoice);
        infoPanel.add(dropdown);
//        infoPanel.add(tf);
        infoPanel.add(ntf);
        infoPanel.add(bUpdate);
        infoPanel.add(bDone);
        infoPanel.add(bAddEditRemoveFacilites);
//        infoPanel.add(bCancel);
        setLocationRelativeTo(null);
        add(infoPanel);
        add(imageDisplayPanel);
        // finalize window
//        pack();
        setResizable(false);
//        setResizable(false);
        setVisible(true);
    }

    /**
     * Method that allows a textfield to accept all characters, or ONLY numbers
     */
    private void updateAcceptedCharactersOfTextFields() {
        if (dropdown.getSelectedIndex() < 3) {
//            tf.setEditable(true);
            ntf.setAcceptAll(true);
            ntf.setText(null);
        } else {
//            tf.setEditable(false);
            ntf.setAcceptAll(false);
            ntf.setText(null);
//            tf.setText(null);
        }
    }

    /**
     * Method that updates the value of the selected info (e.g. the address of the property)
     */
    private void updateValue() {
        int selection = dropdown.getSelectedIndex();
        String propertyName = property.getName();
        String input = ntf.getText().toUpperCase();
        switch (selection) {
            case 0 -> Properties.getProperty(propertyName).setAddress(input);
            case 1 -> Properties.getProperty(propertyName).setDescription(input);
            case 2 -> Properties.getProperty(propertyName).setPropertyType(input);
            case 3 -> Properties.getProperty(propertyName).setPricePerDay(input);
            case 4 -> Properties.getProperty(propertyName).setMaxDaysOfBooking(input);
        }
    }

    /**
     * Method that spawns a new dialog for editing facilities
     *
     * @param propertyName
     */
    private void editFacility(String propertyName) {
        new EditFacilityDialogGUI(this, propertyName);
    }

    /**
     * Method that closes the window
     */
    private void done() {
        this.dispose();
    }

    /**
     * Method that returns the currently in-use {@link ImageDisplayPanelGUI}
     *
     * @return the currently in-use {@link ImageDisplayPanelGUI}
     */
    public ImageDisplayPanelGUI getImageDisplayPanel() {
        return imageDisplayPanel;
    }

}
