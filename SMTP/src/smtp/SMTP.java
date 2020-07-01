/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtp;

import javax.mail.MessagingException;
import static smtp.Alignment.centerTheWindow;

/**
 *
 * @author User
 */
public class SMTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MessagingException {
        // Create login window
        /*LoginWindow loginWindow = new LoginWindow();
        // Align the window to center
        centerTheWindow(loginWindow);
        // Set window visible
        loginWindow.setVisible(true);*/
        
        
        MainWindow mainWindow = new MainWindow();
        // Align the window to center
        centerTheWindow(mainWindow);
        // Set window visible
        mainWindow.setVisible(true);
    }
    
}
