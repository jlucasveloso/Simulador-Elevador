public class Log {
    private String[] mensagens;
    private int capacidade;
    private int tamanho;

    public Log(int capacidade) {
        this.capacidade = capacidade;
        this.mensagens = new String[capacidade];
        this.tamanho = 0;
    }

    public void adicionar(String mensagem) {
        if (tamanho < capacidade) {
            mensagens[tamanho] = mensagem;
            tamanho++;
        } else {
            // Se quiser, pode fazer um deslocamento para manter últimas mensagens
            // Ou simplesmente ignorar mensagens extras
        }
    }

    public void imprimir() {
        System.out.println("\n=== LOG DE SIMULAÇÃO ===");
        for (int i = 0; i < tamanho; i++) {
            System.out.println(mensagens[i]);
        }
        System.out.println("=== FIM DO LOG ===\n");
    }
}
