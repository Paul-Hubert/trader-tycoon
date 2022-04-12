package connection;

import java.sql.*;
import java.util.InputMismatchException;

import javax.servlet.http.HttpServletRequest;

import database.ConnectionProvider;

public class Login {

	public static void validate(HttpServletRequest request) throws SQLException {
		
		String user = request.getParameter("user");
		if(user == null || user == "") throw new InputMismatchException("Username not set");
		
		String pass = request.getParameter("pass");
		if(pass == null || pass == "") throw new InputMismatchException("Password not set");
		
		Connection con = ConnectionProvider.getCon();
		
		if(con == null) throw new SQLException("Could not create SQL connection");
		
		PreparedStatement ps=con.prepareStatement("select * from users where user=? and pass=?;");
		ps.setString(1, user);
		ps.setString(2, pass);
		
		ResultSet rs=ps.executeQuery();
		
		if(!rs.next()) throw new SQLException("No user with this username/password");
		
		System.out.println(rs.getInt("id"));
		
	}
}