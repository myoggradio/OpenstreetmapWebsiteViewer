package osm.viewer;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyListSelectionListener implements ListSelectionListener
{
	private JList<Long> list = null;
	public MyListSelectionListener(JList<Long> list)
	{
		this.list = list;
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) 
	{
		if (!lse.getValueIsAdjusting())
		{
			long l = list.getSelectedValue();
			System.out.println(l);
		}
	}
}