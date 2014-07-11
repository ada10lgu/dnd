package gui.parts.login;

import gui.GUI;
import gui.parts.info.TextScreen;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.DNDModel;
import model.settings.Settings;

@SuppressWarnings("serial")
public class StartUpScreen extends JPanel {

	private final int LOGIN_WIDTH = 300;
	private final int LOGIN_HEIGHT = 100;

	private JLayeredPane mainContainer;
	private TextScreen text;
	private LoginPanel lp;
	private GUI gui;
	
	public StartUpScreen(GUI gui) {
		this.gui = gui;
		setLayout(new BorderLayout());

		mainContainer = new JLayeredPane();

		System.out.println(getBounds());
		text = new TextScreen();
		text.setBounds(10, 10, 200, 200);
		mainContainer.add(text, JLayeredPane.DEFAULT_LAYER);
		mainContainer.setBounds(0, 0, 500, 300);
		add(mainContainer);
		new UpStarter().start();
	}

	@Override
	public void repaint() {
		if (mainContainer != null) {
			mainContainer.setBounds(getBounds());
			text.setBounds(getBounds());
		}
		if (lp != null) {
			Rectangle r = getBounds();
			r.x = r.width / 2 - LOGIN_WIDTH / 2;
			r.y = r.height / 3 - LOGIN_HEIGHT / 2;
			r.width = LOGIN_WIDTH;
			r.height = LOGIN_HEIGHT;
			lp.setBounds(r);
		}
	}

	private void exit(TextScreen text) {
		text.println("Closing program in 10 seconds");
		for (int i = 10; i > 0; i--) {
			text.println("" + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		text.println("Bye bye!");
		System.exit(0);
	}

	private DNDModel loadModel(TextScreen text, Settings settings) {
		DNDModel model = null;
		text.print("Connecting to server...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			model = new DNDModel(settings);
			text.println("done!");
		} catch (UnknownHostException e) {
			text.println(e.getMessage());
		} catch (IOException e) {
			text.println(e.getMessage());
		} catch (Exception e) {
			text.println(e.getMessage());
		}

		return model;
	}

	private Settings loadSettings(TextScreen text) {
		text.print("Loading config file...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			Settings s = new Settings();
			text.println("done!");
			return s;
		} catch (IOException e) {
			text.println("failed!");
		}
		return null;
	}

	private class UpStarter extends Thread {

		@Override
		public void run() {
			Settings s;
			DNDModel model;

			if ((s = loadSettings(text)) != null) {
				if ((model = loadModel(text, s)) != null) {
					text.println("Proceding to login.");
					lp = new LoginPanel(gui, model, text);
					mainContainer.add(lp,JLayeredPane.PALETTE_LAYER);
					repaint();
					updateUI();
				} else {
					exit(text);
				}
			} else {
				exit(text);
			}
		}
	}
}
