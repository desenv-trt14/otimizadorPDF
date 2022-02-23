/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * 
 */
public class JLabelExt extends JLabel {

    @Override
    public void enable() {
        super.enable(); //To change body of generated methods, choose Tools | Templates.
        JOptionPane.showMessageDialog(null, "");
    }

}
