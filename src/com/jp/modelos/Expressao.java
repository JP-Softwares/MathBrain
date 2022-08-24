/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jp.modelos;

/**
 *
 * @author aluno
 */
public class Expressao {
    private String expressao = "";
    private String[] vetor = new String[20];
    private int[] parentesesAberto = new int[10];
    private int indParAbr = -1;
    private int[] parentesesFechado = new int[10];
    private int indParFec = -1;
    
    
    //construtor
    public Expressao(String expressao) {
        this.expressao = expressao;
    }

    
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
    
    public void gerarVetor(){
        int indice = 0;
        char vetorChar[] = this.expressao.toCharArray();
        for(int i = 0; i <= vetorChar[vetorChar.length-1]; i++){
            if(vetorChar[i] =='('){
                indParAbr++;
                parentesesAberto[indParAbr] = indice;
            }else{
                if(vetorChar[i] =='('){
                    indParFec++;
                    parentesesFechado[indParFec] = indice;
                }else{
                    if(vetorChar[i] == '+' || vetorChar[i] == '×' || vetorChar[i] == '÷' || vetorChar[i] == '^' || vetorChar[i] == '√'){
                        indice++;
                        this.vetor[indice] = vetorChar[i] + "";
                        indice++;
                    }else{
                        if(vetorChar[i] == '-'){
                            indice++;
                            this.vetor[indice] = "-";
                        }else{
                            this.vetor[indice] += vetorChar[i];
                        }
                    }
                }   
            }
            
        }
    }
    
    
    public void resolverOperacao(int indiceInicial, int indiceFinal) {
        for(int i = indiceFinal; i >= indiceInicial; i--){
            if(vetor[i].equals("^")){
                vetor[i-1] = Math.pow(Float.parseFloat(vetor[i-1]),(Float.parseFloat(vetor[i+1]))) + "";
                vetor[i+1] = "1";
                vetor[i] = "×";
                i += 1;
            }else{
                if(vetor[i].equals("√")){
                    vetor[i-1] = Math.pow(Float.parseFloat(vetor[i+1]),(1/(Float.parseFloat(vetor[i-1])))) + "";
                    vetor[i+1] = "1";
                    vetor[i] = "×";
                    i += 1;
                }
            }
            
        }
    }

    public String resolverExpressao(){
        gerarVetor();
        //Resolve os parenteses primeiro
        for(int i = indParAbr; i >= 0; i--){
            for(int j = 0; j <= indParFec; j++ ){
                if(parentesesFechado[j] > parentesesAberto[i]){
                    resolverOperacao(parentesesAberto[i], parentesesFechado[j]);
                    parentesesFechado[j] = -1;
                }
            }
        }

    }
}
