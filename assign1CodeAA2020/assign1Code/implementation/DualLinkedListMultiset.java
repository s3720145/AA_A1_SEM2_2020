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
    private Node mHeadO;
    // reference to head node of LL ordered by instance count
    private Node mHeadI;

    // reference to number of elements in the multiset
    private int length;

    public DualLinkedListMultiset() {
        this.mHeadO = null;
        this.mHeadI = null;
        this.length = 0;
    }

    @Override
	public void add(String item) {
        // adding into the LL ordered by alphabet
        if(mHeadO == null) {
            // list empty
            mHeadO = new Node(item);
        } else if(item.compareTo(mHeadO.getValue()) == 0) {
            // item equals mhead
            mHeadO.setInstanceCount(mHeadO.getInstanceCount() + 1);
        } else if(item.compareTo(mHeadO.getValue()) < 0) {
            // item is less than mhead
            Node newNode = new Node(item);
            newNode.setNext(mHeadO);
            mHeadO = newNode;
        } else {
            // iterate thru LL to find suitable insert
            Node currentNode = mHeadO;

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

        // adding into LL ordered by instance from highest to lowest
        if(mHeadI == null) {
            mHeadI = new Node(item);
        } else if(item.compareTo(mHeadI.getValue()) == 0) {
            // head has the most instances no need to arrange
            mHeadI.setInstanceCount(mHeadI.getInstanceCount() + 1);
        } else {
            Node currentNode = mHeadI;
            Node prevNode = null;

            while(currentNode != null) {
                // if the node doesn't exist in the LL. we have reached the end of the list
                if(currentNode.getNext() == null && item.compareTo(currentNode.getValue()) != 0) {
                    currentNode.setNext(new Node(item));
                    break;
                } else if(item.compareTo(currentNode.getValue()) == 0) {
                    // temp reference to currentNode
                    Node tempCurrNode = currentNode;
                    // update instance count and remove node
                    tempCurrNode.setInstanceCount(tempCurrNode.getInstanceCount() + 1);
                    prevNode.setNext(tempCurrNode.getNext());

                    // reinsert node into LL from the start
                    currentNode = mHeadI;

                    while(currentNode != null) {
                        if(tempCurrNode.getInstanceCount() >= mHeadI.getInstanceCount()) {
                            tempCurrNode.setNext(mHeadI);
                            mHeadI = tempCurrNode;
                            break;
                        } else if(tempCurrNode.getInstanceCount() >= currentNode.getNext().getInstanceCount()) {
                            tempCurrNode.setNext(currentNode.getNext());
                            currentNode.setNext(tempCurrNode);
                            break;
                        }

                        currentNode = currentNode.getNext();
                    }
                    break;
                }

                prevNode = currentNode;
                currentNode = currentNode.getNext();
            }
        }

        Node currentNode = mHeadI;
        while(currentNode != null) {
            System.out.println(currentNode.getValue() + ":" + currentNode.getInstanceCount());
            currentNode = currentNode.getNext();
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
        // removing from LL that sorts by alphabet
        if(mHeadO != null) {
            Node currentNode = mHeadO;
            Node prevNode = null;

            // deleting head when instance count = 1
            if(item.compareTo(mHeadO.getValue()) == 0 && mHeadO.getInstanceCount() == 1) {
                mHeadO = currentNode.getNext();
                currentNode = null;
            } else if(item.compareTo(mHeadO.getValue()) == 0) {
                // decreasing head instance count by 1
                mHeadO.setInstanceCount(mHeadO.getInstanceCount() - 1);
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

        // removing from LL sorting by instance
        if(mHeadI != null) {
            Node currentNode = mHeadI;
            Node prevNode = null;

            // deleting head when instance count = 1
            if(item.compareTo(mHeadI.getValue()) == 0 && mHeadI.getInstanceCount() == 1) {
                mHeadI = currentNode.getNext();
                currentNode = null;
            } else if(item.compareTo(mHeadI.getValue()) == 0) {
                // temp reference to currentNode
                Node tempCurrNode = currentNode;
                // decreasing head instance count by 1 and remove node
                tempCurrNode.setInstanceCount(tempCurrNode.getInstanceCount() - 1);
                mHeadI = tempCurrNode.getNext();

                // reinsert node into LL from the start
                currentNode = mHeadI;

                while(currentNode != null) {
                    if(tempCurrNode.getInstanceCount() >= mHeadI.getInstanceCount()) {
                        tempCurrNode.setNext(mHeadI);
                        mHeadI = tempCurrNode;
                        break;
                    } else if(tempCurrNode.getInstanceCount() >= currentNode.getNext().getInstanceCount()) {
                        tempCurrNode.setNext(currentNode.getNext());
                        currentNode.setNext(tempCurrNode);
                        break;
                    }

                    currentNode = currentNode.getNext();
                }
            } else {
                while(currentNode != null) {
                    // if deleting the last node on the list
                    if(currentNode.getNext() == null && item.compareTo(currentNode.getValue()) == 0) {
                        if(currentNode.getInstanceCount() == 1) {
                            prevNode.setNext(null);
                            currentNode = null;
                        } else {
                            // temp reference to currentNode
                            Node tempCurrNode = currentNode;
                            // decreasing head instance count by 1 and remove node
                            tempCurrNode.setInstanceCount(tempCurrNode.getInstanceCount() - 1);
                            prevNode.setNext(tempCurrNode.getNext());

                            // reinsert node into LL from the start
                            currentNode = mHeadI;

                            while(currentNode != null) {
                                if(tempCurrNode.getInstanceCount() >= mHeadI.getInstanceCount()) {
                                    tempCurrNode.setNext(mHeadI);
                                    mHeadI = tempCurrNode;
                                    break;
                                } else if(tempCurrNode.getInstanceCount() >= currentNode.getNext().getInstanceCount()) {
                                    tempCurrNode.setNext(currentNode.getNext());
                                    currentNode.setNext(tempCurrNode);
                                    break;
                                }

                                currentNode = currentNode.getNext();
                            }
                        }
                        break;
                    } else if(item.compareTo(currentNode.getValue()) == 0) {
                        // if finding a node of equal value
                        if(currentNode.getInstanceCount() == 1) {
                            prevNode.setNext(currentNode.getNext());
                            currentNode = null;
                            break;
                        } else {
                            // temp reference to currentNode
                            Node tempCurrNode = currentNode;
                            // decreasing head instance count by 1 and remove node
                            tempCurrNode.setInstanceCount(tempCurrNode.getInstanceCount() - 1);
                            prevNode.setNext(tempCurrNode.getNext());

                            // reinsert node into LL from the start
                            currentNode = mHeadI;

                            while(currentNode != null) {
                                if(tempCurrNode.getInstanceCount() >= mHeadI.getInstanceCount()) {
                                    tempCurrNode.setNext(mHeadI);
                                    mHeadI = tempCurrNode;
                                    break;
                                } else if(tempCurrNode.getInstanceCount() >= currentNode.getNext().getInstanceCount()) {
                                    tempCurrNode.setNext(currentNode.getNext());
                                    currentNode.setNext(tempCurrNode);
                                    break;
                                }

                                currentNode = currentNode.getNext();
                            }
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
        // Placeholder, please update.
        return new String();
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
        StringBuffer sOut = new StringBuffer();

        if(mHeadO == null) {
            sOut.append("Error - No nodes in data structure\n");
        } else if(upper.compareTo(lower) < 0) {
            sOut.append("Error - Invalid upper and lower boundaries\n");
        } else {
            Node currentNode = mHeadO;

            while(currentNode.getValue().compareTo(lower) < 0) {
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
