public class Pessoa {
    private String nome;
    private int idade;
    private boolean cadeirante;
    private int peso;
    private int andarOrigem;
    private int andarDestino;
    private int tempoEspera; // tempo de espera em minutos

    public Pessoa(String nome, int idade, boolean cadeirante, int peso, int andarOrigem, int andarDestino) {
        this.nome = nome;
        this.idade = idade;
        this.cadeirante = cadeirante;
        this.peso = peso;
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.tempoEspera = 0; // inicia em zero
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public boolean isCadeirante() {
        return cadeirante;
    }

    public int getPeso() {
        return peso;
    }

    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean isPrioridade() {
        return idade >= 60 || cadeirante;
    }


    public boolean isIdoso() {
        return idade >= 60;
    }

public String getPrioridadeTexto() {
    if (isCadeirante()) return "Cadeirante";
    if (isIdoso()) return "Idoso";
    return "Normal";
}



    // Métodos para controlar tempo de espera
    public int getTempoEspera() {
        return tempoEspera;
    }

    public void incrementarTempoEspera() {
        tempoEspera++;
    }

    @Override
    public String toString() {
        return nome + " (idade: " + idade + ", peso: " + peso + ", origem: " + andarOrigem + ", destino: " + andarDestino + (isPrioridade() ? ", PRIORITÁRIA" : "") + ", espera: " + tempoEspera + "min)";
    }
}
