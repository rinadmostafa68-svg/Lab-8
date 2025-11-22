package backendpkg;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Lesson implements Record {

    private String lessonId;
    private String courseId;
    private String title;
    private String content;
    private List<String> resources;

    public Lesson(String lessonId,
            String courseId,
            String title,
            String content,
            List<String> resources) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.resources = resources != null ? resources : new ArrayList<>();
    }


    public String getLessonId() {
        return lessonId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("lessonId", lessonId);
        obj.put("courseId", courseId);
        obj.put("title", title);
        obj.put("content", content);
        obj.put("resources", new JSONArray(resources));
        return obj;
    }

    public static Lesson fromJson(JSONObject obj) {
        String lessonId = obj.getString("lessonId");
        String courseId = obj.getString("courseId");
        String title = obj.getString("title");
        String content = obj.getString("content");

        List<String> resources = new ArrayList<>();
        JSONArray arr = obj.optJSONArray("resources");
        if (arr != null) {
            for (int i = 0; i < arr.length(); i++) {
                resources.add(arr.getString(i));
            }
        }

        return new Lesson(lessonId, courseId, title, content, resources);
    }
}
