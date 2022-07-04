package property.gui.admin;

import property.Properties;
import property.Property;
import property.gui.SearchPropertiesDialogGUI;
import property.gui.ViewPropertiesPanelGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The class that lets an admin view and search properties
 *
 * @author Lazaros Gogos
 * @version 2022.1.13
 */
public class ViewPropertiesAsAdminGUI extends JDialog {
    /**
     * Constructor of this class, where a dialog is spawned for an admin to view and search in all properties
     *
     * @param connectedUser the admin's username
     */
    public ViewPropertiesAsAdminGUI(String connectedUser) {
        this(connectedUser, Properties.getAllProperties());
    }

    /**
     * Constructor of this class, where a dialog is spawned for an admin to view and search in all properties
     * which are passed as an {@link ArrayList} parameter
     *
     * @param connectedUser the admin's username
     * @param allProperties the properties to be displayed in the dialog
     */
    public ViewPropertiesAsAdminGUI(String connectedUser, ArrayList<Property> allProperties) {
        // standard code to set up the dialog
        setTitle("View Properties");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(true);
        // declare variables
        ViewPropertiesPanelGUI viewPropertiesPanel;
        JButton bSearch;
        // init variables
        viewPropertiesPanel = new ViewPropertiesPanelGUI(connectedUser, allProperties) {
            @Override
            public void viewProperty(String connectedUser, Property property) {
                new ViewPropertyAsAdminGUI(connectedUser, property);
            }
        };
        bSearch = new JButton("Search");

        // add functionality
        bSearch.addActionListener(l -> new SearchPropertiesDialogGUI(allProperties) {
            @Override
            public void search() {
                ArrayList<Property> results = getResults();
                if (results.isEmpty())
                    JOptionPane.showMessageDialog(this, "Nothing found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
                else
                    new ViewPropertiesAsAdminGUI(connectedUser, results);
            }
        });
        // add to dialog
        add(viewPropertiesPanel, BorderLayout.CENTER);
        add(bSearch, BorderLayout.SOUTH);
        // finalize window and show it to user
        pack();
        setVisible(true);
    }
}
