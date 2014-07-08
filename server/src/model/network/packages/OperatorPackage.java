package model.network.packages;

import model.DNDServerModel;
import model.network.Connection;

public abstract class OperatorPackage extends Package {

	public OperatorPackage(byte command, String name) {
		super(command, name);
	}

	public abstract OperatorPackage perform(DNDServerModel model, Connection conn);
	
}
