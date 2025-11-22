package backendpkg;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Authentication {

    private final Database db;
    List<User> users;

    public Authentication(Database db) throws IOException {
        this.db = db;
        this.users = db.loadUsers();
    }
    private String hashPassword(String plain) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plain.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
    public User login(String email, String plainPassword) throws IOException {
        String hashed = hashPassword(plainPassword);
        for (User u : users) {
            if (u.getPasswordHash().equals(hashed)
                    && u.getEmail().equalsIgnoreCase(email)) {
                return u; 
            } 
        }
        return null; 
    }
    public boolean emailExists(String email) throws IOException {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    public Student signupStudent(String userId, String username, String email,String plainPassword) throws IOException {
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        String hash = hashPassword(plainPassword);
        
        Student s = new Student(userId, username, email, hash, new ArrayList<>(), 0.0);
        users.add(s);
        db.saveUsers(users);
        return s;
    }
    public Instructor signupInstructor(String userId, String username, String email, String plainPassword) throws IOException {
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        String hash = hashPassword(plainPassword);
        Instructor inst = new Instructor(userId, username, email, hash, new ArrayList<>());
        users.add(inst);
        db.saveUsers(users);
        return inst;
    }
}
