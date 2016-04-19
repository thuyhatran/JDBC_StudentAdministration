/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_studentadminission;

import JDBC_Connection.JDBC_StudentConnect;
import beans.Student;
import daoimplement.daocourseimplement;
import daoimplement.daoresultimplement;
import daoimplement.daostudentimplement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class JDBC_StudentAdminission {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
           //Print the menu
           
                        
           
       boolean cnt = true;
       CreateMenu();

    
               
       while (cnt){               
           int choose=GetTask();                
           cnt=DoAdmission(choose);               
       } 
          
            
       
    }
    
    //private static final Logger LOG = Logger.getLogger(TestAdmMain.class.getName());
    
    public static void CreateMenu(){
        System.out.println("1.Insert");
        System.out.println("2.Delete");
        System.out.println("3.Update");
        System.out.println("4.Display a student");
        System.out.println("5.Display all students");
        System.out.println("6.Write all students to a file");
        System.out.println("7.Insert to student table from a file");
        System.out.println("8.Exit");
    }
    
     public static int GetTask(){
        Scanner sc = new Scanner(System.in);
        System.out.print("CHOOSE A TASK: "); int choose = sc.nextInt();
        return choose;
     }  
     
 
    public static boolean DoAdmission(int choose) throws SQLException, ClassNotFoundException{
        boolean cnt=true;
        daostudentimplement daostd = new daostudentimplement();
        daocourseimplement daocourse = new daocourseimplement();
        daoresultimplement result = new daoresultimplement();
        Scanner sc = new Scanner(System.in);
        Student std = null; 
        int id;
        String firstname;
        String lastname;
        String gender;
        String date;
        
            switch (choose){
                case 1: //Insert a new person
                    
                    std = new Student();
                   
                    System.out.println("Create a new person:");
                    System.out.print("ID: "); 
                    id = sc.nextInt();
                    System.out.print("First Name: ");
                    firstname = sc.next();
                    System.out.print("Last Name: ");
                    lastname = sc.next();
                    System.out.print("Gender: ");
                    gender = sc.next();
                    System.out.print("Enter new date: ");
                    date = sc.next();
                    
                    std.setStudent_id(id);
                    std.setFirstname(firstname);
                    std.setLastname(lastname);
                    std.setGender(gender);
                    std.setStartdate(date);
                    daostd.insert(std);
                    
                    break; 
                    
                case 2:  //Delete a person
                    System.out.print("Delete student that has Id :");
                    id = sc.nextInt();
                    daostd.delete(id);
                    
                    break;
                    
                case 3: //update person's name
                    System.out.print("Update student that has Id :");
                    id = sc.nextInt();
                    System.out.print("Enter new firstname: ");
                    firstname = sc.next();
                    System.out.print("Enter new lastname: ");
                    lastname = sc.next();
                    System.out.print("Enter new gender: ");
                    gender = sc.next();
                    
                    std = new Student(id,firstname,lastname,gender,"");
                    daostd.update(std);
                    
                    break;
                    
                case 4: //display a person                   
                    //pers = new Person();
                    System.out.print("ID of student: "); 
                    id = sc.nextInt();
                    //std = daostd.selectById(id);
                    std = (Student) daostd.selectById(id);  //test using Object class
                           
                    //System.out.println("ID = " + id + "\nName = " + std.getFirstname());      
                    System.out.println(std);
                    
                    break;
                    
                case 5://display all people
                    
                    List<Object> students = new ArrayList<>();     
                    
                    students = daostd.select();  
                    
                    for (Object p:students)
                        System.out.println(p);
                                                                    
                    break;
                    
                case 6:
                    daostd.write_to_file("C:/Test3/student.txt");
                    break;
                    
                case 7: // insert to Person table from a file
                                        
                    daostd.insert_from_file("C:/Test/java/student.txt");                    
                    break;  
                    
                case 8:
                    cnt = false;
                    break;

            }
        
        
        return cnt;
    }
    
    
    
    
}