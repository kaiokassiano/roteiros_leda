package adt.btree;

import java.util.Collections;
import java.util.LinkedList;

public class BNode<T extends Comparable<T>> {

	protected LinkedList<T> elements; // PODERIA TRABALHAR COM ARRAY TAMBEM
	protected LinkedList<BNode<T>> children; // PODERIA TRABALHAR COM ARRAY
												// TAMBEM
	protected BNode<T> parent;
	protected int maxKeys;
	protected int maxChildren;

	public BNode(int order) {
		this.maxChildren = order;
		this.maxKeys = order - 1;
		this.elements = new LinkedList<T>();
		this.children = new LinkedList<BNode<T>>();
	}

	@Override
	public String toString() {
		return this.elements.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		boolean resp = false;
		if (obj != null) {
			if (obj instanceof BNode) {
				if (this.size() == ((BNode<T>) obj).size()) {
					resp = true;
					int i = 0;
					while (i < this.size() && resp) {
						resp = resp && this.getElementAt(i).equals(((BNode<T>) obj).getElementAt(i));
						i++;
					}
				}
			}
		}
		return resp;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public int size() {
		return this.elements.size();
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}

	public boolean isFull() {
		return this.size() == maxKeys;
	}

	public void addElement(T element) {
		this.elements.add(element);
		Collections.sort(elements);
	}

	public void removeElement(T element) {
		this.elements.remove(element);
	}

	public void removeElement(int position) {
		this.elements.remove(position);
	}

	public void addChild(int position, BNode<T> child) {
		this.children.add(position, child);
		child.parent = this;
	}

	public void removeChild(BNode<T> child) {
		this.children.remove(child);
	}

	public int indexOfChild(BNode<T> child) {
		return this.children.indexOf(child);
	}

	public T getElementAt(int index) {
		return this.elements.get(index);
	}

	protected void split() {
		int mid = (size()) / 2;

		BNode<T> left = this.copyLeft(mid);
		BNode<T> right = this.copyRight(mid);

		if (parent == null) {
			parent = new BNode<T>(maxChildren);
			parent.children.addFirst(this);
		}

		BNode<T> parent = this.parent;

		int index = parent.indexOfChild(this);
		parent.removeChild(this);

		parent.addChild(index, left);
		parent.addChild(index + 1, right);

		left.setParent(parent);
		right.setParent(parent);

		this.promote(mid);

		if (parent.size() >= maxChildren) {
			parent.split();
		}
	}

	protected void promote(int mid) {
		T element = elements.get(mid);
		this.parent.addElement(element);
	}

	private BNode<T> copyLeft(int mid) {
		BNode<T> node = new BNode<T>(this.maxChildren);

		for (int i = 0; i < mid; i++) {
			node.addElement(this.elements.get(i));
		}

		for (int i = 0; i <= mid; i++) {
			node.addChild(i, this.children.get(i));
		}

		return node;
	}

	private BNode<T> copyRight(int mid) {
		BNode<T> node = new BNode<T>(this.maxChildren);

		for (int i = mid + 1; i < elements.size(); i++) {
			node.addElement(this.elements.get(i));
		}

		for (int i = mid + 1; i < this.children.size(); i++) {
			node.addChild(i - mid - 1, this.children.get(i));
		}

		return node;
	}

	public LinkedList<T> getElements() {
		return elements;
	}

	public void setElements(LinkedList<T> elements) {
		this.elements = elements;
	}

	public LinkedList<BNode<T>> getChildren() {
		return children;
	}

	public void setChildren(LinkedList<BNode<T>> children) {
		this.children = children;
	}

	public BNode<T> copy() {
		BNode<T> result = new BNode<T>(maxChildren);
		result.parent = parent;
		for (int i = 0; i < this.elements.size(); i++) {
			result.addElement(this.elements.get(i));
		}
		for (int i = 0; i < this.children.size(); i++) {
			result.addChild(i, ((BNode<T>) this.children.get(i)).copy());
		}

		return result;
	}

	public BNode<T> getParent() {
		return parent;
	}

	public void setParent(BNode<T> parent) {
		this.parent = parent;
	}

	public int getMaxKeys() {
		return maxKeys;
	}

	public void setMaxKeys(int maxKeys) {
		this.maxKeys = maxKeys;
	}

	public int getMaxChildren() {
		return maxChildren;
	}

	public void setMaxChildren(int maxChildren) {
		this.maxChildren = maxChildren;
	}

}
