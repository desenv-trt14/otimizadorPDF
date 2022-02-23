/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.model;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;

/**
 *
 * @author 
 */
public class NoMockOptionPane extends DefaultOptionPane {

    @Override
    public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        return JOptionPane.NO_OPTION;
    }

    @Override
    public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
        return JOptionPane.NO_OPTION;
    }

    @Override
    public void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
    }
}
