package de.dkt.common.authentication;

import eu.freme.common.exception.BadRequestException;

public class UserAuthentication {

	public static AuthenticationService getAuthenticationService(String serviceType, String service, String serviceName, boolean create){
		AuthenticationService as;
		if(serviceType.equalsIgnoreCase("file")){
			as = new FileAuthenticationService(service,serviceName,create);
		}
//			else if(serviceType.equalsIgnoreCase("database")){
//				as = new DatabaseAuthenticationService(service,serviceName);
//			}
		else{
			throw new BadRequestException("There is no authentication supported for service "+service);
		}
		return as;
	}
	
	public static boolean authenticateUser(String user, String password, String serviceType, String service, String serviceName, boolean create){
		AuthenticationService as = getAuthenticationService(serviceType, service, serviceName, create);
		return as.authenticateUser(user,password);
	}

	public static boolean addCredentials(String user, String password, String type, String serviceType, String service, String serviceName, boolean create){
		AuthenticationService as = getAuthenticationService(serviceType, service, serviceName,create);
		return as.addCredentials(user,password,type);
	}

	public static boolean checkAndAddCredentials(String adminUser, String adminPassword, String user, String password, String type, String serviceType, String service, String serviceName){
		AuthenticationService as = getAuthenticationService(serviceType, service, serviceName,false);
		return as.checkAndAddCredentials(adminUser,adminPassword,user,password,type);
	}

}
