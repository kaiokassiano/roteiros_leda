package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	private DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();

	@Override
	public void insert(T element) {
		if (element != null) {
			
			DoubleLinkedListNode<T> elem = new DoubleLinkedListNode<T>(element, nilNode, last);

			if (isEmpty()) {
				super.head = last = elem;
			} else {
				last.next = elem;
				last = elem;
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {

			if (!isEmpty()) {

				if (head.getData().equals(element)) {
					head = head.getNext();

				} else if (size() > ONE) {
					SingleLinkedListNode<T> aux = head.getNext();

					while (!aux.isNIL() && !aux.getData().equals(element)) {
						aux = aux.getNext();
					}

					if (aux.getData().equals(element)) {

						DoubleLinkedListNode<T> toRemove = (DoubleLinkedListNode<T>) aux,
								previous = toRemove.getPrevious(), next = (DoubleLinkedListNode<T>) toRemove.getNext();

						if (!next.isNIL()) {
							next.setPrevious(previous);
						}

						previous.setNext(next);
					}
				}
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> head = (DoubleLinkedListNode<T>) this.getHead();
			DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<T>(element, head, nilNode);

			head.setPrevious(newNode);
			this.setHead(newNode);

			if (this.getLast().isNIL()) {
				this.setLast(newNode);
			} else if (this.getLast().getPrevious() == null) {
				this.getLast().setPrevious(newNode);
			}

		}

	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {

			if (size() > ONE) {
				DoubleLinkedListNode<T> elem = (DoubleLinkedListNode<T>) this.getHead().getNext();
				elem.setPrevious(nilNode);
				this.setHead(elem);
				
			} else {
				this.setHead(nilNode);
				this.setLast(nilNode);
			}

		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {

			if (size() == ONE) {
				super.head = this.last = (DoubleLinkedListNode<T>) super.head.getNext();
				
			} else {
				this.last.getPrevious().setNext(nilNode);
				this.last = this.last.getPrevious();
			}

		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
