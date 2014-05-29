package general;

import general.connection.connection.Connection;

public interface GenericModel {

	int login(String user, String pass,Connection c);

	void exit(Connection c);

}
