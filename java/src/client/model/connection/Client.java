package client.model.connection;

import general.connection.packages.Package;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Client {

	private Socket s;
	OutputStream os;

	public Client(String address, int port) {
		try {
			s = new Socket(address, port);
			os = s.getOutputStream();
		} catch (IOException e) {
			System.err.println("Problem connection to host!");

			e.printStackTrace();
			System.exit(1);
		}
	}

	public void write(Package p) {
		try {
			os.write(p.getBytes());
			System.out.println("Wrote - " + Arrays.toString(p.getBytes()));
		} catch (IOException e) {
			System.err.println("Could not write to stream, teminating system!");
			close();
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void write(String s) {
		try {
			os.write(s.getBytes());
			os.write('\n');
			os.flush();
		} catch (IOException e) {
			System.err.println("Could not write to stream, teminating system!");
			close();
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void close() {
		try {
			s.close();
		} catch (IOException e) {
			System.err.println("Problem closing socket");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
