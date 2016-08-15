package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private final int CAP;
	private static final int ZERO = 0;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		// isso aqui eh pra manter as coisas em ordem, caso voce tente um teste 'engracadinho'
		if (size < ZERO) {
			size = ZERO;
		}
		
		array = (T[]) new Object[size];
		this.tail = -1;
		CAP = size;
	}

	@Override
	public T head() {
		return this.array[ZERO];
	}

	@Override
	public boolean isEmpty() {
		return this.tail == -1;
	}

	@Override
	public boolean isFull() {
		return this.tail == CAP - 1;
	}

	private void shiftLeft() {
		for (int i = ZERO; i < this.tail; i++) {
			this.array[i] = this.array[i + 1];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		} else {
			if (element != null) {
				this.tail++;
				this.array[this.tail] = element;
			}
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		} else {
			T elem = this.array[ZERO];
			this.shiftLeft();
			this.tail--;
			
			return elem;
		}
	}

}
