package model.network.packages;

import java.io.IOException;
import java.io.InputStream;

import model.DNDServerModel;
import model.network.Connection;
import model.network.packages.data.BytePackage;
import model.network.packages.data.NullPackage;
import model.network.packages.operator.DataPackage;

public class SuperPackage extends Package {

	public static final byte SEND = 0;
	public static final byte ACK = 1;
	
	private BytePackage id;
	private BytePackage way;
	private OperatorPackage load;

	public SuperPackage(byte id, OperatorPackage load) {
		super(SUPER, "Superpackage");
		this.load = load;
		this.id = new BytePackage(id);
		way = new BytePackage(SEND);
	}
	public SuperPackage(InputStream is) throws IOException {
		super(SUPER, "Superpackage");
		readData(is, 0);
		Package[] packages = readPackages(is, 3);

		id = (BytePackage) packages[0];
		way = (BytePackage) packages[1];
		load = (OperatorPackage) packages[2];
	}

	private SuperPackage(BytePackage id, OperatorPackage op) {
		super(SUPER, "Superpackage");
		this.id = id;
		way = new BytePackage(ACK);
		load = op;
	}
	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return new Package[] { id, way, load };
	}
	
	@Override
	public String toString() {
		return "SuperPackage [id="+id.toString()+", way="+way.toString()+"] Load="+load.toString();
	}
	
	public OperatorPackage getLoad() {
		return load;
	}
	
	public byte getID() {
		return (id.toByte())[0];
	}
	
	public boolean isACK() {
		return way.toByte()[0] == ACK;
	}
	public SuperPackage executeLoad(DNDServerModel model, Connection conn) {
		OperatorPackage op = load.perform(model, conn);
		if (op == null)
			op = new DataPackage(new NullPackage());
		return new SuperPackage(id,op);
	}

}
