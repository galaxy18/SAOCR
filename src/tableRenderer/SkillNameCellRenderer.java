package tableRenderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import configs.Params;

@SuppressWarnings("serial")
public class SkillNameCellRenderer extends DefaultTableCellRenderer implements Params{
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		rendererComp.setForeground(color_SKILL_title);

		//Set background color
//		rendererComp .setBackground(Color.black);

		return rendererComp ;
	}
}
