/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class SocketClone implements Serializable{

    public DataOutputStream out;
    public DataInputStream in;
    public ObjectInputStream in1;
    public ObjectOutputStream out1;
    public Socket s;
    public String user_name;
    
    
    public SocketClone(Socket s){
        super();
        try {
            this.s = s;
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
            out1 = new ObjectOutputStream(s.getOutputStream());
            in1 = new ObjectInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SocketClone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setName(String name){
        this.user_name = name;
    }

}
