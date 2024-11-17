# **Relatório TP2**

### **Alunos:**
- Guilherme Gomes de Brites  
- Nalberth Henrique Vieira  
- Mateus Nunes Bello  
- João Lucas Curi  

---

## **1. Classe ArquivoCategoria**

A classe `ArquivoCategoria` gerencia a leitura e escrita de categorias no arquivo **"Categorias.db"**. Ela utiliza uma **árvore B+** para otimizar a busca de registros, garantindo maior eficiência.  

### **Atributos**:
- **arqTarefa**: Arquivo de tarefas.
- **arvore**: Árvore B+ usada para indexação das categorias.

### **Construtor**:
- Inicializa o arquivo de categorias e a árvore B+ associada ao índice.

### **Métodos**:
- **`readAll()`**: Lê todos os registros de categorias do arquivo, ignorando registros excluídos, e retorna uma lista de objetos `Categoria`.

---

## **2. Classe ArquivoTarefa**

A classe `ArquivoTarefa` gerencia a criação, leitura e atualização de tarefas no arquivo **"Tarefas.db"**. Assim como em `ArquivoCategoria`, também utiliza uma **árvore B+** para otimizar a busca de registros, neste caso, de tarefas relacionadas a categorias.

### **Principais Métodos**:
- **`create(Tarefa obj)`**: Cria uma nova tarefa e insere seu índice na árvore B+.
- **`read(int idCategoria)`**: Lê uma tarefa relacionada a uma categoria específica.
- **`readAll()`**: Retorna todas as tarefas do arquivo, ignorando excluídas.
- **`readAll(int idCategoria)`**: Lê todas as tarefas de uma categoria específica.
- **`update(Tarefa novaTarefa)`**: Atualiza uma tarefa, incluindo mudanças na árvore B+ se necessário.

---

## **CHECKLIST**

- O CRUD (com índice direto) de categorias foi implementado? **SIM**
- Há um índice indireto de nomes para as categorias? **SIM**
- O atributo de ID de categoria, como chave estrangeira, foi criado na classe Tarefa? **SIM**
- Há uma árvore B+ que registre o relacionamento 1:N entre tarefas e categorias? **SIM**
- É possível listar as tarefas de uma categoria? **SIM**
- A remoção de categorias checa se há alguma tarefa vinculada a ela? **SIM**
- A inclusão da categoria em uma tarefa se limita às categorias existentes? **SIM**
- O trabalho está funcionando corretamente? **SIM**
- O trabalho está completo? **SIM**
- O trabalho é original e não a cópia de um trabalho de outro grupo? **SIM**
