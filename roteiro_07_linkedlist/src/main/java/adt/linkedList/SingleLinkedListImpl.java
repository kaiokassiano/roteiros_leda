package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	
	protected static final int ONE = 1;
	
	protected SingleLinkedListNode<T> head;
	
	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return (this.getHead().isNIL());
	}

	@Override
	public int size() {
		int size = 0;
		
		if (!isEmpty()) {
			SingleLinkedListNode<T> aux = this.getHead();
			
			while (!aux.isNIL()) {
				size++;
				aux = aux.getNext();
			}
		}
		
		return size;
	}

	@Override
	public T search(T element) {
		T ptr = null;
		
		if (element != null) {
			
			if (!isEmpty()) {
				
				SingleLinkedListNode<T> aux = this.getHead();
				
				while (!aux.isNIL()) {
					
					if (aux.getData().equals(element)) {
						return aux.getData();
					}
					
					aux = aux.getNext();
				}
			}
		}
		
		return ptr;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			
			SingleLinkedListNode<T> next = new SingleLinkedListNode<T>();
			
			if (isEmpty()) {
				SingleLinkedListNode<T> newHead = new SingleLinkedListNode<T>(element, next);
				this.setHead(newHead);
				
			} else {
				SingleLinkedListNode<T> aux = this.getHead();
				
				while (!aux.isNIL())
					aux = aux.getNext();
				
				aux.setData(element);
				aux.setNext(next);
			}
		}
		
	}

	@Override
	public void remove(T element) {
		if (element != null && !isEmpty()) {
			
			if (this.head.getData().equals(element)) {
				head = head.getNext();
				
			} else {
				SingleLinkedListNode<T> previous = new SingleLinkedListNode<T>();
				SingleLinkedListNode<T> aux = this.getHead();
				
				while (!aux.isNIL() && !aux.getData().equals(element)) {
					previous = aux;
					aux = aux.getNext();
				}
				
				if (!aux.isNIL()) {
					previous.setNext(aux.getNext());
				}
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray(){
		int size = this.size();
		int index = 0;
		
		T[] array = (T[]) new Object[size];
		SingleLinkedListNode<T> aux = this.getHead();
		
		while (!aux.isNIL()) {
			array[index] = aux.getData();
			aux = aux.getNext();
			index++;
		}
		
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	// esse metodo eh perigoso.
	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

	
}
