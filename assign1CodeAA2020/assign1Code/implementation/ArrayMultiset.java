package implementation;

import java.util.List;

/**
 * Array implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset 
{
	
	protected Info[] array;
	protected Info newInfo;
	//protected Info[] sorted_array;
	
	public ArrayMultiset() {
		array = null;
	}
	
    @Override
	public void add(String elem) {
        // Implement me!
    	//implement if the elem already exists, then increment the number of elem
    	if (array == null) {
    		array = new Info[1];
    		newInfo = new Info(elem);
    		newInfo.incrementNum();
    		array[0] = newInfo;
    	}
    	else {
    		if (!contains(elem)) {
    			Info newArray[] = new Info[array.length + 1];
    		
    			for (int i = 0; i < array.length; i++) {
    				newArray[i] = array[i];
    			}
    		
    		newInfo = new Info(elem);
    		newInfo.incrementNum();
    		newArray[array.length] = newInfo;
    		
    		array = newArray;
    		}
    		else {
    			for (int i = 0; i < array.length; i++) {
    				if (array[i].getElem().equals(elem)) {
    					array[i].incrementNum();
    				}
    			}
    		}
    	}
    	
    } // end of add()


    @Override
	public int search(String elem) {
    	//return the number of instances of the elem, if there is no elem then return -1
    	if (array != null) {
    		for (int i = 0; i < array.length; i++) {
    			if (array[i].getElem().equals(elem)) {
    				return array[i].getNumElem();
    			}
    		}
    	}
    	
        return searchFailed;
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {
    	MyList elemList = new MyList();
    	
    	//search for all elements with
    	//specified number of instance in the multiset with id. Returns a list of such elements
    	
    	if (array != null) {
    		for (int i = 0; i < array.length; i++) {
    			if (array[i].getNumElem() == instanceCount) {
    				elemList.addElem(array[i].getElem());
    			}
    		}
    		if (elemList.size() > 0) {
    			return elemList;
    		}
    	}
    	return null;
    } // end of searchByInstance


    @Override
	public boolean contains(String elem) {
        // Implement me!
    	if (array != null) {
    		for (int i = 0; i < array.length; i++) {
    			if (array[i].getElem().equals(elem)) {
    				return true;
    			}
    		}
    	}
        return false;
    } // end of contains()


    @Override
	public void removeOne(String elem) {
        // Implement me!
    	if (array != null) {
    		for (int i = 0; i < array.length; i++) {
    			if (array[i].getElem().equals(elem)) {
    				if (array[i].getNumElem() > 1) {
    					array[i].decrementNum();
    				}
    				else {
    					Info newArray[] = new Info[array.length - 1];
    		    		
    	    			for (int i1 = 0, i2 = 0; i1 < array.length; i1++) {
    	    				if (array[i1].getElem().equals(elem)) {
    	    					continue;
    	    				}
    	    				newArray[i2++] = array[i1];
    	    			}
    	    		
    	    			array = newArray;
    				}
    			}
    		}
    	}
    } // end of removeOne()


    @Override
	public String print() {
    	if (array != null) {
		    // Placeholder, please update.
			StringBuffer str_for_print = new StringBuffer();
			Info[] ordered_array = sortArray();

			for (int i = 0; i < ordered_array.length; i++) {
				String elem = ordered_array[i].getElem();
				String num_elem = String.valueOf(ordered_array[i].getNumElem());
				
				str_for_print.append(elem + ":" + num_elem + "\n");
			}
			
			return str_for_print.toString();
    	}
    	
    	return null;
    } // end of OrderedPrint

    /*
     * print element in alphabetical order, from lower to upper
     * for loop, 
     */
    @Override
	public String printRange(String lower, String upper) {
    	
    	//first char
    	int first = 0; 
    	//get total num baed on char
    	for(int i = 0; i<lower.length(); i++)
    	{
    		first = first + lower.charAt(i);
    	}
    	
    	int last = 0; 
    	for(int i = 0; i<upper.length(); i++)
    	{
    		last = last + upper.charAt(i);
    	}
    	//String Buffer
    	StringBuffer strPrintRange = new StringBuffer();
    	//array
    	Info[] ordered_array = sortArray();
    	//for loop from first to last
    	for(int current = first; current <= last; current++)
    	{
    		//get compare first character of Elem with current, if its equivalent, store it onto StringBuffer
    		for(int i = 0; i < array.length; i++)
    		{
    			
    			int array_total = 0;
    			String arrayElem = ordered_array[i].getElem();
    			for(int j = 0; j < arrayElem.length(); j++)
    	    	{
    				array_total = array_total + arrayElem.charAt(j);
    	    	}
    			
    			if(current == array_total)
    			{
    				String elem = ordered_array[i].getElem();
    				int num_elem = ordered_array[i].getNumElem();
    				strPrintRange.append(elem + ":" + num_elem + "\n");
    			}
    		}
    	}
        // Placeholder, please update.
        return strPrintRange.toString();
    } // end of printRange()
    
    public Info[] get_array()
    {
    	return array;
    }
    
    public Info[] sortArray() {
    	int n = array.length;
    	Info[] sorted_array = new Info[n];
    	
    	for (int k= 0; k < n; k++) {
    		sorted_array[k] = array[k];
    	}
    	
    	for (int i1 = 0; i1 < n-1; i1++) {
    		for (int j = 0; j < n-i1-1; j++) {
    			if (sorted_array[j].getNumElem() < sorted_array[j+1].getNumElem()) {
    				
    				Info temp = sorted_array[j];
    				sorted_array[j] = sorted_array[j+1];
    				sorted_array[j+1] = temp;
    			}
    			
    		}
    	}
    	
    	//if elements got the same num of elem, compare those elems alphabetically
    	//case insensitive
    	for (int i1 = 0; i1 < n-1; i1++) {
    		for (int j = 0; j < n-i1-1; j++) {
    			if (sorted_array[j].getNumElem() == sorted_array[j+1].getNumElem()) {
    				String curr = sorted_array[j].getElem();
    				String next = sorted_array[j+1].getElem();
    				if (curr.toLowerCase().charAt(0) > next.toLowerCase().charAt(0)) {
    					Info temp = sorted_array[j];
        				sorted_array[j] = sorted_array[j+1];
        				sorted_array[j+1] = temp;
    				}
    				else {
    					continue;
    				}
    			}
    			
    		}
    	}

		return sorted_array;
    }
    
    
    public int array_size() {
    	return array.length;
    }
    
    /*
     * Union Multiset
     * basically add all elem from other into current multiset
     */
    @Override
	public RmitMultiset union(RmitMultiset other) {
    	ArrayMultiset newSet = new ArrayMultiset();
    	ArrayMultiset set2 = (ArrayMultiset) other;

    	//look from the first Set, then proceed to second set
    	int n = this.array.length;
    	if (this.array != null || set2.array != null) {
			for (int i1 = 0; i1 < n; i1++) {
				//elem at position i, for first Set
				String elem_set1 = this.array[i1].getElem();
				int numElem_set1 = this.array[i1].getNumElem();
				for (int j = 0; j < numElem_set1; j++) {
					newSet.add(elem_set1);
				}
				
				if (set2.contains(elem_set1)) {
					int numElem_set2 = set2.search(elem_set1);
					for (int k = 0; k < numElem_set2; k++) {
						newSet.add(elem_set1);
					}
				}
				else {
					continue;
				}	
			}
			
			for (int i2 = 0; i2 < set2.array_size(); i2++) {
				 Info[] set2_array= set2.get_array();
				 String elem_set2 = set2_array[i2].getElem();
				 int numElem_set2 = set2_array[i2].getNumElem();
				 if (!newSet.contains(elem_set2)) {
					 for (int l = 0; l < numElem_set2; l++) {
						 newSet.add(elem_set2);
					 }
				 }
				 else {
					 continue;
				 }
				 
			}
			return newSet;
    	}
    	else {
    		return null;
    	}

    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	ArrayMultiset newSet = new ArrayMultiset();
    	ArrayMultiset set2 = (ArrayMultiset) other;
    	int n = this.array.length;
    	if (this.array != null || set2.array != null) {
    		for (int i = 0; i < n; i++) {
    			String elem_set1 = this.array[i].getElem();
				int numElem_set1 = this.array[i].getNumElem();
				//take the smallest number, < or ==
				if (set2.contains(elem_set1)) {
					int numElem_set2 = set2.search(elem_set1);
					if (numElem_set1 < numElem_set2) {
						for (int j = 0; j < numElem_set1; j++) {
							newSet.add(elem_set1);
						}
					}
					else if (numElem_set1 > numElem_set2) {
						for (int j = 0; j < numElem_set2; j++) {
							newSet.add(elem_set1);
						}
					}
					else if (numElem_set1 == numElem_set2) {
						for (int j = 0; j < numElem_set1; j++) {
							newSet.add(elem_set1);
						}
					}
				}
				else {
					continue;
				}
    		}
    		return newSet;
    	}
    	else {
    		return null;
    	}
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
    	ArrayMultiset newSet = new ArrayMultiset();
    	ArrayMultiset set2 = (ArrayMultiset) other;
    	int n = this.array.length;
    	if (this.array != null || set2.array != null) {
    		for (int i = 0; i < n; i++) {
    			String elem_set1 = this.array[i].getElem();
				int numElem_set1 = this.array[i].getNumElem();
				
				if (set2.contains(elem_set1)) {
					int numElem_set2 = set2.search(elem_set1);
					if (numElem_set1 < numElem_set2) {
						continue;
					}
					else if (numElem_set1 > numElem_set2) {
						int diff_numElem = numElem_set1 - numElem_set2;
						for (int j = 0; j < diff_numElem; j++) {
							newSet.add(elem_set1);
						}
					}
					else if (numElem_set1 == numElem_set2) {
						continue;
					}
				}
				else if (!set2.contains(elem_set1)) {
					for (int j = 0; j < numElem_set1; j++) {
						newSet.add(elem_set1);
					}
				}
				else {
					continue;
				}
    		}
    		return newSet;
    	}
    	else {
    		return null;
    	}
    } // end of difference()

    
    private class Info{
    	protected String elem;
    	protected int numElem = 0;
    	
    	public Info(String elem) {
    		this.elem = elem;
    	}
    	
    	public String getElem() {
    		return elem;
    	}
    	
    	public int getNumElem() {
    		return numElem;
    	}
    	
    	public void incrementNum() {
    		numElem++;
    	}
    	
    	public void decrementNum() {
    		numElem--;
    	}
    }
} // end of class ArrayMultiset
