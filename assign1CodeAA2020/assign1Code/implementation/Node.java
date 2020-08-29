package implementation;

public class Node {
    // Stored value of the node
    private String value;

    private int instanceCount;

    // Stored reference to next node
    private Node mNext;

    // Stored reference to previous node
    private Node mPrev;

    public Node(String value) {
        this.value = value;
        this.instanceCount = 1;
        this.mNext = null;
        this.mPrev = null;
    }

    public String getValue() {
        return this.value;
    }

    public int getInstanceCount() {
        return this.instanceCount;
    }

    public Node getNext() {
        return this.mNext;
    }

    public Node getPrev() {
        return this.mPrev;
    }

    public void setInstanceCount(int instanceCount) {
        this.instanceCount = instanceCount;
    }

    public void setNext(Node mNext) {
        this.mNext = mNext;
    }

    public void setPrev(Node mPrev) {
        this.mPrev = mPrev;
    }
}
