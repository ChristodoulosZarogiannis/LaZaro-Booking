package property.gui.partner;

import property.Bookings;
import property.Properties;
import property.Property;
import property.gui.ImageDisplayPanelGUI;
import property.gui.SearchPropertiesDialogGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * This class spawns a GUI for partner to view their owned properties
 *
 * @author Lazaros Gogos
 * @version 2022.1.3
 */
public class ViewOwnedPropertiesAsPartnerGUI extends JDialog {
    /**
     * Spawns a new dialog where the partner can view their properties
     *
     * @param connectedUser the connected user's name
     */
    public ViewOwnedPropertiesAsPartnerGUI(String connectedUser) {
        this(connectedUser, Properties.getPropertiesOwnedBy(connectedUser));
    }

    /**
     * Spawns a new dialog where the partner can view the properties given as the second parameter
     *
     * @param connectedUser   the connected user's name
     * @param ownedProperties the properties this partner will view
     */
    public ViewOwnedPropertiesAsPartnerGUI(String connectedUser, ArrayList<Property> ownedProperties) {
        // standard code to set up the dialog
        setTitle("View Property Dialog");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(800, 100);
        setModal(false);
        setLocation(300, 300);
        setResizable(true);
        setLayout(new BorderLayout());
        // declare variables
        JButton bSearch, bViewBookings, bViewCancelledBookings;
//        ArrayList<Property> ownedProperties = Properties.getPropertiesOwnedBy(connectedUser);
        JPanel panel, pButtons;

        // init
        panel = new JPanel();
        pButtons = new JPanel();
        panel.setLayout(new GridLayout(ownedProperties.size(), 1, 0, 10));
        bSearch = new JButton("Search");
        ownedProperties.forEach((p) -> {
            JPanel pElements = createElement(connectedUser, p);
            panel.add(pElements);
        });
        bViewBookings = new JButton("View all bookings");
        bViewCancelledBookings = new JButton("View all cancelled bookings");


        // add functionality
        bSearch.addActionListener(l -> {
            new SearchPropertiesDialogGUI(ownedProperties) {
                @Override
                public void search() {
                    ArrayList<Property> results = getResults();
                    if (results.isEmpty())
                        JOptionPane.showMessageDialog(this, "Nothing found!", "No results!", JOptionPane.INFORMATION_MESSAGE);
                    else
                        new ViewOwnedPropertiesAsPartnerGUI(connectedUser, results);
                }
            };
        });
        bViewBookings.addActionListener(l -> new ViewPropertyBookingsAsPartnerGUI(connectedUser, Bookings.getBookingsOfPartner(connectedUser)));
        bViewCancelledBookings.addActionListener(l -> new ViewPropertyBookingsAsPartnerGUI(connectedUser, Bookings.getCancelledBookingsOfPartner(connectedUser)));
        // add to scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pButtons.add(bSearch);
        pButtons.add(bViewBookings);
        pButtons.add(bViewCancelledBookings);
        // add to dialog
        add(scrollPane, BorderLayout.CENTER);
        add(pButtons, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private JPanel createElement(String connectedUser, Property property) {
        JPanel panel = new JPanel(new GridLayout(1, 5, 20, 20));
        JLabel lName = new JLabel(property.getName());
        ImageDisplayPanelGUI imagePanel = new ImageDisplayPanelGUI(property.getImages(), false);
        JButton bView = new JButton("View");
        JButton bEdit = new JButton("Edit");
        JButton bRemove = new JButton("Remove");
        // add functionality to buttons
        bView.addActionListener(l -> {
            new ViewPropertyAsPartnerGUI(connectedUser, property);
        });
        // add a new window listener so that when the editing is over, the new photos are saved
        bEdit.addActionListener(l -> {
            EditPropertyDialogGUI editPropertyDialogGUI =
                    new EditPropertyDialogGUI(connectedUser, property);
            editPropertyDialogGUI.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {

                }

                @Override
                public void windowClosed(WindowEvent e) {
                    imagePanel.updateArray(editPropertyDialogGUI.getImageDisplayPanel().getImagesArray());
                    Properties.getProperty(property.getName()).setImages(editPropertyDialogGUI.getImageDisplayPanel().getImagesArray());
                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });
        });
        bRemove.addActionListener(l -> {
            panel.setVisible(false);
            Properties.deleteProperty(property.getName());
        }); //TODO implement removal correctly
        panel.add(lName);
        panel.add(imagePanel);
        panel.add(bView);
        panel.add(bEdit);
        panel.add(bRemove);
        return panel;
    }

}
