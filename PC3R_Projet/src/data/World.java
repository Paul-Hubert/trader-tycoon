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
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement userps = con.prepareStatement("select id from users;");
		
		ResultSet users = userps.executeQuery();

		while(users.next()) {
			
			var user_id = users.getLong("id");
			
			scheduler.schedule(() -> {

				try {
					
				
					PreparedStatement prodps = con.prepareStatement("select * from production where user_id=? order by resource asc;");
					prodps.setLong(1, user_id);
					
					ResultSet prods = prodps.executeQuery();
					
					while(prods.next()) {
						
						var res = Resource.get(prods.getInt("resource"));
						
						var recipe = Crafting.recipes.get(res);
						
						if(recipe == null) {
							
							
							
						}
						
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}

			}, 0, TimeUnit.SECONDS);
			
		}
	
		
	}
	
}
