package backendpkg;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Student extends User {

    private List<String> enrolledCourseIds;
    private double progress;

    public Student(String userId, String username, String email, String passwordHash, List<String> enrolledCourseIds, double progress) {
        super(userId, username, email, passwordHash, Role.STUDENT);
        if (enrolledCourseIds.isEmpty()) {
            this.enrolledCourseIds = new ArrayList<>();
        } else {
            this.enrolledCourseIds = enrolledCourseIds;
        }
        this.progress = progress;
    }

    public List<String> getEnrolledCourseIds() {

        return enrolledCourseIds;
    }

    public void enroll(String courseid) {
        enrolledCourseIds.add(courseid);
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        obj.put("enrolledCourses", enrolledCourseIds);
        obj.put("progress", progress);
        obj.put("type", "STUDENT");
        return obj;
    }

    public static Student fromJson(JSONObject obj) {
        String userId = obj.getString("userId");
        String username = obj.getString("username");
        String email = obj.getString("email");
        String passwordHash = obj.getString("passwordHash");
        List<String> enrolledCourses = obj.getJSONArray("enrolledCourses").toList().stream().map(Object::toString).toList();
        double progress = obj.optDouble("progress", 0.0);
        return new Student(userId, username, email, passwordHash, enrolledCourses, progress);
    }
}
