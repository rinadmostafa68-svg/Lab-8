package backendpkg;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class LessonAnalytics {

    private List<String> studentsCompleted = new ArrayList<>();
    private Map<String, Integer> quizScores = new HashMap<>();

    public LessonAnalytics() {}

    public List<String> getStudentsCompleted() {
        return studentsCompleted;
    }

    public Map<String, Integer> getQuizScores() {
        return quizScores;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("studentsCompleted", new JSONArray(studentsCompleted));

        JSONObject scoreObj = new JSONObject();
        for (String key : quizScores.keySet()) {
            scoreObj.put(key, quizScores.get(key));
        }
        obj.put("quizScores", scoreObj);

        return obj;
    }

    public static LessonAnalytics fromJson(JSONObject obj) {
        LessonAnalytics analytics = new LessonAnalytics();

        JSONArray compArr = obj.optJSONArray("studentsCompleted");
        if (compArr != null) {
            for (int i = 0; i < compArr.length(); i++) {
                analytics.studentsCompleted.add(compArr.getString(i));
            }
        }

        JSONObject scoreObj = obj.optJSONObject("quizScores");
        if (scoreObj != null) {
            for (String key : scoreObj.keySet()) {
                analytics.quizScores.put(key, scoreObj.getInt(key));
            }
        }

        return analytics;
    }
}