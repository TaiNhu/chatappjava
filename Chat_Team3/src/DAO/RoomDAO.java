/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.Room;
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
public class RoomDAO extends ChatAppDAO<Room, Integer>{
    
    String INSERT_ROOM = "INSERT  INTO  Rooms  (Name)  VALUES  (NULL)";
    String INSERT_GROUP = "INSERT  INTO  Rooms  (Name, isGroup, avatar)  VALUES  (?, ?, ?)";
    String UPDATE_SQL = "UPDATE  Rooms  SET  Name=?  WHERE  ID=?";
    String DELETE_SQL = "DELETE FROM Rooms WHERE ID=?";
    String SELECT_ALL_SQL = "SELECT * FROM Rooms";
    String SELECT_BY_ID_SQL = "SELECT * FROM Rooms where ID=?";
    
    @Override
    public boolean insert(Room entity) {
       try {
            XJdbc.update(INSERT_GROUP,
                    entity.getName(), entity.isIsGroup(), entity.getAvatar());
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean insert_room(){
        try {
            XJdbc.update(INSERT_ROOM);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Room entity) {
        try {
            XJdbc.update(UPDATE_SQL,  entity.getName());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Integer ID) {
        try {
            XJdbc.update(DELETE_SQL, ID);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Room> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
        
    }

    @Override
    public Room selectById(Integer ID) {
        List<Room> list = this.selectBySQL(SELECT_BY_ID_SQL, ID);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
        
    }

    @Override
    protected List<Room> selectBySQL(String sql, Object... args) {
        List<Room> list = new ArrayList<Room>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Room entity = new Room();
                entity.setId(rs.getInt("ID"));
                entity.setName(rs.getString("Name"));
                
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
