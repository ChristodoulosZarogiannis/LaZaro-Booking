package accounts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Christodoulos Zarogiannis
 * @version 2021.12.30
 */
public class ProfileButtonsListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = e.getActionCommand();
        String lastWord = buttonText.substring(buttonText.lastIndexOf(" ")+1);

        new ProfileUI("chriszaro", lastWord);
    }
}
