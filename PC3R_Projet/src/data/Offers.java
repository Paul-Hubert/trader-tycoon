package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.ConnectionProvider;

public class Offers {
	public final long user_id;
	
	private final Map<Resource, ArrayList<Offer>> offers = new HashMap<>();
	
	public Offers(long id) throws Exception {
		this.user_id = id;
	}
	
	public static Offers create(long id) throws Exception {
		var offers = new Offers(id);
		
		Connection con = ConnectionProvider.getCon();
		
		PreparedStatement ps = con.prepareStatement("select * from offers where user_id=?;");
		ps.setLong(1, id);
		
		ResultSet rs=ps.executeQuery();
		
		while(rs.next()) {
			var offer = Offer.create(rs);
			offers.add(offer);
		}
		
		return offers;
		
	}
	
	public void add(Offer offer) {
		var al = offers.get(offer.resource);
		al.add(offer);
		if(al.size() == 0) {
			offers.put(offer.resource, al);
		}
	}
	
	public ArrayList<Offer> get(Resource resource) {
		var al = offers.get(resource);
		if(al == null) {
			return new ArrayList<>();
		}
		return al;
	}

	public void update() throws Exception {
		
		for(var res : offers.keySet()) {
			var al = offers.get(res);
			
			for(var offer : al) {
				offer.update();
			}
		}
		
	}
}
