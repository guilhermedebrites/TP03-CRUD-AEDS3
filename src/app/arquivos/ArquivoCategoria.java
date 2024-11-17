package app.arquivos;

import java.util.ArrayList;
import java.util.List;

import app.entidades.Categoria;
import app.main.ArvoreBMais;

public class ArquivoCategoria extends Arquivo<Categoria> {
    Arquivo<Categoria> arqTarefa;
    ArvoreBMais<ParIDCategoria> arvore;

    public ArquivoCategoria() throws Exception {
        super("Categorias.db", Categoria.class.getConstructor());
        arvore = new ArvoreBMais<>(
                ParIDCategoria.class.getConstructor(),
                5,
                "Categorias.db.bpt.idx");
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
}
