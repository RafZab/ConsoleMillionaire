package Service;

import Model.Question;

import java.util.ArrayList;

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

    private void mockData(){
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
        poolQuestion.add(new Question("Ile sekund to 5 minut?", "300", "60", "40","180"));
    }
}