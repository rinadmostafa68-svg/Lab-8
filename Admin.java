/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lap_8;

/**
 *
 * @author Zaid&Lama&joody
 */
public class Admin extends user {
    public Admin(String username, String password){
        super(username,password,"ADMIN");
    }
    public void approveCourse(Course course){
        course.setStatus("APPROVED");
    }
    public void rejectCourse(Course course){
        course.setStatus("REJECTED");
    }
}
