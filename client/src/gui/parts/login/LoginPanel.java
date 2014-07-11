package gui.parts.login;

import gui.parts.info.TextScreen;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DNDModel;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {

	private JTextField user;
	private JPasswordField pass;
	private JButton button;
	private DNDModel model;
	private TextScreen text;

	public LoginPanel(DNDModel model, TextScreen text) {
		this.model = model;
		this.text = text;
		setOpaque(true);
		setLayout(new GridLayout(3, 1));

		setBorder(BorderFactory.createTitledBorder("Login"));
		setLayout(new GridLayout(3, 1));

		user = new JTextField(10);
		user.addActionListener(this);
		pass = new JPasswordField(10);
		pass.addActionListener(this);
		button = new JButton("Login");
		button.addActionListener(this);

		add(user);
		add(pass);
		add(button);
	}

	private void setEnableElements(boolean enable) {
		user.setEnabled(enable);
		pass.setEnabled(enable);
		button.setEnabled(enable);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		setEnableElements(false);
		new Worker().start();
	}

	private class Worker extends Thread {

		@Override
		public void run() {
			String username = user.getText();
			String password = new String(pass.getPassword());

			text.println("Trying to login.");

			if (model.login(username, password)) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
				}
				text.println("Login successfull!");

				String name = model.getUser().getName();
				text.println("Welcome " + name + "!");

			} else {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e1) {
				}
				text.println("Login failed!");
			}
			setEnableElements(true);
		}
	}

}
