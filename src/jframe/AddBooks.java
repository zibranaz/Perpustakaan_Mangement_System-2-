/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Fahri
 */
public class AddBooks extends javax.swing.JFrame {

    /**
     * Creates new form ManageBooks
     */
    String book_name, author;
    int book_id, quantity;
    DefaultTableModel model;

    public AddBooks() {
        initComponents();
        setBookDetailsToTable();
    }

    // to set book details into the table
    public void setBookDetailsToTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from book_details");

            while (rs.next()) {
                String bookId = rs.getString("book_id");
                String bookName = rs.getString("book_name");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");

                Object[] obj = {bookId, bookName, author, quantity};
                model = (DefaultTableModel) tbl_bookDetails.getModel();
                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //to add book details table
    public boolean addBook() {
        if (!validateInputs()) {
            return false; // Langsung return jika validasi gagal
        }
        boolean isAdded = false;
        book_id = Integer.parseInt(txt_bookId.getText());
        book_name = txt_bookName.getText();
        author = txt_authorName.getText();
        quantity = Integer.parseInt(txt_quantity.getText());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into book_details values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(2, book_name);
            pst.setString(3, author);
            pst.setInt(4, quantity);
            pst.setInt(1, book_id);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isAdded = true;
            } else {
                isAdded = false;
            }
        } catch (Exception e) {

        }
        return isAdded;
    }

    //to update book details
    public boolean updateBook() {
        if (!validateInputs()) {
            return false; // Stop jika validasi gagal
        }
        boolean isUpdate = false;
        book_id = Integer.parseInt(txt_bookId.getText());
        book_name = txt_bookName.getText();
        author = txt_authorName.getText();
        quantity = Integer.parseInt(txt_quantity.getText());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "update book_details set book_name = ?,author = ?,quantity = ? where book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, book_name);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.setInt(4, book_id);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdate = true;
            } else {
                isUpdate = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    private void refreshTableData() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM book_details ORDER BY book_id"; // Sesuaikan dengan struktur table Anda
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        // Ambil model dari table Anda (ganti jTable1 dengan nama table Anda)
        DefaultTableModel model = (DefaultTableModel) tbl_bookDetails.getModel();
        
        // Clear semua data yang ada di table
        model.setRowCount(0);
        
        // Tambahkan data baru dari database
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("book_id"),           // Kolom 1
                rs.getString("book_title"),     // Kolom 2  
                rs.getString("author"),         // Kolom 3
                rs.getString("publisher"),      // Kolom 4
                rs.getInt("quantity")           // Kolom 5
                // Tambahkan kolom lainnya sesuai struktur table Anda
            });
        }
        
        rs.close();
        pst.close();
        con.close();
        
    } catch (SQLException e) {
        System.err.println("Error refreshing table: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Gagal memuat ulang data table", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    //method to delete book details
    private void searchBook() {
        String keyword = JOptionPane.showInputDialog(this, "Cari buku (judul/penulis):");
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM book_details WHERE book_name LIKE ? OR author LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();
            clearTable();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("book_id"),
                    rs.getString("book_name"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

//method to clear table
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_bookDetails.getModel();
        model.setRowCount(0);

    }

    // Tambahkan validasi sebelum operasi database
    public boolean validateInputs() {
        if (txt_bookId.getText().trim().isEmpty()
                || txt_bookName.getText().trim().isEmpty()
                || txt_authorName.getText().trim().isEmpty()
                || txt_quantity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return false;
        }

        try {
            Integer.parseInt(txt_quantity.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity harus angka!");
            return false;
        }

        return true;
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
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_bookId = new rojerusan.RSMetroTextPlaceHolder();
        txt_bookName = new rojerusan.RSMetroTextPlaceHolder();
        txt_authorName = new rojerusan.RSMetroTextPlaceHolder();
        txt_quantity = new rojerusan.RSMetroTextPlaceHolder();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        search = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bookDetails = new rojerusan.RSTableMetro();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, -1, -1));

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Enter Book ID ");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        jLabel16.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Quantity");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        jLabel17.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Enter Book Name");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, -1));

        jLabel19.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel20.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Author Name");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        jLabel21.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        txt_bookId.setBackground(new java.awt.Color(102, 102, 255));
        txt_bookId.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_bookId.setPhColor(new java.awt.Color(255, 255, 255));
        txt_bookId.setPlaceholder("Enter Book ID");
        txt_bookId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookIdActionPerformed(evt);
            }
        });
        jPanel1.add(txt_bookId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 290, -1));

        txt_bookName.setBackground(new java.awt.Color(102, 102, 255));
        txt_bookName.setForeground(new java.awt.Color(255, 255, 255));
        txt_bookName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_bookName.setPhColor(new java.awt.Color(255, 255, 255));
        txt_bookName.setPlaceholder("Enter Book Name");
        txt_bookName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_bookName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 290, -1));

        txt_authorName.setBackground(new java.awt.Color(102, 102, 255));
        txt_authorName.setForeground(new java.awt.Color(255, 255, 255));
        txt_authorName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_authorName.setPhColor(new java.awt.Color(255, 255, 255));
        txt_authorName.setPlaceholder("Enter Author Name");
        txt_authorName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_authorNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_authorName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 290, -1));

        txt_quantity.setBackground(new java.awt.Color(102, 102, 255));
        txt_quantity.setForeground(new java.awt.Color(255, 255, 255));
        txt_quantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_quantity.setPhColor(new java.awt.Color(255, 255, 255));
        txt_quantity.setPlaceholder("Enter Quantity");
        txt_quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantityActionPerformed(evt);
            }
        });
        jPanel1.add(txt_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 290, -1));

        jButton3.setBackground(new java.awt.Color(255, 102, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("ADD");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, 130, 40));

        jButton4.setBackground(new java.awt.Color(255, 102, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("UPDATE");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 540, 130, 40));

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel3.setText("Back");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 50));

        search.setBackground(new java.awt.Color(255, 102, 0));
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setText("SEARCH");
        search.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        jPanel1.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 610, 130, 40));

        jButton6.setBackground(new java.awt.Color(255, 102, 0));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("DELETE");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 610, 130, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 460, 980));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe WP Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 30, 30));

        tbl_bookDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Name", "Author", "Quantity"
            }
        ));
        tbl_bookDetails.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_bookDetails.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_bookDetails.setColorSelBackgound(new java.awt.Color(255, 51, 51));
        tbl_bookDetails.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_bookDetails.setFuenteFilas(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_bookDetails.setFuenteFilasSelect(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        tbl_bookDetails.setFuenteHead(new java.awt.Font("Yu Gothic UI Semibold", 1, 16)); // NOI18N
        tbl_bookDetails.setRowHeight(25);
        tbl_bookDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bookDetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bookDetails);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 520, 160));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel2.setText("Manage Books");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 210, -1));

        jPanel5.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 280, 5));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, 610, 690));

        setSize(new java.awt.Dimension(1067, 690));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_bookIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookIdActionPerformed

    private void txt_bookNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookNameActionPerformed

    private void txt_authorNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_authorNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authorNameActionPerformed

    private void txt_quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (addBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Added");
            clearTable();
            setBookDetailsToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Book Addition Failed");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (updateBook() == true) {
            JOptionPane.showMessageDialog(this, "Book Updated");
            clearTable();
            setBookDetailsToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Book Updating Failed");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void tbl_bookDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bookDetailsMouseClicked
        int rowNo = tbl_bookDetails.getSelectedRow();
        TableModel model = tbl_bookDetails.getModel();

        txt_bookId.setText(model.getValueAt(rowNo, 0).toString());
        txt_bookName.setText(model.getValueAt(rowNo, 1).toString());
        txt_authorName.setText(model.getValueAt(rowNo, 2).toString());
        txt_quantity.setText(model.getValueAt(rowNo, 3).toString());
    }//GEN-LAST:event_tbl_bookDetailsMouseClicked

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        String keyword = JOptionPane.showInputDialog(this, "Masukkan judul/penulis buku:");

        if (keyword != null && !keyword.trim().isEmpty()) {
            try {
                Connection con = DBConnection.getConnection(); // Pastikan DBConnection class ada
                String sql = "SELECT * FROM book_details WHERE book_name LIKE ? OR author LIKE ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "%" + keyword + "%");
                pst.setString(2, "%" + keyword + "%");

                ResultSet rs = pst.executeQuery();

                // Clear tabel dan tampilkan hasil
                clearTable();
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("book_id"),
                        rs.getString("book_name"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                    });
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saat mencari: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_searchActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

    if (txt_bookId.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Book ID harus diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
        txt_bookId.requestFocus();
        return;
    }
    
    int book_id;
    
    // Validasi input - cek apakah numeric
    try {
        book_id = Integer.parseInt(txt_bookId.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Book ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        txt_bookId.requestFocus();
        txt_bookId.selectAll();
        return;
    }
    
    // Validasi input - cek apakah positif
    if (book_id <= 0) {
        JOptionPane.showMessageDialog(this, "Book ID harus angka positif!", "Error", JOptionPane.ERROR_MESSAGE);
        txt_bookId.requestFocus();
        txt_bookId.selectAll();
        return;
    }
    
    // Konfirmasi sebelum menghapus
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Apakah Anda yakin ingin menghapus buku dengan ID: " + book_id + "?", 
        "Konfirmasi Hapus", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE);
    
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }
    
    // Database operation
    Connection con = null;
    PreparedStatement pst = null;
    
    try {
        con = DBConnection.getConnection();
        
        // Cek apakah buku ada sebelum menghapus
        String checkSql = "SELECT COUNT(*) FROM book_details WHERE book_id = ?";
        pst = con.prepareStatement(checkSql);
        pst.setInt(1, book_id);
        ResultSet rs = pst.executeQuery();
        
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        pst.close();
        
        if (count == 0) {
            JOptionPane.showMessageDialog(this, 
                "Buku dengan ID " + book_id + " tidak ditemukan!", 
                "Data Tidak Ditemukan", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Hapus buku
        String deleteSql = "DELETE FROM book_details WHERE book_id = ?";
        pst = con.prepareStatement(deleteSql);
        pst.setInt(1, book_id);
        int rowCount = pst.executeUpdate();
        
        if (rowCount > 0) {
            JOptionPane.showMessageDialog(this, 
                "Buku dengan ID " + book_id + " berhasil dihapus!", 
                "Hapus Berhasil", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form setelah berhasil
            clearForm();
            
            // REFRESH TABLE - INI YANG PENTING!
            refreshTableData();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Gagal menghapus buku!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (SQLException e) {
        System.err.println("Database error: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Terjadi kesalahan database:\n" + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}

// Method untuk clear form
private void clearForm() {
    txt_bookId.setText("");
    txt_bookId.requestFocus();
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(AddBooks.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBooks.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBooks.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBooks.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddBooks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton search;
    private rojerusan.RSTableMetro tbl_bookDetails;
    private rojerusan.RSMetroTextPlaceHolder txt_authorName;
    private rojerusan.RSMetroTextPlaceHolder txt_bookId;
    private rojerusan.RSMetroTextPlaceHolder txt_bookName;
    private rojerusan.RSMetroTextPlaceHolder txt_quantity;
    // End of variables declaration//GEN-END:variables
}
