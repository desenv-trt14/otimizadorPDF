/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.model;

/**
 *
 * @author 
 */
public class FileConverted {
    private String name;
    private long size;
    public FileConverted(String name, long size){
        this.name = name;
        this.size = size;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    
}
