package osm.viewer;
import javax.swing.*;

import core.Protokol;

import java.awt.event.*;
public class Menu extends JFrame implements ActionListener
{
	static final long serialVersionUID = 1;
	public Menu(String name)
	{
		super(name);
		setName(name);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	public void anzeigen()
	{
		Protokol.write("Menu::Beginn");
		JFrame rahmen = this;
		rahmen.pack();
		rahmen.setVisible(true);
		Protokol.write("Menu::End");
	}
	public void actionPerformed(ActionEvent ae)
	{

	}
}
