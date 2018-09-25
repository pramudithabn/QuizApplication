package main;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pramuditha Buddhini
 */
public class QuizApplication extends javax.swing.JFrame {

    Connection conn = null;
    ArrayList<Question> list = new ArrayList<Question>();
    int i = 0;
    int qnum = 0;
    int[] used = new int[10];
    int j = 0;
    int listSize = 0;

    int numQs;
    int wrongs = 0;
    int corrects = 0;
    int total = 0;

    int correctAns = 0;
    int selected = 0;

    public static final Random gen = new Random();

    public QuizApplication() {
        initComponents();
        conn = DbConnector.ConnecrDb();
        pack();
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        qdata();
        setVisibleContent();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        qnumber = new javax.swing.JLabel();
        ansPanel = new javax.swing.JPanel();
        option1 = new javax.swing.JRadioButton();
        option2 = new javax.swing.JRadioButton();
        option3 = new javax.swing.JRadioButton();
        option4 = new javax.swing.JRadioButton();
        qPanel = new javax.swing.JPanel();
        question = new javax.swing.JLabel();
        picLabel = new javax.swing.JLabel();
        submitButton = new javax.swing.JButton();
        rslabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel1.setText("Question: ");

        nextButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        qnumber.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        qnumber.setText("qnumber");

        ansPanel.setLayout(new java.awt.GridBagLayout());

        buttonGroup.add(option1);
        option1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        option1.setText("jRadioButton1");
        option1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 35, 0, 31);
        ansPanel.add(option1, gridBagConstraints);

        buttonGroup.add(option2);
        option2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        option2.setText("jRadioButton2");
        option2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 35, 0, 31);
        ansPanel.add(option2, gridBagConstraints);

        buttonGroup.add(option3);
        option3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        option3.setText("jRadioButton3");
        option3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 35, 0, 31);
        ansPanel.add(option3, gridBagConstraints);

        buttonGroup.add(option4);
        option4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        option4.setText("jRadioButton4");
        option4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 35, 41, 31);
        ansPanel.add(option4, gridBagConstraints);

        question.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        question.setText("jLabel2");

        javax.swing.GroupLayout qPanelLayout = new javax.swing.GroupLayout(qPanel);
        qPanel.setLayout(qPanelLayout);
        qPanelLayout.setHorizontalGroup(
            qPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(question)
                .addContainerGap(319, Short.MAX_VALUE))
        );
        qPanelLayout.setVerticalGroup(
            qPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(question)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        submitButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        rslabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rslabel.setForeground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(rslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ansPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(qnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(qPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nextButton, submitButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(qnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(qPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ansPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {nextButton, submitButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        rslabel.setIcon(null);
        setVisibleContent();


    }//GEN-LAST:event_nextButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed

        if (option1.isSelected()) {
            selected = 1;
        } else if (option2.isSelected()) {
            selected = 2;
        } else if (option3.isSelected()) {
            selected = 3;
        } else if (option4.isSelected()) {
            selected = 4;
        } else {
            selected = 0;
        }

        showResult();


    }//GEN-LAST:event_submitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        JFrame frame = new JFrame("Quiz");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuizApplication().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ansPanel;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton nextButton;
    private javax.swing.JRadioButton option1;
    private javax.swing.JRadioButton option2;
    private javax.swing.JRadioButton option3;
    private javax.swing.JRadioButton option4;
    private javax.swing.JLabel picLabel;
    private javax.swing.JPanel qPanel;
    private javax.swing.JLabel qnumber;
    private javax.swing.JLabel question;
    private javax.swing.JLabel rslabel;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

    public void setVisibleContent() {

        buttonGroup.clearSelection();
//        int a = getRandomNumberInRange(1, 9);
        int a = genRandomInt(listSize);
        
        if(a==listSize){
            a--;
        }
        
        for(int b =0 ; b < used.length ; b++){
           if(used[b]==a){
               a++;
           }
            
        }
        System.out.println(a);
        i=a;
        qnum++;

        numQs = 5;
        qnumber.setText(String.valueOf(qnum));

        question.setText(list.get(i).getDescription());

        option1.setText(list.get(i).getOption1());
        option2.setText(list.get(i).getOption2());
        option3.setText(list.get(i).getOption3());
        option4.setText(list.get(i).getOption4());

        option1.setHorizontalTextPosition(SwingConstants.RIGHT);
        option2.setHorizontalTextPosition(SwingConstants.RIGHT);
        option3.setHorizontalTextPosition(SwingConstants.RIGHT);
        option4.setHorizontalTextPosition(SwingConstants.RIGHT);

        correctAns = list.get(i).getAnswer();

        byte[] p = list.get(i).getPic();

        if (p != null) {
            ByteArrayInputStream pp = new ByteArrayInputStream(p);
            BufferedImage image;
            try {
                image = ImageIO.read(pp);
                Image dimg = image.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon ii = new ImageIcon(dimg);
                picLabel.setIcon(ii);

            } catch (IOException ex) {
                Logger.getLogger(QuizApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            picLabel.setIcon(null);
        }

        used[j] = i;
        j++;
        i++;

    }

    public ArrayList<Question> qdata() {

        String sql = "select * from question";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Question q;
            while (rs.next()) {
                q = new Question(rs.getInt("ID"), rs.getString("DESCRIPTION"), rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4"), rs.getBytes("PIC"), rs.getInt("ANSWER"));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

//        if (list != null) {
//            System.out.print("List is Not null");
//        }
        listSize = list.size();
        System.out.println("Size = " + list.size());
        return list;

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static int genRandomInt(int a) {

        int k = 0;
        Random r = new Random();
        HashSet<Integer> set = new HashSet<>();
        while (set.size() < 1) {
            int random = r.nextInt((a - 1) + 1) + 1;
            set.add(random);
        }

        for (int RandomNumbers : set) {
            k = RandomNumbers;
        }
        return k;
    }

    public void showResult() {

        total++;
//        if(selected ==0){
//            rslabel.setText("Please select an answer!");
//            
//       
//        }

        if (selected == correctAns) {

            corrects++;
            ImageIcon icon1 = new ImageIcon("src\\Images\\right.png");

            rslabel.setIcon(icon1);

//            JOptionPane.showMessageDialog(null, " Correct!", "Result", JOptionPane.INFORMATION_MESSAGE, icon1);
        } else {
            wrongs++;
            ImageIcon icon2 = new ImageIcon("src\\Images\\wrong.png");
            rslabel.setIcon(icon2);
//            JOptionPane.showMessageDialog(null, " Wrong", "Result", JOptionPane.INFORMATION_MESSAGE, icon2);
        }

        if ((numQs - corrects - wrongs) == 0) {
            showSummary();
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