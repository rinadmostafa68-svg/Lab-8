package backendpkg;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Instructor extends User {

    private List<String> createdCourseIds;

    public Instructor(String userId, String username, String email,
            String passwordHash, List<String> createdCourseIds) {
        super(userId, username, email, passwordHash, Role.INSTRUCTOR);
        this.createdCourseIds = createdCourseIds;
    }

    public List<String> getCreatedCourseIds() {
        return createdCourseIds;
    }

    public void setCreatedCourseIds(List<String> createdCourseIds) {
        this.createdCourseIds = createdCourseIds;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        obj.put("createdCourses", createdCourseIds);
        obj.put("type", "INSTRUCTOR");
        return obj;
    }

    public static Instructor fromJson(JSONObject obj) {
        String userId = obj.getString("userId");
        String username = obj.getString("username");
        String email = obj.getString("email");
        String passwordHash = obj.getString("passwordHash");

        List<String> createdCourses = new ArrayList<>();
        JSONArray arr = obj.optJSONArray("createdCourses");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                createdCourses.add(String.valueOf(arr.get(i)));
            }
        }

        return new Instructor(userId, username, email, passwordHash, createdCourses);
    }
}
