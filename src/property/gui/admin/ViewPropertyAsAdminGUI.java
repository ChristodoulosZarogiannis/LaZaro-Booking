package property.gui.admin;

import property.Property;
import property.gui.ViewPropertyPanelGUI;

import javax.swing.*;
import java.awt.*;

/**
 * The class that lets an admin view a property
 *
 * @author Lazaros Gogos
 * @version 2022.1.12
 */
public class ViewPropertyAsAdminGUI extends JDialog {

    /**
     * Constructor of this class, which spawns a dialog where the admin can view the {@link Property} passed
     * as a parameter
     *
     * @param connectedUser   the admin's username
     * @param currentProperty the {@link Property} to be viewed
     */
    public ViewPropertyAsAdminGUI(String connectedUser, Property currentProperty) {
        // standard code to set up the dialog
        setTitle("View Property Dialog");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 500);
        setModal(true);
        // declare and init variables
        ViewPropertyPanelGUI viewPropertyPanel = new ViewPropertyPanelGUI(connectedUser, currentProperty, false);
        // add to dialog
        add(viewPropertyPanel, BorderLayout.CENTER);
        // finalize window
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
