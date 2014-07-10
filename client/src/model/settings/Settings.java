package model.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {

	private static final String FOLDER = "files";
	private static final String CONFIG_FILE = FOLDER + "/config.properties";
	private Properties prop;

	public Settings() throws IOException {
		prop = new Properties();

		File folder = new File(FOLDER);
		if (!folder.exists())
			folder.mkdir();
		File file = new File(CONFIG_FILE);
		if (!file.exists())
			createStandardSettingsFile(file);

		loadSettings(file);
		
		
	}

	private synchronized void createStandardSettingsFile(File file) throws IOException {

		OutputStream output = new FileOutputStream(file);

		prop.setProperty("server_address",
				"127.0.0.1");
		prop.setProperty("server_port", "12345");

		prop.store(output, null);
	}
	
	private synchronized void loadSettings(File file) throws IOException {
		InputStream io = new FileInputStream(file);
		prop.load(io);
	}
	
	public synchronized String getSetting(String setting) {
		String data = prop.getProperty(setting);
		if (data == null)
			data = "";
		return data;
	}
}
