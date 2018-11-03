package hash;

import java.util.LinkedList;
import java.util.ListIterator;

//
// STRINGTABLE.JAVA
// A hash table mapping Strings to their positions in the the pattern sequence
// You get to fill in the methods for this part.
//
public class StringTable {
    
    private LinkedList<Record>[] buckets;
    private int nBuckets;

    //
    // number of records currently stored in table --
    // must be maintained by all operations
    //
    public int size;
    
    
    //
    // Create an empty table with nBuckets buckets
    //
    @SuppressWarnings("unchecked")
	public StringTable(int nBuckets)
    {
    	this.nBuckets = nBuckets;
    	buckets = new LinkedList[nBuckets];
    	// each node of the array is a LinkedList
    	size = 0;
    	
//    	initialize each node of the array a LinkedList
    	for (int i = 0; i < nBuckets; i++) {
    		buckets[i] = new LinkedList<Record>();
    	}
    }
    
    
    /**
     * insert - inserts a record to the StringTable
     *
     * @param r
     * @return true if the insertion was successful, or false if a
     *         record with the same key is already present in the table.
     */
    public boolean insert(Record r) 
    {
    	String r_key = r.key;
    	int index = toIndex(stringToHashCode(r_key));
    	
    	LinkedList<Record> indexlist = buckets[index];
    	if (indexlist.isEmpty() == true) {
    		indexlist.add(r);
    		size = size + 1;
    		return true;
    	}
    	else {
    		ListIterator<Record> insert_Itr = indexlist.listIterator();
    		while (insert_Itr.hasNext()) {
    			Record insert_list = insert_Itr.next();
    			if (r_key.equals(insert_list.key)) {
    				return false;
    			}
    		}
    		
    		indexlist.addLast(r);
    		size = size + 1;
    		return true;
    	}
    }
    
    
    /**
     * find - finds the record with a key matching the input.
     *
     * @param key
     * @return the record matching this key, or null if it does not exist.
     */
    public Record find(String key) 
    {
    	int Buc_index = toIndex(stringToHashCode(key));
    	LinkedList<Record> find_List = buckets[Buc_index];
    	
    	if (find_List.isEmpty() == true) {
    		return null;
    	}
    	else {
    		ListIterator<Record> find_Itr = find_List.listIterator();
    	    while (find_Itr.hasNext()) {
    	    	Record find_in_list = find_Itr.next();
    	    	if (key.equals(find_in_list.key)) {
    			return find_in_list;
    		    }
    	   
    	    } 
		    return null;
    	}
    }
    
    
    /**
     * remove - finds a record in the StringTable with the given key
     * and removes the record if it exists.
     *
     * @param key
     */
    public void remove(String key) 
    {
    	int bucInd = toIndex(stringToHashCode(key));
    	LinkedList<Record> rm_List = buckets[bucInd];
    	
    	if (rm_List.isEmpty() == false) {
    		
    		ListIterator<Record> rmItr =rm_List.listIterator();
    	
    		while (rmItr.hasNext()) {
    			Record rm_in_list = rmItr.next();
    			if (key.equals(rm_in_list.key)) {
    				size = size - 1;
    				rm_List.remove(rm_in_list);
    				break;
    			}
    		}
    	}
//    	else {
//    		break;
//    	}
    }
    

    /**
     * toIndex - convert a string's hashcode to a table index
     *
     * As part of your hashing computation, you need to convert the
     * hashcode of a key string (computed using the provided function
     * stringToHashCode) to a bucket index in the hash table.
     *
     * You should use a multiplicative hashing strategy to convert
     * hashcodes to indices.  If you want to use the fixed-point     
     * computation with bit shifts, you may assume that nBuckets is a
     * power of 2 and compute its log at construction time.
     * Otherwise, you can use the floating-point computation.
     */
    private int toIndex(int hashcode)
    {
    	double A = 0.095;
    	double a = (hashcode*A)%1.0;
    	
    	int hash_index = Math.abs((int)((a*nBuckets)));
    	
    	return hash_index;
    }
    
    
    /**
     * stringToHashCode
     * Converts a String key into an integer that serves as input to
     * hash functions.  This mapping is based on the idea of integer
     * multiplicative hashing, where we do multiplies for successive
     * characters of the key (adding in the position to distinguish
     * permutations of the key from each other).
     *
     * @param string to hash
     * @returns hashcode
     */
    int stringToHashCode(String key)
    {
    	int A = 1952786893;
	
    	int v = A;
    	for (int j = 0; j < key.length(); j++)
	    {
    		char c = key.charAt(j);
    		v = A * (v + (int) c + j) >> 16;
	    }
	
    	return v;
    }

    /**
     * Use this function to print out your table for debugging
     * purposes.
     */
    public String toString() 
    {
    	StringBuilder sb = new StringBuilder();
	
    	for(int i = 0; i < nBuckets; i++) 
	    {
    		sb.append(i+ "  ");
    		if (buckets[i] == null) 
		    {
    			sb.append("\n");
    			continue;
		    }
    		for (Record r : buckets[i]) 
		    {
    			sb.append(r.key + "  ");
		    }
    		sb.append("\n");
	    }
    	return sb.toString();
    }
}



