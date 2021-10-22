package Service;

import Model.Question;
import Model.User;

import java.util.ArrayList;

public class GameSerive {
    private QuestionService questionService;
    private PrizeService prizeService;
    private User user;

    public GameSerive(){
        questionService = new QuestionService();
        prizeService = new PrizeService();
        user = new User("Gosia");
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

    public String getCorrectAnswer(int numberQuestion){
        return questionService.getCorrectAnswer(numberQuestion);
    }

    public int getLoseWinner(int numberQuestion){
        if(numberQuestion >= 11){
            return getWinner(11);
        }
        else if(numberQuestion >= 6){
            return getWinner(6);
        }
        else if(numberQuestion >= 1){
            return getWinner(1);
        } else {
            return 0;
        }
    }

    public int getWinner(int numberQuestion){
        return prizeService.getWinner(numberQuestion);
    }

    public String getNick(){
        return user.getNick();
    }

    public void shuffleQuestions(){
        questionService.shuffleQuestions();
    }

}
