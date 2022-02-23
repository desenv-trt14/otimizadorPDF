package br.jus.trt14.converter;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NiveisCompactacao {

    public static String EXTREMA = "150";
    public static String BOA = "250";
    public static String RAZOAVEL = "325";
    public static String MINIMA = "425";

    public static String getEnumByString(String id) {
        String retorno = MINIMA;
        Field[] declaredFields = NiveisCompactacao.class.getDeclaredFields();
        NiveisCompactacao compactacao = new NiveisCompactacao();
        for (Field declaredField : declaredFields) {
            if(declaredField.getName().toUpperCase().equals(id.toUpperCase())){
                try {
                    retorno = (String)declaredField.get(compactacao);
                } catch (Exception ex) {
                    Logger.getLogger(NiveisCompactacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return retorno;
    }

}
