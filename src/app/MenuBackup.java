package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import app.backup.Backup;
import app.main.Principal;

public class MenuBackup extends Principal {
    private static Backup backup;

    public MenuBackup ( ) throws Exception {
        backup = new Backup( );
    }

    public static void menu() {
        try {
            int opcao = 0;
            do {
                opcoes_menu();
                opcao = ler_opcao();
                action(opcao);
            } while (opcao != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void opcoes_menu() {
        System.out.println("\nPUCTAREFA 1.0           ");
        System.out.println("-------------------------");
        System.out.println("> Início > Backups       ");
        System.out.println("1 - Fazer backup         ");
        System.out.println("2 - Restaurar backup     ");
        System.out.println("0 - Voltar               ");
        System.out.print("Opção: ");
    }

    protected static void action(int opcao) {
        switch (opcao) {
            case 0:
                break;
            case 1:
                fazerBackup();
                break;
            case 2:
                restaurarBackup();
                break;
            default:
                System.out.println("Opção incorreta!");
                break;
        }
    }

    // getCurrentTimeStamp
    public static String getDataHoraAtual( ) 
    {
        return ( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss")) );
    }

    public static void fazerBackup() {
        System.out.println("\n> Relizando Backup:");
        try {
            System.out.println("\nConfirma a realização do backup? (S/N)");
            char resp = console.nextLine().charAt(0);
            if (resp == 'S' || resp == 's') {
                backup.criarBackup(getDataHoraAtual() + ".db");
                System.out.println("Backup realizado com sucesso.");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao realizar backup.");
        }
    }

    public static void restaurarBackup() {
        System.out.println("\n> Restaurar Backup:");
        try {
            ArrayList<String> backupsList = backup.listarBackups();
            if (!backupsList.isEmpty()) {
                System.out.print("ID do arquivo de backup: ");
                String input = console.nextLine();
                if (input.length() > 0) {
                    int idBackup = Integer.parseInt(input);
                    backup.restaurarBackup(backupsList.get(idBackup - 1) + ".db");
                    System.out.println("Backup restaurado com sucesso.");
                } else {
                    System.err.println("ID inválido. Operação cancelada!");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao restaurar backup.");
        }
    }
}
