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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pramuditha Buddhini
 */
public class GradientDescent {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private static final double TOLERANCE = 1E-11;
    ArrayList<InputData> avgList = new ArrayList<InputData>();

    private double theta0;
    private double theta1;

    public void setTheta0(double theta0) {
        this.theta0 = theta0;
    }

    public void setTheta1(double theta1) {
        this.theta1 = theta1;
    }

    public double getTheta0() {
        return theta0;
    }

    public double getTheta1() {
        return theta1;
    }

    public GradientDescent(double theta0, double theta1) {
        conn = Javaconnect.ConnecrDb();
        this.theta0 = theta0;
        this.theta1 = theta1;
    }

    public BigDecimal getHypothesisResult(BigDecimal x) {
        return x.multiply(BigDecimal.valueOf(theta0 + theta1));
                }

    private BigDecimal getResult(ArrayList<InputData> avgData, boolean enableFactor) {
        BigDecimal result = null;
        for (int i = 0; i < avgData.size(); i++) {
            result = (getHypothesisResult(avgData.get(i).getWavenumber()).subtract(avgData.get(i).getTransmittance()));
            if (enableFactor) {
                result = result.multiply(avgData.get(i).getWavenumber());
            }
        }
        return result;
    }

    public void train(double learningRate, ArrayList<InputData> trainingData) {
        int iteration = 0;
        double delta0, delta1;
        do {
            iteration++;
//            System.out.println("SUBS: " + (learningRate * ((double) 1 / trainingData.length)) * getResult(trainingData, false));
            double temp0 = theta0 - learningRate * (((double) 1 / trainingData.size()) * getResult(trainingData, false).doubleValue());
            double temp1 = theta1 - learningRate * (((double) 1 / trainingData.size()) * getResult(trainingData, true).doubleValue());
            delta0 = theta0 - temp0;
            delta1 = theta1 - temp1;
            theta0 = temp0;
            theta1 = temp1;
        } while ((Math.abs(delta0) + Math.abs(delta1)) > TOLERANCE);
        System.out.println(iteration);
    }

    private static final double[][] TDATA = {{200, 20000}, {300, 41000}, {900, 141000}, {800, 41000}, {400, 51000}, {500, 61500}};
//    ArrayList<Double> NORMALIZEDDATA = new ArrayList<Double>();
    private static final double[][] NTDATA = null;

    public void normalize(double[][] TDATA) {

        double avg = getAverage();
        double max = getMaxValue(TDATA);
        double min = getMinValue(TDATA);

        for (int i = 0; i < TDATA.length; i++) {

            double yi = (TDATA[i][1] - avg) / (max - min);
            NTDATA[i][0] = TDATA[i][0];
            NTDATA[i][1] = yi;

        }

    }

    private static double getAverage() {
        int counter = 0;
        double sum = 0;
        for (int i = 0; i < TDATA.length; i++) {
            for (int j = 0; j < TDATA.length; j++) {
                sum = sum + TDATA[i][1];
                counter++;
            }
        }

        return sum / counter;
    }

    public static double getMaxValue(double[][] numbers) {
        double maxValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {

            if (numbers[j][1] > maxValue) {
                maxValue = numbers[j][1];
            }

        }
        return maxValue;
    }

    public static double getMinValue(double[][] numbers) {
        double minValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {

            if (numbers[j][1] < minValue) {
                minValue = numbers[j][1];
            }

        }
        return minValue;
    }

    public static void main(String[] args) {
        GradientDescent gd = new GradientDescent(0, 0);
        gd.train(0.00001, gd.qdata());
        System.out.println("THETA0: " + gd.getTheta0() + " - THETA1: " + gd.getTheta1());
        System.out.println("PREDICTION: " + gd.getHypothesisResult(BigDecimal.valueOf(300)));
    }

    public ArrayList<InputData> qdata() {

        String sql = "select * from avg_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getInt("ID"), rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
                avgList.add(d);

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

        int listSize = avgList.size();

        return avgList;

    }
}
