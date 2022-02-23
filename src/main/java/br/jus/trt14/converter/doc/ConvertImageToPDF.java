/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.doc;

import br.jus.trt14.gui.FrameBasico;
import br.jus.trt14.tools.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author dfs15
 */
public class ConvertImageToPDF implements IConverter {

    @Override
    public boolean convert(String input, String output) {
        boolean retorno = false;
        String fileTemp = Utils.getRandomFileName();
        int border = 40;
        
        String extension = input.toLowerCase().substring(input.lastIndexOf("."));
        
        if (FrameBasico.allowedTypesOthersThanOffice.contains(extension)) {
            try {
                Image image = Image.getInstance(input);
                Rectangle rectangle = new Rectangle(0, 0, image.getWidth() + border, image.getHeight() + border);
                Document document = new Document(rectangle, border / 2, border / 2, border / 2, border / 2);
                PdfWriter.getInstance(document, new FileOutputStream(fileTemp));
                document.open();
                document.add(image);
                document.close();
                Utils.juntarArquivos(Arrays.asList(new String[]{fileTemp}), output);
                retorno = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Falha na conversão da imagem.\n"
                        + "Verifque se as dimensões da mesma não excedem o tamanho máximo de 14400px por 14400px");
                e.printStackTrace();
            }
        }

        return retorno;
    }

}
