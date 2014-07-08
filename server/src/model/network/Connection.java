package model.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import model.DNDServerModel;
import model.general.BlockingQueue;
import model.network.packages.Package;
import model.network.packages.SuperPackage;

public class Connection {

	private DNDServerModel model;
	private Socket socket;
	private BlockingQueue<SuperPackage> inbox;
	private BlockingQueue<SuperPackage> outbox;

	public Connection(DNDServerModel model, Socket socket) {
		this.socket = socket;
		this.model = model;
		System.out.println(socket);
		inbox = new BlockingQueue<>();
		outbox = new BlockingQueue<>();

		new Operator().start();
		new Transmitter().start();
		new Receiver().start();
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
				SuperPackage outgoing = incomming.executeLoad(model,Connection.this);
				outbox.offer(outgoing);
			}

		}
	}
}
