package data;

import java.text.DecimalFormat;

public class Money {

	private static DecimalFormat df = new DecimalFormat("#.##");
	
	public static String format(long money) {
		return df.format(money / 100.0);
		//return (Math.round(money) / 100.) + "$";
		//return String.format("%. 2f", money / 100.0) + "$";
	}
	
}
