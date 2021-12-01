/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.Member;
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
public class MemberDAO extends ChatAppDAO<Member, Integer>{
    String INSERT_SQL = "INSERT  INTO  Members  (User_name, id_room)  VALUES  (?, ?)";
    String UPDATE_SQL = "UPDATE  Members  SET  User_name=?  WHERE  ID=?";
    String DELETE_SQL = "DELETE FROM ChuyenDe WHERE ID=?";
    String SELECT_ALL_SQL = "SELECT * FROM Member";
    String SELECT_BY_ID_SQL = "SELECT * FROM Members where ID=?";
    @Override
    public boolean insert(Member enity) {
        try {
            XJdbc.update(INSERT_SQL,
                    enity.getUser_name(), enity.getId_room());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Member enity) {
        try {
            XJdbc.update(UPDATE_SQL,  enity.getUser_name(), enity.getId());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            XJdbc.update(DELETE_SQL, id);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Member> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Member selectById(Integer id) {
        List<Member> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public int selectByIdRoomUserName(int id_room, String user_name){
        return (int) XJdbc.value("select id from members where user_name = ? and id_room = ?", user_name, id_room);
    }

    @Override
    protected List<Member> selectBySQL(String sql, Object... args) {
        List<Member> list = new ArrayList<Member>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Member enity = new Member();
                enity.setId(rs.getInt("ID"));
                enity.setId_room(rs.getInt("id_room"));
                enity.setUser_name(rs.getString("user_name"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
}
