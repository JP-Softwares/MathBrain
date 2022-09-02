/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.jp.visao;
import com.jp.modelos.Expressao;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.datatransfer.*;

/**
 *
 * @author aluno
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    
    boolean emOperacao = false; // Para saber se está em operação no momento
    boolean virgula = false; // Para saber se já foi clicado na vírgula
    boolean calculado = false;
    boolean parente = false;
    String saida = "0"; // Para contar quantos caracteres de número tem na string
    int contSaida = 0;
    int limiteDeTamanho = 18;
    Expressao MathBrain = null;
    int parenteAbre = 0;
    int parenteFecha = 0;
    Erro JanelaErro = new Erro();
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    public TelaPrincipal() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/jp/imagens/icone_calculadora.png")));
        initComponents();
        this.setLocationRelativeTo(null);
        jLabel1Apagar.setVisible(false);
    }
    
    
    public void atualizarResultado(String saida){
        jLabelCalculo.setText(saida);
    }
    
    public void numero(int numero){
        if(contSaida < limiteDeTamanho){
            calculado();
            if(saida.equals("0")){
                switch(numero){
                    case 0:
                        saida = "0";
                        contSaida--;
                        break;
                    case 1:
                        saida = "1";
                        break;
                    case 2:
                        saida = "2";
                        break;
                    case 3:
                        saida = "3";
                        break;
                    case 4:
                        saida = "4";
                        break;
                    case 5:
                        saida = "5";
                        break;
                    case 6:
                        saida = "6";
                        break;
                    case 7:
                        saida = "7";
                        break;
                    case 8:
                        saida = "8";
                        break;
                    case 9:
                        saida = "9";
                        break;
                }
            }else{
                if(parente && saida.endsWith(")")){
                    saida += "×" +numero;
                    contSaida++;
                }
                else saida += numero;
            }
            atualizarResultado(saida);
            emOperacao = false;
            parente = false;
            contSaida++;
            
            calcular();
            jLabelCalculo.setForeground(new Color(51,255,255));
        }
        tamanhodeCaracteres();
    }
    
    public void calcular(){
        if(saida.contains("+") || saida.contains("-") || saida.contains("×") || saida.contains("÷") || saida.contains("^") || saida.contains("√")){
            MathBrain = new Expressao(saida.replace(",", "."));
            try {
                jLabelResultado.setText(MathBrain.resolverExpressao().replace(".", ","));
            } catch (Exception e) {
                jLabelResultado.setText("");
            }
        }
    }
    
    public void tamanhodeCaracteres(){
        if(contSaida < 8){
            jLabelCalculo.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 48));
        }else{
            if(contSaida < 12){
                jLabelCalculo.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 36));
            }else{
                if(contSaida < 15){
                    jLabelCalculo.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 28));
                }else{
                    if(contSaida < limiteDeTamanho){
                        jLabelCalculo.setFont(new Font("Tahoma", Font.TRUETYPE_FONT, 20));
                    }
                }
            }
        }
    }
    
    public void virgula(){
        if(contSaida < limiteDeTamanho){
            if(!virgula && !emOperacao && !parente){
                char transferir[] = saida.toCharArray();
                if(transferir.length-1 > 0){
                    if(eNumero(transferir[transferir.length-1]) && transferir[transferir.length-1] != ',' && transferir[transferir.length-1] != '(' && transferir[transferir.length-1] != ')'){
                        saida += ",";
                        atualizarResultado(saida);
                        virgula = true;
                    }
                }else{
                    if(transferir[transferir.length-1] != ',' && transferir[transferir.length-1] != '+' && transferir[transferir.length-1] != '-'){
                        saida += ",";
                        atualizarResultado(saida);
                        virgula = true;
                    }
                }
                contSaida++;
            }
            jLabelCalculo.setForeground(new Color(51,255,255));
            tamanhodeCaracteres();
        }
    }
    
    public void operar(char operacao){
        if(contSaida < limiteDeTamanho){
            calculado();
            if(emOperacao){
                emOperacao = false;
                char transferir[] = saida.toCharArray();
                if(saida.endsWith("×-") || saida.endsWith("÷-") || saida.endsWith("^-") || saida.endsWith("2√")){
                    if(saida.endsWith("×2√") || saida.endsWith("÷2√")|| saida.endsWith("^2√")){
                        saida = "";
                        for(int i = 0; i < transferir.length - 3; i++){
                            saida += transferir[i];
                        }
                        contSaida-= 3;
                    }else{
                        if(!saida.equals("2√")){
                            saida = "";
                            for(int i = 0; i < transferir.length - 2; i++){
                                saida += transferir[i];
                            }
                            contSaida-= 2;
                        }
                    }
                }else{
                    if((operacao != '√') && (operacao != '-' || transferir[transferir.length-1] == '-' || transferir[transferir.length-1] == '+')){
                        saida = "";
                        for(int i = 0; i < transferir.length - 1; i++){
                            saida += transferir[i];
                        }
                        contSaida--;
                    }
                    
                    
                    if(operacao == '√' && transferir[transferir.length-1] != '√'){
                        saida += 2;
                        contSaida++;
                    }
                    
                }
                
                /*
                if(saida.equals("-") || saida.equals("x") || saida.equals("÷")){
                    saida = "";
                    for(int i = 0; i < transferir.length - 1; i++){
                        saida += transferir[i];
                    }
                    contSaida--;
                }else{
                    if(saida.contains(" x  - ") || saida.contains(" ÷  - ")){
                        saida = "";
                        for(int i = 0; i < transferir.length - 6; i++){
                            saida += transferir[i];
                        }
                        contSaida-= 6;
                    }else{
                        saida = "";
                        for(int i = 0; i < transferir.length - 3; i++){
                            saida += transferir[i];
                        }
                        contSaida -= 3;
                    }
                }*/

            }
            
            
            if(saida.equals("0") || saida.equals("")){
                switch(operacao){
                    case '+':
                        saida = "+";
                        atualizarResultado(saida);
                        emOperacao = true;
                        virgula = false;
                        tamanhodeCaracteres();
                        break;
                    case '-':
                        saida = "-";
                        atualizarResultado(saida);
                        emOperacao = true;
                        virgula = false;
                        tamanhodeCaracteres();
                        break;
                    case '√':
                        saida = "2√";
                        contSaida = 2;
                        atualizarResultado(saida);
                        emOperacao = true;
                        virgula = false;
                        tamanhodeCaracteres();
                        break;
                }
            }else{
                switch(operacao){
                    case '+':
                        saida+= "+";
                        break;
                    case '-':
                        saida+= "-";
                        break;
                    case '×':
                        if(!saida.endsWith("√")) saida+= "×";
                        break;
                    case '÷':
                        if(!saida.endsWith("√")) saida+= "÷";
                        break;
                    case '^':
                        if(!saida.endsWith("√")) saida+= '^';
                        break;
                    case '√':
                        if(!saida.endsWith("√")) saida +="√";
                        else contSaida--;
                        break;
                }
                contSaida++;
                atualizarResultado(saida);
                emOperacao = true;
                virgula = false;
                tamanhodeCaracteres();
                jLabelCalculo.setForeground(new Color(51,255,255));
            }
            
        }
    }
    
    public void clear(){
        calculado();
        saida = "0";
        parenteAbre = 0;
        parenteFecha = 0;
        contSaida = 0;
        virgula = false;
        emOperacao = false;
        tamanhodeCaracteres();
        atualizarResultado(saida);
        jLabelResultado.setText("");
        jLabelCalculo.setForeground(new Color(51,255,255));
    }
    
    public void apagar(){
        calculado();
        char caractere[] = saida.toCharArray();
        boolean comVirgula = false; // Para saber se o número atual tem vírgula ou não
        boolean comOperacao = false; // Para saber se o número atual tem vírgula ou não
        saida = "";
        
        if(caractere[caractere.length-1] == '('){
            parenteAbre--;
        }else{
            if(caractere[caractere.length-1] == ')'){
                parenteFecha--;
            }
        }
        if(caractere.length <= 1){
            saida = "0";
        }else{
            for(int i = 0; i < caractere.length-1; i++){
                if(caractere[i] == ','){
                    comVirgula = true;
                    
                    if(comOperacao){
                        comOperacao = false;
                    }
                }else{
                    if(!eNumero(caractere[i]) && caractere[i] != '(' && caractere[i] != ')'){
                        comOperacao = true;
                        comVirgula = false;
                    }
                }
                saida+= caractere[i];
            }
            contSaida--;
            
            
            if(eOperacao(caractere[caractere.length-2])){
                emOperacao = true;
            }else{
                emOperacao = false;
                if(virgula && !comVirgula){
                    virgula = false;
                }
            }
        }
        
        if(saida.endsWith("(") || saida.endsWith(")")){
            parente = true;
        }
        
        if(comVirgula){
            virgula = true;
        }
        
        atualizarResultado(saida);
        tamanhodeCaracteres();
        calcular();
        jLabelCalculo.setForeground(new Color(51,255,255));
    }
    
    public void teclar(java.awt.event.KeyEvent evt, String evento){
        char e = evt.getKeyChar();
        
        if(evento.equals("KeyPressed")){
            switch(evt.getKeyCode()){
                case KeyEvent.VK_BACK_SPACE:
                    apagar();
                    jLabel1Apagar.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    jLabel1Apagar.setVisible(true);
                    break;
                case KeyEvent.VK_ENTER:
                    igual();
                    jLabel1Igual.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case KeyEvent.VK_COMMA:
                    virgula();
                    jLabel1Virgula.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case KeyEvent.VK_ESCAPE:
                    clear();
                    jLabel1Clear.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case KeyEvent.VK_CONTROL:
                case KeyEvent.VK_C:
                    StringSelection ss = new StringSelection (jLabelCalculo.getText());
                    clip.setContents (ss, ss);
            }
            
            switch(e){
                case '0':
                    numero(0);
                    jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '1':
                    numero(1);
                    jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '2':
                    numero(2);
                    jLabel12.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '3':
                    numero(3);
                    jLabel13.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '4':
                    numero(4);
                    jLabel14.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '5':
                    numero(5);
                    jLabel15.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '6':
                    numero(6);
                    jLabel16.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '7':
                    numero(7);
                    jLabel17.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '8':
                    numero(8);
                    jLabel18.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '9':
                    numero(9);
                    jLabel19.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '-':
                    operar('-');
                    jLabel1Menos.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '+':
                    operar('+');
                    jLabel1Mais.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '*':
                    operar('×');
                    jLabel1Vezes.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '/':
                    operar('÷');
                    jLabel1Divisao.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case 'p':
                    operar('^');
                    jLabel1Potencia.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case 'r':
                    operar('√');
                    jLabel1Raiz.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case '(':
                    parenteses(0);
                    jLabel1ParentesesAbre.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
                case ')':
                    parenteses(1);
                    jLabel1ParentesesFecha.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
                    break;
            }
        }
        
        if(evento.equals("KeyReleased")){
                switch(e){
                    case '0':
                        jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '1':
                        jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '2':
                        jLabel12.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '3':
                        jLabel13.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '4':
                        jLabel14.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '5':
                        jLabel15.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '6':
                        jLabel16.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '7':
                        jLabel17.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '8':
                        jLabel18.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '9':
                        jLabel19.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '-':
                        jLabel1Menos.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '+':
                        jLabel1Mais.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '*':
                        jLabel1Vezes.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '/':
                        jLabel1Divisao.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case 'p':
                        jLabel1Potencia.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case 'r':
                        jLabel1Raiz.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case '(':
                        jLabel1ParentesesAbre.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case ')':
                        jLabel1ParentesesFecha.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                }
                
                switch(evt.getKeyCode()){
                    case KeyEvent.VK_BACK_SPACE:
                        jLabel1Apagar.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        jLabel1Apagar.setVisible(false);
                        break;
                    case KeyEvent.VK_ENTER:
                        jLabel1Igual.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case KeyEvent.VK_COMMA:
                        jLabel1Virgula.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                    case KeyEvent.VK_ESCAPE:
                        jLabel1Clear.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
                        break;
                }
            }
    }
    
    public void igual(){
        if(parenteAbre == parenteFecha){
            if(saida.contains("0") || saida.contains("1") || saida.contains("2") || saida.contains("3") || saida.contains("4") || saida.contains("5") || saida.contains("6") || saida.contains("7") || saida.contains("8") || saida.contains("9")){
                if(!emOperacao && !calculado){
                    calculado = true;
                    saida = saida.replace(",", ".");
                    MathBrain = new Expressao(saida);

                    try {
                        saida = MathBrain.resolverExpressao().replace(".", ",");
                        contSaida = saida.length();
                        jLabelResultado.setText(saida);
                        calculado();
                    } catch (Exception erro) {
                        contSaida = saida.length();
                        calculado();
                        jLabelCalculo.setForeground(new Color(255,51,51));
                        JanelaErro.setarErro("" + erro.toString().replace("java.lang.Exception: ", "").replace("java.lang.NumberFormatException: empty String", "Revise sua formatação."));
                        //JOptionPane.showMessageDialog(null, erro);
                        saida = saida.replace(".", ",");
                        atualizarResultado(saida);
                    }

                }else{
                    if(emOperacao){
                        JanelaErro.setarErro("Complete a operação.");
                        //JOptionPane.showMessageDialog(null, "Complete a operação.");
                    }else{
                        if(calculado) calculado();
                    }
                }
            }else{
                JanelaErro.setarErro("Digite um número.");
                //JOptionPane.showMessageDialog(null, "Digite um número.");
            }
            
        }else{
            JanelaErro.setarErro("Feche o(os) parentese(s).");
            //JOptionPane.showMessageDialog(null, "Feche o(os) parentese(s).");
        }
    }
    
    public void calculado(){
        if(calculado){
            jLabelCalculo.setText(jLabelResultado.getText());
            jLabelCalculo.setForeground(new Color(51,255,51));
            tamanhodeCaracteres();
            jLabelResultado.setText("");
            calculado = false;
        }
    }
    
    public void parenteses(int parente){
        calculado();
        if(contSaida < limiteDeTamanho){
            this.parente = true;
            if(saida.equals("0")){
                switch(parente){
                    case 0:
                        saida = "(";
                        parenteAbre++;
                        break;
                }
            }else{
                switch(parente){
                    case 0:
                        if(emOperacao) saida += "(";
                        else saida += "×(";
                        parenteAbre++;
                        break;
                    case 1:
                        saida += ")";
                        calcular();
                        parenteFecha++;
                        virgula = false;
                        break;
                }
            }
            jLabelCalculo.setForeground(new Color(51,255,255));
            atualizarResultado(saida);
        }
    }
    
    public boolean eNumero(char vetor){
        if(vetor == '0' || vetor == '1' || vetor == '2' || vetor == '3' || vetor == '4' || vetor == '5' || vetor == '6' || vetor == '7' || vetor == '8' || vetor == '9'){
            return true;
        }
        return false;
    }
    
    public boolean eOperacao(char vetor){
        if(vetor == '+' || vetor == '-' || vetor == '×' || vetor == '÷' || vetor == '^' || vetor == '√'){
            return true;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelParentesesFecha = new javax.swing.JLabel();
        jLabel1ParentesesFecha = new javax.swing.JLabel();
        jLabelParentesesAbre = new javax.swing.JLabel();
        jLabel1ParentesesAbre = new javax.swing.JLabel();
        jLabelCalculo = new javax.swing.JLabel();
        jLabelResultado = new javax.swing.JLabel();
        jLabelApagar = new javax.swing.JLabel();
        jLabel1Apagar = new javax.swing.JLabel();
        jLabellinha = new javax.swing.JLabel();
        jLabelRaiz = new javax.swing.JLabel();
        jLabel1Raiz = new javax.swing.JLabel();
        jLabelPotencia = new javax.swing.JLabel();
        jLabel1Potencia = new javax.swing.JLabel();
        jLabelClear = new javax.swing.JLabel();
        jLabel1Clear = new javax.swing.JLabel();
        jLabelDivisao = new javax.swing.JLabel();
        jLabel1Divisao = new javax.swing.JLabel();
        jLabelVezes = new javax.swing.JLabel();
        jLabel1Vezes = new javax.swing.JLabel();
        jLabelMenos = new javax.swing.JLabel();
        jLabel1Menos = new javax.swing.JLabel();
        jLabelMais = new javax.swing.JLabel();
        jLabel1Mais = new javax.swing.JLabel();
        jLabelIgual = new javax.swing.JLabel();
        jLabel1Igual = new javax.swing.JLabel();
        jLabelVirgula = new javax.swing.JLabel();
        jLabel1Virgula = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel0 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabelFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MathBrain");
        setMinimumSize(new java.awt.Dimension(285, 485));
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabelParentesesFecha.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelParentesesFecha.setForeground(new java.awt.Color(255, 255, 255));
        jLabelParentesesFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelParentesesFecha.setText(")");
        jLabelParentesesFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelParentesesFechaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelParentesesFechaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelParentesesFechaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelParentesesFechaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelParentesesFechaMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelParentesesFecha);
        jLabelParentesesFecha.setBounds(70, 90, 50, 50);

        jLabel1ParentesesFecha.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1ParentesesFecha.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1ParentesesFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1ParentesesFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1ParentesesFecha);
        jLabel1ParentesesFecha.setBounds(70, 90, 50, 50);

        jLabelParentesesAbre.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelParentesesAbre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelParentesesAbre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelParentesesAbre.setText("(");
        jLabelParentesesAbre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelParentesesAbreMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelParentesesAbreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelParentesesAbreMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelParentesesAbreMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelParentesesAbreMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelParentesesAbre);
        jLabelParentesesAbre.setBounds(20, 90, 50, 50);

        jLabel1ParentesesAbre.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1ParentesesAbre.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1ParentesesAbre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1ParentesesAbre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1ParentesesAbre);
        jLabel1ParentesesAbre.setBounds(20, 90, 50, 50);

        jLabelCalculo.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabelCalculo.setForeground(new java.awt.Color(51, 255, 255));
        jLabelCalculo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCalculo.setText("0");
        getContentPane().add(jLabelCalculo);
        jLabelCalculo.setBounds(10, 10, 250, 60);

        jLabelResultado.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelResultado.setForeground(new java.awt.Color(204, 204, 204));
        jLabelResultado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(jLabelResultado);
        jLabelResultado.setBounds(10, 60, 250, 50);

        jLabelApagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/delet 1.png"))); // NOI18N
        jLabelApagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelApagarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelApagarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelApagarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelApagarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelApagarMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelApagar);
        jLabelApagar.setBounds(210, 90, 50, 50);

        jLabel1Apagar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Apagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Apagar);
        jLabel1Apagar.setBounds(210, 90, 50, 50);

        jLabellinha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabellinha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/barrinha (5).png"))); // NOI18N
        getContentPane().add(jLabellinha);
        jLabellinha.setBounds(10, 130, 250, 20);

        jLabelRaiz.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelRaiz.setForeground(new java.awt.Color(51, 255, 255));
        jLabelRaiz.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRaiz.setText("√");
        jLabelRaiz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRaizMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelRaizMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelRaizMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelRaizMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelRaizMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelRaiz);
        jLabelRaiz.setBounds(140, 150, 50, 50);

        jLabel1Raiz.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Raiz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Raiz);
        jLabel1Raiz.setBounds(140, 150, 50, 50);

        jLabelPotencia.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelPotencia.setForeground(new java.awt.Color(51, 255, 255));
        jLabelPotencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPotencia.setText("^");
        jLabelPotencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPotenciaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelPotenciaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelPotenciaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelPotenciaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelPotenciaMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelPotencia);
        jLabelPotencia.setBounds(80, 150, 50, 50);

        jLabel1Potencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Potencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Potencia);
        jLabel1Potencia.setBounds(80, 150, 50, 50);

        jLabelClear.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelClear.setForeground(new java.awt.Color(255, 75, 75));
        jLabelClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelClear.setText("C");
        jLabelClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelClearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelClearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelClearMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelClearMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelClearMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelClear);
        jLabelClear.setBounds(20, 150, 50, 50);

        jLabel1Clear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Clear);
        jLabel1Clear.setBounds(20, 150, 50, 50);

        jLabelDivisao.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelDivisao.setForeground(new java.awt.Color(51, 255, 255));
        jLabelDivisao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDivisao.setText("÷");
        jLabelDivisao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDivisaoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelDivisaoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelDivisaoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDivisaoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelDivisaoMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelDivisao);
        jLabelDivisao.setBounds(200, 150, 50, 50);

        jLabel1Divisao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Divisao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Divisao);
        jLabel1Divisao.setBounds(200, 150, 50, 50);

        jLabelVezes.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelVezes.setForeground(new java.awt.Color(51, 255, 255));
        jLabelVezes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVezes.setText("×");
        jLabelVezes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelVezesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelVezesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelVezesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelVezesMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelVezesMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelVezes);
        jLabelVezes.setBounds(200, 210, 50, 50);

        jLabel1Vezes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Vezes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Vezes);
        jLabel1Vezes.setBounds(200, 210, 50, 50);

        jLabelMenos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelMenos.setForeground(new java.awt.Color(51, 255, 255));
        jLabelMenos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMenos.setText("-");
        jLabelMenos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelMenosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelMenosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelMenosMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelMenosMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelMenosMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelMenos);
        jLabelMenos.setBounds(200, 270, 50, 50);

        jLabel1Menos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Menos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Menos);
        jLabel1Menos.setBounds(200, 270, 50, 50);

        jLabelMais.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelMais.setForeground(new java.awt.Color(51, 255, 255));
        jLabelMais.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMais.setText("+");
        jLabelMais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelMaisMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelMaisMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelMaisMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelMaisMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelMaisMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelMais);
        jLabelMais.setBounds(200, 330, 50, 50);

        jLabel1Mais.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Mais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Mais);
        jLabel1Mais.setBounds(200, 330, 50, 50);

        jLabelIgual.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelIgual.setForeground(new java.awt.Color(51, 255, 255));
        jLabelIgual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelIgual.setText("=");
        jLabelIgual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelIgualMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelIgualMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelIgualMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelIgualMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelIgualMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelIgual);
        jLabelIgual.setBounds(200, 390, 50, 50);

        jLabel1Igual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Igual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Igual);
        jLabel1Igual.setBounds(200, 390, 50, 50);

        jLabelVirgula.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelVirgula.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVirgula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVirgula.setText(",");
        jLabelVirgula.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelVirgula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelVirgulaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelVirgulaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelVirgulaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelVirgulaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelVirgulaMouseReleased(evt);
            }
        });
        getContentPane().add(jLabelVirgula);
        jLabelVirgula.setBounds(140, 390, 50, 50);

        jLabel1Virgula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1Virgula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel1Virgula);
        jLabel1Virgula.setBounds(140, 390, 50, 50);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("9");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel9MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel9);
        jLabel9.setBounds(140, 210, 50, 50);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel19);
        jLabel19.setBounds(140, 210, 50, 50);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("8");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel8MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel8MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel8);
        jLabel8.setBounds(80, 210, 50, 50);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel18);
        jLabel18.setBounds(80, 210, 50, 50);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("7");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel7MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 210, 50, 50);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel17);
        jLabel17.setBounds(20, 210, 50, 50);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("6");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel6MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel6);
        jLabel6.setBounds(140, 270, 50, 50);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel16);
        jLabel16.setBounds(140, 270, 50, 50);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("5");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel5MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(80, 270, 50, 50);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel15);
        jLabel15.setBounds(80, 270, 50, 50);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("4");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel4MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 270, 50, 50);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel14);
        jLabel14.setBounds(20, 270, 50, 50);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("3");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel3MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(140, 330, 50, 50);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel13);
        jLabel13.setBounds(140, 330, 50, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("2");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel2MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(80, 330, 50, 50);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel12);
        jLabel12.setBounds(80, 330, 50, 50);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("1");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 330, 50, 50);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel11);
        jLabel11.setBounds(20, 330, 50, 50);

        jLabel0.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel0.setForeground(new java.awt.Color(255, 255, 255));
        jLabel0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel0.setText("0");
        jLabel0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel0MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel0MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel0MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel0MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel0MouseReleased(evt);
            }
        });
        getContentPane().add(jLabel0);
        jLabel0.setBounds(80, 390, 50, 50);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png"))); // NOI18N
        getContentPane().add(jLabel10);
        jLabel10.setBounds(80, 390, 50, 50);

        jLabelFundo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jp/imagens/fundo.png"))); // NOI18N
        getContentPane().add(jLabelFundo);
        jLabelFundo.setBounds(0, 0, 270, 450);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MouseClicked
        // TODO add your handling code here:
        numero(0);
    }//GEN-LAST:event_jLabel0MouseClicked

    private void jLabel0MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MouseEntered
        // TODO add your handling code here:
        jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel0MouseEntered

    private void jLabel0MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MouseExited
        // TODO add your handling code here:
        jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel0MouseExited

    private void jLabel0MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MousePressed
        // TODO add your handling code here:
        jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel0MousePressed

    private void jLabel0MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel0MouseReleased
        // TODO add your handling code here:
        jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel0MouseReleased

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        numero(1);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
        jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        // TODO add your handling code here:
        jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
        jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
        jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel1MouseReleased

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        numero(2);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
        jLabel12.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        // TODO add your handling code here:
        jLabel12.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        // TODO add your handling code here:
        jLabel12.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseReleased
        // TODO add your handling code here:
        jLabel12.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel2MouseReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        numero(3);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        jLabel13.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
        jLabel13.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        // TODO add your handling code here:
        jLabel13.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseReleased
        // TODO add your handling code here:
        jLabel13.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel3MouseReleased

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        numero(4);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        jLabel14.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        jLabel14.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
        // TODO add your handling code here:
        jLabel14.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel4MousePressed

    private void jLabel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseReleased
        // TODO add your handling code here:
        jLabel14.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel4MouseReleased

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        numero(5);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
        jLabel15.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        // TODO add your handling code here:
        jLabel15.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        // TODO add your handling code here:
        jLabel15.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel5MousePressed

    private void jLabel5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseReleased
        // TODO add your handling code here:
        jLabel15.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel5MouseReleased

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        numero(6);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        jLabel16.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        jLabel16.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MousePressed
        // TODO add your handling code here:
        jLabel16.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel6MousePressed

    private void jLabel6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseReleased
        // TODO add your handling code here:
        jLabel16.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel6MouseReleased

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        numero(7);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        jLabel17.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        jLabel17.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        // TODO add your handling code here:
        jLabel17.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel7MousePressed

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
        // TODO add your handling code here:
        jLabel17.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        numero(8);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // TODO add your handling code here:
        jLabel18.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        // TODO add your handling code here:
        jLabel18.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel8MouseExited

    private void jLabel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MousePressed
        // TODO add your handling code here:
        jLabel18.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel8MousePressed

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
        // TODO add your handling code here:
        jLabel18.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        numero(9);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here:
        jLabel19.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
        jLabel19.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed
        // TODO add your handling code here:
        jLabel19.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabel9MousePressed

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
        // TODO add your handling code here:
        jLabel19.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jLabelVirgulaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVirgulaMouseClicked
        // TODO add your handling code here:
        virgula();
    }//GEN-LAST:event_jLabelVirgulaMouseClicked

    private void jLabelVirgulaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVirgulaMouseEntered
        // TODO add your handling code here:
        jLabel1Virgula.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelVirgulaMouseEntered

    private void jLabelVirgulaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVirgulaMouseExited
        // TODO add your handling code here:
        jLabel1Virgula.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelVirgulaMouseExited

    private void jLabelVirgulaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVirgulaMousePressed
        // TODO add your handling code here:
        jLabel1Virgula.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelVirgulaMousePressed

    private void jLabelVirgulaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVirgulaMouseReleased
        // TODO add your handling code here:
        jLabel1Virgula.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelVirgulaMouseReleased

    private void jLabelIgualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelIgualMouseClicked
        // TODO add your handling code here:
       igual();
    }//GEN-LAST:event_jLabelIgualMouseClicked

    private void jLabelIgualMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelIgualMouseEntered
        // TODO add your handling code here:
        jLabel1Igual.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelIgualMouseEntered

    private void jLabelIgualMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelIgualMouseExited
        // TODO add your handling code here:
        jLabel1Igual.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelIgualMouseExited

    private void jLabelIgualMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelIgualMousePressed
        // TODO add your handling code here:
        jLabel1Igual.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelIgualMousePressed

    private void jLabelIgualMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelIgualMouseReleased
        // TODO add your handling code here:
        jLabel1Igual.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelIgualMouseReleased

    private void jLabelMaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMaisMouseClicked
        // TODO add your handling code here:
        operar('+');
    }//GEN-LAST:event_jLabelMaisMouseClicked

    private void jLabelMaisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMaisMouseEntered
        // TODO add your handling code here:
        jLabel1Mais.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelMaisMouseEntered

    private void jLabelMaisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMaisMouseExited
        // TODO add your handling code here:
        jLabel1Mais.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelMaisMouseExited

    private void jLabelMaisMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMaisMousePressed
        // TODO add your handling code here:
        jLabel1Mais.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelMaisMousePressed

    private void jLabelMaisMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMaisMouseReleased
        // TODO add your handling code here:
        jLabel1Mais.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelMaisMouseReleased

    private void jLabelMenosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenosMouseClicked
        // TODO add your handling code here:
        operar('-');
    }//GEN-LAST:event_jLabelMenosMouseClicked

    private void jLabelMenosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenosMouseEntered
        // TODO add your handling code here:
        jLabel1Menos.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelMenosMouseEntered

    private void jLabelMenosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenosMouseExited
        // TODO add your handling code here:
        jLabel1Menos.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelMenosMouseExited

    private void jLabelMenosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenosMousePressed
        // TODO add your handling code here:
        jLabel1Menos.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelMenosMousePressed

    private void jLabelMenosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenosMouseReleased
        // TODO add your handling code here:
        jLabel1Menos.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelMenosMouseReleased

    private void jLabelVezesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVezesMouseClicked
        // TODO add your handling code here:
        operar('×');
    }//GEN-LAST:event_jLabelVezesMouseClicked

    private void jLabelVezesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVezesMouseEntered
        // TODO add your handling code here:
        jLabel1Vezes.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelVezesMouseEntered

    private void jLabelVezesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVezesMouseExited
        // TODO add your handling code here:
        jLabel1Vezes.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelVezesMouseExited

    private void jLabelVezesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVezesMousePressed
        // TODO add your handling code here:
        jLabel1Vezes.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelVezesMousePressed

    private void jLabelVezesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelVezesMouseReleased
        // TODO add your handling code here:
        jLabel1Vezes.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelVezesMouseReleased

    private void jLabelDivisaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDivisaoMouseClicked
        // TODO add your handling code here:
        operar('÷');
    }//GEN-LAST:event_jLabelDivisaoMouseClicked

    private void jLabelDivisaoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDivisaoMouseEntered
        // TODO add your handling code here:
        jLabel1Divisao.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelDivisaoMouseEntered

    private void jLabelDivisaoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDivisaoMouseExited
        // TODO add your handling code here:
        jLabel1Divisao.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelDivisaoMouseExited

    private void jLabelDivisaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDivisaoMousePressed
        // TODO add your handling code here:
        jLabel1Divisao.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelDivisaoMousePressed

    private void jLabelDivisaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDivisaoMouseReleased
        // TODO add your handling code here:
        jLabel1Divisao.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelDivisaoMouseReleased

    private void jLabelClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClearMouseClicked
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jLabelClearMouseClicked

    private void jLabelClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClearMouseEntered
        // TODO add your handling code here:
        jLabel1Clear.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelClearMouseEntered

    private void jLabelClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClearMouseExited
        // TODO add your handling code here:
        jLabel1Clear.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelClearMouseExited

    private void jLabelClearMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClearMousePressed
        // TODO add your handling code here:
        jLabel1Clear.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelClearMousePressed

    private void jLabelClearMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelClearMouseReleased
        // TODO add your handling code here:
        jLabel1Clear.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelClearMouseReleased

    private void jLabelPotenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPotenciaMouseClicked
        // TODO add your handling code here:
        operar('^');
    }//GEN-LAST:event_jLabelPotenciaMouseClicked

    private void jLabelPotenciaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPotenciaMouseEntered
        // TODO add your handling code here:
        jLabel1Potencia.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelPotenciaMouseEntered

    private void jLabelPotenciaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPotenciaMouseExited
        // TODO add your handling code here:
        jLabel1Potencia.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelPotenciaMouseExited

    private void jLabelPotenciaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPotenciaMousePressed
        // TODO add your handling code here:
        jLabel1Potencia.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelPotenciaMousePressed

    private void jLabelPotenciaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPotenciaMouseReleased
        // TODO add your handling code here:
        jLabel1Potencia.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelPotenciaMouseReleased

    private void jLabelRaizMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRaizMouseClicked
        // TODO add your handling code here:
        operar('√');
    }//GEN-LAST:event_jLabelRaizMouseClicked

    private void jLabelRaizMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRaizMouseEntered
        // TODO add your handling code here:
        jLabel1Raiz.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelRaizMouseEntered

    private void jLabelRaizMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRaizMouseExited
        // TODO add your handling code here:
        jLabel1Raiz.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelRaizMouseExited

    private void jLabelRaizMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRaizMousePressed
        // TODO add your handling code here:
        jLabel1Raiz.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelRaizMousePressed

    private void jLabelRaizMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRaizMouseReleased
        // TODO add your handling code here:
        jLabel1Raiz.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelRaizMouseReleased

    private void jLabelApagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelApagarMouseClicked
        // TODO add your handling code here:
        apagar();
    }//GEN-LAST:event_jLabelApagarMouseClicked

    private void jLabelApagarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelApagarMouseEntered
        // TODO add your handling code here:
        jLabel1Apagar.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
        jLabel1Apagar.setVisible(true);
    }//GEN-LAST:event_jLabelApagarMouseEntered

    private void jLabelApagarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelApagarMouseExited
        // TODO add your handling code here:
        jLabel1Apagar.setVisible(false);
    }//GEN-LAST:event_jLabelApagarMouseExited

    private void jLabelApagarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelApagarMousePressed
        // TODO add your handling code here:
        jLabel1Apagar.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelApagarMousePressed

    private void jLabelApagarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelApagarMouseReleased
        // TODO add your handling code here:
        jLabel1Apagar.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelApagarMouseReleased

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
        teclar(evt, "KeyTyped");
    }//GEN-LAST:event_formKeyTyped

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        teclar(evt, "KeyPressed");
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        teclar(evt, "KeyReleased");
    }//GEN-LAST:event_formKeyReleased

    private void jLabelParentesesAbreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesAbreMouseClicked
        // TODO add your handling code here:
        parenteses(0);
    }//GEN-LAST:event_jLabelParentesesAbreMouseClicked

    private void jLabelParentesesAbreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesAbreMouseEntered
        // TODO add your handling code here:
        jLabel1ParentesesAbre.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelParentesesAbreMouseEntered

    private void jLabelParentesesAbreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesAbreMouseExited
        // TODO add your handling code here:
        jLabel1ParentesesAbre.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelParentesesAbreMouseExited

    private void jLabelParentesesAbreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesAbreMousePressed
        // TODO add your handling code here:
        jLabel1ParentesesAbre.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelParentesesAbreMousePressed

    private void jLabelParentesesAbreMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesAbreMouseReleased
        // TODO add your handling code here:
        jLabel1ParentesesAbre.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelParentesesAbreMouseReleased

    private void jLabelParentesesFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesFechaMouseClicked
        // TODO add your handling code here:
        parenteses(1);
    }//GEN-LAST:event_jLabelParentesesFechaMouseClicked

    private void jLabelParentesesFechaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesFechaMouseEntered
        // TODO add your handling code here:
        jLabel1ParentesesFecha.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelParentesesFechaMouseEntered

    private void jLabelParentesesFechaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesFechaMouseExited
        // TODO add your handling code here:
        jLabel1ParentesesFecha.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Normal.png")));
    }//GEN-LAST:event_jLabelParentesesFechaMouseExited

    private void jLabelParentesesFechaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesFechaMousePressed
        // TODO add your handling code here:
        jLabel1ParentesesFecha.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/clique.png")));
    }//GEN-LAST:event_jLabelParentesesFechaMousePressed

    private void jLabelParentesesFechaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelParentesesFechaMouseReleased
        // TODO add your handling code here:
        jLabel1ParentesesFecha.setIcon(new ImageIcon(getClass().getResource("/com/jp/imagens/Mouse.png")));
    }//GEN-LAST:event_jLabelParentesesFechaMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel1Apagar;
    private javax.swing.JLabel jLabel1Clear;
    private javax.swing.JLabel jLabel1Divisao;
    private javax.swing.JLabel jLabel1Igual;
    private javax.swing.JLabel jLabel1Mais;
    private javax.swing.JLabel jLabel1Menos;
    private javax.swing.JLabel jLabel1ParentesesAbre;
    private javax.swing.JLabel jLabel1ParentesesFecha;
    private javax.swing.JLabel jLabel1Potencia;
    private javax.swing.JLabel jLabel1Raiz;
    private javax.swing.JLabel jLabel1Vezes;
    private javax.swing.JLabel jLabel1Virgula;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelApagar;
    private javax.swing.JLabel jLabelCalculo;
    private javax.swing.JLabel jLabelClear;
    private javax.swing.JLabel jLabelDivisao;
    private javax.swing.JLabel jLabelFundo;
    private javax.swing.JLabel jLabelIgual;
    private javax.swing.JLabel jLabelMais;
    private javax.swing.JLabel jLabelMenos;
    private javax.swing.JLabel jLabelParentesesAbre;
    private javax.swing.JLabel jLabelParentesesFecha;
    private javax.swing.JLabel jLabelPotencia;
    private javax.swing.JLabel jLabelRaiz;
    private javax.swing.JLabel jLabelResultado;
    private javax.swing.JLabel jLabelVezes;
    private javax.swing.JLabel jLabelVirgula;
    private javax.swing.JLabel jLabellinha;
    // End of variables declaration//GEN-END:variables
}
