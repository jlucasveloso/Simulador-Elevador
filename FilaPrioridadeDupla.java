public class FilaPrioridadeDupla {
    private FilaPrioridade filaSubir;
    private FilaPrioridade filaDescer;

    public FilaPrioridadeDupla(int capacidade) {
        filaSubir = new FilaPrioridade(capacidade);
        filaDescer = new FilaPrioridade(capacidade);
    }

    public void inserir(Pessoa p) {
        if (p == null) return;
        if (p.getAndarDestino() > p.getAndarOrigem()) {
            filaSubir.inserir(p);
        } else {
            filaDescer.inserir(p);
        }
    }

    public Pessoa removerPessoaSubir() {
        return filaSubir.removerPessoa();
    }

    public Pessoa removerPessoaDescer() {
        return filaDescer.removerPessoa();
    }

    public boolean estaVaziaSubir() {
        return filaSubir.estaVazia();
    }

    public boolean estaVaziaDescer() {
        return filaDescer.estaVazia();
    }

    public boolean temPessoasEsperando() {
        return !filaSubir.estaVazia() || !filaDescer.estaVazia();
    }

    public Pessoa[] getArraySubir() {
        return filaSubir.toArray();
    }

    public Pessoa[] getArrayDescer() {
        return filaDescer.toArray();
    }

    public void incrementarTempoEspera() {
        filaSubir.incrementarTempoEspera();
        filaDescer.incrementarTempoEspera();
    }

    public int contarPessoasSubirNoAndar(int andar) {
        Pessoa[] arr = filaSubir.toArray();
        int count = 0;
        for (Pessoa p : arr) {
            if (p != null && p.getAndarOrigem() == andar) {
                count++;
            }
        }
        return count;
    }

    public int contarPessoasDescerNoAndar(int andar) {
        Pessoa[] arr = filaDescer.toArray();
        int count = 0;
        for (Pessoa p : arr) {
            if (p != null && p.getAndarOrigem() == andar) {
                count++;
            }
        }
        return count;
    }

    public void imprimirFilas() {
        System.out.println("[LOG] Fila de subir:");
        filaSubir.imprimirFila();
        System.out.println("[LOG] Fila de descer:");
        filaDescer.imprimirFila();
    }
}
