package general.queue;

import general.connection.packages.SuperPackage;

import java.util.HashMap;

public class SynchronizedMap {

	private HashMap<Byte,SuperPackage> map = new HashMap<>();
	
	
	public final synchronized void  put(SuperPackage p) {
		map.put(p.getID(),p);
		notifyAll();
	}
	
	public final synchronized SuperPackage get(byte id) {
		while (map.get(id)!= null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return map.remove(id);
	}
	
	
	
}
