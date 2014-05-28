package client.gui.user.login;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.model.UserModel;

@SuppressWarnings("serial")
public class LoginPane extends JPanel implements ActionListener {

	private JTextField username;
	private JPasswordField password;
	private UserModel m;

	public LoginPane(UserModel m) {
		this.m = m;
		username = new JTextField();
		password = new JPasswordField();
		JButton submit = new JButton("Login");
		submit.addActionListener(this);
		setLayout(new GridLayout(3, 1));
		add(username);
		add(password);
		add(submit);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		m.login(username.getText(), new String(password.getPassword()));
	}

}
