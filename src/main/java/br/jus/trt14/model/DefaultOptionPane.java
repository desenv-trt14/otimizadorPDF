/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.model;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class DefaultOptionPane implements OptionPane {

      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
         return JOptionPane.showConfirmDialog(parentComponent,message,title,optionType,messageType);
      }

    @Override
    public void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent,message,title,messageType);
    }

    @Override
    public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent,message,title,optionType);
    }
    
}
