package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure evitar desperdicio de 
 * memoria alocando o array de contadores com o tamanho sendo o máximo inteiro presente no array 
 * a ser ordenado.  
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array,int leftIndex, int rightIndex) {
		
		if (array == null || array.length == 0)
			return;
		
		int maiorElemento = searchBiggestElement(array);
		Integer[] count = new Integer[maiorElemento + 1];
		
		// transforma null em 0
		for (int i = 0; i < count.length; i++) {
			if (count[i] == null)
				count[i] = 0;
		}
		
		// conta
		for (int i = leftIndex; i <= rightIndex; i++) {
			count[array[i]]++;
		}
		
		// joga no vetor original
		for (int i = 0, j = leftIndex; i < count.length; i++) {
			while (count[i]-- > 0)
				array[j++] = i;
		}
		
	}
	
	public int searchBiggestElement(Integer[] array) {
		int maior = array[0];
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] > maior)
				maior = array[i];
		}
		
		return maior;
	}

}
