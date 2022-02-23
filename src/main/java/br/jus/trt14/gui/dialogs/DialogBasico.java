/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.gui.dialogs;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JDialog;

/**
 *
 * 
 */
public class DialogBasico extends JDialog {

    public DialogBasico(Frame owner) {
        super(owner);

    }

    public DialogBasico(Dialog owner, boolean modal) {
        super(owner, modal);
        setDialog();
    }

    public DialogBasico(java.awt.Frame parent, boolean modal) {
        super();
        setDialog();
    }

    public DialogBasico() {
        super();
        setDialog();
    }

    public void setDialog() {
        this.setResizable(false);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/iconeConversor.png")));
        this.setLocationRelativeTo(null);
 
    }
    
    

}
