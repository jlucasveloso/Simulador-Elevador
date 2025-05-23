public class Elevador {
    private int id;
    private int andarAtual;
    private int direcao; // -1: descendo, 0: parado, 1: subindo
    private Pessoa[] passageiros;
    private int numPassageiros;
    private int capacidade;
    private int pesoMaximo;
    private int pesoAtual;
    private Configuracao config;
    private ResumoSimulacao resumo;
    private double energiaGastaCiclo;

    public Elevador(int id, Configuracao config, ResumoSimulacao resumo) {
        this.id = id;
        this.config = config;
        this.resumo = resumo;
        this.andarAtual = 0;
        this.direcao = 0;
        this.capacidade = config.capacidadeElevador;
        this.pesoMaximo = config.pesoMaximoElevador;
        this.passageiros = new Pessoa[capacidade];
        this.numPassageiros = 0;
        this.pesoAtual = 0;
        this.energiaGastaCiclo = 0;
    }

    public void atenderProximo(FilaPrioridadeDupla[] filas) {
        energiaGastaCiclo = 0; // Reseta a energia gasta no ciclo
        System.out.println("\n=== Elevador " + id + " ===");
        System.out.println("Andar atual: " + andarAtual + " | Direção: " + getDirecaoTexto());
        System.out.println("Passageiros: " + numPassageiros + "/" + capacidade + " | Peso: " + pesoAtual + "/" + pesoMaximo);

        int desembarcados = desembarcarPassageiros();
        if (desembarcados > 0) {
            resumo.registrarEnergiaGasta(config.consumoEnergiaParada);
            System.out.println("Desembarcaram " + desembarcados + " passageiros no andar " + andarAtual);
        }

        int embarcados = embarcarPassageiros(filas);
        if (embarcados > 0) {
            resumo.registrarEnergiaGasta(config.consumoEnergiaParada);
            System.out.println("Embarcaram " + embarcados + " passageiros no andar " + andarAtual);
        }

        
        if (numPassageiros == 0) {
            int proximoAndar = encontrarProximoAndar(filas);
            if (proximoAndar != -1) {
                moverParaAndar(proximoAndar);
            } else if (proximoAndar == -1) {
                System.out.println("Nenhuma chamada pendente");
            }
        } else {
            
            atualizarDirecao();
            if (direcao != 0) {
                mover();
            }
        }
    }

    private int desembarcarPassageiros() {
        int desembarcados = 0;
        for (int i = 0; i < numPassageiros; i++) {
            if (passageiros[i] != null && passageiros[i].getAndarDestino() == andarAtual) {
                resumo.registrarDesembarque();
                pesoAtual -= passageiros[i].getPeso();
                passageiros[i] = null;
                desembarcados++;
            }
        }
       
        int j = 0;
        for (int i = 0; i < numPassageiros; i++) {
            if (passageiros[i] != null) {
                passageiros[j] = passageiros[i];
                j++;
            }
        }
        numPassageiros = j;
        return desembarcados;
    }

    private int embarcarPassageiros(FilaPrioridadeDupla[] filas) {
        FilaPrioridadeDupla filaAndar = filas[andarAtual];
        int embarcados = 0;
        
        while (numPassageiros < capacidade && (!filaAndar.estaVaziaSubir() || !filaAndar.estaVaziaDescer())) {
            Pessoa p = filaAndar.removerPessoaSubir();
            if (p == null) p = filaAndar.removerPessoaDescer();
            
            if (p != null && pesoAtual + p.getPeso() <= pesoMaximo) {
                passageiros[numPassageiros++] = p;
                pesoAtual += p.getPeso();
                resumo.registrarEmbarque(p.getTempoEspera());
                embarcados++;
            } else {
                filaAndar.inserir(p); // Devolve à fila se não couber
                break;
            }
        }
        return embarcados;
    }

    private int encontrarProximoAndar(FilaPrioridadeDupla[] filas) {
        int melhorAndar = -1;
        int menorDistancia = Integer.MAX_VALUE;
        
        for (int andar = 0; andar < filas.length; andar++) {
            if (filas[andar].temPessoasEsperando()) {
                Pessoa[] subir = filas[andar].getArraySubir();
                Pessoa[] descer = filas[andar].getArrayDescer();
                
                boolean temPrioridade = false;
                for (Pessoa p : subir) {
                    if (p != null && p.isPrioridade()) {
                        temPrioridade = true;
                        break;
                    }
                }
                if (!temPrioridade) {
                    for (Pessoa p : descer) {
                        if (p != null && p.isPrioridade()) {
                            temPrioridade = true;
                            break;
                        }
                    }
                }
                
                if (temPrioridade) {
                    int distancia = Math.abs(andar - andarAtual);
                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        melhorAndar = andar;
                    }
                }
            }
        }
        
        if (melhorAndar == -1) {
            for (int andar = 0; andar < filas.length; andar++) {
                if (filas[andar].temPessoasEsperando()) {
                    int distancia = Math.abs(andar - andarAtual);
                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        melhorAndar = andar;
                    }
                }
            }
        }
        
        return melhorAndar;
    }

    private void atualizarDirecao() {
        if (numPassageiros == 0) {
            direcao = 0; // Parado se vazio
            return;
        }

        boolean temParaCima = false;
        boolean temParaBaixo = false;

        for (int i = 0; i < numPassageiros; i++) {
            if (passageiros[i] != null) {
                if (passageiros[i].getAndarDestino() > andarAtual) {
                    temParaCima = true;
                } else if (passageiros[i].getAndarDestino() < andarAtual) {
                    temParaBaixo = true;
                }
            }
        }

        if (direcao == 0) { // Se parado
            direcao = temParaCima ? 1 : (temParaBaixo ? -1 : 0);
        } else if (direcao == 1 && !temParaCima) { // Se subindo e não tem mais para cima
            direcao = temParaBaixo ? -1 : 0;
        } else if (direcao == -1 && !temParaBaixo) { // Se descendo e não tem mais para baixo
            direcao = temParaCima ? 1 : 0;
        }
    }

    private void mover() {
        if (direcao == 0) return;

        int tempoViagem = config.tempoMinimoViagem + 
                         (int)(Math.random() * (config.tempoMaximoViagem - config.tempoMinimoViagem));
        
        System.out.printf("[ELEVADOR %d] Movendo do andar %d para o andar %d (tempo estimado: %d segundos)\n", 
                         id, andarAtual, andarAtual + direcao, tempoViagem);

        andarAtual += direcao;
        energiaGastaCiclo += config.consumoEnergiaDeslocamento;
        resumo.registrarEnergiaGasta(config.consumoEnergiaDeslocamento);
        resumo.registrarViagem();
    }

    private void moverParaAndar(int destino) {
        int andaresParaMover = Math.abs(destino - andarAtual);
        int tempoTotalViagem = 0;
        
        while (andarAtual != destino) {
            direcao = destino > andarAtual ? 1 : -1;
            
            
            int tempoViagem = config.tempoMinimoViagem + 
                            (int)(Math.random() * (config.tempoMaximoViagem - config.tempoMinimoViagem));
            tempoTotalViagem += tempoViagem;
            
            System.out.printf("[ELEVADOR %d] Movendo do andar %d para o andar %d (tempo estimado: %d segundos)\n", 
                            id, andarAtual, andarAtual + direcao, tempoViagem);
            
            mover();
        }
        
        System.out.printf("[ELEVADOR %d] Chegou ao destino (andar %d) em %d segundos\n", 
                         id, destino, tempoTotalViagem);
        
        // Registra energia gasta ao parar no destino
        energiaGastaCiclo += config.consumoEnergiaParada;
        resumo.registrarEnergiaGasta(config.consumoEnergiaParada);
    }

    
    public int getId() { return id; }
    public int getAndarAtual() { return andarAtual; }
    public int getDirecao() { return direcao; }
    public String getDirecaoTexto() { 
        return direcao == 1 ? "Subindo" : (direcao == -1 ? "Descendo" : "Parado"); 
    }
    public int getNumPassageiros() { return numPassageiros; }
    public int getPesoAtual() { return pesoAtual; }
    public Pessoa[] getPassageiros() { return passageiros; }
    public int getCapacidade() { return capacidade; }
    public int getPesoMaximo() { return pesoMaximo; }
    public double getEnergiaGastaCiclo() {
        return energiaGastaCiclo;
    }
}
