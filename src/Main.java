import accounts.Accounts;
import accounts.Admin;
import accounts.LoginMenuUI;
import property.Bookings;
import property.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Lazaros Gogos
 * @author Christodoulos Zarogiannis
 * @version 2021.12.30
 */
public class Main {
    /**
     * Initializes our static fields with values from the files.
     */
    private static void read() {
        // read from file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files" + File.separatorChar + "properties.ser"))) {
            Properties.initialize((Properties) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files" + File.separatorChar + "bookings.ser"))) {
            Bookings.initialize((Bookings) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files" + File.separatorChar + "accounts.ser"))) {
            Accounts accounts = (Accounts) in.readObject();
            Accounts.initialize(accounts);
        } catch (Exception ignored) {
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("files" + File.separatorChar + "pending.ser"))) {
            Admin testAdmin = (Admin) in.readObject();
            Admin.initialize(testAdmin);
        } catch (Exception ignored) {
        }
    }

    public static void main(String[] args) {
        read();
        new LoginMenuUI();
//        Locale.setDefault(Locale.ENGLISH);
        //new NewPropertyDialogGUI("lazar");

        // new SearchPropertiesDialogGUI("lazar");

//        new EditPropertyDialogGUI("lazar", Properties.getProperty("homem"));
        //new ViewPropertyDialogAsGuestGUI("lazar", Properties.getProperty("homem"));
        //new ViewBookingsAsGuestGUI("lazar");
        //new ViewBookingsAsGuestGUI("lazar", Bookings.getBookingsOfGuest("lazar"));

//        new NewBookingDialogGUI("lazar", Properties.getProperty("avida"));
        //  new ViewOwnedPropertiesAsPartnerGUI("lazar");
        //new ViewPropertiesAsGuestGUI("lazar");
        // write();
    }

}
