package implementation;

import java.util.List;

/**
 * Dual linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class DualLinkedListMultiset extends RmitMultiset
{   
    // reference to head node of LL ordered by element
    private Node mHeadOriginal;
    // reference to head node of LL ordered by instance count
    private Node mHeadInstance;

    // reference to number of element in the multiset
    private int length;

    public DualLinkedListMultiset() {
        this.mHeadOriginal = null;
        this.mHeadInstance = null;
        this.length = 0;
    }

    @Override
	public void add(String item) {
        Node newNode = new Node(item);

        // add into original LL
        // if the LL is empty
        if(mHeadOriginal == null) {
            mHeadOriginal = newNode;
            mHeadInstance = newNode;
        } else if(newNode.getValue().compareTo(mHeadOriginal.getValue()) <= 0) {
            // if newNode is smaller than or equal to the head
            newNode.setNext(mHeadOriginal);
            mHeadOriginal = newNode;
        } else {
            Node currentNode = mHeadOriginal;
            // iterate through list until a value larger than the newNode is found
            while(currentNode != null) {
                // if the end of the LL is found insert the newNode
                if(currentNode.getNext() == null) {
                    currentNode.setNext(newNode);
                    break;
                } else if(newNode.getValue().compareTo(currentNode.getNext().getValue()) <= 0) {
                    newNode.setNext(currentNode.getNext());
                    currentNode.setNext(newNode);
                    break;
                }

                currentNode = currentNode.getNext();
            }
        }

        ++length;
    } // end of add()


    @Override
	public int search(String item) {
        // Implement me!

        // Placeholder, please update.
        return searchFailed;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {

        // Placeholder, please update.
        return null;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) {
        // Implement me!

        // Placeholder, please update.
        return false;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        // Implement me!
    } // end of removeOne()


    @Override
	public String print() {

        // Placeholder, please update.
        return new String();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

        // Placeholder, please update.
        return new String();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of difference()

} // end of class DualLinkedListMultiset
