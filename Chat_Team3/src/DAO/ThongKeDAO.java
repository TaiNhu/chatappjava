/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import utils.XJdbc;

/**
 *
 * @author ACER
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<Object[]>();
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getAddress() {
        String sql = "{Call peopleInCountry}";
        String[] cols = {"soluong", "addres"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getWhoChatMost() {
        String sql = "{Call whoChatMost}";
        String[] cols = {"tinNhan", "user_name"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getWhoChatLeast() {
        String sql = "{Call whoChatLeast}";
        String[] cols = {"tinNhan", "user_name"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getAge() {
        String sql = "{Call getAge}";
        String[] cols = {"max", "avg", "min"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getNumberMessage() {
        String sql = "{Call getNumberMessage}";
        String[] cols = {"tinNhan", "user_name"};
        return this.getListOfArray(sql, cols);
    }
}
