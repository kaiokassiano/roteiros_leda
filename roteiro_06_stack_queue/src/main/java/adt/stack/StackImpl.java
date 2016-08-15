package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;
	private final int CAP;
	private static final int ZERO = 0;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		// isso aqui eh pra manter as coisas em ordem, caso voce tente um teste 'engracadinho'
		if (size < ZERO) {
			size = ZERO;
		}
		
		array = (T[]) new Object[size];
		this.top = -1;
		CAP = size;
	}

	@Override
	public T top() {
		if (this.isEmpty()) {
			return null;
		} else {
			return this.array[this.top];
		}
	}

	@Override
	public boolean isEmpty() {
		return this.top == -1;
	}

	@Override
	public boolean isFull() {
		return this.top == CAP - 1;
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (isFull()) {
			throw new StackOverflowException();
		} else {
			if (element != null) {
				this.top++;
				this.array[this.top] = element;
			}
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		} else {
			T elem = this.array[this.top];
			this.top--;
			return elem;
		}
	}

}
