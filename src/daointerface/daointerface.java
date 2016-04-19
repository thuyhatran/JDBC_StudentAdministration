/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daointerface;


import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public abstract interface daointerface {
     void insert (Object ob) throws SQLException, ClassNotFoundException;
    void delete (int id) throws SQLException, ClassNotFoundException;
    void update (Object ob) throws SQLException, ClassNotFoundException;
    List<Object> select()  throws SQLException, ClassNotFoundException;
    Object selectById(int id)  throws SQLException, ClassNotFoundException;
    void write_to_file(String filename) throws SQLException, ClassNotFoundException;
    void insert_from_file(String filename) throws SQLException, ClassNotFoundException;
    void write_to_BinaryFile(String filename) throws SQLException, ClassNotFoundException;
    
}
