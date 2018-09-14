/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Pramuditha Buddhini
 */
public class Qreader {
    
    PreparedStatement pst = null;
    ResultSet rs = null;
    Connection conn =null;
    
//     try {
//            String sql = "select WAVENUMBER , TRANSMITTANCE from input_data";
//
//            pst = conn.prepareStatement(sql);
//            rs = pst.executeQuery();
////            dataTable.setModel(DbUtils.resultSetToTableModel(rs));
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        } finally {
//            try {
//                rs.close();
//                pst.close();
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//            }
//        }
    
}
