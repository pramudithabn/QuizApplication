/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Pramuditha Buddhini
 */
public class MinimaLocator {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    ArrayList<InputData> avgList = new ArrayList<InputData>();
//    ArrayList<BigDecimal,BigDecimal> minimaList = new ArrayList<BigDecimal,BigDecimal>();
    SortedMap<BigDecimal,BigDecimal> minima = new TreeMap<BigDecimal,BigDecimal>();
    
    
   
    
    public MinimaLocator(){
    conn = Javaconnect.ConnecrDb();
    
    
    qdata();
   
    
    }
    
//    public static void main(String args[]){
//        MinimaLocator ml = new MinimaLocator();
//    ml.qdata();
//    ml.findMinima();
//    }
    
    
    public ArrayList<InputData> qdata() {

        String sql = "select * from avg_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getInt("ID"), rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
                avgList.add(d);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Javaconnect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        int listSize = avgList.size();


        return avgList;

    }
   
    
     public void findMinima() {
         
         
         BigDecimal left = null ;
         BigDecimal right= null;
         
         
        
         for(int index=1; index<this.avgList.size()-1;index++){
         
          
         BigDecimal x1 = this.avgList.get(index-1).getWavenumber();
         BigDecimal x2 = this.avgList.get(index).getWavenumber();
         BigDecimal x3 = this.avgList.get(index+1).getWavenumber();
         
         BigDecimal y1 = avgList.get(index-1).getTransmittance();
         BigDecimal y2 = avgList.get(index).getTransmittance();
         BigDecimal y3 = avgList.get(index+1).getTransmittance();
         
             System.out.println(x2 +"-"+ x1+") /("+y2 +"- "+y1+")");
         
//         BigDecimal y1 = avgList.get(index-1).getTransmittance();
//         BigDecimal y2 = avgList.get(index).getTransmittance();
//         BigDecimal y3 = avgList.get(index+1).getTransmittance();
         
         
         //a.divide(b, 2, RoundingMode.HALF_UP)
//         BigDecimal left =  x2.subtract(x1).divide(y2.subtract(y1));    // (x2 - x1) /(y2 - y1);
//         BigDecimal right =  x3.subtract(x2).divide(y3.subtract(y2));   // (x3 - x2) /(y3 - y2); 
         if((y2.compareTo(y1))!=0 && (y3.compareTo(y2))!=0){
         left =  x2.subtract(x1).divide(y2.subtract(y1), 6, RoundingMode.HALF_UP);    // (x2 - x1) /(y2 - y1);
         right =  x3.subtract(x2).divide(y3.subtract(y2),6, RoundingMode.HALF_UP); 
         }
         
         int comL = left.compareTo(BigDecimal.ZERO);
         int comR = right.compareTo(BigDecimal.ZERO);
         
         if(comL==-1 && comR>=0){ //equal zero or grt than one
             minima.put(this.avgList.get(index).getWavenumber(), this.avgList.get(index).getTransmittance());
             
         }
         
         }
         
         /*for (BigDecimal name : minima.keySet()) {

             String key = name.toString();
             String value = minima.get(name).toString();
             System.out.println(key + " " + value);

         }
         System.out.println("Size " + minima.size());*/
         

    }

    public ArrayList locateDownwardSpickes(long arr[]) {

        ArrayList spikeindex = new ArrayList();
        for (int i = 0; i < arr.length; i++) {

            if (arr[i] == 0 && arr[i - 1] < 0 && arr[i + 1] > 0) {
                spikeindex.add(i);
            }

        }
        return spikeindex;
    }

    XYDataset createDataset() {
        final XYSeries minimaPoints = new XYSeries("Downward spickes");
        for (BigDecimal name : minima.keySet()) {
            BigDecimal key = name;
            BigDecimal value = minima.get(name);
            minimaPoints.add(key,value);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(minimaPoints);
        return dataset;
    }

}
