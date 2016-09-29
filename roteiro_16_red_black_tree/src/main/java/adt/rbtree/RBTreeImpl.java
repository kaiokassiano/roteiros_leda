package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	private static final int ZERO = 0;
	private static final int ONE = 1;

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) this.root);
	}

	private int blackHeight(RBNode<T> node) {
		if (node.isEmpty())
			return ONE;

		int height;

		if (isBlack(node))
			height = ONE;
		else
			height = ZERO;

		return height + Math.max(blackHeight((RBNode<T>) node.getLeft()), blackHeight((RBNode<T>) node.getRight()));
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) this.getRoot());
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		if (!node.isEmpty()) {
			if (node.getColour().equals(Colour.RED)) {
				if (!isBlack((RBNode<T>) node.getLeft()) || !isBlack((RBNode<T>) node.getRight())) {
					return false;
				}
			}
			return this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& this.verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
		}
		return true;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		int leftHeight = verifyBlackHeight((RBNode<T>) this.getRoot().getLeft(), ZERO);
		int rightHeight = verifyBlackHeight((RBNode<T>) this.getRoot().getRight(), ZERO);

		if (leftHeight == rightHeight)
			return true;

		return false;
	}

	private int verifyBlackHeight(RBNode<T> node, int height) {
		if (node != null && !node.isEmpty()) {
			if (isBlack(node))
				height++;

			return Math.max(verifyBlackHeight((RBNode<T>) node.getLeft(), height),
					verifyBlackHeight((RBNode<T>) node.getRight(), height));
		}

		height++;
		return height;
	}

	@Override
	public void insert(T value) {
		if (value != null)
			this.insert((RBNode<T>) this.getRoot(), value);
	}

	/**
	 * Insere, recursivamente, um node na arvore, e automaticamente faz o fix do
	 * case1
	 * 
	 * @param node
	 * @param parent
	 * @param element
	 */
	private RBNode<T> insert(RBNode<T> node, T element) {
		RBNode<T> auxNode = node;
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new RBNode<T>());
			node.getLeft().setParent(node);
			node.setRight(new RBNode<T>());
			node.getRight().setParent(node);
			node.setColour(Colour.RED);
			fixUpCase1(node);
		} else if (element.compareTo(node.getData()) < ZERO) {
			this.insert((RBNode<T>) node.getLeft(), element);
		} else if (element.compareTo(node.getData()) > ZERO) {
			this.insert((RBNode<T>) node.getRight(), element);
		}
		return auxNode;

	}

	@SuppressWarnings("unchecked")
	@Override
	public RBNode<T>[] rbPreOrder() {
		RBNode<T>[] array = new RBNode[size()];
		rbPreOrder(array, ZERO, (RBNode<T>) this.getRoot());

		return array;
	}

	private int rbPreOrder(RBNode<T>[] array, int index, RBNode<T> node) {
		if (!node.isEmpty()) {
			array[index++] = node;
			index = rbPreOrder(array, index, (RBNode<T>) node.getLeft());
			index = rbPreOrder(array, index, (RBNode<T>) node.getRight());
		}

		return index;

	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(root))
			node.setColour(Colour.BLACK);
		else
			fixUpCase2(node);
	}

	protected void fixUpCase2(RBNode<T> node) {

		if (!isBlack((RBNode<T>) node.getParent()))
			fixUpCase3(node);

	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		RBNode<T> uncle;

		if (isLeftChild(parent))
			uncle = (RBNode<T>) grandParent.getRight();
		else
			uncle = (RBNode<T>) grandParent.getLeft();

		if (!isBlack(uncle)) {
			parent.setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			grandParent.setColour(Colour.RED);
			fixUpCase1(grandParent);
		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		RBNode<T> parent = (RBNode<T>) node.getParent();

		if (!((isLeftChild(node) && isLeftChild(parent)) || (!isLeftChild(node) && !isLeftChild(parent)))) {
			
			if (isLeftChild(node))
				Util.rightRotation(parent);
			else
				Util.leftRotation(parent);
			
			fixUpCase5(parent);

		} else {
			fixUpCase5(next);
		}
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();

		parent.setColour(Colour.BLACK);
		grandParent.setColour(Colour.RED);

		if (isLeftChild(node))
			Util.rightRotation(grandParent);
		else
			Util.leftRotation(grandParent);
	}

	/**
	 * Checa se a cor do node eh preta.
	 * 
	 * @param node
	 * @return true se a cor do node for preta, false caso contrario
	 */
	private boolean isBlack(RBNode<T> node) {
		if (node != null) {
			return node.getColour() == Colour.BLACK;
		}

		return true;
	}
}
