/*
 * Tribunal Regional do Trabalho - TRT14
 * Esse código foi criado por está regional,
 */
package br.jus.trt14.gui.dialogs;

import br.jus.trt14.gui.AssinarLote;
import br.jus.trt14.gui.Juntar;
import br.jus.trt14.tools.Utils;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author  <>
 */
public class TelaConvertendo extends DialogBasico {

    /**
     * Creates new form Carregando
     */
    public TelaConvertendo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jLabel1.setText("Convertendo Arquivos, Aguarde!!");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loading.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(TelaConvertendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConvertendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConvertendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConvertendo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConvertendo().setVisible(true);
            }
        });
    }

    public void iniciarDialog(final List<String> arquivosEntrada, final String saida, final Component comp) {
        this.setModal(true);
        this.setLocationRelativeTo(comp);
        boolean retorno = false;
        final javax.swing.JDialog tela = this;

        Thread tarefa;
        tarefa = new Thread() {
            @Override
            public void run() {
                try {
                    Utils.juntarArquivos(arquivosEntrada, saida);
                    setVisible(false);

                    if (!Juntar.assinarDigitalmente) {
                        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Conversão concluída com sucesso! \nDeseja abrir? ", "Sucesso!", JOptionPane.OK_CANCEL_OPTION);
                        if (showConfirmDialog == JOptionPane.YES_OPTION) {
                            Utils.openFolder(Utils.extractFolder(saida));
                        }
                    } else {
                        AssinarLote assinarLote = new AssinarLote();
                        assinarLote.setMostrarPrincipal(false);
                        assinarLote.iniciarArgumentos(Arrays.asList(new String[]{saida}));
                        comp.setVisible(false);
                        assinarLote.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                comp.setVisible(true);
                            }
                        });
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TelaConvertendo.class.getName()).log(Level.SEVERE, null, ex);
                    tela.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Erro ao juntar os arquivos", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        };
        tarefa.start();

        this.setVisible(true);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
