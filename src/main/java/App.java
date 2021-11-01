import Model.Statistic;
import Service.GameSerive;
import Service.MusicPlayer;
import Service.QuestionService;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import javazoom.jl.decoder.JavaLayerException;
import org.ietf.jgss.GSSContext;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class App {
    private Window window;
    private WindowBasedTextGUI textGUI;
    private GameSerive gameSerive = new GameSerive();
    private MusicPlayer musicPlayer = new MusicPlayer();
    Screen screen;

    private int questionCount = 0;

    private void welcomeDialog() {
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));

        Panel panel = new Panel();
        panel.withBorder(Borders.singleLine("Welcome"));

        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panel.addComponent(new Label("Welcome in Millionaire Quiz!"));
        panel.addComponent(new EmptySpace(new TerminalSize(0, 2)));

        panel.addComponent(new Label("Enter nickname:"));
        TextBox nick = new TextBox().addTo(panel);
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));


        Panel panelToButton = new Panel();
        panelToButton.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panelToButton.addComponent(new EmptySpace(new TerminalSize(6, 0)));

        Button okButton = new Button("Continue", () -> {authUser(nick.getText());}).addTo(panelToButton);

        panelToButton.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Button exitButton = new Button("CLOSE", this::handleClose).addTo(panelToButton);

        panel.addComponent(panelToButton);

        window.setComponent(panel);
    }

    private void authUser(String nick){
        String checkNick = nick.trim();
        if(!checkNick.isEmpty()){
            gameSerive.setUser(checkNick);
            new MessageDialogBuilder()
                    .setTitle("Welcome")
                    .setText("Good luck " + checkNick + " in Millionaire game!")
                    .addButton(MessageDialogButton.Continue)
                    .build()
                    .showDialog(textGUI);
            showMainMenu();
        } else {
            new MessageDialogBuilder()
                    .setTitle("Fail")
                    .setText("Enter your nick!")
                    .addButton(MessageDialogButton.Continue)
                    .build()
                    .showDialog(textGUI);
        }
    }

    private void setUpWindow() throws IOException {
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

        Label smallLogo = new Label("   __  _________   __   ________  _  _____   _______  ____\n" +
                "  /  |/  /  _/ /  / /  /  _/ __ \\/ |/ / _ | /  _/ _ \\/ __/\n" +
                " / /|_/ // // /__/ /___/ // /_/ /    / __ |_/ // , _/ _/  \n" +
                "/_/  /_/___/____/____/___/\\____/_/|_/_/ |_/___/_/|_/___/  \n" +
                "                                                          ");
        panel.addComponent(smallLogo);

        Panel panelPlay = new Panel();
        panelPlay.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        panelPlay.addComponent(new Label("                        "));
        Button playButton = new Button("PLAY");
        playButton.addListener(button -> {
            try {
                startPlayGame();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        panelPlay.addComponent(playButton);

        panel.addComponent(panelPlay);

        Panel panelStatistics = new Panel();
        panelStatistics.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        panelStatistics.addComponent(new Label("                      "));
        Button statisticsButton = new Button("STATISTICS");
        statisticsButton.addListener(button -> showStatistics());
        panelStatistics.addComponent(statisticsButton);

        panel.addComponent(panelStatistics);

        Panel panelAddQuestion = new Panel();
        panelAddQuestion.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        panelAddQuestion.addComponent(new Label("                     "));
        Button addQuestion = new Button("ADD QUESTION");
        addQuestion.addListener(button -> showAddQuestionForm());
        panelAddQuestion.addComponent(addQuestion);

        panel.addComponent(panelAddQuestion);

        Panel panelClose = new Panel();
        panelClose.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        panelClose.addComponent(new Label("                        "));
        Button exitButton = new Button("CLOSE");
        exitButton.addListener(button -> handleClose());
        panelClose.addComponent(exitButton);

        panel.addComponent(panelClose);

        window.setComponent(panel);
    }

    public void showStatistics(){
        ArrayList<Statistic> statistics = gameSerive.getStatistics();

        Panel panel = new Panel();
        Table<String> table = new Table<>("Number", "Nick", "Prize", "Date");

        for(int i = 0; i < 10; i++){
            if(statistics.size() <= i){
                table.getTableModel().addRow(Integer.toString(i+1), "","","");
            } else {
                table.getTableModel().addRow(Integer.toString(i+1), statistics.get(i).getNick() ,Integer.toString(statistics.get(i).getWin()), statistics.get(i).getDate());
            }
        }
        panel.addComponent(table);

        Panel panelToButton = new Panel();
        panelToButton.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        if(statistics.size() == 0)
            panelToButton.addComponent(new EmptySpace(new TerminalSize(7, 0)));
        else
            panelToButton.addComponent(new EmptySpace(new TerminalSize(13, 0)));
        Button okButton = new Button("OK", this::showMainMenu).addTo(panelToButton);

        panel.addComponent(panelToButton);
        window.setComponent(panel);
    }

    private void startPlayGame() throws JavaLayerException {
        gameSerive.shuffleQuestions();
        playGame();
    }

    private void playGame() throws JavaLayerException {
        musicPlayer.play("/mp3/Correct.mp3");
        Panel panelMain = new Panel();

        panelMain.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Label logo = new Label("███╗   ███╗██╗██╗     ██╗     ██╗ ██████╗ ███╗   ██╗ █████╗ ██╗██████╗ ███████╗\n" +
                "████╗ ████║██║██║     ██║     ██║██╔═══██╗████╗  ██║██╔══██╗██║██╔══██╗██╔════╝\n" +
                "██╔████╔██║██║██║     ██║     ██║██║   ██║██╔██╗ ██║███████║██║██████╔╝█████╗  \n" +
                "██║╚██╔╝██║██║██║     ██║     ██║██║   ██║██║╚██╗██║██╔══██║██║██╔══██╗██╔══╝  \n" +
                "██║ ╚═╝ ██║██║███████╗███████╗██║╚██████╔╝██║ ╚████║██║  ██║██║██║  ██║███████╗\n" +
                "╚═╝     ╚═╝╚═╝╚══════╝╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚══════╝\n" +
                "                                                                               ");
        panelMain.addComponent(logo);

        panelMain.addComponent(new EmptySpace(new TerminalSize(0, 2)));
        Panel panel = new Panel();

        Panel questionPanel = new Panel();
        questionPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        questionPanel.addComponent(new EmptySpace(new TerminalSize(16, 0)));
        Label questionTitle = new Label("Question number " + (questionCount + 1));
        questionPanel.addComponent(questionTitle);

        panel.addComponent(questionPanel);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        Label questionContent = new Label(gameSerive.getQuestion(questionCount));
        panel.addComponent(questionContent);

        RadioBoxList<String> radioBoxList = new RadioBoxList<>(new TerminalSize(50, 10));

        List<String> answers = gameSerive.getAnswers(questionCount);
        // shuffle the order answers
        Collections.shuffle(answers);
        answers.forEach(radioBoxList::addItem);
        panel.addComponent(radioBoxList);

        Panel panelToButton = new Panel();
        panelToButton.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panelToButton.addComponent(new Label("          "));
        Button submit = new Button("Submit");
       submit.addListener(button -> {
           try {
               checkAnswer(radioBoxList.getCheckedItem());
           } catch (JavaLayerException e) {
               e.printStackTrace();
           }
       });
        panelToButton.addComponent(submit);

        panelToButton.addComponent(new Label("      "));

        Button end = new Button("End game");
        end.addListener(button -> {
            try {
                endGame();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
        panelToButton.addComponent(end);

        panelToButton.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        panel.addComponent(panelToButton);

//        right Panel

        Panel rightPanel = new Panel();

        rightPanel.addComponent(new EmptySpace(new TerminalSize(4, 4)));

        Panel textPanel = new Panel();
        textPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        textPanel.addComponent(new Label("You play for"));
        textPanel.addComponent(new Label(gameSerive.getPrize(questionCount) + " PLN!").setForegroundColor(TextColor.ANSI.RED));

        rightPanel.addComponent(textPanel);
        rightPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Panel textWinPanel = new Panel();
        textWinPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        textWinPanel.addComponent(new Label("You won"));
        textWinPanel.addComponent(new Label(gameSerive.getPrize(questionCount - 1) + " PLN!").setForegroundColor(TextColor.ANSI.YELLOW));

        rightPanel.addComponent(textWinPanel);
        rightPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        rightPanel.addComponent(new Label("Your guaranteed amount"));
        rightPanel.addComponent(new Label("is " + gameSerive.getgGuaranteedPrize(questionCount - 1) + " PLN!").setForegroundColor(TextColor.ANSI.GREEN));

        Panel twoMenuPanel = new Panel();
        twoMenuPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        twoMenuPanel.addComponent(panel);
        twoMenuPanel.addComponent(rightPanel);

        panelMain.addComponent(twoMenuPanel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        window.setComponent(panelMain);
    }

    private AnimatedLabel getFirework(){
        AnimatedLabel firework = new AnimatedLabel("\n" + "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n" + "\n" + "\n" + "\n" + "\n" +
                "           _ \n" +
                "          | |\n" +
                "          | |\n" +
                "          | |\n" +
                "          |_|");

        firework.addFrame( "\n" + "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n" +
                "           _ \n" +
                "          | |\n" +
                "          | |\n" +
                "          | |\n" +
                "          |_|\n" +
                "          | |\n" +
                "          | |\n" +
                "          | |\n" +
                "          |_|");

        firework.addFrame( "\n" + "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+ "\n"+
                "                  \n" +
                "      _/\\__/\\__/\\_\n" +
                "      >  <>  <>  <\n" +
                "       \\/  \\/  \\/ \n" +
                "          | |     \n" +
                "          | |     \n" +
                "          | |     \n" +
                "          |_|     \n" +
                "          | |     \n" +
                "          | |     \n" +
                "          | |     \n" +
                "          |_|     ");

        firework.addFrame( "\n" + "\n"+ "\n"+ "\n"+
                "                     \n" +
                "  _/\\__/\\__/\\__/\\__/\\_\n" +
                "  >  <>  <>  <>  <>  <\n" +
                "   \\/  \\/  \\/  \\/  \\/ \n" +
                "      _/\\__/\\__/\\_    \n" +
                "      >  <>  <>  <    \n" +
                "       \\/  \\/  \\/     \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          |_|         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          |_|         ");

        firework.addFrame("\n"+ "\n"+
                "                      \n" +
                "    _/\\__/\\__/\\__/\\_  \n" +
                "    >  <>  <>  <>  <  \n" +
                "  _/\\\\//\\\\//\\\\//\\\\//\\_\n" +
                "  >  <>  <>  <>  <>  <\n" +
                "   \\/  \\/  \\/  \\/  \\/ \n" +
                "      _/\\__/\\__/\\_    \n" +
                "      >  <>  <>  <    \n" +
                "       \\/  \\/  \\/     \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          |_|         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          |_|         ");

        firework.addFrame("                      \n" +
                "      _/\\__/\\__/\\_    \n" +
                "      >  <>  <>  <    \n" +
                "    _/\\\\//\\\\//\\\\//\\_  \n" +
                "    >  <>  <>  <>  <  \n" +
                "  _/\\\\//\\\\//\\\\//\\\\//\\_\n" +
                "  >  <>  <>  <>  <>  <\n" +
                "   \\/  \\/  \\/  \\/  \\/ \n" +
                "      _/\\__/\\__/\\_    \n" +
                "      >  <>  <>  <    \n" +
                "       \\/  \\/  \\/     \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          |_|         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          | |         \n" +
                "          |_|         ");
        firework.startAnimation(200);
        return  firework;
    }

    private void checkAnswer(String answer) throws JavaLayerException {
        if(gameSerive.getCorrectAnswer(questionCount).equals(answer)){
            if(questionCount < 11){
                questionCount++;
                new MessageDialogBuilder()
                        .setTitle("Success")
                        .setText("Correct answer! Continue to next question :)")
                        .addButton(MessageDialogButton.OK)
                        .build()
                        .showDialog(textGUI);
                playGame();
            } else {
                new MessageDialogBuilder()
                        .setTitle("Congratulations!")
                        .setText("You are the winner!")
                        .addButton(MessageDialogButton.OK)
                        .build()
                        .showDialog(textGUI);
                questionCount++;
                endGame();
            }
        } else {
            new MessageDialogBuilder()
                    .setTitle("Wrong")
                    .setText("Wrong answer. End game :(")
                    .addButton(MessageDialogButton.OK)
                    .build()
                    .showDialog(textGUI);
            loseGame();
        }
    }

    private void endGame() throws JavaLayerException {
        if(questionCount == 0) {
            gameSerive.addStatistic(0);
            winnerInfo(0);
        }else {
            int win = gameSerive.getWinner(questionCount -1);
            winnerInfo(win);
        }
    }

    private void loseGame() throws JavaLayerException {
        int win = gameSerive.getLoseWinner(questionCount - 1);
        winnerInfo(win);
    }

    private void winnerInfo(int win) throws JavaLayerException {
        musicPlayer.play("/mp3/Over.mp3");
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        mainPanel.addComponent(getFirework());

        Panel panel = new Panel();

        panel.addComponent(new EmptySpace(new TerminalSize(0, 10)));
        Label title = new Label("Congratulations, " + gameSerive.getNick() + "!").setForegroundColor(TextColor.ANSI.GREEN);
        Label winner = new Label("You winner " + win + " PLN!").setForegroundColor(TextColor.ANSI.GREEN);

        panel.addComponent(title);
        panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        panel.addComponent(winner);

        Panel panelToButton = new Panel();
        panelToButton.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Button ok = new Button("OK");
        ok.addListener(button -> {
            showMainMenu();
            questionCount = 0;
        });
        panelToButton.addComponent(new Label("             "));
        panelToButton.addComponent(ok);

        panel.addComponent(new EmptySpace(new TerminalSize(0, 2)));
        panel.addComponent(panelToButton);

        mainPanel.addComponent(panel);
        mainPanel.addComponent(getFirework());
        window.setComponent(mainPanel);
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

        panel.addComponent(new Label(""));

        Panel panelToButton = new Panel();
        panelToButton.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        new Button("SUBMIT", () -> this.handleNewQuestion(question.getText(), correctAnswer.getText(), incorrectAnswer1.getText(), incorrectAnswer2.getText(), incorrectAnswer3.getText())).addTo(panelToButton);
        new Button("CLOSE", this::showMainMenu).addTo(panelToButton);
        panel.addComponent(panelToButton);

        window.setComponent(panel);
    }
    private void handleNewQuestion(String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3){
        if(!question.isEmpty() && !correctAnswer.isEmpty() && !incorrectAnswer1.isEmpty() && !incorrectAnswer2.isEmpty() && !incorrectAnswer3.isEmpty()){
            this.gameSerive.addQuestion(question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);
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

            textGUI.addWindowAndWait(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
