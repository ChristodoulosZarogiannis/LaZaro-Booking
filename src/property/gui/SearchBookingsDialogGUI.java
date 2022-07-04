package property.gui;

import property.Booking;
import property.BookingUUID;
import property.Bookings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class spawns a dialog for a user to search bookings, in a given {@link Collection} of {@link Booking}s
 * in an intuitive way
 *
 * @author Lazaros Gogos
 * @version 2022.1.14
 */
public abstract class SearchBookingsDialogGUI extends JDialog {
    /**
     * The options by which the search will happen
     */
    private final JComboBox<String> dropdown;
    /**
     * The search keyword
     */
    private final JTextField tfSearchPhrase;
    /**
     * The {@link Collection} of {@link Booking}s we're searching in
     */
    private final Collection<Booking> allBookings;

    /**
     * Spawns a new dialog where the user can search in the given {@link Collection} of {@link Booking}s
     *
     * @param allBookings the bookings we're searching in
     */
    public SearchBookingsDialogGUI(Collection<? extends Booking> allBookings) {
        // init fields
        this.allBookings = new ArrayList<>();
        this.allBookings.addAll(allBookings);
        // standard code to set up the dialog
        setSize(800, 500);
        setModal(true);
        setLayout(new GridLayout(3, 2, 20, 20));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // declare variables
        String CHOICES[] = {"Booking ID", "Property Name", "Property Type", "Address", "Description", "Days of stay", "Owner"};
        JLabel lSearchPhrase, lSearchFilter;

        // init variables
        lSearchPhrase = new JLabel("Search Phrase:");
        lSearchFilter = new JLabel("Search filter");
        tfSearchPhrase = new JTextField();
        dropdown = new JComboBox(CHOICES);
        JButton bClose = new JButton("Close");
        JButton bSearch = new JButton("Search");
        // add functionality
        bClose.addActionListener(l -> close());
        bSearch.addActionListener(l -> search());
        // add to panel @formatter:off
        add(lSearchPhrase); add(tfSearchPhrase);
        add(lSearchFilter); add(dropdown);
        add(bClose); add(bSearch);
        // finalize @formatter:on
        setLocationRelativeTo(null);
        setResizable(true);
        pack();
        setVisible(true);
    }

    /**
     * Closes the dialog
     */
    private void close() {
        this.dispose();
    }

    /**
     * Method that is called when the user is ready to search
     */
    public abstract void search();

    /**
     * Method that returns an {@link ArrayList} with {@link Booking}s that match their search
     * options
     *
     * @return an {@link ArrayList} with {@link Booking}s that matches their search
     * options
     */
    public ArrayList<Booking> getResults() {
        ArrayList<Booking> arrayList = new ArrayList<>();
        String phrase = tfSearchPhrase.getText().toLowerCase();
        switch (dropdown.getSelectedIndex()) {
            case 0 -> {
                try {
                    int id = Integer.parseInt(phrase);
                    BookingUUID uuid = new BookingUUID(id);
                    if (Bookings.bookingExists(uuid))
                        arrayList.add(Bookings.getBooking(uuid));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Provide a vaild integer!", "Error while parsing integer", JOptionPane.ERROR_MESSAGE);
                }
            }
            case 1 -> {
                allBookings.forEach(booking -> {
                    if (booking.getProperty().getName().toLowerCase().contains(phrase))
                        arrayList.add(booking);
                });
            }
            case 2 -> {
                allBookings.forEach(booking -> {
                    if (booking.getProperty().getPropertyType().toLowerCase().contains(phrase))
                        arrayList.add(booking);
                });
            }
            case 3 -> {
                allBookings.forEach(booking -> {
                    if (booking.getProperty().getAddress().toLowerCase().contains(phrase))
                        arrayList.add(booking);
                });
            }
            case 4 -> {
                allBookings.forEach(booking -> {
                    if (booking.getProperty().getDescription().toLowerCase().contains(phrase))
                        arrayList.add(booking);
                });
            }
            case 5 -> {
                try {
                    int daysOfStay = Integer.parseInt(phrase);
                    allBookings.forEach(booking -> {
                        if (booking.getLength() == daysOfStay)
                            arrayList.add(booking);
                    });
                } catch (NumberFormatException e) {
                    tfSearchPhrase.setText(null);
                    JOptionPane.showMessageDialog(this, "Provide a vaild integer!", "Error while parsing integer", JOptionPane.ERROR_MESSAGE);
                }
            }
            case 6 -> {
                allBookings.forEach(booking -> {
                    if (booking.getProperty().getPartner().toLowerCase().contains(phrase))
                        arrayList.add(booking);
                });
            }
        }
        return arrayList;
    }
}
