/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.gui.Otimizar;
import br.jus.trt14.model.NoMockOptionPane;
import br.jus.trt14.model.OptionPane;
import br.jus.trt14.tools.Utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.sourceforge.tess4j.Tesseract;
import org.ghost4j.Ghostscript;

/**
 *
 * @author 
 */
public class Teste {

    public static void main(String[] args) throws Exception {
        final Otimizar otimizar = new Otimizar();
        otimizar.setVisible(true);
        final JFileChooser chooser = otimizar.fc;
        otimizar.optionPane = new NoMockOptionPane();

        otimizar.fc.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                otimizar.fc.setSelectedFile(new File("testes\\Resultados\\OtimizarCaracteres_1.pdf"));
                chooser.approveSelection();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });
        JProgressBar jpb = (JProgressBar) lookupObject(otimizar,"jPBProgresso");
        


        jpb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JProgressBar jpb = (JProgressBar) e.getSource();
                if (jpb.getValue() == jpb.getMaximum()) {
                    otimizar.dispose();
                    System.out.println("CHEGOU AO FINAL");
                }
            }
        });

        clicarBotao(otimizar, "jBAbrirDocumentoPDFEntrada", false);
        
        clicarBotao(otimizar, "jBConverter", false);
    }

    private static void clicarBotao(final Object object, final String field, boolean thread) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        JButton button = (JButton) lookupObject(object, field);
        button.doClick();

    }

    private static Object lookupObject(Object object, String field) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Field declaredField = object.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }

    public static void testHOCRCreation(String imagemAtual) throws FileNotFoundException {

        String result = "";
        //File imageFile =  new File("C:\\tesseract-3.0.4\\a4.png");
        File imageFile = new File(imagemAtual);

        /**
         * JNA Interface Mapping
         *
         */
        Tesseract instance = new Tesseract();

        /**
         * You either set your own tessdata folder with your custom language
         * pack or use LoadLibs to load the default tessdata folder for you.
         *
         */
        instance.setLanguage("por");
        instance.setDatapath("C:\\tesseract-3.0.4");

        try {
            /**
             * HOCR | Set the HOCR option in order to get the desired result
             * from the doOCR method.
             *
             */
            instance.setHocr(true);
            instance.setPageSegMode(1);
            result = instance.doOCR(imageFile);
            hocr2pdf(result, new File(imagemAtual), new FileOutputStream(imagemAtual + ".pdf"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void hocr2pdf(String hocrFile, File inputFile, OutputStream outputFile) {
        try {
            // The resolution of a PDF file (using iText) is 72pt per inch
            float pointsPerInch = 72.0f;

            // Using the jericho library to parse the HTML file
            Source source = new Source(hocrFile);

            // Load the image
            Image image = Image.getInstance(inputFile.getAbsolutePath());
            float dotsPerPointX;
            float dotsPerPointY;
            if (image.getDpiX() > 0) {
                dotsPerPointX = image.getDpiX() / pointsPerInch;
                dotsPerPointY = image.getDpiY() / pointsPerInch;
            } else {
                dotsPerPointX = 1.0f;
                dotsPerPointY = 1.0f;
            }

            float pageImagePixelHeight = image.getHeight();
            Document pdfDocument = new Document(new Rectangle(image.getWidth() / dotsPerPointX, image.getHeight() / dotsPerPointY));
            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, outputFile);
            pdfDocument.open();
            // first define a standard font for our text
            Font defaultFont = FontFactory.getFont(FontFactory.TIMES, 8, Font.NORMAL, CMYKColor.BLACK);

            // Put the text behind the picture (reverse for debugging)
            PdfContentByte cb = pdfWriter.getDirectContentUnder();
            //PdfContentByte cb = pdfWriter.getDirectContent();

            image.scaleToFit(image.getWidth() / dotsPerPointX, image.getHeight() / dotsPerPointY);
            image.setAbsolutePosition(0, 0);
            // Put the image in front of the text (reverse for debugging)
            pdfWriter.getDirectContent().addImage(image);

            // In order to place text behind the recognised text snippets we are interested in the bbox property		
            Pattern bboxPattern = Pattern.compile("bbox(\\s+\\d+){4}");
            // This pattern separates the coordinates of the bbox property
            Pattern bboxCoordinatePattern = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)");
            // Only tags of the ocr_line class are interesting
            StartTag ocrLineTag = source.getNextStartTag(0, "class", "ocr_line", false);
            while (ocrLineTag != null) {
                System.out.println("Encontrei uma linha");
                Element lineElement = ocrLineTag.getElement();
                Matcher bboxMatcher = bboxPattern.matcher(lineElement.getAttributeValue("title"));
                if (bboxMatcher.find()) {
                    // We found a tag of the ocr_line class containing a bbox property
                    Matcher bboxCoordinateMatcher = bboxCoordinatePattern.matcher(bboxMatcher.group());
                    bboxCoordinateMatcher.find();
                    int[] coordinates = {Integer.parseInt((bboxCoordinateMatcher.group(1))),
                        Integer.parseInt((bboxCoordinateMatcher.group(2))),
                        Integer.parseInt((bboxCoordinateMatcher.group(3))),
                        Integer.parseInt((bboxCoordinateMatcher.group(4)))};

                    String line = lineElement.getContent().getTextExtractor().toString();
                    if (line.equals("")) {
                        ocrLineTag = source.getNextStartTag(ocrLineTag.getEnd(), "class", "ocr_line", false);
                        continue;
                    }
                    float bboxWidthPt = (coordinates[2] - coordinates[0]) / dotsPerPointX;
                    float bboxHeightPt = (coordinates[3] - coordinates[1]) / dotsPerPointY;

                    // Put the text into the PDF
                    cb.beginText();
                    // Comment the next line to debug the PDF output (visible Text)
                    cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_INVISIBLE);
                    // Scale the text width to fit the OCR bbox
                    boolean textScaled = false;
                    int attempts = 0;
                    float ratioAdjust = 0.01f;

                    do {
                        float lineWidth = defaultFont.getBaseFont().getWidthPoint(line, bboxHeightPt);
                        if (lineWidth >= bboxWidthPt) {
                            textScaled = true;
                        } else {
                            bboxHeightPt += ratioAdjust;
                        }
                        attempts++;
                        if (attempts % 20 == 0) {
                            ratioAdjust += ratioAdjust;
                        }
                        System.out.println("tentando ajustar ao texto aumentando");
                    } while (textScaled == false);
                    textScaled = false;
                    attempts = 0;
                    do {
                        float lineWidth = defaultFont.getBaseFont().getWidthPoint(line, bboxHeightPt);
                        if (lineWidth <= bboxWidthPt) {
                            textScaled = true;
                        } else {
                            bboxHeightPt -= ratioAdjust;
                        }
                        attempts++;
                        if (attempts % 20 == 0) {
                            ratioAdjust += ratioAdjust;
                        }
                        System.out.println("tentando ajustar ao texto diminuindo");
                    } while (textScaled == false);

                    //put text in the document
                    cb.setFontAndSize(defaultFont.getBaseFont(), bboxHeightPt);
                    cb.moveText((float) (coordinates[0] / dotsPerPointX), (float) ((pageImagePixelHeight - coordinates[3]) / dotsPerPointY));

                    cb.showText(line);
                    cb.endText();
                }
                ocrLineTag = source.getNextStartTag(ocrLineTag.getEnd(), "class", "ocr_line", false);
            }
            pdfDocument.close();
            pdfWriter.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
