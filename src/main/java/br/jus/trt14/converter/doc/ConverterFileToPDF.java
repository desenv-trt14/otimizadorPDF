/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.doc;

import br.jus.trt14.tools.Archive;
import br.jus.trt14.tools.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author dfs15
 */
public class ConverterFileToPDF {

    public static void main(String[] args) {
        // Utils.convertToPDF("C:\\Users\\dfs15\\Desktop\\abc.odt");
        File file = new File("C:\\Users\\dfs15\\Desktop\\docx\\");
        File[] list = file.listFiles();

        for (File string : list) {
            ConverterFileToPDF.convertFile(string.getAbsolutePath());
        }
    }

    public static String convertFile(String originalName) {
        System.out.println(originalName);
        String toLowerCase = originalName.toLowerCase();
        String removeExtension = Archive.removeExtension(originalName);

        String dest = originalName;

        boolean pdfa = Archive.isPDFA(originalName);

        if (!pdfa) {
            dest = Archive.askIfExists(removeExtension + ".pdf");
        }

        List<IConverter> conv = new ArrayList<>();
        conv.add(new ConvertOfficeDocumentWithOfficeSuite());

        conv.add(new ConvertDocxToPDF());
        conv.add(new ConvertOdtToPDF());
        conv.add(new ConvertImageToPDF());

        boolean convertido = false;

        for (IConverter iConverter : conv) {
            convertido = iConverter.convert(originalName, dest);
            if (convertido) {
                break;
            }
        }

        if (toLowerCase.endsWith(".pdf") || convertido) {
            convertido = true;
        } else {
            return "";
        }
        if (convertido) {
            String temp = Utils.getRandomFileName();
            String origin = dest;
            if (toLowerCase.endsWith(".pdf")) {
                origin = originalName;
            }
            if (!pdfa) {
                Archive.copy(new File(origin), new File(temp));
                Utils.juntarArquivos(Arrays.asList(new String[]{temp}), dest);
            }

        }

        return convertido ? dest : "";
    }

}
