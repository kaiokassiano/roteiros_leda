package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	static final int ZERO = 0;

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);

		if (size < ZERO)
			size = ZERO;

		hashFunction = new HashFunctionLinearProbing<T>(size, method);
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
		return ((HashFunctionLinearProbing<T>) super.getHashFunction()).hash(element, probe);
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
