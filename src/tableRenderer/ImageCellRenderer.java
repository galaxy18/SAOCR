package tableRenderer;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import objects.SAOCRData;

import configs.Params;

@SuppressWarnings("serial")
public class ImageCellRenderer extends DefaultTableCellRenderer implements Params{
	private String path = "";
    private String[] pattens = {folder_icon+"%s.png",
    							folder_icon+"%s1.png",
    							folder_icon+"web_%s1.png"};
    
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
		String id = (String) table.getValueAt(row, index);
        if (!id.endsWith("0")){
        	id = SAOCRData.awake2id.get(id);
        }
    	if (path.equals(String.format(pattens[0],id)) ||
    			path.equals(String.format(pattens[1],id)) ||
    			path.equals(String.format(pattens[2],id))){
    	}
    	else{
    		try{
    			File file;
    			path = String.format(pattens[0],id);
    			file = new File(path);//folder_icon+id+".png");
    			if (file.exists()){
    			}
    			else{
    				path = String.format(pattens[1],id);
    				file = new File(path);//(folder_icon+id+"1.png");
    				if (file.exists()){
    				}
    				else{
    					path = String.format(pattens[2],id);
    					file = new File(path);//(folder_icon+"web_"+id+"1.png");
    					if (file.exists()){
    					}
    					else{
    						path = folder_icon+"icon_ERROR.PNG";
    					}
    				}
    			}
    			ImageIcon icon = new ImageIcon(path);
    			setIcon(icon);
	        }
	        catch (Exception e){
	        }
        }
        return this;
    }
}
