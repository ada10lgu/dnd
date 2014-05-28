package server.model.connection;

import general.connection.packages.LoginPackage;
import general.connection.packages.Package;
import general.connection.packages.SuperPackage;
import general.write.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.model.Model;

public class Connection extends Writer {

	private static int count = 0;
	
	
	private Socket s;
	private Model m;
	
	private int n = -1;
	private int user = -1;
	
	
	
	
	public Connection(Socket s,Model m) {
		super("Connection");
		n = getCount();
		updateName("Connection #" + n);
		this.s = s;
		this.m = m;
		new SocketHandler().start();
	}

	private synchronized static int getCount() {
		return count++;
	}

	private class SocketHandler extends Thread {

		@Override
		public void run() {
			write("Connection established from " + s.getLocalAddress() + ":"
					+ s.getLocalPort());
			InputStream is;
			try {
				is = s.getInputStream();
				while (!s.isClosed()) {
					SuperPackage p = (SuperPackage) Package.generateFromStream(is);
					
					Package load = p.getLoad();
					
					if ( user == -1) {
						if (load.isCommand(Package.LOGIN)) {
							int id = m.login((LoginPackage) load);
							if (id != -1) {
								write("User id " + id + " has logged in");
								user = id;
							} else {
								write("Failed login");
							}
						} else {
							
							write("Illegal package while loging in, reminating link.");
							write(p.toString());
							s.close();
						}
					} else {
						write (p.toString());
					}
				}
				write("Closing connection.");
				s.close();
			} catch (IOException e) {
				write("Error: Connection teminated wrongly.");
				write("Error: Look in the log for more details");
				e.printStackTrace();
			}
		}
	}
}
