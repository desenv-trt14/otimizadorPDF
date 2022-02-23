/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.events;

import br.jus.trt14.converter.doc.ConverterFileToPDF;
import br.jus.trt14.gui.Validar;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * 
 */
public class DragableTextField extends DropTarget {

    private final JTextField field;
    private final Callable<Void> myFunc;
    private boolean onlyPDF = false;

    @Override
    public synchronized void drop(DropTargetDropEvent dtde) {
        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        Transferable t = dtde.getTransferable();
        List fileList = null;
        try {
            fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
            String filename = fileList.get(0).toString();
            if (filename.toLowerCase().endsWith(".pdf")) {
                field.setText(filename);
                myFunc.call();
            } else {
                if (!onlyPDF) {
                    String convertFile = ConverterFileToPDF.convertFile(filename);
                    if (!convertFile.isEmpty()) {
                        field.setText(convertFile);
                        myFunc.call();
                    }
                }
            }
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DragableTextField.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DragableTextField(JTextField field, boolean onlyPDF, Callable<Void> myFunc) {
        this.field = field;
        this.myFunc = myFunc;
        this.onlyPDF = onlyPDF;
    }
}
