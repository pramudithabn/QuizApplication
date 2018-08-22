package msc.ftir;

import java.awt.BorderLayout;
import net.proteanit.sql.DbUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.sql.*;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import java.util.*;
import java.util.regex.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartPanel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pramuditha Buddhini
 */
public class FtirInterpreter extends javax.swing.JFrame {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private String fileName;
    ArrayList<Integer> errorLine = new ArrayList<>();

    /**
     * Creates new form HelloWorld
     */
    public FtirInterpreter() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        update_table();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        specPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        button1 = new javax.swing.JButton();
        uploadButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        button_specgen = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        specPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Validate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        specPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 80, -1));
        specPanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 326, -1));

        button1.setText("...");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        specPanel.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 20, 20));

        uploadButton.setText("Upload");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });
        specPanel.add(uploadButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 80, -1));

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(dataTable);

        specPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 305, 435));

        jPanel1.setBackground(new java.awt.Color(28, 67, 130));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        specPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, -1, 80));

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        specPanel.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 80, -1));

        button_specgen.setText("Generate Spectrum");
        button_specgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_specgenActionPerformed(evt);
            }
        });
        specPanel.add(button_specgen, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, -1, -1));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Open File");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(specPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(specPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed

        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "xlsx", "dpt", "csv");
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
//        chooser.setFileFilter(filter);
//        chooser.setAcceptAllFileFilterUsed(rootPaneCheckingEnabled);
        File dataFile = chooser.getSelectedFile();
        fileName = dataFile.getAbsolutePath();
        jTextField1.setText(fileName);
//fileChooser();

    }//GEN-LAST:event_button1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed

        vaidateInputData();
        try {

            System.out.println(fileName);
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            String[] value = null;

            while ((line = br.readLine()) != null) {

                //Make sure the line is not null, not empty, and contains data format
                if (!line.equals("") && line.matches("\\d{3,4}\\.\\d{6}[ \\t]\\d{1,2}\\.\\d{6}\\s")) {
                    
                    
                    String cvsfiletype = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(csv)";
                    String txtfiletype = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(txt)";
                    String xlsfiletype = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(xls|xlsx)";
                    String docfiletype = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(doc|docx)";
                    String dptfiletype = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(dpt)";
        
                    Pattern fileExtPatternCVS = Pattern.compile(cvsfiletype);
                    Pattern fileExtPatternTXT = Pattern.compile(txtfiletype);
                    Pattern fileExtPatternXLS = Pattern.compile(xlsfiletype);
                    Pattern fileExtPatternDOC = Pattern.compile(docfiletype);
                    Pattern fileExtPatternDPT = Pattern.compile(dptfiletype);

                    Matcher mtch1 = fileExtPatternCVS.matcher(fileName);
                    Matcher mtch2 = fileExtPatternTXT.matcher(fileName);
                    Matcher mtch3 = fileExtPatternXLS.matcher(fileName);
                    Matcher mtch4 = fileExtPatternDOC.matcher(fileName);
                    Matcher mtch5 = fileExtPatternDPT.matcher(fileName);
                    
        
                    
                    //if the file is CVS
                    if(mtch1.matches()){
                        value = line.split(","); //if the file is CVS
                    }
                    //if the file is text
                    else if(mtch2.matches()){
                        value = line.split("\\s+"); //whitespace regex 
                    }
                    else if(mtch3.matches()){
                        value = line.split(","); //whitespace regex 
                    }
                    
                    
//                   

                    String sql = "insert into input_data (WAVENUMBER , TRANSMITTANCE)" + "values ('" + value[0] + "','" + value[1] + "')";
                    pst = conn.prepareStatement(sql);
                    pst.executeUpdate();
                }
            }
            br.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        update_table();
    }//GEN-LAST:event_uploadButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        vaidateInputData();
        validateFileType();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear data?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);

        if (p == 0) {
            String sql = "delete from input_data";
            try {
                pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Delete Successful!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            update_table();
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void button_specgenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_specgenActionPerformed
        // TODO add your handling code here:

        generate_spectrum();
    }//GEN-LAST:event_button_specgenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FtirInterpreter().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button1;
    private javax.swing.JButton button_specgen;
    private javax.swing.JButton clearButton;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel specPanel;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables

    private void update_table() {

        try {
            String sql = "select WAVENUMBER , TRANSMITTANCE from input_data";

            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            dataTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    private void generate_spectrum() {
        try {

            String query1 = "select WAVENUMBER, TRANSMITTANCE from input_data";
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(Javaconnect.ConnecrDb(), query1);
            JFreeChart spec = ChartFactory.createLineChart("FTIR Spectrum", "Wavenumber", "Transmittance %", dataset, PlotOrientation.VERTICAL, false, true, true);
            BarRenderer renderer = null;
            CategoryPlot plot = null;
            renderer = new BarRenderer();
            ChartFrame frame = new ChartFrame("Spectrum", spec);
            frame.setVisible(true);
            frame.setSize(1000, 600);

//            // set the range axis to display integers only...
//            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void readlist() {
        List<String> al = new ArrayList<String>();
        try {
            FileReader fin = new FileReader(fileName);
            Scanner s = new Scanner(fin);

            s.useDelimiter(" ");

            int lineNum;
            String A;

            for (lineNum = 1; s.hasNextLine(); lineNum++) {

                A = s.next();
                if (!A.contains("##YUNITS=%T")) {
                    al.add(A);
                } else {
                    continue;
                }
            }

            for (int i = 0; i < al.size(); i++) {
                System.out.println(al.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void fileChooser() {
//       FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "xlsx", "dpt", "csv");
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
//        chooser.setFileFilter(filter);
//        chooser.setAcceptAllFileFilterUsed(rootPaneCheckingEnabled);
        File dataFile = chooser.getSelectedFile();
        fileName = dataFile.getAbsolutePath();
        jTextField1.setText(fileName);

    }

    private void vaidateInputData() {
//##YUNITS=%T 
//397.336096 9.683705 
//3842.201472 0.566372

        Matcher regrexMatch = null;
//        String point = "\\d{3,4}\\.\\d{6}\\s\\d{1,2}\\.\\d{6}\\s"; //[0-9]{3,4}\\.[0-9]{6}\\s[0-9]{1,2}\\.[0-9]{6}
        String point = "\\d{3,4}\\.\\d{6}[ \\t]\\d{1,2}\\.\\d{6}\\s"; //trying for DPT file works for txt as well
//        String point = "(?(#)##YUNITS=%T|\\d{3,4}\\.\\d{6}[ \\t]\\d{1,2}\\.\\d{6}\\s))";

        int invalid_input = 0;
        int valid_input = 0;
        int lineNumber = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {

                Pattern input_pattern = Pattern.compile(point);
                regrexMatch = input_pattern.matcher(line);
//                System.out.println("aaa"+line+"aaa");
                System.out.println(line);
                boolean m = regrexMatch.matches();
                System.out.println(m);

                if (!regrexMatch.matches()) {
                    ++invalid_input;
                    errorLine.add(lineNumber + 1);

                } else {
                    ++valid_input;

                }

                ++lineNumber;

            }
            br.close();

            System.out.println(invalid_input + " invalid inputs found at line #" + Arrays.toString(errorLine.toArray()));
            System.out.println("valid inputs = " + valid_input);
            System.out.println("Total Number of lines = " + lineNumber);

            if (invalid_input > 0) {

                JOptionPane.showMessageDialog(null, "Data format error!", "Error", JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Data format is correct!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void validateFileType() {

        String filetype = "(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(txt|csv|dpt|doc|docx|xls|xlsx)";
        Pattern fileExtPattern = Pattern.compile(filetype);

        Matcher mtch = fileExtPattern.matcher(fileName);
        if (mtch.matches()) {

            JOptionPane.showMessageDialog(null, "Vaild file format.");
            System.out.println(fileName);
            System.out.print(mtch.matches());

        } else {
            JOptionPane.showMessageDialog(null, "Invalid file format!", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void createTempFile() {
        File tempFile = null;
        BufferedWriter writer = null;

        try {
            tempFile = File.createTempFile("datafile", ".tmp");
            writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write("Writing data into temp file!!!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception ex) {
            }
        }
        System.out.println("Stored data in temporary file.");
    }

}
