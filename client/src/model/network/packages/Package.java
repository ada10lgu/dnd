package model.network.packages;

import java.io.IOException;
import java.io.InputStream;

import model.network.packages.data.BytePackage;
import model.network.packages.data.IntegerPackage;
import model.network.packages.data.StringPackage;
import model.network.packages.operator.DataPackage;
import model.network.packages.operator.LoginPackage;
import model.network.packages.operator.UserRequest;

public abstract class Package {
	public static final byte SUPER = 1;
	public static final byte UNKNOWN = 2;
	public static final byte DATA = 3;
	public static final byte EXIT = 4;

	public static final byte LOGIN = 10;
	public static final byte USER_REQUEST = 11;
	
	public static final byte INT = 20;
	public static final byte STRING = 21;
	public static final byte BYTE = 22;
	
	public static final byte NULL = 41;
	
	
	private byte command;
	private String name;

	public static Package generateFromStream(InputStream is) throws IOException {

		byte command = read(is);

		
		switch (command) {
		case SUPER:
			return new SuperPackage(is);
		case DATA:
			return new DataPackage(is);
		case LOGIN:
			return new LoginPackage(is);
		case USER_REQUEST:
			return new UserRequest(is);
		case INT:
			return new IntegerPackage(is);
		case STRING:
			return new StringPackage(is);
		case BYTE:
			return new BytePackage(is);
		case NULL:
			return new BytePackage(is);
		default:
			return new UnknownPackage(command, is); 
		}

	}

	protected final static byte read(InputStream is) throws IOException {
		int i = is.read();
		if (i == -1)
			throw new IOException("Interupted stream!");
		return (byte) i;
	}

	public Package(byte command, String name) {
		this.command = command;
		this.name = name;
	}

	protected abstract byte[] getData();

	protected abstract Package[] getPackages();

	protected byte[] readData(InputStream is, int data_ammount)
			throws IOException {
		byte size = read(is);
		if (data_ammount == 0 && size != 0)
			throw new IOException(name + " does not support datafield");
		if (data_ammount == 1 && size == 0)
			throw new IOException(name + " requires datafield");
		byte[] load = new byte[size];
		for (int i = 0; i < size; i++)
			load[i] = read(is);
		return load;
	}

	protected Package[] readPackages(InputStream is, int package_ammount)
			throws IOException {
		int size = read(is);
		if (package_ammount == 0 && size != 0)
			throw new IOException(name + " does not support extra package load");
		if (package_ammount > 0 && package_ammount != size)
			throw new IOException(name
					+ " require a specific ammount of package load");
		Package[] packages = new Package[size];
		for (int i = 0; i < size; i++)
			packages[i] = generateFromStream(is);		
		return packages;
	}

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

	private int addToArray(byte[] destination, int start, byte... from) {
		for (byte b : from)
			destination[start++] = b;
		return start;

	}
}