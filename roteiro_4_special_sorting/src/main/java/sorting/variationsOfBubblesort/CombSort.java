package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {

	private static final double FATOR = 1.25;

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (validaEntradas(array, leftIndex, rightIndex))
			combSort(array, leftIndex, rightIndex);
	}

	private void combSort(T[] array, int leftIndex, int rightIndex) {

		// Variaveis de controle
		int size = rightIndex - leftIndex + 1;
		int salto = size;
		boolean trocou = true;

		// Ocorre ate nao haver trocas
		while (salto > 1 || trocou) {

			// Divide o salto, pra cada vez que o tamanho do salto for maior que 1
			if (salto > 1)
				salto = (int) (salto / FATOR);

			trocou = false;

			// Percorre o array e troca os elementos
			for (int i = leftIndex; i + salto <= rightIndex; i++) {

				if (array[i].compareTo(array[i + salto]) > 0) {
					Util.swap(array, i, i + salto);
					trocou = true;
				}
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
