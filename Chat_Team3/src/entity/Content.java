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
public class Content implements Serializable{

    private int id;
    private byte[] content;
    private int id_message;

    public Content() {
    }

    public Content(int id, byte[] content, int id_message) {
        this.id = id;
        this.content = content;
        this.id_message = id_message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

}
