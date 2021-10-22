package Service;

import Model.Question;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionService {
    private ArrayList<Question> poolQuestion;

    public QuestionService(){
        this.poolQuestion = new ArrayList<Question>();
        mockData();
    }

    public ArrayList<String> getAnswers(int numberQuestion){
        Question question = poolQuestion.get(numberQuestion);
        return question.getAnswers();
    }

    public ArrayList<Question> getQuestions() {
        return poolQuestion;
    }

    public void addQuestion(String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3){
        Question newQuestion = new Question(question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);
        poolQuestion.add(newQuestion);
    }

    public String getQuestion(int numberQuestion){
        Question question = poolQuestion.get(numberQuestion);
        return  question.getQuestion();
    }

    public String getCorrectAnswer(int numberQuestion){
        Question question = poolQuestion.get(numberQuestion);
        return question.getCorrectAnswer();
    }

    public void shuffleQuestions(){
        Collections.shuffle(poolQuestion);
    }

    private void mockData(){
        poolQuestion.add(new Question("1.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("2.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("3.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("4.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("5.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("6.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("7.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("8.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("9.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("10.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("11.Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("12.Ile sekund to 5 minut?", "300", "60", "40","180"));
    }
}
