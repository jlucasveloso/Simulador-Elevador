public class ResumoSimulacao {
    private int totalPassageirosGerados;
    private int totalEmbarques;
    private int totalDesembarques;
    private int totalViagens;
    private double energiaGastaTotal;
    private int tempoEsperaTotal;

    public ResumoSimulacao() {
        totalPassageirosGerados = 0;
        totalEmbarques = 0;
        totalDesembarques = 0;
        totalViagens = 0;
        energiaGastaTotal = 0;
        tempoEsperaTotal = 0;
    }

    public void registrarPassageiroGerado() {
        totalPassageirosGerados++;
    }

    public void registrarEmbarque(int tempoEspera) {
        totalEmbarques++;
        tempoEsperaTotal += tempoEspera;
    }

    public void registrarDesembarque() {
        totalDesembarques++;
    }

    public void registrarViagem() {
        totalViagens++;
    }

    public void registrarEnergiaGasta(double energia) {
        energiaGastaTotal += energia;
    }

    public double getEnergiaGastaTotal() {
        return energiaGastaTotal;
    }

    public double getTempoEsperaMedio() {
        if (totalEmbarques == 0) return 0.0;
        return (double) tempoEsperaTotal / totalEmbarques;
    }

    public void imprimirResumo() {
        System.out.println("=== RESUMO DA SIMULAÇÃO ===");
        System.out.println("Total de passageiros gerados: " + totalPassageirosGerados);
        System.out.println("Total de embarques realizados: " + totalEmbarques);
        System.out.println("Total de desembarques realizados: " + totalDesembarques);
        System.out.println("Total de viagens realizadas pelos elevadores: " + totalViagens);
        System.out.printf("Energia total consumida: %.2f unidades\n", energiaGastaTotal);
        System.out.printf("Tempo médio de espera dos passageiros: %.2f minutos\n", getTempoEsperaMedio());
        System.out.println("===========================");
    }
}
