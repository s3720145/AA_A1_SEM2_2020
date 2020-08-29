package implementation;

import java.util.Iterator;
import java.util.LinkedList;
//import java.util.List;


public class MyList extends LinkedList<String> implements Iterable<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Node<String> head;
	protected Node<String> tail;
    protected int length;
    
	
	public MyList() {
		 head = null;
		 tail = null;
	     length = 0;
	}
	
	public Node<String> getHead(){
		return head;
	}
	
	public Node<String> getTail(){
		return tail;
	}
	
	public void addElem(String elem) {
		Node<String> newNode = new Node<String>(elem);
		
		if (head == null) {
			head = newNode;
			tail = newNode;
		}
		
		else {
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
		}
		
		length++;
	}
	
	public Iterator<String> iterator(){
		return new MyListIterator(this);
	}

	public int size() {
		return length;
	}
	
	
	public class MyListIterator implements Iterator<String> {

		Node<String> current;
		
		public MyListIterator(MyList list) {
			current = list.getHead();
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != null;
			//return false;
		}

		@Override
		public String next() {
			// TODO Auto-generated method stub
			String elem = current.getElement();
			current = current.getNext();
			return elem;
			//return null;
		}

	}
	 
	 private class Node<String>{
        //element stored in node
        private String elem;
        //a reference to the next node 
        private Node<String> next;
        private Node<String> prev;

        public Node(String elem) {
            this.elem = elem;
            next = null;
            prev = null;
        }

        public String getElement() {
            return elem;
        }


        public Node<String> getNext() {
            return next;
        }
        
        public Node<String> getPrev() {
        	return prev;
        }

        public void setElement(String elem) {
            this.elem = elem;
        }


        public void setNext(Node<String> next) {
            this.next = next;
        }
        
        public void setPrev(Node<String> prev) {
        	this.prev = prev;
        }
    } // end of inner class Node


}
