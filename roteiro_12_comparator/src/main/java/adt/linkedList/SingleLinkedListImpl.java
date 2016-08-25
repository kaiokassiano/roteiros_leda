package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	private static final int ZERO = 0;

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
		int size = ZERO;

		SingleLinkedListNode<T> aux = this.getHead();
		while (!aux.isNIL() && aux.getNext() != null) {
			aux = aux.getNext();
			size++;
		}

		if (!aux.isNIL()) {
			size++;
		}
		
		return size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> aux = this.getHead();
		
		while (!aux.isNIL() && aux.getNext() != null && !aux.getData().equals(element)) {
			aux = aux.getNext();
		}
		
		if (!aux.isNIL() && aux.getData().equals(element))
			return aux.getData();
		else
			return null;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			
            SingleLinkedListNode<T> aux = this.getHead();
            
            while (!aux.isNIL()) {
                aux = aux.getNext();
            }

            aux.setData(element);
            aux.setNext(new SingleLinkedListNode<T>());
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
	public T[] toArray() {

		int size = this.size();
		T[] array = (T[]) new Object[size];
		SingleLinkedListNode<T> aux = this.getHead();

		int index = ZERO;
		while (!aux.isNIL() && aux.getNext() != null) {
			array[index++] = aux.getData();
			aux = aux.getNext();
		}

		if (!aux.isNIL()) {
			array[index++] = aux.getData();
		}

		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
