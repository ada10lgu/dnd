package general.connection.connection;

import general.GenericModel;
import general.connection.packages.OperatorPackage;
import general.connection.packages.Package;
import general.connection.packages.SuperPackage;
import general.queue.SynchronizedMap;
import general.queue.SynchronizedQueue;
import general.write.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection extends Writer {

	private static int count = 0;

	private Socket s;
	private GenericModel m;

	private SynchronizedQueue inbox;
	private SynchronizedQueue outgoing;
	private SynchronizedMap response;
	private byte packageN = 0;
	
	
	public synchronized static Connection createConnection(Socket s,
			GenericModel m) {
		int id = count++;
		return new Connection(id, s, m);
	}
	
	public synchronized static Connection createConnection(String address, int port, GenericModel m) throws IOException {
		Socket s = new Socket(address,port);
		return createConnection(s, m);
	}

	private Connection(int id, Socket s, GenericModel m) {
		super("Connection #" + id);

		this.s = s;
		this.m = m;
		inbox = new SynchronizedQueue();
		outgoing = new SynchronizedQueue();
		response = new SynchronizedMap();

		new PackageHandler().start();
		new Outgoing().start();
		new Inbox().start();
	}

	public synchronized byte write(OperatorPackage op) {
		SuperPackage sp = new SuperPackage(packageN++, SuperPackage.SEND, op);
		outgoing.offer(sp);
		return sp.getID();
	}

	public synchronized OperatorPackage getResponse(byte id) {
		return response.get(id).getLoad();
	}
	
	public void close() {
		try {
			s.close();
		} catch (IOException e) {
			write("Error trying to close socket");
			e.printStackTrace();
		}
	}

	private class PackageHandler extends Thread {
		@Override
		public void run() {
			write("Connection established from " + s.getLocalAddress() + ":"
					+ s.getLocalPort());
			while (!s.isClosed()) {
				SuperPackage p = inbox.poll();
				if (p.isACK()) {
					response.put(p);
				} else {
					OperatorPackage op = p.getLoad().perform(m,Connection.this);
					if (op != null)
						inbox.offer(new SuperPackage(p.getID(), Package.ACK, op));
				}
			}
			write("Connection is closed.");
		}
	}

	private class Outgoing extends Thread {
		@Override
		public void run() {
			try {
				OutputStream os = s.getOutputStream();
				while (!s.isClosed()) {
					SuperPackage p = outgoing.poll();
					os.write(p.getBytes());
				}

			} catch (IOException e) {
				write("Error: Connection teminated wrongly [OS].");
				write("Error: Look in the log for more details");
				e.printStackTrace();
			}
		}
	}

	private class Inbox extends Thread {

		@Override
		public void run() {

			InputStream is;
			try {
				is = s.getInputStream();
				while (!s.isClosed()) {
					SuperPackage p = (SuperPackage) Package
							.generateFromStream(is);
					inbox.offer(p);
				}
			} catch (IOException e) {
				write("Error: Connection teminated wrongly [IS].");
				write("Error: Look in the log for more details");
				e.printStackTrace();
			}
		}
	}
}
