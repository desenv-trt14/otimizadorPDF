/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.gui.dialogs;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.tools.Preferences;
import javax.swing.JOptionPane;

/**
 *
 * 
 */
public class Configuracao extends DialogBasico {

    static boolean restartProgram = false;

    public Configuracao() {
        super();
        initComponents();
    }

    public void iniciarDialog() {
        jCGerarPDF1B.setSelected(Preferences.isPDF1B());
        jCPesquisavel.setSelected(Preferences.isDocumentoPesquisavel());
        if (Preferences.isTamanhoDefinido()) {
            jCTamanhoCustomizado.doClick();
        }
        jTTamanhoArquivo.setText(String.valueOf(Preferences.getSizeArquivo()));

        jLMsgReiniciar.setVisible(restartProgram);

        if (Preferences.isMensagemAssinaturaDigitalDefinida()) {
            String mensagem = Preferences.getPreferences(Constant.MENSAGEM_ASSINATURA_DIGITAL, "");
            if (!mensagem.equals(jCMensagemAssinatura.getItemAt(0)) && !mensagem.equals(jCMensagemAssinatura.getItemAt(1))) {
                jCMensagemAssinatura.addItem(mensagem);
                jCMensagemAssinatura.setSelectedIndex(jCMensagemAssinatura.getItemCount() - 1);
            } else {
                for (int i = 0; i < jCMensagemAssinatura.getItemCount(); i++) {
                    if (mensagem.equals(jCMensagemAssinatura.getItemAt(i))) {
                        jCMensagemAssinatura.setSelectedIndex(i);
                    }
                }
            }

        } else {
            Preferences.setPreferences(Constant.MENSAGEM_ASSINATURA_DIGITAL, jCMensagemAssinatura.getSelectedItem().toString());
        }

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setModal(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jCGerarPDF1B = new javax.swing.JCheckBox();
        jLMsgReiniciar = new javax.swing.JLabel();
        jCTamanhoCustomizado = new javax.swing.JCheckBox();
        jTTamanhoArquivo = new javax.swing.JTextField();
        jLEInforme = new javax.swing.JLabel();
        jCMensagemAssinatura = new javax.swing.JComboBox<>();
        jCPesquisavel = new javax.swing.JCheckBox();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configurações");
        setModal(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opções"));

        jCGerarPDF1B.setText("Gerar PDFA-1b (AFD)");
        jCGerarPDF1B.setToolTipText("<html>\nResolução: 300 dpi<br>\nFinalidade da Conversão: Não dividir<br>\nTornar PDF Pesquisável: SIM\n</html>");
        jCGerarPDF1B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCGerarPDF1BActionPerformed(evt);
            }
        });

        jLMsgReiniciar.setForeground(new java.awt.Color(255, 0, 51));
        jLMsgReiniciar.setText("Para que as configurações tenham efeito você precisará reiniciar o programa.");

        jCTamanhoCustomizado.setText("Tamanho customizado (MB) ##.#");
        jCTamanhoCustomizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCTamanhoCustomizadoActionPerformed(evt);
            }
        });

        jTTamanhoArquivo.setEnabled(false);
        jTTamanhoArquivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTamanhoArquivoFocusLost(evt);
            }
        });
        jTTamanhoArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTTamanhoArquivoActionPerformed(evt);
            }
        });
        jTTamanhoArquivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTTamanhoArquivoKeyReleased(evt);
            }
        });

        jLEInforme.setText("Texto da assinatura digital:");

        jCMensagemAssinatura.setEditable(true);
        jCMensagemAssinatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Assinado digitalmente na forma da Lei 11.419/2006 por ${USUARIO} em: ${DATA_HORA}.", "Assinado digitalmente na forma do Decreto 8.539/2015 por ${USUARIO} em: ${DATA_HORA}." }));
        jCMensagemAssinatura.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCMensagemAssinaturaItemStateChanged(evt);
            }
        });
        jCMensagemAssinatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMensagemAssinaturaActionPerformed(evt);
            }
        });
        jCMensagemAssinatura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCMensagemAssinaturaKeyReleased(evt);
            }
        });

        jCPesquisavel.setText("Documento sempre pesquisável");
        jCPesquisavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPesquisavelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCMensagemAssinatura, 0, 662, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLMsgReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCTamanhoCustomizado)
                                    .addComponent(jCGerarPDF1B))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTTamanhoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLEInforme)
                            .addComponent(jCPesquisavel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCGerarPDF1B)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCTamanhoCustomizado)
                    .addComponent(jTTamanhoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCPesquisavel)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLEInforme)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCMensagemAssinatura, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLMsgReiniciar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCMensagemAssinaturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMensagemAssinaturaKeyReleased

    }//GEN-LAST:event_jCMensagemAssinaturaKeyReleased

    private void jCMensagemAssinaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMensagemAssinaturaActionPerformed
        Preferences.setPreferences(Constant.MENSAGEM_ASSINATURA_DIGITAL, jCMensagemAssinatura.getSelectedItem().toString());
    }//GEN-LAST:event_jCMensagemAssinaturaActionPerformed

    private void jCMensagemAssinaturaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCMensagemAssinaturaItemStateChanged

    }//GEN-LAST:event_jCMensagemAssinaturaItemStateChanged

    private void jTTamanhoArquivoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTamanhoArquivoKeyReleased

        try {
            if (Preferences.isTamanhoDefinido()) {
                Float valueOf = Float.valueOf(jTTamanhoArquivo.getText());
                if (valueOf == 0) {
                    throw new IllegalArgumentException();
                }
                Preferences.setPreferences(Constant.TAMANHO_ARQUIVO, String.valueOf(Float.valueOf(jTTamanhoArquivo.getText())));
            }
        } catch (Exception e) {
            jTTamanhoArquivo.setText(String.valueOf(Preferences.getSizeArquivo()));
        }
    }//GEN-LAST:event_jTTamanhoArquivoKeyReleased

    private void jTTamanhoArquivoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTamanhoArquivoFocusLost
        try {
            if (Preferences.isTamanhoDefinido()) {
                Float valueOf = Float.valueOf(jTTamanhoArquivo.getText());
                if (valueOf == 0) {
                    throw new IllegalArgumentException();
                }
            }
        } catch (Exception e) {
            jTTamanhoArquivo.setText(String.valueOf(Preferences.getSizeArquivo()));
            JOptionPane.showMessageDialog(null, "Tamanho inválido! Defina um número (formato ##.#) com o valor mínimo de 0.1 .");
        }
    }//GEN-LAST:event_jTTamanhoArquivoFocusLost

    private void jTTamanhoArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTTamanhoArquivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTTamanhoArquivoActionPerformed

    private void jCTamanhoCustomizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCTamanhoCustomizadoActionPerformed
        jTTamanhoArquivo.setEnabled(jCTamanhoCustomizado.isSelected());
        Preferences.setPreferences(Constant.TAMANHO_ARQUIVO_DEFINIDO, jCTamanhoCustomizado.isSelected() ? "S" : "N");
        if (jCTamanhoCustomizado.isSelected()) {
            Preferences.setPreferences(Constant.TAMANHO_ARQUIVO, String.valueOf(Preferences.getSizeArquivo()));
        }
    }//GEN-LAST:event_jCTamanhoCustomizadoActionPerformed

    private void jCGerarPDF1BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGerarPDF1BActionPerformed
        Preferences.setPreferences(Constant.GERAR_PDF_1b, jCGerarPDF1B.isSelected() ? "S" : "N");
        restartProgram = true;
        jLMsgReiniciar.setVisible(restartProgram);
    }//GEN-LAST:event_jCGerarPDF1BActionPerformed

    private void jCPesquisavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPesquisavelActionPerformed
        Preferences.setPreferences(Constant.GERAR_DOCUMENTO_PESQUISAVEL, jCPesquisavel.isSelected() ? "S" : "N");
        restartProgram = true;
        jLMsgReiniciar.setVisible(restartProgram);
    }//GEN-LAST:event_jCPesquisavelActionPerformed

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
            java.util.logging.Logger.getLogger(Configuracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configuracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configuracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configuracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCGerarPDF1B;
    private javax.swing.JComboBox<String> jCMensagemAssinatura;
    private javax.swing.JCheckBox jCPesquisavel;
    private javax.swing.JCheckBox jCTamanhoCustomizado;
    private javax.swing.JLabel jLEInforme;
    private javax.swing.JLabel jLMsgReiniciar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTTamanhoArquivo;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
