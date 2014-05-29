package general.connection.packages;

import general.connection.packages.data.BytePackage;
import general.connection.packages.data.IntegerPackage;
import general.connection.packages.data.NullPackage;
import general.connection.packages.data.StringPackage;
import general.connection.packages.operator.DataPackage;
import general.connection.packages.operator.ExitPackage;
import general.connection.packages.operator.LoginPackage;

import java.io.IOException;
import java.io.InputStream;

public abstract class Package {

	public static final byte SEND = 0;
	public static final byte ACK = 1;

	public static final byte SUPER = 1;
	public static final byte DATA = 3;
	public static final byte EXIT = 4;
	
	public static final byte LOGIN = 10;
	
	public static final byte INT = 20;
	public static final byte STRING = 21;
	public static final byte BYTE = 22;
	public static final byte NULL = 41;

	private byte command;

	public static Package generateFromStream(InputStream is) throws IOException {

		byte command = read(is);

		switch (command) {
		case SUPER:
			return new SuperPackage(is);
		case DATA:
			return new DataPackage(is);
		case EXIT:
			return new ExitPackage(is);
		case LOGIN:
			return new LoginPackage(is);
		case INT:
			return new IntegerPackage(is);
		case STRING:
			return new StringPackage(is);
		case BYTE:
			return new BytePackage(is);
		case NULL:
			return new NullPackage(is);
		default:
			throw new IOException("Illegal package (" + command + ")");
		}
	}

	protected final static byte read(InputStream is) throws IOException {
		int i = is.read();
		if (i == -1)
			throw new IOException("Interupted stream!");
		return (byte) i;
	}

	public Package(byte command) {
		this.command = command;
	}

	protected abstract byte[] getData();

	protected abstract Package[] getPackages();

	public final byte[] getBytes() {
		int size = 2 + getData().length + 1;
		for (Package p : getPackages())
			size += p.getBytes().length;
		byte[] data = new byte[size];

		int i = 0;
		data[i++] = command;
		data[i++] = (byte) getData().length;
		i = addToArray(data, i, getData());

		data[i++] = (byte) getPackages().length;
		for (Package p : getPackages())
			i = addToArray(data, i, p.getBytes());
		return data;
	}

	public final boolean isCommand(byte command) {
		return command == this.command;
	}

	private int addToArray(byte[] destination, int start, byte... from) {
		for (byte b : from)
			destination[start++] = b;
		return start;

	}

}
