package backendpkg.quiz;

import java.util.*;

public class AssessmentService {
    private final Map<String, List<String>> assessments = new HashMap<>(); // assessmentId -> list of quizIds
    private final QuizService quizService;

    public AssessmentService(QuizService qs){
        this.quizService = qs;
    }

    public void createAssessment(String assessmentId, List<String> quizIds){
        assessments.put(assessmentId, new ArrayList<>(quizIds));
    }

    public Optional<List<String>> getAssessmentQuizIds(String assessmentId){
        return Optional.ofNullable(assessments.get(assessmentId));
    }

    public Map<String,Object> gradeAssessment(String assessmentId, Map<String,Integer> answers){
        List<String> qids = assessments.get(assessmentId);
        if(qids==null) throw new IllegalArgumentException("Assessment not found: " + assessmentId);
        int totalQs = 0;
        int totalCorrect = 0;
        List<Map<String,Object>> perQuiz = new ArrayList<>();
        for(String qid : qids){
            Optional<Quiz> oq = quizService.getQuiz(qid);
            if(oq.isEmpty()) continue;
            Quiz quiz = oq.get();
            Map<String,Integer> quizAnswers = new HashMap<>();
            for(Question qs : quiz.getQuestions()){
                if(answers.containsKey(qs.getId())){
                    quizAnswers.put(qs.getId(), answers.get(qs.getId()));
                }
            }
            Map<String,Object> graded = quizService.gradeQuiz(quiz.getId(), quizAnswers);
            perQuiz.add(graded);
            Integer correct = (Integer)graded.get("correct");
            Integer total = (Integer)graded.get("total");
            totalQs += total==null?0:total;
            totalCorrect += correct==null?0:correct;
        }
        int percent = totalQs==0?0:(int)Math.round(100.0 * totalCorrect / totalQs);
        Map<String,Object> report = new HashMap<>();
        report.put("totalQuestions", totalQs);
        report.put("totalCorrect", totalCorrect);
        report.put("percent", percent);
        report.put("perQuiz", perQuiz);
        report.put("passed", percent >= 60); // default pass threshold
        return report;
    }
}
