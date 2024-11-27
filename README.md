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
- **`listaInvertida`**: Instância para gerenciar o índice invertido.
- **`totalTarefas`**: Contador do número total de tarefas no índice.


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

## **3. Class ArquivoRotulo**

A classe `ArquivoRotulo` Gerencia operações de armazenamento e recuperação de rótulos em arquivos, além de sua indexação em uma Árvore-B+.

### **Atributos**: 

- **`arvoreRotulo`**: Árvore-B+ para indexação dos rótulos.

### **Construtor**:

Inicializa o arquivo Rotulos.db e a Árvore-B+ associada.

### **Método**:

- **`create`**: Adiciona um novo rótulo ao arquivo e cria um índice relacionado na Árvore-B+.
- **`read`**: Retorna um rótulo pelo seu identificador.
- **`readAll`**: Retorna todos os rótulos presentes no arquivo.
- **`update`**: Atualiza os dados de um rótulo no arquivo e atualiza o índice da Árvore-B+ caso o identificador do rótulo seja alterado.
- **`delete`**: Remove um rótulo do arquivo e remove o índice correspondente na Árvore-B+.

---

## **4. Class Rotulo**

A classe `Rotulo` Representa um rótulo associado a tarefas. Implementa a interface Registro para manipulação de dados em formato binário.

### **Atributos**: 

- **`id`**: Identificador único do rótulo.
- **`rotulo`**: Nome ou descrição do rótulo.

### **Construtor**:

Inicializa o rótulo com os valores fornecidos.

### **Método**:

- **`getId`**: Retorna o identificador do rótulo.
- **`setId`**: Define o identificador do rótulo.
- **`getRotulo`**: Retorna a descrição do rótulo.
- **`setRotulo`**: Define a descrição do rótulo.
- **`toByteArray`**: Converte os dados do rótulo para um array de bytes.
- **`fromByteArray`**: Lê os dados de um array de bytes para preencher os atributos do rótulo.
- **`toString`**: Retorna uma representação textual do rótulo.

---

## **5. Class ParTarefaRotulo**

A classe `ParTarefaRotulo` Representa um par associativo entre uma tarefa e um rótulo, utilizado na indexação pela Árvore-B+.

### **Atributos**: 

- **`idTarefa`**: Identificador da tarefa.
- **`idRotulo`**: Identificador do rótulo.
- **`TAMANHO`**: Tamanho fixo da representação em bytes (32 bytes).

### **Construtor**:

Inicializa o par com os identificadores da tarefa e do rótulo.

### **Método**:

- **`getIdRotulo`**: Retorna o identificador do rótulo.
- **`getIdTarefa`**: Retorna o identificador da tarefa.
- **`clone`**: Cria uma cópia do objeto atual.
- **`size`**: Retorna o tamanho fixo do objeto em bytes.
- **`fromByteArray`**: Converte um array de bytes nos atributos do par.
- **`toByteArray`**: Converte um array de bytes nos atributos do par.
- **`compareTo`**: Compara o par atual com outro par baseado no idTarefa.

---

## **6. Class MenuRotulos**

A classe `MenuRotulos` cria um menu para operações com Rotulos.

### **Método**:

- **`incluirRotulo`**: Recebe os dados de um novo rótulo e o adiciona.
- **`buscarRotulo`**: Lista os rótulos e permite buscar um específico pelo ID.
- **`alterarRotulo`**: Edita os dados de um rótulo existente.
- **`excluirRotulo`**: Remove um rótulo do sistema.

- **`buscarTarefaPorRotulo`**: Busca tarefas associadas a um rótulo específico.

---

## **CHECKLIST**

- O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida? **SIM**
- O CRUD de rótulos foi implementado? **SIM**
- No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+? **SIM**
- É possível buscar tarefas por palavras usando o índice invertido? **SIM**
- É possível buscar tarefas por rótulos usando uma árvore B+? **SIM**
- O trabalho está completo? **SIM**
- O trabalho é original e não a cópia de um trabalho de um colega? **SIM**