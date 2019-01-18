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
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pramuditha Buddhini
 */
public class Scale {

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private static ArrayList<InputData> originalList = new ArrayList<InputData>();
    private int listSize;
    private SortedMap<BigDecimal, BigDecimal> fixedScaleList = new TreeMap<BigDecimal, BigDecimal>();

    public SortedMap<BigDecimal, BigDecimal> getFixedScaleList() {
        return fixedScaleList;
    }

    public void setFixedScaleList(SortedMap<BigDecimal, BigDecimal> fixedScaleList) {
        this.fixedScaleList = fixedScaleList;
    }

    public Scale() {
        conn = Javaconnect.ConnecrDb();
       
    }

    private ArrayList<InputData> qdata(String table) {
        String sql = "select * from "+table;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getInt("ID"), rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
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

    private BigDecimal findMaxT() {

        InputData max = originalList.stream().max(Comparator.comparing(InputData::getTransmittance)).orElseThrow(NoSuchElementException::new);
        return max.getTransmittance();

    }

    private BigDecimal findMinT() {
        InputData min = originalList.stream().min(Comparator.comparing(InputData::getTransmittance)).orElseThrow(NoSuchElementException::new);
        return min.getTransmittance();

    }

    public void fixScale(int start, int end, String table) {
                
        
        qdata(table);
        double max = findMaxT().doubleValue();
        double min = findMinT().doubleValue();
        double f;
        fixedScaleList.clear();
        
        for (int i = 0; i < listSize; i++) {
            double y = originalList.get(i).getTransmittance().doubleValue();
            BigDecimal x = originalList.get(i).getWavenumber();
            f = (((end - start) * (y - min)) / (max - min)) + start;
            fixedScaleList.put(x, BigDecimal.valueOf(f));
        }
    }
}
