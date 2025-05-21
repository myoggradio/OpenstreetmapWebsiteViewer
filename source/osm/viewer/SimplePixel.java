package osm.viewer;
import core.*;
public class SimplePixel implements Pixel
{
	private int x = 0;
	private int y = 0;
	private int rgb = 0;
	public int getRGB()
	{
		return rgb;
	}
	public int getX() 
	{
		return x;
	}
	public int getY() 
	{
		return y;
	}
	public void setRGB(int i)
	{
		rgb = i;
	}
	public void setX(int x) 
	{
		this.x = x;	
	}
	public void setY(int y) 
	{
		this.y = y;
	}
	public Pixel copy() 
	{
		Pixel erg = Factory.getPixel();
		erg.setX(x);
		erg.setY(y);
		erg.setRGB(rgb);
		return erg;
	}
	public String toString()
	{
		String erg = x + "." + y + ":" + rgb;
		return erg;
	}
}
