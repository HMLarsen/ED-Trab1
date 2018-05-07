package trabalho;

import trabalho.auxiliares.fila.Fila;
import trabalho.auxiliares.fila.FilaVetor;
import trabalho.auxiliares.pilha.PilhaVetor;

/**
 *
 * @author hugo
 */
public class Calculadora {

    public Fila<String> extrairTermos(String expressao) {
        expressao = expressao.trim();
        FilaVetor<String> fila = new FilaVetor<>(expressao.length());

        for (int i = 0; i <= expressao.getBytes().length; i++) {
            char caractere = expressao.charAt(i);
            String numeral = "";

            if ((expressao.charAt(i) == '(')
                    || caractere == ')'
                    || caractere == '*'
                    || caractere == '/'
                    || caractere == '+'
                    || caractere == '-') {
                if (!numeral.isEmpty()) {
                    fila.inserir(numeral);
                    numeral = "";
                }
                
                fila.inserir(Character.toString(expressao.charAt(i)));
            } else if (Character.isDigit(caractere)) {
                numeral += caractere;
            } else if ((caractere == ',') || (caractere == '.')) {
                numeral += '.';
            }
        }
        
        return fila;
    }

    public Fila<String> gerarExprPosfixada(Fila<String> exprInfixada) {
        PilhaVetor<String> pilhaB = new PilhaVetor<>(exprInfixada.hashCode());
        FilaVetor<String> filaC = new FilaVetor<>(exprInfixada.hashCode());
        
        // Valor de referencia contendo conteudo da Fila A
        String valorRef = "";
        // Variável para desimpilhamento da Pilha B
        String tiraPilha = "";
        
        // Percorrendo Fila A
        for (int i = 0; i < exprInfixada.hashCode(); i++) {
            //Retirando primeiro elemento da fila
            valorRef = exprInfixada.peek();
            //Fazendo a fila andar
            exprInfixada.retirar();
            
            //Empilhando operadores
            if (valorRef == "*" || valorRef == "/" || valorRef == "+" || valorRef == "-") {
                pilhaB.push(valorRef);
                
            //Em caso de parenteses de fechamento desempilhar Pilha B    
            } else if (valorRef == ")") {
                for (int j = 0; j < pilhaB.hashCode(); j++) {
                    tiraPilha = pilhaB.peek();
                    pilhaB.pop();
                    filaC.inserir(tiraPilha);
                    tiraPilha = "";
                }
                
            //Inserindo operador na Fila C
            } else if (valorRef != null){
                filaC.inserir(valorRef);
            }
            
            
            valorRef = "";
        }
        
        return filaC;
        
    }

    public double calcularExprPosFixada(Fila<String> exprPosfixada) {

    }

}
