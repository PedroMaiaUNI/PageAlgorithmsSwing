# Simulador de Algoritmos de Substituição de Páginas

Este projeto tem como objetivo simular o comportamento de diferentes algoritmos de substituição de páginas no contexto de gerenciamento de memória virtual em sistemas operacionais.

Repositório: [https://github.com/PedroMaiaUNI/PageAlgorithmsSwing](https://github.com/PedroMaiaUNI/PageAlgorithmsSwing)

---

## 📚 Sobre o Projeto

O gerenciamento eficiente da memória virtual é crucial para o desempenho dos sistemas operacionais. Neste projeto, são avaliados os seguintes algoritmos:

- **FIFO (First-In, First-Out)**
- **LRU (Least Recently Used)**
- **NFU (Not Frequently Used)**
- **Aging (Envelhecimento)**

O simulador foi implementado em **Java com interface Swing**, permitindo a inserção de parâmetros como tamanho da memória, fila de requisições e valor de clock interrupt (para NFU e Aging).

---

## 🧠 Algoritmos Implementados

### 🔁 FIFO (First-In, First-Out)
- Substitui a página mais antiga da memória.
- Simples de implementar.
- Pouco eficiente em cenários com alta reutilização de páginas.

### 🧠 LRU (Least Recently Used)
- Remove a página menos recentemente usada.
- Aproximação do algoritmo ótimo.
- Exige controle de uso recente (maior custo de implementação).

### 📊 NFU (Not Frequently Used)
- Utiliza contadores para medir a frequência de acesso das páginas.
- Páginas com menos acessos são substituídas.
- Punitivo em padrões de acesso variáveis, pois mantém o histórico completo.

### 🕒 Aging (Envelhecimento)
- Variação do NFU com contadores binários que decaem com o tempo.
- Considera tanto frequência quanto recência.
- Melhor adaptabilidade em sistemas com recursos limitados.

---

## ▶️ Como Executar o Simulador

### 🧪 Executando pelo Código (IDE como IntelliJ ou Eclipse)

1. Clone o repositório:
   ```bash
   git clone https://github.com/PedroMaiaUNI/PageAlgorithmsSwing.git
   cd PageAlgorithmsSwing
   ```

2. Abra o projeto em sua IDE Java favorita.

3. Compile e execute a classe SimulatorUI.java.

> Certifique-se de que o JDK 17 ou superior esteja instalado.

### 📦 Executando via Release (.jar)

1. Acesse a aba [Releases](https://github.com/PedroMaiaUNI/PageAlgorithmsSwing/releases) no repositório.

2. Baixe o arquivo `.jar` mais recente.

3. Execute com o seguinte comando:
   ```bash
   java -jar SimulatorPageAlgorithms.jar
    ```

> É necessário ter o Java instalado e configurado no sistema.

---

## ⚙️ Metodologia

- O simulador recebe como entrada:
  - A memória inicial.
  - A fila de requisições.
  - O valor de clock interrupt.
- Calcula e exibe o número de **page faults** para cada algoritmo.
- Interface gráfica desenvolvida com **Java Swing**.

---

## 📊 Resultados

Com base em testes realizados com a seguinte configuração:

- **Memória inicial:** `0,0,0`
- **Fila de requisições:** `7,5,1,2,5,3,5,4,2,3,5,3,2,1,2,5,1,7,5,1`
- **Clock interrupt:** `3`

Os resultados foram:

| Algoritmo | Page Faults |
|-----------|-------------|
| FIFO      | 15          |
| LRU       | 12          |
| NFU       | 10          |
| Aging     | 11          |

---

## 🧪 Conclusões

- **FIFO** é simples, mas pode ser ineficiente.
- **LRU** apresenta melhor desempenho em cargas com alta localidade temporal.
- **NFU** é mais conservador, acumulando o histórico completo.
- **Aging** representa uma alternativa mais equilibrada entre frequência e recência.

---

## 📖 Referências

- MACHADO, Francis Berenger; MAIA, Luiz Paulo. *Arquitetura de Sistemas Operacionais*. LTC, 2004.
- [Algoritmos de Substituição de Páginas - YouTube](https://youtu.be/mzlaMAV1-wg?si=VPacAaotlqmMcg2s)
- [Substituição de Página - YouTube](https://youtu.be/s3oJG0M3hSs?si=xqSVxiFMnDYBJQvl)

---

## 👨‍💻 Autor

**Pedro Lucas Maia de Lima**  
Universidade de Fortaleza - Ciência da Computação
