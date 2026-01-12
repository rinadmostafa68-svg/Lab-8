package backendpkg.quiz;

import java.util.List;

public class Question {
    private String id;
    private String text;
    private List<String> options;
    private int correctOptionIndex;

    public Question() {}

    public Question(String id, String text, List<String> options, int correctOptionIndex) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getText(){ return text; }
    public void setText(String text){ this.text = text; }
    public List<String> getOptions(){ return options; }
    public void setOptions(List<String> options){ this.options = options; }
    public int getCorrectOptionIndex(){ return correctOptionIndex; }
    public void setCorrectOptionIndex(int i){ this.correctOptionIndex = i; }
}
