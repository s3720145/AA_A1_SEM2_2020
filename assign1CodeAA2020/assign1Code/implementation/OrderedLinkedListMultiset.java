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
        Node newNode = new Node(item);

        /**
         *  IF the list is empty, then make the newNode the head node
         *      ELSE IF the node value is smaller than or equal to the head node, insert the node at the start and
         *          make it the new head node
         *      ELSE iterate through the LL, starting at the head, until a node with a larger than or equal to value is found
         *          IF the end of the list is reached, insert the node
         *          ELSE IF the item value is smaller than or equal to the next node, THEN insert the node and BREAK
         *  INCREMENT length by 1
         */
                
        if(mHead == null) {
            mHead = newNode;
        } else if(newNode.getValue().compareTo(mHead.getValue()) <= 0) {
            newNode.setNext(mHead);
            mHead = newNode;
        } else {
            Node currentNode = mHead;

            while(currentNode != null) {
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
        int numOccurences = 0;

        if(mHead != null) {
            Node currentNode = mHead;

            // Moves past node values that are not equal to item
            while(currentNode != null && currentNode.getValue().compareTo(item) != 0) {
                currentNode = currentNode.getNext();
            }

            // Count node values that are equal to item and stops
            while(currentNode != null && currentNode.getValue().compareTo(item) == 0) {
                ++numOccurences;
                currentNode = currentNode.getNext();
            }
        } else {
            numOccurences = -1;
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
            int counter = 1;

            while(currentNode.getNext() != null) {
                if(currentNode.getValue().compareTo(currentNode.getNext().getValue()) == 0) {
                    ++counter;
                } else {
                    if(counter == instanceCount) {
                        outList.add(currentNode.getValue());
                    }
                    counter = 1;
                }

                currentNode = currentNode.getNext();
            }

            if(counter == instanceCount) {
                outList.add(currentNode.getValue());
            }

            if(outList.isEmpty() == true) {
                outList.add("Could not find elements matching instance count");
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
                if(currentNode.getValue().compareTo(item) == 0) {
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

            // IF deleting head
            if(currentNode.getValue().compareTo(item) == 0) {
                mHead = currentNode.getNext();
                currentNode = null;
            } else {
                while(currentNode != null) {
                    if(currentNode.getNext() == null) {
                        prevNode.setNext(null);
                        currentNode = null;
                        break;
                    } else if(currentNode.getValue().compareTo(item) == 0) {
                        prevNode.setNext(currentNode.getNext());
                        currentNode = null;
                        break;
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
        String[] sOutArray = new String[100];
        int sOutArrayLength = 0;
              
        if(mHead == null) {
            sOut.append("Error - No nodes in data structure\n");
        } else {
            Node currentNode = mHead;
            int counter = 1;

            while(currentNode.getNext() != null) {
                if(currentNode.getValue().compareTo(currentNode.getNext().getValue()) == 0) {
                    ++counter;
                } else {
                    sOutArray[sOutArrayLength] = currentNode.getValue() + ":" + counter + "\n";
                    ++sOutArrayLength;
                    counter = 1;
                }

                currentNode = currentNode.getNext();
            }

            sOutArray[sOutArrayLength] = currentNode.getValue() + ":" + counter + "\n";
            ++sOutArrayLength;
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
            sOut.append("Error - Invalid upper and lower boundaries");
        } else {
            Node currentNode = mHead;

            while(currentNode.getValue().compareTo(lower) < 0) {
                currentNode = currentNode.getNext();
            }

            while(currentNode != null && currentNode.getValue().compareTo(upper) <= 0) {
                sOut.append(currentNode.getValue() + "\n");
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
            union.add(currentNode.getValue());
            currentNode = currentNode.getNext();
        }

        // inserting nodes from the other multiset
        currentNode = casted.getHead();
        while(currentNode != null) {
            union.add(currentNode.getValue());
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
                intersect.add(currentNode.getValue());
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
        Node currentNodeOther = casted.getHead();

        // Difference is those elements in this multiset, subtract the elements in the other multiset.
        while(currentNode != null && currentNodeOther != null) {
            if(currentNode.getValue().compareTo(currentNodeOther.getValue()) < 0) {
                difference.add(currentNode.getValue());
                currentNode = currentNode.getNext();
            } else if(currentNodeOther.getValue().compareTo(currentNode.getValue()) < 0) {
                difference.add(currentNode.getValue());
                currentNodeOther = currentNodeOther.getNext();
            } else {
                currentNode = currentNode.getNext();
                currentNodeOther = currentNodeOther.getNext();
            }
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
            if(sOutArray[left].substring(sOutArray[left].length() - 1).
            compareTo(sOutArray[right].substring(sOutArray[right].length() - 1)) <= 0) {
                temp[index] = sOutArray[left];
                ++left;
            } else {
                temp[index] = sOutArray[right];
                ++right;
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
