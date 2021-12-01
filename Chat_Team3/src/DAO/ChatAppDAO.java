/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;

/**
 *
 * @author ACER
 */
abstract public class ChatAppDAO<E, K> {
    abstract public  boolean insert(E entity);
    abstract public boolean update(E entity);
    abstract public boolean delete(K key);
    abstract public List<E> selectAll();
    abstract public E selectById(K key);
    abstract protected List<E> selectBySQL(String sql, Object...args);
}
