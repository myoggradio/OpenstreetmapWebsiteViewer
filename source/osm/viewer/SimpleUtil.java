package osm.viewer;
import java.text.NumberFormat;

import core.Util;
public class SimpleUtil implements Util
{
	@Override
	public String getRound2(double d) 
	{
		double x = 100 * d;
		long l = Math.round(x);
		x = (double) l;
		x = x / 100;
		String erg = "" + x;
		return erg;
	}	
	@Override
	public String getRound3(double d) 
	{
		double x = 1000 * d;
		long l = Math.round(x);
		x = (double) l;
		x = x / 1000;
		String erg = "" + x;
		return erg;
	}	
	@Override
	public String getRound4(double d) 
	{
		double x = 10000 * d;
		long l = Math.round(x);
		x = (double) l;
		x = x / 10000;
		String erg = "" + x;
		return erg;
	}
	@Override
	public String formatDouble(double d, int stellen) 
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(stellen); 
		nf.setMinimumFractionDigits(1);
		String temp = nf.format(d);
		String erg = "";
		for (int i=0;i<temp.length();i++)
		{
			String ch = temp.substring(i,i+1);
			if (ch.equals(",")) ch = ".";
			erg += ch;
		}
		return erg;
	}
}