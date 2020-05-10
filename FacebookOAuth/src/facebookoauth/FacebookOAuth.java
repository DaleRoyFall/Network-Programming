/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookoauth;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import static com.restfb.Version.VERSION_7_0;
import com.restfb.types.User;
import static facebookoauth.Alignment.centerTheWindow;
import java.lang.Runtime.Version;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author olegb
 */
public class FacebookOAuth {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*String accessToken = "EAA1Y85IKpigBANoZAZAqAVal1spHJTBHTOCbM5ER8TdHNzQR0PSyHCN9HVInZAjMz51NCZCso3E1ZBTsnns2p80ZBKyZAPh0wYpcgDSUZCQFXne4a9QssZCn6wUfxJpbKaGHv7hpWcHWWor2I0pop51tR6hOGCYKgkpZCZCfiTMRZAfgrOoHVWiSZC8g4IDoDO2z7ZAILeYXxYIZABoYQZDZD";
        MainForm mainWindow = new MainForm(accessToken);
        centerTheWindow(mainWindow);
        mainWindow.setVisible(true);*/
        
        
        AuthForm auth = new AuthForm();
        auth.setVisible(true);
        centerTheWindow(auth);
    }
    
}
