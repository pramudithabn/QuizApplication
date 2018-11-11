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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;

/**
 * A method to smooth a line based on the McMaster line smoothing algorithm
 *
 * @author Pramuditha Buddhini
 */
public class LineSmoother {

    static ArrayList<InputData> rowDataList = new ArrayList<InputData>();
    static ArrayList<BigDecimal> avgPointList = new ArrayList<BigDecimal>();
    ArrayList<BigDecimal> gapDifferenceList = new ArrayList<BigDecimal>();
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int rindex = 0;
    int listSize = 0;
    ChartPanel chartPanel;
    BigDecimal minScale;
    BigDecimal maxScale;
    BigDecimal smoothingFactor;
    private static volatile LineSmoother instance;

    public LineSmoother() {
        conn = Javaconnect.ConnecrDb();
      

        qdata();
        

    }
    public static LineSmoother getInstance() {
   
       instance = new LineSmoother();
    
    return instance;
}

    public ArrayList<InputData> qdata() {

        String sql = "select * from input_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getInt("ID"), rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
                rowDataList.add(d);

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

        listSize = rowDataList.size();

        return rowDataList;

    }

    public void calPointAverage() {

        BigDecimal sum = null;
        BigDecimal avg = null;

        BigDecimal first = rowDataList.get(0).getTransmittance();
        BigDecimal last = rowDataList.get(listSize - 1).getTransmittance();
        avgPointList.add(first);

        for (rindex = 1; rindex < listSize - 1; rindex++) {

            BigDecimal n1 = rowDataList.get(rindex - 1).getTransmittance();
            BigDecimal n2 = rowDataList.get(rindex).getTransmittance();
            BigDecimal n3 = rowDataList.get(rindex + 1).getTransmittance();

            sum = n1.add(n2);
            sum = sum.add(n3);
            avg = sum.divide(BigDecimal.valueOf(3), 8, RoundingMode.HALF_UP);

            avgPointList.add(avg);

        }
        avgPointList.add(last);
        System.out.println("Point avg calculated ");

    }

    public void loadAvgDataTable() throws SQLException {

        for (int i = 0; i < avgPointList.size(); i++) {

            Statement statement = conn.createStatement();
            String qry = "insert into avg_data (WAVENUMBER , TRANSMITTANCE) values (?,?)";

            try {
                pst = conn.prepareStatement(qry);

                BigDecimal wavenum = rowDataList.get(i).getWavenumber();
                BigDecimal transmittance = avgPointList.get(i);
                rowDataList.get(i).setTransmittance(transmittance);

                pst.setBigDecimal(1, wavenum);
                pst.setBigDecimal(2, transmittance);

                pst.execute();

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    pst.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }

        }
        System.out.println("DB avg table loaded");

    }

    public void avgAlgorithm(int scale) {

        BigDecimal diff = null;

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

        smoothingFactor = ((maxScale.subtract(minScale)).divide(BigDecimal.valueOf(100))).multiply(BigDecimal.valueOf(scale));

        System.out.println("\n Smoothing Factor " + smoothingFactor);

        BigDecimal sum = null;
        BigDecimal avg = null;

        BigDecimal first = rowDataList.get(0).getTransmittance();
        BigDecimal last = rowDataList.get(listSize - 1).getTransmittance();
        avgPointList.add(first);

        for (rindex = 1; rindex < listSize - 1; rindex++) {

            int res;

            res = (gapDifferenceList.get(rindex - 1)).compareTo(smoothingFactor);

            if (res == 1 | res == 0) {
                avgPointList.add(rowDataList.get(rindex - 1).getTransmittance());

            } else if (res == -1) {

                BigDecimal n1 = rowDataList.get(rindex - 1).getTransmittance();
                BigDecimal n2 = rowDataList.get(rindex).getTransmittance();
                BigDecimal n3 = rowDataList.get(rindex + 1).getTransmittance();

                sum = n1.add(n2);
                sum = sum.add(n3);
                avg = sum.divide(BigDecimal.valueOf(3), 8, RoundingMode.HALF_UP);

                avgPointList.add(avg);
//                System.out.println("added");

            }

        }
        avgPointList.add(last);
        System.out.println("Point avg calculated ");

//        for (int i = 0; i < listSize - 1; i++) {
//            System.out.print(rowDataList.get(i).getTransmittance() + ",");
//
//        }
//        System.out.print("\n");
//        for (int i = 0; i < avgPointList.size() - 1; i++) {
//
//            System.out.print(avgPointList.get(i) + ",");
//        }
    }

    public void updateSmoothedValue() {

        String fullarrays = "";
        for (int i = 0; i < avgPointList.size(); i++) {
            String twoarrays = "(" + rowDataList.get(i).getWavenumber() + " , " + avgPointList.get(i) + ")";
            fullarrays = fullarrays + twoarrays + ",";
        }
        fullarrays = fullarrays.substring(0, fullarrays.length() - 1);

//        System.out.println(fullarrays);
        String sql = "INSERT INTO avg_data (wavenumber,transmittance)  VALUES " + fullarrays;

        System.out.println(sql);

        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }

    }

}
