package tableRenderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import configs.Params;

@SuppressWarnings("serial")
public class WorldCellRenderer extends DefaultTableCellRenderer implements Params{
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		 int index = 0;
		 for (int i = 0; i < table.getColumnCount(); i++){
			 if ("ID".equals(table.getColumnName(i))){
				 index = i;
				 break;
			 }
		 }
		String id = (String) table.getValueAt(row, index);
		char world = id.charAt(0);
		switch (world){
			case flag_SAO:
				if (image_SAO.getImageLoadStatus() != java.awt.MediaTracker.ERRORED)
					((JLabel) rendererComp).setIcon(image_SAO); 
				else
					((JLabel) rendererComp).setText(world_SAO); 
				break;
			case flag_ALO:
				if (image_ALO.getImageLoadStatus() != java.awt.MediaTracker.ERRORED)
					((JLabel) rendererComp).setIcon(image_ALO); 
				else
					((JLabel) rendererComp).setText(world_ALO);
				break;
			case flag_GGO:
				if (image_GGO.getImageLoadStatus() != java.awt.MediaTracker.ERRORED)
					((JLabel) rendererComp).setIcon(image_GGO); 
				else
					((JLabel) rendererComp).setText(world_GGO);
				break;
			case flag_EX:
				((JLabel) rendererComp).setIcon(null);
				((JLabel) rendererComp).setText(world_OTHER);
				break;
		}

		//Set background color
//		rendererComp .setBackground(Color.black);

		return rendererComp ;
	}
}
