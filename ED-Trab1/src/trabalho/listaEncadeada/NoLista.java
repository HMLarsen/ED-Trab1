package trabalho.listaEncadeada;

/**
 *
 * @author hugo
 */
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
