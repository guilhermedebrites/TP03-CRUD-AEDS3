package app.arquivos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import app.registros.RegistroArvoreBMais;

public class ParIDTarefa implements RegistroArvoreBMais<ParIDTarefa> {
    private int idCategoria;
    private int idTarefa;
    private final short SIZE = 15;

    public ParIDTarefa() throws Exception {
        this(-1, -1);
    }

    public ParIDTarefa(int idCategoria) throws Exception {
        this(idCategoria, -1);
    }

    public ParIDTarefa(int idCategoria, int idTarefa) throws Exception {
        this.idCategoria = idCategoria;
        this.idTarefa = idTarefa;
    }

    public int getIDCategoria() {
        return idCategoria;
    }

    public int getIDTarefa() {
        return idTarefa;
    }

    @Override
    public ParIDTarefa clone() {
        ParIDTarefa clone = null;
        try {
            clone = new ParIDTarefa(this.idCategoria, this.idTarefa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clone;
    }

    public int compareTo(ParIDTarefa picit) {
        int result = 0xFFFFFF7;
        if (this.idCategoria != picit.idCategoria) {
            result = this.idCategoria - picit.idCategoria;
        } else {
            result = this.idTarefa == -1 ? 0 : this.idTarefa - picit.idTarefa;
        }
        return result;
    }

    public short size() {
        return this.SIZE;
    }

    public String toString() {
        return String.format("%3d", this.idCategoria) + ";" + String.format("%3d", this.idTarefa);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.idCategoria);
        dos.writeInt(this.idTarefa);

        return (baos.toByteArray());
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.idCategoria = dis.readInt();
        this.idTarefa = dis.readInt();
    }

}