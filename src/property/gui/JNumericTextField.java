package property.gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A JTextField implementation that accepts only numeric values
 *
 * @see <a href=https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java>This site</a> for
 * more info
 */
public class JNumericTextField extends JTextField {
    /**
     * If true, all characters are accepted. Otherwise,
     * only numeric characters are accepted
     */
    private boolean acceptAll;

    { // this block will be executed no matter which constructor is called
        acceptAll = false;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (acceptAll)
                    return;
                super.keyPressed(e);
//                String text = JNumericTextField.super.getText();
                if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '.' || e.getKeyCode() == KeyEvent.VK_BACK_SPACE
                        || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_MINUS) {
                    JNumericTextField.super.setEditable(true);
                    JNumericTextField.super.setToolTipText(null);
                } else {
                    JNumericTextField.super.setEditable(false);
                    JNumericTextField.super.setToolTipText("Enter only numeric digits (0-9)!");
//                    JNumericTextField.super.setHighlighter("Enter only numeric digits (0-9)!");
                }


            }
        });
    }

    /**
     * Set whether this text field can accept all characters, or only numeric ones
     *
     * @param acceptAll the boolean value
     */
    public void setAcceptAll(boolean acceptAll) {
        this.acceptAll = acceptAll;
    }


}
