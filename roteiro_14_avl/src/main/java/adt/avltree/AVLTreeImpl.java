package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * Performs consistency validations within a AVL Tree instance
 *
 * @param <T>
 * @author Claudio Campelo
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	private static final int ZERO = 0;

	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = super.search(element);

		rebalanceUp(node);
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);

		if (!node.isEmpty()) {
			super.remove(element);
			rebalanceUp(node);
		}
	}

	// AUXILIARY
	/**
	 * Rotaciona o node para a esquerda
	 * @param node
	 */
	protected void leftRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.leftRotation(node);
		checkParent((BSTNode<T>) aux);
	}

	// AUXILIARY
	/**
	 * Rotaciona o node para a direita
	 * @param node
	 */
	protected void rightRotation(BSTNode<T> node) {
		BSTNode<T> aux = Util.rightRotation(node);
		checkParent((BSTNode<T>) aux);
	}

	// AUXILIARY
	/**
	 * Realiza o balanceamento ate a root
	 * @param node
	 */
	protected void rebalanceUp(BSTNode<T> node) {
		Integer balance = calculateBalance(node);

		if (checkAbsoluteBalance(balance))
			rebalance(node);

		if (node.getParent() != null)
			rebalanceUp((BSTNode<T>) node.getParent());

	}

	// AUXILIARY
	/**
	 * Realiza o rebalanceamento
	 * @param node
	 */
	protected void rebalance(BSTNode<T> node) {
		Integer balance = calculateBalance(node);

		if (balance < -1) {
			BSTNode<T> rightAuxNode = (BSTNode<T>) node.getRight();

			if (calculateBalance(rightAuxNode) >= 1)
				rightRotation(rightAuxNode);

			leftRotation(node);

		} else if (balance > 1) {
			BSTNode<T> leftAuxNode = (BSTNode<T>) node.getLeft();

			if (calculateBalance(leftAuxNode) <= -1)
				leftRotation(leftAuxNode);

			rightRotation(node);

		}
	}

	// AUXILIARY
	/**
	 * Calcula o valor do balance
	 * @param node
	 * @return int contendo o valor do balanceamento
	 */
	protected int calculateBalance(BSTNode<T> node) {
		if (!node.isEmpty())
			return this.height((BSTNode<T>) node.getLeft()) - this.height((BSTNode<T>) node.getRight());
		
		return ZERO;
	}
	
	// Metodos para auxilio
	
	private void checkParent(BSTNode<T> node) {
		if (node.getParent() == null)
			this.root = node;
	}
	
	private boolean checkAbsoluteBalance(Integer balance) {
		return Math.abs(balance) >= 2;
	}

}