package osm.viewer;
import java.io.File;
import java.util.prefs.Preferences;

import core.*;
public class Main 
{
	public static void main(String[] args) 
	{
		Protokol.write(Parameter.version);
		String homedir = System.getProperty("user.home");
		String s = File.separator;
		Parameter.cachedir = homedir + s + ".osmwvcache" + s;
		Protokol.write("Main:main:cachedir:");
		Protokol.write(Parameter.cachedir);
		File dir = new File(Parameter.cachedir);
		if (!dir.isDirectory())
		{
			Protokol.write("Main:main:will create cachedir");
			dir.mkdir();
		}
		Preferences prefs = Preferences.userRoot();
		Parameter.benutzer = prefs.get("benutzer","christian");
		Protokol.write("Benutzer: " + Parameter.benutzer);
		MainFrame mf = new MainFrame();
		mf.anzeigen();
	}
}
