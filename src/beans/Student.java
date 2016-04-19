/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Student {
    private int student_id;
    private String firstname;
    private String lastname;
    private String gender;
    private String startdate;
    
   //contructors

    public Student() {
    }

    public Student(int student_id, String firstname, String lastname, String gender, String startdate) {
        this.student_id = student_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.startdate = startdate;
    }

    public Student(int student_id, String firstname, String lastname) {
        this.student_id = student_id;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    //getters
    public int getStudent_id() {
        return student_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getStartdate() {
        return startdate;
    }
    
    //setters
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    
    @Override
    public String toString(){
        return student_id + ", " + firstname + " " + lastname +", " +gender+", " + startdate;
    }
    
}
