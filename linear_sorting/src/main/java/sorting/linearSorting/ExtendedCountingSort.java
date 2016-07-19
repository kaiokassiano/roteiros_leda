package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {

		// checagem de condicoes anormais na ordenacao, apenas para evitar erros
		if (array == null || array.length == 0 || leftIndex < 0 || leftIndex > rightIndex || rightIndex > array.length)
			return;

		int menorElemento = procuraMenorElemento(array);
		int maiorElemento = procuraMaiorElemento(array);
		int size = (maiorElemento - menorElemento) + 1;

		// array auxiliar
		int[] count = new int[size];

		// conta as ocorrencias dos valores
		for (int i = leftIndex; i <= rightIndex; i++) {
			count[array[i] - menorElemento]++;
		}

		// substitui as ocorrencias no array original
		for (int i = 0, j = leftIndex; i < count.length; i++) {
			while (count[i] > 0) {
				array[j++] = i + menorElemento;
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

	public int procuraMenorElemento(Integer[] array) {
		int menor = array[0];

		for (int i = 1; i < array.length; i++) {
			if (array[i] < menor)
				menor = array[i];
		}

		return menor;
	}

}
