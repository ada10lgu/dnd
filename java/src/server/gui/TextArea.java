package server.gui;

import general.write.Writeable;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextArea extends JTextArea implements Writeable {

	@Override
	public void write(String who, String what) {
		String message = getText() + "[" + who + "] " + what + "\n";
		setText(message);

	}

}
