package property.gui.admin;

import property.Booking;
import property.Bookings;
import property.gui.BookingsTablePanelGUI;
import property.gui.SearchBookingsDialogGUI;
import property.gui.guest.ViewBookingsAsGuestGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * The class that lets an admin view and search cancelled bookings
 *
 * @author Lazaros Gogos
 * @version 2022.1.13
 */
public class ViewCancelledBookingsAsAdmin extends JDialog {
    /**
     * Constructor of this class, which creates a dialog where an admin can view all cancelled bookings
     *
     * @param connectedUser the admin's username
     */
    public ViewCancelledBookingsAsAdmin(String connectedUser) {
        this(connectedUser, Bookings.getCancelledBookings());
    }

    /**
     * Constructor of this class, which creates a dialog where the connected user can view only the cancelled bookings passed as a parameter
     *
     * @param connectedUser the connected user
     * @param bookings      the {@link Collection} of cancelled bookings the connected user is to see
     */
    public ViewCancelledBookingsAsAdmin(String connectedUser, Collection<? extends Booking> bookings) {
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
        bSearch = new JButton("Search in cancelled bookings");

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
        add(bSearch, BorderLayout.SOUTH);
        // finalize window and show it to user
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
