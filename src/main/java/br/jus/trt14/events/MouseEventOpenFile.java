/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.events;

import br.jus.trt14.tools.Utils;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * 
 */
public class MouseEventOpenFile extends MouseAdapter {

    public void mouseClicked(java.awt.event.MouseEvent evt) {
        JTable table = (JTable) evt.getSource();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.getRowCount();

        Point p = evt.getPoint();
        int row = table.rowAtPoint(p);
        int selectedRow = table.convertRowIndexToModel(row);

        if (evt.getClickCount() == 2) {
            String file = model.getValueAt(selectedRow, 0).toString();
            Utils.openFolder(file);
        }
    }
}
