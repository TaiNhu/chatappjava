/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import utils.Auth;
import utils.XImage;

/**
 *
 * @author ACER
 */
public class Group extends javax.swing.JPanel {

    /**
     * Creates new form Room
     */
    public String user_name;
    public byte[] avatar;
    public String email;
    public String nick_name;

    public Group(byte[] avatar, String name, String nick_name) {
        super();
        initComponents();
        this.user_name = name;
        this.email = email;
        this.avatar = avatar;
        this.nick_name = nick_name;
        ImageIcon image = new ImageIcon(avatar);
        ImageIcon i = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        lblAvatar.setIcon(i);
        lblTen.setText(nick_name);
        lblTinNhanCuoi.setText(name);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(32767, 0));
        lblAvatar = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 0), new java.awt.Dimension(7, 0));
        jPanel1 = new javax.swing.JPanel();
        lblTen = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 15));
        lblTinNhanCuoi = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(32767, 0));
        jCheckBox1 = new javax.swing.JCheckBox();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(32767, 0));

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(290, 52));
        setMinimumSize(new java.awt.Dimension(290, 52));
        setPreferredSize(new java.awt.Dimension(290, 52));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        add(filler4);

        lblAvatar.setText("Avatar");
        lblAvatar.setMaximumSize(new java.awt.Dimension(50, 50));
        lblAvatar.setMinimumSize(new java.awt.Dimension(50, 50));
        lblAvatar.setPreferredSize(new java.awt.Dimension(50, 50));
        add(lblAvatar);
        add(filler2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(431443, 43423423));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        lblTen.setText("Ten");
        jPanel1.add(lblTen);
        jPanel1.add(filler1);

        lblTinNhanCuoi.setText("tentk");
        jPanel1.add(lblTinNhanCuoi);

        add(jPanel1);
        add(filler5);

        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        add(jCheckBox1);
        add(filler6);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        if(jCheckBox1.isSelected()){
            Auth.trungGiang.add(lblTinNhanCuoi.getText());
        } else{
            Auth.trungGiang.remove(lblTinNhanCuoi.getText());
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTinNhanCuoi;
    // End of variables declaration//GEN-END:variables
}
