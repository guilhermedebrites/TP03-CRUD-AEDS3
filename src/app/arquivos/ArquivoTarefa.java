package app.arquivos;

import java.util.ArrayList;
import java.util.List;

import app.entidades.Tarefa;
import app.main.ArvoreBMais;

public class ArquivoTarefa extends Arquivo<Tarefa> {
    ArvoreBMais<ParIDTarefa> arvore;

    public ArquivoTarefa() throws Exception {
        super("Tarefas.db", Tarefa.class.getConstructor());
        arvore = new ArvoreBMais<>(
                ParIDTarefa.class.getConstructor(),
                5,
                "Tarefas.db.bpt.idx");
    }

    @Override
    public int create(Tarefa obj) throws Exception {
        int id = super.create(obj);
        try {
            arvore.create(new ParIDTarefa(obj.getIdCategoria(), obj.getId()));
        } catch (Exception e) {
            System.out.print("");
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Tarefa read(int idCategoria) throws Exception {
        ArrayList<ParIDTarefa> picit = arvore.read(new ParIDTarefa(idCategoria, -1));
        return super.read(picit.get(0).getIDTarefa());
    }

    public List<Tarefa> readAll() throws Exception {
        List<Tarefa> tarefas = new ArrayList<>();

        file.seek(header);
        byte lapide = ' ';
        short tam = 0;
        byte[] b = null;

        Tarefa t = null;
        while (file.getFilePointer() < file.length()) {
            lapide = file.readByte();
            tam = file.readShort();
            b = new byte[tam];
            file.read(b);

            if (lapide != '*') {
                t = new Tarefa();
                t.fromByteArray(b);
                tarefas.add(t);
            }
        }
        return (tarefas);
    }

    public List<Tarefa> readAll(int idCategoria) throws Exception {
        List<Tarefa> tarefas = new ArrayList<>();

        file.seek(header);
        byte lapide = ' ';
        short tam = 0;
        byte[] b = null;

        Tarefa t = null;
        while (file.getFilePointer() < file.length()) {
            lapide = file.readByte();
            tam = file.readShort();
            b = new byte[tam];
            file.read(b);

            if (lapide != '*') {
                t = new Tarefa();
                t.fromByteArray(b);
                if (t.getIdCategoria() == idCategoria) {
                    tarefas.add(t);
                }
            }
        }
        return (tarefas);
    }

    @Override
    public boolean update(Tarefa novaTarefa) throws Exception {
        boolean result = false;
        Tarefa tarefaAntiga = super.read(novaTarefa.getId());
        if (super.update(novaTarefa)) {
            if (novaTarefa.getId() != tarefaAntiga.getId()) {
                arvore.delete(new ParIDTarefa(tarefaAntiga.getIdCategoria(), tarefaAntiga.getId()));
                arvore.create(new ParIDTarefa(novaTarefa.getIdCategoria(), novaTarefa.getId()));
            }
            result = true;
        }
        return result;
    }

    public boolean update(Tarefa novaTarefa, int id) throws Exception {
        boolean result = false;
        Tarefa tarefaAntiga = super.read(novaTarefa.getId());
        if (super.update(novaTarefa)) {
            if (novaTarefa.getIdCategoria() != tarefaAntiga.getIdCategoria()) {
                arvore.delete(new ParIDTarefa(tarefaAntiga.getIdCategoria(), tarefaAntiga.getId()));
                arvore.create(new ParIDTarefa(novaTarefa.getIdCategoria(), novaTarefa.getId()));
            }
            result = true;
        }
        return result;
    }
}
