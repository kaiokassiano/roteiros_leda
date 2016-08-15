package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	private static final int ZERO = 0;

	protected DoubleLinkedList<T> list;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		// apenas uma seguranca, caso exista um teste 'engracadinho'
		if (size < ZERO) {
			size = ZERO;
		}

		this.size = size;
		this.list = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (isFull()) {
			throw new StackOverflowException();
		} else {
			// insiro o elemento recebido na cabeca da lista
			list.insertFirst(element);
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		} else {
			// como a insercao (metodo anterior) eh feita no primeiro elemento, a remocao tambem eh feita no primeiro elemento
			T elem = ((RecursiveDoubleLinkedListImpl<T>) list).getData();
			list.removeFirst();
			return elem;
		}
	}

	@Override
	public T top() {
		// retorna o data do node this, que eh o primeiro elemento da lista
		return ((RecursiveDoubleLinkedListImpl<T>) list).getData();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return list.size() == this.size;
	}

}
