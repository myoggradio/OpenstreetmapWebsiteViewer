package osm.viewer;

public class SatzPlz implements Comparable<SatzPlz>
{
	private String plz = "";
	private String ort = "";
	private double lon = 0.0;
	private double lat = 0.0;
	public String getPlz()
	{
		return plz;
	}
	public String getOrt()
	{
		return ort;
	}
	public double getLon()
	{
		return lon;
	}
	public double getLat()
	{
		return lat;
	}
	public void setPlz(String s)
	{
		plz = s;
	}
	public void setOrt(String s)
	{
		ort = s;
	}
	public void setLon(double d)
	{
		lon = d;
	}
	public void setLat(double d)
	{
		lat = d;
	}
	@Override
	public int compareTo(SatzPlz s) 
	{
		double delta = getLat() - s.getLat();
		if (delta > 0) return 1;
		if (delta < 0) return -1;
		return 0;
	}
}
