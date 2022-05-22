package simulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import data.Resource;
import database.ConnectionProvider;

public class Market {
	
	private static long[] prices = new long[Resource.values().length];
	
	public static boolean canAutoSell(Resource res) {
		switch(res) {
			case bread:
			case car:
			case phone:
				return true;
			default:
				return false;
		}
	}
	
	public static long price(Resource res) {
		
		return prices[res.getID()];
		
		/*
		switch(res) {
			case bread:
				return 1000;
			case car:
				return 1000000;
			case phone:
				return 100000;
			default:
				return 0;
		}
		*/
		
	}
	
	public static void step(ScheduledExecutorService scheduler) throws Exception {
		
		ArrayList<ScheduledFuture<?>> futures = new ArrayList<>();
		
		for(final var res : Resource.values()) {
			
			futures.add(scheduler.schedule(() -> {

				try {
					
					step(res);
				
				} catch (Exception e) {
					e.printStackTrace();
				}

			}, 0, TimeUnit.SECONDS));
			
		}
		
		for(var future : futures) {
			future.get();
		}
		
		
	}
	
	public static void step(Resource res) throws Exception {
		
		Connection con = ConnectionProvider.getCon();
		//a.user_id=ap.user_id and b.user_id=bp.user_id and a.resource=ap.resource and b.resource=bp.resource
		
		//PreparedStatement ps = con.prepareStatement("select * from offers as a inner join offers as b on, users as au, users as bu left join production ap on a.user_id=ap.user_id and a.resource=ap.resource where a.resource=b.resource and a.buy!=b.buy and a.buy=true and a.user_id!=b.user_id and a.user_id=au.id and b.user_id=bu.id;");
		//PreparedStatement ps = con.prepareStatement("select * from offers as a inner join offers as b on a.resource=b.resource and a.buy!=b.buy and a.buy=true and a.user_id!=b.user_id left join production as ap on a.user_id=ap.user_id and a.resource=ap.resource left join production as bp on b.user_id=bp.user_id and b.resource=bp.resource inner join users as au on a.user_id=au.id inner join users bu on b.user_id=bu.id;");
		//PreparedStatement ps = con.prepareStatement("select * from offers as a left join production as ap on a.user_id=ap.user_id and a.resource=ap.resource inner join users as au on a.user_id=au.id order by rand()");
		
		PreparedStatement sellerStatement = con.prepareStatement("select * from offers as o inner join production as p on o.user_id=p.user_id and o.resource=p.resource inner join users as u on o.user_id=u.id where o.resource=? and buy=false and p.count>0 order by o.price");
		PreparedStatement buyerStatement = con.prepareStatement("select * from offers as o left join production as p on o.user_id=p.user_id and o.resource=p.resource inner join users as u on o.user_id=u.id where o.resource=? and buy=true and u.money>0 order by rand()");
		
		sellerStatement.setInt(1, res.getID());
		buyerStatement.setInt(1, res.getID());
		
		ResultSet sellers = sellerStatement.executeQuery();
		ResultSet buyers = buyerStatement.executeQuery();
		
		Comparator<Long> inverted = new Comparator<Long>() {
            @Override
            public int compare(Long s1, Long s2) {
            	return -s1.compareTo(s2);
            }
        };
		
		if(Market.canAutoSell(res)) {
			
			while(sellers.next()) {
				
				long price = Market.price(res);
				long stock = sellers.getLong("p.count");
				
				long quantity = sellers.getLong("o.quantity");
				
				long amount = stock-quantity;
				
				if(amount<=0) continue;
				
				long cost = amount*price;
				
				long seller = sellers.getLong("u.id");
				
				System.out.println("autosell " + amount + " " + res);
				
				PreparedStatement sellerUpdate = con.prepareStatement("update users as u, production as p set u.money=u.money+?, p.count=p.count-? where p.resource=? and u.id=? and p.user_id=u.id");
				sellerUpdate.setLong(1, cost);
				sellerUpdate.setLong(2, amount);
				sellerUpdate.setInt(3, res.getID());
				sellerUpdate.setLong(4, seller);
				
				sellerUpdate.execute();
				
			}
			
		} else {
		
			if(!sellers.next()) return;
			
			while(buyers.next()) {
				
				long price = sellers.getLong("o.price");
				if(price > buyers.getLong("o.price")) continue;
				
				long money = buyers.getLong("u.money");
				long stock = sellers.getLong("p.count");
				if(stock<=0) {
					if(!sellers.next()) {
						return;
					}
				}
				
				long amount = money/price;
				if(amount>stock) amount = stock;

				System.out.println("buy " + amount + " " + res);
				
				if(amount<=0) continue;
				
				long cost = amount*price;
				
				long buyer = buyers.getLong("u.id");
				long seller = sellers.getLong("u.id");
				
				PreparedStatement buyerUpdate = con.prepareStatement("update users as u, production as p set u.money=u.money-?, p.count=p.count+? where p.resource=? and u.id=? and p.user_id=u.id");
				buyerUpdate.setLong(1, cost);
				buyerUpdate.setLong(2, amount);
				buyerUpdate.setInt(3, res.getID());
				buyerUpdate.setLong(4, buyer);
				
				buyerUpdate.execute();
				
				PreparedStatement sellerUpdate = con.prepareStatement("update users as u, production as p set u.money=u.money+?, p.count=p.count-? where p.resource=? and u.id=? and p.user_id=u.id");
				sellerUpdate.setLong(1, cost);
				sellerUpdate.setLong(2, amount);
				sellerUpdate.setInt(3, res.getID());
				sellerUpdate.setLong(4, seller);
				
				sellerUpdate.execute();
				
			}
			
		}
		
		
	}

	public static void updatePrice() {
		
	}
	
}
