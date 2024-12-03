package com.example.poker_api.dsa.list;

/**
 * A skeletal implementation of the List abstract data type. This class provides
 * implementation for common methods that can be implemented the same no matter
 * what specific type of concrete data structure is used to implement the list
 * abstract data type.
 * 
 * @author Dr. King
 * @author Tristan Curtis (tmc3221)
 *
 * @param <E> the type of elements stored in the list
 */
public abstract class AbstractList<E> implements List<E> {

    @Override
    public void addFirst(E element) {
        add(0, element);
    }

    @Override
    public void addLast(E element) {
        add(size(), element);
    }

    /**
     * Checks whether the provided index is a legal index based on the current state
     * of the list. This check should be performed when accessing any specific
     * indexes within the list.
     * 
     * @param index the index for which to check whether it is valid/legal in the
     *              current list or not
     */
    protected void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index is invalid: " + index + " (size=" + size() + ")");
        }
    }

    /**
     * Checks whether the provided index is a legal index based on the current state
     * of the list. This check should be performed when adding elements at specific
     * indexes within the list.
     * 
     * @param index the index for which to check whether it is valid/legal in the
     *              current list or not
     */
    protected void checkIndexForAdd(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index is invalid: " + index + " (size=" + size() + ")");
        }
    }

    /**
     * Gets first element
     * @return first element
     */
    @Override
    public E first() {
        return get(0);
    }
    
    /**
     * Checks if empty or not
     * @return if empty
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Gets last element
     * @return last element
     */
    @Override
    public E last() {
        return get(size() - 1);
    }

    /**
     * Removes first element
     * @return removed element
     */
    @Override
    public E removeFirst() {
        return remove(0);
    }

    /**
     * Removes last element
     * @return removed element
     */
    @Override
    public E removeLast() {
        return remove(size() - 1);
    }
}