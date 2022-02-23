/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.util;

import br.jus.trt14.gui.dialogs.InstrucaoImportacaoCertificado;
import com.sun.star.uno.XComponentContext;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ooo.connector.BootstrapSocketConnector;

public class Test {

    public static void main(String[] args) {
        InstrucaoImportacaoCertificado instrucaoImportacaoCertificado = new InstrucaoImportacaoCertificado();
        instrucaoImportacaoCertificado.setModal(true);
        System.out.println("OK");
    }
}
