public class ListaEncadeada {
    private class Nodo {
        Pessoa pessoa;
        Nodo proximo;

        Nodo(Pessoa pessoa) {
            this.pessoa = pessoa;
            this.proximo = null;
        }
    }

    private Nodo inicio;

    public ListaEncadeada() {
        this.inicio = null;
    }

    public void adicionarPessoa(Pessoa pessoa) {
        Nodo novo = new Nodo(pessoa);
        if (inicio == null) {
            inicio = novo;
        } else {
            Nodo atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        System.out.println("[LOG] Pessoa adicionada à lista encadeada: " + pessoa.getNome());
    }

    public Pessoa removerPessoa() {
        if (inicio == null) return null;
        Pessoa pessoa = inicio.pessoa;
        inicio = inicio.proximo;
        System.out.println("[LOG] Pessoa removida da lista encadeada: " + pessoa.getNome());
        return pessoa;
    }

    public boolean temPessoas() {
        return inicio != null;
    }
}
