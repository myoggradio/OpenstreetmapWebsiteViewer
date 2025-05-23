package osm.viewer;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.Parameter;
import core.Protokol;
public class BenutzerMenu extends Menu 
{
	private static final long serialVersionUID = 0;
	private Preferences prefs = null;
	private JButton butt1 = new JButton("change");
	private JButton butt2 = new JButton("cancel");
	private JTextField tf1 = new JTextField(10);
	private JLabel lab1 = new JLabel("Benutzer");
	private JPanel cpan = new JPanel();
	public BenutzerMenu() 
	{
		super("BenutzerMenu");
		Protokol.write("BenutzerMenu::Beginn");
		prefs = Preferences.userRoot();
		Parameter.benutzer = prefs.get("benutzer","christian");
		tf1.setText(Parameter.benutzer);
		cpan.setLayout(new GridLayout(2,2));
		cpan.add(lab1);
		cpan.add(tf1);
		cpan.add(butt1);
		cpan.add(butt2);
		setContentPane(cpan);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		Protokol.write("BenutzerMenu::End");
	}
	@Override
	public void anzeigen()
	{
		Protokol.write("BenutzerMenu:anzeigen:Beginn");
		this.pack();
		this.setVisible(true);
		Protokol.write("BenutzerMenu:anzeigen:End");
	}
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object quelle = ae.getSource();
		if (quelle == butt1)
		{
			Parameter.benutzer = tf1.getText();
			prefs.put("benutzer",Parameter.benutzer);
			dispose();
		}
		if (quelle == butt2)
		{
			dispose();
		}
	}
}
