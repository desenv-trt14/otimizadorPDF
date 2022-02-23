/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import br.jus.trt14.gui.Otimizar;
import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author 
 */
public class UtilsTest {

    public static void clicarBotao(final Object object, final String field, boolean thread) {
        JButton button = (JButton) lookupObject(object, field);
        button.doClick();

    }
    
    public static void alterarComboBox(final Object object, final String field, boolean thread, final Integer value) {
        JComboBox combobox = (JComboBox) lookupObject(object, field);
        combobox.setSelectedIndex(value);
    }

    public static void clicarCheck(final Object object, final String field, boolean thread) {
        JCheckBox button = (JCheckBox) lookupObject(object, field);
        button.doClick();

    }

    public static Object lookupObject(Object object, String field) {
        Object get = null;
        try {
            Field declaredField = object.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            get = declaredField.get(object);

        } catch (Exception ex) {
            Logger.getLogger(UtilsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return get;
    }

    public static void setTextField(final Otimizar otimizar, final String nameObject, final String value) {
        JTextField jTFSaidaPath = (JTextField) UtilsTest.lookupObject(otimizar, nameObject);
        jTFSaidaPath.setText(value);
    }

    public static long getLength(String file) {
        File gerado = new File(file);
        long length = gerado.length();
        System.out.println(file + " " + length);
        return length;
    }

    public static void addArquivo(JFileChooser fc, final Otimizar otimizar, final JFileChooser chooser, final String file) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        fc.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                otimizar.fc.setSelectedFile(new File(file));
                chooser.approveSelection();
                atomicBoolean.set(true);
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });

    }
}
