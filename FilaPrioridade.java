public class FilaPrioridade {
    private Pessoa[] pessoas;
    private int quantidade;

    public FilaPrioridade(int capacidade) {
        if (capacidade <= 0) capacidade = 10;
        this.pessoas = new Pessoa[capacidade];
        this.quantidade = 0;
    }

    public void inserir(Pessoa p) {
        if (p == null) return;
        if (quantidade >= pessoas.length) {
            System.out.println("[ERRO] Fila cheia. Pessoa não adicionada: " + p.getNome());
            return;
        }

        int i = quantidade - 1;
        while (i >= 0 && comparaPrioridade(pessoas[i], p) < 0) {
            pessoas[i + 1] = pessoas[i];
            i--;
        }
        pessoas[i + 1] = p;
        quantidade++;
    }

    public Pessoa removerPessoa() {
        if (quantidade == 0) return null;
        Pessoa p = pessoas[0];
        for (int i = 1; i < quantidade; i++) {
            pessoas[i - 1] = pessoas[i];
        }
        pessoas[quantidade - 1] = null;
        quantidade--;
        return p;
    }

    public boolean estaVazia() {
        return quantidade == 0;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Pessoa[] toArray() {
        Pessoa[] copia = new Pessoa[quantidade];
        for (int i = 0; i < quantidade; i++) {
            copia[i] = pessoas[i];
        }
        return copia;
    }

    public void incrementarTempoEspera() {
        for (int i = 0; i < quantidade; i++) {
            if (pessoas[i] != null) {
                pessoas[i].incrementarTempoEspera();
            }
        }
    }

    private int comparaPrioridade(Pessoa a, Pessoa b) {
        return Integer.compare(prioridadePessoa(b), prioridadePessoa(a));
    }

    private int prioridadePessoa(Pessoa p) {
        if (p == null) return 0;
        if (p.isCadeirante()) return 3;
        if (p.isIdoso()) return 2;
        return 1;
    }

    public void imprimirFila() {
        System.out.println("[LOG] Fila de espera (" + quantidade + " pessoas):");
        for (int i = 0; i < quantidade; i++) {
            if (pessoas[i] != null) {
                System.out.println(" - " + pessoas[i]);
            }
        }
    }
}
