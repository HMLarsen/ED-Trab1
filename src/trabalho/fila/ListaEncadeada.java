package trabalho.fila;

import trabalho.listaEncadeada.*;

/**
 *
 * @author hugo
 */
public class ListaEncadeada<T> {

    private NoLista<T> primeiro;
    private NoLista<T> ultimo;

    public ListaEncadeada() {
        this.primeiro = null;
        this.ultimo = null;
    }

    public NoLista<T> getPrimeiro() {
        return primeiro;
    }

    public void inserirNoFinal(T info) {
        ultimo = new NoLista(info);

        if (primeiro == null) {
            primeiro = ultimo;
        } else {
            NoLista<T> p = obterNo(obterComprimento() - 1);
            p.setProximo(ultimo);
        }
    }

    public boolean estaVazia() {
        return primeiro == null;
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
                novaLista.inserirNoFinal(aux.getInfo());
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
