
import java.util.Random;

public class AlgoritmoGenetico {

    private static int[] tarefas = new int[20];
    private static int[][] popInicial = new int[7][21];
    private static int[][] popIntermediaria = new int[7][21];

    public static void popula() {

        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            tarefas[i] = rand.nextInt(100) + 1;
        }

    }

    public static void inicializaPop() {

        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 20; j++) {
                popInicial[i][j] = rand.nextInt(2);
            }
        }

    }

    public static int funcaoAptidao(int[] cromossomo) {

        int somaZero = 0;
        int somaUm = 0;

        for (int i = 0; i < 20; i++) {
            if (cromossomo[i] == 0) {
                somaZero += tarefas[i];
            } else {
                somaUm += tarefas[i];
            }
        }

        return Math.abs(somaZero - somaUm);
    }

    public static void aptidaoPop() {

        for (int i = 0; i < 7; i++) {
            popInicial[i][20] = funcaoAptidao(popInicial[i]);
        }

    }

    public static void printa() {

        System.out.println("Tarefas: ");
        for (int i = 0; i < 20; i++) {
            System.out.print(tarefas[i] + " ");
        }

        System.out.println("");

        System.out.println("População Atual: ");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 21; j++) {
                System.out.print(popInicial[i][j] + " ");
            }
            System.out.println("");
        }

    }
    public static void elitismo(){
        int numLinha = 0;
        int menor = popInicial[0][20];
        for(int i = 1; i < 7; i++){
            if(popInicial[i][20] < menor){
                menor = popInicial[i][20];
                numLinha = i;
            } 
        }
        
        copia(numLinha, 0);
    }

    public static void copia(int linhaOrigem, int linhaDestino ){
        for(int i = 0;i<20;i++){
            popIntermediaria[linhaDestino][i] = popInicial[linhaOrigem][i];
        }
    }
    
    public static int torneio(){
        Random rand = new Random();
        int c1 = rand.nextInt(7);
        int c2 = rand.nextInt(7);
        
        if(popInicial[c1][20] > popInicial[c2][20]){
            return c2;
        }else{
            return c1;
        }
    }   
    
    public static void cruzamento(){
        for(int x = 1; x < 7;x+=2){
            int pai = torneio();
            int mae = torneio();        
            
            System.out.println("Pai: " + pai);
            System.out.println("Mãe: " + mae);
            
            for(int i =0;i < 10;i++){
                popIntermediaria[x][i] = popInicial[pai][i];
                popIntermediaria[x+1][i] = popInicial[mae][i];
            }
            for(int i =10;i < 20;i++){
                popIntermediaria[x][i] = popInicial[mae][i];
                popIntermediaria[x+1][i] = popInicial[pai][i];
            }
            
            
        }
    }
    
    public static void mutacao(){
        
        Random rand = new Random();
        int linha = rand.nextInt(6)+1;
        int coluna = rand.nextInt(20);
                
        if(popInicial[linha][coluna] == 0){
            popInicial[linha][coluna] = 1;
        } else {
            popInicial[linha][coluna] = 0;
        }
        
        System.out.println("Mutação: "+"Linha:" + linha + " | "+"Coluna:" + coluna);
        
    }
    
    public static void main(String[] args) {

        Random rand = new Random();
        int muta;
        popula();
        inicializaPop();
        for(int geracao = 0; geracao < 1000; geracao++){
            System.out.println("\nGeração: " + geracao);
            aptidaoPop();
            printa();
            elitismo();
            cruzamento();
            popInicial = popIntermediaria;
            muta = rand.nextInt(5);
            if(muta == 0){
                mutacao();
            }
            popIntermediaria = new int[7][21];
        }
        
    }

}
