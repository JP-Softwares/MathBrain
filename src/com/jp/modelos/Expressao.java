/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jp.modelos;
import javax.swing.JOptionPane;

/**
 *
 * @author aluno
 */
public class Expressao {
    private String expressao = "";
    private String[] vetor = new String[20];
    private int[] parentesesAberto = new int[20];
    private int indParAbr = -1;
    private int[] parentesesFechado = new int[20];
    private int indParFec = -1;
    private int indice = 0;
    
    
    //construtor
    public Expressao(String expressao) {
        this.expressao = expressao;
    }

    
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
    
    private void gerarVetor(){
        indice = 0;
        char vetorChar[] = this.expressao.toCharArray();
        for(int i = 0; i < 20; i++){
            vetor[i] = "";
            parentesesAberto[i] = -1;
            parentesesFechado[i] = -1;
        }
        for(int i = 0; i < vetorChar.length; i++){
            if(vetorChar[i] =='('){
                indParAbr++;
                parentesesAberto[indParAbr] = indice;
            }else{
                if(vetorChar[i] ==')'){
                    indParFec++;
                    parentesesFechado[indParFec] = indice;
                }else{
                    if(vetorChar[i] == '×' || vetorChar[i] == '÷' || vetorChar[i] == '^' || vetorChar[i] == '√'){
                        indice++;
                        this.vetor[indice] = vetorChar[i] + "";
                        indice++;
                    }else{
                        if(vetorChar[i] == '-'){
                            indice++;
                            this.vetor[indice] = "-";
                        }else{
                            if(vetorChar[i] == '+') indice++;
                            else this.vetor[indice] += vetorChar[i];
                        }
                    }
                }   
            }
            
        }
    }
    
    
    private void resolverOperacao(int indiceInicial, int indiceFinal) {
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
        for(int i = 0; i<= indice; i++){
            resultado += Float.parseFloat(vetor[i]);
        }
        vetor[indiceInicial] = resultado + "";
        int diferenca = 0;
        diferenca = indiceFinal - indiceInicial;
        for(int i = indiceFinal ; i < indice; i++){
            if(vetor[i] != "0"){
                vetor[i-diferenca] = vetor[i];
            }
            for(int j = 0; j <=indParAbr; j++ ){
                if(parentesesAberto[j] > indiceFinal){
                    parentesesAberto[j] += diferenca*-1;
                }
                if(parentesesFechado[j] > indiceFinal){
                    parentesesFechado[j] += diferenca*-1;
                }
            }
        } 

    }

    public String resolverExpressao() throws Exception{
        gerarVetor();
        boolean execucao = false;
        //Resolve os parenteses primeiro
        while(parentesesFechado[0] != -1 && parentesesAberto[0] != -1){
            int i = parentesesAberto[indParAbr];
            System.out.print(i);
            execucao = false;
            for(int j = 0; j <= indice; j++ ){
                if(parentesesFechado[j] > parentesesAberto[i] || execucao == false){
                    JOptionPane.showMessageDialog(null, "teste");
                    JOptionPane.showMessageDialog(null, "" + i + parentesesFechado[j]);
                    resolverOperacao(i, parentesesFechado[j]);
                    //JOptionPane.showMessageDialog(null, "" + parentesesAberto[i] + parentesesFechado[j]);
                    parentesesFechado[j] = -1;
                    execucao = true;
                }
            }
            indParAbr--;
            JOptionPane.showMessageDialog(null, ""+ indParAbr + "    " + parentesesFechado[0]);
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
