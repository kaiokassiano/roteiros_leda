package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	private static final int ZERO = 0;

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(BSTNode<T> node) {
		if (node.isEmpty())
			return -1;
		return Math.max(this.height((BSTNode<T>) node.getLeft()), this.height((BSTNode<T>) node.getRight())) + 1;
	}

	@Override
	public BSTNode<T> search(T element) {
		return searchNode(element, this.root);
	}

	/**
	 * Busca um node recursivamente na arvore.
	 * @param element
	 * @param node
	 * @return
	 */
	private BSTNode<T> searchNode(T element, BSTNode<T> node) {
		if (element == null) {
			return new BSTNode<T>(); // NIL node

		} else {
			if (node.isEmpty() || node.getData().equals(element))
				return node;

			else if (element.compareTo(node.getData()) < ZERO)
				return searchNode(element, (BSTNode<T>) node.getLeft());

			else
				return searchNode(element, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public void insert(T element) {
		recursiveInsert(this.root, element);
	}

	/**
	 * Faz a insercao recursiva de um elemento na arvore.
	 * 
	 * @param node
	 * @param element
	 */
	private void recursiveInsert(BSTNode<T> node, T element) {
		if (element != null) {
			if (node.isEmpty()) {
				node.setData(element);
				node.setLeft(new BSTNode<T>());
				node.setRight(new BSTNode<T>());
				node.getLeft().setParent(node);
				node.getRight().setParent(node);
			} else {
				if (element.compareTo(node.getData()) > ZERO) {
					recursiveInsert((BSTNode<T>) node.getRight(), element);
				} else if (element.compareTo(node.getData()) < ZERO) {
					recursiveInsert((BSTNode<T>) node.getLeft(), element);
				}
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(root);
	}

	/**
	 * Encontra, recursivamente, e retorna o maior elemento na arvore.
	 * @param node
	 * @return
	 */
	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.isEmpty())
			return null;
		else if (node.getRight().isEmpty())
			return node;
		else
			return maximum((BSTNode<T>) node.getRight());
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(root);
	}

	/**
	 * Encontra, recursivamente, e retorna o menor elemento na arvore.
	 * @param node
	 * @return
	 */
	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.isEmpty())
			return null;
		else if (node.getLeft().isEmpty())
			return node;
		else
			return minimum((BSTNode<T>) node.getLeft());
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty())
			return null;

		return sucessor(node);
	}

	/**
	 * Encontra, recursivamente, o sucessor de um node na arvore.
	 * @param node
	 * @return
	 */
	private BSTNode<T> sucessor(BSTNode<T> node) {
		BSTNode<T> result = minimum((BSTNode<T>) node.getRight());

		if (result != null) {
			return result;
		} else {
			result = (BSTNode<T>) node.getParent();
			while (result != null && result.getData().compareTo(node.getData()) < ZERO) {
				result = (BSTNode<T>) result.getParent();
			}
			return result;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty())
			return null;

		return predecessor(node);
	}

	/**
	 * Encontra, recursivamente, o antecessor de um node na arvore.
	 * @param node
	 * @return
	 */
	private BSTNode<T> predecessor(BSTNode<T> node) {
		BSTNode<T> result = maximum((BSTNode<T>) node.getLeft());

		if (result != null) {
			return result;
		} else {
			result = (BSTNode<T>) node.getParent();
			while (result != null && result.getData().compareTo(node.getData()) > ZERO) {
				result = (BSTNode<T>) result.getParent();
			}
			return result;
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {

			BSTNode<T> node = search(element);

			if (!node.isEmpty())
				remove(node);
		}
	}

	/**
	 * Remove, recursivamente, um node da arvore.
	 * @param node
	 */
	protected BSTNode<T> remove(BSTNode<T> node) {
		if (node.isLeaf()) {
			node.setData(null);
			return node;
		}
		else if (gotOneSon(node))
			return removeOneChildrenNode(node);
		else
			return removeTwoChildrenNode(node);
	}

	/**
	 * Remove um node que tenha apenas um filho.
	 * @param node
	 */
	private BSTNode<T> removeOneChildrenNode(BSTNode<T> node) {
		BSTNode<T> auxNode;

		if (justLeftSon(node)) {
			auxNode = (BSTNode<T>) node.getLeft();
		} else {
			auxNode = (BSTNode<T>) node.getRight();
		}
		
		if (node.getParent() == null) {
			auxNode.setParent(null);
			this.root = auxNode;

		} else {
			if (isSonLeft(node)) {
				node.getParent().setLeft(auxNode);
			} else {
				node.getParent().setRight(auxNode);
			}
			auxNode.setParent(node.getParent());
		}
		
		return auxNode;
	}

	/**
	 * Remove da arvore um node que tenha dois filhos.
	 * @param node
	 */
	private BSTNode<T> removeTwoChildrenNode(BSTNode<T> node) {
		BSTNode<T> auxNode = minimum((BSTNode<T>) node.getRight());

		T aux = node.getData();

		node.setData(auxNode.getData());
		auxNode.setData(aux);

		return remove(auxNode);
	}

	@Override
	public T[] preOrder() {
		// utiliza preordem
		return orderTypeMethod("preOrder");
	}

	@Override
	public T[] order() {
		// utiliza ordem
		return orderTypeMethod("order");
	}

	@Override
	public T[] postOrder() {
		// utiliza posordem
		return orderTypeMethod("postOrder");
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

	
	
	// Metodos abaixo criados por mim, para melhorar na implementacao. Todos possuem javadoc.

	
	
	@SuppressWarnings("unchecked")
	/**
	 * Retorna o array que representa a arvore, de acordo com o tipo de impressao desejada.
	 * @param type
	 * @return
	 */
	private T[] orderTypeMethod(String type) {
		int size = size();
		T[] array = (T[]) new Comparable[size];

		if (!isEmpty()) {
			if (type.equals("preOrder")) {
				constructPreOrder(array, ZERO, this.root);
			} else if (type.equals("order")) {
				constructOrder(array, ZERO, this.root);
			} else {
				constructPostOrder(array, ZERO, this.root);
			}
		}

		return array;
	}

	/**
	 * Preenche o array com os elementos da arvore em preOrdem.
	 * @param array
	 * @param index
	 * @param node
	 * @return
	 */
	private int constructPreOrder(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			array[index++] = node.getData();

			index = constructPreOrder(array, index, (BSTNode<T>) node.getLeft());
			index = constructPreOrder(array, index, (BSTNode<T>) node.getRight());
		}

		return index;
	}

	/**
	 * Preenche o array com os elementos da arvore em ordem.
	 * @param array
	 * @param index
	 * @param node
	 * @return
	 */
	private int constructOrder(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			index = constructOrder(array, index, (BSTNode<T>) node.getLeft());

			array[index++] = node.getData();

			index = constructOrder(array, index, (BSTNode<T>) node.getRight());
		}

		return index;
	}

	/**
	 * Preenche o array com os elementos da arvore em posOrdem.
	 * @param array
	 * @param index
	 * @param node
	 * @return
	 */
	private int constructPostOrder(T[] array, int index, BSTNode<T> node) {
		if (!node.isEmpty()) {
			index = constructPostOrder(array, index, (BSTNode<T>) node.getLeft());
			index = constructPostOrder(array, index, (BSTNode<T>) node.getRight());

			array[index++] = node.getData();
		}

		return index;
	}

	/**
	 * Verifica se o node possui *pelo menos* um filho que nao seja null.
	 * @param node
	 * @return
	 */
	protected boolean gotOneSon(BSTNode<T> node) {
		return justLeftSon(node) || justRightSon(node);
	}

	/**
	 * Verifica se o node possui apenas o filho da esquerda diferente de null.
	 * @param node
	 * @return
	 */
	protected boolean justLeftSon(BSTNode<T> node) {
		return !node.getLeft().isEmpty() && node.getRight().isEmpty();
	}

	/**
	 * Verifica se o node possui apenas o filho da direita diferente de null.
	 * @param node
	 * @return
	 */
	protected boolean justRightSon(BSTNode<T> node) {
		return node.getLeft().isEmpty() && !node.getRight().isEmpty();
	}

	/**
	 * Verifica se o node eh filho da esquerda de seu pai.
	 * @param node
	 * @return
	 */
	protected boolean isSonLeft(BSTNode<T> node) {
		return node.getParent() != null && !node.getParent().isEmpty()
                && !node.getParent().getLeft().isEmpty() &&
                node.getParent().getLeft().getData().equals(node.getData());
	}
	
	protected boolean isLeftChild(BSTNode<T> node) {
		return node.equals(node.getParent().getLeft());
	}

}
