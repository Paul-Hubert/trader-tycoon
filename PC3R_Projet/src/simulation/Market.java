package simulation;

import data.Resource;

public class Market {
	
	public static boolean canAutoSell(Resource res) {
		return autoSellPrice(res) != 0;
	}
	
	public static long autoSellPrice(Resource res) {
		
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
		
	}
	
}
