/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jframe;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fahri
 */
public class AuthorList extends javax.swing.JFrame {

    /**
     * Creates new form ManageBooks
     */
    String author_name, book_name, address, email;
    int author_id;
    DefaultTableModel model;

    public AuthorList() {
        initComponents();
        setauthorlistToTable();
    }

    // to set book details into the table
    public void setauthorlistToTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from author_list");

            while (rs.next()) {
                int authorid = rs.getInt("author_id");
                String authorname = rs.getString("author_name");
                String bookname = rs.getString("book_name");
                String address = rs.getString("address");
                String email = rs.getString("email");

                Object[] obj = {authorid, authorname, bookname, address, email};
                model = (DefaultTableModel) authorlist.getModel();
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
        author_name = authorname_txt.getText();
        book_name = bookname_txt.getText();
        address = address_txt.getText();
        email = email_txt.getText();
        author_id = Integer.parseInt(author_txt.getText());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "insert into author_list values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(2, author_name);
            pst.setString(3, book_name);
            pst.setString(4, address);
            pst.setString(5, email);
            pst.setInt(1, author_id);


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
        author_name = authorname_txt.getText();
        book_name = bookname_txt.getText();
        address = address_txt.getText();
        email = email_txt.getText();
        author_id = Integer.parseInt(author_txt.getText());

        try {
            Connection con = DBConnection.getConnection();;
            String sql = "update author_list set author_name = ?,book_name = ?,address = ?, email = ? where author_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, author_name);
            pst.setString(2, book_name);
            pst.setString(3, address);
            pst.setString(4, email);
            pst.setInt(5, author_id);

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
        String sql = "SELECT * FROM author_list ORDER BY author_id"; 
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        // Ambil model dari table Anda (ganti jTable1 dengan nama table Anda)
        DefaultTableModel model = (DefaultTableModel) authorlist.getModel();
        
        // Clear semua data yang ada di table
        model.setRowCount(0);
        
        // Tambahkan data baru dari database
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("author_id"),           // Kolom 1
                rs.getString("author_name"),     // Kolom 2  
                rs.getString("book_name"),         // Kolom 3
                rs.getString("address"),      // Kolom 4
                rs.getInt("email")           // Kolom 5
                
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
       
    private void searchBook() {
        String keyword = JOptionPane.showInputDialog(this, "Cari buku (judul/penulis):");
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM author_list WHERE book_name LIKE ? OR author_id LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();
            clearTable();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("author_id"),
                    rs.getString("author_name"),
                    rs.getString("book_name"),
                    rs.getString("address"),
                    rs.getString("email")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

//method to clear table
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) authorlist.getModel();
        model.setRowCount(0);

    }

    // Tambahkan validasi sebelum operasi database
    public boolean validateInputs() {
        if (author_txt.getText().trim().isEmpty()
                || authorname_txt.getText().trim().isEmpty()
                || bookname_txt.getText().trim().isEmpty()
                || email_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
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
        authorname_txt = new rojerusan.RSMetroTextPlaceHolder();
        bookname_txt = new rojerusan.RSMetroTextPlaceHolder();
        email_txt = new rojerusan.RSMetroTextPlaceHolder();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        address_txt = new rojerusan.RSMetroTextPlaceHolder();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        author_txt = new rojerusan.RSMetroTextPlaceHolder();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        authorlist = new rojerusan.RSTableMetro();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        search = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        search1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        jLabel15.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 255));
        jLabel15.setText("Author ID ");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel16.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 255));
        jLabel16.setText("Email");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, -1, -1));

        jLabel17.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel18.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 255));
        jLabel18.setText("Book Name");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, -1, -1));

        jLabel19.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel20.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 255));
        jLabel20.setText("Author Name");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        jLabel21.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, -1, -1));

        authorname_txt.setBackground(new java.awt.Color(102, 102, 255));
        authorname_txt.setForeground(new java.awt.Color(255, 255, 255));
        authorname_txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        authorname_txt.setPhColor(new java.awt.Color(255, 255, 255));
        authorname_txt.setPlaceholder("Enter Author Name");
        authorname_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorname_txtActionPerformed(evt);
            }
        });
        jPanel1.add(authorname_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 290, -1));

        bookname_txt.setBackground(new java.awt.Color(102, 102, 255));
        bookname_txt.setForeground(new java.awt.Color(255, 255, 255));
        bookname_txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bookname_txt.setPhColor(new java.awt.Color(255, 255, 255));
        bookname_txt.setPlaceholder("Enter Book Name");
        bookname_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookname_txtActionPerformed(evt);
            }
        });
        jPanel1.add(bookname_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 290, -1));

        email_txt.setBackground(new java.awt.Color(102, 102, 255));
        email_txt.setForeground(new java.awt.Color(255, 255, 255));
        email_txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        email_txt.setPhColor(new java.awt.Color(255, 255, 255));
        email_txt.setPlaceholder("Enter Email");
        email_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                email_txtActionPerformed(evt);
            }
        });
        jPanel1.add(email_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, 290, -1));

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

        address_txt.setBackground(new java.awt.Color(102, 102, 255));
        address_txt.setForeground(new java.awt.Color(255, 255, 255));
        address_txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        address_txt.setPhColor(new java.awt.Color(255, 255, 255));
        address_txt.setPlaceholder("Enter Address");
        address_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                address_txtActionPerformed(evt);
            }
        });
        jPanel1.add(address_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 290, -1));

        jLabel22.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 255));
        jLabel22.setText("Address");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, -1, -1));

        jLabel23.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        author_txt.setBackground(new java.awt.Color(102, 102, 255));
        author_txt.setForeground(new java.awt.Color(255, 255, 255));
        author_txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        author_txt.setPhColor(new java.awt.Color(255, 255, 255));
        author_txt.setPlaceholder("Enter Author ID");
        author_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                author_txtActionPerformed(evt);
            }
        });
        jPanel1.add(author_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 290, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 980));

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));
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
                .addContainerGap(8, Short.MAX_VALUE)
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

        authorlist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Author ID", "Author Name", "Book", "Address", "Email"
            }
        ));
        authorlist.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        authorlist.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        authorlist.setColorSelBackgound(new java.awt.Color(255, 51, 51));
        authorlist.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        authorlist.setFuenteFilas(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        authorlist.setFuenteFilasSelect(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        authorlist.setFuenteHead(new java.awt.Font("Yu Gothic UI Semibold", 1, 16)); // NOI18N
        authorlist.setRowHeight(25);
        authorlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                authorlistMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(authorlist);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 600, 160));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel2.setText("Author List");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 210, -1));

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

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        search.setBackground(new java.awt.Color(255, 102, 0));
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setText("SEARCH");
        search.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        jPanel3.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 130, 40));

        jButton4.setBackground(new java.awt.Color(255, 102, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("UPDATE");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 460, 130, 40));

        jButton3.setBackground(new java.awt.Color(255, 102, 0));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("ADD");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 460, 130, 40));

        search1.setBackground(new java.awt.Color(255, 102, 0));
        search1.setForeground(new java.awt.Color(255, 255, 255));
        search1.setText("DELETE");
        search1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search1ActionPerformed(evt);
            }
        });
        jPanel3.add(search1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 130, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 630, 690));

        setSize(new java.awt.Dimension(1070, 690));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void author_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_author_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_author_txtActionPerformed

    private void authorname_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorname_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorname_txtActionPerformed

    private void bookname_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookname_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookname_txtActionPerformed

    private void email_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_email_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_email_txtActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (addBook() == true) {
            JOptionPane.showMessageDialog(this, "Author Added");
            clearTable();
            setauthorlistToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Author Addition Failed");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (updateBook() == true) {
            JOptionPane.showMessageDialog(this, "Author Updated");
            clearTable();
            setauthorlistToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Author Updating Failed");
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

    private void authorlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_authorlistMouseClicked
        int rowNo = authorlist.getSelectedRow();
        TableModel model = authorlist.getModel();

        author_txt.setText(model.getValueAt(rowNo, 0).toString());
        authorname_txt.setText(model.getValueAt(rowNo, 1).toString());
        bookname_txt.setText(model.getValueAt(rowNo, 2).toString());
        address_txt.setText(model.getValueAt(rowNo, 3).toString());
        email_txt.setText(model.getValueAt(rowNo, 4).toString());
    }//GEN-LAST:event_authorlistMouseClicked

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        String keyword = JOptionPane.showInputDialog(this, "Masukkan Author ID:");

        if (keyword != null && !keyword.trim().isEmpty()) {
            try {
                Connection con = DBConnection.getConnection(); // Pastikan DBConnection class ada
                String sql = "SELECT * FROM author_list WHERE book_name LIKE ? OR author_id LIKE ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "%" + keyword + "%");
                pst.setString(2, "%" + keyword + "%");

                ResultSet rs = pst.executeQuery();

                // Clear tabel dan tampilkan hasil
                clearTable();
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("book_name"),
                        rs.getString("address"),
                        rs.getString("email"),
                    });
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saat mencari: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_searchActionPerformed

    private void address_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_address_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_address_txtActionPerformed

    private void search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search1ActionPerformed
 
    if (author_txt.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Book ID harus diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
        author_txt.requestFocus();
        return;
    }
    
    int author_id;
    
    // Validasi input - cek apakah numeric
    try {
        author_id = Integer.parseInt(author_txt.getText().trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Book ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        author_txt.requestFocus();
        author_txt.selectAll();
        return;
    }
    
    // Validasi input - cek apakah positif
    if (author_id <= 0) {
        JOptionPane.showMessageDialog(this, "Book ID harus angka positif!", "Error", JOptionPane.ERROR_MESSAGE);
        author_txt.requestFocus();
        author_txt.selectAll();
        return;
    }
    
    
    // Konfirmasi sebelum menghapus
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Apakah Anda yakin ingin menghapus buku dengan ID: " + author_id + "?", 
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
        String checkSql = "SELECT COUNT(*) FROM author_list WHERE author_id = ?";
        pst = con.prepareStatement(checkSql);
        pst.setInt(1, author_id);
        ResultSet rs = pst.executeQuery();
        
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        pst.close();
        
        if (count == 0) {
            JOptionPane.showMessageDialog(this, 
                "Buku dengan ID " + author_id + " tidak ditemukan!", 
                "Data Tidak Ditemukan", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Hapus buku
        String deleteSql = "DELETE FROM author_list WHERE author_id = ?";
        pst = con.prepareStatement(deleteSql);
        pst.setInt(1, author_id);
        int rowCount = pst.executeUpdate();
        
        if (rowCount > 0) {
            JOptionPane.showMessageDialog(this, 
                "Buku dengan ID " + author_id + " berhasil dihapus!", 
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
    author_txt.setText("");
   author_txt.requestFocus();
    }//GEN-LAST:event_search1ActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AuthorList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMetroTextPlaceHolder address_txt;
    private rojerusan.RSMetroTextPlaceHolder author_txt;
    private rojerusan.RSTableMetro authorlist;
    private rojerusan.RSMetroTextPlaceHolder authorname_txt;
    private rojerusan.RSMetroTextPlaceHolder bookname_txt;
    private rojerusan.RSMetroTextPlaceHolder email_txt;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton search;
    private javax.swing.JButton search1;
    // End of variables declaration//GEN-END:variables
}
