package osm.viewer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
    public String loescheAktuellenTrack()
    {
    	String erg = null;
    	if (con == null) connect();
    	String sql = "delete from track";
        try 
        {
        	Protokol.write(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (Exception e) 
        {
            Protokol.write("GPXPostgres:loescheAktuellenTrack:Exception:");
            Protokol.write(e.toString());
            erg = e.toString();
        }
        return erg;
    }
    public String copyAktuellenTrack()
    {
    	String erg = null;
    	if (con == null) connect();
    	String sql = "insert into zeit_track select lat,lon,ele,datum,? from track";
        try 
        {
        	Protokol.write(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1,new Date().getTime());
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (Exception e) 
        {
            Protokol.write("GPXPostgres:copyAktuellenTrack:Exception:");
            Protokol.write(e.toString());
            erg = e.toString();
        }
        return erg;
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
    public String deleteTrackWithId(long id)
    {
    	String erg = null;
    	if (con == null) connect();
    	String sql = "delete from zeit_track where track_datum = ?";
        try 
        {
        	Protokol.write(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
        } 
        catch (Exception e) 
        {
            Protokol.write("GPXPostgres:deleteTrackWithId:Exception:");
            Protokol.write(e.toString());
            erg = e.toString();
        }
        return erg;
    }
    public ArrayList<Point> selectTrackWithId(long id) 
    {
        if (con == null) connect();
    	ArrayList<Point> erg = new ArrayList<Point>();
        ResultSet rs = null;
        String sql = "Select lat,lon";
        sql += " from zeit_track";
        sql += " where track_datum = ?";
        sql += " order by datum";
        try 
        {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
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
            Protokol.write("GPXPostgres:selectTrackWithId:Exception:");
            Protokol.write(e.toString());
        }
        return erg;
    }
    public ArrayList<Long> selectDistinczTrackDatum() 
    {
        if (con == null) connect();
    	ArrayList<Long> erg = new ArrayList<Long>();
        ResultSet rs = null;
        String sql = "Select distinct track_datum";
        sql += " from zeit_track";
        sql += " order by track_datum";
        try 
        {
            PreparedStatement stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next())
            {
            	Long track_datum = rs.getLong(1);
            	erg.add(track_datum);
            }
            rs.close();
            stmt.close();
        } 
        catch (Exception e) 
        {
            Protokol.write("GPXPostgres:selectDistintTrackDatum:Exception:");
            Protokol.write(e.toString());
        }
        return erg;
    }
}
