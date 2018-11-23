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
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pramuditha Buddhini
 */
public class TriangularSmooth {
     Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public ArrayList<InputData> originalPoints = new ArrayList<InputData>();
    SortedMap<BigDecimal, BigDecimal> originalPointList = new TreeMap<BigDecimal, BigDecimal>();

    public TriangularSmooth() {
        conn = Javaconnect.ConnecrDb();

        qdata();
        fillMap();

    }

    public static void main(String[] args) {
        TriangularSmooth nw = new TriangularSmooth();

        for (BigDecimal wn : nw.originalPointList.keySet()) {
            BigDecimal key = wn;
            BigDecimal value = nw.originalPointList.get(wn);
            System.out.println(key + "," + value);
        }
        System.out.println("Size2 = " + nw.originalPointList.size());
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
}
