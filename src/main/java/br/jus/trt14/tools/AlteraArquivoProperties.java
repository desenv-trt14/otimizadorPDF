/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class AlteraArquivoProperties {
    public static void main(String[] args) {
        try {
            File file1 = new File(".");
            System.out.println(file1.getAbsolutePath());
            File file = new File("src/main/resources/versao/parametros.properties");
           
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");       
            InputStream inputStream = new FileInputStream(file);
            byte b [] = new byte[1024];
            StringBuilder builder = new StringBuilder();
            while(inputStream.read(b)!=-1){
                builder.append(new String(b));
            }
            inputStream.close();
            
            String toString = builder.toString();
            
            int lastIndexOf = toString.lastIndexOf("=");
            
            String substring = toString.substring(0, lastIndexOf + 1) + dateFormat.format(new Date());          
            
            
            OutputStream outputStream = new FileOutputStream(file);
            
            outputStream.write(substring.getBytes());
            Properties p = new Properties();
            p.load(new FileInputStream(file));
            String versao = p.getProperty("versao").toString();
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(AlteraArquivoProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
