package adt.hashtable.closed;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
														// the immediate prime
														// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method, realSize);
		this.hashFunction = function;
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {
		int prime = number;

		if (prime % 2 == 0)
			prime++;

		while (!Util.isPrime(prime))
			prime += 2;

		return prime;
	}

	@Override
	public void remove(T element) {
		if (element != null && !this.isEmpty()) {

			int index = getHashFunction().hash(element);
			int size = getCell(index).size();
			int elemPst = searchPosition(element);

			if (elemPst != -1) {

				if (size > 1)
					COLLISIONS--;

				getCell(index).remove(elemPst);
				elements--;
			}
		}
	}
	
	@Override
	public void insert(T element) {
		if (element != null) {

			int index = hashIt(element);

			boolean cont = searchPosition(element) != -1;
			boolean containsElem = !getCell(index).isEmpty();

			if (!cont) {

				if (containsElem) {
					COLLISIONS++;
				}

				getCell(index).add(element);
				elements++;
			}
		}
	}

	@Override
	public T search(T element) {
		T value = null;

		if (element != null) {
			if (indexOf(element) != -1) {

				int elemPst = searchPosition(element);
				int index = hashIt(element);

				value = getCell(index).get(elemPst);

			}
		}

		return value;
	}

	@Override
	public int indexOf(T element) {
		int index = -1;

		if (element != null) {

			if (!isEmpty()) {

				int elemPst = hashIt(element);

				if (searchPosition(element) != -1)
					index = elemPst;

			}
		}

		return index;
	}

	@Override
	public HashFunctionClosedAddress<T> getHashFunction() {
		return (HashFunctionClosedAddress<T>) super.getHashFunction();
	}

	// apenas pra eu usar depois varias vezes, pra evitar repeticao de codigo
	// pode confiar
	private int hashIt(T element) {
		return getHashFunction().hash(element);
	}

	@SuppressWarnings("unchecked")
	private LinkedList<T> getCell(int index) {
		if (super.table[index] == null)
			super.table[index] = new LinkedList<T>();
		return (LinkedList<T>) super.table[index];
	}

	private int searchPosition(T element) {

		int index = hashIt(element);

		LinkedList<T> cell = getCell(index);

		int elemHash = element.hashCode();
		int elemCell = 0;

		for (T step : cell) {
			int stepHash = step.hashCode();

			if (stepHash == elemHash)
				break;

			elemCell++;
		}

		if (elemCell == cell.size())
			elemCell = -1;

		return elemCell;
	}

}
