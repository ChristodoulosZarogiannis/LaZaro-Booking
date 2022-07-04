package property.gui.guest;

import property.BookingUUID;
import property.Bookings;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog where a booking can be cancelled
 *
 * @author Lazaros Gogos
 * @author Christodoulos Zarogiannis
 * @version 2022.1.10
 */
public class CancelBookingDialog extends JDialog {
    /**
     * The text field where the {@link BookingUUID} will be
     * given so that it can be cancelled
     */
    private final JTextField tfID;

    /**
     * Constructor which sets up and spawns the dialog
     * to allow for booking cancellation
     */
    public CancelBookingDialog() {
        setModal(true);
        setTitle("Cancel booking");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0, 3));
        setSize(500, 80);
        // declare varialbes
        JLabel lBookingUUID;
        JButton bCancel;

        // init variables
        lBookingUUID = new JLabel("Provide ID");
        tfID = new JTextField();
        //tfID.setSize(100,20);
        bCancel = new JButton("Cancel this booking");

        // add button funcionality
        bCancel.addActionListener(l -> {
            cancelBooking();
        });
        // add to dialog
        add(lBookingUUID);
        add(tfID);
        add(bCancel);
        // finalize window
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    /**
     * Method that cancels the booking, if it exists
     */
    private void cancelBooking() {
        try {
            int id = Integer.parseInt(tfID.getText());
            if (!Bookings.getBookings().containsKey(new BookingUUID(id)))
                JOptionPane.showMessageDialog(this, "Booking does not exist!", "Invalid booking UUID", JOptionPane.ERROR_MESSAGE);
            else {
                Bookings.cancelBooking(id);
                JOptionPane.showMessageDialog(this, "Booking canceled!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (NumberFormatException e) {
            tfID.setText(null);
            JOptionPane.showMessageDialog(this, "Provide a valid integer", "Invalid number", JOptionPane.ERROR_MESSAGE);
        }

    }

}
