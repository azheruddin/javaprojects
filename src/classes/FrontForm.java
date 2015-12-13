/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class FrontForm extends javax.swing.JFrame implements Runnable {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String filename = "";
    String str1 = "";
    int categoryLength;
    int siteLength;
    String add = "";
    String dir = "";
    private InsertForm insertForm;
    private SearchUrlForm searchForm;
    private DeleteForm deleteForm;
    private UpdateForm updateform;
    private final Thread dateTimeThread;

    public FrontForm() {
        initComponents();
        
        String dir = System.getProperty("user.dir");
        setSearchFormMainForm();
        ////////////////date///////////
        dateTimeThread = new Thread(this);
        dateTimeThread.start();
        //////////////////////////////
    }

//method for import/Store CSV File to database
    public void importCsvFile() {
        int i = 0;
        try {

            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);

            if (chooser.getSelectedFile() != null) {
                File f = chooser.getSelectedFile();
                filename = f.getAbsolutePath();

                if (filename.length() == 0) {

                } else {

                    BufferedReader br = new BufferedReader(new FileReader(filename));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] val = line.split(",");    //your seperator

                        dir = System.getProperty("user.dir");
                        Class.forName("org.sqlite.JDBC");
                        conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db");
                        String sql1 = "select URL from URLINFO where URL ='" + val[0] + "'";
                        pst = conn.prepareStatement(sql1);
                        rs = pst.executeQuery();

                        if (rs.isBeforeFirst()) {
                            JOptionPane.showMessageDialog(null, "Url Already Exist", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        } else {

                            //  Class.forName("org.sqlite.JDBC");
                            //  conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db");
                            String sql = "insert into URLINFO values(NULL,'" + val[0] + "','" + val[1] + "','" + val[2] + "','" + val[3] + "','" + val[4] + "')";
                            pst = conn.prepareStatement(sql);
                            pst.executeUpdate();
                            i++;

                        }
                        conn.close();
                    }
                    br.close();

                }

            }

            JOptionPane.showMessageDialog(null, +i + " Records Import", "IMPORT CSV FILE", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("import from catch block");
        }
    }

    //for creating csv File from databse
    public void exportFromDb() {
        try {
            int j = 0;
            String dir = System.getProperty("user.dir");
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dir + "\\URLDB.db");
            String sql = "select URL,SITE,CATEGORY,DESCRIPTION,DATE from URLINFO";
            pst = conn.prepareStatement(sql);

            rs = pst.executeQuery();

            // parent component of the dialog and Jfilechooser attachment
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                FileWriter fw = new FileWriter(fileToSave.getAbsolutePath() + ".csv");
                while (rs.next()) {
                    fw.write(rs.getString(1));
                    fw.write(",");
                    fw.write(rs.getString(2));
                    fw.write(",");
                    fw.write(rs.getString(3));
                    fw.write(",");
                    fw.write(rs.getString(4));
                    fw.write(",");
                    fw.write(rs.getString(5));
                    fw.write("\n");
                    j++;
                }
                fw.close();
            }
            conn.close();
            JOptionPane.showMessageDialog(null, +j + " Records Export ", "EXPORT CSV FILE", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("from export catch");
        }
    }

    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        searchUrlMenu = new javax.swing.JMenu();
        newUrlSubmenu = new javax.swing.JMenuItem();
        seachUrlMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        importMenu = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenuItem();
        deleteMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        manualMenu = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Welcome To Url Library");

        dateLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        dateLabel.setForeground(new java.awt.Color(255, 153, 153));

        javax.swing.GroupLayout jDesktopPaneLayout = new javax.swing.GroupLayout(jDesktopPane);
        jDesktopPane.setLayout(jDesktopPaneLayout);
        jDesktopPaneLayout.setHorizontalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        jDesktopPaneLayout.setVerticalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jDesktopPane);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        searchUrlMenu.setText("FILE");

        newUrlSubmenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        newUrlSubmenu.setText("NEW URL");
        newUrlSubmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newUrlSubmenuActionPerformed(evt);
            }
        });
        searchUrlMenu.add(newUrlSubmenu);

        seachUrlMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        seachUrlMenu.setText("SEARCH URL");
        seachUrlMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachUrlMenuActionPerformed(evt);
            }
        });
        searchUrlMenu.add(seachUrlMenu);

        exitMenu.setText("EXIT");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        searchUrlMenu.add(exitMenu);

        jMenuBar1.add(searchUrlMenu);

        jMenu1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenu1.setText("RECORDS");

        importMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK));
        importMenu.setText("IMPORT");
        importMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importMenuActionPerformed(evt);
            }
        });
        jMenu1.add(importMenu);

        exportMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        exportMenu.setText("EXPORT");
        exportMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuActionPerformed(evt);
            }
        });
        jMenu1.add(exportMenu);

        deleteMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        deleteMenu.setText("CLEAR");
        deleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuActionPerformed(evt);
            }
        });
        jMenu1.add(deleteMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("HELP");

        manualMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        manualMenu.setText("MANUAL");
        manualMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualMenuActionPerformed(evt);
            }
        });
        jMenu2.add(manualMenu);

        aboutMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        aboutMenu.setText("ABOUT");
        aboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuActionPerformed(evt);
            }
        });
        jMenu2.add(aboutMenu);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setInsertFromMainForm() {
        try {
            jDesktopPane.removeAll();
            insertForm = new InsertForm(jDesktopPane);
            jDesktopPane.add(insertForm);
            insertForm.setVisible(true);
            insertForm.setPreferredSize(getMinimumSize());
            insertForm.setClosable(true);
            insertForm.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Main Form", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setSearchFormMainForm() {
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

    public void setDeleteFormMainForm() {
        try {
            jDesktopPane.removeAll();
            deleteForm = new DeleteForm(jDesktopPane);
            jDesktopPane.add(deleteForm);
            deleteForm.setVisible(true);
            deleteForm.setPreferredSize(getMinimumSize());
            deleteForm.setClosable(true);
            deleteForm.setMaximum(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Main Form", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void newUrlSubmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newUrlSubmenuActionPerformed

        try {
            setInsertFromMainForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Main Form", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_newUrlSubmenuActionPerformed

    private void exportMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuActionPerformed
        exportFromDb();
    }//GEN-LAST:event_exportMenuActionPerformed

    private void seachUrlMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachUrlMenuActionPerformed
        try {
            setSearchFormMainForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Main Form", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_seachUrlMenuActionPerformed

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        dispose();
    }//GEN-LAST:event_exitMenuActionPerformed

    private void importMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMenuActionPerformed
        importCsvFile();
    }//GEN-LAST:event_importMenuActionPerformed

    private void deleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMenuActionPerformed

        try {
            setDeleteFormMainForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Main Form", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteMenuActionPerformed

    private void aboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuActionPerformed
        new AboutForm().setVisible(true);
    }//GEN-LAST:event_aboutMenuActionPerformed

    private void manualMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualMenuActionPerformed
         try {
            Desktop.getDesktop().open(new File("" + dir + "Url_library_manual.pdf"));
            System.out.println(dir);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_manualMenuActionPerformed

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
            java.util.logging.Logger.getLogger(FrontForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrontForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrontForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrontForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenu;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JMenuItem deleteMenu;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenuItem exportMenu;
    private javax.swing.JMenuItem importMenu;
    private javax.swing.JDesktopPane jDesktopPane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem manualMenu;
    private javax.swing.JMenuItem newUrlSubmenu;
    private javax.swing.JMenuItem seachUrlMenu;
    private javax.swing.JMenu searchUrlMenu;
    // End of variables declaration//GEN-END:variables
public void run() {
        while (true) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy  'at' hh:mm:ss a");
            dateLabel.setText(sdf.format(date));
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Main Form", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
