/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import com.github.sarxos.webcam.Webcam;
import entity.User;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ImageIcon;
import utils.Auth;
import utils.Client;

/**
 *
 * @author ACER
 */
public class VideoCall extends javax.swing.JFrame {

    /**
     * Creates new form VideoCall
     */
    public List<RoomVideoCall> user_in_call = new ArrayList<RoomVideoCall>();
    public Client c;
    BufferedImage br;
    Webcam cam;
    ImageIcon ic;
    boolean is_connected = true;
    boolean run = true;
    HashMap hs;
    ManHinhChinh parent;

    public VideoCall(Client c, ManHinhChinh parent) {
        initComponents();
        this.parent = parent;
        this.c = c;
        cam = Webcam.getDefault();
        cam.open();
        hs = new HashMap();
        setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            
            public void windowClosing(WindowEvent winEvt) {
                try {
                    is_connected = false;
                    run = false;
                    cam.close();
                    hs.put("header", "disconnected");
                    c.out1.writeObject(hs);
                    c.client.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        );
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }

    public void add_room_video_call(RoomVideoCall v) {
        jPanel1.add(Box.createHorizontalStrut(3));
        jPanel1.add(v);
        user_in_call.add(v);
    }

    public void run_video() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageIcon cj;
                HashMap hs;
                while (is_connected) {
                    try {
                        hs = (HashMap) c.in1.readObject();
                        if (hs.get("header").equals("get_data")) {
                            for (RoomVideoCall r : user_in_call) {
                                cj = (ImageIcon) hs.get("image");
                                String user_name = (String) hs.get("user_name");
                                r.setImage(cj, user_name);
                            }
                        } else if (hs.get("header").equals("add_new_room")) {
                            add_room_video_call(new RoomVideoCall((String) hs.get("user_name")));
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap hs = new HashMap();
                while (run) {
                    try {
                        br = cam.getImage();
                        hs.put("header", "get_data");
                        hs.put("user_name", Auth.user.getUser_name());
                        hs.put("room", parent.last_room_clicked.id);
                        ic = new ImageIcon(br);
                        hs.put("image", ic);
                        c.out1.writeObject(hs);
                        br.flush();
                        user_in_call.get(0).setImage(ic, Auth.user.getUser_name());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 300));
        setPreferredSize(new java.awt.Dimension(700, 300));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setMaximumSize(new java.awt.Dimension(4314324, 41424123));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 235));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 235));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VideoCall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VideoCall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VideoCall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VideoCall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                try {
//                    Client client = new Client(new Socket("localhost", 11000));
//                    Auth.user = new User();
//                    Auth.user.setUser_name("tainhu");
//                    HashMap hs = new HashMap();
//                    hs.put("header", "connected");
//                    hs.put("user_name", Auth.user.getUser_name());
//                    hs.put("room", 1);
//                    client.out1.writeObject(hs);
//                    VideoCall i = new VideoCall(client);
//                    i.add_room_video_call(new RoomVideoCall("tainhu"));
//                    i.run_video();
//                    i.setVisible(true);
//                } catch (IOException ex) {
//                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
