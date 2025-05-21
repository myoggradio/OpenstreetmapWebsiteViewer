package core;
public interface Pixel 
{
	public void setX(int x);
	public void setY(int y);
	public void setRGB(int rgb);
	public int getX();
	public int getY();
	public int getRGB();
	public Pixel copy();
	public String toString();
}
