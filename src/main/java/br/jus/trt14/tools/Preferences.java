/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;

import br.jus.trt14.constant.Constant;

/**
 *
 * 
 */
public class Preferences {

    static java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node(Constant.NODE_NAME);

    public static Boolean isMostrarAvisoMenuContexto() {
        return prefs.get(Constant.MOSTRAR_AVISO_CONTEXTO, "S").equals("S");
    }

    public static Boolean isPDF1B() {
        return prefs.get(Constant.GERAR_PDF_1b, "N").equals("S");
    }
    
    public static Boolean isDocumentoPesquisavel() {
        return prefs.get(Constant.GERAR_DOCUMENTO_PESQUISAVEL, "N").equals("S");
    }

    public static Boolean isTamanhoDefinido() {
        return prefs.get(Constant.TAMANHO_ARQUIVO_DEFINIDO, "N").equals("S");
    }

    public static Float getSizeArquivo() {
        return Float.valueOf(prefs.get(Constant.TAMANHO_ARQUIVO, "10"));
    }

    public static void setPreferences(String preferences, String value) {
        prefs.put(preferences, value);
    }

    public static String getPreferences(String preferences, String value) {
        return prefs.get(preferences, "");
    }
    
     public static Boolean isMensagemAssinaturaDigitalDefinida() {
        return !prefs.get(Constant.MENSAGEM_ASSINATURA_DIGITAL, "").equals("");
    }
     
    
    public static boolean isURLBaseAssinadorDefinida() {
        return !prefs.get(Constant.URL_BASE_ASSINADOR_PREF, "").equals("");
    }

}
