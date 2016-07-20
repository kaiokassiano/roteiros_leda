package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The implementation of the algorithm must be in-place!
 */
public class GnomeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (validaEntradas(array, leftIndex, rightIndex))
			gnomeSort(array, leftIndex, rightIndex);
	}

	private void gnomeSort(T[] array, int leftIndex, int rightIndex) {
		
		int pivot = leftIndex;

		// Executa ate que o pivo ser menor que o rightIndex. Ou seja, quando o pivo alcancar o final do array
		while (pivot < rightIndex) {
			
			int nextIndex = pivot + 1;
			
			// Executa a troca do elemento com o pivo, caso o proximo elemento seja menor que o pivo
			// Se nao, apenas incrementa o pivo
			if (pivot < leftIndex || array[pivot].compareTo(array[nextIndex]) <= 0)
				pivot++;
			else {
				Util.swap(array, pivot, nextIndex);
				pivot--;
			}
		}
	}

	// Valida o array, bem como os indices que foram passados como parametro
	private boolean validaEntradas(T[] array, int leftIndex, int rightIndex) {
		if (array == null)
			return false;
		else if (array.length <= 1)
			return false;
		else if (leftIndex >= rightIndex)
			return false;
		else if (leftIndex < 0 || rightIndex > array.length)
			return false;
		else
			return true;
	}
}
