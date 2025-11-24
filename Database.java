package backendpkg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Database {

    private final Path usersFile;
    private final Path coursesFile;
    List<User> users = new ArrayList<>();

    public Database(String usersPath, String coursesPath) {
        this.usersFile = Path.of(usersPath);
        this.coursesFile = Path.of(coursesPath);
    }

    private String readFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            return "[]";
        }
        return Files.readString(path);
    }

    private void writeFile(Path path, String content) throws IOException {
        Files.writeString(path, content);
    }

    public List<User> loadUsers() throws IOException {
        if (!users.isEmpty()) {
            return users;
        }
        String json = readFile(usersFile);
        JSONArray arr = new JSONArray(json);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String type = obj.optString("role", "");
            switch (type) {
                case "STUDENT" ->
                    users.add(Student.fromJson(obj));
                case "INSTRUCTOR" ->
                    users.add(Instructor.fromJson(obj));
                case "ADMIN" ->
                    users.add(Admin.fromJson(obj));
                default -> {
                }
            }
        }
        return users;
    }

    public void saveUsers(List<User> users) throws IOException {
        JSONArray arr = new JSONArray();
        for (User u : users) {
            arr.put(u.toJson());
        }
        writeFile(usersFile, arr.toString(2));
    }

    public List<Course> loadCourses() throws IOException {
        String json = readFile(coursesFile);
        JSONArray arr = new JSONArray(json);
        List<Course> courses = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            courses.add(Course.fromJson(obj));
        }

        return courses;
    }

   public void saveCourses(List<Course> courses) throws IOException {
    JSONArray arr = new JSONArray();
    for (Course c : courses) {
        arr.put(c.toJson());
    }
    writeFile(coursesFile, arr.toString(2));
}
    //************************************************************

    public String generateCertificateJSON(Student student, String courseId, String courseTitle) throws IOException {

        Certificate cert = new Certificate(
                student.getUserId(),
                courseId,
                courseTitle
        );

        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("certificateId", cert.getCertificateId());
        jsonMap.put("studentId", cert.getStudentId());
        jsonMap.put("courseId", cert.getCourseId());
        jsonMap.put("courseTitle", cert.getCourseTitle());

        JSONObject json = new JSONObject(jsonMap);

        String jsonString = json.toString(4);
        String fileName = "certificate_" + cert.getCertificateId() + ".json";
        System.out.println(jsonString);

        student.getCertificates().add(cert);

        saveUsers(users);
        return fileName;
    }
}
