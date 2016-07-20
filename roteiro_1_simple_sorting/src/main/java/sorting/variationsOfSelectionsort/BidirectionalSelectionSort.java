package sorting.variationsOfSelectionsort;

import sorting.AbstractSorting;
import util.Util;

/**
 * This selection sort variation has two internal iterations. In the first, it takes the
 * smallest elements from the array, and puts it in the first position. In the second,
 * the iteration is done backwards, that is, from right to left, and this time the biggest
 * element is selected and stored in the last position. Then it repeats the process,
 * excluding the positions already filled in, until the whole array is ordered.
 */
public class BidirectionalSelectionSort<T extends Comparable<T>> extends AbstractSorting<T>{

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		while (leftIndex < rightIndex) {
            int max = leftIndex;

            for (int i = leftIndex; i <= rightIndex; i++) {
                if (array[i].compareTo(array[max]) > 0) {
                    max = i;
                }
            }

            Util.swap(array, rightIndex, max);
            rightIndex--;

            int min = rightIndex;
            for (int i = rightIndex; i >= leftIndex; i--) {
                if (array[i].compareTo(array[min]) < 0) {
                    min = i;
                }
            }

            Util.swap(array, leftIndex, min);
            leftIndex++;
        }
	}
}
