package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	private static final int ZERO = 0;
	
	protected DoubleLinkedList<T> list;
	protected int size;
	
	public QueueDoubleLinkedListImpl(int size) {
		if (size < ZERO) {
			size = ZERO;
		}
		
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}
	
	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		} else {
			this.list.insert(element);
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
			
		} else {
			T elem = this.head();
			this.list.removeFirst();
			return elem;
		}
	}

	@Override
	public T head() {
		T elem = null;
		
		if (!isEmpty()) {
			elem = ((DoubleLinkedListImpl<T>) list).getHead().getData();
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
