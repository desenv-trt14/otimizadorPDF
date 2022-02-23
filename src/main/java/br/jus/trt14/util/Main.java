/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import br.jus.trt14.converter.NiveisCompactacao;
import br.jus.trt14.tools.Utils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * 
 */
public class Main {

    public static boolean validaRange(String rangeInput, int numeroPaginas) throws Exception {

        try {
            String[] ranges = rangeInput.split(";");

            for (String range : ranges) {
                if (range.length() > 0) {

                    if (range.contains("-")) {
                        int primeiraPagina = Integer.valueOf(range.split("-")[0]);
                        int ultimaPagina = Integer.valueOf(range.split("-")[1]);
                        if (primeiraPagina > ultimaPagina || ultimaPagina > numeroPaginas || primeiraPagina > numeroPaginas) {
                            return false;
                        }

                    } else {
                        int primeiraPagina = Integer.valueOf(range);
                        if (primeiraPagina > numeroPaginas) {
                            return false;
                        }

                    }
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(validaRange("1-4", 8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
