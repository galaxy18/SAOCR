package tableRenderer;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import configs.Params;

@SuppressWarnings("serial")
public class DigitalCellRenderer extends DefaultTableCellRenderer implements Params{
    Font font = extabledigitalfont;
    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        setFont(font);
        return this;
    }
}
