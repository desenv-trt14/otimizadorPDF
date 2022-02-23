/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SwingTest extends JFrame {

    private JTable table = new JTable();
    private DefaultTableModel tm = new DefaultTableModel(new String[]{"File", "File Type", "Size", "Status"}, 0);
    private JScrollPane jScrollPane = new JScrollPane(table);

    public SwingTest() {
       
        table.setModel(tm);
        table.setDropTarget(new DropTarget() {
            @Override
            public synchronized void dragOver(DropTargetDragEvent dtde) {

                dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
            }

            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    Transferable t = dtde.getTransferable();
                    List fileList = null;
                    try {
                        fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                        if (fileList.size() > 0) {

                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            for (Object value : fileList) {
                                if (value instanceof File) {
                                    File f = (File) value;
                                    model.addRow(new Object[]{f.getAbsolutePath(), "", f.length(), "", ""});

                                }
                            }
                        }
                    } catch (UnsupportedFlavorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dtde.rejectDrop();
                }
            }

        });

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jScrollPane);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new SwingTest();
    }
}
