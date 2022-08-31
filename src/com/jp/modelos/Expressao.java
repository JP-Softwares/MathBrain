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
    private String[] vetor = new String[20];//recebe a expressão dividida
    private int[] parentesesAberto = new int[20];//Guarda os indices em que os parenteses abertos estão
    private int indParAbr = -1;//Controla qual indice do vetor acima está sendo usado
    private int[] parentesesFechado = new int[20];
    private int indParFec = -1;
    private int indice = 0;//guarda o indice final que foi usado pela expressão após ser dividida no vetor
    
    
    //construtor
    public Expressao(String expressao) {
        this.expressao = expressao;
    }

    
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
    
    private void gerarVetor(){//primeiro divide a expressão no vetor
        indice = 0;
        indParAbr= -1;
        indParFec=-1;
        char vetorChar[] = this.expressao.toCharArray();//transforma a String que vem da tela em um vetor de Chars
        for(int i = 0; i < 20; i++){//coloca elementos nulos em todos os indices dos vetores que vão ser usados
            vetor[i] = "";
            parentesesAberto[i] = -1;
            parentesesFechado[i] = -1;
        }
        for(int i = 0; i < vetorChar.length; i++){
            if(vetorChar[i] =='('){
                indParAbr++;
                parentesesAberto[indParAbr] = indice;//guarda o indice em que o parenteses começa no indice decidido pelo indParAbr
            }else{
                if(vetorChar[i] ==')'){
                    indParFec++;
                    parentesesFechado[indParFec] = indice;
                }else{
                    if(vetorChar[i] == '×' || vetorChar[i] == '÷' || vetorChar[i] == '^' || vetorChar[i] == '√'){//caso seja encontrada alguma operação que deve ser inserida no vetor
                        indice++;
                        this.vetor[indice] = vetorChar[i] + "";
                        indice++;
                    }else{
                        if(vetorChar[i] == '-'){//garante que o numero que vem depois seja guardado como negativo nem precisar guardar o "-" em um indice separado
                            if(indice == 0 || vetorChar[i-1] == '×' || vetorChar[i-1] == '÷' || vetorChar[i-1] == '^' || vetorChar[i-1] == '√' ||  vetorChar[i-1] == '(' ||  vetorChar[i-1] == ')') this.vetor[indice] = "-";
                            else{
                                indice++;
                                this.vetor[indice] = "-";
                            }
                        }else{
                            if(vetorChar[i] == '+') indice++;//apenas separa os numeros digitados antes e depois do "+"
                            else this.vetor[indice] += vetorChar[i];
                        }
                    }
                }   
            }
            
        }
    }
    
    
    private void resolverOperacao(int indiceInicial, int indiceFinal)throws Exception {
        for(int i = indiceFinal; i >= indiceInicial; i--){
            if(vetor[i].equals("^")){
                if(Float.parseFloat(vetor[i+1]) < 1 && Float.parseFloat(vetor[i+1]) > 0 && Float.parseFloat(vetor[i-1]) < 0){//caso a operação seja semelhante a raiz de um numero negativo ela é enviada ao calculo de raiz para melhor tratamento
                     vetor[i] = "√";
                     String aux = "";
                     aux = vetor[i+1];
                     vetor[i+1] = vetor[i-1];
                     vetor[i-1] = aux;
                     //JOptionPane.showMessageDialog(null, vetor[i+1] + " - "+ vetor[i-1] + " - " + vetor[i]);
                }else{
                    vetor[i-1] = Math.pow(Float.parseFloat(vetor[i-1]),(Float.parseFloat(vetor[i+1]))) + "";
                    vetor[i+1] = "1";
                    vetor[i] = "×";
                    i += 1;
                }
            }
            if(vetor[i].equals("√")){
                    //JOptionPane.showMessageDialog(null, "Entrei aqui");
                    if(Float.parseFloat(vetor[i+1]) < 0 && Float.parseFloat(vetor[i-1])== (int)(Float.parseFloat(vetor[i-1])) && Integer.parseInt(vetor[i-1])%2 != 0){ 
                        vetor[i+1] = (Float.parseFloat(vetor[i+1]))*-1 + "";
                        vetor[i-1] = (Math.pow(Float.parseFloat(vetor[i+1]),(1/(Float.parseFloat(vetor[i-1])))))*-1 + "";
                        vetor[i+1] = "1";
                        vetor[i] = "×";
                        i += 1;
                    }else{  
                        if(Float.parseFloat(vetor[i-1]) < 1 && Float.parseFloat(vetor[i-1]) > 0 && Float.parseFloat(vetor[i+1]) < 0){
                                float x = 1.0f/Float.parseFloat(vetor[i-1]);
                                int y = (int)(x) % 2;
                                boolean teste = x == Math.round(x);
                                if(teste && y > 0){
                                vetor[i+1] = Float.parseFloat(vetor[i+1])*-1 + "";
                                vetor[i-1] = (Math.pow(Float.parseFloat(vetor[i+1]),(Float.parseFloat(vetor[i-1]))))*-1 + "";
                                vetor[i+1] = "1";
                                vetor[i] = "×";
                                i += 1;
                                }else throw new Exception("Erro!! revise sua formatação");
                        }else{
                            vetor[i-1] = Math.pow(Float.parseFloat(vetor[i+1]),(1/(Float.parseFloat(vetor[i-1])))) + "";
                            vetor[i+1] = "1";
                            vetor[i] = "×";
                            i += 1;
                        }
                    }
            }
            
        }
        for( int i = indiceInicial; i < indiceFinal; i++){
                if(vetor[i].equals("×")){
                    vetor[i+1] = (Float.parseFloat(vetor[i-1])*(Float.parseFloat(vetor[i+1]))) + "";
                    vetor[i-1] = "0";
                    vetor[i] = "0";
                    i += 1;
                }else{
                    if(vetor[i].equals("÷")){
                    vetor[i+1] = (Float.parseFloat(vetor[i-1])/(Float.parseFloat(vetor[i+1]))) + "";
                    vetor[i-1] = "0";
                    vetor[i] = "0";
                    i += 1;
                    }
                }
        }
        double resultado = 0;
        for(int i = indiceInicial; i<= indiceFinal; i++){//guarda o resultado da operação em uma variavel e zera todos os outros
            resultado += Float.parseFloat(vetor[i]);
            vetor[i] = "0";
        }
        vetor[indiceInicial] = resultado + "";//guarda o resultado dentro do primeiro indice usado na operação
        int diferenca = 0;
        diferenca = indiceFinal - indiceInicial;
        for(int i = indiceFinal+1 ; i <= indice; i++){//puxa todos os indices depois da operação escolhida pra esquerda de acordo com a quantidade de indices que foram limpos
            vetor[i-diferenca] = vetor[i];
            vetor [i] = "0";
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
            resultado += Float.parseFloat(vetor[i]);
        }
        String saida = "";
        if(resultado == (int) resultado){
            saida = (int) (resultado) + "";
        }else{
            saida = resultado + "";
        }
        if(saida.equals("Infinity") || saida.equals("NaN")) throw new Exception("Erro!! revise sua formatação");
        return(saida);
    }
}
