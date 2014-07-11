package gui.parts.main;

import gui.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainScreen extends JPanel implements ActionListener {

	private GUI gui;
	
	public MainScreen(GUI gui) {
		this.gui = gui;
		JButton b = new JButton("Logout");
		setLayout(new BorderLayout());
		b.addActionListener(this);
		add(b,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.showLogin(true);
	}
}
