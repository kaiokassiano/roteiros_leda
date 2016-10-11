
package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (super.isFull()) {
			throw new HashtableOverflowException();
		}

		if (element != null && search(element) == null) {
			int i = 0;
			int hashIndex = hashFunction(element, i);
			while (i < super.capacity()) {
				if (table[hashIndex] == null || deletedElement.equals(table[hashIndex])) {
					table[hashIndex] = element;
					super.elements++;
					return;
				} else {
					i++;
					hashIndex = hashFunction(element, i);
					super.COLLISIONS++;
				}
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element == null) {
			return;
		}
		if (this.indexOf(element) != -1) {
			int hashIndex = indexOf(element);
			table[hashIndex] = super.deletedElement;
			super.elements--;
		}
	}

	@Override
	public T search(T element) {
		if (element != null) {
			int i = 0;
			int hashIndex = hashFunction(element, i);
			while (i < super.capacity() && table[hashIndex] != null && !deletedElement.equals(table[hashIndex])) {
				if (table[hashIndex].equals(element)) {
					return element;
				} else {
					i++;
					hashIndex = hashFunction(element, i);
				}
			}
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		if (element != null) {
			int i = 0;
			int hashIndex = hashFunction(element, i);
			while (i < super.capacity() && table[hashIndex] != null && !deletedElement.equals(table[hashIndex])) {
				if (table[hashIndex].equals(element)) {
					return hashIndex;
				} else {
					i++;
					hashIndex = hashFunction(element, i);
				}
			}
		}
		return -1;
	}

	private int hashFunction(T element, int probe) {
		int hashIndex = ((HashFunctionOpenAddress<T>) super.hashFunction).hash(element, probe);
		return hashIndex;
	}

}
=======
	if(super.isFull()){
 			throw new HashtableOverflowException();
  		}
  		
  		if(element != null && search(element) == null){
  			int i = 0;
  			int hashIndex = hashFunction(element, i);
  			while(i < super.capacity()){
  				if(table[hashIndex] == null || deletedElement.equals(table[hashIndex])){
  					table[hashIndex] = element;
  					super.elements++;
  					return;
  				}else{
  					i++;
  					hashIndex = hashFunction(element, i);
  					super.COLLISIONS++;
  				}
  			}
  		}
  	}
  
  	@Override
  	public void remove(T element) {
  		if(element == null){
  			return;
  		}
  		if(this.indexOf(element) != -1){
  			int hashIndex = indexOf(element);
  			table[hashIndex] = super.deletedElement;
  			super.elements--;
  		}
  	}
  
  	@Override
  	public T search(T element) {
  		if(element != null){
  			int i = 0;
  			int hashIndex = hashFunction(element, i);
  			while(i < super.capacity() && table[hashIndex] != null && !deletedElement.equals(table[hashIndex])){
  					if(table[hashIndex].equals(element)){
  						return element;
  					}else{
  						i++;
  						hashIndex = hashFunction(element, i);
 					}
  			}
  		}
  		return null;
  	}
  
  	@Override
  	public int indexOf(T element) {
  		if(element != null){
  			int i = 0;
  			int hashIndex = hashFunction(element, i);
  			while(i < super.capacity() && table[hashIndex] != null && !deletedElement.equals(table[hashIndex])){
  					if(table[hashIndex].equals(element)){
  						return hashIndex;
  					}else{
  						i++;
  						hashIndex = hashFunction(element, i);
  					}
  			}
  		}
  		return -1;
  	}
  	
  	private int hashFunction(T element, int probe){
  		int hashIndex = ((HashFunctionOpenAddress<T>)super.hashFunction).hash(element, probe);
  		return hashIndex;
  	}
  
  }

>>>>>>> 341b34ae4084a9f13f67dc7a1bb54575df6603ab
