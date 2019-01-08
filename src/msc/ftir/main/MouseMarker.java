/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.main;

/**
 *
 * @author Pramuditha Buddhini
 */
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.Layer;

public final class  MouseMarker extends MouseAdapter{
        private Marker marker;
        private static Double markerStart = Double.NaN;
        private static Double markerEnd = Double.NaN;

    public static Double getMarkerStart() {
        return markerStart;
    }

    public void setMarkerStart(Double markerStart) {
        this.markerStart = markerStart;
    }

    public static Double getMarkerEnd() {
        return markerEnd;
    }

    public void setMarkerEnd(Double markerEnd) {
        this.markerEnd = markerEnd;
    }
        private final XYPlot plot;
        private final JFreeChart chart;
        private  final ChartPanel panel;


        public MouseMarker(ChartPanel panel) {
            this.panel = panel;
            this.chart = panel.getChart();
            this.plot = (XYPlot) chart.getPlot();
        }

        private void updateMarker(){
            if (marker != null){
                plot.removeDomainMarker(marker,Layer.BACKGROUND);
            }
            if (!( markerStart.isNaN() && markerEnd.isNaN())){
                if ( markerEnd > markerStart){
                    marker = new IntervalMarker(markerStart, markerEnd);
                    marker.setPaint(new Color(0xff, 0xff, 0xff)); //0xDD, 0xFF, 0xDD, 0x80
                    marker.setAlpha(0.5f);
                    plot.addDomainMarker(marker,Layer.BACKGROUND);
                }
            }
        }

        private Double getPosition(MouseEvent e){
            Point2D p = panel.translateScreenToJava2D( e.getPoint());
            Rectangle2D plotArea = panel.getScreenDataArea();
            XYPlot plot = (XYPlot) chart.getPlot();
            return plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            markerEnd = getPosition(e);
            updateMarker();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            markerStart = getPosition(e);
        }
    }
