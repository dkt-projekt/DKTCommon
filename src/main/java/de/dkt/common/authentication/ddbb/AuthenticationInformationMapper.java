package de.dkt.common.authentication.ddbb;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import de.dkt.common.authentication.AuthenticationInformation;

public class AuthenticationInformationMapper implements RowMapper<AuthenticationInformation> {

	public AuthenticationInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
		      AuthenticationInformation fi = new AuthenticationInformation();
		      fi.setUser(rs.getString("user"));
		      fi.setPassword(rs.getString("password"));
		      fi.setType(rs.getString("type"));
		      fi.setService(rs.getString("service"));
		      fi.setServiceName(rs.getString("serviceName"));
		      return fi;
		   }
	   
}
