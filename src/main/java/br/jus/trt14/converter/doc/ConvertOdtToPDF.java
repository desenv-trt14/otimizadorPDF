/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.doc;

import fr.opensagres.odfdom.converter.pdf.PdfConverter;
import fr.opensagres.odfdom.converter.pdf.PdfOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

/**
 *
 * @author dfs15
 */
public class ConvertOdtToPDF implements IConverter {

    @Override
    public boolean convert(String input, String output) {
        boolean retorno = false;
        if (input.toLowerCase().endsWith(".odt")) {
            try {
                File file = new File(input);

                OdfTextDocument document = OdfTextDocument.loadDocument(new FileInputStream(file));
                OutputStream out = new FileOutputStream(output);
                PdfOptions options = PdfOptions.create();

                PdfConverter.getInstance().convert(document, out, options);
                retorno = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return retorno;
    }

}
