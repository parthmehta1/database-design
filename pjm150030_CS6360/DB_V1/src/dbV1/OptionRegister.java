package dbV1;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;


public class OptionRegister extends javax.swing.JFrame {

    
   int flag, flag2;
   int posx, posy;
    int width = 500;
    int height = 500;
    static Connection conn;
    String card_no, fname, lname, address, phone,ssn;

    public OptionRegister() {
        initComponents();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = (screen.getWidth() - this.width) / 2;
        Double y = (screen.getHeight() - this.height) / 2;
        posx = x.intValue();
        posy = y.intValue();
        this.setSize(550, 550);
        this.setLocation(posx, posy);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        jButton1.setText("Sign Up");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Phone Number");

        jLabel3.setText("Address");

        jLabel2.setText("Last Name");

        jLabel1.setText("First Name");
        
        JLabel lblSsn = new JLabel();
        lblSsn.setText("SSN");
        
        textField = new JTextField();
        
        lblCity = new JLabel();
        lblCity.setText("City");
        
        textField_1 = new JTextField();
        
        lblEmailId = new JLabel();
        lblEmailId.setText("Email ID");
        
        textField_2 = new JTextField();
        
        lblState = new JLabel();
        lblState.setText("State");
        
        textField_3 = new JTextField();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(151)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addComponent(lblState, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
        							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addComponent(jLabel4)
        							.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
        							.addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
        							.addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblCity)
        								.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
        							.addGap(89)
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
        								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
        								.addComponent(jLabel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        								.addComponent(lblSsn, Alignment.LEADING))
        							.addPreferredGap(ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addComponent(lblEmailId, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
        								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))))
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        					.addGap(174)))
        			.addGap(319))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(61)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSsn)
        				.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel1)
        				.addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(22)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel2)
        				.addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel3)
        				.addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCity)
        				.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(19)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblState)
        				.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel4)
        				.addComponent(jTextField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblEmailId)
        				.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
        			.addGap(82))
        );
        jPanel1.setLayout(jPanel1Layout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addGap(19)
        			.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        LoginAndRegister page1 = new LoginAndRegister();
        page1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conn = null;
        flag = 0;
        flag2 = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "pm2197");

            if (textField.getText().equals("") || jTextField5.getText().equals("") || jTextField6.getText().equals("") || jTextField7.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "First Name, Last Name, SSN and Address are mandatory!!!");
            } else {
                Statement stmt = conn.createStatement();

                stmt.executeQuery("use library;");

                ResultSet rs = stmt.executeQuery("SELECT * FROM borrower;");
                Random rand = new Random();
                Integer r = rand.nextInt(1000) + 1;
                while (rs.next()) {

                    String card_no, first_name, last_name, address, phone,ssn,city,state,email;
                    card_no = rs.getString("card_no");
                    first_name = rs.getString("first_name");
                    last_name = rs.getString("last_name");
                    address = rs.getString("address");
                    phone = rs.getString("phone");
                    ssn = rs.getString("ssn");
                    city = rs.getString("city");
                    state = rs.getString("state");
                    email = rs.getString("email");
                    if ((ssn.equals(textField.getText()) && fname.equals(jTextField5.getText())) && (lname.equals(jTextField6.getText())) && (address.equals(jTextField7.getText()))) {
                        flag = 1;
                        if (r.toString().equals(card_no)) {
                            flag2 = 1;
                        }
                        break;
                    }
                }
               
                if (flag == 1) {
                    JOptionPane.showMessageDialog(null, "User already exists!!! One user is allowed only one card!!!");

                } else {
                    if (flag2 != 1) {
                        stmt.executeUpdate("INSERT INTO borrower VALUES(\"" + r.toString() + "\",\""+textField.getText() 
                        + "\",\"" + jTextField5.getText() + "\",\"" + jTextField6.getText() + "\",\"" +textField_2.getText() 
                        + "\",\"" + jTextField7.getText() + "\",\"" + jTextField8.getText() + "\", \""+ textField_1.getText() 
                        +"\",\"" + textField_3.getText() + "\");");
                    }
                    JOptionPane.showMessageDialog(null, "User successfully created!!! Your Card ID is " + r.toString());
                    dispose();
                    OptionLogin page3 = new OptionLogin();
                    page3.setVisible(true);
                    this.dispose();
                }
                rs.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        }
    }

    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OptionRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OptionRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OptionRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OptionRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OptionRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private JTextField textField;
    private JLabel lblCity;
    private JTextField textField_1;
    private JLabel lblEmailId;
    private JTextField textField_2;
    private JLabel lblState;
    private JTextField textField_3;
}
