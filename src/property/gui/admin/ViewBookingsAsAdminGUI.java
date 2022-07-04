package property.gui.admin;

import property.Booking;
import property.Bookings;
import property.gui.BookingsTablePanelGUI;
import property.gui.SearchBookingsDialogGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The class that lets an admin view and search bookings
 *
 * @author Lazaros Gogos
 * @version 2022.1.13
 */
public class ViewBookingsAsAdminGUI extends JDialog {
    /**
     * Constructor of this class, which creates a dialog where an admin can view all bookings
     *
     * @param connectedUser the admin's username
     */
    public ViewBookingsAsAdminGUI(String connectedUser) {
        this(connectedUser, Bookings.getBookings().values());
    }


    /**
     * Constructor of this class, which creates a dialog where the connected user can view only the bookings passed as a parameter
     *
     * @param connectedUser the connected user
     * @param allBookings   the {@link Collection} of bookings the connected user is to see
     */
    public ViewBookingsAsAdminGUI(String connectedUser, Collection<? extends Booking> allBookings) {
        // standard code to set up the dialog
        setModal(true);
        setTitle("Bookings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        // declare variables
        JButton bSearch;
        BookingsTablePanelGUI bookingsTable;
        // init variables
        bSearch = new JButton("Search");
//        bookings.addAll(allBookings);
        bookingsTable = new BookingsTablePanelGUI(allBookings);
        // add functionality
        bSearch.addActionListener(l -> {
            new SearchBookingsDialogGUI(allBookings) {
                @Override
                public void search() {
                    ArrayList<Booking> results = getResults();
                    if (results.isEmpty())
                        JOptionPane.showMessageDialog(this, "Nothing found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
                    else
                        new ViewBookingsAsAdminGUI(connectedUser, results);
                }
            };
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
