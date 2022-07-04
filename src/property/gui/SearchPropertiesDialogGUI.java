package property.gui;

import property.Booking;
import property.Property;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class spawns a dialog for a user to search for any {@link Property}, in a given {@link Collection} of {@link Booking}s
 * in an intuitive way
 *
 * @author Lazaros Gogos
 * @version 2022.1.14
 */
public abstract class SearchPropertiesDialogGUI extends JDialog {
    /**
     * The options by which a search can be called
     */
    private final JComboBox<String> dropdown;
    /**
     * The search phrase text field
     */
    private final JTextField tfSearchPhrase;
    /**
     * The properties we're searching in
     */
    private final ArrayList<Property> allProperties;

    /**
     * Spawns a new dialog where the user can search in the given {@link Collection} of {@link Property}-ies
     *
     * @param allProperties a {@link Collection} of {@link Property}-ies we're searching in
     */
    public SearchPropertiesDialogGUI(Collection<Property> allProperties) {
        super();
        // init fields
        tfSearchPhrase = new JTextField();
        String CHOICES[] = {"Owner", "Facility", "Address", "Property Type", "Name", "Description"};
        dropdown = new JComboBox(CHOICES);
        this.allProperties = new ArrayList<>();
        this.allProperties.addAll(allProperties);
        // set up window
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));
        setSize(800, 500);
        setModal(false);
        // declare variables
        JLabel lSearchPhrase, lSearchFilter;
        JButton bClose, bSearch;

        // init variables
        lSearchPhrase = new JLabel("Search Phrase:");
        lSearchFilter = new JLabel("Search filter");

        bClose = new JButton("Close");
        bSearch = new JButton("Search");
        // add functionality
        bClose.addActionListener(l -> close());
        bSearch.addActionListener(l -> search());

        // add to dialog @formatter:off
        add(lSearchPhrase); add(tfSearchPhrase);
        add(lSearchFilter); add(dropdown);
        add(bClose); add(bSearch);
        // finalize @formatter:on
        setLocationRelativeTo(null);
        setResizable(true);
        pack();
        setVisible(true);
    }

    /**
     * Closes the dialog
     */
    private void close() {
        this.dispose();
    }

    /**
     * Method that returns an {@link ArrayList} based on the search phrase and the search filter
     *
     * @return an {@link ArrayList} of any {@link Property} that holds info
     */
    public ArrayList<Property> getResults() {
        ArrayList<Property> arrayList = new ArrayList<>();
        String phrase = tfSearchPhrase.getText().toUpperCase();
        switch (dropdown.getSelectedIndex()) {
            case 0 -> allProperties.forEach(p -> {
                if (p.getPartner().toUpperCase().contains(phrase))
                    arrayList.add(p);
            });

            case 1 -> allProperties.forEach(p -> {
                        if (p.getFacilities().containsKey(phrase))
                            arrayList.add(p);
                    }
            );
            case 2 -> allProperties.forEach(p -> {
                        if (p.getAddress().toUpperCase().contains(phrase))
                            arrayList.add(p);
                    }
            );
            case 3 -> allProperties.forEach(p -> {
                        if (p.getPropertyType().toUpperCase().contains(phrase))
                            arrayList.add(p);
                    }
            );
            case 4 -> allProperties.forEach(p -> {
                if (p.getName().toUpperCase().contains(phrase))
                    arrayList.add(p);
            });
            case 5 -> allProperties.forEach(p -> {
                if (p.getDescription().toUpperCase().contains(phrase))
                    arrayList.add(p);
            });
        }
        return arrayList;
    }

    /**
     * Abstract method that is called in order to search in the given {@link Collection} of {@link Property}-ies
     */
    public abstract void search();


}
