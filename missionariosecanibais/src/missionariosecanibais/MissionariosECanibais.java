import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;

import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;
import javax.swing.JOptionPane;

public class MissionariosECanibais implements Estado {
    
    @Override
    public String getDescricao() {
        return " Problema dos Missionarios e Canibais.... ";
    }
    
    final int mEsq, cEsq, mDir, cDir;
    final char barco; // 'E' para esquerda, 'D' para direita
    final String op; // operacao que gerou o estado
    
    /** construtor para o estado, recebe cada valor de atributo */
    public MissionariosECanibais(int mEsq, int cEsq, int mDir, int cDir, char barco, String op) {
        this.mEsq = mEsq;
        this.cEsq = cEsq;
        this.mDir = mDir;
        this.cDir = cDir;
        this.barco = barco;
        
        this.op = op;
    }
    
    /**
     * verifica se o estado final foi atingido
     */
    @Override
    public boolean ehMeta() {
        // A meta é não ter ninguém na margem esquerda
        return this.mEsq == 0 && this.cEsq == 0 && this.barco == 'D';
    }
    
    /**
     * Custo para geracao do estado
     */
    @Override
    public int custo() {
        return 1;
    }
    
    /**
     * gera uma lista de sucessores do nodo.
     * @return visitados
     */
    @Override
    public List<Estado> sucessores() {
        List<Estado> visitados = new LinkedList<Estado>(); // a lista de sucessores
        HashSet<Estado> visitadosTemp = new HashSet<>();
        
        mover1Missionario(visitadosTemp);
        mover2Missionarios(visitadosTemp);
        mover1Canibal(visitadosTemp);
        mover2Canibais(visitadosTemp);
        mover1Missionario1Canibal(visitadosTemp);
        
        visitados.clear();
        visitados.addAll(visitadosTemp);
        
        return visitados;
    }
    
    private boolean ehValido(int me, int ce, int md, int cd) {
        // 1. Não pode haver número negativo de pessoas
        if (me < 0 || ce < 0 || md < 0 || cd < 0) {
            return false;
        }
        
        // 2. Se há missionários na margem, eles não podem ser minoria
        if ((me > 0 && me < ce) || (md > 0 && md < cd)) {
            return false;
        }
        
        return true;
    }
    
    private void mover1Missionario(HashSet<Estado> visitados) {
        if (this.barco == 'E') {
            if (ehValido(mEsq - 1, cEsq, mDir + 1, cDir)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq - 1, cEsq, mDir + 1, cDir, 'D', "Moveu 1 Missionario para a Direita");
                visitados.add(novo);
            }
        } else {
            if (ehValido(mEsq + 1, cEsq, mDir - 1, cDir)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq + 1, cEsq, mDir - 1, cDir, 'E', "Moveu 1 Missionario para a Esquerda");
                visitados.add(novo);
            }
        }
    }
    
    private void mover2Missionarios(HashSet<Estado> visitados) {
        if (this.barco == 'E') {
            if (ehValido(mEsq - 2, cEsq, mDir + 2, cDir)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq - 2, cEsq, mDir + 2, cDir, 'D', "Moveu 2 Missionarios para a Direita");
                visitados.add(novo);
            }
        } else {
            if (ehValido(mEsq + 2, cEsq, mDir - 2, cDir)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq + 2, cEsq, mDir - 2, cDir, 'E', "Moveu 2 Missionarios para a Esquerda");
                visitados.add(novo);
            }
        }
    }
    
    private void mover1Canibal(HashSet<Estado> visitados) {
        if (this.barco == 'E') {
            if (ehValido(mEsq, cEsq - 1, mDir, cDir + 1)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq, cEsq - 1, mDir, cDir + 1, 'D', "Moveu 1 Canibal para a Direita");
                visitados.add(novo);
            }
        } else {
            if (ehValido(mEsq, cEsq + 1, mDir, cDir - 1)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq, cEsq + 1, mDir, cDir - 1, 'E', "Moveu 1 Canibal para a Esquerda");
                visitados.add(novo);
            }
        }
    }
    
    private void mover2Canibais(HashSet<Estado> visitados) {
        if (this.barco == 'E') {
            if (ehValido(mEsq, cEsq - 2, mDir, cDir + 2)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq, cEsq - 2, mDir, cDir + 2, 'D', "Moveu 2 Canibais para a Direita");
                visitados.add(novo);
            }
        } else {
            if (ehValido(mEsq, cEsq + 2, mDir, cDir - 2)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq, cEsq + 2, mDir, cDir - 2, 'E', "Moveu 2 Canibais para a Esquerda");
                visitados.add(novo);
            }
        }
    }
    
    private void mover1Missionario1Canibal(HashSet<Estado> visitados) {
        if (this.barco == 'E') {
            if (ehValido(mEsq - 1, cEsq - 1, mDir + 1, cDir + 1)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq - 1, cEsq - 1, mDir + 1, cDir + 1, 'D', "Moveu 1 Missionario e 1 Canibal para a Direita");
                visitados.add(novo);
            }
        } else {
            if (ehValido(mEsq + 1, cEsq + 1, mDir - 1, cDir - 1)) {
                MissionariosECanibais novo = new MissionariosECanibais(mEsq + 1, cEsq + 1, mDir - 1, cDir - 1, 'E', "Moveu 1 Missionario e 1 Canibal para a Esquerda");
                visitados.add(novo);
            }
        }
    }
    
    /**
     * verifica se um estado e igual a outro
     * (usado para poda)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MissionariosECanibais) {
            MissionariosECanibais e = (MissionariosECanibais)o;
            return this.mEsq == e.mEsq &&
                   this.cEsq == e.cEsq &&
                   this.barco == e.barco;
        }
        return false;
    }
    
    /** * retorna o hashCode do estado
     * (usado para poda, conjunto de fechados)
     */
    @Override
    public int hashCode() {
        return ("" + this.mEsq + this.cEsq + this.barco).hashCode();
    }
    
    @Override
    public String toString() {
        return "[Esq: " + mEsq + "M," + cEsq + "C | Dir: " + mDir + "M," + cDir + "C | Barco: " + barco + "] -> " + op + "\n";
    }
    
    public static void main(String[] a) {
        int quantidade = 3; // Valor padrão do problema clássico
        
        try {
            String input = JOptionPane.showInputDialog(null, "Quantos missionários e canibais (padrão é 3)?");
            if (input == null) System.exit(0);
            quantidade = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            quantidade = 3;
        }
        
        if (quantidade == 0) System.exit(0);
        
        // Inicia com todos na margem esquerda e barco na esquerda
        MissionariosECanibais estadoInicial = new MissionariosECanibais(quantidade, quantidade, 0, 0, 'E', "estado inicial");
        
        System.out.println("busca em andamento....");
        
        // Utilizando Busca em Largura (garante o menor número de travessias)
        // Você também pode trocar por new BuscaProfundidade() se preferir
        Nodo n = new BuscaLargura(new MostraStatusConsole()).busca(estadoInicial);
        
        if (n == null) {
            System.out.println("sem solucao!");
        } else {
            System.out.println("solucao:\n" + n.montaCaminho() + "\n\n");
        }
    }
}