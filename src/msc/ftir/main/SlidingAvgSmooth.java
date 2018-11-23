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
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pramuditha Buddhini
 */
public class SlidingAvgSmooth {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public ArrayList<InputData> originalPoints = new ArrayList<InputData>();
    public ArrayList<BigDecimal> smoothedPoints = new ArrayList<BigDecimal>();
    NavigableMap<BigDecimal, BigDecimal> originalPointList = new TreeMap<BigDecimal, BigDecimal>();
    SortedMap<BigDecimal, BigDecimal> smoothedPointList = new TreeMap<BigDecimal, BigDecimal>();
    public static int count = 0;

    public SlidingAvgSmooth() {
        conn = Javaconnect.ConnecrDb();

        qdata();
        

        

    }

    public static void main(String[] args) {
        SlidingAvgSmooth nw = new SlidingAvgSmooth();

//        for (BigDecimal wn : nw.originalPointList.keySet()) {
//            BigDecimal key = wn;
//            BigDecimal value = nw.originalPointList.get(wn);
//            System.out.println(key + "," + value);
//        }
        nw.cal_3point_avg();
        for (int i = 0; i < nw.smoothedPoints.size(); i++) {
            System.out.println(nw.smoothedPoints.get(i));
        }
        System.out.println("Size2 = " + nw.smoothedPoints.size());
        System.out.println("Size1 = " + nw.originalPoints.size());
    }

    public ArrayList<InputData> qdata() {

        String sql = "select WAVENUMBER, TRANSMITTANCE from input_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
                originalPoints.add(d);

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

        return originalPoints;

    }

    public void fillMap() {
        for (int i = 0; i < this.originalPoints.size(); i++) {

            originalPointList.put(this.originalPoints.get(i).getWavenumber(), this.originalPoints.get(i).getTransmittance());
        }
    }

    public void cal_3point_tri() {

        for (Map.Entry<BigDecimal, BigDecimal> entry : originalPointList.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

        }

    }

    public void cal_3point_avg() {
        
        if (!smoothedPoints.isEmpty()) {
            for (int i = 0; i < smoothedPoints.size(); i++) {
                originalPoints.get(i).setTransmittance(smoothedPoints.get(i));
            }
        }
        smoothedPoints.clear();

        smoothedPoints.clear();
        BigDecimal sum = null;
        BigDecimal avg = null;
        int rindex = 0;
        int listSize = originalPoints.size();

        BigDecimal first = originalPoints.get(0).getTransmittance();
        BigDecimal last = originalPoints.get(listSize - 1).getTransmittance();
        smoothedPoints.add(first);

        for (rindex = 1; rindex < listSize - 1; rindex++) {

            BigDecimal n1 = originalPoints.get(rindex - 1).getTransmittance();
            BigDecimal n2 = originalPoints.get(rindex).getTransmittance();
            BigDecimal n3 = originalPoints.get(rindex + 1).getTransmittance();

            sum = n1.add(n2);
            sum = sum.add(n3);
            avg = sum.divide(BigDecimal.valueOf(3), 8, RoundingMode.HALF_UP);

            smoothedPoints.add(avg);

        }
        smoothedPoints.add(last);
        count++;

    }

    public void cal_5point_avg() {
        
        if (!smoothedPoints.isEmpty()) {
            for (int i = 0; i < smoothedPoints.size(); i++) {
                originalPoints.get(i).setTransmittance(smoothedPoints.get(i));
            }
        }
        smoothedPoints.clear();

        smoothedPoints.clear();
        double sum = 0;
        double avg = 0;
        BigDecimal entry = null;
        int rindex = 0;
        int listSize = originalPoints.size();

        BigDecimal start0 = originalPoints.get(0).getTransmittance();//0 1st element
        BigDecimal start1 = originalPoints.get(1).getTransmittance();//1 2nd element
        BigDecimal end1 = originalPoints.get(listSize - 1).getTransmittance();//n-2 (n-1)th element
        BigDecimal end2 = originalPoints.get(listSize - 2).getTransmittance();//n-1 (n)th element

        smoothedPoints.add(start0);
        smoothedPoints.add(start1);

        for (rindex = 2; rindex < listSize - 2; rindex++) {

            double n1 = (originalPoints.get(rindex - 2).getTransmittance()).doubleValue();
            double n2 = (originalPoints.get(rindex - 1).getTransmittance()).doubleValue();
            double n3 = (originalPoints.get(rindex).getTransmittance()).doubleValue();
            double n4 = (originalPoints.get(rindex + 1).getTransmittance()).doubleValue();
            double n5 = (originalPoints.get(rindex + 2).getTransmittance()).doubleValue();

            sum = n1 + n2 + n3 + n4 + n5;

//            avg = sum/5;
            entry = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(5), 8, RoundingMode.HALF_UP);

            smoothedPoints.add(entry);

        }
        smoothedPoints.add(end1);
        smoothedPoints.add(end2);
        count++;
    }

    public void cal_9point_avg() {
        
        if (!smoothedPoints.isEmpty()) {
            for (int i = 0; i < smoothedPoints.size(); i++) {
                originalPoints.get(i).setTransmittance(smoothedPoints.get(i));
            }
        }
        smoothedPoints.clear();
        
        double sum = 0;
        double avg = 0;
        BigDecimal entry = null;
        int rindex = 0;
        int listSize = originalPoints.size();

        BigDecimal start0 = originalPoints.get(0).getTransmittance();//0 1st element
        BigDecimal start1 = originalPoints.get(1).getTransmittance();//1 2nd element
        BigDecimal start2 = originalPoints.get(2).getTransmittance();//2 3rd element
        BigDecimal start3 = originalPoints.get(3).getTransmittance();//3 4th element
        BigDecimal end4 = originalPoints.get(listSize - 4).getTransmittance();//n-4 (n-3)th element
        BigDecimal end3 = originalPoints.get(listSize - 3).getTransmittance();//n-3 (n-2)th element
        BigDecimal end2 = originalPoints.get(listSize - 2).getTransmittance();//n-2 (n-1)th element
        BigDecimal end1 = originalPoints.get(listSize - 1).getTransmittance();//n-1 (n)th element or the last

        smoothedPoints.add(start0);
        smoothedPoints.add(start1);
        smoothedPoints.add(start2);
        smoothedPoints.add(start3);

        for (rindex = 4; rindex < listSize - 4; rindex++) {

            double n1 = (originalPoints.get(rindex - 4).getTransmittance()).doubleValue();
            double n2 = (originalPoints.get(rindex - 3).getTransmittance()).doubleValue();
            double n3 = (originalPoints.get(rindex - 2).getTransmittance()).doubleValue();
            double n4 = (originalPoints.get(rindex - 1).getTransmittance()).doubleValue();
            double n5 = (originalPoints.get(rindex).getTransmittance()).doubleValue();
            double n6 = (originalPoints.get(rindex + 1).getTransmittance()).doubleValue();
            double n7 = (originalPoints.get(rindex + 2).getTransmittance()).doubleValue();
            double n8 = (originalPoints.get(rindex + 3).getTransmittance()).doubleValue();
            double n9 = (originalPoints.get(rindex + 4).getTransmittance()).doubleValue();

            sum = n1 + n2 + n3 + n4 + n5 + n6 + n6 + n7 + n8 + n9;

//            avg = sum/5;
            entry = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(9), 8, RoundingMode.HALF_UP);

            smoothedPoints.add(entry);

        }
        smoothedPoints.add(end4);
        smoothedPoints.add(end3);
        smoothedPoints.add(end2);
        smoothedPoints.add(end1);
        count++;
    }

}
