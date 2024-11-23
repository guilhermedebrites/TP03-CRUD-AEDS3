package app.arquivos;

import java.util.ArrayList;
import java.util.List;

import app.entidades.Rotulo;
import app.main.ArvoreBMais;

public class ArquivoRotulo extends Arquivo<Rotulo> {
    ArvoreBMais<ParRotuloTarefa> arvoreRotulo;

    public ArquivoRotulo() throws Exception {
        super("Rotulos.db", Rotulo.class.getConstructor());
        arvoreRotulo = new ArvoreBMais<>(
                ParRotuloTarefa.class.getConstructor(),
                5,
                "Rotulos.db.bpt.idx");
    }

    public int create(Rotulo rotulo) throws Exception {
        int id = super.create(rotulo);
        try {
            arvoreRotulo.create(new ParRotuloTarefa(rotulo.getId(), -1)); // ID do rótulo relacionado à tarefa (genérico)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public Rotulo read(int idRotulo) throws Exception {
        return super.read(idRotulo);
    }

    public List<Rotulo> readAll() throws Exception {
        List<Rotulo> rotulos = new ArrayList<>();

        file.seek(header);
        byte lapide = ' ';
        short tam = 0;
        byte[] b = null;

        Rotulo t = null;
        while (file.getFilePointer() < file.length()) {
            lapide = file.readByte();
            tam = file.readShort();
            b = new byte[tam];
            file.read(b);

            if (lapide != '*') {
                t = new Rotulo();
                t.fromByteArray(b);
                rotulos.add(t);
            }
        }
        return (rotulos);
    }

    public boolean update(int idRotulo, Rotulo novoRotulo) throws Exception {
        boolean result = false;
        Rotulo oldRotulo = super.read(novoRotulo.getId());
        if (super.update(novoRotulo)) {
            if (novoRotulo.getId() != oldRotulo.getId()) {
                arvoreRotulo.delete(new ParRotuloTarefa(oldRotulo.getId(), oldRotulo.getId()));
                arvoreRotulo.create(new ParRotuloTarefa(novoRotulo.getId(), novoRotulo.getId()));
            }
            result = true;
        }
        return result;
    }
    
    public boolean delete(int idRotulo) throws Exception {
        boolean removido = super.delete(idRotulo);
        if (removido) {
            try {
                arvoreRotulo.delete(new ParRotuloTarefa(idRotulo, -1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return removido;
    }
}
