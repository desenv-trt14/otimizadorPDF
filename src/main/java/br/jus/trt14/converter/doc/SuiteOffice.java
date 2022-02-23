/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.doc;

import br.jus.trt14.tools.ConverterDoc;
import br.jus.trt14.util.WinRegistry;
import com.sun.star.uno.XComponentContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import ooo.connector.BootstrapSocketConnector;

/**
 *
 * @author dfs15
 */
public class SuiteOffice {

    public static XComponentContext xContext = null;
    public static boolean suiteOfficeExists = false;

    public static void init() {
        List<String> pathsOffice = new ArrayList<>();
        pathsOffice.add("SOFTWARE\\Wow6432Node\\BrOffice\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\Wow6432Node\\BrOffice.org\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\Wow6432Node\\LibreOffice\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\Wow6432Node\\LibreOffice.org\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\Wow6432Node\\OpenOffice\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\Wow6432Node\\OpenOffice.org\\UNO\\InstallPath");

        pathsOffice.add("SOFTWARE\\BrOffice\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\BrOffice.org\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\LibreOffice\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\LibreOffice.org\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\OpenOffice\\UNO\\InstallPath");
        pathsOffice.add("SOFTWARE\\OpenOffice.org\\UNO\\InstallPath");


        for (String path : pathsOffice) {
            String value = "";
            try {
                value = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE, path, ""); //ValueName
                if (value != null) {
                    xContext = BootstrapSocketConnector.bootstrap(value);
                    suiteOfficeExists = true;
                    break;
                }
            } catch (Exception ex) {
                Logger.getLogger(ConverterDoc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!suiteOfficeExists) {
            List<String> pathsOfficeDir = new ArrayList<>();
            
            pathsOfficeDir.add("c:\\Program Files\\BrOffice 3\\program");
            pathsOfficeDir.add("c:\\Program Files\\BrOffice 4\\program");
            pathsOfficeDir.add("c:\\Program Files\\BrOffice 5\\program");
            pathsOfficeDir.add("c:\\Program Files\\BrOffice 6\\program");
            pathsOfficeDir.add("c:\\Program Files\\BrOffice\\program");
            
            pathsOfficeDir.add("c:\\Program Files\\OpenOffice 3\\program");
            pathsOfficeDir.add("c:\\Program Files\\OpenOffice 4\\program");
            pathsOfficeDir.add("c:\\Program Files\\OpenOffice 5\\program");
            pathsOfficeDir.add("c:\\Program Files\\OpenOffice 6\\program");
            pathsOfficeDir.add("c:\\Program Files\\OpenOffice\\program");
            
            pathsOfficeDir.add("c:\\Program Files\\LibreOffice 3\\program");
            pathsOfficeDir.add("c:\\Program Files\\LibreOffice 4\\program");
            pathsOfficeDir.add("c:\\Program Files\\LibreOffice 5\\program");
            pathsOfficeDir.add("c:\\Program Files\\LibreOffice 6\\program");
            pathsOfficeDir.add("c:\\Program Files\\LibreOffice\\program");
            
            for (String string : pathsOfficeDir) {
                try {
                    xContext = BootstrapSocketConnector.bootstrap(string);
                    suiteOfficeExists = true;
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(ConverterDoc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (!suiteOfficeExists) {
            JOptionPane.showMessageDialog(null, "Não consegui encontrar a suite BrOffice, LibreOffice ou OpenOffice.\n"
                    + "A partir de agora a conversão será feita internamente pelo programa\ne pode não ter a mesma qualidade das ferramentas mencionadas.\n"
                    + "Caso a qualidade deixe a desejar considere instalar uma das ferramentas citadas.");
            ConvertOfficeDocumentWithOfficeSuite.noOffice = true;
        }

    }
}
