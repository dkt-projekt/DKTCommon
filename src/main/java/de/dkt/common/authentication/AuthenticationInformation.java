package de.dkt.common.authentication;

import java.io.Serializable;

public class AuthenticationInformation implements Serializable {

	private static final long serialVersionUID = 7529999995622776147L;
	
	private String user;
	private String password;
	private String service;
	private String serviceName;
	private String type;
	
	public AuthenticationInformation() {
		super();
	}

	public AuthenticationInformation(String user, String password, String type, String service, String serviceName) {
		super();
		this.user = user;
		this.password = password;
		this.type = type;
		this.service = service;
		this.serviceName = serviceName;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
