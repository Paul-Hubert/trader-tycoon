package data;

import java.sql.ResultSet;

public class ResourceProduction {
	public final Resource resource;
	public final long count;
	public final long production;
	public final long research;
	
	ResourceProduction(Resource resource) {
		this.resource = resource;
		count = 0;
		production = 0;
		research = 0;
	}
	
	ResourceProduction(ResultSet rs) throws Exception {
		resource = Resource.get(rs.getInt("resource"));
		count = rs.getLong("count");
		production = rs.getLong("production");
		research = rs.getLong("research");
	}
}
