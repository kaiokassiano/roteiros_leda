package adt.bt;

import adt.bst.BSTNode;

public class Util {

	private static <T extends Comparable<T>> boolean isLeftChild(BSTNode<T> node) {
		return node.getParent() != null && !node.getParent().isEmpty()
                && !node.getParent().getLeft().isEmpty() &&
                node.getParent().getLeft().getData().equals(node.getData());
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> leftNode = (BSTNode<T>) node.getLeft();

        node.setLeft(leftNode.getRight());
        leftNode.getRight().setParent(node);
        leftNode.setRight(node);
        
        if (node.getParent() != null) {
            if (isLeftChild(node))
                node.getParent().setLeft(leftNode);
            else
                node.getParent().setRight(leftNode);
        }
        
        leftNode.setParent(node.getParent());
        node.setParent(leftNode);

        return leftNode;
	}
	

	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * 
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> rightNode = (BSTNode<T>) node.getRight();

        node.setRight(rightNode.getLeft());
        rightNode.getLeft().setParent(node);
        rightNode.setLeft(node);
        
        if (node.getParent() != null) {
            if (isLeftChild(node))
                node.getParent().setLeft(rightNode);
            else
                node.getParent().setRight(rightNode);
        }
        
        rightNode.setParent(node.getParent());
        node.setParent(rightNode);

        return rightNode;
	}

}
