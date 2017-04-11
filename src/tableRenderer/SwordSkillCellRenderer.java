package tableRenderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import objects.SAOCRData;

import configs.Params;

@SuppressWarnings("serial")
public class SwordSkillCellRenderer extends DefaultTableCellRenderer implements Params{
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
		int index = 0;
		for (int i = 0; i < table.getColumnCount(); i++){
			if ("ID".equals(table.getColumnName(i))){
				index = i;
				break;
			}
		}
        String id = (String) table.getValueAt(row, index)+defaultType;
        
        if (SAOCRData.chara2swordskill9.containsKey(id) ||
        		SAOCRData.chara2swordskill10.containsKey(id)){
        	setForeground(color_UniqueSS_title);
        }
        else{
        	setForeground(Color.white);
        }
        return this;
    }
}
