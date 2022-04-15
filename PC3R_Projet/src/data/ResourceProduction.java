package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.ConnectionProvider;

public class ResourceProduction {
	public final Resource resource;
	public long count;
	public long production;
	public long research_cost;
	public long research;
	public final boolean empty;
	
	ResourceProduction(Resource resource) {
		this.resource = resource;
		count = 0;
		production = 0;
		research_cost = 0;
		research = 0;
		empty = true;
	}
	
	ResourceProduction(ResultSet rs) throws Exception {
		resource = Resource.get(rs.getInt("resource"));
		count = rs.getLong("count");
		production = rs.getLong("production");
		research_cost = rs.getLong("research_cost");
		research = rs.getLong("research");
		empty = false;
	}
	
	public void addProduction(User user) throws Exception {
		production++;
		update(user);
	}
	
	public void update(User user) throws Exception {
		Connection con = ConnectionProvider.getCon();
		PreparedStatement ps;
		if (empty) {
			ps = con.prepareStatement("insert into production (user_id, resource, count, production, research_cost, research) values (?,?,?,?,?,?);");
			ps.setLong(1, user.id);
			ps.setInt(2, resource.getID());
			ps.setLong(3, count);
			ps.setLong(4, production);
			ps.setLong(5, research_cost);
			ps.setLong(6, research);
		}
		else {
			ps = con.prepareStatement("update production set count=?, production=?, research_cost=?, research=? where user_id=? and resource=?;");
			ps.setLong(1, count);
			ps.setLong(2, production);
			ps.setLong(3, research_cost);
			ps.setLong(4, research);
			ps.setLong(5, user.id);
			ps.setInt(6, resource.getID());
		}

		ps.executeUpdate();
	}
	
	public int getProductionCost() {
		return 10000;
	}
	
}
