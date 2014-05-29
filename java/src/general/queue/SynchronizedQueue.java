package general.queue;

import general.connection.packages.SuperPackage;

import java.util.PriorityQueue;

public class SynchronizedQueue {

	private PriorityQueue<SuperPackage> queue = new PriorityQueue<>();
	
	
	public final synchronized void  offer(SuperPackage p) {
		queue.offer(p);
		notifyAll();
	}
	
	public final synchronized SuperPackage poll() {
		while (queue.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(5);
			}
		return queue.poll();
	}
	
	
	
}
