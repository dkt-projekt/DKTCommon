package de.dkt.common.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.freme.common.exception.BadRequestException;

@Service
public class UserAuthentication {

	@Autowired
	DatabaseAuthenticationService das;
	
	public UserAuthentication() {
	}

	public AuthenticationService getAuthenticationService(String serviceType, String service, String serviceName, boolean create){
		AuthenticationService as;
		if(serviceType.equalsIgnoreCase("file")){
			as = new FileAuthenticationService(service,serviceName,create);
		}
		else if(serviceType.equalsIgnoreCase("database")){
			das.setService(service);
			das.setServiceName(serviceName);
			return das;
		}
		else{
			throw new BadRequestException("There is no authentication supported for service "+service);
		}
		return as;
	}
	
	public boolean authenticateUser(String user, String password, String serviceType, String service, String serviceName, boolean create){
		AuthenticationService as = getAuthenticationService(serviceType, service, serviceName, create);
		return as.authenticateUser(user,password);
	}

	public boolean addCredentials(String user, String password, String type, String serviceType, String service, String serviceName, boolean create){
		AuthenticationService as = getAuthenticationService(serviceType, service, serviceName,create);
		return as.addCredentials(user,password,type);
	}

	public boolean checkAndAddCredentials(String adminUser, String adminPassword, String user, String password, String type, String serviceType, String service, String serviceName){
		AuthenticationService as = getAuthenticationService(serviceType, service, serviceName,false);
		return as.checkAndAddCredentials(adminUser,adminPassword,user,password,type);
	}

}
