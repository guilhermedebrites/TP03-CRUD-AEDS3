package app.arquivos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import app.registros.RegistroArvoreBMais;

public class ParIDCategoria implements RegistroArvoreBMais<ParIDCategoria> {
    private String nome;
    private int idCategoria;
    private final short TAMANHO = 32; // tamanho em bytes

    public ParIDCategoria() {
        this("", -1);
    }

    public ParIDCategoria(String nome) {
        this(nome, -1);
    }

    public ParIDCategoria(String nome, int idCategoria) {
        if (nome.getBytes().length > 28)
            throw new IllegalArgumentException("Nome da categoria deve ter no máximo 28 bytes.");
        this.nome = nome;
        this.idCategoria = idCategoria;
    }

    public int getIDCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public ParIDCategoria clone() {
        ParIDCategoria pnic = null;
        try {
            pnic = new ParIDCategoria(this.nome, this.idCategoria);
        } catch (Exception e) {
            pnic = null;
            e.printStackTrace();
        }
        return pnic;
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.nome + ";" + String.format("%3d", this.idCategoria) + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        byte[] ba = new byte[28];
        byte[] baNome = this.nome.getBytes();
        int i = 0;
        while (i < baNome.length && i < ba.length) {
            ba[i] = baNome[i];
            i++;
        }
        while (i < 28) {
            ba[i] = ' ';
            i++;
        }

        dos.write(baNome);
        dos.writeInt(this.idCategoria);

        return (baos.toByteArray());
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        byte[] b = new byte[28];
        dis.read(b);

        this.nome = (new String(b)).trim();
        this.idCategoria = dis.readInt();
    }

    @Override
    public int compareTo(ParIDCategoria obj) {
        return this.nome.compareTo(obj.nome);
    }

}