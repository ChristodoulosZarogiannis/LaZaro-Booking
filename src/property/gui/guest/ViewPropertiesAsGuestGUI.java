package property.gui.guest;

import property.Properties;
import property.Property;
import property.gui.SearchPropertiesDialogGUI;
import property.gui.ViewPropertiesPanelGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class spawns a GUI for a guest to view all properties
 *
 * @author Lazaros Gogos
 * @version 2022.1.4
 */
public class ViewPropertiesAsGuestGUI extends JDialog {

    /**
     * Creates a dialog where a guest can view all available properties
     *
     * @param connectedUser the connected user's name
     */
    public ViewPropertiesAsGuestGUI(String connectedUser) {
        this(connectedUser, Properties.getAllProperties());
    }

    /**
     * Creates a dialog where a guest can view all the properties specified by the second parameter
     *
     * @param connectedUser the connected user's name
     * @param allProperties the bookings {@link Collection} to be viewed
     */
    public ViewPropertiesAsGuestGUI(String connectedUser, ArrayList<Property> allProperties) {
        // standard code to set up the dialog
        setTitle("View Properties Dialog");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setModal(false);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
        // declare variables
        JScrollPane scrollPane;
        ViewPropertiesPanelGUI viewPropertiesPanel;
        JButton bSearch;
        // init variables
        viewPropertiesPanel = new ViewPropertiesPanelGUI(connectedUser, allProperties) {
            @Override
            public void viewProperty(String connectedUser, Property property) {
                new ViewPropertyDialogAsGuestGUI(connectedUser, property);
            }
        };
        bSearch = new JButton("Search");
        // add functionality to button
        bSearch.addActionListener(l -> new SearchPropertiesDialogGUI(allProperties) {
            @Override
            public void search() {
                ArrayList<Property> results = getResults();
                if (results.isEmpty())
                    JOptionPane.showMessageDialog(this, "Nothing found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
                else
                    new ViewPropertiesAsGuestGUI(connectedUser, results);
            }
        });
        scrollPane = new JScrollPane(viewPropertiesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // add to dialog
        add(scrollPane, BorderLayout.CENTER);
        add(bSearch, BorderLayout.SOUTH);
        //finalize window
        pack();
        setVisible(true);
    }

}
