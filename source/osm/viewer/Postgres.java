package osm.viewer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import core.Protokol;
public class Postgres
{
    int rc = 0;
    private Connection con = null;
    private String benutzer = "plz";
    private String passwort = "osmwvplz";
    private String url = "jdbc:postgresql://85.215.40.239:5432/plz";
    private String driver = "org.postgresql.Driver";
    public Postgres()
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
    public ArrayList<SatzPlz> selectAllePlz() 
    {
        if (con == null) connect();
    	ArrayList<SatzPlz> erg = new ArrayList<SatzPlz>();
        ResultSet rs = null;
        String sql = "Select plz,ort,lon,lat";
        sql += " from plz";
        sql += " order by plz";
        try 
        {
            PreparedStatement stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next())
            {
            	String plz = rs.getString(1);
            	String ort = rs.getString(2);
            	float lon = rs.getFloat(3);
            	float lat = rs.getFloat(4);
            	SatzPlz satz = new SatzPlz();
            	satz.setPlz(plz);
            	satz.setOrt(ort);
            	satz.setLon(lon);
            	satz.setLat(lat);
            	erg.add(satz);
            }
            rs.close();
            stmt.close();
        } 
        catch (Exception e) 
        {
            Protokol.write("Postgres:selectAllePlz:Exception:");
            Protokol.write(e.toString());
        }
        return erg;
    }
}
