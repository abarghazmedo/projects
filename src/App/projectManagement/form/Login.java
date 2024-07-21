package App.projectManagement.form;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
          try {
             Image img = ImageIO.read(getClass().getResource("/adminIcons/code.png"));
             this.setIconImage(img);
         } catch (Exception e) {
         }
    }

    //validaion login if tetfiled empty 
    public boolean validateLogin() {
        String name = txt_username.getText();
        String paswrd = txt_password.getText();
        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Entre Email");
            return false;
        }
        if (paswrd.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Entre pasword ");
            return false;
        }
        return true;
    }
    
     String permission;
     
     
     static String userName;

    //verify to entre application
    public void login() {
    String email = txt_username.getText();
    char[] password = txt_password.getPassword(); // Assuming txt_password is a JPasswordField

    try (Connection con = DBconnection.getConnection()) {
        String query = "SELECT UserName FROM userp WHERE UserEmail = ? AND UserPassword = ?";
        try (PreparedStatement prs = con.prepareStatement(query)) {
            prs.setString(1, email);
            prs.setString(2, String.valueOf(password));

            try (ResultSet rs = prs.executeQuery()) {
                if (rs.next()) {
                    userName = rs.getString("UserName");
                    new Dashbord(email,userName).setVisible(true);
                    this.setVisible(false);
                    new Project(email);
                    new Task(email);
                    new SubTask(email);
                  
                   
                    
         
                    
                  // Project pr= new Project(email);
                } else {
                    JOptionPane.showMessageDialog(this, "Login is not correct!");
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Clear the password array for security reasons
        Arrays.fill(password, '0');
    }
}
     
  



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_password = new rojerusan.RSPasswordTextPlaceHolder();
        txt_username = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rSButtonHover1 = new rojerusan.RSButtonHover();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(250, 150));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/LogoProjectManaement3.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));

        jPanel2.setBackground(new java.awt.Color(0, 206, 209));
        jPanel2.setToolTipText("");
        jPanel2.setName(""); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Candara", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 81, 98));
        jLabel2.setText("x");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 20, 50));

        jLabel9.setFont(new java.awt.Font("Adobe Kaiti Std R", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("LOGIN");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 50));

        txt_password.setBackground(new java.awt.Color(0, 206, 209));
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_password.setForeground(new java.awt.Color(0, 0, 0));
        txt_password.setToolTipText("");
        txt_password.setPhColor(new java.awt.Color(255, 255, 255));
        txt_password.setPlaceholder("Entre Password");
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel2.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 170, -1));

        txt_username.setBackground(new java.awt.Color(0, 206, 209));
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 81, 98)));
        txt_username.setForeground(new java.awt.Color(0, 0, 0));
        txt_username.setToolTipText("");
        txt_username.setPhColor(new java.awt.Color(255, 255, 255));
        txt_username.setPlaceholder("Entre Your Email");
        jPanel2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 170, 40));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 81, 98));
        jLabel13.setText("E-mail");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 70, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/padlock (1).png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 40, 50));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 81, 98));
        jLabel14.setText("Password");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 70, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/user.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 50, 50));

        rSButtonHover1.setText("SE CONNECTER ");
        rSButtonHover1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonHover1ActionPerformed(evt);
            }
        });
        jPanel2.add(rSButtonHover1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, 150, 30));

        jLabel4.setFont(new java.awt.Font("Candara", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 81, 98));
        jLabel4.setText("-");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 20, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 0, 320, 500));

        setSize(new java.awt.Dimension(800, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void rSButtonHover1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonHover1ActionPerformed
         if (validateLogin()) {
            login();
        }
    }//GEN-LAST:event_rSButtonHover1ActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
         this.setExtendedState(Login.ICONIFIED);
    }//GEN-LAST:event_jLabel4MouseClicked

 
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       

       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private rojerusan.RSButtonHover rSButtonHover1;
    private rojerusan.RSPasswordTextPlaceHolder txt_password;
    private app.bolivia.swing.JCTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
