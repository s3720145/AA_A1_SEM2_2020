package implementation;

import java.util.List;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{
	private BstNode root;
	private int highest_num;
	
	public BstMultiset() {
		this.root = null;
	}

    @Override
	public void add(String item) {
        // Implement me!
    	BstNode newNode = new BstNode(item);
    	if (root == null) {
    		root = newNode;
    		root.incrementNum();
    		highest_num = root.getNumElem();
    	}
    	else {
    		BstNode current = root;
    		BstNode parent = null;
    		boolean status = false;
    		
    		while(status != true) {
    			parent = current;
    			if (item.equals(current.getElem()))
    			{
					current.incrementNum();
					if(current.getNumElem() > highest_num)
					{
						highest_num = current.getNumElem();
					}
    				status = true;	
    			}
    			//if item first char same as current first char in lower case
    			else if (item.toLowerCase().charAt(0) == current.getElem().toLowerCase().charAt(0)) {
    				current = current.left;
    				if (current == null) {
    					parent.left = newNode;
    					newNode.incrementNum();
    					status = true;
    				}
    			}
    			
    			else if (item.toLowerCase().charAt(0) < current.getElem().toLowerCase().charAt(0)) {
    				current = current.left;
    				if (current == null) {
    					parent.left = newNode;
    					newNode.incrementNum();
    					status = true;
    				}
    			}
    			
    			else if (item.toLowerCase().charAt(0) > current.getElem().toLowerCase().charAt(0)){
    				current = current.right;
    				if (current == null) {
    					parent.right = newNode;
    					parent.right.incrementNum();
    					status = true;
    				}
    			}
    		}
    	}
    } // end of add()


    @Override
	public int search(String item) {
    	BstNode current = root;
    	//if (root != null) {
		while(current!=null){	
			//if the item is at the root
			if (current.getElem().equals(item)){
				return current.getNumElem();
			}
			//if item is not at the root, check the first char of the item and determine the position
			else if(current.getElem().toLowerCase().charAt(0) == item.toLowerCase().charAt(0)){
				current = current.left;
			}
			else if (item.toLowerCase().charAt(0) < current.getElem().toLowerCase().charAt(0)) {
				current = current.left;
			}

			else if(item.toLowerCase().charAt(0) > current.getElem().toLowerCase().charAt(0)){
				current = current.right;
			}

		}
		return searchFailed;
	
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {

        MyList elemList = new MyList();
        BstNode current, parent;
        
        current = root; 
        while (current != null) { 
            if (current.right == null) { 
            	if(current.getNumElem() == instanceCount)
             	{              				
      				elemList.addElem(current.getElem());
             	}
                current = current.left; 
            } 
            else { 
                parent = current.right; 
                while (parent.left != null && parent.left != current) 
                    parent = parent.left; 
  
                if (parent.left == null) { 
                    parent.left = current; 
                    current = current.right; 
                } 
 
                else { 
                    parent.left = null; 
                    if(current.getNumElem() == instanceCount)
                 	{              				
          				elemList.addElem(current.getElem());
                 	}
                    current = current.left; 
                }
            } 
        }
             
        if (elemList.size() > 0) {
        	return elemList;
     	}
		return null;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) 
    {
    	BstNode current = root;
    	//if (root != null) {
		while(current!=null){	
			//if the item is at the root
			if (current.getElem().equals(item)){
				return true;
			}
			//if item is not at the root, check the first char of the item and determine the position
			else if(current.getElem().toLowerCase().charAt(0) == item.toLowerCase().charAt(0)){
				current = current.left;
			}
			else if (item.toLowerCase().charAt(0) < current.getElem().toLowerCase().charAt(0)) {
				current = current.left;
			}

			else if(item.toLowerCase().charAt(0) > current.getElem().toLowerCase().charAt(0)){
				current = current.right;
			}

		}
        return false;
    } // end of contains()


    public void removeOne(String item) {
        //check if bst containing the item
    	BstNode current = root;
    	boolean status = false;
    	BstNode parent = null;
		while(status != true)
		{
			if(current.getElem().equals(item))
			{
				//check numElem, if num of elem more thn 1, decrement the num
				//--------------------------------------------------------------->
				current.decrementNum();
				if(current.getNumElem() <= 0)
				{
					//check if right and left is null
					BstNode left = current.left;
					BstNode right = current.right;
					
					//if right is not null, 
					if(right != null)
					{
						BstNode temp = null;
						if(current != root)
						{	
							if((parent.left == current))
							{
								parent.left = right;
							}
							
							else if (parent.right == current)
							{
								parent.right = right;
							}
							current = temp;
						}
						else
						{
							root = temp;
							root = right;
							if(left != null)
							{
								root.left = left;
							}
							
						}
					}
					else if(left != null)
					{
						BstNode temp = null;
						if(current != root)
						{						
							if((parent.left == current))
							{
								parent.left = left;
							}
							else if (parent.right == current)
							{
								parent.right = left;
							}
							current = temp;
						}
						else
						{
							root = temp;
							root = left;
						}
					}
					else
					{
						//delete current;
						BstNode temp = null;
						
						if(current != root)
						{						
							if((parent.left == current))
							{
								parent.left = temp;
							}
							else if (parent.right == current)
							{
								parent.right = temp;
							}
							current = temp;
						}
						else
						{
							root = temp;
						}
						
					}
				}
				status = true;
				
			}
			else if (item.toLowerCase().charAt(0) == current.getElem().toLowerCase().charAt(0))
			{
				
				parent = current;
				current = current.left;
			}
			else if (item.toLowerCase().charAt(0) < current.getElem().toLowerCase().charAt(0))
			{
				parent = current;
				current = current.left;
			}
			else if (item.toLowerCase().charAt(0) > current.getElem().toLowerCase().charAt(0))
			{
				parent = current;
				current = current.right;
			}
		}
    } // end of removeOne()


    @Override
	public String print() { 
        BstNode current;
        BstNode parent; 
        StringBuffer temp = new StringBuffer();
  
        if (root == null) {
            return null;
        }
        for(int i = highest_num; i >= 1; i--)
        {
             current = root; 
             while (current != null) { 
                 if (current.left == null) { 
                	if(i == current.getNumElem())
                	{
                		String elem = current.getElem();
         				String num_elem = String.valueOf(current.getNumElem());
         				
         				temp.append(elem + ":" + num_elem + "\n");
                	}
                     current = current.right; 
                 } 
                 else { 
                	 parent = current.left; 
                     while (parent.right != null && parent.right != current) 
                    	 parent = parent.right; 
       
                     if (parent.right == null) { 
                    	 parent.right = current; 
                         current = current.left; 
                     } 
       
                     else { 
                    	 parent.right = null; 
                         if(i == current.getNumElem())
                     	{
                     		String elem = current.getElem();
              				String num_elem = String.valueOf(current.getNumElem());
              				
              				temp.append(elem + ":" + num_elem + "\n");
                     	}
                         current = current.right; 
                     }
                 }
             }
        }
       
        return temp.toString();
        
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

    	 BstNode current;
    	 BstNode parent;
    	 int first = calculateStrVal(lower);
	     int last = calculateStrVal(upper);
         StringBuffer temp = new StringBuffer();
   
         if (root == null) {
        	 return null;
         }
             
         for(int curVal = first; curVal <= last; curVal++)
         {
              current = root; 
              while (current != null) { 
                  if (current.left == null) { 
                	int array_total = 0;
                	for(int j = 0; j < current.getElem().length(); j++)
        	    	{
        				array_total = array_total + current.getElem().charAt(j);
        	    	}
                 	if(curVal == array_total)
                 	{
                 		String elem = current.getElem();
          				String num_elem = String.valueOf(current.getNumElem());
          				
          				temp.append(elem + ":" + num_elem + "\n");
                 	}
                      current = current.right; 
                  } 
                  else { 
                	  parent = current.left; 
                      while (parent.right != null && parent.right != current) 
                    	  parent = parent.right; 
        
                      if (parent.right == null) { 
                    	  parent.right = current; 
                          current = current.left; 
                      } 
       
                      else { 
                	  		parent.right = null; 
	                        int array_total = 0;
		                    for(int j = 0; j < current.getElem().length(); j++)
		                    {
		                    	array_total = array_total + current.getElem().charAt(j);
	              	    	}
		                    if(curVal == array_total)
	                       	{
	                       		String elem = current.getElem();
	                			String num_elem = String.valueOf(current.getNumElem());
	                				
	                			temp.append(elem + ":" + num_elem + "\n");
	                       	}
		                    current = current.right; 
                      }
                  }    
              }
         }
        
         return temp.toString();
    } // end of printRange()
    
    @Override
	public RmitMultiset union(RmitMultiset other) {
    	BstMultiset newSet = new BstMultiset();
    	BstMultiset set2 = (BstMultiset) other;
    	BstNode current_1, parent_1, current_2, parent_2;
    	//add all the elements in set1 nodes into newSet, check for same element in set2
    	//after that, check every elements in set2, if element not exists in newSet, add into the newSet
    	if (this.root != null || set2.root != null) {
            current_1 = this.root; 
            while (current_1 != null) { 
                if (current_1.right == null) { 
                	//add the curr node's elem into new set 
                	String elem_set1 = current_1.getElem();
                	int numElem_set1 = current_1.getNumElem();
                	for (int i = 0; i < numElem_set1; i++) {
                		newSet.add(elem_set1);
                	}
                	//search if set2 contains the same element, if it does, add the num of elems into the newSet
                	if (set2.contains(elem_set1)) {
                		int numElem_set2 = set2.search(elem_set1);
                		for (int i = 0; i < numElem_set2; i++) {
                			newSet.add(elem_set1);
                		}
                	}
                	
                    current_1 = current_1.left; 
                } 
                else { 
                	parent_1 = current_1.right; 
                    while (parent_1.left != null && parent_1.left != current_1) 
                        parent_1 = parent_1.left; 
      
                    if (parent_1.left == null) { 
                        parent_1.left = current_1; 
                        current_1 = current_1.right; 
                    } 
      
                    else { 
                        parent_1.left = null; 
                        String elem_set1 = current_1.getElem();
                    	int numElem_set1 = current_1.getNumElem();
                    	for (int i = 0; i < numElem_set1; i++) {
                    		newSet.add(elem_set1);
                    	}
                    	
                    	if (set2.contains(elem_set1)) {
                    		int numElem_set2 = set2.search(elem_set1);
                    		for (int i = 0; i < numElem_set2; i++) {
                    			newSet.add(elem_set1);
                    		}
                    	}
                    	
                        current_1 = current_1.left; 
                    }
                }
            }
            //----------------------------------------------------------------------------------->
            //For set 2, add in the elements that doesnt exists in newSet
            current_2 = set2.root; 
            while (current_2 != null) { 
                if (current_2.right == null) { 
                	//check whether the current node of set2 exists in newSet, if not, add in the elem into the newSet
                	if (!newSet.contains(current_2.getElem())) {
                		String elem_set2 = current_2.getElem();
                    	int numElem_set2 = current_2.getNumElem();
                    	for (int i = 0; i < numElem_set2; i++) {
                    		newSet.add(elem_set2);
                    	}
                	}
                	
                    current_2 = current_2.left; 
                } 
                else { 
                    parent_2 = current_2.right; 
                    while (parent_2.left != null && parent_2.left != current_2) 
                        parent_2 = parent_2.left; 
      
                    if (parent_2.left == null) { 
                        parent_2.left = current_2; 
                        current_2 = current_2.right; 
                    } 
      
                    else { 
                        parent_2.left = null; 
                        //check whether the current node of set2 exists in newSet, if not, add in the elem into the newSet
                    	if (!newSet.contains(current_2.getElem())) {
                    		String elem_set2 = current_2.getElem();
                        	int numElem_set2 = current_2.getNumElem();
                        	for (int i = 0; i < numElem_set2; i++) {
                        		newSet.add(elem_set2);
                        	}
                    	}
                        current_2 = current_2.left; 
                    }
                }
            }
            return newSet;
    	}
        
        return null;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	BstMultiset newSet = new BstMultiset();
    	BstMultiset set2 = (BstMultiset) other;
    	BstNode current, parent;
    	
    	//check if the element in set1 exists in set2, if it exists, take the smallest or equal number of values
    	//and add into newSet.
    	if (this.root != null || set2.root != null) {
            current = this.root; 
            while (current != null) { 
                if (current.right == null) {
                	//check if element in set1 exists in set2
                	//if exists, take the smallest/equal num and add the curr node's elem into new set 
                	String elem_set1 = current.getElem();
                	int numElem_set1 = current.getNumElem();
                	if (set2.contains(elem_set1)) {
                		int numElem_set2 = set2.search(elem_set1);
                		if (numElem_set1 < numElem_set2) {
                			for (int i = 0; i < numElem_set1; i++) {
                				newSet.add(elem_set1);
                			}
                		}
                	
                		else if (numElem_set1 > numElem_set2) {
                			for (int i = 0; i < numElem_set2; i++) {
                				newSet.add(elem_set1);
                			}
                		}
                		else if (numElem_set1 == numElem_set2) {
                			for (int i = 0; i < numElem_set1; i++) {
                				newSet.add(elem_set1);
                			}
                		}
                	}
                	
                    current = current.left; 
                } 
                else { 
                    parent = current.right; 
                    while (parent.left != null && parent.left != current) 
                        parent = parent.left; 
      
                    if (parent.left == null) { 
                        parent.left = current; 
                        current = current.right; 
                    } 
      
                    else { 
                        parent.left = null; 
                        String elem_set1 = current.getElem();
                    	int numElem_set1 = current.getNumElem();
                    	if (set2.contains(elem_set1)) {
                    		int numElem_set2 = set2.search(elem_set1);
                    		if (numElem_set1 < numElem_set2) {
                    			for (int i = 0; i < numElem_set1; i++) {
                    				newSet.add(elem_set1);
                    			}
                    		}
                    	
                    		else if (numElem_set1 > numElem_set2) {
                    			for (int i = 0; i < numElem_set2; i++) {
                    				newSet.add(elem_set1);
                    			}
                    		}
                    		else if (numElem_set1 == numElem_set2) {
                    			for (int i = 0; i < numElem_set1; i++) {
                    				newSet.add(elem_set1);
                    			}
                    		}
                    	}
                    	
                        current = current.left; 
                    }
      
                }
      
            }
            return newSet;
    	}
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {

    	BstMultiset newSet = new BstMultiset();
    	BstMultiset set2 = (BstMultiset) other;
    	BstNode current, parent;
    	
    	//check if element in set1 exists in set2, if exists and num in set1 is higher than in set2
    	//find the difference between those two num of elements and add into newSet.
    	//if it doesnt exists, add the elements into the newset
    	if (this.root != null || set2.root != null) {
            current = this.root; 
            while (current != null) { 
                if (current.right == null) {
                	String elem_set1 = current.getElem();
                	int numElem_set1 = current.getNumElem();
                	
                	if (set2.contains(elem_set1)) {
                		int numElem_set2 = set2.search(elem_set1);
                		if (numElem_set1 > numElem_set2) {
                			int diff_numElem = numElem_set1 - numElem_set2;
                			for (int i = 0; i < diff_numElem; i++) {
                				newSet.add(elem_set1);
                			}
                		}
                	}
                	else if (!set2.contains(elem_set1)) {
                		for (int i = 0; i < numElem_set1; i++) {
                			newSet.add(elem_set1);
                		}
                	}
                	
                    current = current.left; 
                } 
                else { 
                    parent = current.right; 
                    while (parent.left != null && parent.left != current) 
                        parent = parent.left; 
      
                    if (parent.left == null) { 
                        parent.left = current; 
                        current = current.right; 
                    } 
      
                    else { 
                        parent.left = null; 
                        String elem_set1 = current.getElem();
                    	int numElem_set1 = current.getNumElem();
                    	
                    	if (set2.contains(elem_set1)) {
                    		int numElem_set2 = set2.search(elem_set1);
                    		if (numElem_set1 > numElem_set2) {
                    			int diff_numElem = numElem_set1 - numElem_set2;
                    			for (int i = 0; i < diff_numElem; i++) {
                    				newSet.add(elem_set1);
                    			}
                    		}
                    	}
                    	else if (!set2.contains(elem_set1)) {
                    		for (int i = 0; i < numElem_set1; i++) {
                    			newSet.add(elem_set1);
                    		}
                    	}
                    	
                        current = current.left; 
                    }       
                } 
            }
          
            return newSet;
    	}
        return null;
    } // end of difference()
    
    public int calculateStrVal(String str)
    {
    	int total = 0;
    	for(int i = 0; i<str.length(); i++)
    	{
    		total = total + str.charAt(i);
    	}
    	return total;
    }
    
    private class BstNode{
    	private String elem;
    	private int numElem = 0;
    	private BstNode left;
    	private BstNode right;
    	
    	public BstNode(String elem) {
    		this.elem = elem;
    		left = null;
    		right = null;
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

} // end of class BstMultiset



