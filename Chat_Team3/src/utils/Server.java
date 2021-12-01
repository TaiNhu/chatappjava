/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.*;
import java.io.IOException;
import static java.lang.System.in;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.net.URL;
import DAO.*;
import entity.User;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;
import entity.*;

/**
 *
 * @author ACER
 */
public class Server {

    final static int PORT = 10000;
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
                            while (is_connected) {
                                String header = "";
                                try {
                                    while (true) {
                                        header = socket.in.readUTF();
                                        if (!header.equals("")) {
                                            break;
                                        }
                                    }
                                } catch (EOFException e) {
                                }
                                if (header.equals("login")) {
                                    String user = socket.in.readUTF();
                                    String pass = socket.in.readUTF();
                                    User entity = new UserDAO().selectByIdAndPass(user, pass);
                                    String address = getAddress.get_ip_Details(client_socket.getInetAddress().toString());
                                    new UserDAO().updateAddress(user, pass, address);
                                    socket.out1.writeObject(entity);
                                } else if (header.equals("registration")) {
                                    User entity = (User) socket.in1.readObject();
                                    boolean ok = new UserDAO().insert(entity);
                                    socket.out.writeBoolean(ok);
                                } else if (header.equals("getPass")) {
                                    String email = socket.in.readUTF();
                                    String t = new UserDAO().getPass(email);
                                    if (t == null) {
                                        socket.out.writeUTF("null");
                                    } else {
                                        socket.out.writeUTF(t);
                                    }
                                } else if (header.equals("fillRoom")) {
                                    String name = socket.in.readUTF();
                                    ResultSet r = XJdbc.query("{call all_room(?)}", name);
                                    List list = new ArrayList();
                                    while (r.next()) {
                                        Object[] o = new Object[]{
                                            r.getObject(1),
                                            r.getObject(2),
                                            r.getObject(3),
                                            r.getObject(4)
                                        };
                                        if (room.get(r.getObject(2)) == null) {
                                            ArrayList<SocketClone> clients = new ArrayList<SocketClone>();
                                            clients.add(socket);
                                            room.put(r.getObject(2), clients);
                                        } else {
                                            ArrayList<SocketClone> t = (ArrayList<SocketClone>) room.get(r.getObject(2));
                                            t.add(socket);
                                        }
                                        list.add(o);
                                    }
                                    socket.out1.writeObject(list);
                                } else if (header.equals("updateUser")) {
                                    User entity = (User) socket.in1.readObject();
                                    boolean rs = new UserDAO().update(entity);
                                    socket.out.writeBoolean(rs);
                                } else if (header.equals("selectByNickName")) {
                                    String nick_name = socket.in.readUTF();
                                    List<User> list = new UserDAO().selectByNickName(nick_name);
                                    socket.out.writeUTF("selectByNickName");
                                    socket.out1.writeObject(list);
                                } else if (header.equals("add_friend")) {
                                    String name = socket.in.readUTF();
                                    String friend_name = socket.in.readUTF();
                                    new RoomDAO().insert_room();
                                    int id_room = (int) XJdbc.value("select max(id) from rooms");
                                    new MemberDAO().insert(new Member(id_room, name));
                                    new MemberDAO().insert(new Member(id_room, friend_name));
                                    int id_member = new MemberDAO().selectByIdRoomUserName(id_room, name);
                                    new MessageDAO().insert(new Message(0, "hello", 1, id_member));
                                    new MessageDAO().insert(new Message(0, "hello", 1, id_member + 1));
                                    ResultSet r = XJdbc.query("{call add_friend(?,?)}", name, id_room);
                                    List list = new ArrayList();
                                    while (r.next()) {
                                        Object[] o = new Object[]{
                                            r.getObject(1),
                                            r.getObject(2),
                                            r.getObject(3),
                                            r.getObject(4)
                                        };
                                        list.add(o);
                                    }
                                    if (room.get(id_room) == null) {
                                        ArrayList<SocketClone> clients = new ArrayList<SocketClone>();
                                        clients.add(socket);
                                        room.put(id_room, clients);
                                    } else {
                                        ArrayList<SocketClone> t = (ArrayList<SocketClone>) room.get(id_room);
                                        t.add(socket);
                                    }
                                    socket.out.writeUTF("add_friend");
                                    socket.out1.writeObject(list);
                                } else if (header.equals("add_group")) {
                                    List names = (List) socket.in1.readObject();
                                    String name = socket.in.readUTF();
                                    int size = Integer.valueOf(socket.in.readUTF());
                                    byte[] hinh = new byte[size];
                                    socket.in.read(hinh);
                                    new RoomDAO().insert(new Room(name, true, hinh));
                                    int id_room = (int) XJdbc.value("select max(id) from rooms");
                                    for (int i = 0; i < names.size(); i++) {
                                        new MemberDAO().insert(new Member(id_room, (String) names.get(i)));
                                        int id_member = new MemberDAO().selectByIdRoomUserName(id_room, (String) names.get(i));
                                        new MessageDAO().insert(new Message(0, "hello", 1, id_member));
                                    }
                                    ResultSet r = XJdbc.query("{call add_group(?)}", id_room);
                                    List list = new ArrayList();
                                    while (r.next()) {
                                        Object[] o = new Object[]{
                                            r.getObject(1),
                                            r.getObject(2),
                                            r.getObject(3),
                                            r.getObject(4)
                                        };
                                        list.add(o);
                                    }
                                    if (room.get(id_room) == null) {
                                        ArrayList<SocketClone> clients = new ArrayList<SocketClone>();
                                        clients.add(socket);
                                        room.put(id_room, clients);
                                    } else {
                                        ArrayList<SocketClone> t = (ArrayList<SocketClone>) room.get(id_room);
                                        t.add(socket);
                                    }
                                    socket.out.writeUTF("add_group");
                                    socket.out1.writeObject(list);
                                } else if (header.equals("fillGroup")) {
                                    String name = socket.in.readUTF();
                                    ResultSet r = XJdbc.query("{call all_group(?)}", name);
                                    List list = new ArrayList();
                                    while (r.next()) {
                                        Object[] o = new Object[]{
                                            r.getObject(1),
                                            r.getObject(2),
                                            r.getObject(3),
                                            r.getObject(4)
                                        };
                                        if (room.get(r.getObject(1)) == null) {
                                            ArrayList<SocketClone> clients = new ArrayList<SocketClone>();
                                            clients.add(socket);
                                            room.put(r.getObject(1), clients);
                                        } else {
                                            ArrayList<SocketClone> t = (ArrayList<SocketClone>) room.get(r.getObject(1));
                                            t.add(socket);
                                        }
                                        list.add(o);
                                    }
                                    socket.out1.writeObject(list);

                                } else if (header.equals("selectAllFriend")) {
                                    String name = socket.in.readUTF();
                                    List<User> list = new UserDAO().selectAllFriend(name);
                                    socket.out.writeUTF("selectAllFriend");
                                    socket.out1.writeObject(list);
                                } else if (header.equals("get_messsage")) {
                                    int id_room = Integer.valueOf(socket.in.readUTF());
                                    ResultSet s = XJdbc.query("select * from (select top 30 messages.id, messages.message, messages.id_category, members.id_room, members.user_name\n"
                                            + "from messages inner join members on members.id = messages.id_member\n"
                                            + "where members.id_room = ?\n"
                                            + "order by messages.id desc) a\n"
                                            + "order by id asc;", id_room);
                                    List<Object[]> y = new ArrayList<Object[]>();
                                    while (s.next()) {
                                        Message c = new Message(s.getInt(1), s.getString(2), s.getInt(3), s.getInt(4), s.getString(5));
                                        User d = new UserDAO().selectById(s.getString(5));
                                        Object[] ob = {c, d};
                                        y.add(ob);
                                    }
                                    socket.out.writeUTF("get_messsage");
                                    socket.out1.writeObject(y);
                                } else if (header.equals("send_message")) {
                                    Message m = (Message) socket.in1.readObject();
                                    int id_member = (int) XJdbc.value("select id from members where id_room = ? and user_name = ?", m.getId_room(), m.getOwner());
                                    m.setId_member(id_member);
                                    new MessageDAO().insert(m);
                                    ResultSet s = XJdbc.query("select top 1 messages.id, messages.message, messages.id_category, members.id_room, members.user_name\n"
                                            + "from messages inner join members on members.id = messages.id_member \n"
                                            + "where members.user_name = ? and id_room = ? order by id desc;", m.getOwner(), m.getId_room());
                                    List<Object[]> y = new ArrayList<Object[]>();
                                    while (s.next()) {
                                        Message c = new Message(s.getInt(1), s.getString(2), s.getInt(3), s.getInt(4), s.getString(5));
                                        User d = new UserDAO().selectById(s.getString(5));
                                        Object[] ob = {c, d};
                                        y.add(ob);
                                    }
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                List<SocketClone> s = (List<SocketClone>) room.get(m.getId_room());
                                                for (SocketClone j : s) {
                                                    j.out.writeUTF("get_messsage");
                                                    j.out1.writeObject(y);
                                                }
                                            } catch (IOException ex) {
                                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }).start();
                                } else if (header.equals("send_file")) {
                                    Message m = (Message) socket.in1.readObject();
                                    int size = Integer.valueOf(socket.in.readUTF());
                                    int id_member = (int) XJdbc.value("select id from members where id_room = ? and user_name = ?", m.getId_room(), m.getOwner());
                                    m.setId_member(id_member);
                                    new MessageDAO().insert(m);
                                    int g = (int) XJdbc.value("select top 1 messages.id\n"
                                            + "from messages inner join members on members.id = messages.id_member \n"
                                            + "where members.user_name = ? and id_room = ? order by id desc;", m.getOwner(), m.getId_room());
                                    byte[] data = new byte[50000];
                                    for (int i = 0; i < size; i++) {
                                        socket.in.read(data);
                                        new ContentDAO().insert(new Content(0, data, g));
                                    }
                                    ResultSet s = XJdbc.query("select top 1 messages.id, messages.message, messages.id_category, members.id_room, members.user_name\n"
                                            + "from messages inner join members on members.id = messages.id_member \n"
                                            + "where members.user_name = ? and id_room = ? order by id desc;", m.getOwner(), m.getId_room());
                                    List<Object[]> y = new ArrayList<Object[]>();
                                    while (s.next()) {
                                        Message c = new Message(s.getInt(1), s.getString(2), s.getInt(3), s.getInt(4), s.getString(5));
                                        User d = new UserDAO().selectById(s.getString(5));
                                        Object[] ob = {c, d};
                                        y.add(ob);
                                    }
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                List<SocketClone> s = (List<SocketClone>) room.get(m.getId_room());
                                                for (SocketClone j : s) {
                                                    if (!j.equals(socket)) {
                                                        j.out.writeUTF("get_messsage");
                                                        j.out1.writeObject(y);
                                                    } else {
                                                        j.out.writeUTF("get_id_file_message");
                                                        j.out1.writeObject(y);
                                                    }
                                                }
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }).start();
                                } else if (header.equals("get_file")) {
                                    int id_message = Integer.valueOf(socket.in.readUTF());
                                    List<Content> list = new ContentDAO().selectByIdMessage(id_message);
                                    socket.out.writeUTF("get_file");
                                    int size = 0;
                                    for (Content i : list) {
                                        size += i.getContent().length;
                                    }
                                    socket.out.writeUTF(String.valueOf(size));
                                    socket.out1.writeObject(list);
                                } else if (header.equals("getAddress")) {
                                    socket.out1.writeObject(new ThongKeDAO().getAddress());
                                } else if (header.equals("getWhoChatMost")) {
                                    socket.out1.writeObject(new ThongKeDAO().getWhoChatMost());
                                } else if (header.equals("getWhoChatLeast")) {
                                    socket.out1.writeObject(new ThongKeDAO().getWhoChatLeast());
                                } else if (header.equals("getAge")) {
                                    socket.out1.writeObject(new ThongKeDAO().getAge());
                                } else if (header.equals("getNumberMessage")) {
                                    socket.out1.writeObject(new ThongKeDAO().getNumberMessage());
                                } else if (header.equals("disconnected")) {
                                    socket.s.close();
                                    is_connected = false;
                                    count -= 1;
                                    for (Object t : room.keySet()) {
                                        ArrayList<SocketClone> v = (ArrayList<SocketClone>) room.get(t);
                                        for (int i = 0; i < v.size(); i++) {
                                            if (v.get(i).equals(socket)) {
                                                v.remove(i);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                socket.s.close();
                                socket.in.close();
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
