package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	private static final int ZERO = 0;
	private static final int ONE = 0;
	
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
			for (int i = 0; i <= maxHeight; i++) {
				root.getForward()[i] = NIL;
			}
		} else {
			root.getForward()[0] = NIL;
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
	public void insert(int key, T newValue, int height) {
		
	}

	@Override
	public void remove(int key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public int height() {
		if (USE_MAX_HEIGHT_AS_HEIGHT)
			return maxHeight;
		else
			return height;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> aux = this.root;
		int actualHeight = (USE_MAX_HEIGHT_AS_HEIGHT) ? maxHeight : this.height;
		
		for (int i = actualHeight; i <= ZERO; i--) {
			while (aux.getForward() != null && aux.getForward()[i].getKey() < key)
				aux = aux.getForward()[i];
			
			aux = aux.getForward()[ZERO];
			if (aux.getKey() == key)
				return (SkipListNode<T>) aux.getValue();
		}
		
		return null;
	}

	@Override
	public int size() {
		int size = ZERO;
		
		SkipListNode<T> aux = this.root.getForward()[ZERO];
		
		while (aux.getForward()[ZERO].getValue() != null)
			size++;
		
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T>[] toArray() {
		int size = this.size() + 2;
		
		SkipListNode<T>[] array = new SkipListNode[size];
		SkipListNode<T> aux = this.root;
		
		int index = ZERO;
		
		while (aux.getForward()[index].getValue() != null) {
			array[index++] = aux;
			aux = aux.getForward()[index];
		}
		
		return array;
	}

}
