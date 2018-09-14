package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pramuditha Buddhini
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Font;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Quiz extends JFrame {

    JPanel p = new JPanel();
    CardLayout cards = new CardLayout();
    int numQs;
    int wrongs = 0;
    int corrects = 0;
    int total = 0;
    int qnumber = 0;

    String[][] answers = {
        {"RAM", "CPU", "Keyboard", "Mouse"},
        {"1TB", "1MB", "1000KB", "1GB"},
        {"Booktop", "Laptop", "Super computer", "CPU"},
        {"Yes", "No"},
        {"Yahoo", "Bing", "Google", "SOSO"}
    };

    RadioQuestion questions[] = {
        new RadioQuestion(
        "What do you use to click stuff?",
        answers[0],
        3, this
        ),
        new RadioQuestion(
        "What memory below is the largest?",
        answers[1],
        0, this
        ),
        new RadioQuestion(
        "What do you call a portable computer?",
        answers[2],
        1, this
        ),
        new RadioQuestion(
        "Does RAM stores information about your computer when it is closed?",
        answers[3],
        1, this
        ),
        new RadioQuestion(
        "What is the most used search engine?",
        answers[4],
        2, this
        )

    };

    public static void main(String args[]) {
        new Quiz();
    }

    public Quiz() {
        super("Quiz");
        setResizable(false);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        p.setLayout(cards);
        numQs = questions.length;
        for (int i = 0; i < numQs; i++) {
            p.add(questions[i], "q" + i);
        }
        Random r = new Random();
        int i = r.nextInt(numQs);
        cards.show(p, "q" + i);
        add(p);
        setVisible(true);
    }

    public void next() {
        if ((total - wrongs) == numQs) {
            showSummary();
        } else {
            Random r = new Random();
            boolean found = false;
            int i = 0;
            while (!found) {
                i = r.nextInt(numQs);
                if (!questions[i].used) {
                    found = true;
                }
            }
            cards.show(p, "q" + i);
        }
    }

    public void showSummary() {

        ImageIcon icon3 = new ImageIcon("src\\Images\\award1.png");
//        ImageIcon icon3 = new ImageIcon("resources\\Images\\award1.png");
        String s = "Results";
        JLabel label = new JLabel(s);
        label.setFont(new Font("serif", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(null, s
                + "\nIncorrect Answers: \t" + wrongs
                + "\nCorrect Answers  : \t" + (total - wrongs)
                + "\nScore            : \t\t" + (int) (((float) (total - wrongs) / total) * 100) + "%",
                "Results", JOptionPane.INFORMATION_MESSAGE, icon3
        );
        System.exit(0);
    }
}
