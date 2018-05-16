package trabalho.fila;

import trabalho.listaEncadeada.ListaEncadeada;

/**
 *
 * @author hugo
 */
public class FilaLista<T> implements Fila<T> {

    private ListaEncadeada<T> lista;

    public FilaLista() {
        lista = new ListaEncadeada<>();
    }

    @Override
    public void inserir(T valor) {
        lista.inserir(valor);
    }

    @Override
    public T retirar() {
        if (estaVazia()) {
            throw new FilaVaziaException("A fila está vazia!");
        }

        T valor = lista.getPrimeiro().getInfo();
        lista.retirar(valor);

        return valor;
    }

    @Override
    public T peek() {
        if (estaVazia()) {
            throw new FilaVaziaException("A fila está vazia!");
        }

        T valor = lista.getPrimeiro().getInfo();
        return valor;
    }

    @Override
    public void liberar() {
        lista = null;
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            throw new FilaVaziaException("A fila está vazia!");
        }

        return lista.toString();
    }

    @Override
    public boolean estaVazia() {
        return lista.estaVazia();
    }

}
