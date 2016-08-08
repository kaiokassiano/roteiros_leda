package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				this.data = element;
				this.setNext(new RecursiveDoubleLinkedListImpl<T>());
			} else {
				this.next.insert(element);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(T element) {
		if (!isEmpty()) {

			if (this.data.equals(element)) {

				if (previous != null && previous.isEmpty() && next.isEmpty()) {
					data = (T) (next = previous = null);

				} else {
					data = next.getData();
					next = next.getNext();

					if (next != null) {
						((RecursiveDoubleLinkedListImpl<T>) next).previous = this;
					}
				}

			} else {
				next.remove(element);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			// crio um node contendo o data do this, o next sendo o this.next e o previous sendo o this
			RecursiveSingleLinkedListImpl<T> add = new RecursiveDoubleLinkedListImpl<T>(this.data,
											this.next, this);

			// o next do node atual vai ser o node criado anteriormente, e sua data vai ser o element recebido
			this.next = add;
			this.data = element;
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			// crio um node como referencia para o next
			RecursiveDoubleLinkedListImpl<T> add = (RecursiveDoubleLinkedListImpl<T>) this.getNext();
			
			// o data do this eh a data do next, e o next do this eh o next do next
			this.data = add.getData();
			this.next = add.getNext();

			if (add.getNext() != null) {
				// crio uma referencia para o next do add, que foi instanciado anteriormente,
				// e defino o previous do aux como sendo o this
				RecursiveDoubleLinkedListImpl<T> aux = (RecursiveDoubleLinkedListImpl<T>) add.getNext();
				aux.previous = this;
			}
		}
	}

	@Override
	public void removeLast() {
		if (this.next != null) {
			// se o next for diferente de null, eu entro
			if (this.next.isEmpty()) {
				// se o next for vazio, eu defino a data do atual para null, bem como seu next
				this.data = null;
				this.next = null;
			
			} else {
				// se nao for vazio, chamada recursiva para o next node
				((RecursiveDoubleLinkedListImpl<T>) next).removeLast();
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
