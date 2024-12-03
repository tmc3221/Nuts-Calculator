package com.example.poker_api.dsa.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A singly-linked list is a linked-memory representation of the List abstract
 * data type. This list maintains a dummy/sentinel front node in the list to
 * help promote cleaner implementations of the list behaviors. This list also
 * maintains a reference to the tail/last node in the list at all times to
 * ensure O(1) worst-case cost for adding to the end of the list. Size is
 * maintained as a global field to allow for O(1) size() and isEmpty()
 * behaviors.
 * 
 * @author Dr. King
 * @author Tristan Curtis (tmc3221)
 *
 * @param <E> the type of elements stored in the list
 */
public class SinglyLinkedList<E> extends AbstractList<E> {

    /** A reference to the dummy/sentinel node at the front of the list **/
    private LinkedListNode<E> front;
    
    /** A reference to the last/final node in the list **/
    private LinkedListNode<E> tail;
    
    /** The number of elements stored in the list **/
    private int size;
        
    /**
     * Constructs an empty singly-linked list
     */     
    public SinglyLinkedList() {
        front = new LinkedListNode<E>(null);
        tail = null;
        size = 0;
    }
    
    /**
     * Adds a node to the specified index
     * @param index we are adding at
     * @param element we are adding
     * @throws IndexOutOfBoundsException if index is invalid
     */
	@Override
	public void add(int index, E element) {
		if(index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		// Front
		if (index == 0) {
	        LinkedListNode<E> newNode = new LinkedListNode<E>(element);
	        newNode.next = front.getNext(); 
	        front.next = newNode;      
	        if (size == 0) {
	            tail = newNode;        
	        }
	        size++;
	        return;
	    }
		
		// End
		if (index == size()) {
	        LinkedListNode<E> newNode = new LinkedListNode<E>(element);
	        if (tail != null) {
	            tail.next = newNode;   
	        }
	        tail = newNode;          
	        size++;
	        return;
	    }
		
		// Else
		LinkedListNode<E> current = front;
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		
		LinkedListNode<E> newNode = new LinkedListNode<E>(element);
		newNode.next = current.getNext();
		current.next = newNode;
		size++;
	}
	
	/**
	 * Gets element at index
	 * @param index we are grabbing from
	 * @return element at the index
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		LinkedListNode<E> temp = front.next;
		
		for(int i = 0; i < index; i++) {
			temp = temp.getNext();
		}
		
		return temp.getElement();
	}
	
	/**
	 * Removes element at specified index
	 * @param index we are removing at
	 * @return the previous element
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E remove(int index) {
		if(index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		LinkedListNode<E> prev = front;
		LinkedListNode<E> current = front.getNext();
		
		for(int i = 0; i < index; i++) {
			prev = current;
			current = current.getNext();
		}
		
		// Remove
		prev.next = current.getNext();
		
		// Check tail
		if(current.next == null) {
			tail = prev;
		}
		
		size--;
		
		return current.getElement();
	}
	
	/**
	 * This method modifies the value at the specified 
	 * element
	 * @param index we are changing
	 * @param element we are changing to
	 * @return the previous element
	 * @throws IndexOutOfBoundsException for invalid index
	 */
	@Override
	public E set(int index, E element) {
		if(index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		LinkedListNode<E> current = front.getNext();
		for(int i = 0; i < index; i++) {
			current = current.getNext();
		}
		
		E oldElement = current.getElement();
		current.element = element;
		return oldElement;
	}

	/**
	 * Gets the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Constructs a new iterator
	 * @return a new iterator object
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
    /**
     * {@inheritDoc} For a singly-linked list, this behavior has O(1) worst-case
     * runtime.
     */
    @Override
    public E last() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("The list is empty");
        }
        return tail.getElement();
    }

    /**
     * {@inheritDoc}
     * For this singly-linked list, addLast(element) behavior has O(1) worst-case runtime.
     */    
    @Override
    public void addLast(E element) {
    	LinkedListNode<E> newNode = new LinkedListNode<>(element);

        // If the list is empty
        if (tail == null) {
            front.next = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        // Increase the size
        size++;
    }
    
    /**
	 * Private inner class which allows us to iterate
	 * through elements
	 * 
	 * @author Tristan Curtis (tmcurti4)
	 */
    private class ElementIterator implements Iterator<E> {
        /**
         * Keep track of the next node that will be processed
         */
        private LinkedListNode<E> current;
        
        
        /**
         * Construct a new element iterator where the cursor is initialized 
         * to the beginning of the list.
         */
        public ElementIterator() {
            current = front.next;
        }

        /**
         * Checks if there is a next node in the list
         * @return if there is next or not
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        /**
         * Goes to the next element
         * @return the next element
         * @throws NoSuchElementException if there is no next
         */
        @Override
        public E next() {
        	if(!this.hasNext()) {
	        	throw new NoSuchElementException();
	        }
        	
        	LinkedListNode<E> temp = current;
        	current = current.getNext();
        	return temp.getElement();
        }
        
        /**
         * Removes from list
         * Should not support remove
         * @throws UnsupportedOperationException if called
         */
        @Override    
        public void remove() {
    	    // DO NOT CHANGE THIS METHOD
            throw new UnsupportedOperationException(
                "This SinglyLinkedList implementation does not currently support removal of elements when using the iterator.");
        }
    }
    
    /**
     * Inner class which creates each linked list node
     * object which makes up our list
     * 
     * @author Tristan Curtis (tmcurti4)
     * @param <E> the generic data type
     */
    private static class LinkedListNode<E> {
        
    	/** The element in the list node */
        private E element;
        
        /** The next reference in the linked list */
        private LinkedListNode<E> next;
        
        /**
         * Default constructor for list node
         * @param element we are storing
         */
        public LinkedListNode(E element) {
			this(element, null);
		}
        
        /**
         * Constructs list node with a next reference
         * @param element we are storing
         * @param next node in our list
         */
        public LinkedListNode(E element, LinkedListNode<E> next) {
			this.element = element;
			this.next = next;
		}

        /**
         * Gets the element at node
         * @return element at node
         */
		public E getElement() {
        	return this.element;
        }
		
		/**
		 * Gets next node reference
		 * @return the next list node reference
		 */
        public LinkedListNode<E> getNext() {
        	return this.next;
        }
    }
    
}
