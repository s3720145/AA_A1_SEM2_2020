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
    // keeps number of 
    private String[] instanceCount;

    // reference to number of element in the multiset
    private int length;

    public DualLinkedListMultiset() {
        this.mHeadOriginal = null;
        this.mHeadInstance = null;
        this.instanceCount = new String[1000];
        this.length = 0;
    }

    @Override
	public void add(String item) {
        Node newNode = new Node(item);

        // TODO: have a node that stores both item and value

        // add into instance count array
        if(instanceCount[0] == null) {
            // if array is empty
            instanceCount[0] = item + ":1";
        } else {
            int index = 0;
            while(instanceCount[index] != null) {
                // if the end of the array is reached
                if(instanceCount[index + 1] == null) {
                    instanceCount[index + 1] = item + ":1";
                } else if(instanceCount[index].substring(0, instanceCount[index].indexOf(':')).compareTo(item) == 0) {
                    int numInstances = Integer.parseInt(instanceCount[index].substring(instanceCount[index].indexOf(':') + 1, 
                    instanceCount[index].length())) + 1;
                    instanceCount[index] = item + ":" + numInstances;
                    break;
                }
                ++index;
            }
        }

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

        // add into LL sorted by instance count

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


    public void mergeSortForInstances(String[] sOutArray, String[] temp, int leftStart, int rightEnd) {
        if(leftStart >= rightEnd) {
            return;
        }

        int middle = (leftStart + rightEnd) / 2;
        // sorting left
        mergeSortForInstances(sOutArray, temp, leftStart, middle);
        // sorting right
        mergeSortForInstances(sOutArray, temp, middle + 1, rightEnd);
        // merging halves
        mergeHalves(sOutArray, temp, leftStart, rightEnd);
    }


    public void mergeHalves(String[] sOutArray, String[] temp, int leftStart, int rightEnd) {
        int leftEnd = (rightEnd + leftStart) / 2;
        int rightStart = leftEnd + 1;
        int size = rightEnd - leftStart + 1;

        int left = leftStart;
        int right = rightStart;
        int index = leftStart;

        while(left <= leftEnd && right <= rightEnd) {
            // take the substring occuring after ':'
            String leftValue = sOutArray[left].substring(sOutArray[left].indexOf(':') + 1, sOutArray[left].length());
            String rightValue = sOutArray[right].substring(sOutArray[right].indexOf(':') + 1, sOutArray[right].length());
            if(leftValue.compareTo(rightValue) <= 0) {
                temp[index] = sOutArray[right];
                ++right;
            } else {
                temp[index] = sOutArray[left];
                ++left;
            }
             
            ++index;
        }

        // copies elements from the left side
        System.arraycopy(sOutArray, left, temp, index, leftEnd - left + 1);
        // copies elements from the right side
        System.arraycopy(sOutArray, right, temp, index, rightEnd - right + 1);
        // copies everything from temp back into sOutArray
        System.arraycopy(temp, leftStart, sOutArray, leftStart, size);
    }

} // end of class DualLinkedListMultiset
