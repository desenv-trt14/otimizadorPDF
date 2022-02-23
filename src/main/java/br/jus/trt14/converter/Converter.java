package br.jus.trt14.converter;

import br.jus.trt14.constant.Constant;
import br.jus.trt14.gui.AssinarLote;
import br.jus.trt14.gui.Otimizar;
import br.jus.trt14.model.DefaultOptionPane;
import br.jus.trt14.model.OptionPane;
import br.jus.trt14.tools.Archive;
import br.jus.trt14.tools.Utils;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Converter {

    private String fileInput;
    private String fileOutputTemp;
    private String fileOutput;
    private String nivelCompactacao;
    private int finalidade;
    private float tamanhoArquivo;
    private int primeiraPagina;
    private int ultimaPagina;
    private boolean mostraMensageAbrirDiretorio;
    public static boolean cancelar = false;
    public static int totalArquivos = 0;
    public static int arquivoAtual = 0;
    public static boolean evoluiProgressBar = true;
    public static OptionPane optionPane = new DefaultOptionPane();

    public Converter(String fileInput, String fileOutput,
            String nivelCompactacao, Integer finalidade, float tamanhoArquivo, Integer primeiraPagina, Integer ultimaPagina, Boolean mensagemFinal) {
        super();

        this.fileInput = fileInput;
        String output = Utils.escapeString(fileOutput);
        output = Utils.replaceLast(output, ".pdf", "_%d" + Otimizar.sufixo + ".pdf");
        this.fileOutput = output;
        output = output.substring(output.lastIndexOf("\\") + 1);

        this.fileOutputTemp = Constant.PATH_BASE_FILE + output;
        this.nivelCompactacao = nivelCompactacao;
        this.finalidade = finalidade;
        this.tamanhoArquivo = tamanhoArquivo;
        this.primeiraPagina = primeiraPagina;
        this.ultimaPagina = ultimaPagina;
        this.mostraMensageAbrirDiretorio = mensagemFinal;
    }

    public boolean converter(JProgressBar jPBProgresso, JLabel jTempoEstimado, JButton jBConverter, JButton jBCancelar) {
        String sOUTPUT_FILE = String.format(Constant.S_OUTPUT_FILE, this.fileOutputTemp);
        jBCancelar.setVisible(true);
        jBConverter.setEnabled(false);
        int qtdeArquivosDivididos = 0;
        try {
            int count = this.ultimaPagina - this.primeiraPagina + 1;

            //Inicializa a barra de progresso
            jPBProgresso.setMinimum(1);
            jPBProgresso.setMaximum(count + 1);
            jPBProgresso.setValue(1);

            long tempoTotal = 0;
            long inicio = System.currentTimeMillis();
            if (finalidade == Constant.OTIMIZAR_SEM_DIVIDIR) {
                qtdeArquivosDivididos = otimizarSemDividir(tempoTotal, count, jPBProgresso, jTempoEstimado, qtdeArquivosDivididos);
            } else {
                qtdeArquivosDivididos = otimizarDividindo(tempoTotal, count, jPBProgresso, jTempoEstimado, qtdeArquivosDivididos);
            }
            long fim = System.currentTimeMillis();

            System.out.println("Tempo total da conversão: " + (fim - inicio) / 1000);
            jPBProgresso.setValue(jPBProgresso.getMaximum()); //último % do Progress bar

            if (cancelar) {
                optionPane.showMessageDialog(null, "Otimização cancelada!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                cancelar = false;
            } else {

                long tamanhoArquivoEntrada = Utils.getTamanhoArquivo(fileInput);
                long tamanhoArquivoSaida = 0;

                for (int j = 1; j <= qtdeArquivosDivididos; j++) {
                    String file = String.format(fileOutput, qtdeArquivosDivididos);
                    tamanhoArquivoSaida += Utils.getTamanhoArquivo(file);
                }

                double razaoConversao = (1 - ((double) tamanhoArquivoSaida / (double) tamanhoArquivoEntrada)) * 100;

                long razaoConversaoTexto = (long) razaoConversao;

                arquivoAtual++;
                if (!mostraMensageAbrirDiretorio) {
                    jTempoEstimado.setText(arquivoAtual + "/" + totalArquivos);
                }

                if (mostraMensageAbrirDiretorio) {
                    Utils.cleanTempFolder();
                    if (!Otimizar.assinarDigitalmente) {
                        int showConfirmDialog = optionPane.showConfirmDialog(null, "Conversão concluída com sucesso! \n " + (qtdeArquivosDivididos) + " arquivos gerados\nA otimização do arquivo resultou em " + razaoConversaoTexto + "% de compactação.\nDeseja abri-los? ", "Sucesso!", JOptionPane.OK_CANCEL_OPTION);
                        String dirFinal = Utils.extractFolder(fileOutput);
                        if (showConfirmDialog == JOptionPane.YES_OPTION) {
                            Utils.openFolder(dirFinal);
                        }
                    }

                } else if (arquivoAtual == totalArquivos) {
                    Utils.cleanTempFolder();
                    optionPane.showMessageDialog(null, "Conversão concluída com sucesso!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            jBConverter.setEnabled(true);
            jBCancelar.setVisible(false);
            //Limpa todos os arquivos temporarios convertidos anteriormente

        } catch (Exception e) {
            optionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private int otimizarDividindo(final long tempoTotal, final int count, final JProgressBar jPBProgresso, final JLabel jTempoEstimado, int qtdeArquivosDivididos) throws IOException {
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();

        int availableProcessors = Math.min(Runtime.getRuntime().availableProcessors(), (int) (memorySize / (1024 * 1024 * 1024)) * 2);

        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);

        for (int i = this.primeiraPagina; i <= this.ultimaPagina; i++) {
            if (cancelar) {
                break;
            }
            final int k = i;

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    converterPDFFile(tempoTotal, k, k, count, jPBProgresso, jTempoEstimado);
                    System.out.println("Terminei a execução dessa thread  converterPDFFile(tempoTotal, k, k, count, jPBProgresso, jTempoEstimado);");
                }
            });

            System.out.println("executorService.execute terminou");

        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.MINUTES);
            executorService.shutdownNow();

        } catch (InterruptedException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }

        int tamanhoMaximo = 0;
        int i_filePage = this.primeiraPagina;

        switch (finalidade) {
            case 0:
                tamanhoMaximo = Constant.MAX_SIZE_PJE;
                break;
            case 1:
                tamanhoMaximo = Constant.MAX_SIZE_PROAD;
                break;
            case 2:
                tamanhoMaximo = (int) ((tamanhoArquivo * 1024) * 1024);
                break;
            default:
                tamanhoMaximo = Integer.MAX_VALUE;
                break;

        }

        boolean temMaisArquivo = true;
        List<List<File>> listaDeListaDeArquivo = new ArrayList<List<File>>();
        while (temMaisArquivo) {
            List<File> listaArquivos = new ArrayList<File>();
            long tamanho = 0;
            while (true) {
                File file2 = new File(String.format(fileOutputTemp, i_filePage++));
                if (!file2.exists()) {
                    temMaisArquivo = false;
                    break;
                }
                temMaisArquivo = true;
                tamanho += file2.length();
                if (tamanho > tamanhoMaximo
                        && !listaArquivos.isEmpty()) {
                    i_filePage--;
                    break;
                }
                listaArquivos.add(file2);
            }
            listaDeListaDeArquivo.add(listaArquivos);
        }
        List<String> lista = new ArrayList<String>();
        for (List<File> list : listaDeListaDeArquivo) {
            //System.out.println("Lista");
            List<String> pdfs = new ArrayList<String>();
            for (File file2 : list) {
                pdfs.add(file2.getCanonicalPath());
                //System.out.println(file2);
            }
            String baseArquivoSaida = String.format(fileOutputTemp, 9999 + qtdeArquivosDivididos++);
            juntarPDFs(pdfs, baseArquivoSaida, nivelCompactacao, false);

            File file3 = new File(baseArquivoSaida);
            File saida = new File(String.format(fileOutput, qtdeArquivosDivididos));

            Archive.copy(file3, saida);
            lista.add(saida.getAbsolutePath());

        }

        assinarSaida(lista);

        return qtdeArquivosDivididos;
    }

    private int otimizarSemDividir(long tempoTotal, int count, JProgressBar jPBProgresso, JLabel jTempoEstimado, int qtdeArquivosDivididos) {
        jTempoEstimado.setText("");
        progressBarOtimizarSemDividir(jPBProgresso);
        converterPDFFile(tempoTotal, primeiraPagina, ultimaPagina, count, jPBProgresso, jTempoEstimado);

        evoluiProgressBar = false;

        File file2 = new File(String.format(fileOutputTemp, primeiraPagina));
        File saida = new File(String.format(fileOutput, 1));
        Archive.copy(file2, saida);

        List<String> lista = new ArrayList<String>();
        lista.add(saida.getAbsolutePath());

        assinarSaida(lista);

        jPBProgresso.setValue(jPBProgresso.getValue() + 5);

        return 1;

    }

    public void assinarSaida(List<String> lista) {
        if (Otimizar.assinarDigitalmente) {
            AssinarLote assinarLote = new AssinarLote();
            assinarLote.setMostrarPrincipal(false);
            assinarLote.iniciarArgumentos(lista);
            assinarLote.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AssinarLote.finished = true;
                }
            });

            while (!AssinarLote.finished) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            AssinarLote.finished = false;
            assinarLote.dispose();
        }
    }

    private void progressBarOtimizarSemDividir(JProgressBar jPBProgresso) {
        evoluiProgressBar = true;
        final JProgressBar jpbthread = jPBProgresso;
        Thread r1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (evoluiProgressBar && i < ultimaPagina - 5) {
                    try {
                        jpbthread.setValue(i++);
                        Thread.sleep(200);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(Converter.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        r1.start();
    }

    public String getFileOutput() {
        return fileOutputTemp;
    }

    private void converterPDF(String input, String output, int firstPage, int lastPage, String compactacao) {
        try {

            List<Integer> paginas = new ArrayList<>();
            for (int i = firstPage; i <= lastPage; i++) {
                paginas.add(i);
            }

            PdfReader reader = new PdfReader(input);

            reader.selectPages(paginas);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output));
            stamper.close();
            reader.close();

            System.out.println(input);
            //Archive.copy(new File(input), new File(file1));
            System.out.println(output);
            //get Ghostscript instance
            // String file2 = (Constant.PATH_BASE_FILE + System.nanoTime() + ".pdf");

            //Archive.copy(new File(input), new File(file1));
            //prepare Ghostscript interpreter parameters
            //refer to Ghostscript documentation for parameter usage
            /*  List<String> listaParametros = new ArrayList<String>();
                //listaParametros.add("-dNOSAFER");

                listaParametros.add(Constant.D_BATCH);
                listaParametros.add(Constant.D_NO_PAUSE);
                listaParametros.add(Constant.S_DEVICE);
                listaParametros.add(Constant.D_QUIET);
                listaParametros.add(Constant.D_PDFA);
                listaParametros.add(Constant.D_NO_ROTATE);
                listaParametros.add(String.format(Constant.S_OUTPUT_FILE, output));
                listaParametros.add(Constant.D_DOWNSAMPLE_COLOR_IMAGES);
                listaParametros.add(Constant.D_DOWNSAMPLE_GRAY_IMAGES);
                listaParametros.add(Constant.D_DOWNSAMPLE_MONO_IMAGES);
                listaParametros.add(Constant.D_PDFA_COMPATIBILITY_POLICY);
                listaParametros.add(String.format(Constant.D_COLOR_IMAGE_RESOLUTION, compactacao.toString()));
                listaParametros.add(String.format(Constant.D_MONO_IMAGE_RESOLUTION, compactacao.toString()));
                listaParametros.add(String.format(Constant.D_GRAY_IMAGE_RESOLUTION, compactacao.toString()));
                listaParametros.add(String.format(Constant.D_FIRST_PAGE, firstPage));
                listaParametros.add(String.format(Constant.D_LAST_PAGE, lastPage));

                listaParametros.add(input);
                System.out.println(listaParametros);

                //execute and exit interpreter
                String[] str = listaParametros.toArray(new String[listaParametros.size()]);
                Utils.gs.initialize(str);
                Utils.gs.exit();
                System.gc();
                //Archive.copy(new File(file2), new File(output));*/
            //     }
        } catch (Exception e) {
            e.printStackTrace();
            optionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void juntarPDFs(List<String> input, String output, String compactacao, Boolean copyFiles) {
        //get Ghostscript instance
        try {
            List<String> filesTemp = new ArrayList<String>();
            if (copyFiles) {
                for (String f : input) {
                    String file1 = (Constant.PATH_BASE_FILE + System.nanoTime() + ".pdf");
                    Archive.copy(new File(f), new File(file1));
                    filesTemp.add(file1);
                }
            } else {
                filesTemp = input;
            }

            String file2 = (Constant.PATH_BASE_FILE + System.nanoTime() + ".pdf");

            //prepare Ghostscript interpreter parameters
            //refer to Ghostscript documentation for parameter usage
            List<String> listaParametros = new ArrayList<String>();
            //listaParametros.add("-dNOSAFER");
            listaParametros.add(Constant.D_BATCH);
            listaParametros.add(Constant.D_NO_PAUSE);
            listaParametros.add(Constant.S_DEVICE);
            listaParametros.add(Constant.D_QUIET);
            listaParametros.add(Constant.D_PDFA);
            listaParametros.add(Constant.D_NO_ROTATE);
            listaParametros.add(String.format(Constant.S_OUTPUT_FILE, file2));
            listaParametros.add(Constant.D_DOWNSAMPLE_COLOR_IMAGES);
            listaParametros.add(Constant.D_DOWNSAMPLE_GRAY_IMAGES);
            listaParametros.add(Constant.D_DOWNSAMPLE_MONO_IMAGES);
            listaParametros.add(Constant.D_PDFA_COMPATIBILITY_POLICY);
            listaParametros.add(String.format(Constant.D_COLOR_IMAGE_RESOLUTION, compactacao.toString()));
            listaParametros.add(String.format(Constant.D_MONO_IMAGE_RESOLUTION, compactacao.toString()));
            listaParametros.add(String.format(Constant.D_GRAY_IMAGE_RESOLUTION, compactacao.toString()));
            listaParametros.add("-c");
            listaParametros.add(".setpdfwrite");
            listaParametros.add("-f");
            for (String file : filesTemp) {
                listaParametros.add(file);
            }

            //execute and exit interpreter
            String[] str = listaParametros.toArray(new String[listaParametros.size()]);
            Utils.gs.initialize(str);
            Utils.gs.exit();
            System.gc();
            Archive.copy(new File(file2), new File(output));

        } catch (Exception e) {
            e.printStackTrace();
            optionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void converterPDFFile(long tempoTotal, Integer pagI, Integer pagF, Integer count, JProgressBar jPBProgresso, JLabel jTempoEstimado) {
        //System.out.println(comando);
        long inicio = System.currentTimeMillis();
        converterPDF(fileInput, String.format(fileOutputTemp, pagI), pagI, pagF, nivelCompactacao);

        long fim = System.currentTimeMillis();

        tempoTotal += fim - inicio;
        long tempoMedio = (tempoTotal / pagI);

        long tempoEstimado = (tempoMedio * (count - pagI)) / 1000;

        if (mostraMensageAbrirDiretorio) {
            jTempoEstimado.setText(tempoEstimado + " s");
        } else {
            jTempoEstimado.setText(arquivoAtual + "/" + totalArquivos);
        }

        //System.out.println("pagina");
        //Incrementa a barra de progresso
        jPBProgresso.setValue(pagI);

        System.out.println("converterPDFFile " + fileInput);
    }
}
