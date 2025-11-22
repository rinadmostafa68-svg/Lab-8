package backendpkg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentManagement {

    private final List<Course> courses;
    private final List<User> users;
    private final Database db;
    private final Map<String, Set<String>> completedLessonsMap = new HashMap<>();

    public StudentManagement(Database db) throws IOException {
        this.db = db;
        this.courses = db.loadCourses();
        this.users = db.loadUsers();
    }

    public List<Course> getAvailableCourses() {
        return new ArrayList<>(courses);
    }

    public List<Course> getEnrolledCourses(Student student) {
        List<Course> result = new ArrayList<>();
        List<String> enrolledIds = student.getEnrolledCourseIds();

        for (Course c : courses) {
            if (enrolledIds.contains(c.getCourseId())) {
                result.add(c);
            }
        }
        return result;
    }

    public void enrollCourse(Student student, String courseId) throws IOException {
        Course course = findCourseById(courseId);
        System.out.println(course.getCourseId());
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        String studentId = student.getUserId();
        System.out.println(studentId);
        if (!student.getEnrolledCourseIds().contains(courseId)) {
            System.out.println("added");
            student.enroll(courseId);
        }

        if (!course.getStudentIds().contains(studentId)) {
            course.getStudentIds().add(studentId);
        }

        db.saveUsers(users);
        db.saveCourses(courses);
        System.out.println("done");
    }

    public List<Lesson> getCourseLessons(String courseId) {
        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }
        return new ArrayList<>(course.getLessons());
    }

    public void completeLesson(Student student, String courseId, String lessonId) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found: " + courseId);
        }

        if (!student.getEnrolledCourseIds().contains(courseId)) {
            throw new IllegalStateException("Student is not enrolled in this course");
        }

        boolean exists = course.getLessons().stream()
                .anyMatch(l -> l.getLessonId().equals(lessonId));

        if (!exists) {
            throw new IllegalArgumentException("Lesson not found: " + lessonId);
        }
        String key = student.getUserId() + "|" + courseId;
        Set<String> completed = completedLessonsMap
                .computeIfAbsent(key, k -> new HashSet<>());
        completed.add(lessonId);

        updateStudentProgress(student);

        db.saveUsers(users);
    }

    public Map<String, Set<String>> getCompletedLessonsMap() {
        return completedLessonsMap;
    }



    private void updateStudentProgress(Student student) {
        List<String> enrolledCourseIds = student.getEnrolledCourseIds();
        if (enrolledCourseIds.isEmpty()) {
            student.setProgress(0.0);
            return;
        }
        int completedCourses = 0;

        for (String cId : enrolledCourseIds) {
            Course c = findCourseById(cId);
            if (c == null) {
                continue;
            }

            String key = student.getUserId() + "|" + cId;
            Set<String> completedLessonIds = completedLessonsMap.get(key);
            if (c.getLessons().isEmpty()) {
                continue;
            }

            if (completedLessonIds != null && completedLessonIds.size() == c.getLessons().size()) {
                completedCourses++;
            }
        }

        double progress = (double) completedCourses / enrolledCourseIds.size();
        student.setProgress(progress);
    }

    private Course findCourseById(String id) {
        for (Course c : courses) {
            if (c.getCourseId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
