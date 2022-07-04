package accounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class RegisterUI extends JDialog {
    /**
     * The new user enters here his/her username.
     */
    private JTextField usernameField;

    /**
     * The new user enters here his/her password.
     */
    private JPasswordField passwordField;

    /**
     * The new user enters here his/her email.
     */
    private JTextField emailField;

    /**
     * The new user enters here his/her name.
     */
    private JTextField nameField;

    /**
     * The new user enters here his/her address.
     */
    private JTextField addressField;

    /**
     * The new user enters here his/her phone.
     */
    private JTextField phoneField;

    /**
     * group for the guestRadioButton and the partnerRadioButton
     */
    private JPanel radioButtonGroup;

    /**
     * if this is selected, the new user is a guest.
     */
    private JRadioButton guestRadioButton;

    /**
     * if this is selected, the new user is a partner.
     */
    private JRadioButton partnerRadioButton;

    /**
     * Constructor. Creates the Registration form GUI.
     */
    public RegisterUI() {
        setTitle("Registration Form");
        setModal(true);
        setLayout(new GridLayout(0, 2, 10, 10));
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        JLabel accountTypeLabel = new JLabel("Account Type:");
        JPanel radioButtonGroup = new JPanel();
        guestRadioButton = new JRadioButton("Guest");
        partnerRadioButton = new JRadioButton("Partner");
        JButton buttonRegister = new JButton("Register");
        JButton buttonCancel = new JButton("Cancel");

        ButtonGroup group = new ButtonGroup();
        group.add(guestRadioButton);
        group.add(partnerRadioButton);

        radioButtonGroup.add(guestRadioButton);
        radioButtonGroup.add(partnerRadioButton);

        buttonRegister.addActionListener(e -> onRegister());

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
        add(emailLabel);
        add(emailField);
        add(nameLabel);
        add(nameField);
        add(addressLabel);
        add(addressField);
        add(phoneLabel);
        add(phoneField);
        add(accountTypeLabel);
        add(radioButtonGroup);
        add(buttonRegister);
        add(buttonCancel);
        pack();
        setVisible(true);

    }

    /**
     * This method implements the function of the register button. It checks if credentials are valid and creates the new account.
     */
    private void onRegister() {
        if (usernameField.getText().isBlank() || passwordField.getPassword().length == 0 || emailField.getText().isBlank() || nameField.getText().isBlank() || addressField.getText().isBlank() || phoneField.getText().isBlank() || (!guestRadioButton.isSelected() && !partnerRadioButton.isSelected())) {
            JOptionPane.showMessageDialog(null, "Please fill all fields.", "Blank Fields", JOptionPane.ERROR_MESSAGE);
        } else if (Accounts.accountExists(usernameField.getText().toLowerCase())) {
            JOptionPane.showMessageDialog(null, "Username taken!", "Invalid Username", JOptionPane.ERROR_MESSAGE);
        } else if (!emailField.getText().contains("@") || !emailField.getText().contains(".") || emailField.getText().length() < 5) {
            JOptionPane.showMessageDialog(null, "This is not an email.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
        } else if (!Admin.isNumeric(phoneField.getText()) || phoneField.getText().length() != 10) {
            JOptionPane.showMessageDialog(null, "This is not a phone number.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
        } else {
            if (guestRadioButton.isSelected())
                Admin.addAccountToGetApproved(new Guest(usernameField.getText().toLowerCase(), String.valueOf(passwordField.getPassword()), nameField.getText(), emailField.getText().toLowerCase(), phoneField.getText(), addressField.getText(), guestRadioButton.getText().toLowerCase()));
            else if (partnerRadioButton.isSelected())
                Admin.addAccountToGetApproved(new Partner(usernameField.getText().toLowerCase(), String.valueOf(passwordField.getPassword()), nameField.getText(), emailField.getText().toLowerCase(), phoneField.getText(), addressField.getText(), partnerRadioButton.getText().toLowerCase()));
            JOptionPane.showMessageDialog(null, "Your registration has been submitted and pending for approval.", "Successful Registration", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    /**
     * This method implements the function of the cancel button.
     */
    private void onCancel() {
        dispose();
    }
}


