/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.converter.doc.SuiteOffice;
import br.jus.trt14.util.WinRegistry;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ooo.connector.BootstrapSocketConnector;

/**
 *
 * 
 */
public class ConverterDoc {

    private static List<XDesktop> xDesktops = new ArrayList<>();

    public static String convertDocToPDF(String input, List<String> acceptedTypes) {
        return "";
        
    }

    public static String removeOriginalExtensionFromFileName(List<String> acceptedTypes, String myTemplate) {
        for (String acceptedFile : acceptedTypes) {
            myTemplate = Utils.replaceLast(myTemplate, acceptedFile, "");
        }
        return myTemplate;
    }

    public static String convertOtherFileToPDF(String absolutePath, List<String> acceptedTypesOthersThanOffice) {
        String workingDir = Utils.extractFolder(absolutePath);
        String myTemplate = Utils.getFileName(absolutePath);

        String removeOriginalExtensionFromFileName = workingDir + removeOriginalExtensionFromFileName(acceptedTypesOthersThanOffice, myTemplate) + Constant.CONVERT_FILE;

        removeOriginalExtensionFromFileName = Archive.askIfExists(removeOriginalExtensionFromFileName);

        String fileTemp = Constant.PATH_BASE_FILE + System.nanoTime() + Constant.CONVERT_FILE;

        int border = 40;

        try {
            Image image = Image.getInstance(absolutePath);
            Rectangle rectangle = new Rectangle(0, 0, image.getWidth() + border, image.getHeight() + border);
            Document document = new Document(rectangle, border / 2, border / 2, border / 2, border / 2);
            PdfWriter.getInstance(document, new FileOutputStream(fileTemp));
            document.open();
            document.add(image);
            document.close();
            Utils.juntarArquivos(Arrays.asList(new String[]{fileTemp}), removeOriginalExtensionFromFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return removeOriginalExtensionFromFileName;
    }
}
