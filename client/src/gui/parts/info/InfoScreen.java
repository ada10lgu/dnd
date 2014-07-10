package gui.parts.info;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InfoScreen extends JPanel {

	public InfoScreen(String title, String message) {
		JPanel mid = new JPanel();

		mid.setBorder(BorderFactory.createTitledBorder(title));
		mid.setLayout(new GridLayout(3, 1));
		
		mid.setPreferredSize(new Dimension(300, 200));
		mid.add(new JTextField(10));
		mid.add(new JPasswordField(10));
		mid.add(new JButton("Login"));
		add(mid);
	}

}
