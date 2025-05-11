import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import java.util.ArrayList;

// Question class
class Question {
    String text;
    boolean isCritical;
    boolean isExitOnZero;

    public Question(String text, boolean isCritical, boolean isExitOnZero) {
        this.text = text;
        this.isCritical = isCritical;
        this.isExitOnZero = isExitOnZero;
    }
}

// Game logic class
class Game {
    int score = 10000;
    int currentQuestion = 0;
    ArrayList<Question> questions = new ArrayList<>();

    public Game() {
        questions.add(new Question("Your health and happiness matter the most. Do you want to feel more energetic, look your best, and take steps toward a healthier version of yourself?\nIf yes, press 1. If not, press 0.", false, false));
        questions.add(new Question("Time is the most precious gift. Would you like to invest it in building your dreams and becoming the best version of yourself?\nIf yes, press 1. If not, press 0.", false, false));
        questions.add(new Question("This one is important. Are you single and open to something genuine with someone who truly cares?\nIf yes, press 1. If your heart already belongs to someone else, press 0.", true, true));
        questions.add(new Question("What kind of heart are you drawn to more?\nSomeone kind, respectful and sincere (press 1), or someone wild and unpredictable (press 0)?", false, false));
        questions.add(new Question("Spending time with you always felt like a beautiful moment.\nIf you'd like to hear the thousand thoughts I still have for you, press 1. If not, press 0.", false, false));
        questions.add(new Question("I’ve always wondered what inspires you the most. Do you believe in chasing your dreams no matter how hard it gets?\nIf yes, press 1. If you feel life should just go with the flow, press 0.", false, false));
        questions.add(new Question("Some conversations stay in our hearts forever. Have you ever felt that with me — like time just flies when we talk?\nIf yes, press 1. If not really, press 0.", false, false));
        questions.add(new Question("I often smile remembering our little moments. Do you have a favorite memory of us that makes you smile?\nIf yes, press 1. If not yet, press 0.", false, false));
        questions.add(new Question("Trust is rare, and comfort is priceless. Do you feel comfortable sharing things with me?\nIf yes, press 1. If not yet, press 0.", false, false));
        questions.add(new Question("If I told you that knowing your heart means the world to me, and this game was my silly way of reaching out—would you give me a chance to talk more?\nIf yes, press 1. If not, press 0.", false, false));
    }

    public boolean answer(int input) {
        if (input == 1) {
            score += 1000;
        } else {
            score -= 500;
            if (questions.get(currentQuestion).isExitOnZero) {
                return false;
            }
        }
        currentQuestion++;
        return true;
    }

    public boolean hasNextQuestion() {
        return currentQuestion < questions.size();
    }

    public Question getNextQuestion() {
        return questions.get(currentQuestion);
    }
}

// Game UI
public class GameUI extends JFrame implements ActionListener {
    JLabel messageLabel, questionLabel, scoreLabel;
    JButton btn1, btn0;
    Game game;
    Font bigFont = new Font("Serif", Font.BOLD, 20);

    public GameUI() {
        game = new Game();

        // Set background image
        setContentPane(new JLabel(new ImageIcon("background.jpg")));
        setLayout(new BorderLayout());

        setTitle("Message from a Secret Admirer 💌");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Romantic message screen
        messageLabel = new JLabel("<html><center>Hello Sakshi,<br><br>" +
                "It’s been such a beautiful journey with you, and I truly cherish every moment we’ve shared.<br>" +
                "I love spending time with you, but there’s something on my mind. I can’t help but wonder about your feelings.<br>" +
                "That’s why I’ve created something special, just for us.<br><br>" +
                "I’ve designed this game, a little puzzle, to help me understand your heart and where we stand.<br>" +
                "I have so many things I want to share with you, thousands of little thoughts, dreams, and wishes.<br>" +
                "But I’ll leave it to this journey to take its course.<br><br>" +
                "I hope we can continue this together. Press 1 if you want to take this step, to uncover the mystery and see what lies ahead. 💖</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(bigFont);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btn1 = new JButton("1");
        btn0 = new JButton("0");
        btn1.setFont(bigFont);
        btn0.setFont(bigFont);
        btn1.addActionListener(this);
        btn0.addActionListener(this);
        buttonPanel.add(btn1);
        buttonPanel.add(btn0);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(bigFont);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel = new JLabel("Score: 10000", SwingConstants.CENTER);
        scoreLabel.setFont(bigFont);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(questionLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scoreLabel, BorderLayout.EAST);

        // Play background music
        playBackgroundMusic("background.wav"); // make sure this file exists in your directory

        setVisible(true);
    }

    private void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    public void nextQuestion() {
        if (game.hasNextQuestion()) {
            Question q = game.getNextQuestion();
            questionLabel.setText("<html><center>" + q.text.replaceAll("\n", "<br>") + "</center></html>");
            scoreLabel.setText("Score: " + game.score);
        } else {
            questionLabel.setText("<html><center>🎉 You’ve completed the game.<br><br>Thank you for going on this little emotional journey with me.<br>No matter your answers, I’m truly grateful for you.<br><br>If you loved this game, send me your final score! ❤<br>Final Score: " + game.score + "</center></html>");
            btn1.setEnabled(false);
            btn0.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = (e.getSource() == btn1) ? 1 : 0;

        if (messageLabel.isVisible()) {
            messageLabel.setVisible(false);
            nextQuestion();
            return;
        }

        boolean continueGame = game.answer(choice);

        if (!continueGame) {
            questionLabel.setText("<html><center>😢 Looks like this journey ends here. Maybe another time!</center></html>");
            btn1.setEnabled(false);
            btn0.setEnabled(false);
        } else {
            nextQuestion();
        }
    }

    public static void main(String[] args) {
        new GameUI();
    }
}
