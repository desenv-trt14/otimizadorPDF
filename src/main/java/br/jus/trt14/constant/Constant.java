package br.jus.trt14.constant;

import br.jus.trt14.tools.Preferences;
import java.net.URI;

public class Constant {

    private static final String PATH_BASE_ROOT = System.getProperty("java.io.tmpdir") + "conversorpdf\\temp\\";
    private static final String PATH_BASE_FILE_TEMP = System.currentTimeMillis() + "\\";
    public static final String PATH_BASE_FILE = PATH_BASE_ROOT + PATH_BASE_FILE_TEMP;

    public static final String S_DEVICE = "-sDEVICE=pdfwrite";
    public static final String R = "-r1000";
    public static final String D_COMPATIBILITY_LEVEL = "";
    public static final String D_PDF_SETTINGS = "-dPDFSETTINGS=%s";
    public static final String D_NO_PAUSE = "-dNOPAUSE";
    public static final String D_QUIET = "-dSAFER -dNOOUTERSAVE -dEmbedAllFonts=true";
    public static final String D_BATCH = "-dBATCH";

    public static final String D_OVERRIDE_ICC = "-dOverrideICC";
    public static final String D_PROCESS_COLOR_MODEL = "-sProcessColorModel=DeviceGray";
    public static final String D_COLOR_CONVERSION_STRATEGY = "-sColorConversionStrategy=Gray";

    public static final String D_PDFA = "-dPDFA=" + (Preferences.isPDF1B() ? "1" : "2");
    public static final String D_PDFA_COMPATIBILITY_POLICY = "-dPDFACompatibilityPolicy=1";
    public static final String S_OUTPUT_FILE = "-sOutputFile=%s";
    public static final String D_USE_CIE_COLOR = "-dUseCIEColor";
    public static final String D_FIRST_PAGE = "-dFirstPage=%d";
    public static final String D_LAST_PAGE = "-dLastPage=%d";

    public static final String D_NO_ROTATE = "-dAutoRotatePages=/None";

    public static final String D_DOWNSAMPLE_COLOR_IMAGES = "-dDownsampleColorImages=true";
    public static final String D_DOWNSAMPLE_GRAY_IMAGES = "-dDownsampleGrayImages=true";
    public static final String D_DOWNSAMPLE_MONO_IMAGES = "-dDownsampleMonoImages=true";

    public static final String D_COLOR_IMAGE_RESOLUTION = "-dColorImageResolution=%s";
    public static final String D_GRAY_IMAGE_RESOLUTION = "-dGrayImageResolution=%s";
    public static final String D_MONO_IMAGE_RESOLUTION = "-dMonoImageResolution=%s";

    public static final String PATH_INTPUT = "-input.pdf";
    public static final String PATH_OUTPUT = "-output%d.pdf";

    public static final String D_NO_SAFER = "";

    public static final int TIMEOUT_PROCESS = 60;
    public final static String NODE_NAME = "otimizadorPDF";
    public final static String MOSTRAR_AVISO_CONTEXTO = "mostrarAvisoMenuContextoImpressora";
    public final static String GERAR_PDF_1b = "gerarPDF1B";
    
    public final static String GERAR_DOCUMENTO_PESQUISAVEL = "gerarDocumentoPesquisavel";

    public static final int MAX_SIZE_PJE = 3145728;
    public static final int MAX_SIZE_PROAD = 5242880;
    public final static String PDF_SIGNED = ".assinado.pdf";
    public final static Integer OTIMIZAR_SEM_DIVIDIR = 999;
    public final static String URL_VERSAO = "";
    public final static String DELETE_FOLDER_OLDER_THAN_X_DAYS = "cmd.exe /c FORFILES /P " + PATH_BASE_ROOT + " /S /D -2 /C \"cmd /c IF @isdir == TRUE rd /S /Q @path\"";
    public final static String CONVERT_FILE = ".pdf";

    public final static String HASH_CERT = "1254108d109628abdb10e3f901323419a59e1d3bce9a8baf770c9df53342d6249ea0404cdf45ed5a0412299e146ef10c34e63afcba20aaf1022cc6531f6d7921";
    public final static String TAMANHO_ARQUIVO = "tamanhoArquivo";
    public final static String TAMANHO_ARQUIVO_DEFINIDO = "tamanhoArquivoDefinido";
    public final static String MENSAGEM_ASSINATURA_DIGITAL = "mensagemAssinaturaDigital";
    public final static String URL_BASE_ASSINADOR_PREF = "urlBaseAssinador";
  //  public final static String URL_BASE_ASSINADOR_HTTP = "http://wildfly.trt14.jus.br:8089/";
    public final static String URL_BASE_ASSINADOR_HTTP = "";
    public final static String MY_DIR = System.getProperty("user.home") + "\\Otimizador PDF\\";
    
    
   

}
