/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author 
 */
public class Versao {

    public String getVersaoAplicacao() {
        String versao = "";
        try {
            Properties p = new Properties();
            URL resource = getClass().getResource("/versao/parametros.properties");
            // File fileImage = new File();
            InputStream file = getClass().getResourceAsStream("/versao/parametros.properties");
            // System.out.println(resource.toURI().toString());
            p.load(file);
            versao = p.getProperty("versao").replaceAll("\\$", "");
            versao = versao.replace("Revision:", "").replace("Rev:", "");
            return versao;
        } catch (Exception e) {
        }
        return versao;
    }

    public static Integer getVersion() {
        return Integer.valueOf(new Versao().getVersaoAplicacao().trim());
    }

}
