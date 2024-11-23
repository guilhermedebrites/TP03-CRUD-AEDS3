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

    public boolean update(Tarefa newTarefa) throws Exception {
        boolean result = false;
        Tarefa olfTarefa = super.read(newTarefa.getIdCategoria());
        if (super.update(newTarefa)) {
            if (newTarefa.getIdCategoria() != olfTarefa.getIdCategoria()) {
                arvore.delete(new ParIDTarefa(olfTarefa.getIdCategoria(), olfTarefa.getId()));
                arvore.create(new ParIDTarefa(newTarefa.getIdCategoria(), newTarefa.getId()));
            }
            result = true;
        }
        return result;
    }
}
