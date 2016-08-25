package adt.linkedList.ordered;

import adt.linkedList.LinkedList;

/**
 * Representa um a lista ordenada (ascendente ou descendente) de elementos,
 * usando um comparador externo. Dependendo do comparador usado a lista pode ser
 * ascendente ou descendente.
 * 
 * Naturalmente, numa lista ordenada é possível enconrtar o elemento máximo e o
 * elemento mínimo da lista. Também, a execução das operações mantém a
 * propriedade de ordem da lista e usam o comparator para isso.
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public interface OrderedLinkedList<T> extends LinkedList<T> {

	/**
	 * Retorna o elemento de menor valor da lista ou null se a lista form vazia
	 * 
	 * @return
	 */
	public T minimum();

	/**
	 * Retorna o elemento de maior valor da lista ou null se a lista form vazia
	 * 
	 * @return
	 */
	public T maximum();
}
