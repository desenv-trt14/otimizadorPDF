/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author dfs15
 */
public class ConvertDocxToPDF implements IConverter {

    @Override
    public boolean convert(String input, String output) {
        boolean retorno = false;
        if (input.toLowerCase().endsWith(".docx")) {
            try {
                InputStream doc = new FileInputStream(new File(input));
                XWPFDocument document = new XWPFDocument(doc);
                org.apache.poi.xwpf.converter.pdf.PdfOptions options = org.apache.poi.xwpf.converter.pdf.PdfOptions.create();

                OutputStream out = new FileOutputStream(output);
                org.apache.poi.xwpf.converter.pdf.PdfConverter.getInstance().convert(document, out, options);
                retorno = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return retorno;
    }

}
