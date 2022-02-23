/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.tools;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.converter.Converter;
import br.jus.trt14.converter.doc.ConverterFileToPDF;
import br.jus.trt14.gui.FrameBasico;
import br.jus.trt14.gui.Otimizar;
import br.jus.trt14.gui.Principal;
import br.jus.trt14.gui.dialogs.Atualizacao;
import br.jus.trt14.gui.dialogs.DialogChooseCertificate;
import br.jus.trt14.model.FileConverted;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.security.CodeSource;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.ghost4j.Ghostscript;
import org.ghost4j.GhostscriptException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author 
 */
public class Utils {

    private static NodeList elementsByTagName;
    private final static List<AtualizacaoModel> listaAtualizacaoModels = new ArrayList<AtualizacaoModel>();
    public static JDialog dialog;
    public static Ghostscript gs = Ghostscript.getInstance();

    static {
        createTempDirectory();
        PdfReader.unethicalreading = true;
        new Thread() {
            @Override
            public void run() {
                Utils.comparaVersao();
            }
        }.start();
    }

    public synchronized static void init() {
        try {

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };

            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();

            }

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Integer versaoAtual = Versao.getVersion();
            Document parse = db.parse(Constant.URL_VERSAO + versaoAtual);
            Element documentElement = parse.getDocumentElement();
            elementsByTagName = documentElement.getElementsByTagName("programa");

            AtualizacaoModel am;
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                am = new AtualizacaoModel();
                NodeList childNodes = elementsByTagName.item(i).getChildNodes();
                String versao = childNodes.item(0).getFirstChild().getNodeValue();
                String data = childNodes.item(1).getFirstChild().getNodeValue();
                String mensagem = childNodes.item(2).getFirstChild().getNodeValue();
                String url = childNodes.item(3).getFirstChild().getNodeValue();
                am.setUrl(url);
                am.setCdRevisao(Integer.valueOf(versao));
                am.setDtInsercao(data);
                am.setMensagem(String.format("%s \n", mensagem));
                listaAtualizacaoModels.add(am);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Integer getUltimaRevisao() {
        NodeList childNodes = elementsByTagName.item(0).getChildNodes();
        Integer versao = Integer.valueOf(childNodes.item(0).getFirstChild().getNodeValue());

        return versao;
    }

    public synchronized static List<AtualizacaoModel> getDetalhesUltimasRevisoes() {
        return listaAtualizacaoModels;
    }

    public static AtualizacaoModel getLinkAtualizacao() {
        return listaAtualizacaoModels.get(0);
    }

    public static void comparaVersao() {
        new Thread() {

            @Override
            public void run() {
                init();
                System.out.println(listaAtualizacaoModels.get(0));
                if (Versao.getVersion() < Utils.getUltimaRevisao()) {
                    Atualizacao atualizacao = new Atualizacao();
                    atualizacao.setModal(true);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    atualizacao.setVisible(true);

                }
            }

        }.start();
        System.out.println("Fora da thread" + System.currentTimeMillis());

    }

    /**
     *
     * @param uri URL da
     * atualizaÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â§ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â£o
     * do programa
     */
    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String removeMetadataFile(String fileName) {
        String output = "";
        try {
            output = String.valueOf(System.nanoTime());
            output = Constant.PATH_BASE_FILE + output + "_.pdf";

            PdfReader pdfReader = new PdfReader(fileName);

            HashMap info = pdfReader.getInfo();
            Iterator entries = info.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();
                Object key = thisEntry.getKey();
                Object value = thisEntry.getValue();
                System.out.println(key + " " + value);
                info.put(key, null);
            }
            PdfStamper stamper = null;
            try {
                stamper = new PdfStamper(pdfReader, new FileOutputStream(output));
            } catch (com.itextpdf.text.DocumentException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }

            stamper.setMoreInfo(info);
            stamper.close();
            pdfReader.close();

        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static int getPageCount(String fileInput) {
        int count = 0;
        try {
            PdfReader pdfReader = new PdfReader(fileInput);
            count = pdfReader.getNumberOfPages();
            pdfReader.close();
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    /**
     * Remove a ultima ocorrÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Âªncia de
     * sequÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Âªncia dentro de uma string,
     * ÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Âºtil para remover .pdf dos
     * documentos
     *
     * @param string string a ser procurada
     * @param substring string a ser substituida
     * @param replacement valor substituto do @param substring
     * @return
     */
    public static String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1) {
            return string;
        }
        return string.substring(0, index) + replacement
                + string.substring(index + substring.length());
    }

    public static void cleanTempFolder() {
        File pastaTemp = new File(Constant.PATH_BASE_FILE);
        if (pastaTemp.exists()) {
            try {
                Runtime.getRuntime().exec("cmd.exe /c del /q " + Constant.PATH_BASE_FILE).waitFor();
            } catch (Exception ex) {
                Logger.getLogger(Otimizar.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void cleanTempFolderOlderThanXDays() {
        try {
            Runtime.getRuntime().exec(Constant.DELETE_FOLDER_OLDER_THAN_X_DAYS).waitFor();
        } catch (Exception ex) {
            Logger.getLogger(Otimizar.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized static FileConverted extractPage(String input, int firstPage, int lastPage, String compactacao) throws IllegalAccessException {
        if (lastPage > getPageCount(input)) {
            throw new IllegalAccessException("PÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¡gina maior do que a quantidade de pÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¡ginas");
        }
        FileConverted fileConverted = null;
        try {
            //get Ghostscript instance
            String file1 = (Constant.PATH_BASE_FILE + System.nanoTime() + ".pdf");
            String file2 = (Constant.PATH_BASE_FILE + System.nanoTime() + ".pdf");

            Archive.copy(new File(input), new File(file1));

            Ghostscript gs = Ghostscript.getInstance();

            //prepare Ghostscript interpreter parameters
            //refer to Ghostscript documentation for parameter usage
            List<String> listaParametros = new ArrayList<String>();
            //listaParametros.add("-dNOSAFER");
            listaParametros.add(Constant.D_BATCH);
            listaParametros.add(Constant.D_NO_PAUSE);
            listaParametros.add(Constant.S_DEVICE);
            listaParametros.add(Constant.D_QUIET);

            if (compactacao != null) {
                listaParametros.add(Constant.D_DOWNSAMPLE_COLOR_IMAGES);
                listaParametros.add(Constant.D_DOWNSAMPLE_GRAY_IMAGES);
                listaParametros.add(Constant.D_DOWNSAMPLE_MONO_IMAGES);
                listaParametros.add(String.format(Constant.D_COLOR_IMAGE_RESOLUTION, compactacao.toString()));
                listaParametros.add(String.format(Constant.D_MONO_IMAGE_RESOLUTION, compactacao.toString()));
                listaParametros.add(String.format(Constant.D_GRAY_IMAGE_RESOLUTION, compactacao.toString()));
            }

            listaParametros.add(String.format(Constant.S_OUTPUT_FILE, file2));
            listaParametros.add(String.format(Constant.D_FIRST_PAGE, firstPage));
            listaParametros.add(String.format(Constant.D_LAST_PAGE, lastPage));
            listaParametros.add(file1);

            //execute and exit interpreter
            String[] str = listaParametros.toArray(new String[listaParametros.size()]);
            gs.initialize(str);
            gs.exit();

            //System.out.println(file2);
            long retorno = new File(file2).length();

            fileConverted = new FileConverted(file2, retorno);
            if (fileConverted.getSize() < 10000) {
                System.out.println(input + " " + fileConverted.getName() + " " + firstPage + " " + lastPage + " " + compactacao);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileConverted;

    }

    public static int getQuantidadePaginasAleatorios(int quantidadePaginas) {
        String toBinaryString = Integer.toBinaryString(quantidadePaginas);
        return toBinaryString.length();
    }

    public static Set<Integer> getPaginasAleatorias(String arquivo) {
        int quantidadePaginas = getPageCount(arquivo);
        int quantidadeNumerosAleatorios = getQuantidadePaginasAleatorios(quantidadePaginas);

        Random randomizaPaginas = new Random();

        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < quantidadeNumerosAleatorios) {
            set.add(randomizaPaginas.nextInt(quantidadePaginas) + 1);
        }

        return set;
    }

    public static int getResolucaoAdequada(String arquivoOriginal, JProgressBar barraProgresso) {
        Set<Integer> set = getPaginasAleatorias(arquivoOriginal);
        barraProgresso.setMinimum(0);
        barraProgresso.setMaximum(set.size());
        int resolucaoTempMedia = 0;

        int i = 1;
        for (Integer pag : set) {
            FileConverted extractPage = null;
            try {
                extractPage = Utils.extractPage(arquivoOriginal, pag, pag, null);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }

            int resolucaoMenor = 75, resolucaoMaior = 600, resolucaoTemp = 0;
            long inicio = System.currentTimeMillis();
            while (Math.abs(resolucaoMenor - resolucaoMaior) > 1) {
                resolucaoTemp = (resolucaoMenor + resolucaoMaior) / 2;
                if (tamanhoDoArquivoEhMaior(extractPage.getName(), resolucaoTemp, pag)) {
                    resolucaoMaior = resolucaoTemp;
                } else {
                    resolucaoMenor = resolucaoTemp;
                }
            }
            resolucaoTempMedia += resolucaoTemp;
            barraProgresso.setValue(i);
            i++;
            long fim = System.currentTimeMillis();
            // System.out.println("Pagina selecionada para verificar dpi: " + pag + " resolucao " + resolucaoTemp);
        }

        resolucaoTempMedia = resolucaoTempMedia / set.size();

        return resolucaoTempMedia;
    }

    private static boolean tamanhoDoArquivoEhMaior(String arquivo, int resolucaoTemp, int pagina) {
        boolean r = false;

        FileConverted converted = null;
        try {
            converted = Utils.extractPage(arquivo, 1, 1, String.valueOf(resolucaoTemp));
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (converted.getSize() * 1.05 > getTamanhoArquivo(arquivo)) {
            r = true;
        }
        return r;
    }

    public static long getTamanhoArquivo(String arquivo) {
        return new File(arquivo).length();
    }

    public static String getRandomFileName() {
        return (Constant.PATH_BASE_FILE + System.nanoTime() + ".pdf");
    }

    public static String retiraCaracteresEspeciais(String stringFonte) {
        String passa = stringFonte;
        passa = Normalizer.normalize(passa, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return passa;
    }

    public static String getLocationApp() {
        String retorno = "";
        try {
            CodeSource codeSource = Principal.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
        } catch (Exception e) {
        }
        return retorno;
    }

    public static String escapeString(String replaceLast) {
        return replaceLast.replace("%", "%%");
    }

    public static void juntarArquivos(List<String> arquivosEntrada, String saida) {
        try {
            //prepare Ghostscript interpreter parameters
            //refer to Ghostscript documentation for parameter usage
            List<String> listaParametros = new ArrayList<String>();
            //listaParametros.add("-dNOSAFER");
            listaParametros.add(Constant.D_BATCH);
            listaParametros.add(Constant.D_NO_PAUSE);
            listaParametros.add(Constant.D_NO_ROTATE);
            listaParametros.add(Constant.S_DEVICE);
            listaParametros.add(Constant.D_QUIET);
            listaParametros.add(Constant.D_PDFA);
            listaParametros.add(Constant.D_PDFA_COMPATIBILITY_POLICY);

            listaParametros.add(String.format(Constant.S_OUTPUT_FILE, saida));

            listaParametros.add("-f");

            for (String arquivo : arquivosEntrada) {
                listaParametros.add(arquivo);
            }

            String[] str = listaParametros.toArray(new String[listaParametros.size()]);
            gs.initialize(str);
            gs.exit();
            System.gc();
        } catch (GhostscriptException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro. Verifique se o arquivo " + saida + " não está em uso.", "Otimizador de PDF - TRT14", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            if (FrameBasico.MODO_IMPRESSAO) {
                System.exit(0);
            }
        }
    }

    public static String sanitizaEntrada(String entrada) throws HeadlessException {
        //Copia e retira os metadados
        String sanitizar = "";
        try {
            sanitizar = Utils.removeMetadataFile(entrada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sanitizar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro na remoção dos metadados do PDF.", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            sanitizar = entrada;
        }
        return sanitizar;
    }

    public static Integer mostrarMenssagem(Component comp, String msg) {
        JOptionPane pane = new JOptionPane(msg, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        dialog = pane.createDialog(comp, msg);

        dialog.setVisible(true);
        dialog.dispose();

        return (Integer) (pane.getValue() == JOptionPane.UNINITIALIZED_VALUE ? JOptionPane.NO_OPTION : pane.getValue());
    }

    public static void openFolder(String dirFinal) {
        try {
            Runtime.getRuntime().exec("explorer " + "\"" + String.format(dirFinal) + "\"");
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getDomain() {
        return System.getenv("USERDOMAIN");
    }

    public static String showCertificates(KeyStore ks, JFrame parent) throws KeyStoreException {
        fixAliases(ks);
        Enumeration<String> aliases = ks.aliases();

        List<Object[]> certificates = new ArrayList<Object[]>();
        while (aliases.hasMoreElements()) {
            String nextElement = aliases.nextElement();
            X509Certificate certificate = (X509Certificate) ks.getCertificate(nextElement);
            System.out.println(nextElement + " " + certificate.getNotBefore() + " " + certificate.getNotAfter());

            if (certificate.getNotAfter().getTime() > Calendar.getInstance().getTimeInMillis()) {
                certificates.add(new Object[]{certificate, nextElement});
            }
        }
        DialogChooseCertificate certificate = new DialogChooseCertificate(certificates);
        while (certificate.selectedAlias.isEmpty() && certificate.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return certificate.selectedAlias;
    }

    public static void detectEnterTableAction(JTable table, final JButton button) {
        String action = "Solve";
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, action);
        table.getActionMap().put(action, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
    }

    public static Object[] extractCertificateInfo(Object[] obj) {
        Object[] r = null;
        X509Certificate certificate = (X509Certificate) obj[0];
        try {
            r = new Object[]{obj[1],
                extractDateToString(certificate.getNotBefore()),
                extractDateToString(certificate.getNotAfter())};
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public static String extractDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(date);
    }

    public static String extractNameSigner(X509Certificate signer) throws CertificateParsingException, CertificateEncodingException {
        String signerName = "";
        X500Name x500name = new JcaX509CertificateHolder(signer).getSubject();
        RDN cn = x500name.getRDNs(BCStyle.CN)[0];

        signerName = IETFUtils.valueToString(cn.getFirst().getValue());

        Pattern compile = Pattern.compile("(.{1,60})?([\\W])");
        Matcher matcher = compile.matcher(signerName);
        if (matcher.find()) {
            signerName = matcher.group(1).toUpperCase();
        }
        return signerName;
    }

    public static void fixAliases(final KeyStore keyStore) {
        Field field;
        KeyStoreSpi keyStoreVeritable;
        final Set<String> tmpAliases = new HashSet<String>();
        try {
            field = keyStore.getClass().getDeclaredField("keyStoreSpi");
            field.setAccessible(true);
            keyStoreVeritable = (KeyStoreSpi) field.get(keyStore);

            if ("sun.security.mscapi.KeyStore$MY".equals(keyStoreVeritable.getClass().getName())) {
                Collection<Object> entries;
                String alias, hashCode;
                X509Certificate[] certificates;

                field = keyStoreVeritable.getClass().getEnclosingClass().getDeclaredField("entries");
                field.setAccessible(true);
                entries = (Collection<Object>) field.get(keyStoreVeritable);

                for (Object entry : entries) {
                    field = entry.getClass().getDeclaredField("certChain");
                    field.setAccessible(true);
                    certificates = (X509Certificate[]) field.get(entry);

                    hashCode = Integer.toString(certificates[0].hashCode());

                    field = entry.getClass().getDeclaredField("alias");
                    field.setAccessible(true);
                    alias = (String) field.get(entry);
                    String tmpAlias = alias;
                    int i = 0;
                    while (tmpAliases.contains(tmpAlias)) {
                        i++;
                        tmpAlias = alias + "-" + i;
                    }
                    tmpAliases.add(tmpAlias);
                    if (!alias.equals(hashCode)) {
                        field.set(entry, tmpAlias);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String extractFolder(String s) {
        int lastIndexOf = s.lastIndexOf("\\");
        return s.substring(0, lastIndexOf + 1);
    }

    public static void createTempDirectory() {
        cleanTempFolderOlderThanXDays();
        mkdir(Constant.PATH_BASE_FILE);
    }

    public static void createBKPDirectory() {
        mkdir(Constant.PATH_BASE_FILE);
    }

    public static void deleteFile(String dest) {
        new File(dest).delete();
    }

    public static void mkdir(String string) {
        try {
            new File(string).mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String fileName) {
        int lastIndexOf = fileName.lastIndexOf("\\");
        return fileName.substring(lastIndexOf + 1);
    }

    public static String convertDocXToPDF(String input) {
        String retorno = "";
        try {
            if (input.toLowerCase().endsWith(".docx")) {
                InputStream doc = new FileInputStream(new File(input));
                XWPFDocument document = new XWPFDocument(doc);
                org.apache.poi.xwpf.converter.pdf.PdfOptions options = org.apache.poi.xwpf.converter.pdf.PdfOptions.create();

                File output = new File(Utils.replaceLast(input.toLowerCase(), ".docx", "") + Constant.CONVERT_FILE);

                OutputStream out = new FileOutputStream(output);
                org.apache.poi.xwpf.converter.pdf.PdfConverter.getInstance().convert(document, out, options);
                System.out.println("Done");
                retorno = output.getAbsolutePath();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }

    public static void addFiles(File[] files, DefaultTableModel tableModel, JLabel label, List<String> acceptedTypesOffice, List<String> otherFiles) {
        label.setIcon(new javax.swing.ImageIcon(label.getClass().getResource("/images/loading_small.gif")));
        for (File f : files) {
            String convertFile = ConverterFileToPDF.convertFile(f.getAbsolutePath());
            if (!convertFile.isEmpty() && !isFilePresent(convertFile, tableModel)) {
                int pageCount = Utils.getPageCount(convertFile);
                tableModel.addRow(new Object[]{convertFile, String.valueOf(pageCount)});
            }
        }
        label.setIcon(null);
    }

    private static boolean isFilePresent(String fileName, DefaultTableModel model) {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (model.getValueAt(i, 0).equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public static String getThumbPrint(X509Certificate cert)
            throws NoSuchAlgorithmException, CertificateEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] der = cert.getEncoded();
        md.update(der);
        byte[] digest = md.digest();
        return hexify(digest);

    }

    private static String hexify(byte bytes[]) {

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            buf.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
            buf.append(hexDigits[bytes[i] & 0x0f]);
        }

        return buf.toString();
    }

    public static ImageIcon getIcon(boolean b) {
        if (b) {
            return new ImageIcon(Utils.class.getResource("/images/yes.png"));
        }
        return new ImageIcon(Utils.class.getResource("/images/no.png"));

    }

    public static String getCPFCNPJ(X509Certificate cert) {
        DERObjectIdentifier OID_PF_DADOS_TITULAR = new DERObjectIdentifier(
                "2.16.76.1.3.1");

        DERObjectIdentifier OID_PJ_DADOS_TITULAR = new DERObjectIdentifier(
                "2.16.76.1.3.3");
        String CPF = "";
        try {
            Collection<?> col = X509ExtensionUtil.getSubjectAlternativeNames(cert);
            for (Object obj : col) {
                if ((obj instanceof ArrayList)) {
                    ArrayList<?> lst = (ArrayList) obj;

                    Object value = lst.get(1);

                    if ((value instanceof DLSequence)) {
                        DLSequence seq = (DLSequence) value;

                        DERObjectIdentifier oid = (DERObjectIdentifier) seq
                                .getObjectAt(0);
                        DERTaggedObject tagged = (DERTaggedObject) seq
                                .getObjectAt(1);
                        String info = null;

                        ASN1Primitive derObj = tagged.getObject();
                        if ((derObj instanceof DEROctetString)) {
                            DEROctetString octet = (DEROctetString) derObj;
                            info = new String(octet.getOctets());
                        } else if ((derObj instanceof DERPrintableString)) {
                            DERPrintableString octet = (DERPrintableString) derObj;
                            info = new String(octet.getOctets());
                        } else if ((derObj instanceof DERUTF8String)) {
                            DERUTF8String str = (DERUTF8String) derObj;
                            info = str.getString();
                        }
                        if ((info != null) && (!info.isEmpty())
                                && (oid.equals(OID_PF_DADOS_TITULAR))) {
                            if (true) {
                                String nascimento = info.substring(0, 8);
                                System.out.println("Data Nascimento: "
                                        + new SimpleDateFormat("ddMMyyyy")
                                                .parse(nascimento));
                                String cpf = info.substring(8, 19);

                                System.out.println("CPF: " + cpf);
                                String nis = info.substring(19, 30);
                                System.out.println("NIS: " + nis);
                                String rg = info.substring(30, 45);
                                System.out.println("RG: " + rg);
                                if (!rg.equals("000000000000000")) {
                                    String ufExp = info.substring(45, 50);
                                    System.out.println("OrgÃ£o Expedidor: "
                                            + ufExp);
                                }
                            }
                            CPF = info.substring(8, 19);
                        } else if ((info != null) && (!info.isEmpty())
                                && (oid.equals(OID_PJ_DADOS_TITULAR))) {
                            CPF = info;
                        }
                    } else if (CPF == null) {
                        CPF = value.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CPF;
    }

    public static boolean confirmaAcao(String mensagem, String titulo) {
        int showConfirmDialog = Converter.optionPane.showConfirmDialog(null, mensagem, titulo, JOptionPane.YES_NO_OPTION);
        return showConfirmDialog == JOptionPane.YES_OPTION;
    }

    public static void noToolTip() {
        ToolTipManager ttm = ToolTipManager.sharedInstance();
        ttm.setInitialDelay(0);
        ttm.setDismissDelay(0);
    }

    public static void defaultToolTip() {
        ToolTipManager ttm = ToolTipManager.sharedInstance();
        ttm.setInitialDelay(0);
        ttm.setDismissDelay(4000);
    }

    public static String trataOctal(String s) {
        Pattern compile = Pattern.compile("\\\\\\d{3}");
        Matcher matcher = compile.matcher(s);
        while (matcher.find()) {
            String m = matcher.group();
            int octal = Integer.parseInt(m.replace("\\", ""), 8);
            char c = (char) octal;
            s = s.replaceAll("\\" + m, String.valueOf(c));
        }

        s = s.replaceAll("[\\\\/:\\*\\?\"<>|]", "");
        return s;
    }

    public static String extractImagePage(String absolutePath, int page) {
        String retorno = "";
        try {
            String outputFileImageOCR = Constant.PATH_BASE_FILE;
            outputFileImageOCR += getFileName(absolutePath) + String.format("OCR%d.png", page);
            gs.initialize(gerarParametrosExtracao(absolutePath, outputFileImageOCR, page));
            gs.exit();
            retorno = outputFileImageOCR;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public static String[] gerarParametrosExtracao(String inputFile, String outputFile, int page) {
        List<String> listaParametros = new ArrayList<String>();
        listaParametros.add(Constant.D_BATCH);
        listaParametros.add(Constant.D_NO_PAUSE);
        listaParametros.add("-sDEVICE=png256");
        listaParametros.add("-dFirstPage=" + page);
        listaParametros.add("-dLastPage=" + page);
        listaParametros.add("-r150");
        listaParametros.add(Constant.D_QUIET);
        listaParametros.add(String.format(Constant.S_OUTPUT_FILE, outputFile));
        listaParametros.add(inputFile);

        //execute and exit interpreter
        String[] str = listaParametros.toArray(new String[listaParametros.size()]);
        return str;
    }

    public static BufferedImage resizeImage(BufferedImage read, float widthFactor, float heightFactor) {
        int newWidth = new Double(read.getWidth() * widthFactor).intValue();
        int newHeight = new Double(read.getHeight() * heightFactor).intValue();
        BufferedImage resized = new BufferedImage(newWidth, newHeight, read.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(read, 0, 0, newWidth, newHeight, 0, 0, read.getWidth(),
                read.getHeight(), null);
        g.dispose();
        return resized;
    }

}
