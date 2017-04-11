package tableRenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import configs.Params;

@SuppressWarnings("serial")
public class TypeCellRenderer extends DefaultTableCellRenderer implements Params{
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		//Set foreground color
		if (typetextlist[0].equals(table.getValueAt(row, column))){
			rendererComp.setForeground(color_TYPE1);
		}
		else if (typetextlist[1].equals(table.getValueAt(row, column))){
			rendererComp.setForeground(color_TYPE2);
		}
		else if (typetextlist[2].equals(table.getValueAt(row, column))){
			rendererComp.setForeground(color_TYPE3);
		}
		else if (typetextlist[3].equals(table.getValueAt(row, column))){
			rendererComp.setForeground(color_TYPE4);
		}
		else{
			rendererComp.setForeground(Color.white);
		}

		//Set background color
//		rendererComp .setBackground(Color.black);

		return rendererComp ;
	}
}
