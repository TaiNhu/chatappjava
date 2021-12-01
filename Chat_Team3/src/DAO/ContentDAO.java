/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.Content;
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
public class ContentDAO extends ChatAppDAO<Content, Integer> {

    String INSERT_SQL = "INSERT  INTO  Contents(Content,ID_Message)  VALUES  (?,?)";
    String UPDATE_SQL = "UPDATE  Contents  SET  Content=?,ID_Message=?  WHERE  ID=?";
    String DELETE_SQL = "DELETE FROM Contents WHERE ID=?";
    String SELECT_ALL_SQL = "SELECT * FROM Contents";
    String SELECT_BY_ID_SQL = "SELECT * FROM Contents where ID=?";
    String SELECT_BY_ID_MESSAGE = "SELECT * from contents where id_message = ?";

    @Override
    public boolean insert(Content entity) {
        try {
            XJdbc.update(INSERT_SQL, entity.getContent(), entity.getId_message());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Content entity) {
        try {
            XJdbc.update(UPDATE_SQL, entity.getContent(), entity.getId_message(), entity.getId());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            XJdbc.update(DELETE_SQL, id);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Content> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Content selectById(Integer id) {
        List<Content> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<Content> selectByIdMessage(int id_message){
        List<Content> list = this.selectBySQL(SELECT_BY_ID_MESSAGE, id_message);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    protected List<Content> selectBySQL(String sql, Object... args) {
        List<Content> list = new ArrayList<Content>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Content entity = new Content();
                entity.setId(rs.getInt("ID"));
                entity.setContent(rs.getBytes("Content"));
                entity.setId_message(rs.getInt("Id_message"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
