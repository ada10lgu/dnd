package general.connection.packages;

import java.io.IOException;
import java.io.InputStream;

public class SuperPackage extends Package {

	private BytePackage id;
	private BytePackage way;
	private Package load;

	public SuperPackage(byte id, byte way, Package load) {
		super(SUPER);
		this.id = new BytePackage(id);
		this.way = new BytePackage(way);
		this.load = load;

	}

	protected SuperPackage(InputStream is) throws IOException {
		super(LOGIN);
		byte data = read(is);
		if (data != 0)
			throw new IOException("SuperPackage does not support datafield");
		byte size = read(is);
		if (size != 3)
			throw new IOException("SuperPackage demands 3 packages");
		id = (BytePackage) generateFromStream(is);
		way = (BytePackage) generateFromStream(is);
		load = generateFromStream(is);
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
	
	public Package getLoad() {
		return load;
	}
	
	public byte getID() {
		return (id.getLoad())[0];
	}
	
	public boolean isACK() {
		return way.getLoad()[0] == ACK;
	}

}
