package osm.viewer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import core.Point;

import core.Protokol;
public class GPXPostgres
{
    int rc = 0;
    private Connection con = null;
    private String benutzer = "plz";
    private String passwort = "osmwvplz";
    private String url = "jdbc:postgresql://85.215.40.239:5432/gpx";
    private String driver = "org.postgresql.Driver";
    public GPXPostgres()
    {

    }
    public int connect() 
    {
        rc = 0;
        if (con == null)
        {
            try
            {
                Class.forName(driver);
                String url = this.url;
                String userid = benutzer;
                String password = passwort;
                con = DriverManager.getConnection(url, userid, password);
            } 
            catch (Exception e) 
            {
                con = null;
                rc = 8;
                Protokol.write("Postgres:Connect:Exception:");
                Protokol.write(e.toString());
            }
        }
        return rc;
    }
    public void close()
    {
    	try 
    	{
			con.close();
		} 
    	catch (SQLException e) 
    	{
    		Protokol.write("Postgres:close:Exception:");
    		Protokol.write(e.toString());
		}
    }
    public ArrayList<Point> selectTrack() 
    {
        if (con == null) connect();
    	ArrayList<Point> erg = new ArrayList<Point>();
        ResultSet rs = null;
        String sql = "Select lat,lon";
        sql += " from track";
        sql += " order by datum";
        try 
        {
            PreparedStatement stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next())
            {
            	double lat = rs.getDouble(1);
            	double lon = rs.getDouble(2);
            	Point punkt = new Point();
            	punkt.setLon(lon);
            	punkt.setLat(lat);
            	erg.add(punkt);
            }
            rs.close();
            stmt.close();
        } 
        catch (Exception e) 
        {
            Protokol.write("GPXPostgres:selectTrack:Exception:");
            Protokol.write(e.toString());
        }
        return erg;
    }
}
