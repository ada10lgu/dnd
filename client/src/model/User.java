package model;

import model.network.packages.data.StringPackage;
import model.network.packages.operator.DataPackage;
import model.network.packages.operator.UserRequest;

public class User {

	private DNDModel model;
	private int id;
	private String username;
	private String email;
	private String name;

	public User(DNDModel model, int id) {
		this.model = model;
		this.id = id;

		DataPackage dp = (DataPackage) model.sendAndWait(new UserRequest(id));
		StringPackage spUsername = (StringPackage) dp.getPackages()[0];
		username = spUsername.toString();
		StringPackage spName = (StringPackage) dp.getPackages()[1];
		name = spName.toString();
		StringPackage spEmail = (StringPackage) dp.getPackages()[2];
		email = spEmail.toString();

	}

	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUsername() {
		return username;
	}

}
