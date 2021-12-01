/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.SQLException;
import java.util.List;
import utils.XJdbc;
import entity.Category;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class CategoryDAO extends ChatAppDAO<Category, Integer>{
    String INSERT_SQL = "INSERT  INTO  Categories  (name)  VALUES  (?)";
    String UPDATE_SQL = "UPDATE  Categories  SET  name=?  WHERE  ID=?";
    String DELETE_SQL = "DELETE FROM Categories WHERE ID=?";
    String SELECT_ALL_SQL = "SELECT * FROM Categories";
    String SELECT_BY_ID_SQL = "SELECT * FROM Categories WHERE ID=?";
    @Override
    public boolean insert(Category entity) {
        try {
            XJdbc.update(INSERT_SQL,
                    entity.getName());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Category entity) {
        try {
            XJdbc.update(UPDATE_SQL,
                     entity.getName(), entity.getId());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer ID) {
        try {
            XJdbc.update(DELETE_SQL, ID);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public List<Category> selectAll() {
        return (List<Category>) this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Category selectById(Integer ID) {
        List<Category> list = (List<Category>) this.selectBySQL(SELECT_BY_ID_SQL, ID);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    @Override
    protected List<Category> selectBySQL(String sql, Object... args) {
        List<Category> list = new ArrayList<Category>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Category enity = new Category();
                enity.setId(rs.getInt("ID"));
                enity.setName(rs.getString("Name"));
                list.add(enity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            
        }
        return null;
    }
}
