/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimplement;

import JDBC_Connection.JDBC_StudentConnect;
import beans.Results;
import beans.Student;
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
public class daoresultimplement implements daointerface {

    @Override
    public void insert(Object ob) throws SQLException, ClassNotFoundException {
        String query = "Insert into result (student_id, course_id, mark1, marks2) values (?,?,?,?)";
        PreparedStatement preStatement = JDBC_StudentConnect.getConnection().prepareStatement(query);
        preStatement.setInt(1, ((Results)ob).getStudent_id());
        preStatement.setInt(2, ((Results)ob).getCourse_id());   
        preStatement.setInt(3, ((Results)ob).getMarks1());   
        preStatement.setInt(4, ((Results)ob).getMarks2());   
        
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
        String query = "Select * from result";
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
        int student_id;
        int course_id;
        int marks1;
        int marks2;

        PrintWriter fout = null;
        
        try {
            fout = new PrintWriter( new BufferedWriter (new FileWriter(file)));
        } catch (IOException ex) {
            Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        while (rlst.next()){
            student_id  = rlst.getInt("student_id");
            course_id   = rlst.getInt("course_id");
            marks1      = rlst.getInt("mark1");
            marks2      = rlst.getInt("mark2");
         
            fout.println(student_id+","+course_id +","+marks1+","+marks2);               
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
                                
                int student_id = Integer.parseInt(components[0]);
                int course_id = Integer.parseInt(components[1]);
                int marks1 = Integer.parseInt(components[2]);    
                int marks2 = Integer.parseInt(components[3]);

                              
                Results rls = new Results(student_id,course_id,marks1,marks2);
                
                //insert to database
                this.insert(rls);
                
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
            String query = "Select * from Results";
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
            int student_id; 
            int course_id;
            int marks1;
            int marks2; 
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
                    
                    student_id = rlst.getInt("student_id");
                    course_id = rlst.getInt("course_id");
                    marks1 = rlst.getInt("mark1");
                    marks2 = rlst.getInt("mark2");
                  
                    fout.writeInt(student_id);
                    fout.writeInt(course_id);
                    fout.writeInt(marks1);
                    fout.writeInt(marks2);
                    
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
