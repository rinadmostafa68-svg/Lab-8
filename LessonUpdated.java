package backendpkg;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class LessonUpdated implements Record {

    private String lessonId;
    private String courseId;
    private String title;
    private String content;
    private List<String> resources;

    private String quizId;
    private LessonAnalytics analytics;

    public LessonUpdated(String lessonId,
                         String courseId,
                         String title,
                         String content,
                         List<String> resources) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.resources = resources != null ? resources : new ArrayList<>();
        this.analytics = new LessonAnalytics();
        this.quizId = null;
    }

    public LessonUpdated(String lessonId,
                         String courseId,
                         String title,
                         String content,
                         List<String> resources,
                         String quizId,
                         LessonAnalytics analytics) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.resources = resources != null ? resources : new ArrayList<>();
        this.quizId = quizId;
        this.analytics = analytics != null ? analytics : new LessonAnalytics();
    }

    public String getLessonId() { return lessonId; }
    public String getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getResources() { return resources; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setResources(List<String> resources) { this.resources = resources; }

    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }

    public LessonAnalytics getAnalytics() { return analytics; }
    public void setAnalytics(LessonAnalytics a) { this.analytics = a; }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("lessonId", lessonId);
        obj.put("courseId", courseId);
        obj.put("title", title);
        obj.put("content", content);
        obj.put("resources", new JSONArray(resources));
        obj.put("quizId", quizId == null ? JSONObject.NULL : quizId);
        obj.put("analytics", analytics.toJson());
        return obj;
    }

    public static LessonUpdated fromJson(JSONObject obj) {
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

        String quizId = obj.optString("quizId", null);
        if (obj.isNull("quizId")) quizId = null;

        LessonAnalytics analytics = LessonAnalytics.fromJson(obj.optJSONObject("analytics"));

        return new LessonUpdated(lessonId, courseId, title, content, resources, quizId, analytics);
    }
}