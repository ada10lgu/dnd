package server.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import server.model.Model;
import server.model.connection.Server;

@SuppressWarnings("serial")
public class Viewer extends JFrame {

	public static void main(String[] args) {
		Model m = new Model();
		Server s = new Server(m);
		new Viewer(s,m);
	}

	public Viewer(Server s,Model m) {
		super("DND-server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);

		TextArea text = new TextArea();
		s.addWritable(text);
		m.addWritable(text);
		add(text, BorderLayout.CENTER);
		setVisible(true);
	}

}
