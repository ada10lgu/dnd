package gui;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import gui.parts.info.StartUpScreen;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public static void main(String[] args) {

		new GUI();

	}

	public GUI() {
		Rectangle r = new Rectangle(0, 0, 700, 500);
		setSize(700,500);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		StartUpScreen sus = new StartUpScreen(r);
		add(sus,BorderLayout.CENTER);
		setVisible(true);

	}

}
