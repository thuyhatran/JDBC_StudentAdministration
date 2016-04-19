/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoimplement;

import JDBC_Connection.JDBC_StudentConnect;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class daostudentimplement implements daointerface {

    @Override
    public void insert(Object ob) throws SQLException, ClassNotFoundException {
        String query = "Insert into student (student_id, first_name, last_name, gender,start_date) values (?,?,?,?,?)";
        PreparedStatement preStatement = JDBC_StudentConnect.getConnection().prepareStatement(query);
        preStatement.setInt(1, ((Student)ob).getStudent_id());
        preStatement.setString(2, ((Student)ob).getFirstname());   
        preStatement.setString(3, ((Student)ob).getLastname());   
        preStatement.setString(4, ((Student)ob).getGender());   
        preStatement.setString(5, ((Student)ob).getStartdate());   
        preStatement.executeUpdate();
        preStatement.close();
        JDBC_StudentConnect.closeConnection();
    }

    @Override
    public void delete(int id) throws SQLException, ClassNotFoundException {
        String query = "delete from student where student_id = " + id;
        Statement statement = JDBC_StudentConnect.getConnection().createStatement();
        statement.executeUpdate(query);
        statement.close();
        JDBC_StudentConnect.closeConnection();
    }

    @Override
    public void update(Object ob) throws SQLException, ClassNotFoundException {
        String query = "Update student set first_name = ?, last_name = ?, gender = ?"
                        + "where student_id = ?";
        PreparedStatement preStatement = JDBC_StudentConnect.getConnection().prepareStatement(query);
        
        preStatement.setString(1, ((Student)ob).getFirstname());
        preStatement.setString(2, ((Student)ob).getLastname());
        preStatement.setString(3, ((Student)ob).getGender());
        
        preStatement.setInt(4,((Student)ob).getStudent_id());
        
        preStatement.executeUpdate();
        preStatement.close();
        JDBC_StudentConnect.closeConnection();
    }
 
    @Override
    @SuppressWarnings("unchecked")
    public List<Object> select() throws SQLException, ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        Statement statement = JDBC_StudentConnect.getConnection().createStatement();
        String query = "Select * from student order by student_id";
        ResultSet rslSet = statement.executeQuery(query);
        Student std = null;
        while (rslSet.next()){
            std = new Student();
            std.setStudent_id(rslSet.getInt("student_id"));
            std.setFirstname(rslSet.getString("first_name"));
            std.setLastname(rslSet.getString("last_name"));
            std.setGender(rslSet.getString("gender")); 
            std.setStartdate(rslSet.getString("start_date")); 
            students.add(std);            
        }
        
        rslSet.close();
        statement.close();
        JDBC_StudentConnect.closeConnection();
        return (List<Object>)(Object)students;
    }

    @Override
    public Object selectById(int id) throws SQLException, ClassNotFoundException {
        Student std=null;
        try (Statement statement = JDBC_StudentConnect.getConnection().createStatement()) {
            String query  = "Select * from student where student_id = " +id;
            ResultSet rslSet = statement.executeQuery(query);
            
            while (rslSet.next()){
                std = new Student();
                std.setStudent_id(rslSet.getInt("student_id"));
                std.setFirstname(rslSet.getString("first_name"));  
                std.setLastname(rslSet.getString("last_name")); 
                std.setGender(rslSet.getString("gender")); 
                std.setStartdate(rslSet.getString("start_date")); 
            }
            
            rslSet.close();
        }
        JDBC_StudentConnect.closeConnection();
        return std;
    }

    @Override
    public void write_to_file(String filename) throws SQLException, ClassNotFoundException {
        //Get information from Database
        String query = "Select * from student";
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
        int id;
        String firstname;
        String lastname;
        String gender;
        String startdate;
        PrintWriter fout = null;
        
        try {
            fout = new PrintWriter( new BufferedWriter (new FileWriter(file)));
        } catch (IOException ex) {
            Logger.getLogger(daostudentimplement.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        while (rlst.next()){
            id          = rlst.getInt("student_id");
            firstname   = rlst.getString("first_name");
            lastname    = rlst.getString("last_name");
            gender      = rlst.getString("gender");
            startdate   = rlst.getString("start_date");
            fout.println(id+","+firstname +","+lastname+","+gender+","+startdate);               
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
                String firstname = components[1];
                String lastname = components[2];    
                String gender = components[3];
                String startdate = components[4];
                              
                Student std = new Student(student_id,firstname,lastname,gender,startdate);
                
                //insert to database
                this.insert(std);
                
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
            String query = "Select * from student";
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
            int id;
            String firstname;
            String lastname;
            String gender;
            String startdate;
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
                    id = rlst.getInt("student_id") ;
                    firstname = rlst.getString("first_name");
                    lastname = rlst.getString("last_name");
                    gender = rlst.getString("gender");
                    startdate = rlst.getString("start_date");
                    fout.writeInt(id);
                    fout.writeUTF(firstname);
                    fout.writeUTF(lastname);
                    fout.writeUTF(gender);
                    fout.writeUTF(startdate);
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

    //Method for student only
     public void displayGrade() throws SQLException, ClassNotFoundException {
        Statement statement = JDBC_StudentConnect.getConnection().createStatement();
        // this one can't display the student don't have any grade, (+) has limit on second join (how to used?)
//        String query = "Select st.student_id, first_name, last_name, gender, course_name, mark1, mark2 "
//                    + "from student st, course crs, result rls "
//                    + "where st.student_id = rls.student_id (+) and crs.course_id = rls.course_id "
//                    + "order by st.student_id";
        
        
        String query = "Select st.student_id, first_name, last_name, gender, course_name, mark1, mark2 "
                    + "from student st left join course crs on st.STUDENT_ID = crs.COURSE_ID " 
                    + "left join result rls on crs.course_id = rls.course_id "
                    + "order by st.student_id";
        
        
        System.out.println(query);
         
        ResultSet rslSet = statement.executeQuery(query);
            
        
        while (rslSet.next()){
            
            System.out.print(rslSet.getInt("student_id") + ", ");
            System.out.print(rslSet.getString("first_name")+ " ");
            System.out.print(rslSet.getString("last_name")+ ", ");
            System.out.print(rslSet.getString("gender")+ ", "); 
            System.out.print(rslSet.getString("course_name")+ ", ");
            System.out.print(rslSet.getInt("mark1")+ ", ");
            System.out.print(rslSet.getInt("mark2"));
            System.out.println();
                              
        }
        
        rslSet.close();
        statement.close();
        JDBC_StudentConnect.closeConnection();
        
    }
         
}
