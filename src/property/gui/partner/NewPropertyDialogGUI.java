package property.gui.partner;

import property.Properties;
import property.Property;
import property.gui.ImageDisplayPanelGUI;
import property.gui.JNumericTextField;
import property.gui.JScrollableTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * This class creates a new dialog for a user, where they can create a property
 *
 * @author Lazaros Gogos
 * @version 2021.12.30
 */
public class NewPropertyDialogGUI extends JDialog {
    /**
     * Textfields for name and property type
     */
    private JTextField tfName, tfPropertyType;
    /**
     * Text areas for info like the description or the address of this property
     */
    private JScrollableTextArea taDescription, tfAddress;
    /**
     * Numeric text fields, for info like the price per day or the maximum days this
     * property can be booked
     */
    private JNumericTextField ntfPricePerDay, ntfMaxDaysOfBooking;
    /**
     * Buttons to add or cancel the new addition
     */
    private JButton bAdd, bCancel;
    /**
     * The connected user's name
     */
    private String connectedUser;
    //    private int imageIndex;
    /**
     * An image display panel where images can be added, viewed or deleted
     */
    private ImageDisplayPanelGUI imageDisplayPanel;

    /**
     * Creates a new dialog where a partner can add a new property
     *
     * @param connectedUser
     */
    public NewPropertyDialogGUI(String connectedUser) {
        // standard code to set up the dialog
        this.connectedUser = connectedUser;
        setModal(true);
        JLabel lName, lDescription, lAddress, lPropertyType, lPricePerDay, lMaxDaysOfBooking;
        setTitle("New Property Dialog");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 700);
//        setLayout(new GridLayout(7, 2, 5, 10));
        setLayout(new FlowLayout());
        JPanel infoPanel = new JPanel(new GridLayout(7, 2, 5, 10));
        // init image panel
//        JPanel imagePanel = new JPanel(new FlowLayout());
        imageDisplayPanel = new ImageDisplayPanelGUI();
//        imagePanel.add(imageDisplayPanel);
        // init labels
        lName = new JLabel("Name:");
        lDescription = new JLabel("Description:");
        lAddress = new JLabel("Address:");
        lPropertyType = new JLabel("Property Type:");
        lPricePerDay = new JLabel("Price per day:");
        lMaxDaysOfBooking = new JLabel("Maximum days it can be booked:");

        // init text fields
        tfName = new JTextField();
        taDescription = new JScrollableTextArea();
        taDescription.getTextArea().setLineWrap(true);
        tfAddress = new JScrollableTextArea();
        tfAddress.getTextArea().setLineWrap(true);
        tfPropertyType = new JTextField();
        ntfPricePerDay = new JNumericTextField();
        ntfMaxDaysOfBooking = new JNumericTextField();

        // init buttons
        bAdd = new JButton("Add property!");

//        bAdd.setEnabled(false);
        tfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
/*                if (tfName.getText().trim().equals(""))
                    bAdd.setEnabled(false);
                else */
                if (Properties.propertyExists(tfName.getText())) {
//                    bAdd.setEnabled(false);
                    tfName.setText(null);
                    showNameNotAvailable();
                } /*else
                    bAdd.setEnabled(true);*/

            }
        });
        bCancel = new JButton("Cancel");
        // add button functionality
        bAdd.addActionListener(e -> add());
        bCancel.addActionListener(e -> cancel());

        // add everything to the Grid layout, in this order
        //@formatter:off
        infoPanel.add(lName);               infoPanel.add(tfName);
        infoPanel.add(lDescription);        infoPanel.add(taDescription);
        infoPanel.add(lAddress);            infoPanel.add(tfAddress);
        infoPanel.add(lPropertyType);       infoPanel.add(tfPropertyType);
        infoPanel.add(lPricePerDay);        infoPanel.add(ntfPricePerDay);
        infoPanel.add(lMaxDaysOfBooking);   infoPanel.add(ntfMaxDaysOfBooking);
        infoPanel.add(bCancel);             infoPanel.add(bAdd);
        // imagePanel.add(imageDisplayPanel); // @formatter:on

        // finalize window
        setLocationRelativeTo(null);
        add(infoPanel);
        add(imageDisplayPanel);
        //pack();
        setResizable(true);
//        setResizable(false);
        setVisible(true);
    }

    /**
     * Method to add the new property listing
     */
    private void add() {
        if (tfName.getText().trim().equals("") || tfAddress.getTextArea().getText().trim().equals("")
                || taDescription.getTextArea().getText().trim().equals("") || tfPropertyType.getText().trim().equals("")
                || ntfPricePerDay.getText().trim().equals("") || ntfMaxDaysOfBooking.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Property info cannot be empty!", "Empty property info.", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Property property = new Property(connectedUser, tfName.getText(),
                taDescription.getTextArea().getText(), tfAddress.getTextArea().getText().toUpperCase(), tfPropertyType.getText().toUpperCase(),
                ntfPricePerDay.getText(), ntfMaxDaysOfBooking.getText());
        // add images
        property.addImages(imageDisplayPanel.getImagesArray());
        Properties.addProperty(property);
        dispose();
    }

    /**
     * Cancel the new property addition
     */
    private void cancel() {
        this.dispose();
    }

    /**
     * Tell user the property name is already in use
     */
    private void showNameNotAvailable() {
        JOptionPane.showMessageDialog(this, "Property name already taken! Try another!", "Property name already in use.", JOptionPane.WARNING_MESSAGE);
    }


}
