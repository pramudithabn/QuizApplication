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
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Quiz extends JFrame {

    JPanel p = new JPanel();
    GroupLayout layout = new GroupLayout(p);

    int numQs;
    int wrongs = 0;
    int corrects = 0;
    int total = 0;
    int qnumber = 0;

    int j = 0;

    private Connection conn = null;

    private RadioQuestion questions[][];
    
    ArrayList<Question> list = new ArrayList<Question>(); 

    public void selectAnswers() {

        String sql = "select * from question";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
//             Question q = new Question(rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4"));
//            System.out.println("resultset = " + rs.next());

            rs.first();
            do {
                String[][] answers = {{rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4")}};

                Blob pic = rs.getBlob("PIC");
                int blobLength = (int) pic.length();
                byte[] picAsBytes = pic.getBytes(1, blobLength);

                int i = 0;
                RadioQuestion questions[][] = {{new RadioQuestion(rs.getString("DESCRIPTION"), answers[i], rs.getInt("ANSWER"), picAsBytes, this)}};
                i++;

                int numCol = rs.getMetaData().getColumnCount();
                int numRows = rs.getRow();

                j = numRows;

                System.out.println("Rows # =" + numRows);

            } while (rs.next());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

//    String[][] answers = {
//        {"RAM", "CPU", "Keyboard", "Mouse"},
//        {"1TB", "1MB", "1000KB", "1GB"},
//        {"Booktop", "Laptop", "Super computer", "CPU"},
//        {"Yes", "No"},
//        {"Yahoo", "Bing", "Google", "SOSO"}
//    };
//    RadioQuestion questions[] = {
//        new RadioQuestion(
//        "What do you use to click stuff?",
//        answers[0],
//        3, this
//        ),
//        new RadioQuestion(
//        "What memory below is the largest?",
//        answers[1],
//        0, this
//        ),
//        new RadioQuestion(
//        "What do you call a portable computer?",
//        answers[2],
//        1, this
//        ),
//        new RadioQuestion(
//        "Does RAM stores information about your computer when it is closed?",
//        answers[3],
//        1, this
//        ),
//        new RadioQuestion(
//        "What is the most used search engine?",
//        answers[4],
//        2, this
//        )
//
//    };
    public static void main(String args[]) {

        new Quiz();

    }

    public Quiz() {
        super("Quiz");
        conn = DbConnector.ConnecrDb();

//        selectAnswers();
        String sql = "select * from question";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
//             Question q = new Question(rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4"));
//            System.out.println("resultset = " + rs.next());

            rs.first();
            do {
                String[][] answers = {{rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4")}};

                Blob pic = rs.getBlob("PIC");
                int blobLength = (int) pic.length();
                byte[] picAsBytes = pic.getBytes(1, blobLength);

                int i = 0;
                RadioQuestion questions[][] = {{new RadioQuestion(rs.getString("DESCRIPTION"), answers[i], rs.getInt("ANSWER"), picAsBytes, this)}};

                int numCol = rs.getMetaData().getColumnCount();
                int numRows = rs.getRow();
                

                j = numRows;

                System.out.println("Rows # =" + numRows);
                
//                JLabel desl = new JLabel(questions[0].toString());
//                desl.setHorizontalTextPosition(JLabel.LEFT);
//                desl.setVerticalTextPosition(JLabel.TOP);
////                desl.setBorder(border);
//                p.add(desl);
//
//                layout.setAutoCreateGaps(true);
//                layout.setAutoCreateContainerGaps(true);
//
//                layout.setHorizontalGroup(
//                        layout.createSequentialGroup()
//                                .addComponent(desl)
////                                .addComponent(c2)
////                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
////                                        .addComponent(c3)
////                                        .addComponent(c4))
//                );

               

                i++;
            } while (rs.next());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        setResizable(false);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        numQs = questions.length;
        numQs = 1;
//        System.out.print("q numbers ="+numQs);

//            p.add(rs.getString("DESCRIPTION"));
//            p.add(questions[][1]);
//            p.add(questions[][3]);
//        
        Random r = new Random();
        int i = r.nextInt(numQs);
//        cards.show(p, "q" + i);
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

                if (!questions[0][i].used) {
                    found = true;
                }
            }
//            layout.show(p, "q" + i);
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
