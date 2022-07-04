package accounts;

import property.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.Book;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class ProfileUI extends JDialog {
    /**
     * contains the email of the user
     */
    private JTextField emailField;

    /**
     * contains the address of the user
     */
    private JTextField addressField;

    /**
     * contains the phone of the user
     */
    private JTextField phoneField;

    /**
     * a String with the username of the connected user
     */
    private final String connectedUser;

    /**
     * a String with the username of the profile we request to view
     */
    private final String profile;

    /**
     * the account of the user we want to view
     */
    private final Account profileAccount;

    /**
     * Constructor. Creates the GUI.
     *
     * @param connectedUser a String with the username of the connected user
     * @param profile       a String with the username of the profile we request to view
     */
    ProfileUI(String connectedUser, String profile) {
        this.connectedUser = connectedUser;
        this.profile = profile;
        profileAccount = Accounts.getAccount(profile);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth() / 3;
        int height = (int) size.getHeight() / 3;
        setLocation(width, height);

        setTitle("Profile");
        setModal(false);
        setResizable(false);
        setLayout(new GridLayout(0, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(profileAccount.getUsername());
        usernameField.setEnabled(false);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(profileAccount.getEmail());
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(profileAccount.getName());
        nameField.setEnabled(false);
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(profileAccount.getAddress());
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(profileAccount.getPhone());
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete Account");

        add(nameLabel);
        add(nameField);
        add(usernameLabel);
        add(usernameField);
        add(emailLabel);
        add(emailField);
        add(phoneLabel);
        add(phoneField);
        add(addressLabel);
        add(addressField);
        add(updateButton);
        add(deleteButton);
        pack();
        setVisible(true);

        deleteButton.addActionListener(e -> onDelete());

        updateButton.addActionListener(e -> onUpdate());

        emailField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onUpdate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        phoneField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onUpdate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        addressField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onUpdate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    /**
     * This method implements the function of the "Delete" button. After it deletes the account, it updates the database.
     */
    private void onDelete() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?", "Delete Account Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result != JOptionPane.NO_OPTION) {
            if (!Accounts.getAccount(connectedUser).getRole().equals("admin")) {
                if (profileAccount.getRole().equals("guest")) {
                    Iterator<Map.Entry<BookingUUID, Booking>> it;
                    it = Bookings.getBookings().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<BookingUUID, Booking> e = it.next();
                        Booking b = e.getValue();
                        if (b.getGuest().equals(profile)) {
                            Bookings.getBooking(b.getBookingUUID()).getProperty().removeBookingsByUUID(b.getBookingUUID());
                            Bookings.getBookings().remove(b.getBookingUUID());
                            it = Bookings.getBookings().entrySet().iterator();
                        }
                    }
                } else if (profileAccount.getRole().equals("partner")) {
                    Iterator<Map.Entry<String, Property>> it;
                    it = Properties.getProperties().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Property> e = it.next();
                        if (e.getValue().getPartner().equals(profile)) {
                            Properties.deleteProperty(e.getValue().getName());
                            it = Properties.getProperties().entrySet().iterator();
                        }
                    }
                }
                Accounts.deleteAccount(profile);
                write();
                System.exit(0);
            } else {
                Accounts.deleteAccount(profile);
                dispose();
            }
            write();
        }
    }

    /**
     * This method updates the files with the new changes.
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
    }

    /**
     * This method implements the function of the "Update" button. It sets new values to the credentials of the user.
     */
    private void onUpdate() {
        boolean check1 = false, check2 = false;
        if (!emailField.getText().contains("@") || !emailField.getText().contains(".") || emailField.getText().length() < 5) {
            JOptionPane.showMessageDialog(null, "This is not an email.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            check1 = false;
        } else {
            Accounts.getAccount(connectedUser).setEmail(emailField.getText());
            check1 = true;
        }

        if (!Admin.isNumeric(phoneField.getText()) || phoneField.getText().length() != 10) {
            JOptionPane.showMessageDialog(null, "This is not a phone number.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
            check2 = false;
        } else {
            Accounts.getAccount(connectedUser).setPhone(phoneField.getText());
            check2 = true;
        }

        Accounts.getAccount(connectedUser).setAddress(addressField.getText());
        if (check1 && check2)
            JOptionPane.showMessageDialog(null, "Updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
