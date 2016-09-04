package dbV1;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.sun.rowset.CachedRowSetImpl;

public class LibraryManagement extends javax.swing.JFrame implements Runnable {

   
    int posx,

    posy;

    static Connection conn;
    /* book table */
    String ISBN10, title,author;
    /* borrower table */
    String card_no, fname, lname, address, phone;
    /* library_branch table */
    String branch_id, branch_name;
    /* book_authors */
    String author_name;
    /* book_copies */
    String no_of_copies;
    /* book_loans */
    String date_out, due_date, ISBN;
    int linect = 0;
    String output = "";
    String available = "";
    String[] columnNames1 = {"Title", "ISBN10", "Author Name", "Branch ID","Branch Name", "Total", "Available"};
    String[] columnNames2 = {"ISBN", "Title", "Branch ID", "Date Out", "Date Due", "Fine"};

    DefaultTableModel model1, model2;
    String eno;
    int width = 800;
    int height = 500;
    String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
    String date1 = new SimpleDateFormat("yyyy-dd-MM").format(new Date());

    public LibraryManagement() {
    	setTitle("Library Management System - Mcdermott Library ");
    }

    public LibraryManagement(String text) {
        super();
        eno = text;
        initComponents();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = (screen.getWidth() - this.width) / 2;
        Double y = (screen.getHeight() - this.height) / 2;
        posx = x.intValue();
        posy = y.intValue();
        this.setLocation(posx, posy);
       
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        model1 = new DefaultTableModel();
        model1.setColumnIdentifiers(columnNames1);
        jTable1 = new javax.swing.JTable(model1);
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        model2 = new DefaultTableModel();
        model2.setColumnIdentifiers(columnNames2);
        jTable2 = new javax.swing.JTable(model2);
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel(date1);
        jButton8 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Title");

        jLabel1.setText("ISBN10");

        jLabel2.setText("Author Name");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Check Out");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(model1);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(262, 262, 262))
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(261, 261, 261)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addComponent(jTextField1)
                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGap(262, 262, 262)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(325, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Check Out", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(model2);
        jScrollPane2.setViewportView(jTable2);

        jButton3.setText("Show Borrowed Books");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Check In");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton8.setText("Pay Fine");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(186, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(147, 147, 147)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(194, 194, 194)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(164, 164, 164)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Check In", jPanel4);

        jButton7.setText("Logout");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(343, 343, 343)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(343, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(219, 219, 219)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(220, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Logout", jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int column;
        int flag = 0;
        Integer count = 0;
        conn = null;
        try {
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");
            Statement stmt = conn.createStatement();
            
            stmt.executeQuery("use library;");
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM book_loans where card_no=\"" + eno + "\";");
            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }
            System.out.print("Count is " + count);
            if (count >= 3) {
                flag = 1;
            }
            rs.close();
            conn.close();
            System.out.println("Success!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        }
        if (flag == 1) {
            JOptionPane.showMessageDialog(null, "Can borrow maximum of 3 books! Limit reached!");
        } else {
            try {

                int selectedRow = jTable1.getSelectedRow();
                int rowCount = model1.getRowCount();
                int columnCount = model1.getColumnCount();
                for (column = 0; column < columnCount; column++) {
                    System.out.println(jTable1.getValueAt(selectedRow, column) + ", ");
                }
                ISBN = (String) jTable1.getValueAt(selectedRow, 1);
                branch_id = (String) jTable1.getValueAt(selectedRow, 3);
                available = (String) jTable1.getValueAt(selectedRow, columnCount - 1);

                System.out.println();
                if (available.equals("0")) {
                    JOptionPane.showMessageDialog(null, "No copies available!!!");
                } else {
                    conn = null;
                    try {
                        String l_id = "";
                        // Create a connection to the local MySQL server, with the "company" database selected.
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");

                        // Create a SQL statement object and execute the query.
                        Statement stmt = conn.createStatement();
                        //String q;
                        stmt.executeQuery("use library;");
                        //q = "INSERT INTO book_loans VALUES(\"" + book_id + "\"," + branch_id + ",\"" + eno + "\",CURRENT_DATE(),CURRENT_DATE()+14);";
                        //System.out.println(q);
                        System.out.println(ISBN);
                        //ResultSet rs = stmt.executeQuery("SELECT b.title, b.book_id, lb.branch_id;");
                        stmt.executeUpdate("INSERT INTO book_loans(ISBN,branch_id,card_no,date_out,due_date) VALUES(\"" + ISBN + "\"," + branch_id + ",\"" + eno + "\",CURRENT_DATE(),DATE_ADD(CURRENT_DATE(), INTERVAL 14 DAY));");
                        ResultSet rs1 = stmt.executeQuery("SELECT loan_id FROM book_loans where card_no=\"" + eno + "\";");
                        while (rs1.next()) {
                            l_id = rs1.getString("loan_id");
                        }
                        stmt.executeUpdate("INSERT INTO fines VALUES(\"" + l_id + "\"," + "0.0" + ",\"" + "1\");");
                        //rs.close();
                        conn.close();
                        System.out.println("Success!!");
                    } catch (SQLException ex) {
                        System.out.println("Error in connection: " + ex.getMessage());
                    }
                }
            } catch (Exception e) {
            }
            conn = null;
            try {
                // Create a connection to the local MySQL server, with the "company" database selected.
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");

                // Create a SQL statement object and execute the query.
                Statement stmt = conn.createStatement();

                stmt.executeQuery("use library;");
               ResultSet rs = stmt.executeQuery("SELECT b.title, b.ISBN10,b.author,lb.branch_name, lb.branch_id, "
                        + "No_of_copies as Total, No_of_copies-count(bl.branch_id) as Available from book b "
                        + "inner join book_copies bc on b.ISBN10=bc.book_id "
                        /*+ "inner join authors1 ba on b.ISBN10=ba.book_id "*/
                        + "inner join library_branch lb on lb.branch_id=bc.branch_id "
                        + "left outer join book_loans bl on bl.ISBN=b.ISBN10 and bl.branch_id=lb.branch_id "
                        + "where b.ISBN10 like \"%" + jTextField1.getText()
                        + "%\" and b.author like \"%" + jTextField2.getText()
                        + "%\" and b.title like \"%" + jTextField3.getText()
                        + "%\"" + "group by b.title,lb.branch_name;");
                for (int i = model1.getRowCount() - 1; i > -1; i--) {
                    model1.removeRow(i);
                }
                
                while (rs.next()) {
                    linect++;
                    title = rs.getString("title");
                    ISBN10 = rs.getString("ISBN10");
                    author = rs.getString("author");
                    branch_id = rs.getString("branch_id");
                    branch_name = rs.getString("branch_name");
                    no_of_copies = rs.getString("Total");
                    available = rs.getString("Available");
                    model1.addRow(new Object[]{title, ISBN10,author, branch_id,branch_name, no_of_copies, available});
                    output = output + title + "\t" + ISBN10 + "\t"+author+"\t" + branch_id + "\t"+branch_name+"\t" + no_of_copies + "\t" + available + "\n";
                    System.out.print(title);
                    System.out.print("\t");
                    System.out.print(ISBN10);
                    System.out.print("\t");
                    System.out.println(author);
                    System.out.println("\t");
                    System.out.print(branch_id);
                    System.out.print("\t");
                    System.out.println(branch_name);
                    System.out.println("\t");
                    System.out.print(no_of_copies);
                    System.out.print("\t");
                    System.out.print(available);
                    System.out.print("\n");
                    System.out.println();

                } // End while(rs.next())
               
                rs.close();
                conn.close();
                System.out.println("Success!!");
            } catch (SQLException ex) {
                System.out.println("Error in connection: " + ex.getMessage());
            }
    }//GEN-LAST:event_jButton2ActionPerformed
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conn = null;
        try {
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");

            Statement stmt = conn.createStatement();

            stmt.executeQuery("use library;");
           
            ResultSet rs = stmt.executeQuery("SELECT b.title, b.ISBN10,b.author, lb.branch_id, lb.branch_name,"
                    + "No_of_copies as Total, No_of_copies-count(bl.branch_id) as Available from book b "
                    + "inner join book_copies bc on b.ISBN10=bc.book_id "
                   /* + "inner join book_authors ba on b.ISBN10=ba.book_id "*/
                    + "inner join library_branch lb on lb.branch_id=bc.branch_id "
                    + "left outer join book_loans bl on bl.ISBN=b.ISBN10 and bl.branch_id=lb.branch_id "
                    + "where b.ISBN10 like \"%" + jTextField1.getText()
                    + "%\" and b.author like \"%" + jTextField2.getText()
                    + "%\" and b.title like \"%" + jTextField3.getText()
                    + "%\"" + "group by b.title,lb.branch_name;");
            for (int i = model1.getRowCount() - 1; i > -1; i--) {
                model1.removeRow(i);
            }
            // Iterate through the result set.
            while (rs.next()) {
            
                linect++;
                title = rs.getString("title");
                ISBN10 = rs.getString("ISBN10");
                author = rs.getString("author");
                branch_id = rs.getString("branch_id");
                branch_name = rs.getString("branch_name");
                no_of_copies = rs.getString("Total");
                available = rs.getString("Available");
                model1.addRow(new Object[]{title, ISBN10,author, branch_id,branch_name, no_of_copies, available});
                output = output + title + "\t" + ISBN10 + "\t" +author+"\t"+ branch_id + "\t" +branch_name+"\t"+ no_of_copies + "\t" + available + "\n";
                System.out.print(title);
                System.out.print("\t");
                System.out.print(ISBN10);
                System.out.print("\t");
                System.out.println(author);
                System.out.print("\t");
                System.out.print(branch_id);
                System.out.print("\t");
                System.out.println(branch_name);
                System.out.print("\t");
                System.out.print(no_of_copies);
                System.out.print("\t");
                System.out.print(available);
                System.out.print("\n");
                System.out.println();

            } // End while(rs.next())
          
            rs.close();
            conn.close();
            System.out.println("Success!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        int n = JOptionPane.showConfirmDialog(
                this,
                "Do you want to logout?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            OptionLogin page3 = new OptionLogin();
            page3.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        int selectedRow = jTable2.getSelectedRow();
        String fine = "";
        conn = null;
        ISBN = (String) jTable2.getValueAt(selectedRow, 0);
        branch_id = (String) jTable2.getValueAt(selectedRow, 2);
        fine = (String) jTable2.getValueAt(selectedRow, 5);
        System.out.println(ISBN);
        System.out.println(branch_id);
        if (Double.parseDouble(fine) == 0.0) {
            model2.removeRow(jTable2.getSelectedRow());
            try {
                // Create a connection to the local MySQL server, with the "company" database selected.
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");

                // Create a SQL statement object and execute the query.
                Statement stmt = conn.createStatement();

                stmt.executeQuery("use library;");
                String query = "DELETE FROM book_loans WHERE ISBN ='" + ISBN +"'" + "AND branch_id="+ branch_id + " AND card_no='" + eno +"';";
                System.out.println(" delete query : " + query);
                stmt.executeUpdate(query);
              

                conn.close();
                System.out.println("Success!!");
            } catch (SQLException ex) {
                System.out.println("Error in connection: " + ex.getMessage());
            }
            String date_out = "", date_due = "";
            conn = null;
          try {
           
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");
                Statement stmt = conn.createStatement();

                stmt.executeQuery("use library;");
                ResultSet rs = stmt.executeQuery("SELECT ISBN,title,branch_id,date_out,due_date,Loan_id from book_loans,book WHERE book.ISBN10 = book_loans.ISBN AND card_no='" + eno + "';");
                for (int i = model2.getRowCount() - 1; i > -1; i--) {
                    model2.removeRow(i);
                }
        
                while (rs.next()) {
              
                    linect++;
                    ISBN = rs.getString("ISBN");
                    title = rs.getString("title");
                    branch_id = rs.getString("branch_id");
                    date_out = rs.getString("date_out");
                    date_due = rs.getString("due_date");
                    model2.addRow(new Object[]{ISBN, title, branch_id, date_out, date_due, fine});
                    output = output + ISBN + "\t" + title + "\t" + branch_id + "\t" + date_out + "\t" + date_due + "\n";

                    System.out.print(ISBN);
                    System.out.print("\t");
                    System.out.print(title);
                    System.out.print("\t");
                    System.out.print(branch_id);
                    System.out.print("\t");
                    System.out.print(date_out);
                    System.out.print("\t");
                    System.out.print(date_due);
                    System.out.print("\n");
                    System.out.println();

                } // End while(rs.next())
                rs.close();
                conn.close();
                System.out.println("Success!!");
            } catch (SQLException ex) {
                System.out.println("Error in connection: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pay fine before returning book!");
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    	String ISBN = "";
        String date_out = "", date_due = "", fine = "";
        String l_id = "", pay_flag = "";
       
        conn = null;
      
        try {
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");
            Statement stmt = conn.createStatement();

            stmt.executeQuery("use library;");

            ResultSet rs = stmt.executeQuery("SELECT ISBN,title,branch_id,date_out,due_date,Loan_id from book_loans,book WHERE book.ISBN10 = book_loans.ISBN AND card_no='" + eno + "';");
            CachedRowSet rowset;
            rowset = new CachedRowSetImpl();
            rowset.populate(rs);
            for (int i = model2.getRowCount() - 1; i > -1; i--) {
                model2.removeRow(i);
            }
      
            while (rowset.next()) 
            {
             
                linect++;
              
                ISBN = rowset.getString("ISBN");
                System.out.println(ISBN);
                title = rowset.getString("title");
                branch_id = rowset.getString("branch_id");
                date_out = rowset.getString("date_out");
                date_due = rowset.getString("due_date");
                l_id = rowset.getString("loan_id");

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = format.parse(date_due);
                Date date2 = new Date();
                if (date1.compareTo(date2) <= 0) {

                    ResultSet rs1 = stmt.executeQuery("SELECT paid FROM fines WHERE loan_id=\"" + l_id + "\";");
                    while (rs1.next()) {
                        pay_flag = rs1.getString("paid");
                    }
             
                    if (Integer.parseInt(pay_flag) == 1) {
                        int diffInDays = (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
                    
                        Double f = -0.25 * diffInDays;
                        fine = f.toString();

                        stmt.executeUpdate("UPDATE fines SET fine_amt=" + f + "WHERE loan_id=\"" + l_id + "\";");

                    } else {
                        stmt.executeUpdate("UPDATE book_loans SET due_date = DATE_ADD(CURRENT_DATE(), INTERVAL 14 DAY) WHERE loan_id=\"" + l_id + "\";");
                        stmt.executeUpdate("UPDATE fines SET paid=1 WHERE loan_id=\"" + l_id + "\";");
                        fine = "0.0";
                    }
                } else {
                    fine = "0.0";
        
                }

                model2.addRow(new Object[]{ISBN, title, branch_id, date_out, date_due, fine});
                output = output + ISBN + "\t" + title + "\t" + branch_id + "\t" + date_out + "\t" + date_due + "\t" + fine + "\n";

                System.out.print(ISBN);
                System.out.print("\t");
                System.out.print(title);
                System.out.print("\t");
                System.out.print(branch_id);
                System.out.print("\t");
                System.out.print(date_out);
                System.out.print("\t");
                System.out.print(date_due);
                System.out.print("\n");
                System.out.println();

            } // End while(rs.next())
            // Always close the recordset and connection.
            rs.close();
            rowset.close();
            conn.close();
            System.out.println("Success!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(LibraryManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String l_id = "", check_book = "", check_branch = "";
        int selectedRow = jTable2.getSelectedRow();
        String fine = "";
        conn = null;
        fine = (String) jTable2.getValueAt(selectedRow, 5);
        check_branch = (String) jTable2.getValueAt(selectedRow, 2);
        check_book = (String) jTable2.getValueAt(selectedRow, 0);
        if (Double.parseDouble(fine) > 0.0) {
            JOptionPane.showMessageDialog(null, "Fine Paid! Money debited from student's Comet Card!");

            String date_out = "", date_due = "";
            conn = null;
            try {
         
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");

                Statement stmt = conn.createStatement();

                stmt.executeQuery("use library;");

                ResultSet rs = stmt.executeQuery("SELECT ISBN,title,branch_id,date_out,due_date,Fine_amt from book_loans,book,fines WHERE book.ISBN10 = book_loans.ISBN AND FINES.Loan_id = book_loans.Loan_id AND card_no='" + eno + "';");
                for (int i = model2.getRowCount() - 1; i > -1; i--) {
                    model2.removeRow(i);
                }
                CachedRowSet rowset = new CachedRowSetImpl();
                rowset.populate(rs);
                while (rowset.next()) {
                    linect++;
                    ISBN = rowset.getString("ISBN");
                    title = rowset.getString("title");
                    branch_id = rowset.getString("branch_id");
                    date_out = rowset.getString("date_out");
                    date_due = rowset.getString("due_date");
                    
                    if ((ISBN.equals(check_book)) && (branch_id.equals(check_branch))) {
                        System.out.println("current: " + ISBN + "\ncurrent: " + branch_id + "\n\ncheck: " + check_branch + "\ncheck: " + check_book);
                        ResultSet rs1 = stmt.executeQuery("SELECT loan_id FROM book_loans where ISBN=\"" + ISBN + "\" AND branch_id=\"" + branch_id + "\";");
                        while (rs1.next()) {
                            l_id = rs1.getString("loan_id");
                        }
                        stmt.executeUpdate("UPDATE fines SET paid=0 WHERE loan_id=\"" + l_id + "\";");
                        fine = "0.0";
                    }
                    model2.addRow(new Object[]{ISBN, title, branch_id, date_out, date_due, fine});
                    output = output + ISBN + "\t" + title + "\t" + branch_id + "\t" + date_out + "\t" + date_due + "\t" + fine + "\n";

                    System.out.print(ISBN);
                    System.out.print("\t");
                    System.out.print(title);
                    System.out.print("\t");
                    System.out.print(branch_id);
                    System.out.print("\t");
                    System.out.print(date_out);
                    System.out.print("\t");
                    System.out.print(date_due);
                    System.out.print("\n");
                    System.out.println();

                } // End while(rs.next())
                rowset.close();
                conn.close();
                System.out.println("Success!!");
            } catch (SQLException ex) {
                System.out.println("Error in connection: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LibraryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LibraryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LibraryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LibraryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibraryManagement("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
