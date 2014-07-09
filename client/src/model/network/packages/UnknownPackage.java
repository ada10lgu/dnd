package model.network.packages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class UnknownPackage extends Package {

	byte command;
	byte[] data;
	Package[] packages;
	
	public UnknownPackage(byte command,InputStream is) throws IOException {
		super(command, "Unknwon package");
		this.command = command;
		data = readData(is, -1);
		packages = readPackages(is, -1);
	}

	@Override
	protected byte[] getData() {
		return data;
	}

	@Override
	protected Package[] getPackages() {
		return packages;
	}
	
	@Override
	public String toString() {
		return "[Unknwon package("+command+") DATA("+Arrays.toString(data)+") PACKAGES("+Arrays.toString(packages)+")";
		
	}

}
