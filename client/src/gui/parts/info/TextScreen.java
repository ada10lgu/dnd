package gui.parts.info;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import static model.general.HelpMethods.*;
@SuppressWarnings("serial")
public class TextScreen extends JPanel {

	private JTextArea box; 
	
	public TextScreen() {
		box = new JTextArea();
		box.setEditable(false);
		setLayout(new BorderLayout());
		
		add(box,BorderLayout.CENTER);
	}
	
	public void print(String text) {
		box.setText(box.getText()+text);
	}
	
	public void println() {
		print(nl());
	}
	
	public void println(String text) {
		print(text+nl());
	}
}
