/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import static jframe.DBConnection.con;

/**
 *
 * @author Fahri
 */
public class SignupPage extends javax.swing.JFrame {

    /**
     * Creates new form SignupPage
     */
    public SignupPage() {
        initComponents();
    }

    // method to insert values into users table
    public void insertSignupDetails() {
        String name = txt_username.getText();
        String pwd = txt_password.getText();
        String email = txt_email.getText();
        String contact = txt_contact.getText();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into users(name,password,email,contact) values (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, name);
            pst.setString(2, pwd);
            pst.setString(3, email);
            pst.setString(4, contact);

            int updatedRowCount = pst.executeUpdate();

            if (updatedRowCount > 0) {
                JOptionPane.showMessageDialog(this, "recorded inserted successfully");
                LoginPage page = new LoginPage();
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "recorded insection failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    validation
    public boolean validateSignup() {
        String name = txt_username.getText();
        String pwd = txt_password.getText();
        String email = txt_email.getText();
        String contact = txt_contact.getText();

        if (name.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter username");
            return false;
        }

        if (pwd.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter password");
            return false;
        }

        if (email.equals("") || !email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(this, "please enter  valid email");
            return false;
        }

        if (contact.equals("")) {
            JOptionPane.showMessageDialog(this, "please enter contact number");
            return false;
        }
        return true;
    }

//    check duplicate users
    public boolean checkDuplicateUser() {
        String name = txt_username.getText();
        boolean isExist = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "");

            PreparedStatement pst = con.prepareStatement("select * from users where name = ?");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txt_username = new rojerusan.RSMetroTextPlaceHolder();
        txt_password = new rojerusan.RSMetroTextPlaceHolder();
        txt_email = new rojerusan.RSMetroTextPlaceHolder();
        txt_contact = new rojerusan.RSMetroTextPlaceHolder();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Swis721 Ex BT", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 255));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 120, -1));

        jLabel2.setFont(new java.awt.Font("Sitka Display", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 51, 255));
        jLabel2.setText("SMK Al Basyariah Library");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 320, -1));

        jLabel3.setFont(new java.awt.Font("Swis721 Ex BT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        jLabel4.setFont(new java.awt.Font("Sitka Display", 0, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 51));
        jLabel4.setText("Welcome To");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 160, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Webp.net-resizeimage (1).jpg"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 500, 470));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 850));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 36, -1, -1));

        jLabel8.setFont(new java.awt.Font("Swis721 Cn BT", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("X");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));

        jLabel9.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Create New Account");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Password");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, -1, 20));

        jLabel12.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Google_Mobile_50px.png"))); // NOI18N
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, -1, -1));

        jLabel13.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Contact");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, 90, -1));

        jLabel14.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Account_50px.png"))); // NOI18N
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Username");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        jLabel16.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Secure_50px.png"))); // NOI18N
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, -1));

        jLabel17.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Email");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 60, -1));

        jLabel18.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Secured_Letter_50px.png"))); // NOI18N
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 204));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 130, 40));

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Signup");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 580, 130, 40));

        jLabel19.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Signup Page");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        txt_username.setBackground(new java.awt.Color(102, 102, 255));
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_username.setForeground(new java.awt.Color(255, 255, 255));
        txt_username.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_username.setPhColor(new java.awt.Color(255, 255, 255));
        txt_username.setPlaceholder("Enter Username");
        txt_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_usernameFocusLost(evt);
            }
        });
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        jPanel2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        txt_password.setBackground(new java.awt.Color(102, 102, 255));
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_password.setForeground(new java.awt.Color(255, 255, 255));
        txt_password.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_password.setPhColor(new java.awt.Color(255, 255, 255));
        txt_password.setPlaceholder("Enter Password");
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel2.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, -1));

        txt_email.setBackground(new java.awt.Color(102, 102, 255));
        txt_email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_email.setForeground(new java.awt.Color(255, 255, 255));
        txt_email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_email.setPhColor(new java.awt.Color(255, 255, 255));
        txt_email.setPlaceholder("Enter Email");
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        jPanel2.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, -1, -1));

        txt_contact.setBackground(new java.awt.Color(102, 102, 255));
        txt_contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_contact.setForeground(new java.awt.Color(255, 255, 255));
        txt_contact.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_contact.setPhColor(new java.awt.Color(255, 255, 255));
        txt_contact.setPlaceholder("Enter Contact");
        txt_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactActionPerformed(evt);
            }
        });
        jPanel2.add(txt_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 480, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, 690, 830));

        setSize(new java.awt.Dimension(1067, 690));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoginPage page = new LoginPage();
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (validateSignup() == true) {
            if (checkDuplicateUser() == false) {
                insertSignupDetails();
            } else {
                JOptionPane.showMessageDialog(this, "username has already exist");
            }

        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_usernameFocusLost
        if (checkDuplicateUser() == true) {
            JOptionPane.showMessageDialog(this, "username already exist");
        }
    }//GEN-LAST:event_txt_usernameFocusLost

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
            java.util.logging.Logger.getLogger(SignupPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignupPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignupPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignupPage.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignupPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private rojerusan.RSMetroTextPlaceHolder txt_contact;
    private rojerusan.RSMetroTextPlaceHolder txt_email;
    private rojerusan.RSMetroTextPlaceHolder txt_password;
    private rojerusan.RSMetroTextPlaceHolder txt_username;
    // End of variables declaration//GEN-END:variables
}
