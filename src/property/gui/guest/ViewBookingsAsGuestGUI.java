package property.gui.guest;

import property.Booking;
import property.Bookings;
import property.gui.BookingsTablePanelGUI;
import property.gui.SearchBookingsDialogGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * The class that lets a guest view and search their bookings
 *
 * @author Lazaros Gogos
 * @version 2022.1.13
 */
public class ViewBookingsAsGuestGUI extends JDialog {


    /**
     * Creates a dialog where a guest can view their bookings
     *
     * @param connectedUser the connected user's name
     */
    public ViewBookingsAsGuestGUI(String connectedUser) {
        this(connectedUser, Bookings.getBookingsOfGuest(connectedUser));
    }

    /**
     * Creates a dialog where the connected user can view their bookings, specified by the second parameter
     *
     * @param connectedUser the connected user's name
     * @param bookings      the bookings {@link Collection} to be viewed
     */
    public ViewBookingsAsGuestGUI(String connectedUser, Collection<? extends Booking> bookings) {
        // standard code to set up the dialog
        setModal(true);
        setTitle("Bookings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(700, 400);
        // declare variables
        JButton bSearch, bCancel;
        JPanel pButtons;
        BookingsTablePanelGUI bookingsTable;

        // init variables
        bookingsTable = new BookingsTablePanelGUI(bookings);
        bSearch = new JButton("Search in bookings");
        bCancel = new JButton("Cancel booking");
        pButtons = new JPanel(new FlowLayout());

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
        bCancel.addActionListener(l -> {
            new CancelBookingDialog();
        });
        // add buttons to panel
        pButtons.add(bSearch);
        pButtons.add(bCancel);
        // add to dialog

        add(bookingsTable, BorderLayout.CENTER);
        add(pButtons, BorderLayout.SOUTH);
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
