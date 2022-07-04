package property.gui.guest;

import property.Booking;
import property.Bookings;
import property.gui.BookingsTablePanelGUI;
import property.gui.SearchBookingsDialogGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * The class that lets a guest view and search their cancelled bookings
 *
 * @author Lazaros Gogos
 * @version 2022.1.13
 */
public class ViewCancelledBookingsAsGuest extends JDialog {
    /**
     * Creates a dialog where a guest can view their cancelled bookings
     *
     * @param connectedUser the connected user's name
     */
    public ViewCancelledBookingsAsGuest(String connectedUser) {
        this(connectedUser, Bookings.getCancelledBookingsOfGuest(connectedUser));
    }

    /**
     * Creates a dialog where the connected user can view their cancelled bookings, specified by the second parameter
     *
     * @param connectedUser the connected user's name
     * @param bookings      the bookings {@link Collection} to be viewed
     */
    public ViewCancelledBookingsAsGuest(String connectedUser, Collection<? extends Booking> bookings) {
        // standard code to set up the dialog
        setModal(true);
        setTitle("Cancelled Bookings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        // declare variables
        JButton bSearch;
        BookingsTablePanelGUI bookingsTable;

        // init variables
        bookingsTable = new BookingsTablePanelGUI(bookings);
//        cancelledBookingsTable = new BookingsTablePanelGUI(cancelledBookings);
        bSearch = new JButton("Search in cancelled bookings");
//        bSearchCancelled = new JButton("Search in cancelled bookings");

        // add functionality
        bSearch.addActionListener(l -> new SearchBookingsDialogGUI(bookings) {
            @Override
            public void search() {
                if (this.getResults().isEmpty())
                    JOptionPane.showMessageDialog(this, "Nothing found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
                else
                    new ViewBookingsAsGuestGUI(connectedUser, this.getResults());
            }
        });
        // add to dialog

        add(bookingsTable, BorderLayout.CENTER);
//        add(cancelledBookingsTable);
        add(bSearch, BorderLayout.SOUTH);
//        add(bSearchCancelled);
//        add(table);
//        bookingsTable.setVisible(true);
//        pack();
        //finalize window
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

}
