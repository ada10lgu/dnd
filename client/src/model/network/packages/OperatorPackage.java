package model.network.packages;

import model.DNDModel;
import model.network.Connection;

public abstract class OperatorPackage extends Package {

	public OperatorPackage(byte command, String name) {
		super(command, name);
	}

	public abstract OperatorPackage perform(DNDModel model, Connection conn);
	
}
