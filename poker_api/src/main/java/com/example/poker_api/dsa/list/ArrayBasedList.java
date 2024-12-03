package com.example.poker_api.dsa.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An array-based list is a contiguous-memory representation of the List
 * abstract data type. This array-based list dynamically resizes to ensure O(1)
 * amortized cost for adding to the end of the list. Size is maintained as a
 * global field to allow for O(1) size() and isEmpty() behaviors.
 * 
 * @author Dr. King
 * @author Tristan Curtis (tmc3221)
 *
 * @param <E> the type of elements stored in the list
 */
public class ArrayBasedList<E> extends AbstractList<E> {

    /**
     * The initial capacity of the list if the client does not provide a capacity
     * when constructing an instance of the array-based list
     **/
    private final static int DEFAULT_CAPACITY = 0;

    /** The array in which elements will be stored **/
    private E[] data;

    /** The number of elements stored in the array-based list data structure **/
    private int size;
    
	/**
	 * To ensure amortized O(1) cost for adding to the end of the array-based list,
	 * use the doubling strategy on each resize. Here, we add +1 after doubling to
	 * handle the special case where the initial capacity is 0 (otherwise, 0*2 would
	 * still produce a capacity of 0).
	 * 
	 * @param minCapacity the minimum capacity that must be supported by the
	 *                    internal array
	 */
    private void ensureCapacity(int minCapacity) {
        int oldCapacity = data.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity * 2) + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            @SuppressWarnings("unchecked")
			E[] grownArray = (E[]) new Object[newCapacity];
            for (int i = 0; i < data.length; i++) {
    			grownArray[i] = data[i];
    		}

    		data = grownArray;
        }
    }

    /**
     * Constructs a new instance of an array-based list data structure with the
     * default initial capacity of the internal array
     */
    public ArrayBasedList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a new instance of an array-based list data structure with the
     * provided initial capacity
     * 
     * @param capacity the initial capacity of the internal array used to store the
     *                 list elements
     */
    @SuppressWarnings("unchecked")
    public ArrayBasedList(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }
    
    /**
     * Adds to the array
     * @param index we are adding at
     * @param element we are adding
     * @throws IndexOutOfBoundsException if index is invalid
     */
	@Override
	public void add(int index, E element) {
		// Ensure capacity for the new element
		if(data.length == size()) {
			ensureCapacity(size() + 1);
		}
		checkIndexForAdd(index);

	    // Shift elements to the right to make space for the new element
	    for (int i = size() - 1; i >= index; i--) {
	        data[i + 1] = data[i];
	    }

	    // Insert the new element at the specified index
	    data[index] = element;
	    
	    // Update the size of the list
	    size++;
	}

	/**
	 * Gets an element from the array
	 * @param index we are grabbing from
	 * @return element at index
	 * @throws IndexOutOfBoundsException if invalid index
	 */
	@Override
	public E get(int index) {
		checkIndex(index);
		return data[index];
	}

	/**
	 * Removes element at index
	 * @param index we are removing from
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E remove(int index) {
		checkIndex(index);
		
	    // Store the element to be removed
	    E removedElement = data[index];

	    // Shift elements to the left to fill the gap
	    for (int i = index; i < size() - 1; i++) {
	        data[i] = data[i + 1];
	    }

	    // Nullify the last element and update the size
	    data[size() - 1] = null;
	    size--;

	    return removedElement;
	}

	/**
	 * Sets the element at the index
	 * @param index we are setting
	 * @param element we are setting to
	 * @return the previous element
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E set(int index, E element) {
		checkIndex(index);
		
		E old = data[index];
		data[index] = element;
		
		return old;
	}
	
	/**
	 * Gets the size of the list
	 * @return the size of list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Constructs a new iterator object
	 * @return a new iterator
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	/**
	 * Private inner class which allows us to iterate
	 * through elements
	 * 
	 * @author Tristan Curtis (tmcurti4)
	 */
	private class ElementIterator implements Iterator<E> {
	    
		/** Position we are at */
		private int position;
		
		/** Remove flag */
	    private boolean removeOK;

	    /**
	     * Construct a new element iterator where the cursor is initialized 
	     * to the beginning of the list.
	     */
	    public ElementIterator() {
	       position = 0;
	       removeOK = false;
	    }

	    /**
	     * Checks if there is a next element in the list
	     * @return if the list has next or not
	     */
	    @Override
	    public boolean hasNext() {
	    	return position < size();
	    }
	    
	    /**
	     * Moves to the next position
	     * @return the next element
	     * @throws NoSuchElementException if there is not next
	     */
	    @Override
	    public E next() {
	        if(!this.hasNext()) {
	        	throw new NoSuchElementException();
	        }
	        removeOK = true;
	        position++;
	        return data[position - 1];
	    }
	    
	    /**
	     * Removes the last element returned by the iterator
	     * @throws IllegalStateException if remove flag isnt correct
	     */
	    @Override
	    public void remove() {
	        if(removeOK) {
	        	for(int i = position - 1; i < size() - 1; i++) {
	        		data[i] = data[i + 1];
	        	}
	        	data[size() - 1] = null;
	        	size--;
	        	position--;
	        	removeOK = false;
	        } else {
	        	throw new IllegalStateException();
	        }
	    }
	}
    
}
