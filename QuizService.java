package backendpkg.quiz;

import java.util.*;
import java.util.stream.Collectors;

public class QuizService {
    private final Map<String, Quiz> quizzes = new HashMap<>();

    public QuizService(){}

    public void addQuiz(Quiz q){
        quizzes.put(q.getId(), q);
    }

    public Optional<Quiz> getQuiz(String id){
        return Optional.ofNullable(quizzes.get(id));
    }

    public Map<String,Object> gradeQuiz(String quizId, Map<String,Integer> answers){
        Quiz q = quizzes.get(quizId);
        if(q==null) throw new IllegalArgumentException("Quiz not found: " + quizId);
        int total = q.getQuestions()==null?0:q.getQuestions().size();
        int correct = 0;
        for(Question ques : q.getQuestions()){
            Integer chosen = answers.get(ques.getId());
            if(chosen != null && chosen == ques.getCorrectOptionIndex()) correct++;
        }
        int percent = total==0?0:(int)Math.round(100.0 * correct / total);
        boolean passed = percent >= q.getPassingPercentage();
        Map<String,Object> res = new HashMap<>();
        res.put("scorePercent", percent);
        res.put("passed", passed);
        res.put("correct", correct);
        res.put("total", total);
        return res;
    }

    public List<Quiz> listQuizzes(){
        return new ArrayList<>(quizzes.values());
    }
}
