import Service.QuestionService;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.Console;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class App {
    private Window window;
    private WindowBasedTextGUI textGUI;
    private QuestionService questionService = new QuestionService();
    Screen screen;

    void welcomeDialog() {
        new MessageDialogBuilder()
                .setTitle("Welcome")
                .setText("Welcome in Millionaire Quiz!")
                .addButton(MessageDialogButton.Continue)
                .build()
                .showDialog(textGUI);
    }

    void setUpWindow() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();

        screen = terminalFactory.createScreen();
        screen.startScreen();

        textGUI = new MultiWindowTextGUI(screen);

        window = new BasicWindow("Millionaire Quiz");
    }

    private void showMainMenu() {
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Button playButton = new Button("PLAY");
        //playButton.addListener(button -> playGame());
        panel.addComponent(playButton);

        Button statisticsButton = new Button("STATISTICS");
        //statisticsButton.addListener(button -> showStatistics());
        panel.addComponent(statisticsButton);

        Button addQuestion = new Button("ADD QUESTION");
        addQuestion.addListener(button -> showAddQuestionForm());
        panel.addComponent(addQuestion);

        Button exitButton = new Button("CLOSE");
        exitButton.addListener(button -> handleClose());
        panel.addComponent(exitButton);

        window.setComponent(panel);
    }

    private void showAddQuestionForm() {

        Panel panel = new Panel();

        panel.addComponent(new Label("Question"));
        TextBox question = new TextBox().addTo(panel);

        panel.addComponent(new Label("Correct Answer"));
        TextBox correctAnswer = new TextBox().addTo(panel);

        panel.addComponent(new Label("Incorrect Answer"));
        TextBox incorrectAnswer1 = new TextBox().addTo(panel);

        panel.addComponent(new Label("Incorrect Answer"));
        TextBox incorrectAnswer2 = new TextBox().addTo(panel);

        panel.addComponent(new Label("Incorrect Answer"));
        TextBox incorrectAnswer3 = new TextBox().addTo(panel);

        new Button("SUBMIT", () -> this.handleNewQuestion(question.getText(), correctAnswer.getText(), incorrectAnswer1.getText(), incorrectAnswer2.getText(), incorrectAnswer3.getText())).addTo(panel);

        new Button("CLOSE", this::showMainMenu).addTo(panel);

        window.setComponent(panel);
    }
    private void handleNewQuestion(String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3){
        if(!question.isEmpty() && !correctAnswer.isEmpty() && !incorrectAnswer1.isEmpty() && !incorrectAnswer2.isEmpty() && !incorrectAnswer3.isEmpty()){
            this.questionService.addQuestion(question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);
            new MessageDialogBuilder()
                    .setTitle("Success")
                    .setText("New Question Saved!")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(textGUI);
            showMainMenu();
        } else {
            new MessageDialogBuilder()
                    .setTitle("Failed")
                    .setText("Complete all fields!")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(textGUI);
        }
    }

    private void handleClose() {
        try {
            screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        app.start();
    }

    public void start() throws IOException {
        try {
            setUpWindow();

            welcomeDialog();

            showMainMenu();

            textGUI.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
