/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import msc.ftir.main.InputData;
import msc.ftir.main.Javaconnect;
import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Pramuditha Buddhini
 */
public class Interpolator {

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private static ArrayList<InputData> originalList = new ArrayList<InputData>();
    private ArrayList<BigDecimal> wavelenths = new ArrayList<BigDecimal>();
    private int listSize;

    public Interpolator() {
        conn = Javaconnect.ConnecrDb();
        qdata();
    }

    public ArrayList<InputData> qdata() {

        String sql = "select WAVENUMBER, TRANSMITTANCE from avg_data";
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            InputData d;
            while (rs.next()) {
                d = new InputData(rs.getBigDecimal("WAVENUMBER"), rs.getBigDecimal("TRANSMITTANCE"));
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

    private SortedMap<BigDecimal, BigDecimal> interpolatedDataset = new TreeMap<BigDecimal, BigDecimal>();

    public SortedMap<BigDecimal, BigDecimal> linearInterp(XYDataset dataset, int size) {

        double[] y = new double[size];
        double[] x = new double[size];
//        double[] xi = new double[listSize];
        wavelenths.clear();
        interpolatedDataset.clear();

        for (int i = 0; i < size; i++) {
            x[i] = dataset.getXValue(0, i);
            y[i] = dataset.getYValue(0, i);
        }

        for (int i = 0; i < listSize; i++) {
            double w = originalList.get(i).getWavenumber().doubleValue();
            if (w > x[0] && w < x[x.length - 1]) {

                wavelenths.add(BigDecimal.valueOf(w));

            }

        }
      
//        System.out.println(x[0] + " " + x[x.length - 1]);
//        System.out.println(wavelenths.get(0));

        LinearInterpolator li = new LinearInterpolator(); // or other interpolator
        PolynomialSplineFunction psf = li.interpolate(x, y);
        

        double[] yi = new double[wavelenths.size()];
        for (int i = 0; i < wavelenths.size(); i++) {

            yi[i] = psf.value(wavelenths.get(i).doubleValue());

            interpolatedDataset.put(wavelenths.get(i), BigDecimal.valueOf(yi[i]));
        }
        System.out.println(interpolatedDataset.size());
        return interpolatedDataset;
    }
    
    public SortedMap<BigDecimal, BigDecimal> splineInterp(XYDataset dataset, int size) {

        double[] y = new double[size];
        double[] x = new double[size];
        double[] xi = new double[listSize];
        wavelenths.clear();
        interpolatedDataset.clear();

        for (int i = 0; i < size; i++) {
            x[i] = dataset.getXValue(0, i);
            y[i] = dataset.getYValue(0, i);
        }

        for (int i = 0; i < listSize; i++) {
            double w = originalList.get(i).getWavenumber().doubleValue();
            if (w > x[0] && w < x[x.length - 1]) {
//                xi[i] = w;
                wavelenths.add(BigDecimal.valueOf(w));

            }

        }
      
        System.out.println(x[0] + " " + x[x.length - 1]);
        System.out.println(wavelenths.get(0));

        SplineInterpolator li = new SplineInterpolator(); // or other interpolator
        PolynomialSplineFunction psf = li.interpolate(x, y);
        
//        UnivariateInterpolator
         


        double[] yi = new double[wavelenths.size()];
        for (int i = 0; i < wavelenths.size(); i++) {
//            yi[i] = psf.value(xi[i]);
            yi[i] = psf.value(wavelenths.get(i).doubleValue());

            interpolatedDataset.put(wavelenths.get(i), BigDecimal.valueOf(yi[i]));
        }
//        System.out.println(interpolatedDataset.size());
        return interpolatedDataset;
    }
    
      public SortedMap<BigDecimal, BigDecimal> cubicInterp(XYDataset dataset, int size) {

        double[] y = new double[size];
        double[] x = new double[size];
        double[] xi = new double[listSize];
        wavelenths.clear();
        interpolatedDataset.clear();

        for (int i = 0; i < size; i++) {
            x[i] = dataset.getXValue(0, i);
            y[i] = dataset.getYValue(0, i);
        }

        for (int i = 0; i < listSize; i++) {
            double w = originalList.get(i).getWavenumber().doubleValue();
            if (w > x[0] && w < x[x.length - 1]) {
//                xi[i] = w;
                wavelenths.add(BigDecimal.valueOf(w));

            }

        }
      
        System.out.println(x[0] + " " + x[x.length - 1]);
        System.out.println(wavelenths.get(0));

        AkimaSplineInterpolator  li = new AkimaSplineInterpolator (); // or other interpolator
        PolynomialSplineFunction psf = li.interpolate(x, y);
        
//        UnivariateInterpolator
         


        double[] yi = new double[wavelenths.size()];
        for (int i = 0; i < wavelenths.size(); i++) {
//            yi[i] = psf.value(xi[i]);
            yi[i] = psf.value(wavelenths.get(i).doubleValue());

            interpolatedDataset.put(wavelenths.get(i), BigDecimal.valueOf(yi[i]));
        }
//        System.out.println(interpolatedDataset.size());
        return interpolatedDataset;
    }
    
    
}
