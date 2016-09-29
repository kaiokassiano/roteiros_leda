package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements SplayTree<T> {

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = super.search(element);

		if (!node.isEmpty())
			splay(node);
		else
			splay((BSTNode<T>) node.getParent());

		return node;
	}

	@Override
	public void insert(T element) {
		insertNode(this.root, element);
	}

	private void insertNode(BSTNode<T> node, T element) {
		if (node.isEmpty()) {

			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);

			splay((BSTNode<T>) node);

		} else {

			if (node.getData().compareTo(element) == -1)
				insertNode((BSTNode<T>) node.getRight(), element);
			else
				insertNode((BSTNode<T>) node.getLeft(), element);

		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> auxNode = super.search(element);
		BSTNode<T> node = super.remove(auxNode);
		this.splay((BSTNode<T>) node.getParent());
	}

	private void splay(BSTNode<T> node) {
		if (node != null && node.getParent() != null) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();

			if (parent.getParent() == null) {
				if (isSonLeft(node)) {
					Util.rightRotation(parent);
				} else {
					Util.leftRotation(parent);
				}

			} else {
				BSTNode<T> grandParent = (BSTNode<T>) parent.getParent();
				if (isSonLeft(node)) {

					if (isSonLeft(parent)) {
						// zig-zig
						Util.rightRotation(grandParent);
						Util.rightRotation(parent);
					} else {
						// zig-zag
						Util.rightRotation(parent);
						Util.leftRotation(grandParent);
					}

					if (node.getParent() == null)
						this.root = node;

				} else {

					if (isSonLeft(parent)) {
						// zag-zig
						Util.leftRotation(parent);
						Util.rightRotation(grandParent);
					} else {
						// zag-zag
						Util.leftRotation(grandParent);
						Util.leftRotation(parent);
					}

					if (node.getParent() == null)
						this.root = node;
				}
			}

			if (node.getParent() == null)
				this.root = node;

			splay(node);
		}
	}
}
