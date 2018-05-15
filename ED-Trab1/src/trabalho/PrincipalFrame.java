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

    public class NoListaDupla<T> {

        private T info;
        private NoListaDupla proximo;
        private NoListaDupla anterior;

        public NoListaDupla(T info) {
            this.info = info;
        }

        public T getInfo() {
            return info;
        }

        public void setInfo(T info) {
            this.info = info;
        }

        public NoListaDupla<T> getProximo() {
            return proximo;
        }

        public void setProximo(NoListaDupla<T> proximo) {
            this.proximo = proximo;
        }

        public NoListaDupla<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(NoListaDupla<T> anterior) {
            this.anterior = anterior;
        }
    }

    public class ListaVaziaException extends Exception {

        public ListaVaziaException(String msg) {
            super(msg);
        }
    }

    public class Lista<T> {

        //ATRIBUTOS
        protected NoListaDupla<T> primeiro;
        protected NoListaDupla<T> ultimo;

        //CONSTRUTOR
        //INICIALIZA UMA LISTA VAZIA
        public Lista() {
            primeiro = null;
        }

        //INSERE UM NÓ NO IN�?CIO DA LISTA
        public void inserirNoInicio(T valor) {
            //CRIA UM OBJETO E ATRIBUI O VALOR DO PARÂMETRO A ELE
            NoListaDupla<T> novo = new NoListaDupla<>(valor);
            novo.setProximo(primeiro);
            primeiro = novo;
        }

        //INSERE UM NÓ NO FINAL DA LISTA
        public void inserirNoFinal(T valor) {
            NoListaDupla<T> novo = new NoListaDupla<>(valor);
            novo.setProximo(null);

            if (estaVazia()) {
                primeiro = novo;
            } else {
                ultimo.setProximo(novo);
            }
            ultimo = novo;
        }

        //Deve remover o primeiro nó que for encontrado que contiver o dado fornecido como argumento.
        public void retirar(T info) {
            NoListaDupla<T> anterior = null;
            NoListaDupla<T> p = primeiro;

            while ((p != null) && (p.getInfo() != info)) {
                anterior = p;
                p = p.getProximo();
            }

            if (p != null) {
                if (anterior == null) {
                    this.primeiro = p.getProximo();
                } else {
                    anterior.setProximo(p.getProximo());
                }

                if (p == ultimo) {
                    ultimo = anterior;
                }
            }
        }

        //Deve exibir o conteúdo armazenado nos nós da lista encadeada.
        public String exibir() {
            String saida = "";
            NoListaDupla p = primeiro;
            while (p != null) {
                saida = saida + p.getInfo() + "\n";
                p = p.getProximo();
            }
            return saida;
        }

        //Deve retornar true se a lista estiver vazia ou false se tiver algum nó.
        public boolean estaVazia() {
            return primeiro == null;
        }

        //Deve procurar na lista encadeada se há um nó cujo conteúdo seja igual à variável info. 
        //Caso seja localizado, deverá retornar este nó (objeto da classe NoListaDupla). Se não for localizado, deverá retornar null.
        public NoListaDupla buscar(T info) {
            NoListaDupla p = primeiro;
            while (p != null) {
                if (info == p.getInfo()) {
                    return p;
                }
                p = p.getProximo();
            }
            return null;
        }

        public void liberar() {
            NoListaDupla<T> p = primeiro;
            NoListaDupla<T> anterior;
            while (p != null) {
                anterior = p.getProximo();
                p.setAnterior(null);
                p.setProximo(null);

                p = anterior;
            }
            this.primeiro = null;
        }

        //Deverá retornar a quantidade de nós encadeados na lista. 
        //Implemente este método sem criar nova variável de instância na classe Lista.
        public int obterComprimento() {

            int qtdeNos = 0;

            NoListaDupla p = primeiro;

            while (p != null) {
                qtdeNos++;
                p = p.getProximo();
            }
            return qtdeNos;
        }

        //Deverá retornar o valor do último nó da lista encadeada, isto é, aquele que está na extremidade oposta do primeiro nós da lista.
        //Caso a lista esteja vazia, deverá lançar exceção ListaVaziaException.
        //Implemente este método sem criar nova variável de instância na classe Lista.
        public T obterUltimo() throws Exception {
            if (estaVazia()) {
                throw new ListaVaziaException("Lista está vazia!");
            }

            NoListaDupla<T> ultimo = null;

            NoListaDupla p = primeiro;
            while (p != null) {
                ultimo = p;
                p = p.getProximo();
            }
            return (T) ultimo.getInfo();
        }

        public NoListaDupla<T> getPrimeiro() {
            return primeiro;
        }

    }

    public class FilaLista<T> implements Fila<T> {

        private Lista<T> fila;

        public FilaLista() {
            fila = new Lista<>();
        }

        @Override
        public void inserir(T valor) {
            fila.inserirNoFinal(valor);
        }

        @Override
        public T retirar() {
            if (estaVazia()) {
                throw new RuntimeException("A Fila esta Vazia");
            }

            T valor = fila.getPrimeiro().getInfo();
            fila.retirar(valor);

            return valor;
        }

        @Override
        public T peek() {
            if (estaVazia()) {
                throw new RuntimeException("A Fila esta Vazia");
            }

            T valor = fila.getPrimeiro().getInfo();
            return valor;
        }

        @Override
        public void liberar() {
            fila.liberar();
        }

        @Override
        public String toString() {
            if (estaVazia()) {
                throw new RuntimeException("Pilha esta vazia");
            }

            return fila.exibir();
        }

        @Override
        public boolean estaVazia() {
            return fila.estaVazia();
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

    public class NoLista<T> {

        private T info;
        private NoLista proximo;

        public NoLista(T info) {
            this.info = info;
        }

        public T getInfo() {
            return info;
        }

        public void setInfo(T info) {
            this.info = info;
        }

        public NoLista<T> getProximo() {
            return proximo;
        }

        public void setProximo(NoLista<T> proximo) {
            this.proximo = proximo;
        }
    }

    public class ListaEncadeada<T> {

        private NoLista<T> primeiro;

        public ListaEncadeada() {
            this.primeiro = null;
        }

        public NoLista<T> getPrimeiro() {
            return primeiro;
        }

        public void inserir(T info) {
            NoLista<T> segundo = primeiro;
            primeiro = new NoLista(info);
            primeiro.setProximo(segundo);
        }

        public boolean estaVazia() {
            return (primeiro == null);
        }

        public NoLista<T> buscar(T info) throws IndexOutOfBoundsException {
            NoLista<T> aux = primeiro;

            while (aux != null) {
                if (aux.getInfo().equals(info)) {
                    return aux;
                }

                aux = aux.getProximo();
            }

            return null;
        }

        public void retirar(T info) {
            NoLista<T> aux = primeiro;
            NoLista<T> anterior = null;

            while ((aux != null) && (!aux.getInfo().equals(info))) {
                anterior = aux;
                aux = aux.getProximo();
            }

            if (aux != null) {
                if (anterior == null) {
                    this.primeiro = aux.getProximo();
                } else {
                    anterior.setProximo(aux.getProximo());
                }
            }
        }

        public int obterComprimento() {
            NoLista<T> aux = primeiro;
            int count = 0;

            while (aux != null) {
                count++;
                aux = aux.getProximo();
            }

            return count;
        }

        public NoLista<T> obterNo(int posicao) throws IndexOutOfBoundsException {
            if (posicao < 0) {
                throw new IndexOutOfBoundsException("A posição informada está negativa!");
            }

            NoLista<T> aux = primeiro;
            int auxPosicao = 0;

            while (aux != null) {
                if (posicao == auxPosicao) {
                    return aux;
                }

                auxPosicao++;
                aux = aux.getProximo();
            }

            throw new IndexOutOfBoundsException("A posição informada é maior que o tamanho da lista!");
        }

        public ListaEncadeada<T> criarSubLista(int inicio, int fim) {
            if (inicio < 0) {
                throw new IndexOutOfBoundsException("A posição inicial informada é negativa!");
            }

            if (fim > obterComprimento()) {
                throw new IndexOutOfBoundsException("A posição final informada é maior que o tamanho da lista!");
            }

            ListaEncadeada<T> novaLista = new ListaEncadeada<>();
            NoLista<T> aux = primeiro;
            int auxPosicao = 0;

            while (aux != null) {
                if ((inicio <= auxPosicao) && (fim >= auxPosicao)) {
                    novaLista.inserir(aux.getInfo());
                }

                auxPosicao++;
                aux = aux.getProximo();
            }

            return novaLista;
        }

        @Override
        public String toString() {
            NoLista<T> aux = primeiro;
            String str = "";

            while (aux != null) {
                if (aux == primeiro) {
                    str += aux.getInfo();
                } else {
                    str += ", " + aux.getInfo();
                }

                aux = aux.getProximo();
            }

            return str;
        }
    }

    public class PilhaLista<T> implements Pilha<T> {

        private ListaEncadeada<T> lista = new ListaEncadeada<>();

        @Override
        public void push(T info) {
            lista.inserir(info);
        }

        @Override
        public T pop() {
            T backup = peek();
            lista.retirar(backup);
            return backup;
        }

        @Override
        public T peek() {
            if (lista.estaVazia()) {
                throw new PilhaVaziaException("Pilha está vazia!");
            }

            return lista.getPrimeiro().getInfo();
        }

        @Override
        public boolean estaVazia() {
            return lista.estaVazia();
        }

        @Override
        public void liberar() {
            lista = null;
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
