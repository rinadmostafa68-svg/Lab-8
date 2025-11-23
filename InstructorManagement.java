package backendpkg;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstructorManagement {

    private int cid = 0;
    private int lid = 0;
    final List<Course> courses;
    private final List<User> users;
    private Database db;

    public InstructorManagement(Database db) throws IOException {
        cid = 0;
        this.db = db;
        this.courses = db.loadCourses();
        this.users = db.loadUsers();
        for (Course c : courses) {
            try {
                int id = Integer.parseInt(c.getCourseId());
                if (id >= cid) cid = id + 1;
            } catch (NumberFormatException ignore) {}
        }
        for (Course c : courses) {
            for (Lesson l : c.getLessons()) {
                try {
                    int lidVal = Integer.parseInt(l.getLessonId());
                    if (lidVal >= lid) lid = lidVal + 1;
                } catch (NumberFormatException ignore) {}
            }
        }
    }

    public Course createCourse(Instructor instructor, String title, String description) throws IOException {
        String courseId = String.valueOf(cid++);
        Course course = new Course(courseId, title, description, instructor.getUserId(), new ArrayList<>(), new ArrayList<>());
        courses.add(course);
        instructor.getCreatedCourseIds().add(courseId);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(instructor.getUserId())) {
                users.set(i, instructor);
                break;
            }
        }
        db.saveCourses(courses);
        db.saveUsers(users);
        return course;
    }

    public void editCourse(String courseId, String newTitle, String newDescription) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found: " + courseId);
        course.setTitle(newTitle);
        course.setDescription(newDescription);
        db.saveCourses(courses);
    }

    public void deleteCourse(String courseId) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found");

        User u = findUserById(course.getInstructorId());
        if (u instanceof Instructor inst) {
            inst.getCreatedCourseIds().remove(courseId);
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserId().equals(inst.getUserId())) {
                    users.set(i, inst);
                    break;
                }
            }
        }

        for (User usr : users) {
            if (usr instanceof Student s) {
                if (s.getEnrolledCourseIds().contains(courseId)) {
                    s.getEnrolledCourseIds().remove(courseId);
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getUserId().equals(s.getUserId())) {
                            users.set(i, s);
                            break;
                        }
                    }
                }
            }
        }

        courses.remove(course);
        db.saveCourses(courses);
        db.saveUsers(users);
    }

    public Lesson addLesson(String courseId, String title, String content, List<String> resources) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found: " + courseId);
        String lessonId = "" + (lid++);
        Lesson lesson = new Lesson(lessonId, courseId, title, content, resources != null ? resources : new ArrayList<>());
        course.getLessons().add(lesson);
        db.saveCourses(courses);
        return lesson;
    }

    public void editLesson(String courseId, String lessonId, String newTitle, String newContent, List<String> newResources) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found: " + courseId);
        Lesson target = null;
        for (Lesson lesson : course.getLessons()) {
            if (lesson.getLessonId().equals(lessonId)) {
                target = lesson;
                break;
            }
        }
        if (target == null) throw new IllegalArgumentException("Lesson not found: " + lessonId);
        if (newTitle != null) target.setTitle(newTitle);
        if (newContent != null) target.setContent(newContent);
        if (newResources != null) target.setResources(newResources);
        db.saveCourses(courses);
    }

    public void deleteLesson(String courseId, String lessonId) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found: " + courseId);
        Lesson target = null;
        for (Lesson lesson : course.getLessons()) {
            if (lesson.getLessonId().equals(lessonId)) {
                target = lesson;
                break;
            }
        }
        if (target == null) throw new IllegalArgumentException("Lesson not found: " + lessonId);
        course.getLessons().remove(target);
        db.saveCourses(courses);
    }

    public List<Student> getEnrolledStudents(String courseId) {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found");
        List<Student> result = new ArrayList<>();
        for (String studentId : course.getStudentIds()) {
            User u = findUserById(studentId);
            if (u instanceof Student s) result.add(s);
        }
        return result;
    }








    // updated from here

    public void recordLessonCompletion(String studentId, String courseId, String lessonId) throws IOException {
        Course course = findCourseById(courseId);
        if (course == null) throw new IllegalArgumentException("Course not found");

        Lesson lesson = null;
        for (Lesson l : course.getLessons()) {
            if (l.getLessonId().equals(lessonId)) {
                lesson = l;
                break;
            }
        }
        if (lesson == null) throw new IllegalArgumentException("Lesson not found");

        List<String> completed = lesson.getAnalytics().getStudentsCompleted();
        if (!completed.contains(studentId)) {
            completed.add(studentId);
            db.saveCourses(courses);
        }
    }

    public void recordQuizResult(String studentId, String courseId, String lessonId, int scorePercent) throws IOException {
        Course c = findCourseById(courseId);
        if (c == null) throw new IllegalArgumentException("Course not found");

        Lesson target = null;
        for (Lesson l : c.getLessons()) {
            if (l.getLessonId().equals(lessonId)) {
                target = l;
                break;
            }
        }
        if (target == null) throw new IllegalArgumentException("Lesson not found");

        target.getAnalytics().getQuizScores().put(studentId, scorePercent);
        db.saveCourses(courses);
    }

    public JSONObject getLessonAnalytics(String courseId, String lessonId) {
        Course c = findCourseById(courseId);
        if (c == null) throw new IllegalArgumentException("Course not found");

        for (Lesson l : c.getLessons()) {
            if (l.getLessonId().equals(lessonId)) {
                JSONObject obj = new JSONObject();
                LessonAnalytics a = l.getAnalytics();
                obj.put("studentsCompleted", a.getStudentsCompleted());
                obj.put("quizScores", a.getQuizScores());
                return obj;
            }
        }
        throw new IllegalArgumentException("Lesson not found");
    }

    public JSONObject getCourseAnalytics(String courseId) {
        Course c = findCourseById(courseId);
        if (c == null) throw new IllegalArgumentException("Course not found");

        JSONObject result = new JSONObject();
        int totalLessons = c.getLessons().size();
        int totalCompletions = 0;
        double quizScoreSum = 0;
        int quizScoreCount = 0;

        for (Lesson l : c.getLessons()) {
            LessonAnalytics a = l.getAnalytics();
            totalCompletions += a.getStudentsCompleted().size();

            for (int score : a.getQuizScores().values()) {
                quizScoreSum += score;
                quizScoreCount++;
            }
        }

        double avgQuiz = quizScoreCount == 0 ? 0 : quizScoreSum / quizScoreCount;

        result.put("totalLessons", totalLessons);
        result.put("totalLessonCompletions", totalCompletions);
        result.put("averageQuizScore", avgQuiz);

        return result;
    }

    private Course findCourseById(String id) {
        for (Course c : courses) {
            if (c.getCourseId().equals(id)) return c;
        }
        return null;
    }

    private User findUserById(String id) {
        for (User u : users) {
            if (u.getUserId().equals(id)) return u;
        }
        return null;
    }

    public List<Lesson> getCourseLessons(String courseId) {
        Course c = findCourseById(courseId);
        if (c == null) throw new IllegalArgumentException("Course not found: " + courseId);
        return new ArrayList<>(c.getLessons());
    }
}