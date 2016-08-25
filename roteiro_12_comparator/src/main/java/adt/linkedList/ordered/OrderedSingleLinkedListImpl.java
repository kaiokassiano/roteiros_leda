package adt.linkedList.ordered;

import java.util.Comparator;

import adt.linkedList.SingleLinkedListImpl;
import adt.linkedList.SingleLinkedListNode;

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
public class OrderedSingleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements OrderedLinkedList<T> {

	private Comparator<T> comparator;
	private static final int ZERO = 0;

	public OrderedSingleLinkedListImpl() {
	}

	public OrderedSingleLinkedListImpl(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			super.insert(element);

		} else {
			SingleLinkedListNode<T> node = this.getHead();
			SingleLinkedListNode<T> previous = null;

			while (node.getData() != null && this.comparator.compare(element, node.getData()) > ZERO) {
				previous = node;
				node = node.getNext();
			}

			SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, node);

			if (previous == null)
				this.setHead(newNode);
			else
				previous.setNext(newNode);

		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {

			if (element.equals(head.getData())) {
				this.head = head.getNext();

			} else {

				SingleLinkedListNode<T> previous = this.head;
				SingleLinkedListNode<T> aux = this.head.getNext();

				while (aux != null && !aux.isNIL()) {
					if (element.equals(aux.getData())) {
						previous.setNext(aux.getNext());
					}

					previous = aux;
					aux = aux.getNext();
				}
			}
		}
	}

	@Override
	public T minimum() {
		if (isEmpty()) {
			return null;
		} else {
			return this.getHead().getData();
		}
	}

	@Override
	public T maximum() {

		if (this.isEmpty() || this.hasComparator()) {
			return null;
		} else {
			SingleLinkedListNode<T> aux;
			
	        for (aux = this.getHead(); !aux.getNext().isNIL() || aux.isNIL() ; aux = aux.getNext());
	        
	        return aux.getData();
		}
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	// fica tranquilo(a) com esse metodo aqui :)
	private boolean hasComparator() {
		return this.comparator == null;
	}
}
