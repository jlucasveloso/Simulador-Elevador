public class Configuracao {
    public int numeroAndares;
    public int numeroElevadores;
    public int capacidadeElevador;
    public int tempoMinimoViagem;  // em segundos
    public int tempoMaximoViagem;  // em segundos
    public int tempoMaximoEspera;  // em minutos
    public int consumoEnergiaDeslocamento; // unidades por andar
    public int consumoEnergiaParada;       // unidades por parada
    public int pesoMaximoElevador;         // em kg

    public Configuracao() {
        // valores padrão
        numeroAndares = 10;
        numeroElevadores = 2;
        capacidadeElevador = 10;
        tempoMinimoViagem = 5;
        tempoMaximoViagem = 10;
        tempoMaximoEspera = 30;
        consumoEnergiaDeslocamento = 2;
        consumoEnergiaParada = 1;
        pesoMaximoElevador = 1000;
    }

    public int getCapacidadeMaximaPassageiros() {
        return capacidadeElevador;
    }

    public int getCapacidadeMaximaPeso() {
        return pesoMaximoElevador;
    }

    public int getPesoMaximo() {
        return pesoMaximoElevador;
    }

    public int getConsumoPorAndar() {
        return consumoEnergiaDeslocamento;
    }

    public int getAndarMaximo() {
        return numeroAndares - 1;
    }
}
