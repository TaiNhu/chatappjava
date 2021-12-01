/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Message;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author ACER
 */
public class Client implements Runnable {

    public Socket client;
    public DataInputStream in;
    public DataOutputStream out;
    public ObjectOutputStream out1;
    public ObjectInputStream in1;

    public Client(Socket client) throws IOException {
        this.client = client;
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();
        in = new DataInputStream(is);
        out = new DataOutputStream(os);
        out1 = new ObjectOutputStream(os);
        in1 = new ObjectInputStream(is);
    }
    
    public void send_message(Message m){
        try {
            out.writeUTF("send_message");
            out1.writeObject(m);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void get_messsage(int id_room){
        try {
            out.writeUTF("get_messsage");
            out.writeUTF(String.valueOf(id_room));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User login(String user_name, String password) {
        try {
            out.writeUTF("login");
            out.writeUTF(user_name);
            out.writeUTF(password);
            return (User) in1.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean registration(User entity) {
        try {
            this.out.writeUTF("registration");
            out1.writeObject(entity);
            return in.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getPass(String email) {
        try {
            this.out.writeUTF("getPass");
            this.out.writeUTF(email);
            return this.in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List fillRoom(String name){
        try {
            this.out.writeUTF("fillRoom");
            this.out.writeUTF(name);
            return (List) this.in1.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List fillGroup(String name){
        try {
            this.out.writeUTF("fillGroup");
            this.out.writeUTF(name);
            return (List) this.in1.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void close() {
        try {
            out.writeUTF("disconnected");
            this.client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            System.out.println("test");
        }
    }
    
    public void updateUser(User entity){
        try {
            out.writeUTF("updateUser");
            out1.writeObject(entity);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_friend(String name, String friend_name){
        try {
            out.writeUTF("add_friend");
            out.writeUTF(name);
            out.writeUTF(friend_name);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void add_group(List args, byte[] hinh, String name){
        try {
            out.writeUTF("add_group");
            out1.writeObject(args);
            out.writeUTF(name);
            out.writeUTF(String.valueOf(hinh.length));
            out.write(hinh);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectByNickName(String nick_name){
        try {
            out.writeUTF("selectByNickName");
            out.writeUTF(nick_name);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectAllFriend(String name){
        try {
            out.writeUTF("selectAllFriend");
            out.writeUTF(name);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Object[]> getAddress() {
        try {
            out.writeUTF("getAddress");
            return (List<Object[]>) in1.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Object[]>();
    }

    public List<Object[]> getWhoChatMost() {
        try {
            out.writeUTF("getWhoChatMost");
            return (List<Object[]>) in1.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Object[]>();
    }

    public List<Object[]> getWhoChatLeast() {
        try {
            out.writeUTF("getWhoChatLeast");
            return (List<Object[]>) in1.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Object[]>();
    }

    public List<Object[]> getAge() {
        try {
            out.writeUTF("getAge");
            return (List<Object[]>) in1.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Object[]>();
    }

    public List<Object[]> getNumberMessage() {
        try {
            out.writeUTF("getNumberMessage");
            return (List<Object[]>) in1.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<Object[]>();
    }
    
    public static void main(String[] args) {
        try {
            int port = 10000;
            Client client = new Client(new Socket("localhost", port));
//            Thread t = new Thread(client);
//            t.start();
//            List<Object[]> list = client.add_group("tai", "Team3", "giang");
//            for(Object[] ob: list){
//                for (Object a: ob){
//                    System.out.println(a);
//                }
//            }
//            client.close("tai");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
