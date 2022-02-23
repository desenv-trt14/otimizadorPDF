/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;


/**
 *
 * @author 
 */
public class AtualizacaoModel {

    

   private String mensagem;
   private String url;
   private String dtInsercao;
   private Integer cdRevisao;
   

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

   
    public String getCabecalho() {
        return String.format("%s - Rev.: %d \n", dtInsercao, cdRevisao);
    }

    public String getMensagem() {
        return mensagem;
    }

    /**
     * @return the dtInsercao
     */
    public String getDtInsercao() {
        return dtInsercao;
    }

    /**
     * @param dtInsercao the dtInsercao to set
     */
    public void setDtInsercao(String dtInsercao) {
        this.dtInsercao = dtInsercao;
    }

    /**
     * @return the cdRevisao
     */
    public Integer getCdRevisao() {
        return cdRevisao;
    }

    /**
     * @param cdRevisao the cdRevisao to set
     */
    public void setCdRevisao(Integer cdRevisao) {
        this.cdRevisao = cdRevisao;
    }
    
    @Override
    public String toString() {
        return "AtualizacaoModel{" + "mensagem=" + mensagem + ", url=" + url + ", dtInsercao=" + dtInsercao + ", cdRevisao=" + cdRevisao + '}';
    }

}