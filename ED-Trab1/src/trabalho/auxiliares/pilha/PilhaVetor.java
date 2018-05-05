package trabalho.auxiliares.pilha;

/**
 *
 * @author hugo
 */
public class PilhaVetor<T> implements Pilha<T> {

    private T[] info;
    private int limite;
    private int tamanho;

    public PilhaVetor(int limite) {
        info = ((T[]) new Object[limite]);
        this.limite = limite;
        this.tamanho = 0;
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
