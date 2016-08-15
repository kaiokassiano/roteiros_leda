package sorting.divideAndConquer.hybridMergesort;

import java.util.Arrays;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe HybridMergeSort representa a implementação de uma variação do MergeSort 
 * que pode fazer uso do InsertionSort (um algoritmo híbrido) da seguinte forma: 
 * o MergeSort é aplicado a entradas maiores a um determinado limite. Caso a entrada 
 * tenha tamanho menor ou igual ao limite o algoritmo usa o InsertionSort. 
 * 
 * A implementação híbrida deve considerar os seguintes detalhes:
 * - Ter contadores das quantidades de MergeSorts e InsertionSorts aplicados, de 
 *   forma que essa informação possa ser capturada pelo teste.
 * - A cada chamado do método de sort(T[] array) esses contadores são resetados. E a cada
 *   chamada interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e 
 *   INSERTIONSORT_APPLICATIONS são incrementados.
 *  - O InsertionSort utilizado no algoritmo híbrido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends AbstractSorting<T> {
    
	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;
	
	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;
	
	public void sort(T[] array, int leftIndex, int rightIndex) {
		MERGESORT_APPLICATIONS = 0;
		INSERTIONSORT_APPLICATIONS = 0;
		hybridSort(array, leftIndex, rightIndex);
	}
	
	private void hybridSort(T[] array, int leftIndex, int rightIndex) {
		if (array.length <= SIZE_LIMIT) {
			insertionSort(array, leftIndex, rightIndex);
		} else {
			if (leftIndex < rightIndex) {
				int mid = (leftIndex + rightIndex) / 2;
				hybridSort(array, leftIndex, mid);
				hybridSort(array, mid + 1, rightIndex);
				merge(array, leftIndex, mid, rightIndex);
			}
		}
	}
	
	private void insertionSort(T[] array, int leftIndex, int rightIndex) {
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			for (int j = i - 1; j >= leftIndex && array[j+1].compareTo(array[j]) < 0; j--) {
				Util.swap(array, j+1, j);
			}
		}
		INSERTIONSORT_APPLICATIONS++;
	}
	
	private void merge(T[] array, int leftIndex, int mid, int rightIndex) {
		T[] arrayAuxiliar = Arrays.copyOf(array, array.length);

		int i = leftIndex;
		int j = mid + 1;
		int k = leftIndex;

		while (i <= mid && j <= rightIndex) {
			if (arrayAuxiliar[i].compareTo(arrayAuxiliar[j]) < 0) {
				array[k] = arrayAuxiliar[i];
				i++;
			} else {
				array[k] = arrayAuxiliar[j];
				j++;
			}

			k++;
		}

		while (i <= mid) {
			array[k] = arrayAuxiliar[i];
			i++;
			k++;
		}
		MERGESORT_APPLICATIONS++;
	}
	
}
