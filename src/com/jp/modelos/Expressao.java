/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jp.modelos;
//import javax.swing.JOptionPane;


/**
 *
 * @author aluno
 */
public class Expressao {
    private String expressao = "";//Recebe a expressão que vem da tela
    private String[] vetor = new String[30];//recebe a expressão dividida
    private int[] parentesesAberto = new int[30];//Guarda os indices em que os parenteses abertos estão
    private int indParAbr = -1;//Controla qual indice do vetor acima está sendo usado
    private int[] parentesesFechado = new int[30];
    private int indParFec = -1;
    private int indice = 0;//guarda o indice final que foi usado pela expressão após ser dividida no vetor
    
    
    //construtor
    public Expressao(String expressao) {
        this.expressao = expressao;
    }

    
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
    
    private void gerarVetor(){//metodo que divide a expressão no vetor
        indice = 0;
        indParAbr= -1;
        indParFec=-1;
        char vetorChar[] = this.expressao.toCharArray();//transforma a String que vem da tela em um vetor de Chars
        for(int i = 0; i < vetor.length; i++){//coloca elementos nulos em todos os indices dos vetores que vão ser usados
            vetor[i] = "";
            parentesesAberto[i] = -1;
            parentesesFechado[i] = -1;
        }
        for(int i = 0; i < vetorChar.length; i++){//percorre todo o vetor de Chars criado analisando cada caractere
            if(vetorChar[i] =='('){//caso o Char no indice "i" do vetor de Chars seja um "(" eu guardo no vetor relacionado o valor numerico do indice "i"
                indParAbr++;//variavel usada para percorrer os indices dos vetor de parenteses abertos e para ter controle de em que indice do vetor de parenteses abertos esta a informação sobre o ultimo parenteses aberto
                parentesesAberto[indParAbr] = indice;//guarda o indice em que o parenteses foi aberto no indice "indParAbr" do vetor de parenteses abertos
            }else{
                if(vetorChar[i] ==')'){
                    indParFec++;
                    parentesesFechado[indParFec] = indice;
                }else{
                    if(vetorChar[i] == '×' || vetorChar[i] == '÷' || vetorChar[i] == '^' || vetorChar[i] == '√'){//caso seja encontrada alguma dessas operações, eu guardo ela no proximo indice e pulo mais um para esperar o proximo input
                        indice++;
                        this.vetor[indice] = vetorChar[i] + "";
                        indice++;
                    }else{
                        if(vetorChar[i] == '-'){//garante que o numero que vem depois seja guardado como negativo sem precisar guardar o "-" em um indice separado
                            if(i == 0 || vetorChar[i-1] == '×' || vetorChar[i-1] == '÷' || vetorChar[i-1] == '^' || vetorChar[i-1] == '√' ||  vetorChar[i-1] == '(' ||  vetorChar[i-1] == ')') this.vetor[indice] = "-";//caso o menos venha após uma dessas operações, o indice atual ja está vazio
                            else{//caso o caractere anterior não seja uma das expressões eu preciso ir para o proximo indice e fazer com que o valor digitado após seja negativo
                                indice++;
                                this.vetor[indice] = "-";
                            }
                        }else{
                            if(vetorChar[i] == '+') indice++;//apenas separa os numeros digitados antes e depois do "+"
                            else this.vetor[indice] += vetorChar[i];//caso seja digitado um numero, ele apenas é guardado no indice atual
                        }
                    }
                }   
            }
            
        }
    }
    
    
    private void resolverOperacao(int indiceInicial, int indiceFinal)throws Exception {//
        for(int i = indiceFinal; i >= indiceInicial; i--){
            if(vetor[i].equals("^")){
                if(Double.parseDouble(vetor[i+1]) < 1 && Double.parseDouble(vetor[i+1]) > 0 && Double.parseDouble(vetor[i-1]) < 0){//caso a operação seja semelhante a raiz de um numero negativo ela é enviada ao calculo de raiz para melhor tratamento
                     vetor[i] = "√";
                     String aux = "";
                     aux = vetor[i+1];
                     vetor[i+1] = vetor[i-1];
                     vetor[i-1] = aux;
                }else{
                    vetor[i-1] = Math.pow(Double.parseDouble(vetor[i-1]),(Double.parseDouble(vetor[i+1]))) + "";
                    vetor[i+1] = "1";
                    vetor[i] = "×";
                    i += 1;
                }
            }
            if(vetor[i].equals("√")){
                    if(Double.parseDouble(vetor[i+1]) < 0 && Double.parseDouble(vetor[i-1])== (int)(Double.parseDouble(vetor[i-1])) && Integer.parseInt(vetor[i-1])%2 != 0){ 
                        vetor[i+1] = (Double.parseDouble(vetor[i+1]))*-1 + "";
                        vetor[i-1] = (Math.pow(Double.parseDouble(vetor[i+1]),(1/(Double.parseDouble(vetor[i-1])))))*-1 + "";
                        vetor[i+1] = "1";
                        vetor[i] = "×";
                        i += 1;
                    }else{  
                        if(Double.parseDouble(vetor[i-1]) < 1 && Double.parseDouble(vetor[i-1]) > 0 && Double.parseDouble(vetor[i+1]) < 0){
                                double x = 1.0f/Double.parseDouble(vetor[i-1]);
                                int y = (int)(x) % 2;
                                boolean teste = x == Math.round(x);
                                if(teste && y > 0){
                                vetor[i+1] = Double.parseDouble(vetor[i+1])*-1 + "";
                                vetor[i-1] = (Math.pow(Double.parseDouble(vetor[i+1]),(Double.parseDouble(vetor[i-1]))))*-1 + "";
                                vetor[i+1] = "1";
                                vetor[i] = "×";
                                i += 1;
                                }else throw new Exception("Erro!! Operação inválida");
                        }else{
                            vetor[i-1] = Math.pow(Double.parseDouble(vetor[i+1]),(1/(Double.parseDouble(vetor[i-1])))) + "";
                            vetor[i+1] = "1";
                            vetor[i] = "×";
                            i += 1;
                        }
                    }
            }
            
        }
        for( int i = indiceInicial; i < indiceFinal; i++){
                if(vetor[i].equals("×")){
                    vetor[i+1] = (Double.parseDouble(vetor[i-1])*(Double.parseDouble(vetor[i+1]))) + "";
                    vetor[i-1] = "0";
                    vetor[i] = "0";
                    i += 1;
                }else{
                    if(vetor[i].equals("÷")){
                    vetor[i+1] = (Double.parseDouble(vetor[i-1])/(Double.parseDouble(vetor[i+1]))) + "";
                    vetor[i-1] = "0";
                    vetor[i] = "0";
                    i += 1;
                    }
                }
        }
        double resultado = 0;
        for(int i = indiceInicial; i<= indiceFinal; i++){//guarda o resultado da operação em uma variavel e zera todos os outros
            resultado += Double.parseDouble(vetor[i]);
            vetor[i] = "0";
        }
        vetor[indiceInicial] = resultado + "";//guarda o resultado dentro do primeiro indice usado na operação
        int diferenca = 0;
        diferenca = indiceFinal - indiceInicial;
        if(diferenca != 0){
            for(int i = indiceFinal+1 ; i <= indice; i++){//puxa todos os indices depois da operação escolhida pra esquerda de acordo com a quantidade de indices que foram limpos
                vetor[i-diferenca] = vetor[i];
                vetor [i] = "0";
            } 
        }
        for(int j = 0; j <= indice; j++ ){//reorganiza os parenteses para que continuem no local certo caso o indice refenciado seja puxado para a esquerda
            if(parentesesAberto[j] > indiceFinal){
                parentesesAberto[j] += diferenca*-1;
            }
            if(parentesesFechado[j] > indiceFinal){
                parentesesFechado[j] += diferenca*-1;
            }
        }

    }

    public String resolverExpressao() throws Exception{
        gerarVetor();
        boolean execucao = false;
        //Resolve os parenteses primeiro
        while(parentesesFechado[0] != -1){
            execucao = false;
            for(int j = 0; j <= indice; j++ ){
                if(parentesesFechado[j] >= parentesesAberto[indParAbr] && execucao == false){
                    resolverOperacao(parentesesAberto[indParAbr], parentesesFechado[j]);
                    parentesesFechado[j] = -1;
                    execucao = true;
                }
            }
            indParAbr--;
        }
        resolverOperacao(0, indice);
        double resultado = 0;
        for(int i = 0; i<= indice; i++){
            resultado += Double.parseDouble(vetor[i]);
        }
        String saida = "";
        if(resultado == (int) resultado){
            saida = (int) (resultado) + "";
        }else{
            saida = String.format("%.7f", resultado);
            saida = saida.replace(",", ".");
            char limpa[] = saida.toCharArray();
            int i = limpa.length-1;
            saida = "";
            while(i >= 0){
                if((limpa[i] == '0' || limpa[i] == '.' ) && saida.equals(""));
                else{
                    saida = limpa[i] + saida;
                }
                i--;
            }
        }
        if(saida.equals("")) saida = "0";
        if(saida.equals("Infinity") || saida.equals("NaN")) throw new Exception("Erro!! Operação inválida");
        return(saida);
    }
}
