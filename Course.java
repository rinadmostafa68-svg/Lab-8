/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lap_8;

/**
 *
 * @author Zaid
 */

public class Course {
    private String id;
     private String tittle;
      private String instructorusername;
       private String status;

    public Course(String id, String tittle, String instructorusername) {
        this.id = id;
        this.tittle = tittle;
        this.instructorusername = instructorusername;
        this.status ="PENDING";
    }
        public String getId() {
            return id;}
        public String getTittle(){
            return tittle;}
         public String getInstructorusername() {
             return instructorusername;}
          public String getStatus(){
           return status;}

    public void setStatus(String status) {
        this.status = status;
    }
}
