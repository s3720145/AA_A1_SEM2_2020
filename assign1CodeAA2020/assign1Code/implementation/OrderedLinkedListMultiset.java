package implementation;

import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class OrderedLinkedListMultiset extends RmitMultiset
{
    // reference for head node
    private Node mHead;
    
    // reference for number of items in multiset
    private int length;

    public OrderedLinkedListMultiset() {
        this.mHead = null;
        this.length = 0;
    }

    @Override
	public void add(String item) {
        // Implement me!
        Node newNode = new Node(item);

        // IF the list is empty, then make the node as head
        if(mHead == null) {
            mHead = newNode;
        } else if() {
            // ELSE IF the node value is smaller than the head node, insert the node at the start and make it
            // the new head node

            // USE compareTo() to compare lexicographically
        } else {
            // ELSE iterate through the LL until a node with a larger value than the item is found
            // IF value is larger than the item, insert the node
            if() {

            }
        }

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

} // end of class OrderedLinkedListMultiset
