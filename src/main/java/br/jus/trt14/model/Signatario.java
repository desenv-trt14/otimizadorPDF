/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.model;

import java.security.cert.X509Certificate;


/**
 *
 * @author dfs15
 */
public class Signatario {
    private String name;
    private String cpfCnpj;
    private String date;
    private X509Certificate certificate;
    private boolean valido;

    public Signatario(String name, String cpfCnpj, String date, X509Certificate certificate, boolean valido) {
        this.name = name;
        this.cpfCnpj = cpfCnpj;
        this.date = date;
        this.certificate = certificate;
        this.valido = valido;
    }

    public String getName() {
        return name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getDate() {
        return date;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public boolean isValido() {
        return valido;
    }

    public String getValido() {
        return valido? "Sim": "Não";
    }
   
    
    
}
