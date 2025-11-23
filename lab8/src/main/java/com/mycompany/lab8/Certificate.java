
import java.time.LocalDate;
import java.util.UUID;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RinadMostafa
 */
public class Certificate {
     private String certificateId;
    private String studentId;
    private String courseId;
    private String issueDate;
    private String courseTitle;

    public Certificate(String studentId, String courseId, String courseTitle) {
        this.certificateId = UUID.randomUUID().toString();
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.issueDate = LocalDate.now().toString();
        
        
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    
}
