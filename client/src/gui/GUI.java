package gui;

import gui.parts.login.StartUpScreen;
import gui.parts.main.MainScreen;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public static void main(String[] args) {
		new GUI();
	}

	private JPanel mainContainer;
	private StartUpScreen login;
	private boolean show = true;

	public GUI() {
		setSize(700, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		mainContainer = new JPanel();
		mainContainer.setLayout(new BorderLayout());

		login = new StartUpScreen(this);
		mainContainer.add(login, BorderLayout.CENTER);

		add(mainContainer);
		setVisible(true);
		new ScreenHandler().start();
	}

	public synchronized void showLogin(boolean show) {
		this.show = show;
		notifyAll();
	}

	private synchronized void waitForLogin() {
		while (!show)
			try {
				wait();
			} catch (InterruptedException e) {
			}

	}

	private synchronized void waitForMain() {
		while (show)
			try {
				wait();
			} catch (InterruptedException e) {
			}

	}

	private class ScreenHandler extends Thread {

		@Override
		public void run() {
			while (true) {
				waitForLogin();
				System.out.println(show);
				System.out.println("Showing login");
				mainContainer.removeAll();
				mainContainer.add(login);
				mainContainer.updateUI();
				waitForMain();
				System.out.println("showing main");
				mainContainer.removeAll();
				mainContainer.add(new MainScreen(GUI.this));
				mainContainer.updateUI();
			}
		}
	}

}
