package accounts;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class PendingAccountsUI extends JDialog {

    JTable table;

    /**
     * Constructor
     */
    PendingAccountsUI() {
        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth()/3;
        int height = (int)size.getHeight()/3;
        setLocation(width, height);

        setTitle("Pending Accounts");

        table = new JTable(new MyTableModel());
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton button = new JButton("Approve All");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin.approveAccounts();
                dispose();
            }
        });
        add(button, BorderLayout.PAGE_END);

        add(scrollPane, BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    class MyTableModel extends AbstractTableModel {
        /**
         * The categories of our data.
         */
        String[] columnNames = {"username",
                "password",
                "email",
                "phone",
                "address",
                "role"};

        /**
         * The data of the table.
         */
        Object[][] data = (Object[][]) Admin.getAccountsToBeApproved();

        /**
         * @return the number of columns
         */
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * @return the number of rows
         */
        public int getRowCount() {
            return data.length;
        }

        /**
         * @param col the number of the column we are interested in
         * @return the name of the column
         */
        public String getColumnName(int col) {
            return columnNames[col];
        }

        /**
         * @param row the number of the row the data is located
         * @param col the number of the column the data is located
         * @return the data of the cell we want
         */
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /**
         * @param c the number of the column we are interested in
         * @return the class of the column
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }
}
