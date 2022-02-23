/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;

import br.jus.trt14.gui.dialogs.RenameFiles;
import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class Archive {

    // Copia arquivo desejado, para o arquivo de destino
    // Se o arquivo de destino não existir, ele será criado
    public static void copy(File src, File dst) {
        try {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);           // Transferindo bytes de entrada para saída
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }

    }

    public static void rename(String src, String dst) {
        try {
            new File(src).renameTo(new File(dst));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String removeExtension(String src) {
        String retorno = "";
        try {
            retorno = src.substring(0, src.lastIndexOf("."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static String getExtension(String src) {
        String retorno = "";
        try {
            retorno = src.substring(src.length() - 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static boolean exists(String src) {
        boolean retorno = false;
        try {
            retorno = new File(src).exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static String getNewName(String src) {
        String retorno = "";
        try {
            File file = new File(Utils.extractFolder(src));
            final String removeExtension = removeExtension(src);
            File[] listFiles = file.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return pathname.getAbsolutePath().startsWith(removeExtension);
                }
            });
            retorno = removeExtension + "_" + listFiles.length + ".pdf";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static String removeFileProtocol(String output) {
        return output.replace("file:///", "").replace("//", "\\").replace("/", "\\");
    }

    public static String addFileProtocol(String output) {
        return "file:///" + output.replace("\\", "/");
    }

    public static String askIfExists(String myResult) {
        if (Archive.exists(myResult)) {
            String newName = Archive.getNewName(myResult);
            if (!RenameFiles.deAgoraEmDiante) {
                RenameFiles renameFiles2 = new RenameFiles(null, true);
                renameFiles2.setNameFile(myResult, newName);
                renameFiles2.setVisible(true);
                if (RenameFiles.acaoEscolhida == RenameFiles.RENOMEAR) {
                    myResult = newName;
                }
            } else {
                if (RenameFiles.acaoEscolhida == RenameFiles.RENOMEAR) {
                    myResult = newName;
                }
            }
        }
        return myResult;
    }

    public static boolean isPDFA(String originalName) {
        boolean retorno = false;
        PdfReader reader = null;
        try {
            if (originalName.toLowerCase().endsWith(".pdf")) {
                reader = new PdfReader(originalName);
                byte metaBytes[] = reader.getMetadata();
                String metadata = "";
                if (metaBytes != null) {
                    metadata = new String(metaBytes);
                    if (metadata.contains("pdfaid:conformance")) {
                        retorno = true;
                    }
                }
                reader.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(Archive.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }

}
