package property.gui.partner;

import property.Properties;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * This class spawns a GUI for a partner to edit one of their properties's facilities
 *
 * @author Lazaros Gogos
 * @version 2022.1.14
 */
public class EditFacilityDialogGUI extends JDialog {
    /**
     * The options from which the partner selects what facilities to edit
     */
    private final JComboBox<String> dropdown;
    /**
     * Textfields where the partner can add new facilities
     */
    private final JTextField tfNewFacilityName, tfNewFacilityValue, tfExistingFacilityNewValue;
    /**
     * This property's name
     */
    private final String propertyName;

    /**
     * Creates a dialog where a partner can edit the selected
     * property
     *
     * @param parent       the parent window
     * @param propertyName this property's name
     */
    public EditFacilityDialogGUI(JDialog parent, String propertyName) {
        // standard code to set up the dialog
        super(parent);
        this.propertyName = propertyName;
        setTitle("Edit facility dialog");
        setModal(true);
        setLayout(new GridLayout(6, 2, 10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // declare variables
        HashMap<String, String> facilities;
        JLabel lNewFacilityName, lNewFacilityValue, lAvailableFacilities, lNewValueForExistingFacility;
        JButton bAdd, bOverwrite, bRemove, bDone;

        // init
        facilities = Properties.getProperty(propertyName).getFacilities();
        lNewFacilityName = new JLabel("New Facility Name");
        lNewFacilityValue = new JLabel("New Facility Value");
        lAvailableFacilities = new JLabel("Available Facilities");
        lNewValueForExistingFacility = new JLabel("New Value for existing facility (overwrite)");
        tfNewFacilityName = new JTextField();
        tfNewFacilityValue = new JTextField();
        tfExistingFacilityNewValue = new JTextField();


        bAdd = new JButton("Add new facility");
        bOverwrite = new JButton("Overwrite existing facility");
        bRemove = new JButton("Remove");
        bDone = new JButton("Done");
        dropdown = new JComboBox(facilities.keySet().toArray());
        // add tooltips
        tfNewFacilityName.setToolTipText("This field cannot be left empty!");
        tfNewFacilityValue.setToolTipText("This field can be left empty");
        tfExistingFacilityNewValue.setToolTipText("This field can be left empty");
        // add button functionality
        dropdown.addActionListener(l -> updateTextFieldExistingValue());
        if (dropdown.getItemCount() != 0) {
            dropdown.setSelectedIndex(0);
            tfExistingFacilityNewValue.setText((String) facilities.values().toArray()[0]); // get first facility value
        }
        bAdd.addActionListener(l -> addFacility());
        bOverwrite.addActionListener(l -> overwriteFacility());
        bRemove.addActionListener(l -> removeSelectedFacility());
        bDone.addActionListener(l -> done());
        // add evefyrthing in the dialog window @formatter:off
        add(lNewFacilityName); add(lNewFacilityValue);
        add(tfNewFacilityName); add(tfNewFacilityValue);
        add(lAvailableFacilities); add(dropdown);
        add(lNewValueForExistingFacility); add(tfExistingFacilityNewValue);
        add(bAdd); add(bOverwrite);
        add(bRemove); add(bDone);

        // finalize window @formatter:on
        pack();
        setResizable(false);
        setVisible(true);

    }

    /**
     * Method that adds a NEW facility to this property
     */
    private void addFacility() {
        String newFacilityName = tfNewFacilityName.getText().trim().toUpperCase();
        if (newFacilityName.equals("")) { // ensure new facility name is not an empty string
            JOptionPane.showMessageDialog(this, "New facility name cannot be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Properties.getProperty(propertyName).facilityExists(newFacilityName)) { // guard if item exists
            JOptionPane.showMessageDialog(this, "This facility name already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String newFacilityValue = tfNewFacilityValue.getText().trim();
        Properties.getProperty(propertyName).addFacility(newFacilityName, newFacilityValue);
        dropdown.addItem(newFacilityName); // this item definitely does not exist
        tfNewFacilityName.setText(null);
        tfNewFacilityValue.setText(null);
    }

    /**
     * Method that edits an already existing facility
     */
    private void overwriteFacility() {
        if (dropdown.getItemCount() == 0)
            return;
        String newFacilityValue = tfExistingFacilityNewValue.getText().trim();
        String facilityName = (String) dropdown.getSelectedItem();
        Properties.getProperty(propertyName).editFacility(facilityName, newFacilityValue);
    }

    /**
     * Method that removes selected facility
     */
    private void removeSelectedFacility() {
        if (dropdown.getItemCount() == 0)
            return;
        String facilityName = (String) dropdown.getSelectedItem();
        Properties.getProperty(propertyName).removeFacility(facilityName);
        dropdown.removeItem(facilityName);
    }

    /**
     * Method that closes the window
     */
    private void done() {
        this.dispose();
    }

    /**
     * Updates the textfield of editing an already existing facility, with its currently set value
     */
    private void updateTextFieldExistingValue() {
        tfExistingFacilityNewValue.setText(Properties.getProperty(propertyName).getFacilityValue((String) dropdown.getSelectedItem()));
    }

}
