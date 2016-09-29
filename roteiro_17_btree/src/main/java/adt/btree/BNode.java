package adt.btree;

import java.util.Collections;
import java.util.LinkedList;

public class BNode<T extends Comparable<T>> {
	private static final int ZERO = 0;
	private static final int ONE = 1;
	
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
		T mid = elements.get(elements.size() / 2);

		BNode<T> firstHalfNodes = new BNode<T>(maxChildren);
		BNode<T> secondHalfNodes = new BNode<T>(maxChildren);
		
		for (int i = 0; i < this.elements.size(); i++) {
			if (mid.compareTo(this.getElementAt(i)) > 0)
				firstHalfNodes.elements.add(this.getElementAt(i));
			else if (mid.compareTo(this.getElementAt(i)) < 0)
				secondHalfNodes.elements.add(this.getElementAt(i));
		}
		
		if (this.parent == null && this.isLeaf()) {
			
			this.setElements(new LinkedList<T>());
			this.addElement(mid);
			
			firstHalfNodes.parent = this;
			secondHalfNodes.parent = this;
			
			this.addChild(ZERO, firstHalfNodes);
			this.addChild(ONE, secondHalfNodes);
		
		} else if (this.parent == null && !this.isLeaf()) {
			
			LinkedList<BNode<T>> childrenList = this.children;
			
			this.setElements(new LinkedList<T>());
			this.addElement(mid);
			this.setChildren(new LinkedList<BNode<T>>());
			
			firstHalfNodes.parent = this;
			secondHalfNodes.parent = this;
			
			this.addChild(ZERO, firstHalfNodes);
			this.addChild(ONE, secondHalfNodes);
			
			if (!this.isLeaf()) {
				checkNodes(ZERO, firstHalfNodes.size() + ONE, firstHalfNodes, childrenList);
				checkNodes(secondHalfNodes.size() + ONE, childrenList.size(), secondHalfNodes, childrenList);
			}
			
		} else if (this.isLeaf()) {
			
			BNode<T> toPromoteNode = new BNode<>(maxChildren);
			
			toPromoteNode.elements.add(mid);
			toPromoteNode.parent = this.parent;

			firstHalfNodes.parent = this.parent;
			secondHalfNodes.parent = this.parent;

			int position = findPosition(mid, toPromoteNode.parent.getElements());
			int left = position;
			int right = position + 1;
			
			parent.children.set(left, firstHalfNodes);
			parent.children.add(right, secondHalfNodes);

			toPromoteNode.promote();
			
		} else {
			
			LinkedList<BNode<T>> childrenList = this.children;

			BNode<T> toPromoteNode = new BNode<>(maxChildren);
			toPromoteNode.elements.add(mid);
			toPromoteNode.parent = this.parent;

			firstHalfNodes.parent = this.parent;
			secondHalfNodes.parent = this.parent;

			int position = findPosition(mid, toPromoteNode.parent.getElements());
			int left = position;
			int right = position + ONE;

			parent.children.add(left, firstHalfNodes);
			parent.children.add(right, secondHalfNodes);

			int aux1 = ZERO;
			int aux2 = ZERO;
			
			for (int i = 0; i < childrenList.size(); i++) {
				if (childrenList.get(i).elements.get(ZERO).compareTo(mid) < ZERO)
					firstHalfNodes.addChild(aux1++, childrenList.get(i));
				else
					secondHalfNodes.addChild(aux2++, childrenList.get(i));
			}
			
			parent.removeChild(this);
			toPromoteNode.promote();
		}
	}

	protected void promote() {
		LinkedList<T> lista = this.parent.getElements();

		int index = findPosition(this.getElementAt(ZERO), lista);

		lista.add(index, this.getElementAt(ZERO));

		if (this.parent.size() > maxKeys)
			this.parent.split();

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

	// METODOS DE AUXILIO PARA O SPLIT E PARA O PROMOTE.
	// ESTOU CRIANDO METODOS EXTRAS PQ AMBAS AS CLASSES (BNode e BTreeImpl) VAO
	// SER ENVIADAS

	private int findPosition(T element, LinkedList<T> lista) {

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).compareTo(element) > ZERO) {
				return i;
			}
		}

		return lista.size();
	}

	private void checkNodes(int leftIndex, int rightIndex, BNode<T> parent,  LinkedList<BNode<T>> children) {
		for (int i = leftIndex; i < rightIndex; i++) {
			int index = findPosition(children.get(i).elements.get(ZERO), parent.getElements());
			parent.addChild(index, children.get(i));
		}
	}

}
