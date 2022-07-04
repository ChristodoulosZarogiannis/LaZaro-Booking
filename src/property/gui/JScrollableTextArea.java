package property.gui;

import javax.swing.*;
import java.awt.*;

public class JScrollableTextArea extends JPanel {
    private JTextArea ta;

    public JScrollableTextArea() {
        this(null);
    }

    public JScrollableTextArea(String text) {

        ta = new JTextArea(text);
        JScrollPane sp = new JScrollPane(ta);
        this.setLayout(new BorderLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 0;
//        this.add(sp, c);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(sp, BorderLayout.CENTER);

    }

    public JTextArea getTextArea() {
        return ta;
    }

}
