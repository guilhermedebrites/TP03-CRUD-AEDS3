package app.main;

import java.util.Scanner;

import app.MenuCategorias;
import app.MenuTarefas;
import app.MenuRotulos;
import app.MenuBackup;

public class Principal {
    protected static Scanner console = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("PUCBOOK 1.0        ");
            System.out.println("-------------------");
            System.out.println("> Início           ");
            System.out.println("1) Categorias      ");
            System.out.println("2) Tarefas         ");
            System.out.println("3) Rótulos         ");
            System.out.println("4) Backup          ");
            System.out.println("0) Sair            ");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    (new MenuCategorias()).menu();
                    break;
                case 2:
                    (new MenuTarefas()).menu();
                    break;
                case 3:
                    (new MenuRotulos()).menu();
                    break;
                case 4:
                    (new MenuBackup()).menu();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    protected static int ler_opcao() {
        int opcao = 0;
        try {
            opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
            opcao = -1;
        }
        return opcao;
    }
}