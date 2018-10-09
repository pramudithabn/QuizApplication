package msc.ftir.main;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pramuditha Buddhini
 */
public class Interpreter extends FTIRDescktopApp {

    private ArrayList dataList = new ArrayList();
    
    int row = dataTable.getRowCount();
    int col = dataTable.getColumnCount();
    
 
    
   
    public void arrayFill(){
    
    for(int i = 0 ; i<dataTable.getRowCount(); i++){
        
        for(int j=0; j<dataTable.getColumnCount(); j++){
            
            dataList.add(dataTable.getValueAt(row, col));
            System.out.println(dataTable.getValueAt(row, col));
        }
    
    }
    

}
    
    
//      dataList.add(dataTable.getValueAt(r, c));  
 

}
