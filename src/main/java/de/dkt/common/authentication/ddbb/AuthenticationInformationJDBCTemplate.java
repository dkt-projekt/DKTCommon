package de.dkt.common.authentication.ddbb;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import de.dkt.common.authentication.AuthenticationInformation;

@Repository
public class AuthenticationInformationJDBCTemplate implements AuthenticationInformationDAO{

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean authenticateUser(String service, String serviceName, String user, String password) {
		try{
			String SQL = "select Authentication_Users.user,password,service,serviceName,type from Authentication_Users,Authentication_Service WHERE Authentication_Users.user = ? AND Authentication_Users.password=PASSWORD( ? ) "
					+ "AND Authentication_Users.user=Authentication_Service.user AND Authentication_Service.service = ? AND Authentication_Service.serviceName = ? ";
			AuthenticationInformation fi = jdbcTemplateObject.queryForObject(SQL, new Object[]{user,password,service,serviceName}, new AuthenticationInformationMapper());
	//		String SQL = "select * from Authentication_Users,Authentication_Service WHERE Authentication_Users.user = ? AND Authentication_Users.password=PASSWORD( ? ) ";
	//		AuthenticationInformation fi = jdbcTemplateObject.queryForObject(SQL, new Object[]{user,password}, new AuthenticationInformationMapper());
			if(fi!=null){
				return true;
			}
			return false;
		}
		catch(Exception e){
			return false;
		}
	}

}
