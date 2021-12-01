/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import entity.User;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ACER
 */
public class Auth {
    public static User user;
    public static Client client = null;
    public static List trungGiang = new ArrayList();
    public static byte[] hinh;
    public static String name;
    public static List trungGian = new ArrayList();
    
    public static void setClient(Client client){
        Auth.client = client;
    }
}
