package msc.ftir;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pramuditha Buddhini
 */
import java.sql.*;
import javax.swing.*;
public class Javaconnect {
    
    Connection conn = null;
    public static Connection ConnecrDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
//          Connection conn =  DriverManager.getConnection("jdbc:mysql:E:\\MSCWorkspace\\Ftir\\ftir.sql");
            Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ftir","root","root");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
}
