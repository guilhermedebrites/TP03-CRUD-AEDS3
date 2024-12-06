package app.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Backup {

    private static final String VERMELHO = "\u001B[31m";
    private static final String RESETAR = "\u001B[0m";
    private static final String DIRETORIO_BACKUP = ".\\backups";
    private static final String DIRETORIO_DADOS = ".\\dados";

    public Backup() {
        criarDiretorio(DIRETORIO_BACKUP);
        criarDiretorio(DIRETORIO_DADOS);
    }

    public String obterDiretorioBackup() {
        return DIRETORIO_BACKUP;
    }

    public String obterDiretorioDados() {
        return DIRETORIO_DADOS;
    }

    private void criarDiretorio(String caminho) {
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    private byte[] serializarArquivos(File[] arquivos) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    dos.writeUTF(arquivo.getName());
                    byte[] conteudo = lerArquivo(arquivo);
                    dos.writeInt(conteudo.length);
                    dos.write(conteudo);
                }
            }
            bytes = baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Erro ao serializar arquivos: " + e.getMessage());
        }
        return bytes;
    }

    private byte[] lerArquivo(File arquivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(arquivo)) {
            return fis.readAllBytes();
        }
    }

    private void escreverArquivo(String caminho, byte[] conteudo) {
        try (FileOutputStream fos = new FileOutputStream(caminho)) {
            fos.write(conteudo);
        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo: " + e.getMessage());
        }
    }

    public double calcularTaxaCompressao(byte[] dadosOriginais, byte[] dadosComprimidos) {
        int tamanhoOriginal = dadosOriginais.length;
        int tamanhoComprimido = dadosComprimidos.length;
        return (1 - ((double) tamanhoComprimido / tamanhoOriginal)) * 100;
    }

    public void criarBackup(String arquivoBackup) {
        try {
            criarDiretorio(DIRETORIO_BACKUP);
            File dirDados = new File(DIRETORIO_DADOS);

            String caminhoSubdir = DIRETORIO_BACKUP + "\\" + arquivoBackup.replace(".db", "");
            criarDiretorio(caminhoSubdir);

            if (!dirDados.exists()) {
                System.err.println(VERMELHO + "Diretório de dados não encontrado." + RESETAR);
            } else {
                File[] arquivos = dirDados.listFiles();
                if (arquivos != null) {
                    byte[] dadosOriginais = serializarArquivos(arquivos);
                    byte[] dadosComprimidos = LZW.codifica(dadosOriginais);

                    double taxaCompressao = calcularTaxaCompressao(dadosOriginais, dadosComprimidos);
                    System.out.printf("Taxa de compressão: %.2f%%\n", taxaCompressao);

                    escreverArquivo(caminhoSubdir + "\\" + arquivoBackup, dadosComprimidos);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar o backup: " + e.getMessage());
        }
    }

    public void restaurarBackup(String arquivoBackup) {
        File backup = new File(DIRETORIO_BACKUP + "\\" + arquivoBackup);

        if (!backup.exists()) {
            File subdir = new File(DIRETORIO_BACKUP + "\\" + arquivoBackup.replace(".db", ""));
            backup = new File(subdir, arquivoBackup);
        }

        if (!backup.exists()) {
            System.err.println(VERMELHO + "Arquivo de backup não encontrado." + RESETAR);
        } else {
            try {
                byte[] dadosBackup = lerArquivo(backup);
                dadosBackup = LZW.decodifica(dadosBackup);

                ByteArrayInputStream bais = new ByteArrayInputStream(dadosBackup);
                DataInputStream dis = new DataInputStream(bais);
                limparDiretorio(DIRETORIO_DADOS);

                while (dis.available() > 0) {
                    String nomeArquivo = dis.readUTF();
                    int tamanhoArquivo = dis.readInt();
                    byte[] conteudoArquivo = new byte[tamanhoArquivo];
                    dis.readFully(conteudoArquivo);

                    escreverArquivo(DIRETORIO_DADOS + "\\" + nomeArquivo, conteudoArquivo);
                }

            } catch (Exception e) {
                System.err.println("Erro ao restaurar o backup: " + e.getMessage());
            }
        }
    }

    private void limparDiretorio(String caminhoDir) {
        File dir = new File(caminhoDir);
        if (dir.exists()) {
            File[] arquivos = dir.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        arquivo.delete();
                    } else if (arquivo.isDirectory()) {
                        limparDiretorio(arquivo.getPath());
                        arquivo.delete();
                    }
                }
            }
        }
    }

    public ArrayList<String> listarBackups() {
        ArrayList<String> backups = new ArrayList<>();
        File dirBackup = new File(DIRETORIO_BACKUP);
        File[] subdirs = dirBackup.listFiles(File::isDirectory);

        if (subdirs == null || subdirs.length == 0) {
            System.out.println(VERMELHO + "Nenhum backup encontrado." + RESETAR);
        } else {
            System.out.println("\nBackups disponíveis:");
            for (int i = 0; i < subdirs.length; i++) {
                System.out.println((i + 1) + ": " + subdirs[i].getName());
                backups.add(subdirs[i].getName());
            }
        }
        return backups;
    }
}
