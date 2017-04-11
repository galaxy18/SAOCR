package tableRenderer;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import objects.SAOCRData;

import configs.Params;

@SuppressWarnings("serial")
public class ElementCellRenderer extends DefaultTableCellRenderer implements Params{
	private String type = "";
	public void setType(String arg_type){
		type = arg_type;
	}
	
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
		int Element = 99;
		if (TYPE_QUEST.equals(type)){
			Vector<String> data = SAOCRData.id2quest.get(id);
			Element = Integer.parseInt(data.get(SAOCRData.id2quest.get(headerRow_key).indexOf(header_element)));
		}
		else if (TYPE_MONSTER.equals(type)){
			Vector<String> data = SAOCRData.id2monster.get(id);
			try{
				Element = Integer.parseInt(data.get(SAOCRData.id2monster.get(headerRow_key).indexOf(header_element)));
			}
			catch (Exception e){
				Element = 4;
			}
		}
		else{
			Vector<String> data = SAOCRData.id2data.get(id + defaultType);
			try{
				Element = Integer.parseInt(data.get(SAOCRData.id2data.get(headerRow_key).indexOf(header_element)));
			}
			catch (Exception e){
				Element = 4;
			}
		}
		switch (Element){
    		case 1:
    			rendererComp.setForeground(color_ELEMENT1);break;
    		case 2: 
    			rendererComp.setForeground(color_ELEMENT2);break;
    		case 3: 
    			rendererComp.setForeground(color_ELEMENT3);break;
    		default: rendererComp.setForeground(Color.white); break;
		}
		//Set background color
//		rendererComp.setBackground(Color.black);
		((JLabel) rendererComp).setHorizontalAlignment(JLabel.CENTER);

		return rendererComp ;
	}
}
