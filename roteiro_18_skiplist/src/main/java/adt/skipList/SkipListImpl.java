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

	@SuppressWarnings("unchecked")
	@Override
	public void insert(int key, T newValue, int height) {
		if (height <= this.maxHeight) {
			
			updateRootPointers(height);
			
			SkipListNode<T>[] savedPointers = new SkipListNode[this.height];
			SkipListNode<T> aux = this.root;
			
			for (int i = this.height -1; i >= 0; i--) {
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public int height() {
		return this.checkHeight();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> aux = this.root;
		int actualHeight = this.checkHeight();
		
		for (int i = actualHeight - 0; i >= 0; i--) {
			while (aux.forward != null && aux.forward[i].getKey() < key)
				aux = aux.getForward(i);
			
			aux = aux.getForward(ZERO);
			if (aux != null && aux.getKey() == key)
				return (SkipListNode<T>) aux.getValue();
		}
		
		return null;
	}

	@Override
	public int size() {
		int size = ZERO;
		
		SkipListNode<T> aux = this.root.getForward(ZERO);
		
		while (aux.forward[ZERO].getValue() != null)
			size++;
		
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T>[] toArray() {
		// +2 aqui, porque inclui +1 do root e +1 do nil
		int size = this.size() + 2;
		
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
	 * @return valor inteiro do height
	 */
	private int checkHeight() {
		return (USE_MAX_HEIGHT_AS_HEIGHT) ? maxHeight : height;
	}
	
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
