package adt.btree;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	private static final int ZERO = 0;

	protected BNode<T> root;
	protected int order;

	public BTreeImpl(int order) {

		// apenas para evitar erros em testes mais 'ousados'
		if (order < ZERO)
			order = ZERO;

		this.order = order;
		this.root = new BNode<T>(order);
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		int value = -1;

		if (!node.isEmpty()) {
			if (!node.isLeaf())
				value = 1 + height(node.getChildren().get(ZERO));
			else
				value = 0;
		}

		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BNode<T>[] depthLeftOrder() {
		BNode<T>[] lista = new BNode[this.size()];
		depthLeftOrder(this.root, lista, ZERO);

		return lista;
	}

	private int depthLeftOrder(BNode<T> node, BNode<T> array[], int index) {
		if (!node.isEmpty()) {
			
			array[index] = node;
			index++;

			for (int i = 0; i < node.getChildren().size(); i++) {
				index = depthLeftOrder(node.getChildren().get(i), array, index);
			}

		}

		return index;
	}

	@Override
	public int size() {
		return size(this.root);
	}

	private int size(BNode<T> node) {
		int size = 0;

		if (!node.isEmpty()) {
			size += node.size();

			for (int i = 0; i < node.getChildren().size(); i++) {
				size += size(node.getChildren().get(i));
			}
		}

		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		return (element != null) ? search(element, this.root) : null;
	}

	private BNodePosition<T> search(T element, BNode<T> node) {

		int index = 0;

		while (this.isLower(node, element, index))
			index++;

		if (this.isEqual(node, element, index))
			return new BNodePosition<T>(node, index);

		if (node.isLeaf())
			return new BNodePosition<T>();

		return search(element, node.getChildren().get(index));

	}

	@Override
	public void insert(T element) {
		if (element != null) {
			
			BNode<T> auxNode = searchLeafNode(element, this.root);
			auxNode.addElement(element);
			
			while (auxNode.getParent() != null) {
				if (auxNode.isFull()) {
					
					this.split(auxNode);
					this.promote(auxNode);
				}
				
				auxNode = auxNode.getParent();
			}
			
			if (auxNode.isFull()) {
				BNode<T> node = new BNode<T>(auxNode.getMaxChildren());
				node.addChild(ZERO, auxNode);
				auxNode.setParent(node);
				
				this.root = node;
				
				this.split(auxNode);
				this.promote(auxNode);
			}
		}
	}

	private void split(BNode<T> node) {
		node.split();
	}

	private void promote(BNode<T> node) {
		node.promote();
	}

	// METODOS AUXILIARES PARA A IMPLEMENTACAO
	
	private boolean isLower(BNode<T> node, T element, int index) {
		return index < node.size() && node.getElementAt(index).compareTo(element) < ZERO;
	}

	private boolean isEqual(BNode<T> node, T element, int index) {
		return index < node.size() && node.getElementAt(index).compareTo(element) == ZERO;
	}
	
	private BNode<T> searchLeafNode(T element, BNode<T> node) {
		int index = 0;
		
        while(isLower(node, element, index))
        	index++;
        
        if(node.isLeaf())
        	return node;
        
        return searchLeafNode(element, node.getChildren().get(index));
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

}
