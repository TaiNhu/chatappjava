/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import entity.Content;
import entity.Message;
import entity.User;
import java.awt.Color;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;
import utils.Auth;
import utils.Client;
import utils.MsgBox;
import utils.XImage;

/**
 *
 * @author ACER
 */
public class ManHinhChinh extends javax.swing.JFrame {

    /**
     * Creates new form ManHinhChinh
     */
    public boolean is_connected = true;
    public Room last_room_clicked = new Room();
    public String header;
    public List<User> selectByNickName = new ArrayList();
    public List<User> selectAllFriend = new ArrayList();
    public JFileChooser FileChooser = new JFileChooser();
    public MyMessage last_file_send;
    public List<Content> contents = new ArrayList();
    public String file_size;
    public VideoCall video_call;

    public ManHinhChinh() throws IOException {
        initComponents();
        setIconImage(XImage.getAppIcon());
        setTitle("BeeChat");
        jButton3.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "jButton3");
        jButton3.getActionMap().put("jButton3", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jButton3ActionPerformed(e);
            }
        });
        setup();
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(WindowEvent winEvt) {
                is_connected = false;
                Auth.client.close();
                System.exit(0);
            }
        }
        );
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    DataOutputStream out = Auth.client.out;
                    DataInputStream in = Auth.client.in;
                    ObjectInputStream in1 = Auth.client.in1;
                    ObjectOutputStream out1 = Auth.client.out1;
                    while (is_connected) {
                        header = in.readUTF();
                        if (header.equals("get_messsage")) {
                            List<Object[]> m = (List<Object[]>) in1.readObject();
                            for (Object[] j : m) {
                                add_message(j);
                            }
                            JScrollBar vertical = jScrollPane2.getVerticalScrollBar();
                            vertical.setValue(vertical.getMaximum());
                            last_room_clicked.setMessage(((Message) m.get(m.size() - 1)[0]).getMessage());
                        } else if (header.equals("add_group")) {
                            List<Object[]> list = (List<Object[]>) in1.readObject();
                            if (list != null) {
                                for (Object[] ob : list) {
                                    add_room((byte[]) ob[2], (String) ob[1], (String) ob[3], (int) ob[0]);
                                }
                                JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
                                vertical.setValue(vertical.getMaximum());
                            }
                            Auth.trungGiang.clear();
                        } else if (header.equals("add_friend")) {
                            List<Object[]> list = (List<Object[]>) in1.readObject();
                            if (list != null) {
                                for (Object[] ob : list) {
                                    add_room((byte[]) ob[2], (String) ob[1], (String) ob[3], (int) ob[0]);
                                }
                            }
                            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
                            vertical.setValue(vertical.getMaximum());
                            Auth.trungGian.clear();
                        } else if (header.equals("selectByNickName")) {
                            selectByNickName = (List<User>) in1.readObject();
                        } else if (header.equals("selectAllFriend")) {
                            selectAllFriend = (List<User>) in1.readObject();
                        } else if (header.equals("get_id_file_message")) {
                            while (last_file_send == null) {
                                System.out.print("");
                            }
                            List<Object[]> m = (List<Object[]>) in1.readObject();
                            last_file_send.id = ((Message) m.get(0)[0]).getId();
                        } else if (header.equals("get_file")) {
                            file_size = in.readUTF();
                            contents = (List<Content>) in1.readObject();
                        }
                    }
                } catch (IOException ex) {
//                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
//                    ex.printStackTrace();
                }
            }
        });
        thread.start();
        setLocationRelativeTo(null);
    }

    void setup() throws IOException {
        ImageIcon image = new ImageIcon(Auth.user.getAvatar());
        ImageIcon i = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        jLabel1.setIcon(i);
        jLabel2.setText(Auth.user.getNick_name());
        List<Object[]> list = Auth.client.fillRoom(Auth.user.getUser_name());
        if (list != null) {
            for (Object[] ob : list) {
                add_room((byte[]) ob[2], (String) ob[0], (String) ob[3], (int) ob[1]);
            }
        }
        List<Object[]> list2 = Auth.client.fillGroup(Auth.user.getUser_name());
        if (list != null) {
            for (Object[] ob : list2) {
                add_room((byte[]) ob[2], (String) ob[1], (String) ob[3], (int) ob[0]);
            }
        }
    }

    public void add_room(byte[] avatar, String name, String message, int id) {
        jPanel10.add(Box.createVerticalStrut(10));
        Room t = new Room(avatar, name, message, id);
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel11.show(false);
                jPanel12.show(true);
                if (!last_room_clicked.equals(t)) {
                    t.setBackground(Color.cyan);
                    Auth.client.get_messsage(t.id);
                    setting_chat_page(t.avatar, t.name);
                    last_room_clicked.setBackground(Color.white);
                    last_room_clicked = t;
                }
            }
        });
        jPanel10.add(t);
        pack();
    }

    public void setting_chat_page(byte[] avatar, String name) {
        ImageIcon image = new ImageIcon(avatar);
        ImageIcon i = new ImageIcon(image.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        jLabel3.setIcon(i);
        jPanel18.removeAll();
        jLabel5.setText(name);
//        repaint();
    }

    public void add_message(Object[] b) {
        jPanel18.add(Box.createVerticalStrut(10));
        JPanel t = null;
        Message m = (Message) b[0];
        if (m.getId_room() == last_room_clicked.id) {
            if (m.getOwner().equals(Auth.user.getUser_name())) {
                t = new MyMessage(m.getId(), m.getMessage(), m.getId_category(), m.getOwner(), Auth.user.getAvatar(), this);
            } else {
                t = new YourMessage(m.getId(), m.getMessage(), m.getId_category(), m.getOwner(), ((User) b[1]).getAvatar(), this);
            }
            jPanel18.add(t);
        }
        jPanel18.repaint();
        pack();
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
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0));
        jLabel1 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0));
        jLabel2 = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0));
        jButton2 = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0));
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0));
        jLabel3 = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0));
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 15));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(500, 0));
        jButton7 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel18 = new javax.swing.JPanel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10));
        jPanel15 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1116, 540));
        setPreferredSize(new java.awt.Dimension(1116, 540));
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(1100, 32767));
        jPanel1.setMinimumSize(new java.awt.Dimension(1100, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 500));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(350, 32767));
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 0));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel4.setMaximumSize(new java.awt.Dimension(32767, 100));
        jPanel4.setPreferredSize(new java.awt.Dimension(350, 100));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMaximumSize(new java.awt.Dimension(350, 65));
        jPanel7.setMinimumSize(new java.awt.Dimension(218, 65));
        jPanel7.setPreferredSize(new java.awt.Dimension(348, 65));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));
        jPanel7.add(filler1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/avatar (2).jpg"))); // NOI18N
        jPanel7.add(jLabel1);
        jPanel7.add(filler2);

        jLabel2.setText("TaiiNhu");
        jPanel7.add(jLabel2);
        jPanel7.add(filler4);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/addfriend.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton2);
        jPanel7.add(filler3);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/group.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        jPanel6.add(jPanel7);
        jPanel6.add(jTextField1);

        jPanel4.add(jPanel6);

        jSeparator1.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(3, 32767));
        jPanel4.add(jSeparator1);

        jPanel2.add(jPanel4);

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jPanel9.setBackground(new java.awt.Color(153, 204, 255));
        jPanel9.setMaximumSize(new java.awt.Dimension(50, 32767));
        jPanel9.setPreferredSize(new java.awt.Dimension(50, 342));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/message.png"))); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/avatar (2).jpg"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/Log-Out-icon.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.add(jPanel9);

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(3, 32767));
        jPanel5.add(jSeparator2);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel10.setBackground(new java.awt.Color(204, 255, 255));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel10MousePressed(evt);
            }
        });
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanel10);

        jPanel8.add(jScrollPane1);

        jPanel5.add(jPanel8);

        jSeparator3.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setMaximumSize(new java.awt.Dimension(3, 32767));
        jPanel5.add(jSeparator3);

        jPanel2.add(jPanel5);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(750, 456545));
        jPanel3.setMinimumSize(new java.awt.Dimension(750, 10));
        jPanel3.setPreferredSize(new java.awt.Dimension(750, 10));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/HinhNenBEECHAT.jpg"))); // NOI18N
        jLabel4.setMaximumSize(new java.awt.Dimension(1100, 500));
        jLabel4.setMinimumSize(new java.awt.Dimension(1100, 500));
        jLabel4.setPreferredSize(new java.awt.Dimension(1100, 500));
        jPanel11.add(jLabel4);

        jPanel3.add(jPanel11, "card4");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setMaximumSize(new java.awt.Dimension(810, 32932));
        jPanel12.setMinimumSize(new java.awt.Dimension(810, 50));
        jPanel12.setPreferredSize(new java.awt.Dimension(810, 162));
        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel13.setBackground(new java.awt.Color(204, 255, 255));
        jPanel13.setMaximumSize(new java.awt.Dimension(32767, 100));
        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(804, 65));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));
        jPanel13.add(filler9);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/avatar (2).jpg"))); // NOI18N
        jPanel13.add(jLabel3);
        jPanel13.add(filler5);

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel5.setText("Hieu Quang");
        jPanel17.add(jLabel5);
        jPanel17.add(filler6);

        jPanel13.add(jPanel17);
        jPanel13.add(filler8);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/videocall.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton7);

        jPanel12.add(jPanel13);

        jPanel14.setMaximumSize(new java.awt.Dimension(750, 24343));
        jPanel14.setMinimumSize(new java.awt.Dimension(750, 32));
        jPanel14.setPreferredSize(new java.awt.Dimension(750, 24));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel18.add(filler7);

        jScrollPane2.setViewportView(jPanel18);

        jPanel14.add(jScrollPane2);

        jPanel12.add(jPanel14);

        jPanel15.setMaximumSize(new java.awt.Dimension(32767, 65));
        jPanel15.setOpaque(false);

        jButton3.setText("Gui");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/atachment.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/smile.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/sad.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/Pacman.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/wow.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/silent.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.add(jPanel15);

        jPanel3.add(jPanel12, "card3");

        jPanel1.add(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (!jTextField2.getText().equals("")) {
            Message m = new Message(0, jTextField2.getText(), 1, last_room_clicked.id, Auth.user.getUser_name());
            jTextField2.setText("");
            Auth.client.send_message(m);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            JDialog t = new CreateBoxChat(this, true);
            t.setVisible(true);
            if (Auth.trungGiang.size() > 2) {
                Auth.client.add_group(Auth.trungGiang, Auth.hinh, Auth.name);
            }
            Auth.trungGiang.clear();
        } catch (IOException ex) {
            Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            JDialog tk = new thongtintaikhoan(this, true);
            tk.setVisible(true);
            Auth.client.updateUser(Auth.user);
            File avatar = new File("./avatar/avatar.jpg");
            avatar.createNewFile();
            Files.write(avatar.toPath(), Auth.user.getAvatar());
            jLabel1.setIcon(new ImageIcon(XImage.read("./avatar/avatar.jpg", 50, 50)));
            jLabel2.setText(Auth.user.getNick_name());
        } catch (Exception e) {
            MsgBox.alert(this, "Khong thanh cong!");
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JDialog taicute = new AddFriend(this, true);
        taicute.setVisible(true);
        for (int i = 0; i < Auth.trungGian.size() - 1; i += 2) {
            Auth.client.add_friend((String) Auth.trungGian.get(i), (String) Auth.trungGian.get(i + 1));
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MousePressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText(jTextField2.getText() + ":)");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText(jTextField2.getText() + ":(");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText(jTextField2.getText() + ":v");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText(jTextField2.getText() + ":0");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText(jTextField2.getText() + ":|");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            Message v = new Message();
            v.setMessage(FileChooser.getSelectedFile().getName());
            v.setId_room(last_room_clicked.id);
            v.setOwner(Auth.user.getUser_name());
            v.setId_category(2);
            v.setId(0);
            jPanel18.add(Box.createVerticalStrut(10));
            MyMessage t = new MyMessage(v.getId(), v.getMessage(), v.getId_category(), v.getOwner(), Auth.user.getAvatar(), this, FileChooser.getSelectedFile().getAbsolutePath());
            last_file_send = t;
            jPanel18.add(t);
            jPanel18.repaint();
            pack();
            JScrollBar vertical = jScrollPane2.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileInputStream fileInputStream = null;
                    try {
                        File file = FileChooser.getSelectedFile();
                        fileInputStream = new FileInputStream(file.getAbsolutePath());
                        byte[] data = new byte[50000];
                        Message m = new Message(0, file.getName(), 2, last_room_clicked.id, Auth.user.getUser_name());
                        Auth.client.out.writeUTF("send_file");
                        Auth.client.out1.writeObject(m);
                        Auth.client.out.writeUTF(String.valueOf((file.length() / 50000) + 1));
                        int percent = (int) (100 / ((file.length() / 50000) + 1)) == 0 ? 1 : (int) (100 / ((file.length() / 50000) + 1));
                        t.jProgressBar1.setVisible(true);
                        while ((fileInputStream.read(data)) != -1) {
                            Auth.client.out.write(data);
                            t.jProgressBar1.setValue(t.jProgressBar1.getValue() + percent);
                            t.repaint();
                            repaint();
                            pack();
                        }
                        fileInputStream.close();
                        t.jProgressBar1.setVisible(false);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            fileInputStream.close();
                        } catch (IOException ex) {
                            Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.setVisible(false);
        new Dangnhap(this, true).setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try {
            Client a = new Client(new Socket("14.183.98.48", 11000));
            HashMap hs = new HashMap();
            hs.put("header", "connected");
            hs.put("user_name", Auth.user.getUser_name());
            hs.put("room", last_room_clicked.id);
            a.out1.writeObject(hs);
            VideoCall i = new VideoCall(a, this);
            i.add_room_video_call(new RoomVideoCall(Auth.user.getUser_name()));
            i.run_video();
            i.setVisible(true);
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(ManHinhChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManHinhChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManHinhChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManHinhChinh.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ManHinhChinh().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ManHinhChinh.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
