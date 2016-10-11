
package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int height;
	protected int maxHeight;

	protected boolean USE_MAX_HEIGHT_AS_HEIGHT = true;/*
														 * OBS: FOI PRECISO
														 * INICIALIZAR COM TRUE
														 * PARA TESTAR, PARA
														 * TESTAR FALSE EH
														 * PRECISO TROCAR ESSE
														 * VALOR AQUI E NA
														 * CLASSE DE TESTE PARA
														 * FALSE
														 */
	protected double PROBABILITY = 0.5;

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			for (int i = 0; i < maxHeight; i++) {
				root.forward[i] = NIL;
			}
		} else {
			root.getForward()[0] = NIL;
		}
	}

	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no
	 * metodo insert(int,V)
	 */
	@SuppressWarnings("unused")
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

		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> node = root;

		for (int i = (height - 1); i >= 0; i--) {
			while (node.getForward(i) != null && node.getForward(i).getKey() < key) {
				node = node.getForward(i);
			}

			update[i] = node;
		}

		node = node.getForward(0);

		if (node.getKey() == key) {
			if (node.height == height) {
				node.setValue(newValue);
			} else {
				remove(key);
				insert(key, newValue, height);
			}
		} else {
			int newLevel = height;

			if (newLevel > this.height) {
				for (int i = this.height; i < newLevel; i++) {
					update[i] = root;
				}

				this.height = newLevel;
			}

			node = new SkipListNode<T>(key, newLevel, newValue);
			for (int i = 0; i < newLevel; i++) {
				if (update[i].getForward(i) == null) {
					node.getForward()[i] = NIL;
				} else {
					node.getForward()[i] = update[i].getForward(i);
				}

				update[i].getForward()[i] = node;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> x = root;
		for (int i = (height - 1); i >= 0; i--) {
			while (x.forward[i].getKey() < key) {
				x = x.getForward(i);
			}

			update[i] = x;
		}
		x = x.getForward(0);

		if (x.getKey() == key) {
			for (int i = 0; i < height; i++) {
				if (!update[i].getForward(i).equals(x)) {
					break;
				}

				update[i].forward[i] = x.forward[i];

				while (height > 1 && root.forward[height - 1].equals(NIL)) {
					if (USE_MAX_HEIGHT_AS_HEIGHT) {
						height--;
					} else {
						root.forward[height - 1] = null;
						height--;
					}
				}
			}
		}
	}

	@Override
	public int height() {
		int i;
		for (i = 0; i < height; i++) {
			if (root.forward[i] == NIL) {
				break;
			}
		}

		return i;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> node = this.root;

		for (int i = (height - 1); i >= 0; i--) {
			while (node.forward[i].getKey() < key) {
				node = node.forward[i];
			}
		}

		node = node.forward[0];

		if (node.getKey() != key) {
			node = null;
		}

		return node;
	}

	@Override
	public int size() {
		int size = 0;

		SkipListNode<T> node = root.getForward(0);
		while (node != NIL) {
			size++;
			node = node.getForward(0);
		}

		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[size() + 2];

		int i = 0;

		SkipListNode<T> node = root;
		while (node != null) {
			array[i++] = node;
			node = node.getForward(0);
		}

		return array;
	}

}