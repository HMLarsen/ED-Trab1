package trabalho.auxiliares.fila;

/**
 *
 * @author hugo
 */
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
        FilaVetor<T> novaFila = new FilaVetor<>(10);
        
        for (int i = inicio; i <= tamanho; i++) {
            novaFila.inserir(this.info[i]);
        }
        
        for (int i = f2.inicio; i <= f2.tamanho; i++) {
            novaFila.inserir(f2.info[i]);
        }
        
        return novaFila;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = inicio; i <= tamanho; i++) {
            if (i == tamanho - 1) {
                str += info[i];
            } else {
                str += ", " + info[i];
            }
        }

        return str;
    }

}
