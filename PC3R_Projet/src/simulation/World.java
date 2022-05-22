package simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import data.Crafting;
import data.Resource;
import data.User;
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
			
			var rp = user.getProduction().get(resource);
			
			var recipe = Crafting.recipes.get(resource);
			
			if(recipe == null) {
				
				rp.count += rp.production;
				
			} else {
				
				long max = rp.production;
				
				for(var ing : recipe.getIngredients()) {
					
					var irp = user.getProduction().get(ing.getResource());
					
					max = Math.min(max, irp.count / ing.getCount());
					
				}
				
				if(max < 0) {
					continue;
				}
				
				rp.count += max;
				
				for(var ing : recipe.getIngredients()) {
					
					var irp = user.getProduction().get(ing.getResource());
					
					irp.count -= max * ing.getCount();
					
				}
				
			}
			
		}
		
		user.update();
		user.getProduction().update();
		
	}

	public void step(ScheduledExecutorService scheduler) throws Exception {
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement userps = con.prepareStatement("select id from users;");
		
		ResultSet users = userps.executeQuery();
		
		ArrayList<ScheduledFuture<?>> futures = new ArrayList<>();
		
		while(users.next()) {
			
			final var user_id = users.getLong("id");
			
			futures.add(scheduler.schedule(() -> {

				try {
					
					step(user_id);
				
				} catch (Exception e) {
					e.printStackTrace();
				}

			}, 0, TimeUnit.SECONDS));
			
		}
		
		for(var future : futures) {
			future.get();
		}
		
	}
	
}
