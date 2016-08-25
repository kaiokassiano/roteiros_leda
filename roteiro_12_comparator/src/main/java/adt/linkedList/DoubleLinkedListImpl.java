package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	private static final int ONE = 1;
	private DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();
	
	protected DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		super();
		DoubleLinkedListNode<T> head = new DoubleLinkedListNode<>();
		this.setHead(head);
		this.setLast(head);
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {

			if (this.getHead().getData().equals(element)) {
				this.removeFirst();

			} else if (this.getLast().getData().equals(element)) {
				this.removeLast();

			} else {
				DoubleLinkedListNode<T> node = this.getLast();
				DoubleLinkedListNode<T> remove = null;

				while (!node.isNIL() && node.getPrevious() != null) {
					if (node.getData().equals(element)) {
						remove = node;
					}
					node = node.getPrevious();
				}

				if (remove != null) {
					remove.getPrevious().setNext(remove.getNext());
					((DoubleLinkedListNode<T>) remove.getNext()).setPrevious(remove.getPrevious());
				}
			}
		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> node = new DoubleLinkedListNode<>(element, null, this.getLast());

			if (isEmpty()) {
				this.setHead(node);
				this.setLast(node);
			} else {
				this.getLast().setNext(node);
				this.setLast(node);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> node = new DoubleLinkedListNode<>(element, (DoubleLinkedListNode<T>) this.getHead(),
					null);

			((DoubleLinkedListNode<T>) this.getHead()).setPrevious(node);
			this.setHead(node);

			if (this.getLast().isNIL()) {
				this.setLast(node);

			} else if (this.getLast().getPrevious() == null) {
				this.getLast().setPrevious(node);
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
		if (size() > ONE) {

			if (this.getLast() != null && this.getLast().getPrevious() != null) {
				this.getLast().getPrevious().setNext(null);
				this.setLast(this.getLast().getPrevious());
			}

		} else if (size() == ONE) {
			this.getHead().setData(null);
			this.getLast().setData(null);
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}

}
