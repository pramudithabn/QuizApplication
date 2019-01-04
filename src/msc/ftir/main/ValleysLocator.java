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

/**
 *
 * @author Pramuditha Buddhini
 */
public class ValleysLocator {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private static ArrayList<BigDecimal> order1_derivatives = new ArrayList<BigDecimal>();
    private ArrayList<BigDecimal> order2_derivatives = new ArrayList<BigDecimal>();
    private SortedMap<BigDecimal, BigDecimal> valleyCandidates = new TreeMap<BigDecimal, BigDecimal>();
    private SortedMap<BigDecimal, BigDecimal> firstOrderDerivatives = new TreeMap<BigDecimal, BigDecimal>();
    private ArrayList<BigDecimal> hList = new ArrayList<BigDecimal>();
    private BigDecimal minH;
    private BigDecimal maxH;
    private double hScale;
    private double c;

    public SortedMap<BigDecimal, BigDecimal> getValleyCandidates() {
        return valleyCandidates;
    }

    public void setValleyCandidates(SortedMap<BigDecimal, BigDecimal> valleyCandidates) {
        this.valleyCandidates = valleyCandidates;
    }
    private static ArrayList<InputData> originalList = new ArrayList<InputData>();
    private int listSize;

    public ValleysLocator() {
        conn = Javaconnect.ConnecrDb();

        qdata();

    }

    public static void main(String[] args) {
        ValleysLocator v = new ValleysLocator();
//        v.cal_1storder_derivative();
        System.out.println("Size origi=" + originalList.size());
//        System.out.println("Size deri=" + v.firstOrderDerivatives.size());

//        v.find_valley_candidates();
        v.addCandidates();
    }

    public ArrayList<InputData> qdata() {

        String sql = "select WAVENUMBER, TRANSMITTANCE from avg_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
                originalList.add(d);

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

        listSize = originalList.size();

        return originalList;

    }

    private void cal_1storder_derivative() {

        double x1 = 0, x2 = 0, x3 = 0;
        double y1 = 0, y2 = 0, y3 = 0;
        BigDecimal result = null;

        x1 = originalList.get(0).getWavenumber().doubleValue();
        x2 = originalList.get(1).getWavenumber().doubleValue();

        y1 = originalList.get(0).getTransmittance().doubleValue();
        y2 = originalList.get(1).getTransmittance().doubleValue();

        result = BigDecimal.valueOf((y2 - y1) / (x2 - x1));

        order1_derivatives.add(result);
        firstOrderDerivatives.put(originalList.get(0).getWavenumber(), result);
        System.out.println(result);

        for (int i = 0; i < listSize - 2; i++) {

            x1 = originalList.get(i).getWavenumber().doubleValue();
            x3 = originalList.get(i + 2).getWavenumber().doubleValue();

            y1 = originalList.get(i).getTransmittance().doubleValue();
            y3 = originalList.get(i + 2).getTransmittance().doubleValue();

            double d1 = (y3 - y1) / (x3 - x1);

            result = BigDecimal.valueOf(d1);
            firstOrderDerivatives.put(originalList.get(i + 1).getWavenumber(), result);
            order1_derivatives.add(result);
            System.out.println(result);
        }

        x1 = originalList.get(listSize - 2).getWavenumber().doubleValue();
        x2 = originalList.get(listSize - 1).getWavenumber().doubleValue();
        y1 = originalList.get(listSize - 2).getTransmittance().doubleValue();
        y2 = originalList.get(listSize - 1).getTransmittance().doubleValue();

        result = BigDecimal.valueOf((y2 - y1) / (x2 - x1));

        firstOrderDerivatives.put(originalList.get(listSize - 1).getWavenumber(), result);
        order1_derivatives.add(result);
        System.out.println(result);

    }

    public void find_valley_candidates() {

        double y1 = 0, y3 = 0;
        BigDecimal x2 = null, y2 = null;

        for (int i = 1; i < listSize - 1; i++) {
            y1 = order1_derivatives.get(i - 1).doubleValue();
            x2 = originalList.get(i).getWavenumber();
            y2 = originalList.get(i).getTransmittance();
            y3 = order1_derivatives.get(i + 1).doubleValue();

            if (y1 < 0 && y3 > 0) {

                valleyCandidates.put(x2, y2);
            }

        }

        for (BigDecimal name : valleyCandidates.keySet()) {

            String key = name.toString();
            String value = valleyCandidates.get(name).toString();
            System.out.println(key + " " + value);

        }

    }

    public void addCandidates() {

        double x1 = 0, x2 = 0, x3 = 0;
        double y1 = 0, y2 = 0, y3 = 0;
        BigDecimal x_val = null, y_val = null;
        double d1 = 0, d2 = 0;

        for (int i = 0; i < listSize - 2; i++) {

            x1 = originalList.get(i).getWavenumber().doubleValue();
            x2 = originalList.get(i + 1).getWavenumber().doubleValue();
            x3 = originalList.get(i + 2).getWavenumber().doubleValue();
            x_val = originalList.get(i + 1).getWavenumber();

            y1 = originalList.get(i).getTransmittance().doubleValue();
            y2 = originalList.get(i + 1).getTransmittance().doubleValue();
            y3 = originalList.get(i + 2).getTransmittance().doubleValue();
            y_val = originalList.get(i + 1).getTransmittance();

            d1 = (y2 - y1) / (x2 - x1);
            d2 = (y3 - y2) / (x3 - x2);

//            double h1, h2, hconst;
//            h1 = Math.abs(y2 - y1);
//            h2 = Math.abs(y3 - y2);
//
//            hconst = det_threshold(10);
//            System.out.println(hconst);
            
            if (d1 > 0 && d2 < 0) {
//                if ((h1 >= hconst) && (h2 >= hconst)) {
                    valleyCandidates.put(x_val, y_val);
//                }
            }
           

        }
//        for (BigDecimal wvl : valleyCandidates.keySet()) {
//
//            String key = wvl.toString();
//            String value = valleyCandidates.get(wvl).toString();
//            System.out.println(key + " " + value);
//
//        }
         System.out.println(valleyCandidates.size()); 

    }
    
    public void addCandidates4neighbors() {

        double x1 = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0;
        double y1 = 0, y2 = 0, y3 = 0,y4 = 0, y5 = 0;
        BigDecimal x_val = null, y_val = null;
        double d1 = 0, d2 = 0;//neighbours 1
        double n1 = 0, n2 = 0;//neighbours 2

        for (int i = 0; i < listSize - 4; i++) {

            x1 = originalList.get(i).getWavenumber().doubleValue();
            x2 = originalList.get(i + 1).getWavenumber().doubleValue();
            x3 = originalList.get(i + 2).getWavenumber().doubleValue();
            x4 = originalList.get(i + 3).getWavenumber().doubleValue();
            x5 = originalList.get(i + 4).getWavenumber().doubleValue();
//            x_val = originalList.get(i + 1).getWavenumber();
            x_val = originalList.get(i + 2).getWavenumber();

            y1 = originalList.get(i).getTransmittance().doubleValue();
            y2 = originalList.get(i + 1).getTransmittance().doubleValue();
            y3 = originalList.get(i + 2).getTransmittance().doubleValue();
            y4 = originalList.get(i + 3).getTransmittance().doubleValue();
            y5 = originalList.get(i + 4).getTransmittance().doubleValue();
//            y_val = originalList.get(i + 1).getTransmittance();
             y_val = originalList.get(i + 2).getTransmittance();

//            d1 = (y2 - y1) / (x2 - x1);
//            d2 = (y3 - y2) / (x3 - x2);
            
            d1 = (y3 - y2) / (x3 - x2);
            d2 = (y4 - y3) / (x4 - x3);
            
            n1 = (y2 - y1) / (x2 - x1);
            n2 = (y5 - y4) / (x5 - x4);
            

//            double h1, h2, hconst;
//            h1 = Math.abs(y2 - y1);
//            h2 = Math.abs(y3 - y2);
//
//            hconst = det_threshold(10);
//            System.out.println(hconst);
            if (d1 > 0 && d2 < 0 && n1>0 && n2<0) {
//                if ((h1 >= hconst) && (h2 >= hconst)) {
                valleyCandidates.put(x_val, y_val);
//                }
            }

        }
        for (BigDecimal wvl : valleyCandidates.keySet()) {

            String key = wvl.toString();
            String value = valleyCandidates.get(wvl).toString();
            System.out.println(key + " " + value);

        }

    }


    public double det_threshold(int c) {

        BigDecimal diff = null;

        for (int i = 0; i < listSize - 2; i++) {

            BigDecimal n1 = originalList.get(i).getTransmittance();
            BigDecimal n2 = originalList.get(i + 1).getTransmittance();

            diff = n1.subtract(n2);

            BigDecimal d = diff.abs();

            hList.add(d);

        }

        int minIndex = hList.indexOf(Collections.min(hList));
        int maxIndex = hList.indexOf(Collections.max(hList));

        minH = hList.get(minIndex);
        maxH = hList.get(maxIndex);

//        hScale = (maxH.doubleValue() - minH.doubleValue()) * 0.01 * c;
        hScale = (maxH.doubleValue() + minH.doubleValue()) / 2;
        return hScale;
    }
}
