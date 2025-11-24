package backendpkg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseManagement {
    public final Database db;
    private final User currentUser;
    private final StudentManagement studentManagement;       
    private final InstructorManagement instructorManagement; 

    public CourseManagement(Database db, User currentUser) throws IOException {
        this.db = db; 
        this.currentUser = currentUser;
        if (currentUser instanceof Student) {
            this.studentManagement = new StudentManagement(db);
            this.instructorManagement = null;
        } else if (currentUser instanceof Instructor) {
            this.instructorManagement = new InstructorManagement(db);
            this.studentManagement = null;
        } else {
            this.studentManagement = null;
            this.instructorManagement = null;
        }
    }

    private Student asStudent() {
        if (!(currentUser instanceof Student s)) {
            throw new IllegalStateException("Current user is not a Student");
        }
        return (Student) currentUser;
    }

    private Instructor asInstructor() {
        if (!(currentUser instanceof Instructor i)) {
            throw new IllegalStateException("Current user is not an Instructor");
        }
        return (Instructor) currentUser;
    }

    private StudentManagement getStudentManagement() {
        if (studentManagement == null) {
            throw new IllegalStateException("StudentManagement is not available for this user");
        }
        return studentManagement;
    }

    private InstructorManagement getInstructorManagement() {
        if (instructorManagement == null) {
            throw new IllegalStateException("InstructorManagement is not available for this user");
        }
        return instructorManagement;
    }

    public List<Course> getAvailableCourses() {
        Student s = asStudent();
        return getStudentManagement().getAvailableCourses();
    }

    public List<Course> getPendingCourse() throws IOException {
        ArrayList<Course> pendingCourses = new ArrayList<>();
        List<Course> courses = db.loadCourses();
        for (Course course : courses) {
            if(course.getStatus().equals("PENDING")){
                pendingCourses.add(course);
            }
        }
        return pendingCourses;
    }
    public List<Course> getEnrolledCourses() {
        Student s = asStudent();
        return getStudentManagement().getEnrolledCourses(s);
    }

    public void enrollCourse(Database db1, Student s, String courseId) throws IOException {
        //Student s = asStudent();
        StudentManagement sm = new StudentManagement(db1);
        sm.enrollCourse(s, courseId);
    }

    public List<Lesson> getLessonsForCurrentStudentInCourse(String courseId) {
        Student s = asStudent();
        return getStudentManagement().getCourseLessons(courseId);
    }

    public List<Course> getInstructorCourses(Instructor inst) {
    List<String> ids = inst.getCreatedCourseIds();
    List<Course> result = new ArrayList<>();
    for (Course c : instructorManagement.courses) {
        if (ids.contains(c.getCourseId())) {
            result.add(c);
        }
    }
    return result;
}
    
    public void completeLesson(Student s,String courseId, String lessonId) throws IOException {
        //Student s = asStudent();
        getStudentManagement().completeLesson(s, courseId, lessonId);
    }

    public Course createCourse(String title, String description) throws IOException {
        Instructor inst = asInstructor();
        return getInstructorManagement().createCourse(inst, title, description);
    }

    public void editCourse(String courseId, String newTitle, String newDescription) throws IOException {
        asInstructor(); 
        getInstructorManagement().editCourse(courseId, newTitle, newDescription);
    }

    public void deleteCourse(String courseId) throws IOException {
        asInstructor();
        getInstructorManagement().deleteCourse(courseId);
    }

    public Lesson addLesson(String courseId,
            String title,
            String content,
            List<String> resources) throws IOException {

        asInstructor();
        return getInstructorManagement().addLesson(courseId, title, content, resources);
    }

    public void editLesson(String courseId,
            String lessonId,
            String newTitle,
            String newContent,
            List<String> newResources) throws IOException {

        asInstructor();
        getInstructorManagement().editLesson(courseId, lessonId, newTitle, newContent, newResources);
    }

    public void deleteLesson(String courseId, String lessonId) throws IOException {
        asInstructor();
        getInstructorManagement().deleteLesson(courseId, lessonId);
    }

    public List<Student> getEnrolledStudents(String courseId) {
        asInstructor();
        return getInstructorManagement().getEnrolledStudents(courseId);
    }
    
    public List<Lesson> getLessonsForInstructor(String courseId) {
    asInstructor();
    return getInstructorManagement().getCourseLessons(courseId);
}
    //****************************************

public List<Course> getPendingCourses() throws IOException {
    List<Course> pending = new ArrayList<>();
    List<Course> courses = db.loadCourses();

    for (Course c : courses) {
        if ("PENDING".equalsIgnoreCase(c.getStatus())) {
            pending.add(c);
        }
    }
    return pending;
}
public void approveCourse(String courseId) throws IOException {
    List<Course> courses = db.loadCourses();

    for (Course c : courses) {
        if (c.getCourseId().equals(courseId)) {
            c.setStatus("APPROVED");
            db.saveCourses(courses);
            return;
        }
    }
    throw new IOException("Course not found");
}
public void rejectCourse(String courseId) throws IOException {
    List<Course> courses = db.loadCourses();

    for (Course c : courses) {
        if (c.getCourseId().equals(courseId)) {
            c.setStatus("REJECTED");
            db.saveCourses(courses);
            return;
        }
    }
    throw new IOException("Course not found");
}
public List<Course> getApprovedCourses() throws IOException {
    List<Course> approved = new ArrayList<>();
    for (Course c : db.loadCourses()) {
        if ("APPROVED".equalsIgnoreCase(c.getStatus())) {
            approved.add(c);
        }
    }
    return approved;
}
}
