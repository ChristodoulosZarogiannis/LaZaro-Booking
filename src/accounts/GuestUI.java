package accounts;

import property.Bookings;
import property.Properties;
import property.gui.guest.ViewBookingsAsGuestGUI;
import property.gui.guest.ViewCancelledBookingsAsGuest;
import property.gui.guest.ViewPropertiesAsGuestGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Creates the GUI for a Guest user.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.10
 */
public class GuestUI extends JFrame {

    /**
     * Default Constructor. Creates the GUI for a Guest user.
     *
     * @param connectedUser String with the username of the connected user
     */
    GuestUI(String connectedUser) {
        setTitle("laZaro Booking; Guest");
        setLocation(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                write();
            }
        });

        JButton profileButton = new JButton("Profile");
        JButton messengerButton = new JButton("Messenger");
        if (Accounts.getAccount(connectedUser).getNewMessageNotification())
            messengerButton.setBackground(Color.YELLOW);
        JButton bookingsButton = new JButton("My Bookings");
        JButton bCancelledBookings = new JButton("Cancelled Bookings");
        JButton searchButton = new JButton("View Properties");

        JPanel pageStart = new JPanel(new BorderLayout());
        add(pageStart, BorderLayout.PAGE_START);
        JPanel displayStart = new JPanel();
        pageStart.add(displayStart, BorderLayout.LINE_END);
        displayStart.add(profileButton);
        displayStart.add(messengerButton);
        displayStart.add(bookingsButton);
        displayStart.add(bCancelledBookings);
        displayStart.add(searchButton);

        profileButton.addActionListener(e -> new ProfileUI(connectedUser, connectedUser));

        messengerButton.addActionListener(e -> new MessengerUI(connectedUser));

        bookingsButton.addActionListener(e -> new ViewBookingsAsGuestGUI(connectedUser));

        searchButton.addActionListener(e -> new ViewPropertiesAsGuestGUI(connectedUser));

        bCancelledBookings.addActionListener(l -> new ViewCancelledBookingsAsGuest(connectedUser));

        pack();
        setVisible(true);
    }

    /**
     * This method updates the files used as database for our data.
     */
    private void write() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files" + File.separatorChar + "accounts.ser"))) {
            out.writeObject(Accounts.closing());
        } catch (Exception ignored) {
        }


        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files" + File.separatorChar + "pending.ser"))) {
            out.writeObject(Admin.closing());
        } catch (Exception ignored) {
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files" + File.separatorChar + "properties.ser"))) {
            out.writeObject(Properties.close());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files" + File.separatorChar + "bookings.ser"))) {
            out.writeObject(Bookings.close());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.exit(0);
    }
}
