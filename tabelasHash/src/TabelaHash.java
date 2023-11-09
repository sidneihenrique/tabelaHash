public class TabelaHash {
    private Node[] tabela;
    private int tamanho;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Node[tamanho];
    }

    public int funcaoHashDivisao(int chave) {
        return chave % tamanho;
    }

    public int funcaoHashMultiplicacao(int chave) {
        double A = (Math.sqrt(5) - 1) / 2;
        double valor = A * chave - Math.floor(A * chave);
        return (int) Math.floor(tamanho * valor);
    }

    public int funcaoHashDobramento(int chave) {
        String chaveString = String.valueOf(chave);
        int tamanhoChave = chaveString.length();
        int soma = 0;

        for (int i = 0; i < tamanhoChave; i++) {
            int digito = Character.getNumericValue(chaveString.charAt(i));
            soma += digito;
        }

        return soma % tamanho;
    }

    public void inserir(Registro registro, int funcaoHash) {
        int chave = registro.getCodigoRegistro();
        int indice = funcaoHash;

        Node newNode = new Node(registro);

        if (tabela[indice] == null) {
            tabela[indice] = newNode;
        } else {
            Node currentNode = tabela[indice];
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
    }

    public int contarColisoes(int tamanhoDaTabela) {
        int colisoes = 0;
        for (int i = 0; i < tamanhoDaTabela; i++) {
            Node currentNode = tabela[i];
            if (currentNode != null) {
                while (currentNode.next != null) {
                    currentNode = currentNode.next;
                    colisoes++;
                }
            }
        }
        return colisoes;
    }
}