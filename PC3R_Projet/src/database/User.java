package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class User {
	
	private final int id;
	private final String username;
	private final String password;
	
	private User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	private static User create(ResultSet rs) throws SQLException {
		
		return new User(rs.getInt("id"), rs.getString("user"), rs.getString("pass"));
		
	}
	
	private static User create(int id) throws Exception {
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement ps=con.prepareStatement("select * from users where id=?;");
		ps.setInt(1, id);
		
		ResultSet rs=ps.executeQuery();
		
		if(!rs.next()) throw new SQLException("No user with this id");
		
		return create(rs);
		
	}
	
	private static void connect(HttpSession session, int id) {
		
		session.setAttribute("id", id);
		
	}
	
	public static void disconnect(HttpSession session) {
		session.removeAttribute("id");
	}
	
	public static boolean isConnected(HttpSession session) {
		
		var id = session.getAttribute("id");
		return id != null;
		
	}
	
	public static int getConnectedID(HttpSession session) throws Exception {
		if(!isConnected(session)) {
			throw new Exception("Not connected, cannot retrieve ID");
		}
		return (int) session.getAttribute("id");
	}
	
	public static User getConnected(HttpSession session) throws Exception {
		return create(getConnectedID(session));
	}
	
	public static void login(HttpServletRequest request) throws Exception {
		
		String user = request.getParameter("user");
		if(user == null || user == "") throw new InputMismatchException("Username not set");
		
		String pass = request.getParameter("pass");
		if(pass == null || pass == "") throw new InputMismatchException("Password not set");
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement ps=con.prepareStatement("select * from users where user=? and pass=?;");
		ps.setString(1, user);
		ps.setString(2, pass);
		
		ResultSet rs=ps.executeQuery();
		
		if(!rs.next()) throw new SQLException("No user with this username/password");
		
		int id = rs.getInt("id");
		
		connect(request.getSession(), id);
		
	}
	
	public static void signup(HttpServletRequest request) throws Exception {
		
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
                int id = generatedKeys.getInt(1);
        		connect(request.getSession(), id);
            }
            else {
                throw new SQLException("User creation failed, no ID obtained.");
            }
        }
		
	}
	
	public static void logout(HttpServletRequest request) {
		disconnect(request.getSession());
	}
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
