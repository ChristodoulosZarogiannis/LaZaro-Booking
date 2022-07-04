package accounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Creates Register/Login menu GUI.
 * @author Christodoulos Zarogiannis
 * @version 2022.01.02
 */
public class LoginMenuUI extends JFrame {
    /**
     * Default Constructor build GUI
     */
    public LoginMenuUI() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files\\accounts.ser"))) {
                    out.writeObject(Accounts.closing());
                } catch (Exception ignored) {
                }

                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files\\pending.ser"))) {
                    out.writeObject(Admin.closing());
                } catch (Exception ignored) {
                }

                System.exit(0);
            }
        });

        JButton register = new JButton("Register");
        register.addActionListener(e -> new RegisterUI());

        JButton login = new JButton("Login");
        login.addActionListener(e -> new LoginUI(this));

        setLayout(new GridLayout(0, 2, 10, 10));

        add(register);
        add(login);
        pack();
        setVisible(true);
    }
}