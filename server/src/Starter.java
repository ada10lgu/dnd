import java.io.IOException;
import java.sql.SQLException;

import model.DNDServerModel;
import model.settings.Settings;


public class Starter {
	public static void main(String[] args) throws IOException, SQLException {
		
		Settings settings = new Settings();
		
		new DNDServerModel(settings);
		
	}
}
