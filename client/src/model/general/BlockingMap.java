package model.general;

import java.util.HashMap;

public class BlockingMap<E,F> {
	private HashMap<E,F> map;
	
	public BlockingMap() {
		map = new HashMap<>();
	}
	
	public synchronized void put(E key, F value) {
		map.put(key, value);
		notifyAll();
	}
	
	public synchronized F get(E key) {
		F value = null;
		while ((value = map.get(key)) == null)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return value;
	}
}
