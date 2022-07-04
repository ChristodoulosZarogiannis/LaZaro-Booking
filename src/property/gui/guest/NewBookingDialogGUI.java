package property.gui.guest;

import date.Date;
import property.Booking;
import property.Bookings;
import property.Properties;
import property.Property;
import property.gui.JNumericTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * This class creates a new dialog for a user, where they can book a property
 *
 * @author Lazaros Gogos
 * @version 2021.12.30
 */
public class NewBookingDialogGUI extends JDialog {
    /**
     * The text fields where the user can input day, month, year and days of stay
     */
    private JNumericTextField day, month, year, length;
    /**
     * The name of the property
     */
    private JLabel lName;
    /**
     * The buttons for checking availability and booking
     */
    private JButton bCheckAvailability, bBook;
    /**
     * The connected user's name
     */
    private String connectedUser;

    /**
     * Creates a new dialog where a user can book a property, if available
     *
     * @param connectedUser The connected user's name
     * @param property      The property that will be booked
     */
    public NewBookingDialogGUI(String connectedUser, Property property) {
        // standard code to set up the dialog
        this.connectedUser = connectedUser;
        setModal(true);
        setTitle("New Booking Dialog");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLayout(new GridLayout(8, 2, 5, 10));
        // declare variables
        JLabel lDay, lMonth, lYear, lLength, lAvailable, lPropertyName;
        // init variables
        length = new JNumericTextField();
        // init buttons
        bCheckAvailability = new JButton("Check Availability");
        bCheckAvailability.addActionListener(e -> checkAvailability());
        bBook = new JButton("Book property");

        bBook.setEnabled(false);
        bBook.addActionListener(e -> book());

        // initialize text fields
        year = new JNumericTextField();
        year.addFocusListener(new FocusAdapter() { // make sure user inputs only years 2021 or later
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {

                    int y = Integer.parseInt(year.getText());
                    if (y <= 2020) {
                        year.setText(null);
                        year.setToolTipText("Year must be 2021 or later!");
                        /*month.setEnabled(false);
                        day.setEnabled(false);
                        month.setText(null);
                        day.setText(null);*/
                    }
                    /*else {
                        month.setEnabled(true);
                        day.setEnabled(true);
                    }*/
                } catch (NumberFormatException exception) {
                    year.setText(null);
                    /*month.setEnabled(false);
                    month.setEnabled(false);
                    day.setEnabled(false);
                    month.setText(null);
                    day.setText(null);*/
                }
            }
        });
        month = new JNumericTextField();
//        month.setEnabled(false);
        month.addFocusListener(new FocusAdapter() { // make sure user inputs only 1-12 numbers
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    int m = Integer.parseInt(month.getText());
                    if (m < 1 || m > 12) {
                        month.setText(null);
//                        day.setEnabled(false);
                    }
                    /*else
                        day.setEnabled(true);*/
                } catch (NumberFormatException exception) {
                    month.setText(null);
//                    day.setEnabled(false);
//                    day.setText(null);
                }
            }
        });
        day = new JNumericTextField();
//        day.setEnabled(false);
        day.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    /* deal with varying days per month. e.g. if it's a leap year
                     * and the month is February, allow for the number 29 to be entered*/
                    super.focusLost(e);
                    int y = Integer.parseInt(year.getText());
                    int m = Integer.parseInt(month.getText());
                    int totalDaysOfMonth = Date.getTotalDaysOfMonth(y, m);
                    int d = Integer.parseInt(day.getText());
                    if (d < 0 || d > totalDaysOfMonth) {
                        day.setText(null);
//                        day.setEnabled(false);
                        day.setToolTipText("Maximum days of given month is: " + totalDaysOfMonth);
                    } else {
                        day.setEnabled(true);
                        day.setToolTipText(null);
                    }
                } catch (NumberFormatException exception) {
                    day.setText(null);
                }
            }
            // add listeners to buttons with lambda expressions
        });
        year.addActionListener(l -> bBook.setEnabled(false));
        month.addActionListener(l -> bBook.setEnabled(false));
        length.addActionListener(l -> bBook.setEnabled(false));
        day.addActionListener(l -> bBook.setEnabled(false));


        // init labels
        lYear = new JLabel("Year: ");
        lYear.setToolTipText("Year must be 2021 or later");
        lMonth = new JLabel("Month: ");
        lDay = new JLabel("Day: ");
        lLength = new JLabel("Length: ");
        lAvailable = new JLabel("Available: ");
        /*
         *
         */
        JLabel lFlag = new JLabel();
        lPropertyName = new JLabel("Property name: ");
        lName = new JLabel(property.getName());

        // add everything to the Grid layout
        //@formatter:off
        add(lPropertyName);
        add(lName);
        add(lYear);
        add(year);
        add(lMonth);
        add(month);
        add(lDay);
        add(day);
        add(lLength);
        add(length);
        add(lAvailable);
        add(lFlag);
        add(bCheckAvailability);
        add(bBook);
        //@formatter:on
        // finalize window and show it to user
        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setVisible(true);
    }


    /**
     * Books the property
     */
    private void book() {
        Property p = Properties.getProperty(lName.getText());
        try {
            int d = Integer.parseInt(day.getText());
            int m = Integer.parseInt(month.getText());
            int y = Integer.parseInt(year.getText());
            int l = Integer.parseInt(length.getText());
            int date = Date.getDay(y, m, d);
            Booking b = new Booking(getGuest(), p, y, m, d, l);
            Bookings.addBooking(b);
            if (p.book(getGuest(), y, m, d, l, b.getBookingUUID())) {
                JOptionPane.showMessageDialog(this, "Booked successfully! Booking ID: " + b.getBookingUUID().getUUID() + ". Cost: " + b.getPrice(),
                        "Booking Dialog", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else
                JOptionPane.showMessageDialog(this, "Something went wrong, the booking was not successful! Does this booking ID belong to you? " +
                        b.getBookingUUID().getUUID(), "Booking Dialog", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Booking not successful! Did you enter correct booking info ?", "Error dialog", JOptionPane.ERROR_MESSAGE);
        }
        bBook.setEnabled(false);
//        dispose();
    }

    /**
     * Checks if the property is available
     */
    private void checkAvailability() {
        Property p = Properties.getProperty(lName.getText());
        try {
            int d = Integer.parseInt(day.getText());
            int m = Integer.parseInt(month.getText());
            int y = Integer.parseInt(year.getText());
            int l = Integer.parseInt(length.getText());
            int date = Date.getDay(y, m, d);
            if (p.isBookedAt(date, l) || l > p.getMaxDaysOfBooking()) {
                JOptionPane.showMessageDialog(this, "Not available!", "Availability dialog", JOptionPane.ERROR_MESSAGE);
                bBook.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Available!", "Availability dialog", JOptionPane.INFORMATION_MESSAGE);
                // if available, set bBook enabled !
                bBook.setEnabled(true);
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Something is not right!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private String getGuest() {
        return connectedUser;
    }

}
