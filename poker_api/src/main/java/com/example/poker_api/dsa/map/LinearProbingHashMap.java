package com.example.poker_api.dsa.map;

/**
 * The LinearProbingHashMap is implemented as a hash table that uses linear
 * probing for collision resolution.
 * 
 * The hash map uses a multiply-and-divide compression strategy for calculating
 * hash functions. The hash map ensures expected O(1) performance of
 * {@link Map#put}, {@link Map#get}, and {@link Map#remove}.
 * 
 * The hash table resizes if the load factor exceeds 0.5.
 * 
 * The LinearProbingHashMap class is based on the implementation developed for
 * use with the textbook:
 *
 * Data Structures and Algorithms in Java, Sixth Edition Michael T. Goodrich,
 * Roberto Tamassia, and Michael H. Goldwasser John Wiley and Sons, 2014
 * 
 * @author Dr. King
 * @author Tristan Curtis (tmc3221)
 *
 * @param <K> the type of keys stored in the hash map
 * @param <V> the type of values associated with keys in the hash map
 */
public class LinearProbingHashMap<K, V> extends AbstractHashMap<K, V> {

	/** Our table storing our entries */
    private TableEntry<K, V>[] table;
    
    /** Stores our size of the table */
    private int size;

    /**
     * Constructs a new linear probing hash map that uses natural ordering of keys
     * when performing comparisons. The created hash table uses the
     * {@link AbstractHashMap#DEFAULT_CAPACITY}
     */
    public LinearProbingHashMap() {
        this(AbstractHashMap.DEFAULT_CAPACITY);
    }

    /**
     * Constructs a new linear probing hash map that uses natural ordering of keys
     * when performing comparisons. The created hash table is initialized to have
     * the provided capacity.
     * 
     * @param capacity the initial capacity of the hash table
     */
    public LinearProbingHashMap(int capacity) {
        this(capacity, false);
    }

    /**
     * FOR TESTING PURPOSES ONLY! Constructs a new linear probing hash map that uses
     * natural ordering of keys when performing comparisons. The created hash table
     * is initialized to have the provided capacity.
     * 
     * @param capacity  the initial capacity of the hash table
     * @param isTesting if true, the hash table uses a predictable series of random
     *                  values for deterministic and repeatable testing
     */
    public LinearProbingHashMap(int capacity, boolean isTesting) {
        super(capacity, isTesting);
        size = 0;
    }
    
    /**
     * Creates an iterable set of entries
     * @return the iterable set of entries 
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
    	EntryCollection collection = new EntryCollection();
    	for (int i = 0; i < table.length; i++) {
            TableEntry<K, V> entry = table[i];
            
            if (entry != null && !entry.isDeleted()) {
                collection.add(entry);
            }
        }
    	
    	return collection;
    }

    /**
     * Creates a table for our hash table
     * @param capacity our capacity for the table
     */
    @SuppressWarnings("unchecked")
    @Override
    public void createTable(int capacity) {
        table = (TableEntry<K, V>[]) new TableEntry[capacity];
        size = 0;
    }

    /**
     * Checks if the bucket is available
     * @param index the index we are checking
     * @return if it is available or not
     */
    private boolean isAvailable(int index) {
    	return table[index] == null || table[index].isDeleted();
    }

    /**
     * Gets the bucket at the specified hash with the specified key
     * @param hash the hash code we are using
     * @param key the key we are getting
     * @return the value at the key
     */
    @Override
    public V bucketGet(int hash, K key) {
        int bucket = findBucket(hash, key);
        
        if(bucket >= 0) {
        	return table[bucket].getValue();
        }
        
        return null;
    }

    /**
     * Puts a key and value at the bucket based on
     * the hash
     * @param hash the hash code we are using
     * @param key the key we are storing
     * @param value the value we are storing
     * @return the previous value
     */
    @Override
    public V bucketPut(int hash, K key, V value) {
        int bucket = findBucket(hash, key);
        
        if(bucket >= 0) {
        	V oldValue = table[bucket].getValue();
        	table[bucket].setValue(value);
        	
        	return oldValue;
        }
        
        int avail = -1 * (bucket + 1);
        table[avail] = new TableEntry<>(key, value);
        size++;
        return null;
    }

    /**
     * Private helper method which finds the bucket at an index and key
     * @param index the index we are searching for
     * @param key the key we are searching for
     * @return the location of the bucket
     */
    private int findBucket(int index, K key) {
        int avail = -1;
        int j = index;
        
        do {
        	if(isAvailable(j)) {
        		if(avail == -1) {
        			avail = j;
        		}
        		if(table[j] == null) {
        			return -1 * (avail + 1);
        		}
        	} else if(table[j].getKey().equals(key)) {
        		return j;
        	}
        	j = (j + 1) % table.length;
        } while (j != index);
        
        return -1 * (avail + 1);
    }

    /**
     * Removes the bucket at the hash with the
     * specifed key
     * @param hash the hash code we are using
     * @param key the key we are removing
     * @return the removed value
     */
    @Override
    public V bucketRemove(int hash, K key) {
        int bucket = findBucket(hash, key);
        
        if(bucket >= 0) {
        	V oldValue = table[bucket].getValue();
        	table[bucket].setDeleted(true);
        	size--;
        	return oldValue;
        }
        
        return null;
    }

    /**
     * Gets the size of the hash table
     * @return the size of the table
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Gets the capacity of the hash table
     * @return the hash table capacity
     */
    @Override
    protected int capacity() {
        return table.length;
    }

    /**
     * Private static class which stores a new element and a field
     * to indicate if an element at the bucket is deleted or not
     * 
     * @author Tristan Curtis (tmcurti4)
     * @author Dr. King
     * 
     * @param <K> The type of key stored
     * @param <V> The type of value stored
     */
    private static class TableEntry<K, V> extends MapEntry<K, V> {

    	/** Boolean flag checking if bucket is deleted */
        private boolean isDeleted;

        /**
         * Constructs a table entry
         * @param key the key we are adding into the bucket
         * @param value the value we are adding into the bucket
         */
        public TableEntry(K key, V value) {
            super(key, value);
            setDeleted(false);
        }

        /**
         * Checks if the entry is deleted or not
         * @return if the entry is deleted
         */
        public boolean isDeleted() {
            return isDeleted;
        }

        /**
         * Sets the delete flag for an entry
         * @param deleted the value we are setting to
         */
        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }
    }
}