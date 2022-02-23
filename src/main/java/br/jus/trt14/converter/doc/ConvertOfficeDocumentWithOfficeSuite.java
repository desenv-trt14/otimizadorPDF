/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.doc;

import static br.jus.trt14.converter.doc.ConvertOfficeDocumentWithOfficeSuite.noOffice;
import br.jus.trt14.tools.Archive;
import br.jus.trt14.tools.ConverterDoc;
import br.jus.trt14.tools.Utils;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dfs15
 */
public class ConvertOfficeDocumentWithOfficeSuite implements IConverter {

    public static boolean noOffice = false;
    public static int timeout = 15;
    private boolean firstTime = true;

    @Override
    public boolean convert(final String input, final String output) {
        if (noOffice) {
            return false;
        }
        if (firstTime) {
            SuiteOffice.init();
            firstTime = false;
            if(!SuiteOffice.suiteOfficeExists){
                return false;
            }
        }
        boolean retorno = false;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(new ConverterDocWithTimeout(input, output));
        try {
            retorno = future.get(timeout, TimeUnit.SECONDS);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Conversão com a suite BrOffice, LibreOffice ou OpenOffice excedeu o tempo limite de " + timeout + " segundos\n"
                    + "A partir de agora a conversão será feita internamente pelo programa\ne pode não ter a mesma qualidade das ferramentas mencionadas.\n"
                    + "Caso a qualidade deixe a desejar considere reiniciar a sua máquina para destravar qualquer instância dos programas citados");
            noOffice = true;
            Logger.getLogger(ConvertOfficeDocumentWithOfficeSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }

}

class ConverterDocWithTimeout implements Callable<Boolean> {

    private final String input;
    private final String output;

    public ConverterDocWithTimeout(final String input, final String ouput) {
        this.input = input;
        this.output = ouput;
    }

    @Override
    public Boolean call() throws Exception {
        boolean retorno = false;
        if (input.toLowerCase().endsWith(".docx") || input.toLowerCase().endsWith(".odt")) {
            XMultiComponentFactory xMCF = null;

            try {

                xMCF = SuiteOffice.xContext.getServiceManager();

                Object oDesktop = xMCF.createInstanceWithContext(
                        "com.sun.star.frame.Desktop", SuiteOffice.xContext);

                XDesktop xDesktop = (XDesktop) UnoRuntime.queryInterface(
                        XDesktop.class, oDesktop);

                // Load the Document
                String workingDir = Utils.extractFolder(input).replace("\\", "/");
                String myTemplate = Utils.getFileName(input).replace("\\", "/");

                XComponentLoader xCompLoader = (XComponentLoader) UnoRuntime
                        .queryInterface(com.sun.star.frame.XComponentLoader.class, xDesktop);

                String sUrl = "file:///" + workingDir + myTemplate;

                PropertyValue[] propertyValues = new PropertyValue[0];

                propertyValues = new PropertyValue[1];
                propertyValues[0] = new PropertyValue();
                propertyValues[0].Name = "Hidden";
                propertyValues[0].Value = new Boolean(true);

                XComponent xComp = xCompLoader.loadComponentFromURL(
                        sUrl, "_blank", 0, propertyValues);

                // save as a PDF 
                XStorable xStorable = (XStorable) UnoRuntime
                        .queryInterface(XStorable.class, xComp);

                propertyValues = new PropertyValue[3];
                propertyValues[0] = new PropertyValue();
                propertyValues[0].Name = "Overwrite";
                propertyValues[0].Value = new Boolean(true);

                propertyValues[1] = new PropertyValue();
                propertyValues[1].Name = "FilterName";
                propertyValues[1].Value = "writer_pdf_Export";

                propertyValues[2] = new PropertyValue();
                propertyValues[2].Name = "SelectPdfVersion";
                propertyValues[2].Value = new Integer(1);

                // Appending the favoured extension to the origin document name
                xStorable.storeToURL(Archive.addFileProtocol(output), propertyValues);

                xComp.dispose();
                System.out.println("Saved " + output);
                retorno = true;

            } catch (Exception ex) {
                Logger.getLogger(ConverterDoc.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Conversão com a suite BrOffice, LibreOffice ou OpenOffice falhou.\n"
                        + "A partir de agora a conversão será feita internamente pelo programa\ne pode não ter a mesma qualidade.");
                noOffice = true;
            }
        }
        return retorno;

    }
}
