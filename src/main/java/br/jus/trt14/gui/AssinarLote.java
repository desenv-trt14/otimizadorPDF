/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.gui;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.converter.security.AssinaturaToken;
import br.jus.trt14.converter.security.ValidacaoDocumento;
import br.jus.trt14.gui.dialogs.Atualizacao;
import br.jus.trt14.tools.Archive;
import br.jus.trt14.tools.Preferences;
import br.jus.trt14.tools.Utils;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * 
 */
public class AssinarLote extends FrameBasico {

    public static boolean verificarCertificado = true;
    public static boolean addCarimboTempo = true;
    public static boolean append = false;
    public static boolean finished = false;
    public static boolean pastaAssinados = false;

    private DefaultTableModel dtm = null;
    public static boolean assOdtFile = true;

    /**
     * Creates new form Assinar
     */
    public AssinarLote() {
        initComponents();
        this.position();

        super.setBasicEvents(jScrollPane1, jTblListaPDF, jLStatus);

        dtm = (DefaultTableModel) jTblListaPDF.getModel();

        verificarCertificado = true;
        addCarimboTempo = true;
        append = false;
        finished = false;
        pastaAssinados = false;

        if (!Preferences.isMensagemAssinaturaDigitalDefinida()) {
            Preferences.setPreferences(Constant.MENSAGEM_ASSINATURA_DIGITAL, "Assinado digitalmente na forma da Lei 11.419/2006 por ${USUARIO} em: ${DATA_HORA}.");
        }

         
        
        
        

        this.setLocationRelativeTo(null);
        
        
        
    }

    public void iniciarArgumentos(List<String> listaArquivos) {
        for (String listaArquivo : listaArquivos) {
            dtm.addRow(new Object[]{listaArquivo, Utils.getPageCount(listaArquivo)});
        }
        this.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLSuperior = new javax.swing.JLabel();
        jBAssinar = new javax.swing.JButton();
        jPEntrada = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblListaPDF = new javax.swing.JTable();
        jLblTextoSelecionePDF = new javax.swing.JLabel();
        jBAbrirDocumentoPDFEntrada = new javax.swing.JButton();
        jBExcluirItemLista = new javax.swing.JButton();
        jBDescerOrdemItemLista = new javax.swing.JButton();
        jBSubirOrdemItemLista = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jCVerificacaoCertificado = new javax.swing.JCheckBox();
        jCCarimboAssinatura = new javax.swing.JCheckBox();
        jCPermitirOutrasAssinaturas = new javax.swing.JCheckBox();
        jCPastaAssinados = new javax.swing.JCheckBox();
        jCAssOdtFile = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("JT - Assinador de arquivos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("PDF");

        jLSuperior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/trt14_logo_small.png"))); // NOI18N
        jLSuperior.setAlignmentX(0.5F);

        jBAssinar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/doc_convert.png"))); // NOI18N
        jBAssinar.setText("Assinar PDFs");
        jBAssinar.setToolTipText("Assina digitalmente todos os documentos convertidos");
        jBAssinar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAssinarActionPerformed(evt);
            }
        });

        jPEntrada.setBorder(javax.swing.BorderFactory.createTitledBorder("Assinar documentos"));
        jPEntrada.setToolTipText("Você pode arrastar e soltar documentos para essa tabela");

        jTblListaPDF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Caminho", "Páginas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblListaPDF.setToolTipText("<html>Você pode arrastar e soltar documentos <b>()</b> para essa tabela.<br>Clique duplo abre o arquivo selecionado</html>");
        jTblListaPDF.setDragEnabled(true);
        jTblListaPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblListaPDFMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTblListaPDFMouseReleased(evt);
            }
        });
        jTblListaPDF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTblListaPDFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTblListaPDFFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(jTblListaPDF);
        if (jTblListaPDF.getColumnModel().getColumnCount() > 0) {
            jTblListaPDF.getColumnModel().getColumn(1).setMinWidth(50);
            jTblListaPDF.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTblListaPDF.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        jLblTextoSelecionePDF.setText("Selecione (ou arraste e solte) os arquivos. Os documentos adicionados são convertidos em PDF/A automaticamente");

        jBAbrirDocumentoPDFEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gnome_folder_open_16.png"))); // NOI18N
        jBAbrirDocumentoPDFEntrada.setText("Incluir");
        jBAbrirDocumentoPDFEntrada.setMaximumSize(new java.awt.Dimension(100, 25));
        jBAbrirDocumentoPDFEntrada.setMinimumSize(new java.awt.Dimension(100, 25));
        jBAbrirDocumentoPDFEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirDocumentoPDFEntradaActionPerformed(evt);
            }
        });

        jBExcluirItemLista.setText("Excluir");
        jBExcluirItemLista.setEnabled(false);
        jBExcluirItemLista.setMaximumSize(new java.awt.Dimension(100, 23));
        jBExcluirItemLista.setMinimumSize(new java.awt.Dimension(100, 23));
        jBExcluirItemLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExcluirItemListaActionPerformed(evt);
            }
        });

        jBDescerOrdemItemLista.setText("↓");
        jBDescerOrdemItemLista.setToolTipText("Descer documento");
        jBDescerOrdemItemLista.setEnabled(false);
        jBDescerOrdemItemLista.setMaximumSize(new java.awt.Dimension(100, 23));
        jBDescerOrdemItemLista.setMinimumSize(new java.awt.Dimension(100, 23));
        jBDescerOrdemItemLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDescerOrdemItemListaActionPerformed(evt);
            }
        });

        jBSubirOrdemItemLista.setText("↑");
        jBSubirOrdemItemLista.setToolTipText("Subir documento");
        jBSubirOrdemItemLista.setEnabled(false);
        jBSubirOrdemItemLista.setMaximumSize(new java.awt.Dimension(100, 23));
        jBSubirOrdemItemLista.setMinimumSize(new java.awt.Dimension(100, 23));
        jBSubirOrdemItemLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubirOrdemItemListaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPEntradaLayout = new javax.swing.GroupLayout(jPEntrada);
        jPEntrada.setLayout(jPEntradaLayout);
        jPEntradaLayout.setHorizontalGroup(
            jPEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPEntradaLayout.createSequentialGroup()
                        .addComponent(jLblTextoSelecionePDF)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(jPEntradaLayout.createSequentialGroup()
                        .addComponent(jBAbrirDocumentoPDFEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBExcluirItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBSubirOrdemItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBDescerOrdemItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPEntradaLayout.setVerticalGroup(
            jPEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEntradaLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLblTextoSelecionePDF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBExcluirItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBAbrirDocumentoPDFEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBDescerOrdemItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBSubirOrdemItemLista, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Otimizador");

        jLStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.gif")));
        jLStatus.setText("  ");
        jLStatus.setToolTipText("Status");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opções de assinatura"));

        jCVerificacaoCertificado.setSelected(true);
        jCVerificacaoCertificado.setText("Verificações extras de segurança (requer conexão a internet)");
        jCVerificacaoCertificado.setToolTipText("<html>Verifica lista de certificados revogados, incluindo-a no documento, permitindo uma validação offline pelo leitor de PDF.<br> Para usar esse recurso seu computador precisa ter uma conexão a internet</html>");
        jCVerificacaoCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCVerificacaoCertificadoActionPerformed(evt);
            }
        });

        jCCarimboAssinatura.setText("Não adicionar carimbo de assinatura");
        jCCarimboAssinatura.setToolTipText("Não adiciona imagem de assinatura na vertical.");
        jCCarimboAssinatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCCarimboAssinaturaActionPerformed(evt);
            }
        });

        jCPermitirOutrasAssinaturas.setText("Permitir assinatura de terceiros");
        jCPermitirOutrasAssinaturas.setToolTipText("<html>Permite que terceiros assinem esse documento. <br><b>Caso esta opção esteja desabilitada sua assinatura será definitiva <br> e nenhuma modificação será permitida nesse documento (incluindo outras assinaturas)<br> sem tornar a sua assinatura inválida</b></html>");
        jCPermitirOutrasAssinaturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPermitirOutrasAssinaturasActionPerformed(evt);
            }
        });

        jCPastaAssinados.setText("Gerar pasta assinados");
        jCPastaAssinados.setToolTipText("<html>Gera uma pasta \"assinados\" no mesmo diretório do primeiro documento <br><b>Caso esta opção esteja desabilitada os arquivos serão substituídos pela versão assinada.</b></html>");
        jCPastaAssinados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPastaAssinadosActionPerformed(evt);
            }
        });

        jCAssOdtFile.setSelected(true);
        jCAssOdtFile.setText("Adicionar \"Ass.\" ao arquivo de origem (ODT)");
        jCAssOdtFile.setToolTipText("<html>Gera uma pasta \"assinados\" no mesmo diretório do primeiro documento <br><b>Caso esta opção esteja desabilitada os arquivos serão substituídos pela versão assinada.</b></html>");
        jCAssOdtFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCAssOdtFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCVerificacaoCertificado)
                    .addComponent(jCCarimboAssinatura)
                    .addComponent(jCPermitirOutrasAssinaturas)
                    .addComponent(jCPastaAssinados)
                    .addComponent(jCAssOdtFile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jCVerificacaoCertificado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCCarimboAssinatura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCPermitirOutrasAssinaturas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCPastaAssinados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCAssOdtFile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(239, 239, 239)
                        .addComponent(jBAssinar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPEntrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))))
                .addGap(11, 11, 11)
                .addComponent(jPEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBAssinar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPEntrada.getAccessibleContext().setAccessibleName("Juntar documentos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBAssinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAssinarActionPerformed
        final DefaultTableModel model = (DefaultTableModel) jTblListaPDF.getModel();
        if (jTblListaPDF.getRowCount() > 0) {
            int rowCount = jTblListaPDF.getRowCount();
            final List<String[]> listaArquivos = new ArrayList<String[]>();
            //Transforma os documentos para PDF/A
            for (int i = 0; i < rowCount; i++) {
                String fileName = (String) jTblListaPDF.getValueAt(i, 0);
                ValidacaoDocumento validacaoDocumento = new ValidacaoDocumento(fileName);
                if (validacaoDocumento.isAssinaturaFinal()) {
                    String msg = "O arquivo %s já está assinado e marcado "
                            + "como assinatura final.\nSe continuar as assinaturas de:\n%sserão removidas.\n"
                            + "Confirma a exclusão desse documento da lista de assinatura?";
                    String signatarios = validacaoDocumento.getNomeSignatarios();
                    msg = String.format(msg, fileName, signatarios);
                    boolean confirmaAcao = Utils.confirmaAcao(msg, "Atenção!");
                    if (confirmaAcao) {
                        model.removeRow(i);
                        continue;
                    } else {
                        validacaoDocumento.removeSignature();
                    }
                }
                String randomFileName = Utils.getRandomFileName();
                Archive.copy(new File(fileName), new File(randomFileName));
                listaArquivos.add(new String[]{randomFileName, fileName});
            }
            final JFrame parent = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AssinaturaToken assinaturaToken = new AssinaturaToken(null);
                    try {
                        if (listaArquivos.size() > 0) {
                            Utils.noToolTip();
                            assinaturaToken.sign(listaArquivos, true);
                            model.setRowCount(0);
                            Utils.defaultToolTip();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(AssinarLote.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        finished = true;
                    }
                    Utils.cleanTempFolder();
                }
            }).start();

        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione pelo menos um arquivo", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBAssinarActionPerformed

    private void jTblListaPDFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTblListaPDFFocusGained
        controlarBotaoExcluir(jTblListaPDF, jBExcluirItemLista);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jTblListaPDFFocusGained


    private void jTblListaPDFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTblListaPDFFocusLost
        jTblListaPDFFocusGained(evt);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jTblListaPDFFocusLost

    private void jTblListaPDFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblListaPDFMouseClicked
        jTblListaPDFFocusGained(null);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jTblListaPDFMouseClicked

    private void jTblListaPDFMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblListaPDFMouseReleased
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jTblListaPDFMouseReleased

    private void jBAbrirDocumentoPDFEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrirDocumentoPDFEntradaActionPerformed
        addFile(jTblListaPDF, jLStatus);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);

    }//GEN-LAST:event_jBAbrirDocumentoPDFEntradaActionPerformed


    private void jBExcluirItemListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExcluirItemListaActionPerformed
        excluirDocumentoPDF(jTblListaPDF);
        jTblListaPDFFocusGained(null);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jBExcluirItemListaActionPerformed


    private void jBDescerOrdemItemListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDescerOrdemItemListaActionPerformed
        down(jTblListaPDF, jBDescerOrdemItemLista, jBSubirOrdemItemLista);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jBDescerOrdemItemListaActionPerformed

    private void jBSubirOrdemItemListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubirOrdemItemListaActionPerformed
        up(jTblListaPDF, jBDescerOrdemItemLista, jBSubirOrdemItemLista);
        controlaBotaoOrdem(jBDescerOrdemItemLista, jBSubirOrdemItemLista, jTblListaPDF);
    }//GEN-LAST:event_jBSubirOrdemItemListaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void jCPermitirOutrasAssinaturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPermitirOutrasAssinaturasActionPerformed
        append = jCPermitirOutrasAssinaturas.isSelected();
    }//GEN-LAST:event_jCPermitirOutrasAssinaturasActionPerformed

    private void jCCarimboAssinaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCarimboAssinaturaActionPerformed
        addCarimboTempo = !jCCarimboAssinatura.isSelected();
    }//GEN-LAST:event_jCCarimboAssinaturaActionPerformed

    private void jCVerificacaoCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCVerificacaoCertificadoActionPerformed
        verificarCertificado = jCVerificacaoCertificado.isSelected();
    }//GEN-LAST:event_jCVerificacaoCertificadoActionPerformed

    private void jCPastaAssinadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPastaAssinadosActionPerformed
        pastaAssinados = jCPastaAssinados.isSelected();
    }//GEN-LAST:event_jCPastaAssinadosActionPerformed

    private void jCAssOdtFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCAssOdtFileActionPerformed
        assOdtFile = jCPastaAssinados.isSelected();
    }//GEN-LAST:event_jCAssOdtFileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AssinarLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AssinarLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AssinarLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AssinarLote.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AssinarLote().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbrirDocumentoPDFEntrada;
    private javax.swing.JButton jBAssinar;
    private javax.swing.JButton jBDescerOrdemItemLista;
    private javax.swing.JButton jBExcluirItemLista;
    private javax.swing.JButton jBSubirOrdemItemLista;
    private javax.swing.JCheckBox jCAssOdtFile;
    private javax.swing.JCheckBox jCCarimboAssinatura;
    private javax.swing.JCheckBox jCPastaAssinados;
    private javax.swing.JCheckBox jCPermitirOutrasAssinaturas;
    private javax.swing.JCheckBox jCVerificacaoCertificado;
    private javax.swing.JLabel jLStatus;
    private javax.swing.JLabel jLSuperior;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLblTextoSelecionePDF;
    private javax.swing.JPanel jPEntrada;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblListaPDF;
    // End of variables declaration//GEN-END:variables

}