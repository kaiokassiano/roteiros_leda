package sorting;

public class Counting {
	
	public void sort(int[] array, int leftIndex, int rightIndex) {
		
		if (array == null || array.length == 0 || (leftIndex - rightIndex) <= 0)
			return;
		
		int maiorElemento = procuraMaiorElemento(array, leftIndex, rightIndex);
		int[] count = new int[maiorElemento + 1];
		
		for (int i = leftIndex; i <= rightIndex; i++) {
			count[array[i]]++;
		}
		
		for (int i = 0, j = rightIndex; i < count.length; i++) {
			while (count[i]-- > 0)
				array[j++] = i;
		}
		
	}
	
	public int procuraMaiorElemento(int[] array, int leftIndex, int rightIndex) {
		int maior = array[0];
		
		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			if (array[i] > maior)
				maior = array[i];
		}
		
		return maior;
	}

}
