/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jus.trt14.converter.security;

import br.jus.trt14.model.Signatario;
import br.jus.trt14.tools.Archive;
import br.jus.trt14.tools.Utils;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 */
public final class ValidacaoDocumento {

    private static KeyStore ks = null;

    static {
        try {
            BouncyCastleProvider bcp = new BouncyCastleProvider();
            Security.addProvider(bcp);
            ks = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = ValidacaoDocumento.class.getResourceAsStream("/certificados/keystore.jks");
            ks.load(resourceAsStream, "12345678".toCharArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Signatario> signatarios = new ArrayList<>();
    private final String input;

    public List<Signatario> getSignatarios() {
        return signatarios;
    }

    public ValidacaoDocumento(String input) {
        this.input = input;
        try {
            PdfReader reader = new PdfReader(input);

            pdfa = Archive.isPDFA(input);
            assinaturaFinal = reader.getCertificationLevel() == PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED;
            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                dateFormat.setTimeZone(TimeZone.getDefault());

                List<PdfPKCS7> verifySignatures = verifySignatures(reader);
                if (verifySignatures.size() > 0) {
                    assinado = true;
                }
                for (PdfPKCS7 verifySignature : verifySignatures) {
                    Certificate[] certificates = verifySignature.getSignCertificateChain();
                    X509Certificate signatario = (X509Certificate) certificates[0];

                    X509Certificate raiz = (X509Certificate) certificates[certificates.length - 1];

                    //KeyStore.getInstance("JKS", null)
                    integro = isValido((X509Certificate[]) certificates) && verifySignature.verify();

                    signatarios.add(new Signatario(Utils.extractNameSigner(signatario), Utils.getCPFCNPJ(signatario),
                            dateFormat.format(verifySignature.getSignDate().getTime()), signatario, verifySignature.verify()));

                }

                reader.close();
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(ValidacaoDocumento.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(ValidacaoDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isAssinado() {
        return assinado;
    }

    public boolean isIntegro() {
        return integro;
    }

    public boolean isPdfa() {
        return pdfa;
    }

    public void setPdfa(boolean pdfa) {
        this.pdfa = pdfa;
    }

    public boolean isResultado() {
        return pdfa && integro && assinado;
    }

    public List<PdfPKCS7> verifySignatures(PdfReader reader) throws IOException, GeneralSecurityException {
        List<PdfPKCS7> pdfs = new ArrayList<>();
        AcroFields fields = reader.getAcroFields();

        ArrayList<String> names = fields.getSignatureNames();
        for (String name : names) {
            System.out.println("===== " + name + " =====");
            pdfs.add(verifySignature(fields, name));
        }
        System.out.println();
        return pdfs;
    }

    public PdfPKCS7 verifySignature(AcroFields fields, String name) throws GeneralSecurityException, IOException {
        System.out.println("Signature covers whole document: " + fields.signatureCoversWholeDocument(name));
        System.out.println("Document revision: " + fields.getRevision(name) + " of " + fields.getTotalRevisions());
        PdfPKCS7 pkcs7 = fields.verifySignature(name);
        System.out.println("Integrity check OK? " + pkcs7.verify());
        return pkcs7;
    }

    private boolean assinado;
    private boolean integro;
    private boolean pdfa;
    private boolean assinaturaFinal;

    public boolean isAssinaturaFinal() {
        return assinaturaFinal;
    }

    public String getNomeSignatarios() {
        String retorno = "";
        retorno += signatarios.get(signatarios.size() - 1).getName() + "\n";

        return retorno;
    }

    public void removeSignature() {
        try {
            PdfReader reader = new PdfReader(input);
            String temp = Utils.getRandomFileName();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(temp));
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            Utils.juntarArquivos(Arrays.asList(new String[]{temp}), input);

        } catch (Exception ex) {
            Logger.getLogger(ValidacaoDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isValido(X509Certificate[] certChain) {
        boolean retorno = false;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            List<Certificate> certx = new ArrayList<>(certChain.length);
            for (X509Certificate c : certChain) {
                certx.add(cf.generateCertificate(new ByteArrayInputStream(c.getEncoded())));
            }
            CertPath path = cf.generateCertPath(certx);
            CertPathValidator validator = CertPathValidator.getInstance("PKIX");

            PKIXParameters params = new PKIXParameters(ks);

            params.setRevocationEnabled(false);

            PKIXCertPathValidatorResult r = (PKIXCertPathValidatorResult) validator.validate(path, params);
            retorno = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

}
