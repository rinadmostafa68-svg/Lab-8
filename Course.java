package backendpkg;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Course implements Record {

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private List<Lesson> lessons;
    private List<String> studentIds;

    public Course(String courseId,
            String title,
            String description,
            String instructorId,
            List<Lesson> lessons,
            List<String> studentIds) {

        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = lessons != null ? lessons : new ArrayList<>();
        this.studentIds = studentIds != null ? studentIds : new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<String> getStudentIds() {
        return studentIds;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("courseId", courseId);
        obj.put("title", title);
        obj.put("description", description);
        obj.put("instructorId", instructorId);

       
        JSONArray lessonsArray = new JSONArray();
        for (Lesson lesson : lessons) {
            lessonsArray.put(lesson.toJson());
        }
        obj.put("lessons", lessonsArray);

        
        obj.put("students", new JSONArray(studentIds));

        return obj;
    }

    public static Course fromJson(JSONObject obj) {

        String courseId = obj.getString("courseId");
        String title = obj.getString("title");
        String description = obj.getString("description");
        String instructorId = obj.getString("instructorId");

        
        List<Lesson> lessons = new ArrayList<>();
        JSONArray lessonsArray = obj.optJSONArray("lessons");
        if (lessonsArray != null) {
            for (int i = 0; i < lessonsArray.length(); i++) {
                JSONObject lessonObj = lessonsArray.getJSONObject(i);
                lessons.add(Lesson.fromJson(lessonObj));
            }
        }

       
        List<String> studentIds = new ArrayList<>();
        JSONArray studentsArray = obj.optJSONArray("students");
        if (studentsArray != null) {
            for (int i = 0; i < studentsArray.length(); i++) {
                studentIds.add(studentsArray.getString(i));
            }
        }

        return new Course(courseId, title, description, instructorId,
                lessons, studentIds);
    }
}
