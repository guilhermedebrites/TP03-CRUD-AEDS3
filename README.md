# **Relatório TP3**

### **Alunos:**
- Guilherme Gomes de Brites  
- Nalberth Henrique Vieira  
- Mateus Nunes Bello  
- João Lucas Curi  

---

## **1. Classe TarefaIndex**

A classe `TarefaIndex` representa o núcleo de manipulação e gerenciamento de um índice invertido associado a tarefas. Seus métodos permitem adicionar, excluir e buscar tarefas, além de verificar o estado do índice.

### **Atributos**:
- **listaInvertida**: Instância para gerenciar o índice invertido.
- **totalTarefas**: Contador do número total de tarefas no índice.


### **Construtor**:
- Inicializa a instância de ListaInvertida e atualiza o total de tarefas.

### **Métodos**:
- **`inserirTarefa`**: Processa o texto da tarefa para obter os termos usando `StringProcessor`, Calcula o TF, Adiciona os termos e suas frequências ao índice invertido e Atualiza o contador de tarefas.
- **`excluirTarefa`**: Processa o texto da tarefa para obter os termos, Remove cada termo associado ao ID no índice invertido e Atualiza o contador de tarefas.
- **`buscar`**: Processa a consulta para obter os termos relevantes, Calcula o TF-IDF de cada termo para encontrar as tarefas mais relevantes, Retorna uma lista ordenada de IDs com base na relevância.
- **`isEmpty`**: Verifica se o índice está vazio retornando true ou false.

---

## **2. class StringProcessor**

A classe `StringProcessor` é utilitária para processar textos e extrair termos relevantes.

### **Método**:
- **`processar`**: Converte o texto para letras minúsculas, Remove acentuações e caracteres não-ASCII, Divide o texto em palavras com base em delimitadores, Remove palavras irrelevantes (stop words).

---

## **3. -------**

---

## **CHECKLIST**

- O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida? **SIM**
- O CRUD de rótulos foi implementado? **SIM**
- No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+? **SIM**
- É possível buscar tarefas por palavras usando o índice invertido? **SIM**
- É possível buscar tarefas por rótulos usando uma árvore B+? **SIM**
- O trabalho está completo? **SIM**
- O trabalho é original e não a cópia de um trabalho de um colega? **SIM**