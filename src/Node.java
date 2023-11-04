public class Node<T> {
    private Node<T> next; // next is a reference to the next node in the list
    private Node<T> down; // down is a reference to the first node in the list below
    private Node<T> previous; // previous is a reference to the previous node in the list
    private T payload; // payload is the data that the node holds

    // Default constructor
    public Node() {
        this.next = null;
        this.down = null;
        this.previous = null;
        this.payload = null;
    }


    public Node(T payload) {
        this.next = null;
        this.down = null;
        this.previous = null;
        this.payload = payload;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getDown() {
        return down;
    }

    public void setDown(Node<T> down) {
        this.down = down;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}