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
import java.util.ArrayList;
import msc.ftir.util.FileType;

/**
 *
 * @author Pramuditha Buddhini
 */
public class TransToAbs {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private FileType fileType;
    private ArrayList<BigDecimal> transmittance = new ArrayList<BigDecimal>(); // store them in an arraylist
    private ArrayList<BigDecimal> wavenumber = new ArrayList<BigDecimal>();
    private ArrayList<BigDecimal> absorbance = new ArrayList<BigDecimal>();
    int size;

    public TransToAbs() {
        conn = Javaconnect.ConnecrDb();
        convert();
        updateTable();
        

    }

    public void convert() {

        String query1 = "select * from input_data"; // get all the id's from the table 

        try {
            PreparedStatement stmnt = conn.prepareStatement(query1);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                wavenumber.add(rs.getBigDecimal("Wavenumber"));
                transmittance.add(rs.getBigDecimal("Transmittance"));
                size = transmittance.size();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }

        for (int i = 0; i < size; i++) {
            double a = transmittance.get(i).doubleValue();
            BigDecimal abs = BigDecimal.valueOf(2 - Math.log10(a));
            BigDecimal a2 = abs.setScale(8, RoundingMode.HALF_UP);
//            BigDecimal bd = abs.setScale(8, RoundingMode.HALF_UP);
            System.out.println(a2);
            absorbance.add(a2);
        }
    }

    public void updateTable() {
        String fullarrays = "";
        for (int i = 0; i < size; i++) {
            String twoarrays = "(" + wavenumber.get(i) + " , " + absorbance.get(i) + ")";
            fullarrays = fullarrays + twoarrays + ",";
        }
        fullarrays = fullarrays.substring(0, fullarrays.length() - 1);

        String sql = "INSERT INTO abs_data (WAVENUMBER,TRANSMITTANCE)  VALUES " + fullarrays ;
        System.out.println(sql);

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
