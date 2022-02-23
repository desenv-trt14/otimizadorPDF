package br.jus.trt14.job;

import br.jus.trt14.converter.Converter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ThreadConverter implements Runnable {

    private Converter converter;
    private JProgressBar jPBProgresso;
    private JLabel jLTempoEstimado;
    private JButton jBConverter;
    private JButton jBCancelar;

    public ThreadConverter(Converter converter, JProgressBar jPBProgresso, JLabel jLabel, JButton jBConverter, JButton jBCancelar) {
        super();
        this.converter = converter;
        this.jPBProgresso = jPBProgresso;
        this.jLTempoEstimado = jLabel;
        this.jBConverter = jBConverter;
        this.jBCancelar = jBCancelar;
    }

    public void run() {   
        if (converter != null) {
            if (converter.converter(jPBProgresso, jLTempoEstimado, jBConverter, jBCancelar)) {
            } else {
            }
        }
    }
}
