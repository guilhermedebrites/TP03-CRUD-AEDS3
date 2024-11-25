package app.arquivos;

import java.util.ArrayList;
import java.util.List;

import app.entidades.Tarefa;
import app.main.ArvoreBMais;

public class ArquivoTarefa extends Arquivo<Tarefa> {
    ArvoreBMais<ParIDTarefa> arvore;
    ArvoreBMais<ParRotuloTarefa> arvore2;

    public ArquivoTarefa() throws Exception {
        super("Tarefas.db", Tarefa.class.getConstructor());
        arvore = new ArvoreBMais<>(
                ParIDTarefa.class.getConstructor(),
                5,
                "Tarefas.db.bpt.idx");

        arvore2 = new ArvoreBMais<>(
            ParRotuloTarefa.class.getConstructor(),
            5,
            "TarefasRotulos.db.bpt.idx");
    }

    public int create(Tarefa obj) throws Exception {
        int id = super.create(obj);
        try {
            arvore.create(new ParIDTarefa(obj.getIdCategoria(), obj.getId()));
            ArrayList<Integer> idRotulo = obj.getIdRotulos();
            for(int i = 0; i < idRotulo.size(); i++){
                arvore2.create(new ParRotuloTarefa(idRotulo.get(i), id));
            }
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

    public ArrayList<Tarefa> read(ParRotuloTarefa parRotuloTarefa) throws Exception{
        ArrayList<Tarefa> t = new ArrayList<>();
        ArrayList<ParRotuloTarefa> id = new ArrayList<>();
        id = arvore2.read(new ParRotuloTarefa(parRotuloTarefa.getIdRotulo()));
        for(int i = 0; i<id.size(); i++){
            t.add(super.read(id.get(i).getIdRotulo())); 
        }
        return t;
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

    public boolean delete(Tarefa tarefa){
        boolean result = false;
        try{
            result = super.delete(tarefa.getId()) ? arvore.delete(new ParIDTarefa(tarefa.getIdCategoria(), tarefa.getId())) : false;
            ArrayList<Integer> idRotulos = tarefa.getIdRotulos();
            for(int i = 0; i < idRotulos.size(); i++){
                arvore2.delete(new ParRotuloTarefa(idRotulos.get(i), tarefa.getId()));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean updateEtiquetas(Tarefa tarefa, ArrayList<Integer> removidos, ArrayList<Integer> adicionados){
        boolean result = false;
        try{
            ArrayList<Integer> idRotulos = tarefa.getIdRotulos();

            if(idRotulos.size() > 0){
                for(int i = 0; i < removidos.size(); i++){
                    boolean existe = false;
                    for(int j = 0; j < idRotulos.size(); j++){
                        if(removidos.get(i) == idRotulos.get(j)){
                            existe = true;
                        }
                        else if(j == idRotulos.size() - 1 && !existe){
                            System.out.println("Etiqueta não encontrada");
                        }
                    }
                    if(existe){
                        arvore2.delete(new ParRotuloTarefa(removidos.get(i), tarefa.getId()));
                        idRotulos.remove(removidos.get(i));
                    }
                }
            }
            else if(removidos.size() > 0 && idRotulos.size() == 0){
                System.out.println("Não há etiquetas para serem removidas");
            }
            for(int i = 0; i < adicionados.size(); i++){
                boolean existe = false;
                if(idRotulos.size() > 0){
                    for(int j = 0; j < idRotulos.size(); j++){
                        if(adicionados.get(i) == idRotulos.get(j)){
                            System.out.println("Etiqueta já existente");
                            existe = true;
                        }
                    }
                }
                if(!existe){
                    idRotulos.add(adicionados.get(i));
                    arvore2.create(new ParRotuloTarefa(adicionados.get(i), tarefa.getId()));
                }
            }
            boolean update = super.update(tarefa);
            tarefa.setIdRotulos(idRotulos);
            if(update){
                result = true;
            }
            else{
                result = false;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
