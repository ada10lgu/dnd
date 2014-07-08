package model.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.DNDServerModel;

public class Server extends Thread {

	private ServerSocket ss;
	private DNDServerModel model;

	public Server(DNDServerModel model, int port) throws IOException {
		ss = new ServerSocket(port);
		this.model = model;
		System.out.println("Starting server");
		start();
	}

	public void run() {

		try {
			while (!isInterrupted()) {
				Socket s = ss.accept();
				new Connection(model,s);
				
			}
		} catch (IOException e) {

		}

	}
}
