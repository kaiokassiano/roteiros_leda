package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		// checagem de condicoes anormais na ordenacao, apenas para evitar erros
		if (array == null || array.length == 0 || leftIndex < 0 || leftIndex > rightIndex || rightIndex > array.length)
			return;

		int maiorElemento = procuraMaiorElemento(array);
		int size = maiorElemento + 1;

		// array auxiliar
		int[] count = new int[size];

		// conta as ocorrencias dos numeros
		for (int i = leftIndex; i <= rightIndex; i++) {
			count[array[i]]++;
		}

		// substitui as ocorrencias no vetor original
		for (int i = 0, j = leftIndex; i < count.length; i++) {

			while (count[i] > 0) {
				array[j++] = i;
				count[i]--;
			}
		}
	}

	public int procuraMaiorElemento(Integer[] array) {
		int maior = array[0];

		for (int i = 1; i < array.length; i++) {
			if (array[i] > maior)
				maior = array[i];
		}

		return maior;
	}

}
