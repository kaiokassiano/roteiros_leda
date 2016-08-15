package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class StackDoubleLinkedListImpl<T> implements Stack<T> {

    private static int ZERO = 0;
    
    protected DoubleLinkedList<T> list;
    protected int size;

    public StackDoubleLinkedListImpl(int size) {
        if (size < ZERO) {
            size = ZERO;
        }
        
        this.size = size;
        this.list = new DoubleLinkedListImpl<T>();
    }

    @Override
    public void push(T element) throws StackOverflowException {
        if (isFull()) {
        	throw new StackOverflowException();
        } else {
        	this.list.insert(element);
        }
    }

    @Override
    public T pop() throws StackUnderflowException {
        if (isEmpty()) {
        	throw new StackUnderflowException();
        } else {
        	T elem = this.top();
        	this.list.removeLast();
        	return elem;
        }
    }

    @Override
    public T top() {
        T elem = null;
        
    	if (!isEmpty()) {
        	elem = ((DoubleLinkedListImpl<T>) this.list).getLast().getData();
        }
    	
    	return elem;
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean isFull() {
        return this.list.size() == this.size;
    }

}