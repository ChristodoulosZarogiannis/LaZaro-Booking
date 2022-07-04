package property.gui.guest;

import property.Property;
import property.gui.ViewPropertyPanelGUI;

import javax.swing.*;
import java.awt.*;

/**
 * This class spawns a GUI for a guest to view one specific property
 *
 * @author Lazaros Gogos
 * @version 2022.1.14
 */
public class ViewPropertyDialogAsGuestGUI extends JDialog {
    /**
     * The property the user is currently viewing
     */
    private Property currentProperty;
    /**
     * The connected user's name
     */
    private String connectedUser;

    /**
     * Creates a new dialog where a connected user can view a property as a guest would.
     *
     * @param connectedUser   the connected user's name
     * @param currentProperty the property the user is currently seeing
     */
    public ViewPropertyDialogAsGuestGUI(String connectedUser, Property currentProperty) {
        // standard code to set up the dialog
        super();
        this.connectedUser = connectedUser;
        this.currentProperty = currentProperty;
        setTitle("View Property Dialog");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 500);
        setModal(false);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        ViewPropertyPanelGUI viewPropertyPanel = new ViewPropertyPanelGUI(connectedUser, currentProperty, false);
        JButton bClose, bBook;

        // init buttons
        bClose = new JButton("Close");
        bBook = new JButton("Book");
        bClose.addActionListener(l -> close());
        bBook.addActionListener(l -> book());

        // add to dialog
        buttonsPanel.add(bClose);
        buttonsPanel.add(bBook);
        add(viewPropertyPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        //@formatter:on
        // finalize window
        setLocationRelativeTo(null);
        setResizable(true);

        setVisible(true);
    }

    /**
     * Closes the dialog
     */
    private void close() {
        dispose();
    }

    /**
     * Spawns a new dialog where the guest can book the specific property
     */
    private void book() {
        new NewBookingDialogGUI(connectedUser, currentProperty);
    }

}
