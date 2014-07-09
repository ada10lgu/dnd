package model.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import model.DNDModel;
import model.general.BlockingMap;
import model.general.BlockingQueue;
import model.general.IDList;
import model.network.packages.OperatorPackage;
import model.network.packages.Package;
import model.network.packages.SuperPackage;

public class Connection {

	private DNDModel model;
	private Socket socket;
	private BlockingQueue<SuperPackage> inbox;
	private BlockingQueue<SuperPackage> outbox;
	private BlockingMap<Byte, SuperPackage> responses;
	private IDList ids;

	public Connection(DNDModel model, Socket socket) {
		this.socket = socket;
		this.model = model;
		System.out.println(socket);
		ids = new IDList();
		responses = new BlockingMap<>();

		inbox = new BlockingQueue<>();
		outbox = new BlockingQueue<>();

		new Operator().start();
		new Transmitter().start();
		new Receiver().start();
	}

	public synchronized int sendPackage(OperatorPackage op) {
		byte id = ids.getID();
		SuperPackage sp = new SuperPackage(id, op);
		outbox.offer(sp);
		return id;
	}

	public synchronized OperatorPackage getResponse(byte id) {
		SuperPackage sp = responses.get(id);
		ids.returnID(id);
		return sp.getLoad();
	}

	private synchronized void close() {
		try {
			if (!socket.isClosed()) {
				System.out
						.println("Closing socket (" + socket.toString() + ")");
				socket.close();
				System.out.println("Closed!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Receiver extends Thread {

		public void run() {
			try {
				InputStream is = socket.getInputStream();

				while (!socket.isClosed()) {
					SuperPackage p = (SuperPackage) Package
							.generateFromStream(is);
					System.out.println("Recieved " + p);
					inbox.offer(p);
				}
			} catch (IOException e) {
				close();
			}

		}
	}

	private class Transmitter extends Thread {
		@Override
		public void run() {
			try {
				OutputStream os = socket.getOutputStream();
				while (!socket.isClosed()) {
					SuperPackage sp = outbox.poll();
					System.out.println("Writing (" + sp + ")");
					os.write(sp.getBytes());
				}
			} catch (IOException e) {
				close();
			}

		}
	}

	private class Operator extends Thread {

		@Override
		public void run() {
			while (!socket.isClosed()) {
				SuperPackage incomming = inbox.poll();
				if (incomming.isACK()) {
					responses.put(incomming.getID(), incomming);
				} else {
					SuperPackage outgoing = incomming.executeLoad(model,
							Connection.this);
					outbox.offer(outgoing);
				}
			}
		}
	}
}
