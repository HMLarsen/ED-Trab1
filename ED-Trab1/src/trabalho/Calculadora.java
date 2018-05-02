package trabalho;

import trabalho.auxiliares.fila.Fila;
import trabalho.auxiliares.fila.FilaVetor;

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

    }

    public double calcularExprPosFixada(Fila<String> exprPosfixada) {

    }

}
