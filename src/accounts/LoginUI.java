package accounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class LoginUI extends JDialog {
    /**
     * text field for username input
     */
    private JTextField usernameField;

    /**
     * text field for password input
     */
    private JPasswordField passwordField;

    /**
     * Constructor. Build GUI for Login.
     * @param frame the parent frame we want to return to if user decides not to login.
     */
    public LoginUI(JFrame frame) {
        setTitle("Login");
        setModal(true);
        setLayout(new GridLayout(3, 1, 10, 10));
        setLocationRelativeTo(null);
        setResizable(false);

        JButton buttonLogin = new JButton("Login");
        JButton buttonCancel = new JButton("Cancel");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        buttonLogin.addActionListener(e -> onLogin(frame));

        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onLogin(frame);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onLogin(frame);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(buttonLogin);
        add(buttonCancel);
        pack();
        setVisible(true);

    }

    /**
     * This method implements the function of the login button.
     * @param frame parent frame
     */
    private void onLogin(JFrame frame) {
        String usernameLogged = usernameField.getText().toLowerCase();
        if (Login(usernameLogged, String.valueOf(passwordField.getPassword())) != null) {
            Account accountLogged = Accounts.getAccount(usernameLogged);
            if (accountLogged instanceof Admin) {
                new AdminUI(usernameField.getText());
            } else if (accountLogged instanceof Partner) {
                new PartnerUI(usernameField.getText());
            } else {
                new GuestUI(usernameField.getText());
            }
            frame.setVisible(false);
            dispose();
        }

    }

    /**
     * This method implements the function of the cancel button.
     */
    private void onCancel() {
        dispose();
    }

    /**
     * This method checks the credentials the user gave as input and returns the username of the connected user
     * else it displays a message for the user.
     * @param username a String with the username of the connected user
     * @param password a String with the password of the connected user
     * @return the username of the user or null if the username doesn't exist
     */
    private static String Login(String username, String password) {
        String userPassword = Accounts.getPassword(username);

        if (userPassword == null) {
            JOptionPane.showMessageDialog(null, "User not found", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else if (!(password.equals(userPassword))) {
            JOptionPane.showMessageDialog(null, "Wrong password", "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
        } else {
            return username;
        }
        return null;
    }
}

