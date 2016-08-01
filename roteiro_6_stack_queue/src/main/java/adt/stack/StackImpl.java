package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;
	private int size;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
		this.size = size;
	}

	@Override
	public T top() {
		return array[this.top];
	}

	@Override
	public boolean isEmpty() {
		if (this.top == -1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isFull() {
		if (this.top == this.size - 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (isFull()) {
			throw new StackOverflowException();
		} else {
			this.top++;
			array[this.top] = element;
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty()) {
			throw new StackUnderflowException();
		} else {
			T elem = array[this.top];
			this.top--;
			return elem;
		}
	}

}
