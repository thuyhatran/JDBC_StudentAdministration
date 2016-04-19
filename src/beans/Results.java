/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Administrator
 */
public class Results {
    private int student_id;
    private int course_id;
    private int marks1;
    private int marks2;

    public Results() {
    }
    
    public Results(int student_id, int course_id, int marks1, int marks2) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.marks1 = marks1;
        this.marks2 = marks2;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setMarks1(int marks1) {
        this.marks1 = marks1;
    }

    public void setMarks2(int marks2) {
        this.marks2 = marks2;
    }

    //Getters
    public int getStudent_id() {
        return student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public int getMarks1() {
        return marks1;
    }

    public int getMarks2() {
        return marks2;
    }
    
    
    
    
    
}
