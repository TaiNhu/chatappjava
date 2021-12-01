/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;
import java.io.*;
/**
 *
 * @author ACER
 */
public class User implements Serializable{

    private String user_name;
    private String password;
    private String nick_name;
    private byte[] avatar;
    private boolean gender;
    private String birthday;
    private String email;
    private boolean role;
    private String time_off;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getTime_off() {
        return time_off;
    }

    public void setTime_off(String time_off) {
        this.time_off = time_off;
    }

    public User() {
    }

    public User(String user_name, String password, String nick_name, byte[] avatar, boolean gender, String birthday, String email, boolean role, String time_off) {
        this.user_name = user_name;
        this.password = password;
        this.nick_name = nick_name;
        this.avatar = avatar;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.role = role;
        this.time_off = time_off;
    }
}
