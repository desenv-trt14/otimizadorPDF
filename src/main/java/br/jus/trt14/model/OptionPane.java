/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.model;

import java.awt.Component;

/**
 *
 * @author 
 */
public interface OptionPane {

    /**
     * @see JOptionPane#showConfirmDialog(Component, Object, String, int, int);
     */
    int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType);

    void showMessageDialog(Component parentComponent, Object message, String title, int messageType);

    int showConfirmDialog(Component parentComponent, Object message, String title, int optionType);

}
