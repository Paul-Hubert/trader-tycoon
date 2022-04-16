package simulation;

import data.Resource;

public class Market {
	
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
