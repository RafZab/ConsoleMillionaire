package Model;

import java.util.ArrayList;

public class Question {
    private String question;
    private String correctAnswer;
    private String incorrectAnswer1;
    private String incorrectAnswer2;
    private String incorrectAnswer3;

    public Question(String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer1 = incorrectAnswer1;
        this.incorrectAnswer2 = incorrectAnswer2;
        this.incorrectAnswer3 = incorrectAnswer3;
    }

    public String getQuestion(){
        return this.question;
    }

    public String getCorrectAnswer(){
        return this.correctAnswer;
    }

    public ArrayList<String> getAnswers(){
        ArrayList<String> result = new ArrayList<>();
        result.add(this.correctAnswer);
        result.add(this.incorrectAnswer1);
        result.add(this.incorrectAnswer2);
        result.add(this.incorrectAnswer3);
        return result;
    }

}
