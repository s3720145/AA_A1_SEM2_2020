package implementation;

import java.util.Iterator;

public class MyLinkedListIterator implements Iterator<String> {
    private Node currentNode;

    public MyLinkedListIterator(Node mHead) {
        currentNode = mHead;
    }

    @Override
    public boolean hasNext() {
        return currentNode != null;
    }

    @Override
    public String next() {
        String value = currentNode.getValue();
        currentNode = currentNode.getNext();
        return value;
    }
}