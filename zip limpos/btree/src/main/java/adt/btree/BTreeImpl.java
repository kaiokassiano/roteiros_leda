package adt.btree;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

    private static final int FIRST_INDEX = 0;
    private static final int ZERO = 0;
    private static final int INVALID_INDEX = -1;
    protected BNode<T> root;
    protected int order;

    public BTreeImpl(int order) {
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
        return height(this.root) - 1;
    }

    private int height(BNode<T> node) {
        int height = ZERO;
        if (!node.isEmpty()) {
            height++;
            int subTreeHeight = ZERO;
            for (BNode<T> child : node.children) {
                subTreeHeight = Math.max(subTreeHeight, this.height(child));
            }
            height += subTreeHeight;
        }
        return height;
    }

    @Override
    public BNode<T>[] depthLeftOrder() {
        int index = FIRST_INDEX;
        BNode<T>[] array = new BNode[this.size()];
        this.depthLeftOrder(index, array, this.getRoot());
        return array;
    }

    private int depthLeftOrder(int index, BNode<T>[] array, BNode<T> node) {
        array[index++] = node;
        for (BNode<T> child : node.getChildren()) {
            index = this.depthLeftOrder(index, array, child);
        }
        return index;
    }

    @Override
    public int size() {
        return this.size(this.getRoot());
    }

    private int size(BNode<T> node) {
        int size = node.getElements().size();
        for (BNode<T> child :
                node.getChildren()) {
            size += this.size(child);
        }
        return size;
    }

    @Override
    public BNodePosition<T> search(T element) {
        return this.search(this.getRoot(), element);
    }

    private BNodePosition<T> search(BNode<T> node, T element) {
        if (node.getElements().isEmpty())
            return new BNodePosition<T>(null, INVALID_INDEX);
        for (int index = FIRST_INDEX; index < node.getElements().size(); index++) {
            if (node.getElementAt(index).equals(element)) {
                return new BNodePosition<T>(node, index);
            } else if (node.getElementAt(index).compareTo(element) > ZERO) {
                if (node.getChildren().size() <= index)
                    return new BNodePosition<T>(null, INVALID_INDEX);
                else
                    return this.search(node.getChildren().get(index), element);
            }
        }
        return this.search(node.getChildren().get(node.getElements().size()), element);
    }

    @Override
    public void insert(T element) {
        this.insert(this.root, element);
    }

    /*
     * Como não foi especificado o algoritmo de inserção, fiz da forma que o professor João Arthur explicou em
     * sala. Algoritmo que abre espaço nos nós acima da folha em que será inserido o nó, se o mesmo estiver cheio
     * para que se precisar fazer promote, o nó acima terá espaço.
     */
    private void insert(BNode<T> node, T element) {
    	if (node.isFull()) {
			split(node);
			if (node.getParent() != null)
				node = node.getParent();
		}
		if (node.getChildren().isEmpty()) {
			node.addElement(element);
		} else {
			int i;
			for (i = 0; i < node.size(); i++) {
				T e = node.getElements().get(i);
				if (e.compareTo(element) > 0) {
					insert(node.getChildren().get(i), element);
					break;
				}
			}
			if (i == node.size()) {
				while (node.getChildren().size() <= i) {
					BNode<T> nNode = new BNode<T>(order);
					nNode.setParent(node);
					node.getChildren().add(nNode);
				}
				insert(node.getChildren().get(i), element);
			}
		}
    }

    private void split(BNode<T> node) {
        node.split();
    }

    private void promote(BNode<T> node) {
        node.promote();
    }

    @Override
	public Integer maximum(BNode<T> node) {
		if (!node.isEmpty()) {
			if (!node.children.isEmpty() && !node.children.getLast().elements.isEmpty()) {
				if (((Integer) node.children.getLast().elements.getLast()).compareTo
					((Integer) node.elements.getLast()) > 0) {
					node = node.children.getLast();
					return this.maximum(node);	
				} else {
					return (Integer) node.elements.getLast();
				}
			}
			
			if (node.elements.getLast() != null) {
				Integer last = (Integer) node.elements.getLast();
				return last;
			}
		}
		
		return null;
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