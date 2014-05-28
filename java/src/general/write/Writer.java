package general.write;

import java.util.ArrayList;

public abstract class Writer {

	private ArrayList<Writeable> w = new ArrayList<>();
	private ArrayList<String> buffer = new ArrayList<>();
	private String name;

	public Writer(String name) {
		this.name = name;
	}

	protected void updateName(String name) {
		this.name = name;

	}

	protected void write(String s) {
		if (w.isEmpty())
			buffer.add(s);
		for (Writeable w : this.w) {
			w.write(name, s);
		}
	}

	public void addWritable(Writeable w) {
		this.w.add(w);
		if (this.w.size() == 1)
			for (String s : buffer)
				write(s);
	}

	public void addWriteablesFrom(Writer wFrom) {
		for (Writeable w : wFrom.w) {
			addWritable(w);
		}
	}
}
