/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.security;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.gui.AssinarLote;
import br.jus.trt14.gui.dialogs.ProcessandoAssinatura;
import br.jus.trt14.tools.Archive;
import br.jus.trt14.tools.Preferences;
import br.jus.trt14.tools.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import static com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode.GRAPHIC;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateUtil;
import com.itextpdf.text.pdf.security.CrlClient;
import com.itextpdf.text.pdf.security.CrlClientOnline;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.OcspClient;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEREncodableVector;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import sun.security.mscapi.SunMSCAPI;

/**
 *
 */
public class AssinaturaToken {

    private JFrame parent;
    public List<String> arquivosConvertidos = new ArrayList<>();
    private boolean isOnline;

    public AssinaturaToken(JFrame parent) {
        this.parent = parent;
    }

    public void sign(List<String[]> listaArquivos, boolean mostrarDiretorio) throws Exception {


        /*System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "3128");*/
        LoggerFactory.getInstance().setLogger(new SysoLogger());

        BouncyCastleProvider providerBC = new BouncyCastleProvider();
        Security.addProvider(providerBC);
        SunMSCAPI providerMSCAPI = new SunMSCAPI();
        Security.addProvider(providerMSCAPI);
        KeyStore ks = KeyStore.getInstance("Windows-MY");
        ks.load(null, null);

        String alias = Utils.showCertificates(ks, parent);

        if (alias.trim().length() > 0) {

            PrivateKey pk = (PrivateKey) ks.getKey(alias, null);
            Certificate[] chain = ks.getCertificateChain(alias);
            X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);

            OcspClient ocspClient = new OcspClientBouncyCastle();
            TSAClient tsaClient = null;
            for (Certificate chain1 : chain) {
                X509Certificate cert = (X509Certificate) chain1;
                String tsaUrl = CertificateUtil.getTSAURL(cert);
                if (tsaUrl != null) {
                    tsaClient = new TSAClientBouncyCastle(tsaUrl);
                    break;
                }
            }

            List<CrlClient> crlList = new ArrayList<CrlClient>();
            if (AssinarLote.verificarCertificado) {
                crlList.add(new CrlClientOnline(chain));
            }

            ProcessandoAssinatura arquivo = new ProcessandoAssinatura();
            arquivo.iniciarDialog(listaArquivos.size());
            String extractFolder = Utils.extractFolder(listaArquivos.get(0)[1]);
            String destFolder = extractFolder;
            if (AssinarLote.pastaAssinados) {
                destFolder += "assinados\\";
            }
            Utils.mkdir(destFolder);

            int k = 0;
            boolean error = false;
            for (String[] file : listaArquivos) {
                String fileName = destFolder + Utils.getFileName(file[1]);
                try {
                    sign(file[0], fileName, chain, pk, DigestAlgorithms.SHA384, providerMSCAPI.getName(), MakeSignature.CryptoStandard.CMS, generateString(certificate), "",
                            crlList, ocspClient, tsaClient, 0, certificate, AssinarLote.append);
                    arquivo.setValueProgressBar(++k);
                    arquivo.setText("Processando assinatura do arquivo " + k + ", aguarde...");

                } catch (Exception e) {
                    e.printStackTrace();
                    arquivo.dispose();
                    error = true;
                    JOptionPane.showMessageDialog(null, "Erro processo de assinatura!\nVerifique se o seu token/cartão está conectado ao computador.");
                    break;
                }
            }
            arquivo.dispose();

            if (!error) {
                if (AssinarLote.pastaAssinados) {
                    String msg = listaArquivos.size() + " arquivo(s) assinado(s) com sucesso!\nOs arquivos assinados estão na pasta \"assinados\"\nDeseja abrir a pasta \"assinados\"?";
                    boolean confirmaAcao = Utils.confirmaAcao(msg, "Atenção");
                    if (confirmaAcao) {
                        Utils.openFolder(destFolder);
                        System.out.println(destFolder);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, listaArquivos.size() + " arquivo(s) assinado(s) com sucesso!");
                }

            }

        } else {
            JOptionPane.showMessageDialog(parent, "Voce cancelou o processo de assinatura");
        }
    }

    private void sign(String src, String dest,
            Certificate[] chain, PrivateKey pk,
            String digestAlgorithm, String provider, CryptoStandard subfilter,
            String reason, String location,
            Collection<CrlClient> crlList,
            OcspClient ocspClient,
            TSAClient tsaClient,
            int estimatedSize, X509Certificate signer, boolean append)
            throws Exception {
        // Creating the reader and the stamper

        boolean primeiraAssinatura = isPrimeiraAssinatura(src);

        //dest.substring(dest.lastIndexOf("\\") + 1)
        String codigoVerificador = "";
        Date dataInsercao = null;



        byte[] readAllBytes = java.nio.file.Files.readAllBytes(Paths.get(new File(src).getAbsolutePath()));


        if (isOnline && dataInsercao != null && primeiraAssinatura) {
            readAllBytes = addImage(readAllBytes, codigoVerificador, dataInsercao.getTime());
        }

        // readAllBytes = removeStamper(readAllBytes);
        PdfReader reader = new PdfReader(readAllBytes);

        String out = dest + ".temp.pdf";
        FileOutputStream os = new FileOutputStream(out);
        try {
            PdfStamper stamper = null;
            stamper = PdfStamper.createSignature(reader, os, '\0', new File(Constant.PATH_BASE_FILE), true);

            if (!append) {
                stamper.getSignatureAppearance().setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
            }
            // Creating the appearance
            Image img = null;
            Rectangle pageSize = null;

            int recuo = 20; // fixo !
            int yy = 43; // fixo !

            if (primeiraAssinatura) {

                img = Image.getInstance(geraCarimboAssinatura(signer, codigoVerificador).toByteArray());

                float scale = 50;

                img.scalePercent(scale);

                int generatedSize = (int) (generatedSize(generateString(signer, codigoVerificador), 13)[0] * (scale / 100));
                if (AssinarLote.addCarimboTempo) {
                    for (int i = 2; i <= reader.getNumberOfPages(); i++) {
                        pageSize = reader.getPageSize(i);
                        //float absoluteX, float absoluteY
                        int position = (int) (pageSize.getWidth() - generatedSize) / 2;
                        img.setAbsolutePosition(position, 10); // fixo !
                        PdfContentByte overContent = stamper.getOverContent(i);
                        overContent.addImage(img);
                    }
                }
            }

            Calendar calendar = GregorianCalendar.getInstance();

            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();

            if (primeiraAssinatura) {
                appearance.setSignatureGraphic(img);
                appearance.setRenderingMode(GRAPHIC);
                appearance.setSignDate(calendar);
                appearance.setAcro6Layers(true);

            }
            pageSize = reader.getPageSize(1);

            int xx = (int) (pageSize.getWidth() - recuo - 1); // fixo !
            //llx, lly, urx, ury
            //  Rectangle rectangle = new Rectangle(xx, yy, (y + xx + 2), (pageSize.getHeight() + 1)); // fixo !

            Rectangle rectangle = new Rectangle(50, 10, xx, 30); // fixo !
            appearance.setReason(reason);
            appearance.setLocation(location);
            if (AssinarLote.addCarimboTempo && primeiraAssinatura) {
                int firstPage = 1;
                appearance.setVisibleSignature(rectangle, firstPage, String.valueOf(System.currentTimeMillis()));
            }
            // Creating the signature
            ExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize, subfilter);
            int pageCount = Utils.getPageCount(out);
            String folder = Utils.extractFolder(dest);
            String fileName = Utils.getFileName(dest);
            fileName = fileName.substring(0, fileName.length() - 3);
            if (!codigoVerificador.isEmpty()) {
                fileName += codigoVerificador + ".pdf";
            } else {
                fileName += "pdf";
            }

            if (pageCount > 0) {
                String odtFileOld = Utils.extractFolder(dest) + Archive.removeExtension(Utils.getFileName(dest)) + ".odt";
                String odtFileNew = Utils.extractFolder(dest) + "Assinado_" + Archive.removeExtension(Utils.getFileName(dest)) + ".odt";
                if (AssinarLote.assOdtFile && Archive.exists(odtFileOld)) {
                    Archive.rename(odtFileOld, odtFileNew);
                }
                File assinado = new File(folder + "Assinado_" + fileName);
                Archive.copy(new File(out), assinado);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
            reader.close();
            os.close();
            throw new Exception("Processo de assinatura cancelado : " + e.getMessage());
        } finally {
            reader.close();
            os.close();
            Utils.deleteFile(out);
        }
    }

    private byte[] addImage(byte[] byteIn, String codigoVerificador, long timeStamp) {
        byte[] byteOut = null;
        try {
            PdfReader reader = new PdfReader(byteIn);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);

            Image image = Image.getInstance(generateQRCodeImage(Constant.URL_BASE_ASSINADOR_HTTP + "c/" + codigoVerificador + "/t/" + timeStamp, 0, 0));
            PdfImage stream = new PdfImage(image, "", null);
            stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
            PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
            image.setDirectReference(ref.getIndirectReference());
            image.setAbsolutePosition(0, 0);
            for (int page = 1; page <= reader.getNumberOfPages(); page++) {
                PdfContentByte over = stamper.getOverContent(page);
                over.addImage(image);
            }
            stamper.close();
            reader.close();
            byteOut = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteOut;
    }

    private static byte[] generateQRCodeImage(String text, int width, int height)
            throws WriterException, IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteOut);
        return byteOut.toByteArray();

    }

    private byte[] removeStamper(byte[] src) {
        byte[] out = null;
        PdfReader reader;
        try {
            reader = new PdfReader(src);
            ByteOutputStream bos = new ByteOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            stamper.setFormFlattening(true);
            stamper.close();
            out = bos.getBytes();
        } catch (Exception ex) {
            Logger.getLogger(AssinaturaToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;

    }

    public static int[] generatedSize(String assinatura, int fontSize) throws Exception {

        int x = 10000;
        int y = 14;

        BufferedImage Originalimage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);

        Graphics g = Originalimage.getGraphics();
        Font font = new Font("Verdana", Font.PLAIN, fontSize);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        int widthPoint = fontMetrics.stringWidth(assinatura);

        return new int[]{widthPoint, fontMetrics.getHeight()};
    }

    private ByteArrayOutputStream geraCarimboAssinatura(X509Certificate signer, String codigoVerificador) throws Exception {
        int fontSize = 13;
        String assinatura = generateString(signer, codigoVerificador);

        int[] generatedSize = generatedSize(generateString(signer, codigoVerificador), fontSize);
        int x = generatedSize[0];
        int y = generatedSize[1] + 15;
        BufferedImage originalImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);

        Graphics g = originalImage.getGraphics();
        Font font = new Font("Verdana", Font.PLAIN, fontSize);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, x, y);

        g.setFont(font);
        g.setColor(Color.BLACK);

        g.drawString(assinatura, 2, 11);

        if (isOnline && !codigoVerificador.isEmpty()) {
            String validar = "Para validar leia o QRCode ou acesse " + Constant.URL_BASE_ASSINADOR_HTTP + " (código " + codigoVerificador + " )  ";
            g.drawString(validar, 2, 23);
        }

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        /* BufferedImage rotateImage = new BufferedImage(height, width, Originalimage.getType());

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                rotateImage.setRGB(j, (width - 1) - i, Originalimage.getRGB(i, j));
            }
        }*/
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //  ImageIO.write(rotateImage, "png", byteArrayOutputStream);
        ImageIO.write(originalImage, "png", byteArrayOutputStream);

        return byteArrayOutputStream;

    }

    private static String generateString(X509Certificate signer, String codigoVerificador) throws Exception {
        String extractNameSigner = Utils.extractNameSigner(signer);

        if (AssinarLote.append) {
            extractNameSigner = "      Múltiplos signatários      ";
        }
        Calendar calendar = GregorianCalendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String mensagem = Preferences.getPreferences(Constant.MENSAGEM_ASSINATURA_DIGITAL, "");
        String data = simpleDateFormat.format(calendar.getTime());
        String retorno = mensagem.replace("${USUARIO}", extractNameSigner).replace("${DATA_HORA}", data);

        return retorno + "    ";
    }

    private static String generateString(X509Certificate signer) throws Exception {
        String extractNameSigner = Utils.extractNameSigner(signer);

        if (AssinarLote.append) {
            extractNameSigner = "Múltiplos signatários";
        }
        Calendar calendar = GregorianCalendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String mensagem = Preferences.getPreferences(Constant.MENSAGEM_ASSINATURA_DIGITAL, "");
        String data = simpleDateFormat.format(calendar.getTime());
        return mensagem.replace("${USUARIO}", extractNameSigner).replace("${DATA_HORA}", data);
    }

    private boolean isPrimeiraAssinatura(String src) {
        ValidacaoDocumento validacaoDocumento = new ValidacaoDocumento(src);
        return validacaoDocumento.getSignatarios().size() == 0;
    }

    private static TimeStampResponse requestTimeStamp(String host, int porta, final byte hashSha1[]) throws Exception {
        TimeStampRequestGenerator reqgen = new TimeStampRequestGenerator();
        reqgen.setReqPolicy("1.3.6.1.4.1.14975.2.1.0");
        Random random = new Random();
        TimeStampRequest req = reqgen.generate(TSPAlgorithms.SHA1, hashSha1, BigInteger.valueOf(random.nextInt(Integer.MAX_VALUE)));
        byte request[] = req.getEncoded();

        int length = request.length + 1;
        Socket s = new Socket(host, porta);
        s.setSoTimeout(5000);

        OutputStream os = s.getOutputStream();
        os.write(new byte[]{(byte) (length >> 24), (byte) (length >> 16), (byte) (length >> 8), (byte) length});
        os.flush();
        os.write(0);
        os.flush();
        os.write(request);
        os.flush();
        DataInputStream is = new DataInputStream(s.getInputStream());
        length = is.readInt() - 1;

        if (is.readByte() == 10) {
            is.readChar();
            length -= 2;
        }

        byte response[] = new byte[length];
        is.readFully(response);
        s.close();
        TimeStampResponse timeStampResponse = new TimeStampResponse(response);
        return timeStampResponse;
    }

    /*private static CMSSignedData addTimestamp(CMSSignedData signedData, TimeStampResponse timeStampResponse) throws Exception {
        Collection<SignerInformation> ss = signedData.getSignerInfos().getSigners();
        Iterator<SignerInformation> iterator = ss.iterator();
        SignerInformation si = null;
        boolean found = false;
        while (iterator.hasNext()) {
            si = iterator.next();
            AttributeTable unsignedAttributes = si.getUnsignedAttributes();
            if (unsignedAttributes == null || unsignedAttributes.get(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14")) == null) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Timestamp error");
        }
        ss.remove(si);
        TimeStampToken tok = timeStampResponse.getTimeStampToken();

        ASN1InputStream asn1InputStream = new ASN1InputStream(tok.getEncoded());
        ASN1Primitive tstDER = asn1InputStream.readObject();
        DERSet ds = new DERSet(tstDER);
        Attribute a = new Attribute(new DERObjectIdentifier("1.2.840.113549.1.9.16.2.14"), ds);
        DEREncodableVector dv = new DEREncodableVector();
        dv.add(a);
        AttributeTable at = new AttributeTable(dv);
        si = SignerInformation.replaceUnsignedAttributes(si, at);
        ss.add(si);
        SignerInformationStore sis = new SignerInformationStore(ss);
        signedData = CMSSignedData.replaceSigners(signedData, sis);
        return signedData;
    }*/

    private String getSHA512(byte[] bytesIn) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(bytesIn);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
