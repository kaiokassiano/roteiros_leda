
package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir o seu filho a direita e
	 * retorna-lo em seguida
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {

		if (node == null) {
			return null;
		} else if (node.isEmpty()) {
			return null;
		}

		BSTNode<T> aux = (BSTNode<T>) node.getRight();
		node.setRight(aux.getLeft());
		node.getRight().setParent(node);
		aux.setParent(node.getParent());

		if (node.getParent().getLeft() == node) {
			node.getParent().setLeft(aux);
		} else {
			node.getParent().setRight(aux);
		}

		aux.setLeft(node);
		node.setParent(aux);
		return aux;
	}

	/**
	 * A rotacao a direita em node deve subir seu filho a esquerda s retorna-lo
	 * em seguida
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {

		if (node == null) {
			return null;
		} else if (node.isEmpty()) {
			return null;
		}

		BSTNode<T> left = (BSTNode<T>) node.getLeft();
		node.setLeft(left.getRight());
		node.getLeft().setParent(node);
		left.setParent(node.getParent());
		if (node.getParent().getLeft() == node) {
			node.getParent().setLeft(left);
		} else {
			node.getParent().setRight(left);
		}

		left.setRight(node);
		node.setParent(left);

		return left;
	}

}