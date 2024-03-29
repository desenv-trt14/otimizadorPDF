/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.gui.dialogs;

import br.jus.trt14.converter.security.AssinaturaToken;
import br.jus.trt14.tools.Utils;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class RenameFiles extends DialogBasico {

    public static boolean deAgoraEmDiante = false;

    public final static int SUBSTITUIR = 1;
    public final static int RENOMEAR = 2;
    public static int acaoEscolhida = RENOMEAR;
    private String newName;
    private String file;

    /**
     * Creates new form RenameFiles
     */
    public RenameFiles(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jCAgoraEmDiante.setSelected(deAgoraEmDiante);
        jRBRenomear.setSelected(acaoEscolhida == RENOMEAR);
        jRBSubstituir.setSelected(acaoEscolhida == SUBSTITUIR);
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(jBOK);
        jBOK.requestFocus();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLMsg = new javax.swing.JLabel();
        jRBSubstituir = new javax.swing.JRadioButton();
        jRBRenomear = new javax.swing.JRadioButton();
        jCAgoraEmDiante = new javax.swing.JCheckBox();
        jBOK = new javax.swing.JButton();
        jLMsg1 = new javax.swing.JLabel();
        jLMostrarArquivo = new javax.swing.JLabel();

        buttonGroup1.add(jRBRenomear);
        buttonGroup1.add(jRBSubstituir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Conversão de arquivos para PDF/A");
        setMinimumSize(new java.awt.Dimension(569, 206));
        setModal(true);

        jLMsg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLMsg.setForeground(new java.awt.Color(102, 0, 0));
        jLMsg.setText("O arquivo %s já existe.");

        jRBSubstituir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRBSubstituir.setText("Substituir o arquivo");
        jRBSubstituir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBSubstituirActionPerformed(evt);
            }
        });

        jRBRenomear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRBRenomear.setSelected(true);
        jRBRenomear.setText("Renomear o arquivo para %s");
        jRBRenomear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBRenomearActionPerformed(evt);
            }
        });

        jCAgoraEmDiante.setText("Não me perguntar novamente");
        jCAgoraEmDiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCAgoraEmDianteActionPerformed(evt);
            }
        });

        jBOK.setText("OK");
        jBOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOKActionPerformed(evt);
            }
        });

        jLMsg1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLMsg1.setText("O que deseja fazer?");

        jLMostrarArquivo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLMostrarArquivo.setForeground(new java.awt.Color(0, 102, 255));
        jLMostrarArquivo.setText("<html><span style=\"text-decoration: underline\">Abrir</span></html>");
        jLMostrarArquivo.setToolTipText("Exibir arquivo");
        jLMostrarArquivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLMostrarArquivoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCAgoraEmDiante)
                            .addComponent(jRBRenomear)
                            .addComponent(jRBSubstituir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                        .addComponent(jBOK))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLMsg1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLMsg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLMostrarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLMsg)
                    .addComponent(jLMostrarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jBOK))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLMsg1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBSubstituir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRBRenomear)
                        .addGap(18, 18, 18)
                        .addComponent(jCAgoraEmDiante)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCAgoraEmDianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCAgoraEmDianteActionPerformed
        deAgoraEmDiante = jCAgoraEmDiante.isSelected();
    }//GEN-LAST:event_jCAgoraEmDianteActionPerformed

    private void jRBSubstituirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBSubstituirActionPerformed
        acaoEscolhida = SUBSTITUIR;
    }//GEN-LAST:event_jRBSubstituirActionPerformed

    private void jRBRenomearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBRenomearActionPerformed
        acaoEscolhida = RENOMEAR;
    }//GEN-LAST:event_jRBRenomearActionPerformed

    private void jBOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOKActionPerformed
        this.setVisible(false);
        this.dispose();
        this.dispatchEvent(new WindowEvent(
                this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jBOKActionPerformed

    private void jLMostrarArquivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLMostrarArquivoMouseClicked
        Utils.openFolder(file);
    }//GEN-LAST:event_jLMostrarArquivoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBOK;
    private javax.swing.JCheckBox jCAgoraEmDiante;
    private javax.swing.JLabel jLMostrarArquivo;
    private javax.swing.JLabel jLMsg;
    private javax.swing.JLabel jLMsg1;
    private javax.swing.JRadioButton jRBRenomear;
    private javax.swing.JRadioButton jRBSubstituir;
    // End of variables declaration//GEN-END:variables

    public void setNameFile(String file, String newName) {
        this.file = file;
        this.newName = newName;
        jLMsg.setText(String.format(jLMsg.getText(), file));
        jRBRenomear.setText(String.format(jRBRenomear.getText(), newName));
        try {
            int[] generatedSize = AssinaturaToken.generatedSize(jLMsg.getText(), 14);
            this.setSize(generatedSize[0] + 100, this.getSize().height);
        } catch (Exception ex) {
            Logger.getLogger(RenameFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
