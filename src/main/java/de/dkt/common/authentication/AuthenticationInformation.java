package de.dkt.common.authentication;

import java.io.Serializable;

public class AuthenticationInformation implements Serializable {

	private static final long serialVersionUID = 7529999995622776147L;
	
	private String user;
	private String password;
	private String type;
	
	public AuthenticationInformation(String user, String password, String type) {
		super();
		this.user = user;
		this.password = password;
		this.type = type;
	}
	
	public boolean checkUser(String u, String p){
		return (user.equalsIgnoreCase(u) && password.equals(p));
	}

	public boolean isUser(String u) {
		return (user.equalsIgnoreCase(u));
	}
	
	public String toString(){
		return user+" "+password+" "+type;
	}
	
}
