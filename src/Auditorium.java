public class Auditorium<T> {
    private Node<T> first;

    public Auditorium() {
        this.first = null;
    }

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

}
