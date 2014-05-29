package server.model.connection;

import general.connection.connection.Connection;
import general.write.Writer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.model.Model;

public class Server extends Writer {
	
	private static final int PORT = 12345;
	
	private Model m;
	
	public Server(Model m) {
		super("Server");
		this.m = m;
		new Listner().start();

	}

	private class Listner extends Thread {

		private ServerSocket ss;
		
		public Listner() {
			try {
				write("Opening socket on port " + PORT);
				ss = new ServerSocket(PORT);
			} catch (IOException e) {
				System.err.println("Could not start socket!");
				e.printStackTrace();
				System.exit(1);
			}
		}

		@Override
		public void run() {
			try {
				while (!isInterrupted() && !ss.isClosed()) {
					write("Waiting for connection...");
					Socket s = ss.accept();
					Connection.createConnection(s, m).addWriteablesFrom(Server.this);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
