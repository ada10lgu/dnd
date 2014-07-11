package gui.parts.info;

import gui.parts.login.LoginPanel;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JLayeredPane;

import model.DNDModel;
import model.settings.Settings;

@SuppressWarnings("serial")
public class StartUpScreen extends JLayeredPane {

	private final int LOGIN_WIDTH = 300;
	private final int LOGIN_HEIGHT = 100;

	private TextScreen text;
	private Rectangle r;

	public StartUpScreen(Rectangle r) {
		setBounds(r);
		this.r = r;
		text = new TextScreen();
		text.setBounds(getBounds());
		add(text, DEFAULT_LAYER);
		text.println("Welcome!");
		new UpStarter().start();
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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

					LoginPanel lp = new LoginPanel(model,text);

					lp.setBounds(r.width / 2 - LOGIN_WIDTH / 2, r.height / 3
							- LOGIN_HEIGHT / 2, LOGIN_WIDTH, LOGIN_HEIGHT);
					add(lp, POPUP_LAYER);

				} else {
					exit(text);
				}

			} else {
				exit(text);
			}

		}
	}
}
