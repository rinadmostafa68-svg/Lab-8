package backendpkg;

import org.json.JSONObject;

public abstract class User implements Record {
    protected String userId;
    protected String username;
    protected String email;
    protected String passwordHash;
    protected Role role;

    protected User(String userId, String username, String email,String passwordHash, Role role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("username", username);
        obj.put("email", email);
        obj.put("passwordHash", passwordHash);
        obj.put("role", role.name());
        return obj;
    }
}
