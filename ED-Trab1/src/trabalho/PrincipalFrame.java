package trabalho;

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

    public interface Fila<T> {

        public void inserir(T valor);

        public boolean estaVazia();

        public T peek();

        public T retirar();

        public void liberar();
    }

    public class FilaCheiaException extends RuntimeException {

        public FilaCheiaException(String m) {
            super(m);
        }
    }

    public class FilaVaziaException extends RuntimeException {

        public FilaVaziaException(String m) {
            super(m);
        }
    }

    public class FilaVetor<T> implements Fila<T> {

        private T[] info;
        private int limite;
        private int tamanho;
        private int inicio;

        public FilaVetor(int limite) {
            info = (T[]) new Object[limite];
            this.limite = limite;
            tamanho = 0;
            inicio = 0;
        }

        public int getLimite() {
            return this.limite;
        }

        public int getTamanho() {
            return this.tamanho;
        }

        @Override
        public void inserir(T valor) {
            if (tamanho == limite) {
                throw new FilaCheiaException("Fila está cheia!");
            }

            int posicaoInserir = (inicio + tamanho) % limite;
            info[posicaoInserir] = valor;
            tamanho++;
        }

        @Override
        public boolean estaVazia() {
            return tamanho == 0;
        }

        @Override
        public T peek() {
            if (estaVazia()) {
                throw new FilaVaziaException("Fila está vazia!");
            }

            return info[inicio];
        }

        @Override
        public T retirar() {
            if (estaVazia()) {
                throw new FilaVaziaException("Fila está vazia!");
            }

            T valor = peek();
            info[inicio] = null;
            inicio = (inicio + 1) % limite;
            tamanho--;
            return valor;
        }

        @Override
        public void liberar() {
            for (int i = 0; i <= tamanho; i++) {
                info[i] = null;
            }

            inicio = 0;
            tamanho = 0;
        }

        public FilaVetor<T> criarFilaConcatenada(FilaVetor<T> f2) {
            FilaVetor<T> f3 = new FilaVetor<>(this.getLimite() + f2.getLimite());

            for (int i = 0; i < tamanho; i++) {
                f3.inserir(info[(inicio + i) % limite]);
            }

            for (int i = 0; i < f2.tamanho; i++) {
                f3.inserir(f2.info[(f2.inicio + i) % f2.limite]);
            }

            return f3;
        }

        @Override
        public String toString() {
            String str = "";

            for (int i = inicio; i < tamanho; i++) {
                if (str.isEmpty()) {
                    str += info[i];
                } else {
                    str += ", " + info[i];
                }
            }

            return str;
        }

    }

    public interface Pilha<T> {

        public void push(T info);

        public T pop();

        public T peek();

        public boolean estaVazia();

        public void liberar();
    }

    public class PilhaCheiaException extends RuntimeException {

        public PilhaCheiaException(String m) {
            super(m);
        }
    }

    public class PilhaVaziaException extends RuntimeException {

        public PilhaVaziaException(String m) {
            super(m);
        }
    }

    public class PilhaVetor<T> implements Pilha<T> {

        private T[] info;
        private int limite;
        private int tamanho;

        public PilhaVetor(int limite) {
            info = ((T[]) new Object[limite]);
            this.limite = limite;
            this.tamanho = 0;
        }

        public int getTamanho() {
            return this.tamanho;
        }

        @Override
        public void push(T info) {
            if (limite == tamanho) {
                throw new PilhaCheiaException("Esgotada capacidade da pilha!");
            }

            this.info[tamanho] = info;
            tamanho++;
        }

        @Override
        public T pop() {
            T backup = peek();
            info[tamanho - 1] = null;
            tamanho--;
            return backup;
        }

        @Override
        public T peek() {
            if (estaVazia()) {
                throw new PilhaVaziaException("Pilha está vazia!");
            }

            return info[tamanho - 1];
        }

        @Override
        public boolean estaVazia() {
            return tamanho == 0;
        }

        @Override
        public void liberar() {
            for (int i = 0; i <= tamanho; i++) {
                info[i] = null;
            }

            tamanho = 0;
        }

        @Override
        public String toString() {
            String str = "";

            for (int i = tamanho - 1; i >= 0; i--) {
                if (i == tamanho - 1) {
                    str += info[i];
                } else {
                    str += ", " + info[i];
                }
            }

            return str;
        }

        public void concatenar(PilhaVetor<T> p) {
            for (int i = 0; i <= p.tamanho - 1; i++) {
                this.push(p.info[i]);
            }
        }
    }

    public class Calculadora {

        public Fila<String> extrairTermos(String expressao) {
            //Diminuir um pouco do conteúdo desnecessário
            expressao = expressao.trim();

            //Pode ser que o limite da fila fique maior que o tamanho propriamente
            //mas não tem problema porque
            //as validações posteriores utilizam o tamanho e não o limite
            //por exemplo: se for o número 25 o limite vai ser 2 mas tamanho vai ser 1
            FilaVetor<String> fila = new FilaVetor<>(expressao.length());

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
            int tamanho = ((FilaVetor) exprInfixada).getTamanho();

            PilhaVetor<String> pilhaB = new PilhaVetor<>(tamanho);
            FilaVetor<String> filaC = new FilaVetor<>(tamanho);

            //Percorrendo fila A
            for (int i = 0; i < tamanho; i++) {
                //Retirando primeiro elemento da fila
                String valorRef = exprInfixada.retirar();

                //Empilhando operadores
                if (valorRef.equals("*")
                        || valorRef.equals("/")
                        || valorRef.equals("+")
                        || valorRef.equals("-")
                        || valorRef.equals("(")) {
                    //Verificar ordem de procedência
                    //se o valor retirado tiver procedência menor que último da pilha
                    //vai adicionar na fica C o último da pila
                    //e continuar empilhando o de menor procedência
                    switch (valorRef) {
                        case "+":
                        case "-":
                            if (!pilhaB.estaVazia()) {
                                if ((pilhaB.peek().equals("*")) || (pilhaB.peek().equals("/"))) {
                                    filaC.inserir(pilhaB.pop());
                                }
                            }

                            break;
                    }

                    pilhaB.push(valorRef);
                } else if (valorRef.equals(")")) {
                    //Em caso de parênteses de fechamento desempilhar tudo da pilha B    
                    while (!pilhaB.estaVazia()) {
                        String dado = pilhaB.pop();

                        //Menos os parênteses de abertura
                        if (!dado.equals("(")) {
                            filaC.inserir(dado);
                        }
                    }
                } else if (!valorRef.equals("(")) {
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
            int tamanho = ((FilaVetor) exprPosfixada).getTamanho();
            PilhaVetor<String> auxiliar = new PilhaVetor<>(tamanho);

            for (int i = 0; i < tamanho; i++) {
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
            Fila<String> termosPosfixada = c.gerarExprPosfixada(termosInfixada);
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
