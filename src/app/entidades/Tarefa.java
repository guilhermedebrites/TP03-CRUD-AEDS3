package app.entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import app.registros.Registro;

public class Tarefa implements Registro {
    private int id;
    private String name;
    private LocalDate createAt;
    private LocalDate conclusionAt;
    private byte status;
    private int Prioridade;
    private int idCategoria;
    private ArrayList<Integer> idRotulos;

    public Tarefa() {
        this(0, "", LocalDate.now(), LocalDate.now(), (byte) 0, 0, 0, new ArrayList<>());
    }

    public Tarefa(int id, String name, LocalDate createAt, LocalDate conclusionAt, byte status, int Prioridade,
            int idCategoria, ArrayList<Integer> idRotulos) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.conclusionAt = conclusionAt;
        this.status = status;
        this.Prioridade = Prioridade;
        this.idCategoria = idCategoria;
        this.idRotulos = idRotulos;
    }

    public Tarefa(String nome, LocalDate dataCriacao, LocalDate dataConclusao, byte status2, byte prioridade2,
            int idCategoria2, ArrayList<Integer> idRotulos) {
        this.name = nome;
        this.createAt = dataCriacao;
        this.conclusionAt = dataConclusao;
        this.status = status2;
        this.Prioridade = prioridade2;
        this.idCategoria = idCategoria2;
        this.idRotulos = idRotulos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getConclusionAt() {
        return conclusionAt;
    }

    public void setConclusionAt(LocalDate conclusionAt) {
        this.conclusionAt = conclusionAt;
    }

    public String getStatus() {
        if (status == 0) {
            return "Pendente";
        } else if (status == 1) {
            return "Concluída";
        } else {
            return "Cancelada";
        }
    }

    public void setStatus(String status) {
        if (status.equalsIgnoreCase("Pendente")) {
            this.status = 0;
        } else if (status.equalsIgnoreCase("Concluída")) {
            this.status = 1;
        } else {
            this.status = 2;
        }
    }

    public int getPrioridade() {
        return Prioridade;
    }

    public void setPrioridade(int Prioridade) {
        this.Prioridade = Prioridade;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public ArrayList<Integer> getIdRotulos() {
        return idRotulos;
    }

    public void setIdRotulos(ArrayList<Integer> idRotulos) {
        this.idRotulos = idRotulos;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(getId());
        dos.writeUTF(getName());
        dos.writeInt((int) this.createAt.toEpochDay());
        dos.writeInt((int) this.conclusionAt.toEpochDay());
        dos.writeUTF(getStatus());
        dos.writeInt(getPrioridade());
        dos.writeInt(getIdCategoria());
        
        dos.writeInt(idRotulos.size()); 
        for (int i = 0; i < idRotulos.size(); i++) {
            dos.writeInt(idRotulos.get(i));
        }

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        setId(dis.readInt());
        setName(dis.readUTF());
        setCreateAt(LocalDate.ofEpochDay(dis.readInt()));
        setConclusionAt(LocalDate.ofEpochDay(dis.readInt()));
        setStatus(dis.readUTF());
        setPrioridade(dis.readInt());
        setIdCategoria(dis.readInt());

        int size = dis.readInt(); 
        ArrayList<Integer> idRotulos = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            idRotulos.add(dis.readInt());
        }
        setIdRotulos(idRotulos);
    }

    public String toString() {
        return "ID: " + getId() + " | Nome: " + getName() + " | Data de Criação: " + getCreateAt()
                + " | Data de Conclusão: " + getConclusionAt() + " | Status: " + getStatus() + " | Prioridade: "
                + getPrioridade() + " | ID da Categoria: " + getIdCategoria() + " | ID dos Rótulos: " + getIdRotulos();
    }
}