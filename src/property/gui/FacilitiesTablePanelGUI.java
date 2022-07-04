package property.gui;

import property.Property;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A table, inside a {@link JPanel}, where facilities can be viewed
 */
public class FacilitiesTablePanelGUI extends JPanel {

    /**
     * Creates the table where facilities can be viewed
     *
     * @param connectedUser the connected user
     * @param property      the property from which to draw facilities
     */
    public FacilitiesTablePanelGUI(String connectedUser, Property property) {
        // set up the panel
        setLayout(new BorderLayout());
        // init data
        String cols[] = {"Facility Name", "Facility Value"};
        HashMap<String, String> facilities = property.getFacilities();
        ArrayList<String> facilityNames = new ArrayList<>();
        ArrayList<String> facilityValues = new ArrayList<>();
        facilityNames.addAll(facilities.keySet());
        facilityValues.addAll(facilities.values());
        int size = facilities.size();
        String data[][] = new String[size][size];
        for (int i = 0; i < size; i++) { // init table
            data[i][0] = facilityNames.get(i);
            data[i][1] = facilityValues.get(i);
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
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        // add to panel
        add(scrollPane);
    }
}
