/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Pramuditha Buddhini
 */
public class Question {
    
      private int id;
    private String description;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private byte[] pic;
    private int answer;
    
    public Question(int id,String des,String op1,String op2,String op3,String op4,byte[] pic,int ans){
        this.id= id;
        this.description=des;
        this.option1=op1;
        this.option2=op2;
        this.option3=op3;
        this.option4=op4;
        this.pic = pic;
        this.answer=ans;
        
    
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
    
  
    
    
}
