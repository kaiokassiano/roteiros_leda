package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	static final int ZERO = 0;
	
	// Aparentemente, os dois codigos sao iguais. A unica diferenca eh o cast na funcao hash.
	// Essa mudanca esta feita em cada um desses dois metodos hashIt
	// Dessa forma, o ctrl+c e ctrl+v foi feito de uma classe para a outra, sem que seja algo catastrofico.
	
	public HashtableOpenAddressQuadraticProbingImpl(int size, HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		
		if (size < ZERO)
			size = ZERO;
			
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(T element) {
		if (isFull()) {
			throw new HashtableOverflowException();

		} else {

			if (element != null && search(element) == null) {

				int probe = 0;
				int key = hashIt(element, probe);
				T aux = (T) super.table[key];

				if (collisionOccur(key))
					COLLISIONS++;

				while (aux != null && probe < this.capacity()) {
					probe++;
					key = hashIt(element, probe);
					aux = (T) super.table[key];

					if (collisionOccur(key))
						COLLISIONS++;

				}

				super.table[key] = element;
				elements++;

			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null) {

			if (indexOf(element) != -1) {
				table[indexOf(element)] = new DELETED();
				elements--;
			}
		}
	}

	@Override
	public T search(T element) {
		return indexOf(element) == -1 ? null : element;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int indexOf(T element) {
		if (element != null) {

			int probe = 0;
			int key = hashIt(element, probe);
			T aux = (T) super.table[key];

			while (aux != null && !aux.equals(element) && probe < this.capacity()) {
				probe++;
				key = hashIt(element, probe);
				aux = (T) super.table[key];
			}

			if (aux != null && aux.equals(element)) {
				return key;
			}

		}

		return -1;
	}

	// Todos os metodos abaixo sao metodos auxiliares, apenas para reuso e
	// facilitar a escrita do codigo
	// Nao tem nenhuma gambiarra. Pode confiar :}

	/**
	 * Faz o hash de um elemento
	 * 
	 * @param element
	 * @param prob
	 * @return
	 */
	private int hashIt(T element, int probe) {
		// Aqui esta a unica mudanca do codigo, que eh o cast para a funcao hash adequada
		return ((HashFunctionQuadraticProbing<T>) super.getHashFunction()).hash(element, probe);
	}

	/**
	 * Checa a ocorrencia de colisoes no array
	 * 
	 * @param key
	 * @return
	 */
	private boolean collisionOccur(int key) {
		return super.table[key] != null;
	}
}
