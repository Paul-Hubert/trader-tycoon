package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import database.ConnectionProvider;

public class Production {
	
	private final Map<Resource, ResourceProduction> resources = new HashMap<>();
	
	Production(int id) throws Exception {
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement ps = con.prepareStatement("select * from production where user_id=?;");
		ps.setInt(1, id);
		
		ResultSet rs=ps.executeQuery();
		
		while(rs.next()) {
			var prod = new ResourceProduction(rs);
			resources.put(prod.resource, prod);
		}
		
	}
	
	public ResourceProduction get(Resource resource) {
		var rp = resources.get(resource);
		if(rp == null) {
			return new ResourceProduction(resource);
		}
		return rp;
	}
	
}
