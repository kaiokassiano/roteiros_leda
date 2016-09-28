package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir o seu filho a direita e retorna-lo em seguida
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> auxRight = (BSTNode<T>) node.getRight();

        node.setRight(auxRight.getLeft());
        auxRight.setLeft(node);
        
        if (node.getParent() != null) {
            if (!isSonLeft(node))
            	node.getParent().setRight(auxRight);
            else
            	node.getParent().setLeft(auxRight);
        }
        
        auxRight.setParent(node.getParent());
        node.setParent(auxRight);

        return auxRight;
	}

	/**
	 * A rotacao a direita em node deve subir seu filho a esquerda s retorna-lo em seguida
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> auxLeft = (BSTNode<T>) node.getLeft();

        node.setLeft(auxLeft.getRight());
        auxLeft.setRight(node);
        
        if (node.getParent() != null) {
            if (!isSonLeft(node))
            	node.getParent().setRight(auxLeft);
            else
            	node.getParent().setLeft(auxLeft);
        }
        
        auxLeft.setParent(node.getParent());
        node.setParent(auxLeft);

        return auxLeft;
	}
	
	private static <T extends Comparable<T>> boolean isSonLeft(BSTNode<T> node) {
        if (node.getParent() != null && !node.getParent().isEmpty())
        	if (!node.getParent().getLeft().isEmpty())
                if (node.getParent().getLeft().getData().equals(node.getData()))
                	return true;
        
        return false;
    }

}
