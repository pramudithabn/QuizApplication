/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.main;

import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pramuditha Buddhini
 */
class MyFileChooser extends JFileChooser{
    public MyFileChooser() {
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "Transmittance vs Wavenumver", "Absorbance vs Wavenumver" }));

        JPanel panel1 = (JPanel)this.getComponent(3);
        JPanel panel2 = (JPanel) panel1.getComponent(3);
        JLabel filetype = new JLabel();
        filetype.setText("File format:");
        Component c1=panel2.getComponent(0);//optional used to add the buttons after combobox
        Component c2=panel2.getComponent(1);//optional used to add the buttons after combobox
        panel2.removeAll();

        panel2.add(filetype);
        panel2.add(comboBox);
        panel2.add(c1);//optional used to add the buttons after combobox
        panel2.add(c2);//optional used to add the buttons after combobox

   }
}