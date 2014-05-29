package general.connection.packages;

import general.GenericModel;
import general.connection.connection.Connection;

public abstract class OperatorPackage extends Package {

	public OperatorPackage(byte command) {
		super(command);
	}

	
	public abstract OperatorPackage perform(GenericModel model,Connection c); 
	
}
