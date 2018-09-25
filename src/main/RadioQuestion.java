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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import sun.net.www.content.image.gif;

public class RadioQuestion extends JPanel implements ActionListener {

    int correctAns;
    Quiz quiz;
    int selected;
    boolean used;
    //questions
    JPanel qPanel = new JPanel();
    
    //picture
    JPanel picPanel =  new JPanel();

    JPanel header = new JPanel();
    int qnumber = 1;

    //answers
    JPanel aPanel = new JPanel();
    JRadioButton[] responses;
    ButtonGroup group = new ButtonGroup();

    JPanel msgPanel = new JPanel();
    ImageIcon gifImage = new ImageIcon("src\\Images\\c.gif");
    JLabel gif = new JLabel(gifImage);
    

    //bottom
    JPanel botPanel = new JPanel();
    JButton next = new JButton("Next");
    JButton finish = new JButton("Finish");

    public RadioQuestion(String q, String[] options, int ans, byte[] p, Quiz quiz) {
        
       System.out.println("Question = "+q);
       System.out.println("answer = "+ans);
       System.out.println("SIZE  = "+options[0].length());
       
       
       System.out.println("-------");
       if(options!=null){
       
       for(int i=0;i<options.length;i++){
           System.out.println("Options");
       System.out.println(options[i] +",");
       }
       }
       System.out.println("-------");
     
       if(p!=null){
       System.out.println("pic not null");
       }
       else{System.out.print("Null");}
        
        this.quiz = quiz;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

//                setLayout(new BorderLayout(this,BorderLayout.);
//        System.out.print(ans + " ");
        correctAns = ans;
//        System.out.print(correctAns + " ");

        JLabel title = new JLabel("Questions");

        title.setFont(new Font("Tahoma", 1, 20));
        header.add(title);
//                header.setAlignmentX(RIGHT_ALIGNMENT);

        add(gif,BorderLayout.LINE_END);
        add(header, BorderLayout.LINE_START);

        //question
        ImageIcon icon3 = new ImageIcon("src\\Images\\q.png");
        JLabel qstn = new JLabel(q);
        qstn.setIcon(icon3);
        qPanel.add(qstn);

        add(qPanel);
        qPanel.setSize(500, 200);
        qPanel.setAlignmentX(RIGHT_ALIGNMENT);
        qPanel.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        //pic
        add(picPanel);
               
        ByteArrayInputStream pp = new ByteArrayInputStream(p);
        BufferedImage image;
        try {
            image = ImageIO.read(pp);
            JLabel piclabel = new JLabel(new ImageIcon(image));
            picPanel.add(piclabel);
        } catch (IOException ex) {
            Logger.getLogger(RadioQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
  
        
        
        //answer
        responses = new JRadioButton[options.length];
        for (int i = 0; i < options.length; i++) {
            responses[i] = new JRadioButton(options[i]);
            responses[i].addActionListener(this);
            group.add(responses[i]);
            Box box1 = Box.createVerticalBox();
            box1.add(responses[i]);
            aPanel.add(box1, BorderLayout.CENTER);

//			aPanel.add(responses[i], BorderLayout.CENTER);
        }
        add(aPanel);
        //bottom
        next.addActionListener(this);
        next.setPreferredSize(new Dimension(100, 40));
        finish.addActionListener(this);
        finish.setPreferredSize(new Dimension(100, 40));
        botPanel.add(next);
        botPanel.add(finish);
        add(botPanel);

        add(msgPanel);
        

    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        

//        System.out.println(selected + "=selected");
        //next button
//        if(src.equals(next)){
//			showResult();
//			if(selected==correctAns){
//				used=true;
//				quiz.next();
//			}
//		}
        if (src.equals(next)) {
            showResult();
            
                used = true;
                quiz.next();
            
        }
        
        
        
//        quiz.showSummary();

        //finish button
        if (src.equals(finish)) {
            quiz.showSummary();
        }
        //radio buttons
        for (int i = 0; i < responses.length; i++) {
            if (src == responses[i]) {
                selected = i;
            }
        }
    }

    public void showResult() {

        quiz.total++;

        if (selected == correctAns) {
            quiz.corrects++;
            ImageIcon icon1 = new ImageIcon("src\\Images\\right.png");

            
            JOptionPane.showMessageDialog(null, " Correct!", "Result", JOptionPane.INFORMATION_MESSAGE, icon1);
        } else {
            quiz.wrongs++;
            ImageIcon icon2 = new ImageIcon("src\\Images\\wrong.png");
            JOptionPane.showMessageDialog(null, " Wrong", "Result", JOptionPane.INFORMATION_MESSAGE, icon2);
        }
        
        System.out.println("qtotal " + quiz.total);
        System.out.println("qnum " + quiz.numQs);
        System.out.println("wrong " + quiz.wrongs);
        System.out.println("qcorrect " + quiz.corrects + "\n\n");
        
        System.out.println(((quiz.numQs - quiz.corrects - quiz.wrongs) == 0));
        if ((quiz.numQs - quiz.corrects - quiz.wrongs) == 0) {
            quiz.showSummary();
        }
    }
}
