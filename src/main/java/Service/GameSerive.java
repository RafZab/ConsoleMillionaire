package Service;

import Model.Question;
import Model.User;

import java.util.ArrayList;

public class GameSerive {
    private QuestionService questionService;
    private User user;

    public GameSerive(){
        questionService = new QuestionService();
    }

    public void addQuestion(String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3){
        this.questionService.addQuestion(question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);
    }

    public ArrayList<Question> getAllQuestion(){
        return questionService.getQuestions();
    }

    public String getQuestion(int numberQuestion){
        return questionService.getQuestion(numberQuestion);
    }

    public ArrayList<String> getAnswers(int numberQuestion){
        return questionService.getAnswers(numberQuestion);
    }

    public void shuffleQuestions(){
        questionService.shuffleQuestions();
    }

}
