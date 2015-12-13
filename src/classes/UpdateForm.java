/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;



import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classes.FrontForm;
import static classes.SearchUrlForm.id;
import java.text.SimpleDateFormat;

public class UpdateForm extends javax.swing.JInternalFrame implements Runnable {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String str1 = "";
    private String dir;
    private javax.swing.JDesktopPane jDesktopPane;
    private SearchUrlForm searchForm;
    private Thread dateTimeThread;

    public UpdateForm() {

        initComponents();

        // Date date = new Date();
        //  dateTimeThread = new Thread(this);
        dateTimeThread = new Thread(this);
        dateTimeThread.start();
        dir = System.getProperty("user.dir");
       try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db");
            String sql = "select * from URLINFO where ID= '" + id + "'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUrl.setText(rs.getString("URL"));
                txtSite.setText(rs.getString("SITE"));
                txtCategory.setText(rs.getString("CATEGORY"));
                txtDescription.setText(rs.getString("DESCRIPTION").replaceAll("_", "\n").replaceAll(";", ","));
            }
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public UpdateForm(javax.swing.JDesktopPane obj) {
        this();
        jDesktopPane = obj;

    }
    /*
     public void saveToDb() {
     String replStr = txtDescription.getText().replaceAll("(\n)+", "_").replaceAll(",", ";");
     try {
     if (txtUrl.getText().equals("")) {
     JOptionPane.showMessageDialog(null, "Url cannot be blank", "Alert", JOptionPane.INFORMATION_MESSAGE);
     } else if (txtUrl.getText().equals("")) {
     JOptionPane.showMessageDialog(null, "Url cannot be blank", "Alert", JOptionPane.INFORMATION_MESSAGE);
     } else if (txtSite.getText().equals("")) {
     JOptionPane.showMessageDialog(null, "Please Enter Site", "Alert", JOptionPane.INFORMATION_MESSAGE);
     } else if (txtCategory.getText().equals("")) {
     JOptionPane.showMessageDialog(null, "Please Enter Category", "Alert", JOptionPane.INFORMATION_MESSAGE);
     } else if (txtDescription.getText().equals("")) {
     JOptionPane.showMessageDialog(null, "Please Write Some Description", "Alert", JOptionPane.INFORMATION_MESSAGE);
     } else {
     Class.forName("org.sqlite.JDBC");
     conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db"); 
     String sqldelete = "delete from URLINFO where ID= '" + id + "'";
     pst = conn.prepareStatement(sqldelete);
     pst.executeUpdate();
           
     PreparedStatement pst1 = conn.prepareStatement("insert into URLINFO values(NULL,?,?,?,?,?)");
     pst1.setString(1, txtUrl.getText());
     pst1.setString(2, txtSite.getText());
     pst1.setString(3, txtCategory.getText());
     //pst1.setString(4, txtDescription.getText());
     pst1.setString(4,replStr);
     pst1.setString(5, dateLabel.getText());
     pst1.executeUpdate();
     JOptionPane.showMessageDialog(null, "Record Update Successfull", "New", JOptionPane.INFORMATION_MESSAGE);
     conn.close();
     backToSearch();
     }
            
            
     } catch (Exception e) {
     System.out.println(e);

     }
     }
     
     */

    public void update() {
        String replStr = txtDescription.getText().replaceAll("(\n)+", "_").replaceAll(",", ";");
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db");
            if (txtUrl.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Url cannot be blank", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } else if (txtUrl.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Url cannot be blank", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } else if (txtSite.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Site", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } else if (txtCategory.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Category", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } else if (txtDescription.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please Write Some Description", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String sqlUpdate = "UPDATE URLINFO set  URL=? , SITE= ?, CATEGORY=?, DESCRIPTION=?, DATE=? where ID = '" + id + "' ";
                PreparedStatement pst1 = conn.prepareStatement(sqlUpdate);
                pst1.setString(1, txtUrl.getText());
                pst1.setString(2, txtSite.getText());
                pst1.setString(3, txtCategory.getText());
                pst1.setString(4, replStr);
                pst1.setString(5, dateLabel.getText());
                pst1.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Update Successfull", "New", JOptionPane.INFORMATION_MESSAGE);
                conn.close();
                backToSearch();
            }

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void backToSearch() {
        try {

            jDesktopPane.removeAll();
            searchForm = new SearchUrlForm(jDesktopPane);
            jDesktopPane.add(searchForm);
            searchForm.setVisible(true);
            searchForm.setPreferredSize(getMinimumSize());
            searchForm.setClosable(true);
            searchForm.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Main Form", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void reset() {
        txtUrl.setText("");
        txtSite.setText("");
        txtCategory.setText("");
        txtDescription.setText("");
    }

    private String getClipboardData() {

        String strRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null) {
            return "";
        } else {
            try {

                str1 = (String) t.getTransferData(DataFlavor.stringFlavor);

            } catch (Exception e) {
                e.printStackTrace();
            }//try

            return str1;

        }

    }//onPaste

    public void getValueToCompare() {
        try {
            final String dir = System.getProperty("user.dir");
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db");
            str1 = getClipboardData();
            String sql = "select URL from URLINFO where URL ='" + str1 + "' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println("url name:" + rs.getString("URL"));

            conn.close();
        } catch (Exception e) {
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        txtCategory = new javax.swing.JTextField();
        txtSite = new javax.swing.JTextField();
        txtUrl = new javax.swing.JTextField();
        btnPasteUrl = new javax.swing.JButton();
        dateLabel = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("UPDATE URL");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setForeground(new java.awt.Color(255, 153, 153));
        jLabel1.setText("URL");

        jLabel2.setForeground(new java.awt.Color(255, 153, 153));
        jLabel2.setText("SITE");

        jLabel3.setForeground(new java.awt.Color(255, 153, 153));
        jLabel3.setText("CATEGORY");

        jLabel4.setForeground(new java.awt.Color(255, 153, 153));
        jLabel4.setText("DESCRIPTION");
        jLabel4.setToolTipText("");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        txtUrl.setEditable(false);

        btnPasteUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnPasteUrl.setText("Paste Url");
        btnPasteUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasteUrlActionPerformed(evt);
            }
        });

        dateLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(255, 153, 153));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clear.PNG"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.PNG"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClose.setText("Back");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(204, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(123, 123, 123)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCategory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSite, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtUrl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnPasteUrl)
                            .addGap(27, 27, 27)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPasteUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(txtSite, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(131, 131, 131)
                            .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(31, 31, 31)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(73, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPasteUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasteUrlActionPerformed
        str1 = getClipboardData();
        Pattern p = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher m;
        m = p.matcher(str1);
        if (m.matches()) {

            txtUrl.setText(getClipboardData());
        } else {
            JOptionPane.showMessageDialog(null, "Url is Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnPasteUrlActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
        //      saveToDb();

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        backToSearch();
    }//GEN-LAST:event_btnCloseActionPerformed

    public String getId() {
        return id;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPasteUrl;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtSite;
    private javax.swing.JTextField txtUrl;
    // End of variables declaration//GEN-END:variables
public void run() {

        while (true) {
            Date date = new Date();

            //formatting time to have AM/PM text using 'a' format
            //  String strDateFormat = "HH:mm:ss a";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy  'at' hh:mm:ss a");
            dateLabel.setText(sdf.format(date)); //(new Date().toString().replace("PST", " "));
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Main Form", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
