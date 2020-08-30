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

    public Node getHead() {
        return this.mHead;
    }

    @Override
	public void add(String item) {
        if(mHead == null) {
            // list empty
            mHead = new Node(item);
        } else if(item.compareTo(mHead.getValue()) == 0) {
            // item equals mhead
            mHead.setInstanceCount(mHead.getInstanceCount() + 1);
        } else if(item.compareTo(mHead.getValue()) < 0) {
            // item is less than mhead
            Node newNode = new Node(item);
            newNode.setNext(mHead);
            mHead = newNode;
        } else {
            // iterate thru LL to find suitable insert
            Node currentNode = mHead;

            while(currentNode != null) {
                if(currentNode.getNext() == null) {
                    // if the end of the list is reached
                    currentNode.setNext(new Node(item));
                    break;
                } else if(item.compareTo(currentNode.getNext().getValue()) == 0) {
                    // if currentNode has the same value as item
                    currentNode.getNext().setInstanceCount(currentNode.getNext().getInstanceCount() + 1);
                    break;
                } else if(item.compareTo(currentNode.getNext().getValue()) < 0) {
                    // item is less than current node
                    Node newNode = new Node(item);
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
        int numOccurences = -1;

        if(mHead != null) {
            Node currentNode = mHead;

            // Moves past node values that are not equal to item
            while(currentNode != null) {
                if(item.compareTo(currentNode.getValue()) == 0) {
                    numOccurences = currentNode.getInstanceCount();
                    break;
                }
                currentNode = currentNode.getNext();
            }
        }
        
        return numOccurences;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
        List<String> outList = new MyLinkedList();

        if(mHead == null) {
            outList.add("Error - No nodes in data structure");
        } else {
            Node currentNode = mHead;

            while(currentNode != null) {
                if(currentNode.getInstanceCount() == instanceCount) {
                    outList.add(currentNode.getValue());
                }

                currentNode = currentNode.getNext();
            }
        }

        return outList;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        boolean isContained = false;

        if(mHead != null) {
            Node currentNode = mHead;

            while(currentNode != null) {
                if(item.compareTo(currentNode.getValue()) == 0) {
                    isContained = true;
                    break;
                }

                currentNode = currentNode.getNext();
            }
        }

        return isContained;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        if(mHead != null) {
            Node currentNode = mHead;
            Node prevNode = null;

            // deleting head when instance count = 1
            if(item.compareTo(mHead.getValue()) == 0 && mHead.getInstanceCount() == 1) {
                mHead = currentNode.getNext();
                currentNode = null;
            } else if(item.compareTo(mHead.getValue()) == 0) {
                // decreasing head instance count by 1
                mHead.setInstanceCount(mHead.getInstanceCount() - 1);
            } else {
                while(currentNode != null) {
                    // if deleting the last node on the list
                    if(currentNode.getNext() == null && item.compareTo(currentNode.getValue()) == 0) {
                        if(currentNode.getInstanceCount() == 1) {
                            prevNode.setNext(null);
                            currentNode = null;
                        } else {
                            currentNode.setInstanceCount(currentNode.getInstanceCount() - 1);
                        }
                        break;
                    } else if(item.compareTo(currentNode.getValue()) == 0) {
                        // if finding a node of equal value
                        if(currentNode.getInstanceCount() == 1) {
                            prevNode.setNext(currentNode.getNext());
                            currentNode = null;
                            break;
                        } else {
                            currentNode.setInstanceCount(currentNode.getInstanceCount() - 1);
                            break;
                        }
                    }

                    prevNode = currentNode;
                    currentNode = currentNode.getNext();
                }
            }
        }

        --length;
    } // end of removeOne()


    @Override
	public String print() {
        StringBuffer sOut = new StringBuffer();
        String[] sOutArray = new String[1000];
        int sOutArrayLength = 0;
              
        if(mHead == null) {
            sOut.append("Error - No nodes in data structure\n");
        } else {
            Node currentNode = mHead;
            
            while(currentNode != null) {
                sOutArray[sOutArrayLength] = currentNode.getValue() + ":" + currentNode.getInstanceCount() + "\n";
                ++sOutArrayLength;

                currentNode = currentNode.getNext();
            }
        }

        // SORT sOutArray
        mergeSortForInstances(sOutArray, new String[length], 0, sOutArrayLength - 1);

        for(int i = 0; i < sOutArrayLength; ++i) {
            sOut.append(sOutArray[i]);
        }

        return sOut.toString();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
        StringBuffer sOut = new StringBuffer();

        if(mHead == null) {
            sOut.append("Error - No nodes in data structure\n");
        } else if(upper.compareTo(lower) < 0) {
            sOut.append("Error - Invalid upper and lower boundaries\n");
        } else {
            Node currentNode = mHead;

            while(currentNode != null && currentNode.getValue().compareTo(lower) < 0) {
                currentNode = currentNode.getNext();
            }

            while(currentNode != null && currentNode.getValue().compareTo(upper) <= 0) {
                sOut.append(currentNode.getValue() + ":" + currentNode.getInstanceCount() + "\n");
                currentNode = currentNode.getNext();
            }
        }
        
        return sOut.toString();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        RmitMultiset union = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset casted = (OrderedLinkedListMultiset) other;
        Node currentNode = mHead;

        // inserting nodes from THIS linked list
        while(currentNode != null) {
            for(int i = 0; i < currentNode.getInstanceCount(); ++i) {
                union.add(currentNode.getValue());
            }
            currentNode = currentNode.getNext();
        }

        // inserting nodes from the other multiset
        currentNode = casted.getHead();
        while(currentNode != null) {
            for(int i = 0; i < currentNode.getInstanceCount(); ++i) {
                union.add(currentNode.getValue());
            }
            currentNode = currentNode.getNext();
        }

        return union;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
        RmitMultiset intersect = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset casted = (OrderedLinkedListMultiset) other;
        Node currentNode = mHead;
        Node currentNodeOther = casted.getHead();

        // ONLY WORKS IN A SORTED LINKED LIST
        // if currentNode < currentNodeOther, advance currentNode
        // if currentNodeOther < currentNode, advance currentNodeOther
        // if equal, add to intersect and advance both currentNode and currentNodeOther

        while(currentNode != null && currentNodeOther != null) {
            if(currentNode.getValue().compareTo(currentNodeOther.getValue()) < 0) {
                currentNode = currentNode.getNext();
            } else if(currentNodeOther.getValue().compareTo(currentNode.getValue()) < 0) {
                currentNodeOther = currentNodeOther.getNext();
            } else {
                int numInstances = currentNode.getInstanceCount() <= currentNodeOther.getInstanceCount() ? 
                currentNode.getInstanceCount() : currentNodeOther.getInstanceCount();
                for(int i = 0; i < numInstances; ++i) {
                    intersect.add(currentNode.getValue());
                }
                currentNode = currentNode.getNext();
                currentNodeOther = currentNodeOther.getNext();
            }
        }
        
        return intersect;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
        RmitMultiset difference = new OrderedLinkedListMultiset();
        OrderedLinkedListMultiset casted = (OrderedLinkedListMultiset) other;
        Node currentNode = mHead;

        // Difference is those elements in this multiset, subtract the elements in the other multiset.
        // iterate through current LL
        while(currentNode != null) {
            // if casted contains the current node value
            if(casted.contains(currentNode.getValue())) {
                int numInstances = currentNode.getInstanceCount() - casted.search(currentNode.getValue());
                for(int i = 0; i < numInstances; ++i) {
                    difference.add(currentNode.getValue());
                }
            } else {
                // if casted does not contain the current node value
                for(int i = 0; i < currentNode.getInstanceCount(); ++i) {
                    difference.add(currentNode.getValue());
                }
            }
            currentNode = currentNode.getNext();
        }
        
        return difference;
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

} // end of class OrderedLinkedListMultiset
