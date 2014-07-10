package gui;

import gui.parts.info.TextScreen;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import model.DNDModel;
import model.settings.Settings;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public static void main(String[] args) {

		new GUI();

	}

	public GUI() {
		setSize(700, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		TextScreen text = new TextScreen();
		add(text);
		setVisible(true);

		Settings s;
		DNDModel model;

		if ((s = loadSettings(text)) != null) {

			if ((model = loadModel(text, s)) != null) {
				text.println("Proceding to login screen.");
				
			} else {
				exit(text);
			}
			
		} else {
			exit(text);
		}


	}

	private void exit(TextScreen text) {
		text.println("Closing program in 10 seconds");
		for (int i = 10; i > 0; i--) {
			text.println(""+i);
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
			Settings s = new Settings();
			text.println("done!");
			return s;
		} catch (IOException e) {
			text.println("failed!");
		}
		return null;
	}
}
