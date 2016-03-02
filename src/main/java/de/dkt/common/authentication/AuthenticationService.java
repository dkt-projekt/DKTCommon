package de.dkt.common.authentication;

public abstract class AuthenticationService {

	public abstract boolean authenticateUser(String user, String password);

	protected abstract boolean addCredentials(String user, String password, String type);

	public boolean checkAndAddCredentials(String adminUser, String adminPassword, String user, String password, String type){
		if(authenticateUser(adminUser, adminPassword)){
			return addCredentials(user, password, type);
		}
		return false;
	}

	protected abstract boolean removeCredentials(String user, String password, String type);

	public boolean checkAndRemoveCredentials(String adminUser, String adminPassword, String user, String password, String type){
		if(authenticateUser(adminUser, adminPassword)){
			return removeCredentials(user, password, type);
		}
		return false;
	}
}
