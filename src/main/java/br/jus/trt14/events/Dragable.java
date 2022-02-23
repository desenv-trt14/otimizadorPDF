/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.events;

import br.jus.trt14.tools.Utils;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import static javax.swing.JComponent.WHEN_FOCUSED;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public class Dragable extends DropTarget {

    private JTable jTable;
    private List<String> acceptedTypes;
    private JLabel jLabel;
    private List<String> acceptedOtherFiles;

    public Dragable(final JTable jTable, List<String> acceptedTypes, JLabel jLabel, List<String> acceptedOtherFiles) {
        this.jTable = jTable;
        this.acceptedTypes = acceptedTypes;
        this.jLabel = jLabel;
        this.acceptedOtherFiles = acceptedOtherFiles;

        InputMap inputMap = this.jTable.getInputMap(WHEN_FOCUSED);
        ActionMap actionMap = this.jTable.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        actionMap.put("delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int row = jTable.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) jTable.getModel();
                model.removeRow(row);
            }

        });
    }

    @Override
    public synchronized void drop(DropTargetDropEvent dtde) {
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            Transferable t = dtde.getTransferable();
            List fileList = null;
            try {
                fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                final File[] filesArray = new File[fileList.size()];
                final File[] filesArrayFinal = (File[]) fileList.toArray(filesArray);
                if (filesArray.length > 0) {
                    final DefaultTableModel model = (DefaultTableModel) jTable.getModel();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.addFiles(filesArrayFinal, model, jLabel, acceptedTypes, acceptedOtherFiles);
                        }
                    }).start();

                }
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        } else {
            dtde.rejectDrop();
        }
    }

}
