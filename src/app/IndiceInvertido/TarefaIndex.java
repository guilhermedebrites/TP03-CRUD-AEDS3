package app.IndiceInvertido;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import app.entidades.ElementoLista;

public class TarefaIndex {
    private ListaInvertida listaInvertida;
    private int totalTarefas;

    public TarefaIndex(int maxBloco, String arquivoDic, String arquivoBloco) throws Exception {
        listaInvertida = new ListaInvertida(maxBloco, arquivoDic, arquivoBloco);
        totalTarefas = listaInvertida.numeroEntidades();
    }

    public void inserirTarefa(int id, String descricao) throws Exception {
        List<String> termos = StringProcessor.processar(descricao);
        for (String termo : termos) {
            float tf = calcularTF(termo, termos);
            listaInvertida.create(termo, new ElementoLista(id, tf));
        }
        totalTarefas++;
        listaInvertida.incrementaEntidades();
    }

    private float calcularTF(String termo, List<String> termos) {
        long ocorrencias = termos.stream().filter(t -> t.equals(termo)).count();
        return (float) ocorrencias / termos.size();
    }

    public void excluirTarefa(int id, String descricao) throws Exception {
        List<String> termos = StringProcessor.processar(descricao);
        for (String termo : termos) {
            listaInvertida.delete(termo, id);
        }
        totalTarefas--;
        listaInvertida.decrementaEntidades();
    }

    public List<Integer> buscar(String consulta) throws Exception {
    List<String> termos = StringProcessor.processar(consulta);
    Map<Integer, Float> resultados = new HashMap<>();

    for (String termo : termos) {
        ElementoLista[] lista = listaInvertida.read(termo);
        float idf = (float) Math.log((double) totalTarefas / lista.length);
        for (ElementoLista el : lista) {
            resultados.put(el.getId(), resultados.getOrDefault(el.getId(), 0f) + el.getFrequencia() * idf);
        }
    }

    return resultados.entrySet().stream()
                     .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
                     .map(Map.Entry::getKey)
                     .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        boolean resultado = false;
        if(totalTarefas == 0) {
            resultado = true;
        }
        return resultado;
    }

}
