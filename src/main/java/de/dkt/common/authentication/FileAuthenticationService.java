package de.dkt.common.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import de.dkt.common.filemanagement.FileFactory;
import eu.freme.common.exception.BadRequestException;

public class FileAuthenticationService extends AuthenticationService implements Serializable {

	private static final long serialVersionUID = 7529999095622776147L;
	
	Logger logger = Logger.getLogger(FileAuthenticationService.class);

	String service;
	String serviceName;
	boolean create;
	List<AuthenticationInformation> list;

	public FileAuthenticationService(String service,String serviceName, boolean create) {
		this.service = service;
		this.serviceName = serviceName;
		this.create = create;
		try{
			try{
				File f = FileFactory.generateFileInstance("authenticationFiles/"+service+"/"+serviceName+".auth");
		        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (f));
		        list = (List<AuthenticationInformation>) ois.readObject();
		        ois.close();
			}
			catch(Exception e){
				if(create){
					list = new LinkedList<AuthenticationInformation>();
				}
				else{
					logger.error("Authentication FILE not found!!!");
					throw new BadRequestException("Authentication FILE not found!!!");
				}
			}
		}
		catch(Exception e){
//			e.printStackTrace();
			logger.error("ERROR: FILE authentication service generation for ["+service+"] FAIED !!!!!");
		}
	}

	public boolean authenticateUser(String user, String password){
		for (AuthenticationInformation ai : list) {
			if(ai.isUser("all") || ai.isUser("public") || ai.checkUser(user, password)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean addCredentials(String user, String password, String type) {
		for (AuthenticationInformation ai : list) {
			System.out.println("INFO: "+ai.toString());
			if(ai.isUser(user)){
				return false;
			}
		}
		list.add(new AuthenticationInformation(user, password, type));
		return updateCredentialsFile();
	}
	
	@Override
	protected boolean removeCredentials(String user, String password, String type) {
		for (AuthenticationInformation ai : list) {
			if(ai.isUser("all") || ai.isUser("public") || ai.checkUser(user,password)){
				list.remove(ai);
				return updateCredentialsFile();
			}
		}
		return false;
	}
	
	private boolean updateCredentialsFile(){
		try{
//			System.out.println("UPDATE CREDENTIALS FILE: authenticationFiles/"+service + "/" + serviceName+".auth");
//			for (AuthenticationInformation ai : list) {
//				System.out.println(ai.toString());
//			}
			File f = null;
			if(create){
				f = FileFactory.generateOrCreateFileInstance("authenticationFiles/"+service + "/" + serviceName+".auth");
			}
			else{	
				f = FileFactory.generateFileInstance("authenticationFiles/"+service + "/" + serviceName+".auth");
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (f));
			oos.writeObject (list);
			oos.close();
			return true;
		}
		catch(Exception e){
//			e.printStackTrace();
			logger.error("ERROR storing credentials file !!!");
			return false;
		}
	}
}
