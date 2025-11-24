/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backendpkg;

import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Zaid&Lama&joody
 */
public class Admin extends User {

    public Admin(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, Role.ADMIN);

    }

    public void approveCourse(Course course) {
        course.setStatus("APPROVED");
    }

    public void rejectCourse(Course course) {
        course.setStatus("REJECTED");
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        return obj;
    }

    public static Admin fromJson(JSONObject obj) {
        String userId = obj.getString("userId");
        String username = obj.getString("username");
        String email = obj.getString("email");
        String passwordHash = obj.getString("passwordHash");
        return new Admin(userId, username, email, passwordHash);
    }
}
