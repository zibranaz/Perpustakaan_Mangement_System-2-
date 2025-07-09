/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fahri
 */
public class Donation extends javax.swing.JFrame {

    private DefaultTableModel model;

    /**
     * Creates new form Donation_baru
     */
    public Donation() {
        loadDonations();
//        addDonation();
//        searchDonation();
        initComponents();
        // Debug: Cek apakah tabel terinisialisasi
        System.out.println("Tabel donation: " + (tbl_donation != null ? "Successfully Initialize" : "NULL"));

        // Inisialisasi model hanya jika tabel ada
        if (tbl_donation != null) {
            model = (DefaultTableModel) tbl_donation.getModel();
            model.setRowCount(0); // Bersihkan data awal

            // Load data
            loadDonations();

            // Set tanggal default
            date_donation.setDatoFecha(new Date());
        } else {
            JOptionPane.showMessageDialog(this, "Error: Table Not Initializing");
        }

//        loadDonations();
//        date_donation.setDatoFecha(new Date());
    }

    private void loadDonations() {
        // Pastikan model dan tabel sudah terinisialisasi
        if (tbl_donation == null || model == null) {
            System.err.println("Error: Table Or Model Not Initialzed");
            return;
        }

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM donation");

            model.setRowCount(0); // Clear existing data

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("donor_name"),
                    rs.getString("book_title"),
                    rs.getString("book_author"),
                    rs.getInt("quantity"),
                    sdf.format(rs.getDate("donation_date"))
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Tutup resources...
        }

    }

    private void addDonation() {
        if (!validateInput()) {
            return;
        }

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Nonaktifkan auto-commit

            String sql = "INSERT INTO donation (donor_name, book_title, book_author, quantity, donation_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txt_donor.getText());
            pst.setString(2, txt_title.getText());
            pst.setString(3, txt_author.getText());
            pst.setInt(4, Integer.parseInt(txt_quantity.getText()));
            pst.setDate(5, new java.sql.Date(date_donation.getDatoFecha().getTime()));

            pst.executeUpdate();
            con.commit(); // Lakukan commit manual
            JOptionPane.showMessageDialog(this, "Donation Successfully Added!");
            loadDonations();
            clearForm();
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // Rollback jika terjadi error
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Failed: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Kembalikan ke mode auto-commit
                    con.close(); // Tutup koneksi
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInput() {
        if (txt_donor.getText().trim().isEmpty()
                || txt_title.getText().trim().isEmpty()
                || txt_author.getText().trim().isEmpty()
                || txt_quantity.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Fill In The Blanks!");
            return false;
        }

        try {
            int qty = Integer.parseInt(txt_quantity.getText());
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity Cannot Be 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity Must Number!");
            return false;
        }

        if (date_donation.getDatoFecha() == null) { // Changed to getDatoFecha()
            JOptionPane.showMessageDialog(this, "Fill Donation Date!");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txt_donor.setText("");
        txt_title.setText("");
        txt_author.setText("");
        txt_quantity.setText("");
        date_donation.setDatoFecha(new Date()); // Changed to setDatoFecha
    }

    private void searchDonation() {
        String keyword = JOptionPane.showInputDialog(this, "Search Name Or Title:");
        if (keyword == null || keyword.trim().isEmpty()) {
            loadDonations(); // Jika kosong, tampilkan semua data
            return;
        }

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT * FROM donation WHERE donor_name LIKE ? OR book_title LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();
            model.setRowCount(0); // Clear table sebelum menampilkan hasil

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("donor_name"),
                    rs.getString("book_title"),
                    rs.getString("book_author"),
                    rs.getInt("quantity"),
                    sdf.format(rs.getDate("donation_date"))
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error While Searching: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close(); // Tutup koneksi
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_donation = new rojerusan.RSTableMetro();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_donor = new rojerusan.RSMetroTextPlaceHolder();
        jLabel18 = new javax.swing.JLabel();
        txt_title = new rojerusan.RSMetroTextPlaceHolder();
        jLabel19 = new javax.swing.JLabel();
        txt_author = new rojerusan.RSMetroTextPlaceHolder();
        txt_quantity = new rojerusan.RSMetroTextPlaceHolder();
        jLabel20 = new javax.swing.JLabel();
        date_donation = new rojeru_san.componentes.RSDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_donation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patron", "Book Tittle", "Author", "Quantity", "Donation Date"
            }
        ));
        tbl_donation.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_donation.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_donation.setColorSelBackgound(new java.awt.Color(255, 51, 51));
        tbl_donation.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_donation.setFuenteFilas(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_donation.setFuenteFilasSelect(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_donation.setFuenteHead(new java.awt.Font("Yu Gothic UI Semibold", 1, 16)); // NOI18N
        tbl_donation.setRowHeight(25);
        tbl_donation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_donationMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_donation);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 740, 250));

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel4.setText("Back");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Edit_Property_50px.png"))); // NOI18N
        jLabel2.setText("Donations");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 210, -1));

        jPanel5.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 280, 10));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 770));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Patron");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        txt_donor.setForeground(new java.awt.Color(0, 0, 0));
        txt_donor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_donor.setPhColor(new java.awt.Color(0, 0, 0));
        txt_donor.setPlaceholder("Enter Donatur Name\n");
        txt_donor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_donorActionPerformed(evt);
            }
        });
        jPanel2.add(txt_donor, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 290, -1));

        jLabel18.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Book Tittle");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, -1, -1));

        txt_title.setForeground(new java.awt.Color(0, 0, 0));
        txt_title.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_title.setPhColor(new java.awt.Color(0, 0, 0));
        txt_title.setPlaceholder("Enter Title Name");
        txt_title.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_titleActionPerformed(evt);
            }
        });
        jPanel2.add(txt_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 290, -1));

        jLabel19.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Author");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, -1));

        txt_author.setForeground(new java.awt.Color(0, 0, 0));
        txt_author.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_author.setPhColor(new java.awt.Color(0, 0, 0));
        txt_author.setPlaceholder("Enter Author Name");
        txt_author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_authorActionPerformed(evt);
            }
        });
        jPanel2.add(txt_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, 290, -1));

        txt_quantity.setForeground(new java.awt.Color(0, 0, 0));
        txt_quantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_quantity.setPhColor(new java.awt.Color(0, 0, 0));
        txt_quantity.setPlaceholder("Enter Quantity");
        txt_quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantityActionPerformed(evt);
            }
        });
        jPanel2.add(txt_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 290, -1));

        jLabel20.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Donation Date");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, -1, -1));

        date_donation.setColorBackground(new java.awt.Color(0, 0, 255));
        date_donation.setColorForeground(new java.awt.Color(0, 0, 255));
        date_donation.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        date_donation.setPlaceholder("Select Donation Date");
        jPanel2.add(date_donation, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 380, 30));

        jLabel21.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Quantity");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEARCH");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 680, 370, 30));

        jButton2.setBackground(new java.awt.Color(255, 102, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("ADD");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 560, 370, 30));

        jButton3.setBackground(new java.awt.Color(255, 102, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("CLEAR");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 620, 370, 30));

        jPanel3.setBackground(new java.awt.Color(51, 51, 255));

        jLabel1.setFont(new java.awt.Font("Segoe WP Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 570, 780));

        setBounds(0, 0, 1366, 768);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_donorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_donorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_donorActionPerformed

    private void txt_titleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_titleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_titleActionPerformed

    private void txt_authorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_authorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authorActionPerformed

    private void txt_quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityActionPerformed

    private void tbl_donationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_donationMouseClicked

    }//GEN-LAST:event_tbl_donationMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Validasi input
        if (txt_donor.getText().trim().isEmpty()
                || txt_title.getText().trim().isEmpty()
                || txt_author.getText().trim().isEmpty()
                || txt_quantity.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Fill In The Blank!");
            return;
        }

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            String sql = "INSERT INTO donation (donor_name, book_title, book_author, quantity, donation_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txt_donor.getText().trim());
            pst.setString(2, txt_title.getText().trim());
            pst.setString(3, txt_author.getText().trim());
            pst.setInt(4, Integer.parseInt(txt_quantity.getText().trim()));
            pst.setDate(5, new java.sql.Date(date_donation.getDatoFecha().getTime()));

            int rowsAffected = pst.executeUpdate();
            con.commit(); // Commit transaction

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Donation Successfully added!");
                loadDonations(); // Refresh tabel
                clearForm(); // Bersihkan form
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantity Must Number!");
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Kembalikan auto-commit
                    con.close(); // Tutup koneksi
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String keyword = JOptionPane.showInputDialog("Search For Patron Or Book Name:");

        if (keyword == null || keyword.trim().isEmpty()) {
            loadDonations(); // Jika kosong, tampilkan semua
            return;
        }

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "SELECT * FROM donation WHERE donor_name LIKE ? OR book_title LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, "%" + keyword.trim() + "%");
            pst.setString(2, "%" + keyword.trim() + "%");

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tbl_donation.getModel();
            model.setRowCount(0); // Clear existing data

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("donor_name"),
                    rs.getString("book_title"),
                    rs.getString("book_author"),
                    rs.getInt("quantity"),
                    sdf.format(rs.getDate("donation_date"))
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error While Searching " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Donation.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Donation.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Donation.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Donation.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Donation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.componentes.RSDateChooser date_donation;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private rojerusan.RSTableMetro tbl_donation;
    private rojerusan.RSMetroTextPlaceHolder txt_author;
    private rojerusan.RSMetroTextPlaceHolder txt_donor;
    private rojerusan.RSMetroTextPlaceHolder txt_quantity;
    private rojerusan.RSMetroTextPlaceHolder txt_title;
    // End of variables declaration//GEN-END:variables
}
