package tableRenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import configs.Params;

@SuppressWarnings("serial")
public class HeaderCellRenderer extends DefaultTableCellRenderer implements Params{
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        setFont(extabledigitalfont);
    	setForeground(Color.white);
    	setBackground(color_back);
    	setHorizontalAlignment(SwingConstants.CENTER);
    	
        return this;
    }
}
