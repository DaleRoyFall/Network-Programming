/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookoauth;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Account;
import com.restfb.types.GraphResponse;
import com.restfb.types.Page;
import com.restfb.types.User;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.border.Border;

/**
 *
 * @author olegb
 */
public final class MainForm extends javax.swing.JFrame {

    MouseEvent getPositionEvent;

    private final FacebookClient facebookClient;
    private String userID;
    private String pageID;
    private User user;
    private Page page;

    ArrayList<AccountPages> listOfPages = new ArrayList<>();

    /**
     * Creates new form MainForm
     */
    public MainForm(String accessToken) {
        initComponents();

        PagePanel.setVisible(false);

        //System.out.println(accessToken);
        // Get facebook client
        facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_7_0);
        // Get facebook user
        user = facebookClient.fetchObject("me", User.class);
        // Get user ID
        userID = user.getId();

        getProfileGeneral();
        getprofileImage();
        getAccountConnections();

        jList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {

                    AccountPanel.setVisible(false);
                    PagePanel.setVisible(true);

                    // Get index 
                    int index = list.locationToIndex(evt.getPoint());

                    // Get page
                    var page = listOfPages.get(index);

                    // Save page ID
                    pageID = page.getId();

                    // Set data
                    PageNameLabel.setText(page.getName());
                    PageIDLabel.setText("ID: " + page.getId());
                    PageTockenLabel.setText("Tocken: " + page.getTocken());
                    PageCategoryLabel.setText("Category: " + page.getCategory());

                    AvatarNameLabel.setText(Character.toString(page.getName().charAt(0)));
                }
            }
        });

    }

    void getProfileGeneral() {

        IDLabel.setText(userID);
        NameLabel.setText(user.getName());
    }

    void getprofileImage() {
        JsonObject picture
                = facebookClient.fetchObject("me/picture",
                        JsonObject.class, Parameter.with("redirect", "false"));

        var pictureUrl = picture.get("data").asObject().get("url");

        try {
            URL imageUrl = new URL(pictureUrl.asString());

            Image image = ImageIO.read(imageUrl);
            BufferedImage resizedImage = resizeImage(image, 100, 100, true);

            ImageIcon icon = new ImageIcon(resizedImage);

            PhotoLabel.setText("");
            PhotoLabel.setIcon(icon);
        } catch (Exception ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void getAccountConnections() {
        Connection<Account> pages = facebookClient.fetchConnection("me/accounts", Account.class);
        List<Account> accounts = pages.getData();

        DefaultListModel listModel = new DefaultListModel();

        for (Account account : accounts) {

            listOfPages.add(new AccountPages(account.getAccessToken(),
                    account.getName(),
                    account.getId(),
                    account.getCategory()));

            listModel.addElement("   " + account.getName());
        }

        Border emptyBorder = BorderFactory.createEmptyBorder();
        jList.setBorder(emptyBorder);
        jList.setModel(listModel);
    }

    BufferedImage resizeImage(Image image,
            int scaledWidth, int scaledHeight,
            boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jPanelDragWindow = new javax.swing.JPanel();
        jPanelCloseWindow = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        PagePanel = new javax.swing.JPanel();
        PageIDLabel = new javax.swing.JLabel();
        PageTockenLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelPages2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PostTextField = new javax.swing.JTextField();
        PostButton = new javax.swing.JButton();
        PageNameLabel = new javax.swing.JLabel();
        PageCategoryLabel = new javax.swing.JLabel();
        AvatarNameLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        AccountPanel = new javax.swing.JPanel();
        IDLabel = new javax.swing.JLabel();
        PhotoLabel = new javax.swing.JLabel();
        WelcomeLabel = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList<>();
        jLabelPages = new javax.swing.JLabel();
        WelcomeLabel1 = new javax.swing.JLabel();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelDragWindow.setBackground(new java.awt.Color(1, 50, 67));
        jPanelDragWindow.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanelDragWindowMouseDragged(evt);
            }
        });
        jPanelDragWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelDragWindowMousePressed(evt);
            }
        });

        jPanelCloseWindow.setBackground(new java.awt.Color(228, 241, 254));
        jPanelCloseWindow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelCloseWindowMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCloseWindowLayout = new javax.swing.GroupLayout(jPanelCloseWindow);
        jPanelCloseWindow.setLayout(jPanelCloseWindowLayout);
        jPanelCloseWindowLayout.setHorizontalGroup(
            jPanelCloseWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 64, Short.MAX_VALUE)
        );
        jPanelCloseWindowLayout.setVerticalGroup(
            jPanelCloseWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelDragWindowLayout = new javax.swing.GroupLayout(jPanelDragWindow);
        jPanelDragWindow.setLayout(jPanelDragWindowLayout);
        jPanelDragWindowLayout.setHorizontalGroup(
            jPanelDragWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDragWindowLayout.createSequentialGroup()
                .addGap(0, 736, Short.MAX_VALUE)
                .addComponent(jPanelCloseWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelDragWindowLayout.setVerticalGroup(
            jPanelDragWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDragWindowLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelCloseWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanelDragWindow, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 60));

        jPanel3.setBackground(new java.awt.Color(44, 62, 80));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 140, 540));

        PagePanel.setBackground(new java.awt.Color(52, 73, 94));
        PagePanel.setPreferredSize(new java.awt.Dimension(800, 500));
        PagePanel.setLayout(null);

        PageIDLabel.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        PageIDLabel.setForeground(new java.awt.Color(240, 240, 240));
        PageIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PageIDLabel.setText("PageID");
        PagePanel.add(PageIDLabel);
        PageIDLabel.setBounds(170, 100, 470, 30);

        PageTockenLabel.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        PageTockenLabel.setForeground(new java.awt.Color(240, 240, 240));
        PageTockenLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PageTockenLabel.setText("PAGETOKEN");
        PagePanel.add(PageTockenLabel);
        PageTockenLabel.setBounds(170, 140, 470, 30);

        jPanel5.setBackground(new java.awt.Color(44, 62, 80));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPages2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelPages2.setForeground(new java.awt.Color(240, 240, 240));
        jLabelPages2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPages2.setText("Make a post");
        jPanel5.add(jLabelPages2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 260, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("Message");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));
        jPanel5.add(PostTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 140, -1));

        PostButton.setText("Post");
        PostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PostButtonMousePressed(evt);
            }
        });
        jPanel5.add(PostButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, -1, -1));

        PagePanel.add(jPanel5);
        jPanel5.setBounds(0, 230, 660, 310);

        PageNameLabel.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        PageNameLabel.setForeground(new java.awt.Color(240, 240, 240));
        PageNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PageNameLabel.setText("PageName");
        PagePanel.add(PageNameLabel);
        PageNameLabel.setBounds(20, 40, 620, 42);

        PageCategoryLabel.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        PageCategoryLabel.setForeground(new java.awt.Color(240, 240, 240));
        PageCategoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PageCategoryLabel.setText("PAGECATEGORY");
        PagePanel.add(PageCategoryLabel);
        PageCategoryLabel.setBounds(170, 180, 470, 30);

        AvatarNameLabel.setBackground(new java.awt.Color(44, 62, 80));
        AvatarNameLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        AvatarNameLabel.setForeground(new java.awt.Color(52, 73, 94));
        AvatarNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AvatarNameLabel.setText("P");
        PagePanel.add(AvatarNameLabel);
        AvatarNameLabel.setBounds(30, 100, 110, 110);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/avatar.png"))); // NOI18N
        PagePanel.add(jLabel1);
        jLabel1.setBounds(30, 100, 110, 110);

        jLabel2.setFont(new java.awt.Font("MS Gothic", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(228, 241, 254));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("←");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        PagePanel.add(jLabel2);
        jLabel2.setBounds(0, 0, 80, 50);

        getContentPane().add(PagePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 660, 540));

        AccountPanel.setBackground(new java.awt.Color(52, 73, 94));
        AccountPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        AccountPanel.setLayout(null);

        IDLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        IDLabel.setForeground(new java.awt.Color(240, 240, 240));
        IDLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IDLabel.setText("ID");
        AccountPanel.add(IDLabel);
        IDLabel.setBounds(210, 120, 260, 30);

        PhotoLabel.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        PhotoLabel.setForeground(new java.awt.Color(240, 240, 240));
        PhotoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PhotoLabel.setText("Photo");
        AccountPanel.add(PhotoLabel);
        PhotoLabel.setBounds(30, 80, 170, 150);

        WelcomeLabel.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(240, 240, 240));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("WELCOME");
        AccountPanel.add(WelcomeLabel);
        WelcomeLabel.setBounds(20, 40, 620, 42);

        NameLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        NameLabel.setForeground(new java.awt.Color(240, 240, 240));
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("NAME");
        AccountPanel.add(NameLabel);
        NameLabel.setBounds(210, 160, 260, 30);

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jList.setBackground(new java.awt.Color(44, 62, 80));
        jList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList.setForeground(new java.awt.Color(240, 240, 240));
        jList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jList);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 250, 210));

        jLabelPages.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelPages.setForeground(new java.awt.Color(240, 240, 240));
        jLabelPages.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPages.setText("Pages");
        jPanel2.add(jLabelPages, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 260, -1));

        AccountPanel.add(jPanel2);
        jPanel2.setBounds(0, 230, 660, 310);

        WelcomeLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        WelcomeLabel1.setForeground(new java.awt.Color(240, 240, 240));
        WelcomeLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel1.setText("WELCOME");
        AccountPanel.add(WelcomeLabel1);
        WelcomeLabel1.setBounds(20, 40, 620, 42);

        getContentPane().add(AccountPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 660, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelCloseWindowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCloseWindowMousePressed
        System.exit(0);
    }//GEN-LAST:event_jPanelCloseWindowMousePressed

    private void jPanelDragWindowMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelDragWindowMouseDragged
        // Set new location
        setLocation(evt.getXOnScreen() - getPositionEvent.getX(),
                evt.getYOnScreen() - getPositionEvent.getY());
    }//GEN-LAST:event_jPanelDragWindowMouseDragged

    private void jPanelDragWindowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelDragWindowMousePressed
        // Get current position
        getPositionEvent = evt;
    }//GEN-LAST:event_jPanelDragWindowMousePressed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        PagePanel.setVisible(false);
        AccountPanel.setVisible(true);
    }//GEN-LAST:event_jLabel2MousePressed

    private void PostButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostButtonMousePressed
        String message = PostTextField.getText();

        // Create new thread
        new Thread("Post") {
            @Override
            public void run() {
                facebookClient.publish(pageID + "/feed", GraphResponse.class, Parameter.with("message", message));
            }
        }.start();
    }//GEN-LAST:event_PostButtonMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccountPanel;
    private javax.swing.JLabel AvatarNameLabel;
    private javax.swing.JLabel IDLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PageCategoryLabel;
    private javax.swing.JLabel PageIDLabel;
    private javax.swing.JLabel PageNameLabel;
    private javax.swing.JPanel PagePanel;
    private javax.swing.JLabel PageTockenLabel;
    private javax.swing.JLabel PhotoLabel;
    private javax.swing.JButton PostButton;
    private javax.swing.JTextField PostTextField;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JLabel WelcomeLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelPages;
    private javax.swing.JLabel jLabelPages2;
    private javax.swing.JList<String> jList;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelCloseWindow;
    private javax.swing.JPanel jPanelDragWindow;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    // End of variables declaration//GEN-END:variables
}
