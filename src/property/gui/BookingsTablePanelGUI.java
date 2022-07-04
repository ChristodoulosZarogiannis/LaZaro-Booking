package property.gui;

import property.Booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A custom table inside a panel where booking details are displayed
 */
public class BookingsTablePanelGUI extends JPanel {

    /**
     * Creates a new panel, which can be added in a {@link JFrame} or a {@link JDialog}, where the connected user can view the bookings, given
     * as a parameter
     *
     * @param allBookings the bookings of which info will be displayed
     */
    public BookingsTablePanelGUI(Collection<? extends Booking> allBookings) {
        // set up the panel
        setLayout(new BorderLayout());
        // init data
        String cols[] = {"Booking ID", "Property Name", "Starting date", "Address", "Guest", "Days of stay", "Price", "Owner"};
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.addAll(allBookings);

        String data[][] = new String[bookings.size()][cols.length];
        for (int i = 0; i < bookings.size(); i++) {
//            if (!bookings.get(i).getGuest().equals(connectedUser))
//                continue;
            data[i][0] = String.valueOf(bookings.get(i).getBookingUUID().getUUID());
            data[i][1] = bookings.get(i).getProperty().getName();
            // Make starting date
            StringBuilder builder = new StringBuilder(); // TODO Is it better to declare this before this loop ?
            Booking b = bookings.get(i);
            builder.append(b.getDay());
            builder.append("-");
            builder.append(b.getMonth());
            builder.append("-");
            builder.append(b.getYear());
            data[i][2] = builder.toString();

            data[i][3] = bookings.get(i).getProperty().getAddress();
            data[i][4] = bookings.get(i).getGuest();
            int daysOfStay = bookings.get(i).getLength();
            data[i][5] = String.valueOf(daysOfStay);
            data[i][6] = String.valueOf(daysOfStay * bookings.get(i).getPrice());
            data[i][7] = bookings.get(i).getProperty().getPartner();
        }
        // create table
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, cols);
        JTable table = new JTable(defaultTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        table.setFillsViewportHeight(true);

        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        // add to panel
        add(scrollPane, BorderLayout.CENTER);
    }

}
