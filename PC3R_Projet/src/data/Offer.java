package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.ConnectionProvider;

public class Offer {
	public final long user_id;
	public final Resource resource;
	public boolean buy;
	public long price;
	public long quantity;
	
	public Offer(long user_id, Resource resource, boolean buy, long price, long quantity) {
		this.user_id = user_id;
		this.resource = resource;
		this.buy = buy;
		this.price = price;
		this.quantity = quantity;
	}
	
	public static Offer create(ResultSet rs) throws Exception {
		return new Offer(rs.getLong("user_id"), Resource.get(rs.getInt("resource")), rs.getBoolean("buy"), rs.getLong("price"), rs.getLong("quantity"));
	}
	
	public void insert() throws Exception {
		
		Connection con = ConnectionProvider.getCon();
		PreparedStatement ps = con.prepareStatement("insert into offers (user_id, resource, buy, price, quantity) values (?,?,?,?,?);");
		ps.setLong(1, user_id);
		ps.setInt(2, resource.getID());
		ps.setBoolean(3, buy);
		ps.setLong(4, price);
		ps.setLong(5, quantity);
		
		ps.executeUpdate();
	}
	
	public void update() throws Exception {
		Connection con = ConnectionProvider.getCon();
		PreparedStatement ps = con.prepareStatement("update production set buy=?, price=?, quantity=? where user_id=? and resource=?;");
		ps.setBoolean(1, buy);
		ps.setLong(2, price);
		ps.setLong(3, quantity);
		ps.setLong(4, user_id);
		ps.setInt(5, resource.getID());

		ps.executeUpdate();
	}
	
	public void delete() throws Exception {
		Connection con = ConnectionProvider.getCon();
		PreparedStatement ps = con.prepareStatement("delete from offers where user_id=? and resource=?;");
		ps.setLong(1, user_id);
		ps.setInt(2, resource.getID());

		ps.executeUpdate();
	}
}
