package bean;
import java.sql.*;
public class LoginDao {

	public static void validate(LoginBean bean) throws SQLException {
		
		Connection con = ConnectionProvider.getCon();
		
		if(con == null) throw new SQLException("Could not create SQL connection");
		
		PreparedStatement ps=con.prepareStatement("select * from user432 where email=? and pass=?");
		ps.setString(1, bean.getUser());
		ps.setString(2, bean.getPass());
		
		ResultSet rs=ps.executeQuery();
		rs.next();
		
	}
}