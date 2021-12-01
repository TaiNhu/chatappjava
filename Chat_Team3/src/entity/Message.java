/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.io.Serializable;
/**
 *
 * @author ACER
 */
public class Message implements Serializable{
    private int id; 
    private String message;
    private int id_category;
    private int id_member;  
    private String owner;
    private int id_room;

    public Message(int id, String message, int id_category, int id_room, String owner) {
        this.id = id;
        this.message = message;
        this.id_category = id_category;
        this.id_room = id_room;
        this.owner = owner;
    }
    
    public Message(int id, String message, int id_category, int id_member){
        this.id = id;
        this.message = message;
        this.id_category = id_category;
        this.id_member = id_member;
    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Message() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_member() {
        return id_member;
    }

    public void setId_member(int id_member) {
        this.id_member = id_member;
    }
}
