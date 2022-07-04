package property.gui.partner;

import property.Bookings;
import property.Property;
import property.gui.ViewPropertyPanelGUI;

import javax.swing.*;
import java.awt.*;

/**
 * This class spawns a GUI for partner to view a property
 *
 * @author Lazaros Gogos
 * @version 2022.1.5
 */
public class ViewPropertyAsPartnerGUI extends JDialog {
    /**
     * Spawns a new dialog where the partner can view their property
     *
     * @param connectedUser the connected user's name
     * @param property      the property this partner will view
     */
    public ViewPropertyAsPartnerGUI(String connectedUser, Property property) {
        // standard code to set up the dialog
        setTitle("View Property Dialog");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(true);
        // declare variables
        JPanel buttonsPanel;
        ViewPropertyPanelGUI viewPropertyPanel;
        JButton bViewBookings, bViewCancelledBookings;

        // init variables
        viewPropertyPanel = new ViewPropertyPanelGUI(connectedUser, property, false);
        buttonsPanel = new JPanel();
        bViewBookings = new JButton("View bookings");
        bViewCancelledBookings = new JButton("View cancelled bookings");
        // add button funcionality
        bViewBookings.addActionListener(l -> {
            new ViewPropertyBookingsAsPartnerGUI(connectedUser, Bookings.getBookingsOfProperty(property));
        });
        bViewCancelledBookings.addActionListener(l -> {
            new ViewPropertyBookingsAsPartnerGUI(connectedUser, Bookings.getCancelledBookingsOfProperty(property));
        });
        // add buttons to dialog
        buttonsPanel.add(bViewBookings);
        buttonsPanel.add(bViewCancelledBookings);
        // add to dialog
        add(viewPropertyPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        // finalize
        //pack();
        setVisible(true);
    }
}
