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
	
	public void step(long user_id) throws Exception {
		
		User user = User.create(user_id);
		
		for(Resource resource : Resource.values()) {
			
			var rp = user.production.get(resource);
			
			var recipe = Crafting.recipes.get(resource);
			
			if(recipe == null) {
				
				rp.count += rp.production;
				
			} else {
				
				long max = 0;
				
				for(var ing : recipe.getIngredients()) {
					
					var irp = user.production.get(ing.getResource());
					
					max = Math.max(max, irp.count / ing.getCount());
					
				}
				
				max = Math.min(max, rp.production);
				
				if(max == 0) {
					continue;
				}
				
				rp.count += max;
				
				for(var ing : recipe.getIngredients()) {
					
					var irp = user.production.get(ing.getResource());
					
					irp.count -= max * ing.getCount();
					
				}
				
			}
			
		}
		
		user.production.update(user);
		
	}

	public void step(ScheduledExecutorService scheduler) throws Exception {
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement userps = con.prepareStatement("select id from users;");
		
		ResultSet users = userps.executeQuery();
		
		while(users.next()) {
			
			var user_id = users.getLong("id");
			
			scheduler.schedule(() -> {

				try {
					
					step(user_id);
				
				} catch (Exception e) {
					e.printStackTrace();
				}

			}, 0, TimeUnit.SECONDS);
			
		}
	
		
	}
	
}
