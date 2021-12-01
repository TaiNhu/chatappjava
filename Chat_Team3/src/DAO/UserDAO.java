/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.XJdbc;

/**
 *
 * @author meoca
 */
public class UserDAO extends ChatAppDAO<User, String> {

    String INSERT_SQL = "INSERT  INTO  users  (user_name,  password,  nickname,  avatar, gender, birthday, email, role)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE  users  SET  password=?,  nickname=?,  birthday=?, email=?, avatar=?, gender=?  WHERE  user_name=?";
    String SELECT_ALL_SQL = "SELECT * FROM users";
    String SELECT_BY_ID_SQL = "SELECT * FROM users WHERE user_name=?";
    String SELECT_BY_ID_SQL_AND_PASS = "select * from users where user_name=? and password=?";
    String SELECT_PASS_BY_EMAIL = "select password from users where email=?";
    String SELECT_BY_NICK_NAME = "select * from users where nickname = ?";

    @Override
    public boolean insert(User enity) {
        try {
            XJdbc.update(INSERT_SQL,
                    enity.getUser_name(), enity.getPassword(), enity.getNick_name(), enity.getAvatar(), enity.isGender(), enity.getBirthday(), enity.getEmail(), enity.isRole());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public String getPass(String email) {
        return (String) XJdbc.value(SELECT_PASS_BY_EMAIL, email);
    }

    @Override
    public boolean update(User enity) {
        try {
            XJdbc.update(UPDATE_SQL,
                    enity.getPassword(), enity.getNick_name(), enity.getBirthday(), enity.getEmail(),  enity.getAvatar(), enity.isGender(), enity.getUser_name());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<User> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public User selectById(String id) {
        List<User> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public User selectByIdAndPass(String id, String pass) {
        List<User> list = this.selectBySQL(SELECT_BY_ID_SQL_AND_PASS, id, pass);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<User> selectAllFriend(String name) {
        return this.selectBySQL("select distinct users.user_name, password, nickname, avatar, gender, birthday, email, time_off, role \n"
                + "from users inner join members on members.User_name = users.user_name\n"
                + "where members.user_name != ?\n"
                + "and members.ID_Room in (select id_room from members where user_name = ?);", name, name);
    }

    public List<User> selectByNickName(String nick_name) {
        List<User> list = this.selectBySQL(SELECT_BY_NICK_NAME, nick_name);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public void updateAddress(String user_name, String pass, String address){
        try {
            XJdbc.update("update users set addres = ? where user_name = ? and password = ?", address, user_name, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected List<User> selectBySQL(String sql, Object... args) {
        List<User> list = new ArrayList<User>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                User enity = new User();
                enity.setUser_name(rs.getString("user_name"));
                enity.setPassword(rs.getString("password"));
                enity.setNick_name(rs.getString("nickname"));
                enity.setAvatar(rs.getBytes("avatar"));
                enity.setBirthday(rs.getString("birthday"));
                enity.setGender(rs.getBoolean("Gender"));
                enity.setEmail(rs.getString("email"));
                enity.setTime_off(rs.getString("time_off"));
                enity.setRole(rs.getBoolean("role"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
