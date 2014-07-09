package model.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class IDList {
	
	private Random r;
	private HashSet<Byte> history;
	private ArrayList<Byte> ids;
	
	public IDList() {
		history=new HashSet<>();
		ids = new ArrayList<>();
		r = new Random();
	}
	
	public byte getID() {
		if (ids.isEmpty())
			generateIDs();
		return ids.remove(r.nextInt(ids.size()));
	}
	
	public void returnID(byte id) {
		ids.add(id);
	}
	
	private void generateIDs() {
		for (int i = 0; i < 10; i++) {
			byte value = generateID();
			while (!history.contains(value))
				value++;
			history.add(value);
			ids.add(value);
		}
	}
	
	private byte generateID() {
		byte[] data = new byte[1];
		r.nextBytes(data);
		return data[0];
	}
}
