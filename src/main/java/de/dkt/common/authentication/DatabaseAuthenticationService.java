package de.dkt.common.authentication;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dkt.common.authentication.ddbb.AuthenticationInformationJDBCTemplate;

/**
 * @author Julian Moreno Schneider julian.moreno_schneider@dfki.de
 */
@Component
public class DatabaseAuthenticationService extends AuthenticationService implements Serializable {

	private static final long serialVersionUID = 7529999095622776147L;
	
	Logger logger = Logger.getLogger(DatabaseAuthenticationService.class);

	private String service;
	private String serviceName;

	@Autowired
	AuthenticationInformationJDBCTemplate authenticationDAO = new AuthenticationInformationJDBCTemplate();

	public DatabaseAuthenticationService() {
	}

	public DatabaseAuthenticationService(String service,String serviceName) {
		this.service = service;
		this.serviceName = serviceName;
	}

	@Override
	public boolean authenticateUser(String user, String password){
		if(authenticationDAO.authenticateUser(service,serviceName,user,password)){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	protected boolean addCredentials(String user, String password, String type) {
		//TODO
		return false;
	}
	
	@Override
	protected boolean removeCredentials(String user, String password, String type) {
		//TODO
		return false;
	}
	
	private boolean updateCredentialsFile(){
		try{
			//TODO
			return false;
		}
		catch(Exception e){
//			e.printStackTrace();
			logger.error("ERROR storing credentials file !!!");
			return false;
		}
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

	public static void main(String[] args) {
		//TODO
	}

}
