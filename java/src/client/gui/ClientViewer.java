package client.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import client.gui.user.login.LoginPane;
import client.model.UserModel;

@SuppressWarnings("serial")
public class ClientViewer extends JFrame implements WindowListener {

	public static void main(String[] args) {
		new ClientViewer(new UserModel());
	}

	private UserModel m;

	public ClientViewer(UserModel m) {
		super("DND-mög!");
		this.m = m;
		setSize(500, 300);
		addWindowListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		add(new LoginPane(m), BorderLayout.CENTER);

		setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		m.close();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
