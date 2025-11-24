/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backendpkg;


import java.util.UUID;

/**
 *
 * @author RinadMostafa
 */
public class Certificate {

    private String certificateId;
    private String studentId;
    private String courseId;
    private String courseTitle;

    public Certificate(String studentId, String courseId, String courseTitle) {
        this.certificateId = UUID.randomUUID().toString();
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        
        
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }



    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}

