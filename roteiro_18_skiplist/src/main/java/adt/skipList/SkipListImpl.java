package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	private static final int ZERO = 0;
	private static final int SENTINEL_NODES_COUNT = 2;

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int height;
	protected int maxHeight;

	protected boolean USE_MAX_HEIGHT_AS_HEIGHT;
	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			this.height = maxHeight;
		} else {
			this.height = 1;
		}
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL. Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			for (int i = maxHeight; i >= 0; i++) {
				root.forward[i] = NIL;
			}
		} else {
			root.forward[ZERO] = NIL;
		}
	}

	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no
	 * metodo insert(int,V)
	 */
	private int randomLevel() {
		int randomLevel = 1;
		double random = Math.random();
		while (Math.random() <= PROBABILITY && randomLevel < maxHeight) {
			randomLevel = randomLevel + 1;
		}
		return randomLevel;
	}

	@Override
	public int size() {
		int size = ZERO;

		SkipListNode<T> aux = this.root.forward[ZERO];

		while (aux != null && !aux.equals(NIL)) {
			size++;
			aux = aux.forward[ZERO];
		}

		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(int key, T newValue, int height) {
		if (height <= this.maxHeight) {

			updateRootPointers(height);

			SkipListNode<T>[] savedPointers = new SkipListNode[this.height];
			SkipListNode<T> aux = this.root;

			for (int i = this.height - 1; i >= 0; i--) {
				while (aux.getForward(i) != null && aux.getForward(i).getKey() < key)
					aux = aux.getForward(i);

				savedPointers[i] = aux;
			}

			aux = aux.forward[ZERO];

			if (aux.getKey() == key)
				aux.setValue(newValue);
			else {
				// finalmente, faz a insercao
				aux = new SkipListNode<T>(key, height, newValue);

				for (int i = 0; i < height; i++) {
					aux.forward[i] = savedPointers[i].forward[i];
					savedPointers[i].forward[i] = aux;
				}
			}
		}
	}

	@Override
	public void remove(int key) {
		// primeiro procura, pra remover. se nao achar, nao remove
		SkipListNode<T> update = this.search(key);

		if (update != null) {

			SkipListNode<T> aux = root;
			int nodeHeight = update.height;
			int actualHeight = nodeHeight - 1;

			for (int i = actualHeight; i >= 0; i--) {

				SkipListNode<T> auxForward = aux.getForward(nodeHeight - 1);
				int auxKey = auxForward.getKey();

				// atualizar os links entre antecessores e sucessores
				while (aux.getKey() < key && auxKey < key) {
					aux = auxForward;
					auxForward = aux.getForward(nodeHeight - 1);
					auxKey = auxForward.getKey();
				}

				aux.getForward()[--nodeHeight] = update.getForward(nodeHeight);
			}
		}
	}

	@Override
	public int height() {
		return this.checkHeight();
	}

	@Override
	public SkipListNode<T> search(int key) {
		int actualHeight = this.height() - 1;
		SkipListNode<T> aux = this.root;

		while (aux != null && actualHeight >= 0) {
			int next = aux.getForward(actualHeight).getKey();
			int auxKey = aux.getKey();

			if (next > key)
				actualHeight--;
			else if (auxKey == key)
				break;
			else
				aux = aux.getForward(actualHeight);
		}
		return aux == null || aux.getKey() != key ? null : aux;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T>[] toArray() {
		int size = this.size() + SENTINEL_NODES_COUNT;

		SkipListNode<T>[] array = new SkipListNode[size];
		SkipListNode<T> aux = this.root;

		for (int i = 0; i < array.length; i++) {
			array[i] = aux;
			aux = aux.getForward(ZERO);
		}

		return array;
	}

	/**
	 * Checa se a skip list esta usando maxHeight
	 * 
	 * @return valor inteiro do height
	 */
	private int checkHeight() {
		return (USE_MAX_HEIGHT_AS_HEIGHT) ? maxHeight : height;
	}

	/**
	 * Atualiza os ponteiros do root na hora da insercao, pois o boolean
	 * USE_MAX_HEIGHT_AS_HEIGHT nao eh recebido no construtor, ocasionando um
	 * problema
	 * 
	 * @param height
	 *            da skip list
	 */
	private void updateRootPointers(int height) {
		if (USE_MAX_HEIGHT_AS_HEIGHT)
			height = this.maxHeight;

		if (this.height < height) {

			for (int i = this.height; i < height; i++)
				this.root.forward[i] = NIL;

			this.height = height;
		}
	}

}
