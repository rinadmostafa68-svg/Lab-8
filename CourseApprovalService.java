/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lap_8;

/**
 *
 * @author Zaid&Lama&joody
 */
import java.util.ArrayList;
public class CourseApprovalService {
    private ArrayList<Course> courses;

    public CourseApprovalService(ArrayList<Course> courses) {
        this.courses = courses;
    }
    public ArrayList<Course> getPendingCourses(){
        ArrayList<Course> pending = new ArrayList<>();
        for(Course c : courses){
            if(c.getStatus().equalsIgnoreCase("PENDING")){
                pending.add(c);
            }
        }
        return pending;
    } 
    public void updateStatus(String corseId, String status){
        for(Course c : courses){
            if(c.getId().equals(corseId)){
                c.setStatus(status);
            }
        }
    }
    
}
