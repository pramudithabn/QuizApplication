/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pramuditha Buddhini
 */
public class DbConnector {

    Connection conn = null;

    public static Connection ConnecrDb() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "root");
//            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public ArrayList<Question> qdata() throws SQLException {

        ArrayList<Question> list = new ArrayList<Question>();

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM QUESTION;");

            Question q;
            while (rs.next()) {
                q = new Question(rs.getInt("ID"), rs.getString("DESCRIPTION"), rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4"), rs.getBytes("PIC"), rs.getInt("ANSWER"));
                list.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
