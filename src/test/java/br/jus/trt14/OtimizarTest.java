/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14;

import br.jus.trt14.converter.Converter;
import br.jus.trt14.gui.Otimizar;
import br.jus.trt14.model.NoMockOptionPane;
import br.jus.trt14.tools.Utils;
import br.jus.trt14.util.UtilsTest;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 
 *
 */
public class OtimizarTest {

    public boolean terminou = false;

    public OtimizarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        Otimizar.optionPane = new NoMockOptionPane();
        Converter.optionPane = new NoMockOptionPane();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        
    }

    @After
    public void tearDown() {
        Utils.cleanTempFolder();
    }

    //@Test
    public void testGUIOtimizarArquivoComProtecao() {
        terminou = false;
        final Otimizar otimizar = new Otimizar();
        otimizar.setVisible(true);
        final JFileChooser chooser = otimizar.fc;
        
        JFileChooser fc = otimizar.fc;

        UtilsTest.addArquivo(fc, otimizar, chooser, "testes\\!@#$%&()_+`^{}çÀÈ^N^N^^ñªº\\Arquivo com Proteção.pdf");
        JProgressBar jpb = (JProgressBar) UtilsTest.lookupObject(otimizar, "jPBProgresso");

        jpb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JProgressBar jpb = (JProgressBar) e.getSource();
                if (jpb.getValue() == jpb.getMaximum()) {
                    //otimizar.dispose();
                    System.out.println("CHEGOU AO FINAL");
                    terminou = true;

                }
            }
        });

        UtilsTest.clicarBotao(otimizar, "jBAbrirDocumentoPDFEntrada", false);

        UtilsTest.setTextField(otimizar, "jTFSaidaPath", "testes\\Gerados\\otimizarArquivoProtegidoResultado.pdf");

        UtilsTest.clicarBotao(otimizar, "jBConverter", false);

        while (!terminou) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OtimizarTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        long gerado = UtilsTest.getLength("testes\\Gerados\\otimizarArquivoProtegidoResultado_1.pdf");
        long resultadoEsperado = UtilsTest.getLength("testes\\Resultados\\OtimizarArquivoProtecao_1.pdf");

        otimizar.dispose();
        
        assertEquals(gerado, resultadoEsperado);
    }

    //@Test
    public void testGUIOtimizarArquivoComProtecaoDividir() throws IOException {
        terminou = false;
        final Otimizar otimizar = new Otimizar();
        otimizar.setVisible(true);
        final JFileChooser chooser = otimizar.fc;

        JFileChooser fc = otimizar.fc;

        UtilsTest.addArquivo(fc, otimizar, chooser, "testes\\!@#$%&()_+`^{}çÀÈ^N^N^^ñªº\\DividirArquivos.pdf");
        JProgressBar jpb = (JProgressBar) UtilsTest.lookupObject(otimizar, "jPBProgresso");

        jpb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JProgressBar jpb = (JProgressBar) e.getSource();
                if (jpb.getValue() == jpb.getMaximum()) {
                    System.out.println("CHEGOU AO FINAL");
                    terminou = true;

                }
            }
        });

        UtilsTest.clicarBotao(otimizar, "jBAbrirDocumentoPDFEntrada", false);

        UtilsTest.setTextField(otimizar, "jTFSaidaPath", "testes\\Gerados\\otimizarDividirArquivos\\OtimizarDividirArquivos.pdf");

        UtilsTest.clicarBotao(otimizar, "jBConverter", false);

        while (!terminou) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OtimizarTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File[] resultadoEsperado = new File("testes\\Resultados\\otimizarDividirArquivos\\").listFiles();

        File[] resultadoGerado = new File("testes\\Gerados\\otimizarDividirArquivos\\").listFiles();

        otimizar.dispose();
        
        for (int i = 0; i < resultadoEsperado.length; i++) {
            assertEquals(UtilsTest.getLength(resultadoEsperado[i].getCanonicalPath()), UtilsTest.getLength(resultadoGerado[i].getCanonicalPath()));
        }
    }

   // @Test
    public void testGUIOtimizarArquivoComProtecaoDividirOCR() throws IOException {
        terminou = false;
        final Otimizar otimizar = new Otimizar();
        otimizar.setVisible(true);
        final JFileChooser chooser = otimizar.fc;

        JFileChooser fc = otimizar.fc;

        UtilsTest.addArquivo(fc, otimizar, chooser, "testes\\!@#$%&()_+`^{}çÀÈ^N^N^^ñªº\\DividirEOCR.pdf");
        JProgressBar jpb = (JProgressBar) UtilsTest.lookupObject(otimizar, "jPBProgresso");

        jpb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JProgressBar jpb = (JProgressBar) e.getSource();
                if (jpb.getValue() == jpb.getMaximum()) {
                    //otimizar.dispose();
                    System.out.println("CHEGOU AO FINAL testGUIOtimizarArquivoComProtecaoDividirOCR");
                    terminou = true;

                }
            }
        });

        UtilsTest.clicarBotao(otimizar, "jBAbrirDocumentoPDFEntrada", false);
        UtilsTest.setTextField(otimizar, "jTFPrimeiraPagina", "2");
        UtilsTest.setTextField(otimizar, "jTFUltimaPagina", "12");
        UtilsTest.alterarComboBox(otimizar, "jCBFinalidade", false, 2);
        UtilsTest.setTextField(otimizar, "jTFTamanhoArquivo1", "1");
        UtilsTest.clicarCheck(otimizar, "jCBdoOCR", false);

        UtilsTest.setTextField(otimizar, "jTFSaidaPath", "testes\\Gerados\\otimizarArquivoComProtecaoDividirOCR\\OtimizarDividirArquivos.pdf");

        UtilsTest.clicarBotao(otimizar, "jBConverter", false);

        while (!terminou) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OtimizarTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File[] resultadoEsperado = new File("testes\\Resultados\\otimizarArquivoComProtecaoDividirOCR\\").listFiles();

        File[] resultadoGerado = new File("testes\\Gerados\\otimizarArquivoComProtecaoDividirOCR\\").listFiles();
        
        System.exit(1);
        
        for (int i = 0; i < resultadoEsperado.length; i++) {
            assertEquals(UtilsTest.getLength(resultadoEsperado[i].getCanonicalPath()), UtilsTest.getLength(resultadoGerado[i].getCanonicalPath()));
        }
    }

    //@Test
    public void testGUIOtimizarArquivoComProtecaoDividirComRangeEspecifico() throws IOException, InterruptedException {
        terminou = false;
        final Otimizar otimizar = new Otimizar();
        otimizar.setVisible(true);
        final JFileChooser chooser = otimizar.fc;

        JFileChooser fc = otimizar.fc;

        UtilsTest.addArquivo(fc, otimizar, chooser, "testes\\!@#$%&()_+`^{}çÀÈ^N^N^^ñªº\\DividirArquivos.pdf");
        JProgressBar jpb = (JProgressBar) UtilsTest.lookupObject(otimizar, "jPBProgresso");

        jpb.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JProgressBar jpb = (JProgressBar) e.getSource();
                if (jpb.getValue() == jpb.getMaximum()) {
                    //otimizar.dispose();
                    System.out.println("CHEGOU AO FINAL");
                    terminou = true;

                }
            }
        });

        UtilsTest.clicarBotao(otimizar, "jBAbrirDocumentoPDFEntrada", false);
        UtilsTest.setTextField(otimizar, "jTFUltimaPagina", "20");

        UtilsTest.setTextField(otimizar, "jTFSaidaPath", "testes\\Gerados\\otimizarDividirArquivosRangeEspecifico\\otimizarDividirArquivosRangeEspecifico.pdf");

        UtilsTest.clicarBotao(otimizar, "jBConverter", false);
        while (!terminou) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OtimizarTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        File[] resultadoEsperado = new File("testes\\Resultados\\otimizarDividirArquivosRangeEspecifico\\").listFiles();

        File[] resultadoGerado = new File("testes\\Gerados\\otimizarDividirArquivosRangeEspecifico\\").listFiles();
        
        otimizar.dispose();
        
        for (int i = 0; i < resultadoEsperado.length; i++) {
            assertEquals(UtilsTest.getLength(resultadoEsperado[i].getCanonicalPath()), UtilsTest.getLength(resultadoGerado[i].getCanonicalPath()));
        }
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation //@Test. For example:
    //
    // //@Test
    // public void hello() {}
}
