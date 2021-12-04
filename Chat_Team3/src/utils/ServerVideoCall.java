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
import static utils.Server.room;

/**
 *
 * @author ACER
 */
public class ServerVideoCall {

    final static int PORT = 11000;
    static HashMap<Object, Object> room = new HashMap<Object, Object>();
    static int count = 0;
    static HashMap header;
    static HashMap a = new HashMap();

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
                            while (is_connected) {
                                try {
                                    while (true) {
                                        header = (HashMap) socket.in1.readObject();
                                        if (header != null) {
                                            break;
                                        }
                                    }
                                } catch (EOFException e) {
                                }
                                if (header.get("header").equals("disconnected")) {
                                    System.out.println("tai");
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
                                    header.clear();
                                } else if (!header.get("header").equals("disconnected")) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (header.get("header").equals("connected")) {
                                                if (room.get(header.get("room")) == null) {
                                                    ArrayList<SocketClone> clients = new ArrayList<SocketClone>();
                                                    clients.add(socket);
                                                    room.put(header.get("room"), clients);
                                                } else {
                                                    ArrayList<SocketClone> t = (ArrayList<SocketClone>) room.get(header.get("room"));
                                                    t.add(socket);
                                                }
                                                for (SocketClone j : (ArrayList<SocketClone>) room.get(header.get("room"))) {
                                                    if (!j.equals(socket)) {
                                                        try {
                                                            a.clear();
                                                            a.put("header", "add_new_room");
                                                            a.put("user_name", header.get("user_name"));
                                                            j.out1.writeObject(a);
                                                        } catch (IOException ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    }

                                                }
                                            } else if (header.get("header").equals("get_data")) {
                                                HashMap data = header;
                                                List<SocketClone> s = (List<SocketClone>) room.get(data.get("room"));
                                                for (SocketClone j : s) {
                                                    if (!j.equals(socket)) {
                                                        try {
                                                            a.clear();
                                                            a.put("header", "get_data");
                                                            a.put("user_name", data.get("user_name"));
                                                            a.put("image", data.get("image"));
                                                            j.out1.writeObject(a);
                                                        } catch (IOException ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }).start();
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                socket.s.close();
                                socket.in.close();
                                socket.in1.close();
                                socket.out.close();
                                socket.out1.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
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
