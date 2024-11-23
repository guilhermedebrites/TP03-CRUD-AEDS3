package app.arquivos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import app.registros.RegistroArvoreBMais;

public class ParTarefaRotulo implements RegistroArvoreBMais<ParTarefaRotulo> {
    private int idTarefa;
    private int idRotulo;
    private final short TAMANHO = 32; // tamanho em bytes

    public ParTarefaRotulo() {
        this(-1, -1);
    }

    public ParTarefaRotulo(int idTarefa) {
        this(idTarefa, -1);
    }

    public ParTarefaRotulo(int idTarefa, int idRotulo) {
        this.idTarefa = idTarefa;
        this.idRotulo = idRotulo;
    }

    public int getIdRotulo() {
        return idRotulo;
    }

    public int getidTarefa() {
        return idTarefa;
    }

    @Override
    public ParTarefaRotulo clone() {
        ParTarefaRotulo pnic = null;
        try {
            pnic = new ParTarefaRotulo(this.idTarefa, this.idRotulo);
        } catch (Exception e) {
            pnic = null;
            e.printStackTrace();
        }
        return pnic;
    }

    public short size() {
        return this.TAMANHO;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.write(String.format("%-28s", this.idTarefa).getBytes());
        dos.writeInt(this.idRotulo);

        byte[] ba = baos.toByteArray();
        byte[] ba2 = new byte[this.TAMANHO];

        for (int i = 0; i < ba2.length; i++) {
            if (i < ba.length) {
                ba2[i] = ba[i];
            } else {
                ba2[i] = ' ';
            }
        }

        return ba2;
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        byte[] baidTarefa = new byte[28];
        dis.read(baidTarefa);
        this.idTarefa = Integer.parseInt(new String(baidTarefa).trim());
        this.idRotulo = dis.readInt();
    }

    @Override
    public int compareTo(ParTarefaRotulo obj) {
        return this.idTarefa - obj.idTarefa;
    }
}
