package app;

import java.util.List;

import app.arquivos.ArquivoCategoria;
import app.entidades.Categoria;
import app.main.Principal;

public class MenuCategorias extends Principal {
    private static ArquivoCategoria arqCategorias;

    public MenuCategorias() throws Exception {
        arqCategorias = new ArquivoCategoria();
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
        System.out.println("> Início > Categorias    ");
        System.out.println("1 - Buscar               ");
        System.out.println("2 - Incluir              ");
        System.out.println("3 - Alterar              ");
        System.out.println("4 - Excluir              ");
        System.out.println("0 - Voltar               ");
        System.out.print("Opção: ");
    }

    protected static void action(int opcao) {
        switch (opcao) {
            case 0:
                break;
            case 1:
                buscarCategoria();
                break;
            case 2:
                incluirCategoria();
                break;
            case 3:
                alterarCategoria();
                break;
            case 4:
                excluirCategoria();
                break;

            default:
                System.out.println("Opção incorreta!");
                break;
        }
    }

    private static void listarCategorias(List<Categoria> lista) {
        if (lista != null) {
            System.out.println("\nLista de categorias:");
            int tam = lista.size();
            for (int i = 0; i < tam; i++) {
                System.out.println((i + 1) + ": " + lista.get(i).getNome());
            }
        }
    }

    private static Categoria ler_Categoria() {
        String nome = "";
        boolean dadosCompletos = false;
        do {
            System.out.print("\nNome da categoria: ");
            nome = console.nextLine();

            if (nome.length() >= 5 || nome.length() == 0) {
                dadosCompletos = true;
            } else {
                System.err.println("Erro: Nome da categoria inválido.");
            }
        } while (dadosCompletos == false);

        return (new Categoria(0, nome));
    }

    public static void incluirCategoria() {
        System.out.println("\n> Incluir:");

        Categoria novaCategoria = ler_Categoria();

        if (novaCategoria != null && novaCategoria.getNome().length() > 0) {
            System.out.println("Confirma a inclusão? (S/N)");
            char resp = console.nextLine().charAt(0);

            if (resp == 'S' || resp == 's') {
                try {
                    arqCategorias.create(novaCategoria);
                    System.out.println("Categoria criada.");
                } catch (Exception e) {
                    System.out.println("Erro do sistema. Não foi possível criar a categoria!");
                }
            }
        } else {
            System.out.println("Operação cancelada!");
        }
    }

    public static void buscarCategoria() {
        System.out.println("\n> Buscar Categoria:");

        try {
            List<Categoria> lista = arqCategorias.readAll();

            if (lista.isEmpty()) {
                System.out.println("Não há categoria cadastrada.");
            } else {
                listarCategorias(lista);

                System.out.print("Nome da Categoria: ");
                String nome = console.nextLine();

                if (nome.length() > 0) {
                    Categoria categoriaEncontrada = null;
                    boolean encontrada = false;
                    int tam = lista.size();
                    for (int i = 0; i < tam && !encontrada; i++) {
                        if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                            categoriaEncontrada = lista.get(i);
                            encontrada = true;
                        }
                    }

                    if (categoriaEncontrada != null) {
                        System.out.println(categoriaEncontrada);
                    } else {
                        System.out.println("Categoria não encontrada.");
                    }
                } else {
                    System.out.println("Operação cancelada!");
                }
            }

        } catch (Exception e) {
            System.err.println("Não foi possível buscar a categoria!");
        }
    }

    public static void alterarCategoria() {
        System.out.println("\n> Alterar Categoria:");

        try {
            List<Categoria> lista = arqCategorias.readAll();

            if (lista.isEmpty()) {
                System.out.println("Não há categoria cadastrada.");
            } else {
                System.out.println("\nLista de categorias:");
                listarCategorias(lista);

                System.out.print("Nome da Categoria: ");
                String nome = console.nextLine();

                if (nome.length() > 0) {
                    Categoria categoriaEncontrada = null;
                    boolean encontrada = false;
                    int tam = lista.size();
                    for (int i = 0; i < tam && !encontrada; i++) {
                        if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                            categoriaEncontrada = lista.get(i);
                            encontrada = true;
                        }
                    }

                    if (categoriaEncontrada != null) {
                        Categoria novaCategoria = ler_Categoria();

                        if (novaCategoria != null && novaCategoria.getNome().length() > 0) {
                            novaCategoria.setId(categoriaEncontrada.getId());
                            arqCategorias.update(novaCategoria);
                            System.out.println("Categoria alterada com sucesso.");
                        } else {
                            System.out.println("Operação cancelada!");
                        }
                    } else {
                        System.out.println("Categoria não encontrada.");
                    }
                } else {
                    System.out.println("Operação cancelada!");
                }
            }

        } catch (Exception e) {
            System.out.println("Não foi possível alterar a categoria!");
        }
    }

    public static void excluirCategoria() {
        System.out.println("\n> Excluir Categoria:");

        try {
            List<Categoria> lista = arqCategorias.readAll();

            if (lista.isEmpty()) {
                System.out.println("Não há categoria cadastrada.");
            } else {
                System.out.println("\nLista de categorias:");
                listarCategorias(lista);

                System.out.print("Nome da categoria: ");
                String nome = console.nextLine();

                if (nome.length() > 0) {
                    Categoria categoriaEncontrada = null;
                    boolean encontrada = false;
                    int tam = lista.size();
                    for (int i = 0; i < tam && !encontrada; i++) {
                        if (lista.get(i).getNome().equalsIgnoreCase(nome)) {
                            categoriaEncontrada = lista.get(i);
                            encontrada = true;
                        }
                    }

                    if (categoriaEncontrada != null) {
                        System.out.print("\nCategoria:");
                        System.out.println(categoriaEncontrada);

                        System.out.println("\nConfirma a exclusão? (S/N)");
                        char resp = console.nextLine().charAt(0);

                        if (resp == 'S' || resp == 's') {
                            boolean sucesso = arqCategorias.delete(categoriaEncontrada.getId());

                            if (sucesso) {
                                System.out.println("Categoria excluída com sucesso.");
                            } else {
                                System.out.println("Não foi possível excluir a categoria.");
                            }
                        }
                    } else {
                        System.out.println("Categoria não encontrada.");
                    }
                } else {
                    System.out.println("Operação cancelada!");
                }
            }

        } catch (Exception e) {
            System.out.println("Não foi possível excluir a categoria!");
        }
    }
}
