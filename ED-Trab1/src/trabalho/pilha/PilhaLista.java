package trabalho.pilha;

import trabalho.pilha.excecao.PilhaVaziaException;
import trabalho.listaEncadeada.ListaEncadeada;

/**
 *
 * @author hugo
 */
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
