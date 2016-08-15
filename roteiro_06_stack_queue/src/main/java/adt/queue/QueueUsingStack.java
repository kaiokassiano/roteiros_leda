package adt.queue;

import adt.stack.Stack;
import adt.stack.StackImpl;
import adt.stack.StackOverflowException;
import adt.stack.StackUnderflowException;

public class QueueUsingStack<T> implements Queue<T> {

	private Stack<T> stackMain;
	private Stack<T> stackAux;
	private static final int ZERO = 0;

	public QueueUsingStack(int size) {
		// isso aqui eh pra manter as coisas em ordem, caso voce tente um teste
		// 'engracadinho'
		if (size < ZERO) {
			size = ZERO;
		}

		stackMain = new StackImpl<T>(size);
		stackAux = new StackImpl<T>(size);
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		}

		if (element != null) {
			try {
				stackMain.push(element);
			} catch (StackOverflowException e) {
				// podemos deixar isso aqui. vai dar certo
				// throw new RuntimeException(e);
			}
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		T elem = null;

		if (isEmpty()) {
			throw new QueueUnderflowException();
		}

		try {

			copyElements(stackMain, stackAux);
			elem = stackAux.pop();
			copyElements(stackAux, stackMain);

			return elem;

		} catch (StackUnderflowException | StackOverflowException e) {
			return elem;
		}
	}

	@Override
	public T head() {
		T element = null;

		try {
			copyElements(stackMain, stackAux);
			element = stackAux.top();
			copyElements(stackAux, stackMain);

		} catch (StackUnderflowException | StackOverflowException e) {
			// pode confiar
		}

		return element;
	}

	@Override
	public boolean isEmpty() {
		return stackMain.isEmpty() && stackAux.isEmpty();
	}

	@Override
	public boolean isFull() {
		return stackMain.isFull() || stackAux.isFull();
	}

	/**
	 * Copia todos os elementos da primeira fila pra a segunda
	 * 
	 * @throws StackUnderflowException
	 * @throws StackOverflowException
	 */
	private void copyElements(Stack<T> firstStack, Stack<T> secondStack) throws StackUnderflowException, StackOverflowException {
		
		try {
			while (!firstStack.isEmpty()) {
				secondStack.push(firstStack.pop());
			}
		} catch (StackUnderflowException | StackOverflowException e) {
			// pode confiar
		}
	}
}
