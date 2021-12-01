/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import entity.Content;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import utils.XImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import utils.Auth;

/**
 *
 * @author ACER
 */
public class MyMessage extends javax.swing.JPanel {

    /**
     * Creates new form MyMessage
     */
    public int id;
    public String message;
    public int id_category;
    public int id_members;
    public String owner;
    public javax.swing.JProgressBar jProgressBar1;
    ManHinhChinh parent;

    public MyMessage(int id, String message, int id_category, String owner, String avatar, ManHinhChinh parent) {
        initComponents();
        this.parent = parent;
        this.id = id;
        this.message = message;
        this.id_category = id_category;
        this.owner = owner;
        jLabel1.setToolTipText(this.owner);
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel4.add(jProgressBar1);
        jProgressBar1.setVisible(false);
        if (this.id_category == 2) {
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/download.png")));
            jLabel4.setText(this.message);
            Pattern pattern = Pattern.compile(".+\\.jpg$|.+\\.png$");
            Matcher matcher = pattern.matcher(this.message);
            if (matcher.find()) {
                while (this.id == 0) {
                    System.out.print("");
                }
                try {
                    Auth.client.out.writeUTF("get_file");
                    Auth.client.out.writeUTF(String.valueOf(this.id));
                    String s = Auth.client.in.readUTF();
                    int size1 = Integer.valueOf(Auth.client.in.readUTF());
                    byte[] data = new byte[size1];
                    List<Content> contents = (List<Content>) Auth.client.in1.readObject();
                    System.arraycopy(contents.get(0).getContent(), 0, data, 0, contents.get(0).getContent().length);
                    int size = contents.get(0).getContent().length;
                    for (int i = 1; i < contents.size(); i++) {
                        System.arraycopy(contents.get(i).getContent(), 0, data, size, contents.get(i).getContent().length);
                        size += contents.get(i).getContent().length;
                    }
                    ImageIcon image = new ImageIcon(data);
                    ImageIcon i = new ImageIcon(image.getImage().getScaledInstance(250, 150, Image.SCALE_DEFAULT));
                    jLabel2.setIcon(i);
                    setMaximumSize(new Dimension(700, 300));
                    setMinimumSize(new Dimension(700, 300));
                    setPreferredSize(new Dimension(700, 300));
                    jPanel1.setMaximumSize(new Dimension(700, 300));
                    jPanel1.setMinimumSize(new Dimension(700, 300));
                    jPanel1.setPreferredSize(new Dimension(700, 300));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (this.id_category == 1) {
            convert_string(message);
        }
        jLabel1.setIcon(new ImageIcon(XImage.read(avatar, 50, 50)));
    }

    public MyMessage(int id, String message, int id_category, String owner, String avatar, ManHinhChinh parent, String anh) {
        initComponents();
        this.parent = parent;
        this.id = id;
        this.message = message;
        this.id_category = id_category;
        this.owner = owner;
        jLabel1.setToolTipText(this.owner);
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel4.add(jProgressBar1);
        jProgressBar1.setVisible(false);
        if (this.id_category == 2) {
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/download.png")));
            jLabel4.setText(this.message);
            Pattern pattern = Pattern.compile(".+\\.jpg$|.+\\.png$");
            Matcher matcher = pattern.matcher(this.message);
            if (matcher.find()) {
                ImageIcon image = new ImageIcon(anh);
                ImageIcon i = new ImageIcon(image.getImage().getScaledInstance(250, (int) ((300.0 / image.getIconWidth()) * image.getIconHeight()), Image.SCALE_DEFAULT));
                jLabel2.setIcon(i);
                setMaximumSize(new Dimension(700, 300));
                setMinimumSize(new Dimension(700, 300));
                setPreferredSize(new Dimension(700, 300));
                jPanel1.setMaximumSize(new Dimension(700, 300));
                jPanel1.setMinimumSize(new Dimension(700, 300));
                jPanel1.setPreferredSize(new Dimension(700, 300));
            }
        } else if (this.id_category == 1) {
            convert_string(message);
        }
        jLabel1.setIcon(new ImageIcon(XImage.read(avatar, 50, 50)));
    }

    MyMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    protected void paintComponent(Graphics g) {
        // call super.paintComponent to get default Swing 
        // painting behavior (opaque honored, etc.)
    }

    private void convert_string(String message) {
        Pattern pattern = Pattern.compile(":\\)|:v|:\\(|:0|:\\|");
        Matcher matcher = pattern.matcher(message);
        List<JLabel> l = new ArrayList<JLabel>();
        while (matcher.find()) {
            if (matcher.group().equals(":)")) {
                JLabel d = new JLabel();
                d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/smile.png")));
                l.add(d);
            } else if (matcher.group().equals(":(")) {
                JLabel d = new JLabel();
                d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/sad.png")));
                l.add(d);
            } else if (matcher.group().equals(":v")) {
                JLabel d = new JLabel();
                d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/Pacman.png")));
                l.add(d);
            } else if (matcher.group().equals(":0")) {
                JLabel d = new JLabel();
                d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/wow.png")));
                l.add(d);
            } else if (matcher.group().equals(":|")) {
                JLabel d = new JLabel();
                d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/silent.png")));
                l.add(d);
            }
        }
        String[] s = message.split(":\\)|:v|:\\(|:0|:\\|");
        int size = (s.length < l.size() ? l.size() : s.length);
        for (int i = 0; i < size; i++) {
            try {
                jPanel6.add(new JLabel(s[i]));
            } catch (Exception e) {
//                e.printStackTrace();
            }
            try {
                jPanel6.add(l.get(i));
            } catch (Exception e) {
//                e.printStackTrace();
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 3), new java.awt.Dimension(3, 3), new java.awt.Dimension(10, 10));
        jLabel3 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 3), new java.awt.Dimension(3, 3), new java.awt.Dimension(10, 10));
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 3), new java.awt.Dimension(10, 3), new java.awt.Dimension(10, 3));
        jPanel4 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(32767, 0));
        jLabel1 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(32767, 0));

        setMaximumSize(new java.awt.Dimension(700, 100));
        setMinimumSize(new java.awt.Dimension(700, 100));
        setPreferredSize(new java.awt.Dimension(700, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setMaximumSize(new java.awt.Dimension(640, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(640, 100));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 100));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(filler4);
        jPanel1.add(jLabel3);
        jPanel1.add(filler3);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(431423, 432142));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(43214324, 43214324));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel6.setMaximumSize(new java.awt.Dimension(4321432, 4321432));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setText(" ");
        jPanel6.add(jLabel4);

        jPanel5.add(jPanel6);

        jLabel2.setText(" ");
        jPanel5.add(jLabel2);
        jPanel5.add(filler5);

        jPanel2.add(jPanel5);

        jPanel3.add(jPanel2);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(jPanel4);

        jPanel1.add(jPanel3);

        add(jPanel1);
        add(filler1);

        jLabel1.setText("Avatar");
        jLabel1.setMaximumSize(new java.awt.Dimension(50, 50));
        jLabel1.setMinimumSize(new java.awt.Dimension(50, 50));
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 50));
        add(jLabel1);
        add(filler2);
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (id != 0 && id_category == 2) {
            try {
                // TODO add your handling code here:
                Auth.client.out.writeUTF("get_file");
                Auth.client.out.writeUTF(String.valueOf(id));
                while (parent.contents.isEmpty()) {
                    System.out.print("");
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jProgressBar1.setVisible(true);
                            File file = new File("./" + message);
                            file.createNewFile();
                            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                            int percent = 100 / (parent.contents.size());
                            for (Content y : parent.contents) {
                                fos.write(y.getContent());
                                jProgressBar1.setValue(jProgressBar1.getValue() + percent);
                                repaint();
                                parent.repaint();
                                parent.pack();
                            }
                            fos.close();
                            parent.file_size = null;
                            parent.contents = new ArrayList();
                            jProgressBar1.setVisible(false);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
