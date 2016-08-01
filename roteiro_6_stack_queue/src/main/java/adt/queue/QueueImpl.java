package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int size;
	
	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[])new Object[size];
		tail = -1;
		this.size = size;
	}

	@Override
	public T head() {
		return array[0];
	}

	@Override
	public boolean isEmpty() {
		if (this.tail < 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isFull() {
		if (this.tail + 1 == this.size) {
			return true;
		} else {
			return false;
		}
	}
	
	private void shiftLeft(){
		for (int i = 0; i < tail; i++) {
			array[i] = array[i + 1];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		} else {
			tail++;
			array[tail] = element;
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		} else {
			T elem = array[0];
			shiftLeft();
			tail--;
			return elem;
		}
	}


}
