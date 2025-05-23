package osm.viewer;
import core.*;
import core.Icon;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;
import java.io.*;
//import java.net.*;
public class MainFrame extends Menu implements PixelListener
{
	private static final long serialVersionUID = 0;
	private Icon icon = null;
	private InfoPanel info = Factory.getInfoPanel();
	private KachelCache cache = Factory.getKachelCache();
	private KachelPrefetcher prefetch = Factory.getKachelPrefetcher();
	private JPanel cpan = new JPanel();
	private JPanel bpan = new JPanel();
	private JButton butt1 = new JButton("+");
	private JButton butt2  = new JButton("-");
	private JButton butt3 = new JButton("@");
	private JButton butt4 = new JButton("mark ul");
	private JButton butt5 = new JButton("mark or");
	//private JButton butt4 = new JButton("gpx");
	private JMenuBar menu = new JMenuBar();
	private JMenu m1 = new JMenu("Information");
	private JMenu m2 = new JMenu("File");
	private JMenu m3 = new JMenu("GPX");
	private JMenu m4 = new JMenu("Cache");
	private JMenu m5 = new JMenu("Position");
	private JMenuItem m11 = new JMenuItem("Postleitzahlen...");
	private JMenuItem m12 = new JMenuItem("Version...");
	private JMenuItem m21 = new JMenuItem("SavePosition");
	private JMenuItem m22 = new JMenuItem("RestorePosition");
	private JMenuItem m23 = new JMenuItem("SaveMapAsPNG");
	private JMenuItem m24 = new JMenuItem("SaveXMLData");
	private JMenuItem m31 = new JMenuItem("Search GPX Track Directory");
	private JMenuItem m32 = new JMenuItem("Load GPX Track from Database");
	private JMenuItem m33 = new JMenuItem("Load GPX Track from File");
	private JMenuItem m34 = new JMenuItem("Withdraw Database Track");
	private JMenuItem m35 = new JMenuItem("Persist Database Track");
	private JMenuItem m36 = new JMenuItem("Database Tracks...");
	private JMenuItem m37 = new JMenuItem("Change User..");
	private JMenuItem m41 = new JMenuItem("Statistik...");
	private JMenuItem m42 = new JMenuItem("Shrink Cache");
	private JMenuItem m51 = new JMenuItem("Position to Border");
	private JMenuItem m52 = new JMenuItem("Position plus one north");
	private JMenuItem m53 = new JMenuItem("Position plus one east");
	private JMenuItem m54 = new JMenuItem("Position plus one south");
	private JMenuItem m55 = new JMenuItem("Position plus one west");
	private IconPanel ip = Factory.getIconPanel();
	private Persistenz persistenz = Factory.getPersistenz();
	private Koordinate koordinate = persistenz.getStartKoordinate();
	private GPXTrack track = null;
	//private Util util = Factory.getUtil();
	public MainFrame() 
	{
		super("http://www.myoggradio.org/osmwv");
		Protokol.write("MainFrame::Beginn");
		bpan.setLayout(new GridLayout(1,5));
		bpan.add(butt1);
		bpan.add(butt2);
		bpan.add(butt3);
		bpan.add(butt4);
		bpan.add(butt5);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		butt3.addActionListener(this);
		butt4.addActionListener(this);
		butt5.addActionListener(this);
		butt1.setToolTipText("Increase Zoom");
		butt2.setToolTipText("Decrease Zoom");
		butt3.setToolTipText("Show local Websites");
		butt4.setToolTipText("Markiert die untere linke Ecke eines Bereichs");
		butt5.setToolTipText("Markiert die obere rechte Ecke eines Bereichs");
		m11.addActionListener(this);
		m12.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m23.addActionListener(this);
		m24.addActionListener(this);
		m31.addActionListener(this);
		m32.addActionListener(this);
		m33.addActionListener(this);
		m34.addActionListener(this);
		m35.addActionListener(this);
		m36.addActionListener(this);
		m37.addActionListener(this);
		m41.addActionListener(this);
		m42.addActionListener(this);
		m51.addActionListener(this);
		m52.addActionListener(this);
		m53.addActionListener(this);
		m54.addActionListener(this);
		m55.addActionListener(this);
		m1.add(m12);
		m1.add(m11);
		m2.add(m21);
		m2.add(m22);
		m2.add(m23);
		m2.add(m24);
		m3.add(m31);
		m3.add(m32);
		m3.add(m33);
		m3.add(m34);
		m3.add(m35);
		m3.add(m36);
		m3.add(m37);
		m4.add(m41);
		m4.add(m42);
		m5.add(m51);
		m5.add(m52);
		m5.add(m53);
		m5.add(m54);
		m5.add(m55);
		menu.add(m5);
		menu.add(m4);
		menu.add(m3);
		menu.add(m2);
		menu.add(m1);
		setJMenuBar(menu);
		ip.addListener(this);
		showIcon();
		cpan.setLayout(new BorderLayout());
		cpan.add(bpan,BorderLayout.NORTH);
		cpan.add(ip,BorderLayout.CENTER);
		cpan.add(info,BorderLayout.SOUTH);
		setContentPane(cpan);
		Protokol.write("MainFrame::End");
	}
	public void setTrack(GPXTrack track)
	{
		this.track = track;
		track.buildKoordinate(koordinate);
		cache.resetRAM();
		showIcon();
	}
	public void showIcon()
	{
		info.setKoordinate(koordinate);
		info.build();
		Kachel k00 = cache.get(koordinate.getX()+0,koordinate.getY()+0,koordinate.getZ());
		Kachel k01 = cache.get(koordinate.getX()+0,koordinate.getY()+1,koordinate.getZ());
		Kachel k02 = cache.get(koordinate.getX()+0,koordinate.getY()+2,koordinate.getZ());
		Kachel k03 = cache.get(koordinate.getX()+0,koordinate.getY()+3,koordinate.getZ());
		Kachel k10 = cache.get(koordinate.getX()+1,koordinate.getY()+0,koordinate.getZ());
		Kachel k11 = cache.get(koordinate.getX()+1,koordinate.getY()+1,koordinate.getZ());
		Kachel k12 = cache.get(koordinate.getX()+1,koordinate.getY()+2,koordinate.getZ());
		Kachel k13 = cache.get(koordinate.getX()+1,koordinate.getY()+3,koordinate.getZ());
		Kachel k20 = cache.get(koordinate.getX()+2,koordinate.getY()+0,koordinate.getZ());
		Kachel k21 = cache.get(koordinate.getX()+2,koordinate.getY()+1,koordinate.getZ());
		Kachel k22 = cache.get(koordinate.getX()+2,koordinate.getY()+2,koordinate.getZ());
		Kachel k23 = cache.get(koordinate.getX()+2,koordinate.getY()+3,koordinate.getZ());		
		Kachel k30 = cache.get(koordinate.getX()+3,koordinate.getY()+0,koordinate.getZ());
		Kachel k31 = cache.get(koordinate.getX()+3,koordinate.getY()+1,koordinate.getZ());
		Kachel k32 = cache.get(koordinate.getX()+3,koordinate.getY()+2,koordinate.getZ());
		Kachel k33 = cache.get(koordinate.getX()+3,koordinate.getY()+3,koordinate.getZ());		
		prefetch = Factory.getKachelPrefetcher();
		prefetch.setKoordinaten(koordinate.getX(),koordinate.getY(),koordinate.getZ());
		//prefetch.startPrefetch(cache);
		Icon icon00 = k00.getIcon(track);
		Icon icon01 = k01.getIcon(track);
		Icon icon02 = k02.getIcon(track);
		Icon icon03 = k03.getIcon(track);
		Icon icon10 = k10.getIcon(track);
		Icon icon11 = k11.getIcon(track);
		Icon icon12 = k12.getIcon(track);
		Icon icon13 = k13.getIcon(track);
		Icon icon20 = k20.getIcon(track);
		Icon icon21 = k21.getIcon(track);
		Icon icon22 = k22.getIcon(track);
		Icon icon23 = k23.getIcon(track);
		Icon icon30 = k30.getIcon(track);
		Icon icon31 = k31.getIcon(track);
		Icon icon32 = k32.getIcon(track);
		Icon icon33 = k33.getIcon(track);
		/*
		Color grau = new Color(100,100,100);
		icon00.umrande(grau);
		icon01.umrande(grau);
		icon02.umrande(grau);
		icon03.umrande(grau);
		icon10.umrande(grau);
		icon11.umrande(grau);
		icon12.umrande(grau);
		icon13.umrande(grau);
		icon20.umrande(grau);
		icon21.umrande(grau);
		icon22.umrande(grau);
		icon23.umrande(grau);
		icon30.umrande(grau);
		icon31.umrande(grau);
		icon32.umrande(grau);
		icon33.umrande(grau);
		*/
		Icon icon16 = Factory.getIcon();
		icon16.build16(icon00,icon01,icon02,icon03,icon10,icon11,icon12,icon13,icon20,icon21,icon22,icon23,icon30,icon31,icon32,icon33);
		icon = icon16.clip16(koordinate.getP(),koordinate.getQ());
		icon = icon16.mittel16Kreuz(icon);
		if (track != null) icon.paintGPX(track,koordinate);
		ip.setIcon(icon);
		this.validate();
		this.repaint();
	}
	@Override
	public void clicked(Pixel pixel) 
	{
		int x = koordinate.getX();
		int y = koordinate.getY();
		int q = koordinate.getQ();
		int p = koordinate.getP();
		int a = pixel.getX();
		int b = pixel.getY();
		p = p + a - 384;
		q = q + b - 384;
		if (p < 0)
		{
			if (p < -256)
			{
				p += 512;
				y--;
				y--;
			}
			else
			{
				p += 256;
				y--;
			}
		}
		if (q < 0) 
		{
			if (q < -256)
			{
				q += 512;
				x--;
				x--;
			}
			else
			{
				q += 256;
				x--;
			}
		}
		if (p > 256)
		{
			if (p > 512)
			{
				p -= 512;
				y++;
				y++;
			}
			else
			{
				p -= 256;
				y++;
			}
		}
		if (q > 256)
		{
			if (q > 512)
			{
				q -= 512;
				x++;
				x++;
			}
			else
			{
				q -= 256;
				x++;
			}
			
		}
		koordinate.setX(x);
		koordinate.setY(y);
		koordinate.setQ(q);
		koordinate.setP(p);
		koordinate.calculateLatLon();
		showIcon();
	}
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object quelle = ae.getSource();
		/*
		if (quelle == m11)
		{
			String about = "http://ehm.homelinux.org/OSMWV/about.html";
			URL url = null;
			try
			{
				url = new URL(about);
				Desktop desktop = Desktop.getDesktop();
				desktop.browse(url.toURI());
			}
			catch (Exception e)
			{
				Protokol.write("MainFrame:actionPerformed:m11:Exception:");
				Protokol.write(e.toString());
			}
		}
		*/
		if (quelle == m11)
		{
			Postgres postgres = new Postgres();
			ArrayList<SatzPlz> saetze = postgres.selectAllePlz();
			postgres.close();
			double lat = koordinate.getLat();
			double lon = koordinate.getLon();
			for (int i=0;i<saetze.size();i++)
			{
				SatzPlz satz = saetze.get(i);
				double plat = satz.getLat();
				double plon = satz.getLon();
				double dlat = lat - plat;
				double dlon = lon - plon;
				Double delta = Math.sqrt(dlat * dlat + dlon * dlon);
				satz.setLat(delta);
			}
			Collections.sort(saetze);
			SatzPlz min0satz = saetze.get(0);
			String min0plz = min0satz.getPlz();
			String min0ort = min0satz.getOrt();
			SatzPlz min1satz = saetze.get(1);
			String min1plz = min1satz.getPlz();
			String min1ort = min1satz.getOrt();
			SatzPlz min2satz = saetze.get(2);
			String min2plz = min2satz.getPlz();
			String min2ort = min2satz.getOrt();
			SatzPlz min3satz = saetze.get(3);
			String min3plz = min3satz.getPlz();
			String min3ort = min3satz.getOrt();
			SatzPlz min4satz = saetze.get(4);
			String min4plz = min4satz.getPlz();
			String min4ort = min4satz.getOrt();
			String min = min0plz + " " + min0ort;
			min += "\n" + min1plz + " " + min1ort;
			min += "\n" + min2plz + " " + min2ort;
			min += "\n" + min3plz + " " + min3ort;
			min += "\n" + min4plz + " " + min4ort;
			JOptionPane.showMessageDialog(null,min,"Fünf nächstgelegene Postleitzahlen",JOptionPane.INFORMATION_MESSAGE);
		}
		if (quelle == m12)
		{
			JOptionPane.showMessageDialog(null,Parameter.version,"Version",JOptionPane.INFORMATION_MESSAGE);
		}
		if (quelle == m21)
		{
			persistenz.storeStartKoordinate(koordinate);
		}
		if (quelle == m22)
		{
			koordinate = persistenz.getStartKoordinate();
			showIcon();
		}
		if (quelle == m23)
		{
			FileChooser fc = Factory.getFileChooser();
			File file = fc.getWriteFile();
			if (file != null)
			{
				icon.writeToFile(file);
			}			
		}
		if (quelle == m24)
		{
			FileChooser fc = Factory.getFileChooser();
			File file = fc.getWriteFile();
			if (file != null)
			{
				XMLData xmlData = Factory.getXMLData();
				ArrayList<String> xml = xmlData.getXML(koordinate);
				try
				{
					Writer writer = new FileWriter(file);
					for (int i=0;i<xml.size();i++)
					{
						String satz = xml.get(i);
						writer.write(satz + "\n");
					}
					writer.close();
				}
				catch (Exception e)
				{
					Protokol.write("MainFrame:actionPerformed:m24:Exception:");
					Protokol.write(e.toString());
				}
			}			
		}
		if (quelle == m31) // Search GPX Track Directory
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("GPX Tracks Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
		    {
		    	File directory = chooser.getSelectedFile();
		    	GPXParser parser = new GPXParser();
		    	try
		    	{
		    		parser.parse(directory);
		    	}
		    	catch (Exception e)
		    	{
		    		Protokol.write("MainFrame:actionPerformed:m31:Exception:");
		    		Protokol.write(e.toString());
		    	}
            }
		}
		/*
		if (quelle == m32) // Close GPX Track
		{
			if (track == null)
			{
				JOptionPane.showMessageDialog(null,"You must Open GPX Track first","",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				track.writeToFile();
				track = null;
			}
		}
		*/
		if (quelle == m32) // Load GPX Track from Database
		{
			cache.resetRAM();
			track = Factory.getGPXTrack();
			boolean ok = track.readFromDatabase();
			if (track == null) ok = false;
			if (ok)
			{
				track.buildKoordinate(koordinate);
			}
			else
			{
				Protokol.write("MainFrame:actionPerformed:m32:Kein Track vorhanden");
				track = null;
			}
			showIcon();
		}
		if (quelle == m33) // Load GPX Track from File
		{
			FileChooser fc = Factory.getFileChooser();
			File file = fc.getGPXFile();
			if (file != null)
			{
				cache.resetRAM();
				track = Factory.getGPXTrack();
				track.readFromFile(file);
				boolean ok = true;
				if (track == null) ok = false;
				if (ok) track.buildKoordinate(koordinate);
				showIcon();
			}			
		}
		if (quelle == m34) //Withdraw Dtabase Track
		{
			GPXPostgres postgres = new GPXPostgres();
			String ok = postgres.loescheAktuellenTrack();
			if (ok == null)
			{
				JOptionPane.showMessageDialog(null,"GPX Track deleted from Database","",JOptionPane.INFORMATION_MESSAGE);	
			}
			else
			{
				JOptionPane.showMessageDialog(null,ok,"",JOptionPane.ERROR_MESSAGE);	
			}
			postgres.close();
		}
		if (quelle == m35) //Persist Database Track
		{
			GPXPostgres postgres = new GPXPostgres();
			String ok = postgres.copyAktuellenTrack();
			if (ok == null)
			{
				ok = postgres.loescheAktuellenTrack();
			}
			if (ok == null)
			{
				JOptionPane.showMessageDialog(null,"GPX Track persisted in Database","",JOptionPane.INFORMATION_MESSAGE);	
			}
			else
			{
				JOptionPane.showMessageDialog(null,ok,"",JOptionPane.ERROR_MESSAGE);	
			}
			postgres.close();
		}
		if (quelle == m36) //Database Tracks..
		{
			GPXPostgres postgres = new GPXPostgres();
			ArrayList<Long> tracks = postgres.selectDistinctTrackDatum();
			postgres.close();
			TracksMenu tm = new TracksMenu();
			tm.setTracks(tracks,this);
			tm.anzeigen();
		}
		if (quelle == m37) //change User..
		{
			BenutzerMenu bm = new BenutzerMenu();
			bm.anzeigen();
		}
		if (quelle == butt1) // Zoom +
		{
			int x = koordinate.getX();
			int y = koordinate.getY();
			int z = koordinate.getZ();
			int q = koordinate.getQ();
			int p = koordinate.getP();
			if (z < 18)
			{
				z++;
				double dx = (double) x;
				dx += 1.5;
				double dy = (double) y;
				dy += 1.5;
				double dp = (double) p;
				double dq = (double) q;
				dx += dq / 256.0;
				dy += dp / 256.0;
				dx = 2.0 * dx;
				dy = 2.0 * dy;
				x = (int) dx;
				y = (int) dy;
				p = (int) ((dy - y) * 256.0);
				q = (int) ((dx - x) * 256.0);
				p -=384;
				q -=384;
				if (p < 0)
				{
					y--;
					p += 256;
					if (p < 0)
					{
						y--;
						p += 256;
					}
				}
				if (q < 0)
				{
					x--;
					q += 256;
					if (q < 0)
					{
						x--;
						q += 256;
					}
				}
			}
			else
			{
				Protokol.write("MainFrame:actionPerformed: Nur Zoomstufe 2 bis 18 erlaubt");
			}
			koordinate.setX(x);
			koordinate.setY(y);
			koordinate.setZ(z);
			koordinate.setQ(q);
			koordinate.setP(p);
			this.showIcon();
		}
		if (quelle == butt2) // Zoom -
		{
			int x = koordinate.getX();
			int y = koordinate.getY();
			int z = koordinate.getZ();
			int q = koordinate.getQ();
			int p = koordinate.getP();
			if (z > 2)
			{
				z--;
				double dx = (double) x;
				dx += 1.5;
				double dy = (double) y;
				dy += 1.5;
				double dp = (double) p;
				double dq = (double) q;
				dx += dq / 256.0;
				dy += dp / 256.0;
				dx = dx / 2.0;
				dy = dy / 2.0;
				x = (int) dx;
				y = (int) dy;
				p = (int) ((dy - y) * 256.0);
				q = (int) ((dx - x) * 256.0);
				p -=384;
				q -=384;
				if (p < 0)
				{
					y--;
					p += 256;
					if (p < 0)
					{
						y--;
						p += 256;
					}
				}
				if (q < 0)
				{
					x--;
					q += 256;
					if (q < 0)
					{
						x--;
						q += 256;
					}
				}
			}
			else
			{
				Protokol.write("MainFrame:actionPerformed: Nur Zoomstufe 2 bis 18 erlaubt");
			}
			koordinate.setX(x);
			koordinate.setY(y);
			koordinate.setZ(z);
			koordinate.setQ(q);
			koordinate.setP(p);
			this.showIcon();
		}
		if (quelle == butt3) // @ Lokale Webseiten anzeigen
		{
			int x = koordinate.getX();
			int y = koordinate.getY();
			int z = koordinate.getZ();
			int q = koordinate.getQ();
			int p = koordinate.getP();
			WebsiteViewer viewer = Factory.getWebsiteViewer(x,y,z,p,q);
			viewer.start();
			JOptionPane.showMessageDialog(null,"WebsiteViewer started. Please Wait","",JOptionPane.INFORMATION_MESSAGE);
		}
		if (quelle == butt4) // markiere Bereich unten links
		{
			koordinate.calculateLatLon();
			double lat = koordinate.getLat();
			double lon = koordinate.getLon();
			Parameter.latul = lat;
			Parameter.lonul = lon;
			Protokol.write("Unten Links: " + lat + ":" + lon);
		}
		if (quelle == butt5) // markiere Bereich oben rechts
		{
			koordinate.calculateLatLon();
			double lat = koordinate.getLat();
			double lon = koordinate.getLon();
			Parameter.lator = lat;
			Parameter.lonor = lon;
			Protokol.write("Oben Rechts: " + lat + ":" + lon);
		}
		if (quelle == m41)
		{
			KachelCache kc = Factory.getKachelCache();
			String statistik = kc.getStatistik();
			JOptionPane.showMessageDialog(null,statistik,"Statistik",JOptionPane.INFORMATION_MESSAGE);
		}
		if (quelle == m42)
		{
			KachelCache kc = Factory.getKachelCache();
			kc.shrink();
		}
		if (quelle == m51) // Position to Border
		{
			koordinate.setP(0);
			koordinate.setQ(0);
			koordinate.calculateLatLon();
			showIcon();
		}
		if (quelle == m54) // Position plus one north
		{
			int x = koordinate.getX();
			int z = koordinate.getZ();
			x++;
			int n = 1;
			for (int i=0;i<z;i++)
			{
				n = n * 2;
			}
			if (x > n) x--;
			koordinate.setX(x);
			showIcon();
		}
		if (quelle == m53) // Position plus one east
		{
			int y = koordinate.getY();
			int z = koordinate.getZ();
			y++;
			int n = 1;
			for (int i=0;i<z;i++)
			{
				n = n * 2;
			}
			if (y > n) y--;
			koordinate.setY(y);
			showIcon();
		}
		if (quelle == m52) // Position plus one south
		{
			int x = koordinate.getX();
			x--;
			if (x < 0) x++;
			koordinate.setX(x);
			showIcon();
		}
		if (quelle == m55) // Position plus one north
		{
			int y = koordinate.getY();
			y--;
			if (y < 0) y++;
			koordinate.setY(y);
			showIcon();
		}
	}
	@Override
	public void anzeigen()
	{
		Protokol.write("MainFrame:anzeigen:Beginn");
		this.pack();
		this.setVisible(true);
		Protokol.write("MainFrame:anzeigen:End");
	}
	public boolean istGerade(int i)
	{
		boolean erg = false;
		int x = i / 2;
		if (i == (2 * x)) erg = true;
		return erg;
	}
}
