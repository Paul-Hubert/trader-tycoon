package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.ConnectionProvider;

public class ResourceProduction {
	public final Resource resource;
	public long count;
	public long production;
	public long research;
	public final boolean empty;
	
	ResourceProduction(Resource resource) {
		this.resource = resource;
		count = 0;
		production = 0;
		research = 0;
		empty = true;
	}
	
	ResourceProduction(ResultSet rs) throws Exception {
		resource = Resource.get(rs.getInt("resource"));
		count = rs.getLong("count");
		production = rs.getLong("production");
		research = rs.getLong("research");
		empty = false;
	}
	
	public void addProduction(User user) throws Exception {
		Connection con = ConnectionProvider.getCon();
		PreparedStatement ps;
		production++;
		if (empty) {
			ps = con.prepareStatement("insert into production * values (?,?,?,?,?);");
			ps.setInt(1, user.id);
			ps.setInt(2, resource.getID());
			ps.setLong(3, count);
			ps.setLong(4, production);
			ps.setLong(5, research);
		}
		else {
			ps = con.prepareStatement("update production set production=? where user_id=? and resource=?;");
			ps.setLong(1, production);
			ps.setInt(2, user.id);
			ps.setInt(3, resource.getID());
		}

		ps.executeUpdate();
	}
}
