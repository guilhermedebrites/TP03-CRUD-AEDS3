package app.entidades;

import java.io.*;

import app.registros.Registro;

public class Rotulo implements Registro {

    private int id;
    private String rotulo;

    public Rotulo() {
        this.id = -1;
        this.rotulo = "";
    }

    public Rotulo(int id, String rotulo) {
        this.id = id;
        this.rotulo = rotulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(getId());
        dos.writeUTF(getRotulo());

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        setId(dis.readInt());
        setRotulo(dis.readUTF());
    }

    public String toString() {
        return "ID: " + getId() + "\nRotulo: " + getRotulo();
    }
}