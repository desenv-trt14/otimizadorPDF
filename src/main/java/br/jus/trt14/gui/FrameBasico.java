/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.gui;

import br.jus.trt14.events.DragableAndDeleteTable;
import br.jus.trt14.events.MouseEventOpenFile;
import br.jus.trt14.tools.Utils;
import br.jus.trt14.tools.Versao;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 */
public class FrameBasico extends JFrame {

    public static boolean MODO_IMPRESSAO = false;

    public List<String> allowedTypesOffice = Arrays.asList(new String[]{".pdf", ".odt", ".docx"});
    public final static List<String> allowedTypesOthersThanOffice = Arrays.asList(new String[]{".jpg", ".png", ".jpeg"});

    public FrameBasico() {

    }

    public String getAllowedFiles(List<String> allowedTypesOffice, List<String> allowedTypesOthersThanOffice) {
        StringBuilder builder = new StringBuilder();
        for (String allowedTypesOthersThanOffice1 : allowedTypesOffice) {
            builder.append("*" + allowedTypesOthersThanOffice1 + ";");
        }
        for (String allowedTypesOthersThanOffice1 : allowedTypesOthersThanOffice) {
            builder.append("*" + allowedTypesOthersThanOffice1 + ";");
        }
        return builder.toString();
    }

    public void position() {
        try {
            Integer versao = Versao.getVersion();
            this.setTitle(this.getTitle() + " - Rev:" + versao);

        } catch (Exception ex) {
            Logger.getLogger(Otimizar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setResizable(false);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/iconeConversor.png")));
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!(e.getSource() instanceof Principal)) {
                    if (MODO_IMPRESSAO) {
                        System.exit(0);
                    } else {
                        FrameBasico basico = (FrameBasico) e.getSource();
                        if (basico.isMostrarPrincipal()) {
                            basico.dispose();
                            Principal principal = new Principal();
                            principal.setVisible(true);
                        }
                    }

                } else {
                    Utils.cleanTempFolder();
                    System.exit(0);
                }
            }
        });
    }

    private boolean mostrarPrincipal = true;

    public void setMostrarPrincipal(boolean mostrarPrincipal) {
        this.mostrarPrincipal = mostrarPrincipal;
    }

    public boolean isMostrarPrincipal() {
        return mostrarPrincipal;
    }

    protected void up(JTable table, JButton jBDescerOrdemItemLista, JButton jBSubirOrdemItemLista) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        final int posicaoLinhaSelecionada = table.getSelectedRow();
        final int posicaoLinhaSuperior = posicaoLinhaSelecionada - 1;

        Object aux = dtm.getDataVector().elementAt(posicaoLinhaSuperior);
        Object linhaSuperior = dtm.getDataVector().elementAt(posicaoLinhaSuperior);
        Object linhaSelecionada = dtm.getDataVector().elementAt(posicaoLinhaSelecionada);

        linhaSuperior = linhaSelecionada;
        linhaSelecionada = aux;

        String tituloLinhaSelecionada = (String) ((Vector) linhaSelecionada).get(0);
        String qtdPaginasLinhaSelecionada = ((Vector) linhaSelecionada).get(1).toString();

        String tituloLinhaSuperior = (String) ((Vector) linhaSuperior).get(0);
        String qtdPaginasLinhaSuperior = ((Vector) linhaSuperior).get(1).toString();

        dtm.setValueAt(tituloLinhaSuperior, posicaoLinhaSuperior, 0);
        dtm.setValueAt(qtdPaginasLinhaSuperior, posicaoLinhaSuperior, 1);
        table.setRowSelectionInterval(posicaoLinhaSuperior, posicaoLinhaSuperior);
        dtm.setValueAt(tituloLinhaSelecionada, posicaoLinhaSelecionada, 0);
        dtm.setValueAt(qtdPaginasLinhaSelecionada, posicaoLinhaSelecionada, 1);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, table);

    }

    protected void down(JTable table, JButton jBDescerOrdemItemLista, JButton jBSubirOrdemItemLista) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        final int posicaoLinhaSelecionada = table.getSelectedRow();
        final int posicaoLinhaInferior = posicaoLinhaSelecionada + 1;

        Object aux = dtm.getDataVector().elementAt(posicaoLinhaInferior);
        Object linhaInferior = dtm.getDataVector().elementAt(posicaoLinhaInferior);
        Object linhaSelecionada = dtm.getDataVector().elementAt(posicaoLinhaSelecionada);

        linhaInferior = linhaSelecionada;
        linhaSelecionada = aux;

        String tituloLinhaSelecionada = (String) ((Vector) linhaSelecionada).get(0);
        String qtdPaginasLinhaSelecionada = (String) ((Vector) linhaSelecionada).get(1).toString();

        String tituloLinhaInferior = (String) ((Vector) linhaInferior).get(0);
        String qtdPaginasLinhaInferior = (String) ((Vector) linhaInferior).get(1).toString();

        dtm.setValueAt(tituloLinhaInferior, posicaoLinhaInferior, 0);
        dtm.setValueAt(qtdPaginasLinhaInferior, posicaoLinhaInferior, 1);
        table.setRowSelectionInterval(posicaoLinhaInferior, posicaoLinhaInferior);
        dtm.setValueAt(tituloLinhaSelecionada, posicaoLinhaSelecionada, 0);
        dtm.setValueAt(qtdPaginasLinhaSelecionada, posicaoLinhaSelecionada, 1);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, table);
    }

    protected void controlaBotaoOrdem(JButton jBDescerOrdemItemLista, JButton jBSubirOrdemItemLista, JTable jTblListaPDF) {
        jBDescerOrdemItemLista.setEnabled(true);
        jBSubirOrdemItemLista.setEnabled(true);

        if (jTblListaPDF.getSelectedRow() == 0 && jTblListaPDF.getRowCount() > 1) {
            jBDescerOrdemItemLista.setEnabled(true);
            jBSubirOrdemItemLista.setEnabled(false);
        } else if (jTblListaPDF.getSelectedRow() == jTblListaPDF.getRowCount() - 1 && jTblListaPDF.getRowCount() > 1) {
            jBDescerOrdemItemLista.setEnabled(false);
            jBSubirOrdemItemLista.setEnabled(true);
        } else if (jTblListaPDF.getRowCount() <= 1 || jTblListaPDF.getSelectedRow() == -1) {
            jBDescerOrdemItemLista.setEnabled(false);
            jBSubirOrdemItemLista.setEnabled(false);
        }

    }

    protected void setBasicEvents(JScrollPane jScrollPane1, JTable jTblListaPDF, JLabel jLStatus) {
        jScrollPane1.setToolTipText(jTblListaPDF.getToolTipText().replace("()", "(" + getAllowedFiles(allowedTypesOffice, allowedTypesOthersThanOffice) + ")"));
        jTblListaPDF.setToolTipText(jTblListaPDF.getToolTipText().replace("()", "(" + getAllowedFiles(allowedTypesOffice, allowedTypesOthersThanOffice) + ")"));
        jScrollPane1.setDropTarget(new DragableAndDeleteTable(jTblListaPDF, allowedTypesOffice, jLStatus, allowedTypesOthersThanOffice));
        jTblListaPDF.setDropTarget(new DragableAndDeleteTable(jTblListaPDF, allowedTypesOffice, jLStatus, allowedTypesOthersThanOffice));
        jTblListaPDF.addMouseListener(new MouseEventOpenFile());
        Utils.defaultToolTip();
    }

    protected void controlarBotaoExcluir(JTable jTblListaPDF, JButton jBExcluirItemLista) {
        if (jTblListaPDF.getSelectedRow() != -1) {
            jBExcluirItemLista.setEnabled(true);
        } else {
            jBExcluirItemLista.setEnabled(false);
        }
    }

    protected void addFile(JTable jTblListaPDF, final JLabel jLStatus) {
        Utils.cleanTempFolder();
        FileDialog dialog = new FileDialog(this);
        dialog.setMultipleMode(true);
        dialog.setFile(getAllowedFiles(allowedTypesOffice, allowedTypesOthersThanOffice));
        dialog.setVisible(true);

        final DefaultTableModel tableModel = (DefaultTableModel) jTblListaPDF.getModel();

        if (dialog.getFiles().length > 0) {
            final File[] files = dialog.getFiles();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Utils.addFiles(files, tableModel, jLStatus, allowedTypesOffice, allowedTypesOthersThanOffice);
                }
            }).start();

        }
    }

    protected void excluirDocumentoPDF(JTable jTblListaPDF) {
        if (jTblListaPDF.getSelectedRow() != -1) {
            DefaultTableModel dtm = (DefaultTableModel) jTblListaPDF.getModel();
            dtm.removeRow(jTblListaPDF.getSelectedRow());
        }

    }

}
