package implementation;

public class Node {
    // Stored value of the node
    private String value;

    // Stored reference to next node
    private Node mNext;

    // Stored reference to previous node
    // NOTE: Only use in DualLinkedListMultiset.java
    private Node mPrev;

    public Node(String value) {
        this.value = value;
        this.mNext = null;
        this.mPrev = null;
    }

    public String getValue() {
        return this.value;
    }

    public Node getNext() {
        return this.mNext;
    }

    public Node getPrev() {
        return this.mPrev;
    }

    public void setNext(Node mNext) {
        this.mNext = mNext;
    }

    public void setPrev(Node mPrev) {
        this.mPrev = mPrev;
    }
}
