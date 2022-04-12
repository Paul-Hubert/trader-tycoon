package connection;

import java.sql.*;
import java.util.InputMismatchException;

import javax.servlet.http.HttpServletRequest;

import database.ConnectionProvider;

public class Signup {

	public static void validate(HttpServletRequest request) throws SQLException {
		
		String user = request.getParameter("user");
		if(user == null || user == "") throw new InputMismatchException("Username not set");
		
		String pass = request.getParameter("pass");
		if(pass == null || pass == "") throw new InputMismatchException("Password not set");
		
		Connection con = ConnectionProvider.getCon();
		
		if(con == null) throw new SQLException("Could not create SQL connection");
		
		PreparedStatement ps = con.prepareStatement("insert into users (user, pass) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user);
		ps.setString(2, pass);
		
		int rows = ps.executeUpdate();
		
		if(rows == 0) throw new SQLException("User creation failed, no changes.");
		
		try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                System.out.println(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
		
	}
}