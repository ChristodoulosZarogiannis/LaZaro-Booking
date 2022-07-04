package accounts;

import property.Bookings;
import property.Properties;
import property.gui.admin.ViewBookingsAsAdminGUI;
import property.gui.admin.ViewCancelledBookingsAsAdmin;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Creates the GUI for an Admin user.
 *
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class AdminUI extends JFrame {

    /**
     * the field that waits the user for input the query word
     */
    private JTextField searchField;

    /**
     * if this button is selected then user wants to search by username
     */
    private JRadioButton usernameRadio;

    /**
     * if this button is selected then user wants to search by email
     */
    private JRadioButton emailRadio;

    /**
     * if this button is selected then user wants to search by phone
     */
    private JRadioButton phoneRadio;

    /**
     * if this button is selected then user wants to search by account type
     */
    private JRadioButton typeRadio;

    /**
     * Default Constructor. Creates the GUI for an Admin user.
     *
     * @param connectedUser String with the username of the connected user
     */
    AdminUI(String connectedUser) {
        setTitle("laZaro Booking; Admin");
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
        JButton pendingAccountsButton = new JButton("Pending Accounts");
        if (Admin.newAccountsExist())
            pendingAccountsButton.setBackground(Color.YELLOW);
        JButton showAllAccountsButton = new JButton("All Accounts");

        JButton myBookingsButton = new JButton("My Bookings");
        JButton bMyCancelledBookings = new JButton("My Cancelled Bookings");
        JButton allPropertiesButton = new JButton("All Properties");
        JButton allBookingsButton = new JButton("All Bookings");
        JButton bAllCancelledBookings = new JButton("All Cancelled Bookings");

        JPanel pageStart = new JPanel(new BorderLayout());
        add(pageStart, BorderLayout.PAGE_START);
        JPanel displayStart = new JPanel();
        pageStart.add(displayStart, BorderLayout.LINE_END);
        displayStart.add(profileButton);
        displayStart.add(messengerButton);
        displayStart.add(myBookingsButton);
        displayStart.add(bMyCancelledBookings);
        displayStart.add(pendingAccountsButton);
        displayStart.add(showAllAccountsButton);
        displayStart.add(allPropertiesButton);
        displayStart.add(allBookingsButton);
        displayStart.add(bAllCancelledBookings);

        profileButton.addActionListener(e -> new ProfileUI(connectedUser, connectedUser));

        messengerButton.addActionListener(e -> new MessengerUI(connectedUser));

        pendingAccountsButton.addActionListener(e -> new PendingAccountsUI());

        showAllAccountsButton.addActionListener(e -> new AllAccountsTableUI());

        myBookingsButton.addActionListener(e -> new ViewBookingsAsGuestGUI(connectedUser));

        bMyCancelledBookings.addActionListener(l -> new ViewCancelledBookingsAsGuest(connectedUser));

        allBookingsButton.addActionListener(e -> new ViewBookingsAsAdminGUI(connectedUser));

        bAllCancelledBookings.addActionListener(l -> new ViewCancelledBookingsAsAdmin(connectedUser));

        allPropertiesButton.addActionListener(e -> new ViewPropertiesAsGuestGUI(connectedUser));

        JPanel searchBarsCentered = new JPanel(new GridLayout(0, 3));
        add(searchBarsCentered, BorderLayout.CENTER);

        JLabel searchLabel = new JLabel("Search User:");
        searchField = new JTextField();
        JPanel displaySearchPanel = new JPanel();
        searchField.setPreferredSize(new Dimension(150, 24));
        displaySearchPanel.add(searchLabel);
        displaySearchPanel.add(searchField);
        searchBarsCentered.add(displaySearchPanel);

        JPanel searchOptions = new JPanel();
        usernameRadio = new JRadioButton("Username");
        searchOptions.add(usernameRadio);
        emailRadio = new JRadioButton("Email");
        searchOptions.add(emailRadio);
        phoneRadio = new JRadioButton("Phone");
        searchOptions.add(phoneRadio);
        typeRadio = new JRadioButton("Type");
        searchOptions.add(typeRadio);

        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(usernameRadio);
        searchGroup.add(emailRadio);
        searchGroup.add(phoneRadio);
        searchGroup.add(typeRadio);

        searchBarsCentered.add(searchOptions);

        JButton searchButton = new JButton("Search");
        searchBarsCentered.add(searchButton);

        searchButton.addActionListener(e -> onSearch());

        pack();
        setVisible(true);
    }

    /**
     * This method implements the function of the search button. Depending on the selected button,
     * the output of the search is based on a specific filter. A HashSet with the values is created and
     * a new GUI is formed.
     */
    private void onSearch() {
        String input = searchField.getText().toLowerCase();
        HashSet<String> set = new HashSet<>();
        if (!input.isEmpty()) {
            if (phoneRadio.isSelected()) {
                Iterator<Map.Entry<String, Account>> it;
                it = Accounts.getAccounts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Account> e = it.next();
                    if (e.getValue().getPhone().equals(input) || e.getValue().getPhone().contains(input))
                        set.add(e.getValue().getUsername());
                }
            } else if (emailRadio.isSelected()) {
                Iterator<Map.Entry<String, Account>> it;
                it = Accounts.getAccounts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Account> e = it.next();
                    if (e.getValue().getEmail().equals(input) || e.getValue().getEmail().contains(input))
                        set.add(e.getValue().getUsername());
                }
            } else if (typeRadio.isSelected()) {
                Iterator<Map.Entry<String, Account>> it;
                it = Accounts.getAccounts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Account> e = it.next();
                    if (e.getValue().getRole().equals(input) || e.getValue().getRole().contains(input))
                        set.add(e.getValue().getUsername());
                }
            } else if (usernameRadio.isSelected()) {
                Iterator<Map.Entry<String, Account>> it;
                it = Accounts.getAccounts().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Account> e = it.next();
                    if (e.getValue().getUsername().equals(input) || e.getValue().getUsername().contains(input))
                        set.add(e.getValue().getUsername());
                }
            }
            if (!set.isEmpty())
                new SearchOutputUI(set);
        }

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
