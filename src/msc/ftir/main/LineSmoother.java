/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Line;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCXYDataset;

/**
 * A method to smooth a line based on the McMaster line smoothing algorithm
 *
 * @author Pramuditha Buddhini
 */
public class LineSmoother {

    ArrayList<InputData> rowDataList = new ArrayList<InputData>();
    ArrayList<BigDecimal> avgPointList = new ArrayList<BigDecimal>();
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int rindex = 0;
    int listSize = 0;
    ChartPanel chartPanel;

    public LineSmoother() {
        conn = Javaconnect.ConnecrDb();

        qdata();
        calPointAverage();
//        System.out.println("\n Size = " + avgPointList.size());
        loadAvgDataTable();

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
        System.out.println("Size = " + rowDataList.size());

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
/*
    public static void main(String[] args) {

        LineSmoother lm = new LineSmoother();
        lm.qdata();
        lm.calPointAverage();
        System.out.println("\n Size = " + lm.avgPointList.size());
        lm.loadAvgDataTable();


        
   }*/
    public void loadAvgDataTable() {
        for (int i = 0; i < avgPointList.size(); i++) {
            try {

                Statement statement = conn.createStatement();
                String qry = "insert into avg_data (WAVENUMBER , TRANSMITTANCE) values (?,?)";
                pst = conn.prepareStatement(qry);

                BigDecimal wavenum = rowDataList.get(i).getWavenumber();
                BigDecimal transmittance = avgPointList.get(i);

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

    }


}
