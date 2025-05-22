package osm.viewer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import core.Factory;
import core.GPXTrack;
import core.Protokol;
public class TracksMenu extends Menu 
{
	private static final long serialVersionUID = 0;
	private JList<Long> list = null;
	private MainFrame mf = null;
	private JButton butt1 = new JButton("showTrack");
	private JButton butt2 = new JButton("deleteTrack");
	public TracksMenu() 
	{
		super("TracksMenu");
		Protokol.write("TracksMenu::Beginn");

		Protokol.write("TracksMenu::End");
	}
	public void setTracks(ArrayList<Long> tracks,MainFrame mf)
	{
		this.mf = mf;
		Long[] s_tracks = new Long[tracks.size()];
		for(int i=0;i<tracks.size();i++)
		{
			s_tracks[i] = tracks.get(i);
		}
		list = new JList<Long>(s_tracks);
		ListSelectionListener listener = new MyListSelectionListener(list);
		list.addListSelectionListener(listener);
		JScrollPane span = new JScrollPane(list);
		span.setPreferredSize(new Dimension(800,800));
		JPanel cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		cpan.add(span,BorderLayout.CENTER);
		JPanel bpan = new JPanel();
		bpan.setLayout(new GridLayout(1,2));
		bpan.add(butt1);
		bpan.add(butt2);
		cpan.add(bpan,BorderLayout.NORTH);
		setContentPane(cpan);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
	}
	@Override
	public void anzeigen()
	{
		Protokol.write("TracksMenu:anzeigen:Beginn");
		this.pack();
		this.setVisible(true);
		Protokol.write("TracksMenu:anzeigen:End");
	}
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object quelle = ae.getSource();
		if (quelle == butt1)
		{
			Long l = list.getSelectedValue();
			if (l != null)
			{
				Protokol.write("Track selected: " + l);
				GPXTrack track = Factory.getGPXTrack();
				boolean ok = track.readFromDatabaseWithId(l);
				if (ok)
				{
					mf.setTrack(track);
					dispose();
				}
				else
				{
					Protokol.write("Datenbankzugriff fehlgeschlagen");
					dispose();
				}
			}
			else
			{
				Protokol.write("Bitte erst einen Track aussuchen");
			}
		}
		if (quelle == butt2)
		{
			Long l = list.getSelectedValue();
			if (l != null)
			{
				GPXTrack track = Factory.getGPXTrack();
				boolean ok = track.deleteFromDatabaseWithId(l);
				if (ok)
				{
					Protokol.write("Track deleted");
					dispose();
				}
				else
				{
					Protokol.write("Datenbankzugriff fehlgeschlagen");
					dispose();
				}
			}
			else
			{
				Protokol.write("Bitte erst einen Track aussuchen");
			}
		}
	}
}
