package property.gui;

import property.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A panel that contains concise information of properties, with button
 * that allows the viewing of any selected property
 */
public abstract class ViewPropertiesPanelGUI extends JPanel {

    /**
     * Creates a panel where info of properties are easily viewed
     *
     * @param connectedUser the connected user's name
     * @param allProperties an {@link ArrayList} of {@link Property}-ies we're looking at
     */
    public ViewPropertiesPanelGUI(String connectedUser, ArrayList<Property> allProperties) {
        // set up panel
        this.setLayout(new GridLayout(allProperties.size(), 1, 0, 10));
        // create each sub-panel for each property, with its name, images and a button for viewing it
        allProperties.forEach((p) -> {
            JPanel pElement = createElement(connectedUser, p);
            this.add(pElement);
        });

//        JScrollPane scrollPane = new JScrollPane(panel);
//        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        add(scrollPane, BorderLayout.CENTER);
//        add(bSearch);
//        pack();
//        setMaximumSize(new Dimension(1000, 400));
        setVisible(true);
    }

    /**
     * Creates a panel with info about a property, such as its name, images and a button for viewing more info
     * about it
     *
     * @param connectedUser the connected user's name
     * @param property      the property of which info is to be displayed
     * @return a panel ready to be put inside the main panel where all properties are shown
     */
    private JPanel createElement(String connectedUser, Property property) {
        JPanel panel = new JPanel(new GridLayout(1, 4, 20, 20));
        JLabel lName = new JLabel(property.getName());
        ImageDisplayPanelGUI imagePanel = new ImageDisplayPanelGUI(property.getImages(), false);
        JButton bView = new JButton("View Property");

        bView.addActionListener(l -> {
            viewProperty(connectedUser, property);
        });
        panel.add(lName);
        panel.add(imagePanel);
        panel.add(bView);
        return panel;
    }

    /**
     * Abstract method that allows for customizable call to view a property, specified by the second parameter
     *
     * @param connectedUser the connected user's name
     * @param property      which property to view
     */
    public abstract void viewProperty(String connectedUser, Property property);
}
