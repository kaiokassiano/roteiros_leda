package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparação não é feita diretamente com os elementos
 * armazenados, mas sim usando um comparator. Dessa forma, dependendo do
 * comparator, a heap pode funcionar como uma max-heap ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	private static final int ZERO = 0;

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = (T[]) new Comparable[(index + 1)];
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		int leftIndex = this.left(position);
		int rightIndex = this.right(position);
		int aux = position;

		if (leftIndex <= this.index) {
			aux = leftIndex;
		}

		if (rightIndex <= this.index) {
			aux = maiorElemento(this.heap, leftIndex, rightIndex);
		}

		aux = maiorElemento(this.heap, position, aux);

		if (aux != position) {
			Util.swap(this.heap, aux, position);
			this.heapify(aux);
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////

		if (element != null) {

			this.index++;
			this.heap[this.index] = element;
			int i = this.index;

			while (maiorElemento(this.heap, i, this.parent(i)) == i && this.parent(i) != i) {
				Util.swap(this.heap, i, this.parent(i));
				i = this.parent(i);
			}
		}
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = array.length - 1;

		// Ate a metade, ja que da metade pra o fim todos os elementos sao
		// folhas
		for (int i = array.length / 2; i >= ZERO; i--) {
			this.heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		T value = this.rootElement();
		this.remove(ZERO);
		return value;
	}

	@Override
	public T rootElement() {
		return this.heap[ZERO];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] heapsort(T[] array) {
		// Salvando o comparator antigo.
		Comparator<T> comparator = this.comparator;

		this.index = -1;

		// Campelo mandou um e-mail, dizendo que era pra ordenar sempre de forma
		// crescente. Entao, to garantindo isso na linha abaixo.
		this.comparator = (a, b) -> b.compareTo(a);

		buildHeap(array);

		T[] newArray = (T[]) (new Comparable[this.size()]);

		for (int index = ZERO; index < newArray.length; index++) {
			newArray[index] = this.extractRootElement();
		}

		// Isso aqui eh apenas pra fazer com que a heap volte ao estado normal, ja que os elementos foram removidos
		// exemplo: ao inves dela ficar [int, int, int, int] com o index sendo -1, ela vai ficar [null, null, null, null]
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);

		// Retorno o comparator ao estado inicial
		this.comparator = comparator;

		return newArray;
	}

	@Override
	public int size() {
		return index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}

	// Metodos abaixo criados para ajudar na
	// modularizacao/legibilidade/implementacao.

	/**
	 * Retorna o indice do maior entre dois elementos.
	 * 
	 * @param array
	 * @param x
	 * @param y
	 * @return
	 */
	private int maiorElemento(T[] array, int elem1, int elem2) {
		if (this.comparator.compare(array[elem1], array[elem2]) > ZERO)
			return elem1;
		else
			return elem2;
	}

	/**
	 * Remove um elemento da heap. Esse metodo eh usado apenas para a remocao da
	 * root.
	 * 
	 * @param index
	 */
	private void remove(int index) {
		if (this.index >= ZERO) {
			Util.swap(this.heap, index, this.index--);
			heapify(index);
		}
	}

}
