package sorting.variationsOfSelectionsort;

import sorting.AbstractSorting;
import util.Util;

/**
 * This algorithm simulates a logical partitioning of the input array by
 * considering different indexing, that is, the first sub-array is indexed by
 * even elements and the second sub-array is indexed by odd elements. Then, it
 * applies a complete selectionsort in the first sub-array considering
 * neighbours (even). After that, it applies a complete selectionsort in the
 * second sub-array considering neighbours (odd). After that, the algorithm
 * performs a merge between elements indexed by even and odd numbers.
 */
public class OddEvenSelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	private static final int DOIS = 2;

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (validaEntradas(array, leftIndex, rightIndex))
			;
		{
			oddEvenSelectionSort(array, leftIndex, rightIndex, DOIS);
			oddEvenSelectionSort(array, leftIndex + 1, rightIndex, DOIS);
			mergeArrays(array, leftIndex, rightIndex, DOIS);
		}
	}

	private void oddEvenSelectionSort(T[] array, int leftIndex, int rightIndex, int step) {
		int firstIndex, secondIndex;

		for (firstIndex = leftIndex; firstIndex <= rightIndex - 2; firstIndex += step) {
			int minIndex = firstIndex;

			for (secondIndex = firstIndex + step; secondIndex <= rightIndex; secondIndex += step) {
				if (array[secondIndex].compareTo(array[minIndex]) < 0) {
					minIndex = secondIndex;
				}
			}
			Util.swap(array, minIndex, firstIndex);
		}
	}

	private void mergeArrays(T[] array, int leftIndex, int rightIndex, int step) {
		int firstPointer = leftIndex;
		int secondPointer = leftIndex + 1;
		int finalPointer = 0;
		int size = rightIndex - leftIndex + 1;

		T[] auxArray = (T[]) new Comparable[size];

		while (firstPointer <= rightIndex && secondPointer <= rightIndex) {
			if (array[firstPointer].compareTo(array[secondPointer]) < 0) {
				auxArray[finalPointer++] = array[firstPointer];
				firstPointer += step;
			} else {
				auxArray[finalPointer++] = array[secondPointer];
				secondPointer += step;
			}
		}

		while (firstPointer <= rightIndex) {
			auxArray[finalPointer++] = array[firstPointer];
			firstPointer += step;
		}

		while (secondPointer <= rightIndex) {
			auxArray[finalPointer++] = array[secondPointer];
			secondPointer += step;
		}

		cloneArrays(array, auxArray, leftIndex, rightIndex);
	}

	// Clona os arrays
	private void cloneArrays(T[] firstArray, T[] secondArray, int from, int to) {
		int cloneIndex;
		for (cloneIndex = 0; cloneIndex <= to - from; cloneIndex++) {
			firstArray[cloneIndex + from] = secondArray[cloneIndex];
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
