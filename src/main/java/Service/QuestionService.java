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
        poolQuestion.add(new Question("How many months is the quarter?", "Three", "Two", "Five","Once"));
        poolQuestion.add(new Question("When we celebrate women's day?", "8 March", "1st of June", "14 June","8 May"));
        poolQuestion.add(new Question("How many countries borders Poland", "Seven", "Six", "Nine","Five"));
        poolQuestion.add(new Question("How many oceans the equator passes through?", "Three", "Two", "Once","Five"));
        poolQuestion.add(new Question("What is the name of the last letter of the Greek alphabet?", "Omega", "Psi", "Chi","alpha"));
        poolQuestion.add(new Question("A dish with a strainer for watering plants this?", "Watering can", "Plate", "Mug","Colander"));
        poolQuestion.add(new Question("The lightest element is?", "Hydrogen", "Oxygen", "Helium","Carbon"));
        poolQuestion.add(new Question("How many tentacles does an octopus have?", "8", "7", "10","12"));
        poolQuestion.add(new Question("Gloria from the movie \"Madagascar\" was?", "Hippo", "Lion", "Dog","Zebra"));
        poolQuestion.add(new Question("When it's Valentine's Day?", "February", "May", "June","March"));
        poolQuestion.add(new Question("The largest country in the world", "Russia", "USA", "Poland","Brazil"));
        poolQuestion.add(new Question("The largest desert in the world is?", "Sahara", "Gobi", "Nevada","Mojave"));
    }
}
