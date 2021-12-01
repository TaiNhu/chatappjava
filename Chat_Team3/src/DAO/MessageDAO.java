/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.Message;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.XJdbc;

/**
 *
 * @author ACER
 */
public class MessageDAO extends ChatAppDAO<Message, Integer> {

    String INSERT_SQL = "INSERT  INTO  Messages  (message, id_category, id_member)  VALUES  (?, ?, ?)";
    String UPDATE_SQL = "UPDATE  Message  SET  message = ?  WHERE  ID = ?";
    String DELETE_SQL = "DELETE FROM Message WHERE ID = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Message";
    String SELECT_BY_ID_SQL = "SELECT * FROM Message where ID = ?";

    @Override
    public boolean insert(Message entity) {
        try {
            XJdbc.update(INSERT_SQL,
                    entity.getMessage(), entity.getId_category(), entity.getId_member());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Message entity) {
        try {
            XJdbc.update(UPDATE_SQL,
                    entity.getMessage(), entity.getId());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
        XJdbc.update(DELETE_SQL, id);
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
    }

    @Override
    public List<Message> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Message selectById(Integer id) {
        List<Message> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public Message selectMax(){
        return this.selectBySQL("select top 1 * from messages order by id asc;").get(0);
    }

    @Override
    protected List<Message> selectBySQL(String sql, Object... args) {
        List<Message> list = new ArrayList<Message>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Message enity = new Message();
                enity.setId(rs.getInt("ID"));
                enity.setMessage(rs.getString("Message"));
                enity.setId_category(rs.getInt("ID_Category"));
                enity.setId_member(rs.getInt("ID_Member"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}