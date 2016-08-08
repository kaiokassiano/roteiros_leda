package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	private static final int ZERO = 0;
	
	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data,
			RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {	
		return this.data == null;
	}

	@Override
	public int size() {
		int sum = ZERO;
		
		if (isEmpty()) {
			return sum;
		} else {
			sum++;
			return sum + next.size();
		}
	}

	@Override
	public T search(T element) {
		T value = null;
		
		if (isEmpty()) {
			// se ta vazia, entao o retorno eh null
			return value;
			
		} else if (this.data.equals(element)) {
			// se o data for igual ao valor que recebeu, retorna data
			return value = element;
		
		} else {
			// se nao passou em nenhuma anterior, chamada recursiva pra o next element
			return this.next.search(element);
		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			
			if (isEmpty()) {
				// se esse node ta vazio, o data vai ser o que recebeu, e o next dele vai ser um NIL
				setData(element);
				this.next = new RecursiveSingleLinkedListImpl<T>();
			
			} else {
				// se nao ta vazio, chamada recursiva pra o proximo
				next.insert(element);
			}
		}
		
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			// so faz se nao estiver vazio
			// se chegar no ultimo node e nao encontrar, a lista nao se altera, pois o elemento nao foi encontrado
			
			if (this.data.equals(element)) {
				// se o data for igual ao que recebeu, o data vai ser o data do next, e o next eh o next do next
				this.data = next.data;
				this.next = next.next;
			
			} else {
				// se nao for igual a data, chamada recursiva pra o proximo
				this.next.remove(element);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		// tamanho do array
		int size = size();
		
		// array com o tamanho da lista
		T[] array = (T[]) new Object[size];
		// metodo recursivo, que adiciona o data do node em cada indice do array, comecando em zero
		recursiveToArray(array, this, ZERO);
		
		return array;
	}
	
	private void recursiveToArray(T[] array, RecursiveSingleLinkedListImpl<T> node, int index) {
		if (!node.isEmpty()) {
			// se o node nao estiver vazio, pega o data do node, coloco no array, e incremento o indice de controle
			// depois, chamada recursiva, e vai parar quando o ultimo node for vazio 
			array[index] = (T) node.data;
			index++;
			recursiveToArray(array, node.next, index);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
