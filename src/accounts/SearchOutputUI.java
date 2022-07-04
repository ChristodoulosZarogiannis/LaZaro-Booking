package accounts;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class SearchOutputUI extends JDialog {

    /**
     * Constructor
     * @param set the set with the values matches the search query
     */
    SearchOutputUI(HashSet<String> set) {
        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.getWidth()/3;
        int height = (int)size.getHeight()/3;
        setLocation(width, height);

        setTitle("Search Results");
        setModal(false);
        setResizable(false);
        setLayout(new GridLayout(0, 2));

        createResults(set);

        pack();
        setVisible(true);
    }

    /**
     * This method creates the results of the search. It creates a table like format with text areas and buttons.
     * @param set the set with the values matches the search query
     */
    private void createResults(HashSet<String> set){
        ProfileButtonsListener listener = new ProfileButtonsListener();

        if (!set.isEmpty()) {
            int counter = 0;
            JTextArea[] textAreas = new JTextArea[set.size()];
            JButton[] viewButtons = new JButton[set.size()];
            JPanel[] panels = new JPanel[set.size()];
            for (String x : set) {
                panels[counter] = new JPanel();
                textAreas[counter] = new JTextArea(Accounts.getAccount(x).getUsername());
                textAreas[counter].setEditable(false);
                panels[counter].add(textAreas[counter]);
                add(panels[counter]);

                viewButtons[counter] = new JButton("Open Profile of " + x);
                viewButtons[counter].addActionListener(listener);
                add(viewButtons[counter]);

                counter++;
            }
        }
    }
}
