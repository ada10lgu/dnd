package model.general;

import java.util.LinkedList;

public class BlockingQueue<E> {
	private LinkedList<E> queue;
	
	
	public BlockingQueue() {
		queue = new LinkedList<>();
	}
	
	public synchronized void offer(E e) {
		queue.offer(e);
		notifyAll();
	}
	
	public synchronized E poll() {
		while (queue.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return queue.poll();
	}
	@Override
	public String toString() {
		return queue.toString();
	}
	
}
