package app.arquivos;

import java.util.ArrayList;
import java.util.List;

import app.entidades.Categoria;
import app.main.ArvoreBMais;

public class ArquivoCategoria extends Arquivo<Categoria> {

    ArvoreBMais<ParIDCategoria> arvore;

    public ArquivoCategoria() throws Exception {
        super("Categorias.db", Categoria.class.getConstructor());
        arvore = new ArvoreBMais<>(
            ParIDCategoria.class.getConstructor(),
            5,
            "dados\\Categorias.db.bpt.idx"
        );
    }

    public List<Categoria> readAll() throws Exception {
        List<Categoria> categorias = new ArrayList<>();

        file.seek(header);
        byte lapide = ' ';
        short tam = 0;
        byte[] b = null;
        Categoria c = null;

        while (file.getFilePointer() < file.length()) {
            lapide = file.readByte();
            tam = file.readShort();
            b = new byte[tam];
            file.read(b);

            if (lapide != '*') {
                c = new Categoria();
                c.fromByteArray(b);
                categorias.add(c);
            }
        }

        return (categorias);
    }

    public Categoria read(String nome) throws Exception {
        ArrayList<ParIDCategoria> par = arvore.read(new ParIDCategoria(nome, -1));
        return super.read(par.get(0).getIDCategoria());
    }

    public int create(Categoria obj) throws Exception {
        int id = super.create(obj);
        arvore.create(new ParIDCategoria(obj.getNome(), id));
        return id;
    }

    public boolean delete(int nome) throws Exception {
        boolean result = false;
        Categoria obj = super.read(nome);
        if (obj != null) {
            if (arvore.delete(new ParIDCategoria(obj.getNome(), obj.getId()))) {
                result = super.delete(obj.getId());
            }
        }
        return result;
    }

    public boolean update(Categoria newCategoria) throws Exception {
        boolean result = false;
        Categoria oldCategoria = super.read(newCategoria.getId());
        if (super.update(newCategoria)) {
            if (newCategoria.getNome() != oldCategoria.getNome()) {
                if (arvore.delete(new ParIDCategoria(oldCategoria.getNome(), oldCategoria.getId()))) {
                    arvore.create(new ParIDCategoria(newCategoria.getNome(), newCategoria.getId()));
                }
                result = true;
            }
        }
        return result;
    }
}
