/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.main;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static msc.ftir.main.DefaultSmooth.rowDataList;
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

    private ArrayList<InputData> avgList = new ArrayList<InputData>();
//    ArrayList<BigDecimal,BigDecimal> minimaList = new ArrayList<BigDecimal,BigDecimal>();
    private SortedMap<BigDecimal, BigDecimal> minima = new TreeMap<BigDecimal, BigDecimal>();
    private SortedMap<BigDecimal, BigDecimal> minimaList = new TreeMap<BigDecimal, BigDecimal>();
    private ArrayList<BigDecimal> gapDifferenceList = new ArrayList<BigDecimal>();
    private double mean;
    private double stdDeviation;
    private double hConst;
    private int maxIndex=0;
    private int minIndex=0;
    private BigDecimal minScale;
    private BigDecimal maxScale;
    private BigDecimal scaleFactor;

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public int getMinIndex() {
        return minIndex;
    }

    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex;
    }

    public MinimaLocator() {
        conn = Javaconnect.ConnecrDb();

        qdata();

    }

    public static void main(String args[]) {
        MinimaLocator ml = new MinimaLocator();
        ml.qdata();
        ml.findMinima();
        ml.cal_Minimas(1);
    }

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

        double left = 0;
        double right = 0;
        BigDecimal diff = null;
        int rindex=0;
        int listSize = rowDataList.size();
        int scale=10;

        for (rindex = 1; rindex < listSize - 1; rindex++) {

            BigDecimal n1 = rowDataList.get(rindex - 1).getTransmittance();
            BigDecimal n2 = rowDataList.get(rindex).getTransmittance();

            diff = n1.subtract(n2);

            BigDecimal d = diff.abs();

            gapDifferenceList.add(d);

        }

        int minIndex = gapDifferenceList.indexOf(Collections.min(gapDifferenceList));
        int maxIndex = gapDifferenceList.indexOf(Collections.max(gapDifferenceList));

        minScale = gapDifferenceList.get(minIndex);
        maxScale = gapDifferenceList.get(maxIndex);

        scaleFactor = ((maxScale.subtract(minScale)).divide(BigDecimal.valueOf(100))).multiply(BigDecimal.valueOf(scale));
        
        double scFac = (scaleFactor).doubleValue();
        
        for (int index = 1; index < this.avgList.size() - 2; index++) {

            double x1 = this.avgList.get(index - 1).getWavenumber().doubleValue();
            double x2 = this.avgList.get(index).getWavenumber().doubleValue();
            double x3 = this.avgList.get(index + 1).getWavenumber().doubleValue();

            double y1 = this.avgList.get(index - 1).getTransmittance().doubleValue();
            double y2 = this.avgList.get(index).getTransmittance().doubleValue();
            double y3 = this.avgList.get(index + 1).getTransmittance().doubleValue();

            if ((y2 - y1) != 0 && (y3 - y2) != 0) {

                left = (y2 - y1) / (x2 - x1);
                right = (y3 - y2) / (x3 - x2);
                
                double absL =Math.abs(left);
                double absR =Math.abs(right);
                
                

                if (left > 0 && right < 0 /*&& */) {
                    
                    if (absL>scFac && absR>scFac) {
                        continue;
                    }
                    
                    minima.put(this.avgList.get(index).getWavenumber(), this.avgList.get(index).getTransmittance());
                }

            }

//            BigDecimal x1 = this.avgList.get(index - 1).getWavenumber();
//            BigDecimal x2 = this.avgList.get(index).getWavenumber();
//            BigDecimal x3 = this.avgList.get(index + 1).getWavenumber();
//
//            BigDecimal y1 = this.avgList.get(index - 1).getTransmittance();
//            BigDecimal y2 = this.avgList.get(index).getTransmittance();
//            BigDecimal y3 = this.avgList.get(index + 1).getTransmittance();
//             System.out.println(x2 +"-"+ x1+") /("+y2 +"- "+y1+")");
//         BigDecimal y1 = avgList.get(index-1).getTransmittance();
//         BigDecimal y2 = avgList.get(index).getTransmittance();
//         BigDecimal y3 = avgList.get(index+1).getTransmittance();
            //a.divide(b, 2, RoundingMode.HALF_UP)
//         BigDecimal left =  x2.subtract(x1).divide(y2.subtract(y1));    // (x2 - x1) /(y2 - y1);
//         BigDecimal right =  x3.subtract(x2).divide(y3.subtract(y2));   // (x3 - x2) /(y3 - y2); 
            /*if ((y2.compareTo(y1)) != 0 && (y3.compareTo(y2)) != 0) {
                left = (x2.subtract(x1)).divide(y2.subtract(y1), 6, RoundingMode.HALF_UP);    // (x2 - x1) /(y2 - y1);
                right = (x3.subtract(x2)).divide(y3.subtract(y2), 6, RoundingMode.HALF_UP);

                int comL = left.compareTo(BigDecimal.ZERO); //-1
                int comR = right.compareTo(BigDecimal.ZERO);//1 or 0
            


                if (comL > 0 && comR <= 0) { //equal zero or grt than one
                    minima.put(this.avgList.get(index).getWavenumber(), this.avgList.get(index).getTransmittance());

                }
            }*/
        }
//        if (getValue(avgList.size() - 2) > getValue(avgList.size() - 1)) {
//
//            minima.put(this.avgList.get(avgList.size() - 1).getWavenumber(), this.avgList.get(avgList.size() - 1).getTransmittance());
//        }

//        for (BigDecimal name : minima.keySet()) {
//
//            String key = name.toString();
//            String value = minima.get(name).toString();
//            System.out.println(key + " " + value);
//
//        }
        System.out.println("Size " + minima.size());

    }

    public double getValue(int i) {

        double t = this.avgList.get(i).getTransmittance().doubleValue();
        return t;
    }

    public void cal_Minimas(double h) {
        //≤ (m + h · s)
        for (BigDecimal wavenum : minima.keySet()) {

            BigDecimal key = wavenum;
            double value = minima.get(wavenum).doubleValue();

            if ((mean + h * stdDeviation) <= value) {

                BigDecimal str = BigDecimal.valueOf(value);
                minimaList.put(key, str);

            }

        }

//        for (BigDecimal name : minimaList.keySet()) {
//
//            String key = name.toString();
//            String value = minima.get(name).toString();
//            System.out.println(key + " " + value);
//
//        }
        System.out.println("New Size " + minimaList.size());

    }

    public double cal_mean() {
        double t = 0;

        for (int index = 0; index < this.avgList.size(); index++) {
            t = t + avgList.get(index).getTransmittance().doubleValue();

        }

        mean = t / (this.avgList.size());
        return mean;

    }

    public double cal_variance() {
        double tsum = 0;

        for (int index = 0; index < this.avgList.size(); index++) {
            double diff = avgList.get(index).getTransmittance().doubleValue() - cal_mean();
            tsum = tsum + (diff * diff);
        }

        return tsum / (this.avgList.size() - 1);
    }

    public double cal_sd() {

        stdDeviation = Math.sqrt(cal_variance());
        return stdDeviation;

    }

    XYDataset createDataset() {
        final XYSeries minimaPoints = new XYSeries("Downward spickes");
        for (BigDecimal name : minima.keySet()) {
            BigDecimal key = name;
            BigDecimal value = minima.get(name);
            minimaPoints.add(key, value);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(minimaPoints);
        return dataset;
    }

    

}
