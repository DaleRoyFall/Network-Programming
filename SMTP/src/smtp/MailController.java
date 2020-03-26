/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtp;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

/**
 *
 * @author User
 */
public class MailController {

    private static final MailController instance = new MailController();

    public static String username = null;
    public static String password = null;

    // Create store
    public static Store store;
    // Get inbox folder
    public static Folder inbox;
    // Get messages
    protected static Message[] messages;

    // Get class instance
    public static MailController getInstance() {
        return instance;
    }

    // Set login data
    public void setLoginData(String username, String password) {
        MailController.username = username;
        MailController.password = password;

        try {
            if (username != null && password != null) {
                initComponents();
            }
        } catch (MessagingException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Initialize components
    private static void initComponents() throws MessagingException {
        // Create proprieties
        Properties properties = new Properties();

        // Create session
        Session session = Session.getDefaultInstance(properties, null);
        // Create store
        store = session.getStore("imaps");
        // Connect to gmail
        store.connect("smtp.gmail.com", username, password);
        // Get inbox folder
        inbox = store.getFolder("inbox");
        // Open folder for read
        inbox.open(Folder.READ_ONLY);
        // Get messages
        messages = inbox.getMessages();
    }

    // Destroy the components
    public static void destroyComponents() throws MessagingException {
        // Close inbox and store
        if (MailController.inbox != null) {
            MailController.inbox.close(true);
        }
        if (MailController.store != null) {
            MailController.store.close();
        }
    }
}
