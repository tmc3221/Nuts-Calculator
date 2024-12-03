package com.example.poker_api.dsa.map;

import java.util.Iterator;

import com.example.poker_api.dsa.list.List;
import com.example.poker_api.dsa.list.SinglyLinkedList;


/**
 * A skeletal implementation of the Map abstract data type. This class provides
 * implementation for common methods that can be implemented the same no matter
 * what specific type of concrete data structure is used to implement the map
 * abstract data type.
 * 
 * @author Dr. King
 * @author Tristan Curtis (tmc3221)
 *
 * @param <K> the type of keys stored in the map
 * @param <V> the type of values that are associated with keys in the map
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    /**
     * MapEntry implements the Entry abstract data type.
     * 
     * @author Dr. King
     *
     * @param <K> the type of key stored in the entry
     * @param <V> the type of value stored in the entry
     */
    protected static class MapEntry<K, V> implements Entry<K, V> {

    	/** The key for the entry */
        private K key;
        
        /** The value for the entry */
        private V value;

        /**
         * Constructs a MapEntry with a provided key and a provided value
         * 
         * @param key   the key to store in the entry
         * @param value the value to store in the entry
         */
        public MapEntry(K key, V value) {
            setKey(key);
            setValue(value);
        }

        /**
         * Return the key
         * @return the key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Return the value
         * @return the value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Set the key of the entry to the provided key
         * 
         * @param key the key to store in the entry
         */
        private void setKey(K key) {
            this.key = key;
        }

        /**
         * Set the value of the entry to the provided value
         * 
         * @param value the value to store in the entry
         */
        public void setValue(V value) {
            this.value = value;
        }
        
        /**
         * Compares two entries
         * @param o the entry we are comparing
         * @return the comparison value
         */
        @SuppressWarnings("unchecked")
        @Override
        public int compareTo(Entry<K, V> o) {
            return ((Comparable<K>)this.key).compareTo(o.getKey());
        }       
    }
    
    /**
     * EntryCollection implements the {@link Iterable} interface to allow traversing
     * through the entries stored in the map. EntryCollection does not allow removal
     * operations
     * 
     * @author Dr. King
     *
     */
    protected class EntryCollection implements Iterable<Entry<K, V>> {

    	/** Our list for our collection of entries */
        private List<Entry<K, V>> list;

        /**
         * Constructor for our entry collection
         */
        public EntryCollection() {
            list = new SinglyLinkedList<Entry<K, V>>();
        }

        /**
         * Adds an entry to the collection
         * @param entry we are adding
         */
        public void add(Entry<K, V> entry) {
            list.addLast(entry);
        }

        /**
         * Constructs a new iterator for our collection
         * @return the new iterator
         */
        public Iterator<Entry<K, V>> iterator() {
            return new EntryCollectionIterator();
        }

        
        /**
         * Private inner class that impliments our entry collection iterator
         * 
         * @author Tristan Curtis (tmcurti4)
         * @author Dr. King
         * 
         */
        private class EntryCollectionIterator implements Iterator<Entry<K, V>> {

        	/** Our iterator for entries */
            private Iterator<Entry<K, V>> it;

            /**
             * Constructor for our list iterator
             */
            public EntryCollectionIterator() {
                it = list.iterator();
            }

            /**
             * Checks if the iterator has next
             * @return if there is a next element or not
             */
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            /**
             * Goes to the next element in the list
             * @return the next element
             */
            @Override
            public Entry<K, V> next() {
                return it.next();
            }

            /**
             * Removes from list
             * Not supported
             * @throws UnsupportedOperationException if called
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("The remove operation is not supported yet.");
            }
        }
    }      

    /**
     * KeyIterator implements the {@link Iterator} interface to allow traversing
     * through the keys stored in the map
     * 
     * @author Dr. King
     *
     */
    protected class KeyIterator implements Iterator<K> {

    	/** Holds the iterator for our keys */
        private Iterator<Entry<K, V>> it;
        
        /**
         * Creates a new iterator for our keys
         */
        public KeyIterator() {
            it = entrySet().iterator();
        }
        
        /**
         * Checks if there is a next element in the list
         * @return if there is a next element in the list
         */
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        /**
         * Goes to next element in the list
         * @return the next key in list
         */
        @Override
        public K next() {
            return it.next().getKey();
        }
        
        /**
         * Remove from our iterator
         * Not supported
         * @throws UnsupportedOperationException if called
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("The remove operation is not supported yet.");
        }
    }

    /**
     * ValueIterator implements the {@link Iterator} interface to allow traversing
     * through the values stored in the map
     * 
     * @author Dr. King
     *
     */
    protected class ValueIterator implements Iterator<V> {
    	
    	/** The iterator for the values, hold order */
    	private Iterator<Entry<K, V>> it;

    	/**
    	 * Constructs a new value iterator
    	 */
        public ValueIterator() {
            it = entrySet().iterator();
        }

        /**
         * Checks for a next reference in the list
         * @return if there is a next reference or not
         */
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        /**
         * Gets the value at the next reference
         * @return the value at the next reference
         */
        @Override
        public V next() {
            return it.next().getValue();
        }

        /**
         * Remove from the list
         * Unsupported
         * @throws UnsupportedOperationException if called
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("The remove operation is not supported yet.");
        }
    }

    /**
     * Returns if the map is empty or not
     * @return if it is empty or not
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Creates a new key iterator
     * @return the new key iterator
     */
    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    /**
     * Creates a new value iterator
     * @return the new value iterator
     */
    @Override
    public Iterable<V> values() {
        return new ValueIterable();
    }

    /**
     * Private inner class for the value iterator
     * Wrapper for value iterator
     * 
     * @author Tristan Curtis (tmcurti4)
     * @author Dr. King
     * 
     */
    private class ValueIterable implements Iterable<V> {
    	
    	/**
    	 * Creates a new iterator for the values
    	 * @return the new value iterator
    	 */
    	@Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }
    }
    
    

}