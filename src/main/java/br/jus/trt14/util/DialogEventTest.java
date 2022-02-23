/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * 
 */
public class DialogEventTest extends JDialog {

    public DialogEventTest() {
        this.setLayout(new GridLayout(0, 1));
        this.add(new JLabel("Dialog event test.", JLabel.CENTER));
        this.add(new JButton(new AbstractAction("Close") {

            @Override
            public void actionPerformed(ActionEvent e) {
                //DialogEventTest.this.setVisible(false);
                DialogEventTest.this.dispatchEvent(new WindowEvent(
                    DialogEventTest.this, WindowEvent.WINDOW_CLOSING));
            }
        }));

    }

    private void display() {
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
         new DialogEventTest().display();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               
            }
        });

    }
}