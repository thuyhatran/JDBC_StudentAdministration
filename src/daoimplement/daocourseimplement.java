/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimplement;


import JDBC_Connection.JDBC_StudentConnect;
import beans.Course;
import beans.Results;
import daointerface.daointerface;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class daocourseimplement implements daointerface{

    @Override
    public void insert(Object ob) throws SQLException, ClassNotFoundException {
        String query = "Insert into course (course_id, course_name) values (?,?)";
        PreparedStatement preStatement = JDBC_StudentConnect.getConnection().prepareStatement(query);
        preStatement.setInt(1, ((Course)ob).getCourse_id());
        preStatement.setString(2, ((Course)ob).getCourse_name());   
       
        preStatement.executeUpdate();
        preStatement.close();
        JDBC_StudentConnect.closeConnection();
    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object ob) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> select() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object selectById(int id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write_to_file(String filename) throws SQLException, ClassNotFoundException {
        //Get information from Database
        String query = "Select * from Course";
        Statement statement = JDBC_StudentConnect.getConnection().createStatement();
        ResultSet rlst =  statement.executeQuery(query);
        
        //Create a file       
        File file = new File(filename);
        if (!file.exists()){
            File citydir = new File(file.getParent());
            if (!citydir.exists())
                citydir.mkdirs();
            
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }
        
        //write to file
        
        int course_id;
        String course_name;

        PrintWriter fout = null;
        
        try {
            fout = new PrintWriter( new BufferedWriter (new FileWriter(file)));
        } catch (IOException ex) {
            Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        while (rlst.next()){
           
            course_id   = rlst.getInt("course_id");
            course_name    = rlst.getString("course_name");
            
            fout.println(course_id +","+course_name);               
        }
       
        fout.close();
        rlst.close();
        statement.close();
        JDBC_StudentConnect.closeConnection();
    }

    @Override
    public void insert_from_file(String filename) throws SQLException, ClassNotFoundException {
         File file = new File(filename);
       BufferedReader fin = null;
        
        if (!file.exists()){
            System.out.println("File not exists!");
        }else{
            System.out.println("Insert data from file ....");
            
            try {
                fin = new BufferedReader( new FileReader(file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String line = null;
            try {
                line = fin.readLine();
            } catch (IOException ex) {
                Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             
            while (line!=null){
                //slipt line data and add fields to Person object 
                                
                String[] components = line.split(",");
                                
               
                int course_id = Integer.parseInt(components[0]);
                String course_name = components[1];    
                                          
                Course course = new Course(course_id,course_name);
                
                //insert to database
                this.insert(course);
                
                 //read next line
                try {
                    line = fin.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
                }            
            }
            
            //close file
            try {                
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("end of inserting");
            
        }
    }

    @Override
    public void write_to_BinaryFile(String filename) throws SQLException, ClassNotFoundException {
         try {
            String query = "Select * from Course";
            Statement statement = JDBC_StudentConnect.getConnection().createStatement();
            ResultSet rlst =  statement.executeQuery(query);
            
            //Create a file
            File file = new File(filename);
            if (!file.exists()){
                File citydir = new File(file.getParent());
                if (!citydir.exists())
                    citydir.mkdirs();
                
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
                                        
            //write to file
           
            int course_id;
            String course_name;
           
            DataOutputStream fout = null;
            
            try {
                fout = new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(file)));
            } catch (IOException ex) {
                Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            while (rlst.next()){
                try {
                    
               
                    course_id = rlst.getInt("course_id");
                    course_name = rlst.getString("course_name");
                   
                    fout.writeInt(course_id);
                    fout.writeUTF(course_name);
                    
                } catch (IOException ex) {
                    Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
            
            fout.close();
            rlst.close();
            statement.close();
            JDBC_StudentConnect.closeConnection();
        } catch (IOException ex) {
            Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    }

  
    

