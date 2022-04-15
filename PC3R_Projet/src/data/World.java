package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import database.ConnectionProvider;

public class World {

	public final ArrayList<User> users = new ArrayList<>();
	
	public static World create() throws Exception {
		
		World world = new World();
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement ps=con.prepareStatement("select * from users;");
		
		ResultSet rs=ps.executeQuery();
		
		while(rs.next()) {

			world.users.add(User.create(rs));
		}
		
		return world;
	}

	public void step(ScheduledExecutorService scheduler) throws Exception {
		
		for(Resource res : Resource.values()) {
			
			Connection con = ConnectionProvider.getCon();
			
			var recipe = Crafting.recipes.get(res);
			
			if(recipe == null) {
				

				scheduler.schedule(() -> {
					
					try {
						PreparedStatement ps = con.prepareStatement("update production set count=(count+production) where resource=?;");
						ps.setLong(1, res.getID());
						
						ps.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}, 0, TimeUnit.SECONDS);
				
				
			} else {
				/*
				query = "UPDATE production p ";
				
				for(var ing : recipe.getIngredients()) {
					var id = ing.getResource().getID();
					query += "JOIN production i" + id;
				}

				boolean first = true;
				for(var ing : recipe.getIngredients()) {
					var id = ing.getResource().getID();
					query += (first ? " ON " : " AND ") + "p.user_id=i"+id+".user_id AND i"+id+".resource="+id+" AND i"+id+".count>"+ing.getCount();
					first = false;
				}
				query += "(SELECT MIN(count/))";
				query += " SET p.count=p.count+p.production";

				for(var ing : recipe.getIngredients()) {
					var id = ing.getResource().getID();
					query += ", i"+id+".count=GREATEST(0, i"+id+".count-p.production*"+ing.getCount()+")";
				}
				query += ";";
				
				//System.out.println(query);
				//String query = "UPDATE production p JOIN production i ON p.user_id=i.user_id AND p.resource=1 AND i.resource=0 AND i.count>p.production SET p.count=p.count+p.production, i.count=i.count-p.production;";
				*/
			}
			
		}
	
		
	}
	
}
