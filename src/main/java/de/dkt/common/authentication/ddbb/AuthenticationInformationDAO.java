package de.dkt.common.authentication.ddbb;

import javax.sql.DataSource;

public interface AuthenticationInformationDAO {

	public void setDataSource(DataSource ds);

	public boolean authenticateUser(String service, String serviceName, String user, String password);
	
}
