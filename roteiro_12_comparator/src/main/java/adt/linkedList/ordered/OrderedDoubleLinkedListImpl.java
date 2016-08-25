package adt.linkedList.ordered;

import java.util.Comparator;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListNode;

/**
 * Para testar essa classe voce deve implementar seu comparador. Primeiro
 * implemente todos os métodos requeridos. Depois implemente dois comparadores
 * (com idéias opostas) e teste sua classe com eles. Dependendo do comparador
 * que você utilizar a lista funcionar como ascendente ou descendente, mas a
 * implemntação dos métodos é a mesma.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class OrderedDoubleLinkedListImpl<T> extends OrderedSingleLinkedListImpl<T>
		implements OrderedLinkedList<T>, DoubleLinkedList<T> {

	private static final int ZERO = 0;
	private static final int ONE = 1;

	private DoubleLinkedListNode<T> last;
	private DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();

	public OrderedDoubleLinkedListImpl() {
		super();
		DoubleLinkedListNode<T> node = new DoubleLinkedListNode<T>(null, nilNode, nilNode);
		
		this.head = node;
		this.last = node;
	}

	public OrderedDoubleLinkedListImpl(Comparator<T> comparator) {
		super(comparator);
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<>(), null);

			if (isEmpty()) {
				this.head = newNode;
				this.last = newNode;

			} else {
				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.head;

				while (!aux.getNext().isNIL() && super.getComparator().compare(element, aux.getData()) > ZERO)
					aux = (DoubleLinkedListNode<T>) aux.getNext();

				if (aux.getPrevious() == null) {

					((DoubleLinkedListNode<T>) this.head).setPrevious(newNode);
					newNode.setNext(this.head);

					this.head = newNode;

				} else if (aux.getNext().isNIL()) {

					this.last.setNext(newNode);
					this.last = newNode;

				} else {

					newNode.setPrevious(aux.getPrevious());
					newNode.setNext(aux);

					aux.getPrevious().setNext(newNode);
					aux.setPrevious(newNode);
				}
			}
		}
	}

	@Override
	public void remove(T element) {

		if (!isEmpty()) {

			if (this.head.getData().equals(element)) {
				this.removeFirst();

			} else if (this.last.getData().equals(element)) {
				this.removeLast();

			} else {
				DoubleLinkedListNode<T> aux = (DoubleLinkedListNode<T>) this.head;

				while (!aux.isNIL() && !aux.getData().equals(element))
					aux = (DoubleLinkedListNode<T>) aux.getNext();

				if (!aux.isNIL() && aux.getData().equals(element)) {
					aux.getPrevious().setNext(aux.getNext());
					((DoubleLinkedListNode<T>) aux.getNext()).setPrevious(aux.getPrevious());
				}
			}
		}
	}

	@Override
	public void removeLast() {
		if (size() > ONE) {

			this.last.setData(null);
			this.last.setNext(null);
			DoubleLinkedListNode<T> newLast = last.getPrevious();
			this.last = newLast;

		} else if (size() == ONE) {

			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<>(null, nilNode, nilNode);
			this.head = newHead;
			this.last = newHead;
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			DoubleLinkedListNode<T> newHead = (DoubleLinkedListNode<T>) head.getNext();
			this.head = newHead;
			((DoubleLinkedListNode<T>) this.head).setPrevious(new DoubleLinkedListNode<T>());

			if (this.head.isNIL()) {
				this.head.setNext(new DoubleLinkedListNode<T>());
				this.last = (DoubleLinkedListNode<T>) this.head;
			}
		}
	}

	/**
	 * Este método faz sentido apenas se o elemento a ser inserido pode
	 * realmente ficar na primeira posição (devido a ordem)
	 */
	@Override
	public void insertFirst(T element) {
		if (element != null) {
			if (!isEmpty() && this.getComparator().compare(element, this.head.getData()) <= ZERO) {

				DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) this.head, null);
				((DoubleLinkedListNode<T>) this.head).setPrevious(newNode);

				this.head = newNode;
			} else {
				this.insert(element);
			}
		}
	}
}
