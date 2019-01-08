/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.smooth;

import msc.ftir.main.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static msc.ftir.smooth.SlidingAvgSmooth.count;

/**
 *
 * @author Pramuditha Buddhini
 */
public class TriangularSmooth_Selection {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public ArrayList<InputData> originalPoints = new ArrayList<InputData>();
    SortedMap<BigDecimal, BigDecimal> originalPointList = new TreeMap<BigDecimal, BigDecimal>();
    public ArrayList<BigDecimal> smoothedPoints = new ArrayList<BigDecimal>();
    private static volatile TriangularSmooth_Selection instance;
    private int listSize = 0;
    private ArrayList<Integer> filteredIDList = new ArrayList<Integer>();
    private int startx, endx;

    public TriangularSmooth_Selection() {
        conn = Javaconnect.ConnecrDb();

        qdata();
        fillMap();

    }

//    static {
//        instance = new TriangularSmooth();
//    }
    public void reset() {
        instance = null;
    }

    public void reverse() {

        reset();
        count = 0;
        qdata();//qdata()
        smoothedPoints.clear();//empty smmothed points array
        clearAvgTable();//empty table

        for (int i = 0; i < originalPoints.size(); i++) {
            smoothedPoints.add(originalPoints.get(i).getTransmittance());
        }
    }

    public static TriangularSmooth_Selection getInstance() {
        instance = new TriangularSmooth_Selection();
        return instance;
    }

    public static void main(String[] args) {
        TriangularSmooth_Selection nw = new TriangularSmooth_Selection();

        for (BigDecimal wn : nw.originalPointList.keySet()) {
            BigDecimal key = wn;
            BigDecimal value = nw.originalPointList.get(wn);
            System.out.println(key + "," + value);
        }
        System.out.println("Size2 = " + nw.originalPointList.size());
        System.out.println("Size1 = " + nw.originalPoints.size());
    }

    public ArrayList<InputData> qdata() {

        String sql = "select * from input_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            originalPoints.clear();
            while (rs.next()) {
                d = new InputData(rs.getInt("ID"), rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
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
        listSize = originalPoints.size();
        return originalPoints;

    }

    public void fillMap() {
        for (int i = 0; i < this.originalPoints.size(); i++) {

            originalPointList.put(this.originalPoints.get(i).getWavenumber(), this.originalPoints.get(i).getTransmittance());
        }
    }


    public void cal_5point_avg(int start, int end) {

        if (!smoothedPoints.isEmpty()) {
            for (int i = 0; i < smoothedPoints.size(); i++) {
                originalPoints.get(i).setTransmittance(smoothedPoints.get(i));
            }
        }
        smoothedPoints.clear();

        smoothedPoints.clear();
        double sum = 0;

        BigDecimal entry = null;
        int rindex = 0;
        int listSize = originalPoints.size();

        BigDecimal start0 = originalPoints.get(0).getTransmittance();//0 1st element
        BigDecimal start1 = originalPoints.get(1).getTransmittance();//1 2nd element
        BigDecimal end1 = originalPoints.get(listSize - 1).getTransmittance();//n-2 (n-1)th element
        BigDecimal end2 = originalPoints.get(listSize - 2).getTransmittance();//n-1 (n)th element

        smoothedPoints.add(start0);
        smoothedPoints.add(start1);

        for (rindex = start; rindex < end ; rindex++) {

            double n1 = (originalPoints.get(rindex - 2).getTransmittance()).doubleValue();
            double n2 = (originalPoints.get(rindex - 1).getTransmittance()).doubleValue();
            double n3 = (originalPoints.get(rindex).getTransmittance()).doubleValue();
            double n4 = (originalPoints.get(rindex + 1).getTransmittance()).doubleValue();
            double n5 = (originalPoints.get(rindex + 2).getTransmittance()).doubleValue();

            sum = n1 + 2 * n2 + 3 * n3 + 2 * n4 + n5;

//            avg = sum/5;
            entry = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(9), 8, RoundingMode.HALF_UP);

            smoothedPoints.add(entry);

        }
        smoothedPoints.add(end1);
        smoothedPoints.add(end2);
        count++;

    }


    public void updateSmoothedValue() {
        clearAvgTable();
        String fullarrays = "";
        for (int i = 0; i < originalPoints.size(); i++) {
            String twoarrays = "(" + originalPoints.get(i).getWavenumber() + " , " + smoothedPoints.get(i) + ")";
            fullarrays = fullarrays + twoarrays + ",";
        }
        fullarrays = fullarrays.substring(0, fullarrays.length() - 1);

        String sql = "INSERT INTO avg_data (wavenumber,transmittance)  VALUES " + fullarrays;
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

   

    public void clearAvgTable() {

        String sql1 = "delete from avg_data";
        try {
            pst = conn.prepareStatement(sql1);
            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                pst.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    //smooth the selected section only
    public void marked_section_smoothing_algorithm() {
        filteredIDList.clear();
        BigDecimal diff = null;
        double start = MouseMarker.getMarkerStart();
        double end = MouseMarker.getMarkerEnd();
        Double d2 = new Double(start);
        Double d3 = new Double(end);
        int startIndex = 0, endIndex = 0;

        for (int i = 0; i < listSize; i++) {

            double w = originalPoints.get(i).getWavenumber().doubleValue();
            Double d1 = new Double(w);

            if ((d2 < d1) && (d1 < d3)) {
                int x = originalPoints.get(i).getId();
                filteredIDList.add(x);

            }
        }

        startx = getIndexById(filteredIDList.get(0));
        endx = getIndexById(filteredIDList.get(filteredIDList.size() - 1));

    }

    //select a section and run a smooth only to that section, other sections remain same
    public void smooth_selected_section() {
        clearAvgTable();
        marked_section_smoothing_algorithm();
        updateUnsmoothedSection(0, startx);
        cal_5point_avg(startx,endx);
//MainWindow.getPoints()
//int k=9;
//        switch (k) {
//            case 3:
//                cal_3point_avg(startx, endx);
//            case 5:
//                cal_5point_avg(startx, endx);
//            case 7:
//                cal_7point_avg(startx, endx);
//            case 9:
//                cal_9point_avg(startx, endx);
//        }
        updateSmoothedValue(startx, endx);
        updateUnsmoothedSection(endx, listSize);
    }

    private int getIndexById(int id) {
        for (int i = 0; i < originalPoints.size(); i++) {
            if (originalPoints != null && (originalPoints.get(i).getId() == id)) {
                return i;
            }
        }
        return -1;// not there is list
    }

//Update selected section
    public void updateUnsmoothedSection(int start, int end) {
//        clearAvgTable();
        String fullarrays = "";
        System.out.println(start + " " + (end - 1));
        for (int i = start; i < end; i++) {
            String twoarrays = "(" + originalPoints.get(i).getWavenumber() + " , " + originalPoints.get(i).getTransmittance() + ")";
            fullarrays = fullarrays + twoarrays + ",";
        }
        fullarrays = fullarrays.substring(0, fullarrays.length() - 1);

        String sql = "INSERT INTO avg_data (wavenumber,transmittance)  VALUES " + fullarrays;
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

    public void updateSmoothedValue(int start, int end) {
//        clearAvgTable();
        String fullarrays = "";
        int i, j;
        System.out.println(start + " " + (end - 1));
        for (i = start, j = 0; i < end && j < smoothedPoints.size(); i++, j++) { //this line has used for i,j both at once
            String twoarrays = "(" + originalPoints.get(i).getWavenumber() + " , " + smoothedPoints.get(j) + ")";
            fullarrays = fullarrays + twoarrays + ",";
        }
        fullarrays = fullarrays.substring(0, fullarrays.length() - 1);

        String sql = "INSERT INTO avg_data (wavenumber,transmittance)  VALUES " + fullarrays;

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
