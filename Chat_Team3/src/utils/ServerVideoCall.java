/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import DAO.ContentDAO;
import DAO.MemberDAO;
import DAO.MessageDAO;
import DAO.RoomDAO;
import DAO.ThongKeDAO;
import DAO.UserDAO;
import entity.Content;
import entity.Member;
import entity.Message;
import entity.Room;
import entity.User;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import static utils.Server.room;

/**
 *
 * @author ACER
 */
public class ServerVideoCall {

    final static int PORT = 11000;
    static HashMap<Object, Object> room = new HashMap<Object, Object>();
    static int count = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket server_socket = new ServerSocket(PORT);
            System.out.println("[SERVER IS LISTENING...]");
            while (true) {
                Socket client_socket = server_socket.accept();
                SocketClone socket = new SocketClone(client_socket);
                System.out.println("[" + client_socket.getInetAddress() + " is connected...]");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean is_connected = true;
                            String header = "";
                            ImageIcon k;
                            while (is_connected) {
                                try {
                                    while (true) {
                                        header = socket.in.readUTF();
                                        if (!header.equals("")) {
                                            break;
                                        }
                                    }
                                } catch (EOFException e) {
                                }
                                if (header.equals("disconnected")) {
                                    is_connected = false;
                                    count -= 1;
                                    for (Object t : room.keySet()) {
                                        ArrayList<SocketClone> v = (ArrayList<SocketClone>) room.get(t);
                                        for (int i = 0; i < v.size(); i++) {
                                            if (v.get(i).equals(socket)) {
                                                v.remove(i);
                                                break;
                                            }
                                        }
                                    }
                                    socket.s.close();
                                } else if (header.equals("connected")) {
                                    String user_name = socket.in.readUTF();
                                    int room1 = Integer.valueOf(socket.in.readUTF());
                                    if (room.get(room1) == null) {
                                        ArrayList<SocketClone> clients = new ArrayList<SocketClone>();
                                        clients.add(socket);
                                        room.put(room1, clients);
                                    } else {
                                        ArrayList<SocketClone> t = (ArrayList<SocketClone>) room.get(room1);
                                        t.add(socket);
                                    }
                                    for (SocketClone j : (ArrayList<SocketClone>) room.get(room1)) {
                                        System.out.println(((ArrayList) room.get(room1)).size());
                                        if (!j.equals(socket)) {
                                            try {
                                                j.out.writeUTF("add_new_room");
                                                j.out.writeUTF(user_name);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                } else if (header.equals("get_data")) {
                                    String user_name = socket.in.readUTF();
                                    int room1 = Integer.valueOf(socket.in.readUTF());
                                    k = (ImageIcon) socket.in1.readObject();
                                    List<SocketClone> s = (List<SocketClone>) room.get(room1);
                                    for (SocketClone j : s) {
                                        j.out.writeUTF("get_data");
                                        j.out.writeUTF(user_name);
                                        j.out1.writeObject(k);

                                    }
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ServerVideoCall.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("[" + client_socket.getInetAddress() + " is disconnected...]");
                    }
                }).start();
                count += 1;
                System.out.println("[Current client]: " + count);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
