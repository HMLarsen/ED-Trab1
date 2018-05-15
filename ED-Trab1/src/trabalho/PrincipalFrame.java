package trabalho;

import trabalho.fila.Fila;
import trabalho.fila.FilaLista;
import trabalho.pilha.PilhaLista;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Hugo Marcel Larsen e Gabriel Boeing
 */
public class PrincipalFrame extends javax.swing.JFrame {

    public class Calculadora {

        public Fila<String> extrairTermos(String expressao) {
            //Diminuir um pouco do conteúdo desnecessário
            expressao = expressao.trim();

            //Pode ser que o limite da fila fique maior que o tamanho propriamente
            //mas não tem problema porque
            //as validações posteriores utilizam o tamanho e não o limite
            //por exemplo: se for o número 25 o limite vai ser 2 mas tamanho vai ser 1
            FilaLista<String> fila = new FilaLista<>();

            //Essa variável serve para "agrupar" os números
            String numeral = "";

            //Percorrer toda a expressão
            for (int i = 0; i < expressao.length(); i++) {
                char caractere = expressao.charAt(i);

                if ((caractere == '(')
                        || caractere == ')'
                        || caractere == '*'
                        || caractere == '/'
                        || caractere == '+'
                        || caractere == '-') {
                    //Se o numeral não tiver vazio terá que ser adicionado
                    if (!numeral.isEmpty()) {
                        fila.inserir(numeral);
                        numeral = "";
                    }

                    //Se for o sinal negativo temos que verificar se
                    //o próximo caractere é espaço ou número
                    //para incluirmos como um número negativo ou
                    //incluirmos como sendo operador
                    if ((caractere == '-') && (i + 1 < expressao.length())) {
                        if (Character.isDigit(expressao.charAt(i + 1))) {
                            numeral += "-";
                            continue; //vamos continuar o loop para não inserir na fila conforme abaixo
                        }
                    }

                    fila.inserir(Character.toString(caractere));
                } else if (Character.isDigit(caractere)) {
                    numeral += caractere;
                } else if ((caractere == ',') || (caractere == '.')) {
                    numeral += '.';
                }
            }

            //Se ainda tiver o último numeral, adiciona
            if (!numeral.isEmpty()) {
                fila.inserir(numeral);
            }

            return fila;
        }

        public Fila<String> gerarExprPosfixada(Fila<String> exprInfixada) {
            PilhaLista<String> pilhaB = new PilhaLista<>();
            FilaLista<String> filaC = new FilaLista<>();

            //Percorrendo fila A
            while (!exprInfixada.estaVazia()) {
                //Retirando primeiro elemento da fila
                String valorRef = exprInfixada.retirar();

                //Empilhando operadores
                if (valorRef.equals("*")
                        || valorRef.equals("/")
                        || valorRef.equals("+")
                        || valorRef.equals("-")) {
                    while (!pilhaB.estaVazia()) {
                        String dado = pilhaB.peek();

                        //Menos os parênteses de abertura
                        if (dado.equals("(")) {
                            break;
                        } else if (((valorRef.equals("+")) || (valorRef.equals("-")))
                                && ((dado.equals("*")) || (dado.equals("/")))) {
                            break;
                        }

                        filaC.inserir(pilhaB.pop());
                    }

                    pilhaB.push(valorRef);
                } else if (valorRef.equals(")")) {
                    //Em caso de parênteses de fechamento desempilhar tudo da pilha B    
                    while (!pilhaB.estaVazia()) {
                        String dado = pilhaB.pop();

                        //Menos os parênteses de abertura
                        if (dado.equals("(")) {
                            break;
                        } else {
                            filaC.inserir(dado);
                        }
                    }
                } else if (valorRef.equals("(")) {
                    pilhaB.push(valorRef);
                } else {
                    //Se não for nada acima apenas irá jogar na fila C
                    filaC.inserir(valorRef);
                }
            }

            //Se ainda tiver o que desempilhar da pilhaB, adiciona na C
            while (!pilhaB.estaVazia()) {
                filaC.inserir(pilhaB.pop());
            }

            return filaC;
        }

        public double calcularExprPosfixada(Fila<String> exprPosfixada) {
            PilhaLista<String> auxiliar = new PilhaLista<>();

            while (!exprPosfixada.estaVazia()) {
                String dado = exprPosfixada.retirar();
                double n1;
                double n2;

                switch (dado) {
                    case "+":
                        n1 = Double.parseDouble(auxiliar.pop());
                        n2 = Double.parseDouble(auxiliar.pop());
                        auxiliar.push(String.valueOf(n1 + n2));
                        break;
                    case "-":
                        //Aqui necessita ser invertido
                        n1 = Double.parseDouble(auxiliar.pop());
                        n2 = Double.parseDouble(auxiliar.pop());
                        auxiliar.push(String.valueOf(n2 - n1));
                        break;
                    case "*":
                        n1 = Double.parseDouble(auxiliar.pop());
                        n2 = Double.parseDouble(auxiliar.pop());
                        auxiliar.push(String.valueOf(n1 * n2));
                        break;
                    case "/":
                        //Aqui necessita ser invertido
                        n1 = Double.parseDouble(auxiliar.pop());
                        n2 = Double.parseDouble(auxiliar.pop());
                        auxiliar.push(String.valueOf(n2 / n1));
                        break;
                    default:
                        auxiliar.push(dado);
                }
            }

            //Retorna o único dado que possui na fila, o resultado
            return Double.parseDouble(auxiliar.pop());
        }
    }

    public final void centralizarComponente() {
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dw = getSize();
        setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
    }

    public PrincipalFrame() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        centralizarComponente();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfExpressao = new javax.swing.JTextField();
        btCalcular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfResultado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculadora");
        setResizable(false);

        jLabel1.setText("Expressão:");

        btCalcular.setText("Calcular");
        btCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCalcularActionPerformed(evt);
            }
        });

        jLabel2.setText("Resultado:");

        tfResultado.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfExpressao, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(tfResultado)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btCalcular)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfExpressao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCalcular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCalcularActionPerformed
        try {
            Calculadora c = new Calculadora();
            Fila<String> termosInfixada = c.extrairTermos(tfExpressao.getText());
            System.out.println(termosInfixada.toString());
            Fila<String> termosPosfixada = c.gerarExprPosfixada(termosInfixada);
            System.out.println(termosPosfixada.toString());
            double resultado = c.calcularExprPosfixada(termosPosfixada);

            //Formatar o resultado
            DecimalFormat df = new DecimalFormat("#0");
            df.setMaximumFractionDigits(50); //máximo de casas decimais
            tfResultado.setText(df.format(resultado));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Expressão inválida!");
        } finally {
            tfExpressao.requestFocus();
        }
    }//GEN-LAST:event_btCalcularActionPerformed

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCalcular;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField tfExpressao;
    private javax.swing.JTextField tfResultado;
    // End of variables declaration//GEN-END:variables
}
