/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14;

import br.jus.trt14.gui.dialogs.TelaConvertendo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 
 */
public class JuntarTest {

    public JuntarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        
    }

    @AfterClass
    public static void tearDownClass() {
        
    }

    @Before
    public void setUp() {
       
    }

    @After
    public void tearDown() {
      File file = new File("testes\\Gerados\\JuntarResultado.pdf");  
      file.delete();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
   // @Test
    public void testJuntar() {
        File resultadoEsperado = new File("testes\\Resultados\\Juntar.pdf");
        TelaConvertendo telaConvertendo = new TelaConvertendo();
        List<String> listaArquivos = new ArrayList<String>();
        File file = new File("testes\\!@#$%&()_+`^{}çÀÈ^N^N^^ñªº\\!@#$&()_+`^{}çÀÈ^N^N^^ñªº.pdf");
        listaArquivos.add(file.getAbsolutePath());
        file = new File("testes\\!@#$%&()_+`^{}çÀÈ^N^N^^ñªº\\Arquivo com Proteção.pdf");
        listaArquivos.add(file.getAbsolutePath());
        file = new File("testes\\Gerados\\JuntarResultado.pdf");
        telaConvertendo.iniciarDialog(listaArquivos, file.getAbsolutePath(), null);
        assertTrue(file.length() == resultadoEsperado.length());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
