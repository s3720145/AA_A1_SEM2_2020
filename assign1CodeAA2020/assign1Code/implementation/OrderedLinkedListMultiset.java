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
                
        if(mHead == null) {
            // if the LL is empty
            newNode.setInstanceCount(1);
            mHead = newNode;
        } else if(item.compareTo(mHead.getValue()) < 0) {
            // if the value is less than the head
            newNode.setNext(mHead);
            newNode.setInstanceCount(1);
            mHead = newNode;
        } else {
            Node currentNode = mHead;

            while(currentNode != null) {
                if(currentNode.getNext() == null) {
                    // if the loop reaches the end of the LL
                    if(item.compareTo(currentNode.getValue()) == 0) {
                        // if the last value equals new node
                        currentNode.setInstanceCount(currentNode.getInstanceCount() + 1);
                        newNode.setInstanceCount(currentNode.getInstanceCount());
                    } else {
                        // else if new node is a unique value
                        newNode.setInstanceCount(1);
                    }
                    currentNode.setNext(newNode);
                    break;
                } else {
                    // both if statements can be true
                    if(item.compareTo(currentNode.getValue()) == 0) {
                        // if we find a node with the same value, update instance count
                        currentNode.setInstanceCount(currentNode.getInstanceCount() + 1);
                        newNode.setInstanceCount(currentNode.getInstanceCount());
    
                    }
    
                    if(item.compareTo(currentNode.getNext().getValue()) < 0) {
                        // if we find a node value larger then newNode insert
                        if(newNode.getInstanceCount() == 0) {
                            newNode.setInstanceCount(1);
                        }
                        newNode.setNext(currentNode.getNext());
                        currentNode.setNext(newNode);
                        break;
                    }
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
            while(currentNode != null && currentNode.getValue().compareTo(item) != 0) {
                currentNode = currentNode.getNext();
            }

            // if the current node value equals item, set numOccurences to instance count, otherwise numOccurences = -1
            if(currentNode != null && currentNode.getValue().compareTo(item) == 0) {
                numOccurences = currentNode.getInstanceCount();
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
            String temp = "";

            while(currentNode != null) {
                if(currentNode.getInstanceCount() == instanceCount && currentNode.getValue().compareTo(temp) != 0) {
                    outList.add(currentNode.getValue());
                    temp = currentNode.getValue();
                }

                currentNode = currentNode.getNext();
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

            // IF deleting head and instance count equals 1
            if(item.compareTo(currentNode.getValue()) == 0 && currentNode.getInstanceCount() == 1) {
                mHead = currentNode.getNext();
                currentNode = null;
            } else {
                while(currentNode != null) {
                    // if the end of the list is reached
                    if(currentNode.getNext() == null) {
                        prevNode.setNext(null);
                        currentNode = null;
                        break;
                    } else {
                        // both if statements can be true at the same time
                        if(item.compareTo(currentNode.getValue()) == 0) {
                            currentNode.setInstanceCount(currentNode.getInstanceCount() - 1);
                        }

                        if(item.compareTo(currentNode.getNext().getValue()) < 0) {
                            // if a larger value is found
                            prevNode.setNext(currentNode.getNext());
                            currentNode = null;
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
            String temp = "";
            
            while(currentNode != null) {
                if(currentNode.getValue().compareTo(temp) != 0) {
                    temp = currentNode.getValue();
                    sOutArray[sOutArrayLength] = currentNode.getValue() + ":" + currentNode.getInstanceCount() + "\n";
                    ++sOutArrayLength;
                }

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
