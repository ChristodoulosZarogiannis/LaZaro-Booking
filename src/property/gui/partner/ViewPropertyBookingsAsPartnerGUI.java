package property.gui.partner;

import property.Booking;
import property.gui.BookingsTablePanelGUI;
import property.gui.SearchBookingsDialogGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * This class spawns a GUI for partner to view the bookings of one property
 *
 * @author Lazaros Gogos
 * @version 2022.1.8
 */
public class ViewPropertyBookingsAsPartnerGUI extends JDialog {
    /**
     * Spawns a new dialog where the partner can view the bookings of one property
     *
     * @param connectedUser the connected user's name
     * @param bookings      the bookings that are to be displayed
     */
    public ViewPropertyBookingsAsPartnerGUI(String connectedUser, Collection<Booking> bookings) {
        // standard code to set up the dialog
        setModal(true);
        setTitle("This property's bookings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        // declare variables
        BookingsTablePanelGUI bookingsTable;
        JButton bSearch;
        // init variables
        bSearch = new JButton("Search");

        bookingsTable = new BookingsTablePanelGUI(bookings);

        // add functionality
        bSearch.addActionListener(l -> new SearchBookingsDialogGUI(bookings) {
            @Override
            public void search() {
                if (this.getResults().isEmpty())
                    JOptionPane.showMessageDialog(this, "Nothing found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
                else
                    new ViewPropertyBookingsAsPartnerGUI(connectedUser, this.getResults());
            }
        });
        // add to dialog
        add(bookingsTable, BorderLayout.CENTER);
        add(bSearch, BorderLayout.SOUTH);
        // finalize
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
