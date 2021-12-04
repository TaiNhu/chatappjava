/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Views;

import entity.User;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utils.Auth;
import utils.XImage;

/**
 *
 * @author ntc10
 */
public class CreateBoxChat extends javax.swing.JDialog {

    byte[] data;

    /**
     * Creates new form CreateBoxChat
     */
    ManHinhChinh parent;

    public CreateBoxChat(ManHinhChinh parent, boolean modal) throws IOException {
        super(parent, modal);
        initComponents();
        setTitle("Tạo nhóm");
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(parent);
        this.parent = parent;
        Auth.trungGiang.add(Auth.user.getUser_name());
        Auth.client.selectAllFriend(Auth.user.getUser_name());
        while (parent.selectAllFriend.isEmpty()) {
            System.out.print("");
        }
        List<User> list = parent.selectAllFriend;
        parent.selectAllFriend = new ArrayList();
        if (list != null) {
            for (User ob : list) {
                add_room((byte[]) ob.getAvatar(), (String) ob.getUser_name(), (String) ob.getNick_name());
            }
        }
    }

    public void add_room(byte[] avatar, String name, String nick_name) {
        jPanel3.add(Box.createVerticalStrut(10));
        jPanel3.add(new Group(avatar, name, nick_name));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        txtNameBox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTaonhom = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setMaximumSize(new java.awt.Dimension(50, 50));
        jPanel4.setMinimumSize(new java.awt.Dimension(50, 50));

        lblAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/Add_Nhom.png"))); // NOI18N
        lblAvatar.setMaximumSize(new java.awt.Dimension(50, 50));
        lblAvatar.setMinimumSize(new java.awt.Dimension(50, 50));
        lblAvatar.setPreferredSize(new java.awt.Dimension(50, 50));
        lblAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvatarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAvatarMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane2.setViewportView(jPanel3);

        jPanel2.add(jScrollPane2);

        txtNameBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameBoxFocusLost(evt);
            }
        });

        jLabel1.setText("Bạn bè hiện có:");

        btnTaonhom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/Ok-icon.png"))); // NOI18N
        btnTaonhom.setText("Tạo nhóm");
        btnTaonhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaonhomActionPerformed(evt);
            }
        });

        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/THOAT.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNameBox))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 288, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTaonhom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThoat)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtNameBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaonhom)
                    .addComponent(btnThoat))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvatarMouseClicked
        // TODO add your handling code here:
        try {
            this.selectImage();
        } catch (IOException ex) {
            Logger.getLogger(dangki.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblAvatarMouseClicked

    private void lblAvatarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvatarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAvatarMouseEntered

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        try {
            new ManHinhChinh().setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(CreateBoxChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void txtNameBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameBoxFocusLost
        // TODO add your handling code here:
        if (txtNameBox.getText().strip().equals("")) {
            txtNameBox.setText("   Nhập tên nhóm...");
        }
        txtNameBox.setForeground(Color.gray);
    }//GEN-LAST:event_txtNameBoxFocusLost

    private void txtNameBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameBoxFocusGained
        // TODO add your handling code here:
        if (txtNameBox.getText().strip().equals("Nhập tên nhóm...")) {
            txtNameBox.setText("");
        }
        txtNameBox.setForeground(Color.black);
    }//GEN-LAST:event_txtNameBoxFocusGained

    private void btnTaonhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaonhomActionPerformed
        // TODO add your handling code here:
        Auth.name = txtNameBox.getText();
        this.dispose();
    }//GEN-LAST:event_btnTaonhomActionPerformed

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
            java.util.logging.Logger.getLogger(CreateBoxChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateBoxChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateBoxChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateBoxChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    CreateBoxChat dialog = new CreateBoxChat(new javax.swing.JFrame(), true);
//                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                        @Override
//                        public void windowClosing(java.awt.event.WindowEvent e) {
//                            System.exit(0);
//                        }
//                    });
//                    dialog.setVisible(true);
//                } catch (IOException ex) {
//                    Logger.getLogger(CreateBoxChat.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }

    JFileChooser FileChooser = new JFileChooser();

    void selectImage() throws IOException {
        if (FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            if (XImage.save(file)) {
                // Hiển thị hình lên form 
                this.data = Files.readAllBytes(file.toPath());
                Auth.hinh = this.data;
                lblAvatar.setIcon(new ImageIcon(XImage.read(file.getPath(), 50, 50)));
//                lblhinhanh.setToolTipText(file.getName());
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTaonhom;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JTextField txtNameBox;
    // End of variables declaration//GEN-END:variables
}
