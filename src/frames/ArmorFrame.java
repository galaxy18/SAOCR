package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import objects.SAOCRData;

import tableRenderer.DigitalCellRenderer;
import tableRenderer.HeaderCellRenderer;
import com.opencsv.CSVReader;
import configs.Params;

public class ArmorFrame implements Params{
	JFrame frm_armorList = new JFrame("Armor List");
	
	JTable tbl_armorList;
	
	List<Vector<String>> list = new ArrayList<Vector<String>>();
	
	public ArmorFrame() throws Exception{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path_armor), "UTF-8"));
    	String[] dataArray = reader.readNext();
		Vector<String> row = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			row.add(dataArray[s]);
		}
		SAOCRData.id2armor.put(dataArray[0], row);
		
    	while((dataArray = reader.readNext()) != null) {
    		row = new Vector<String>();
    		for(int s = 0; s < dataArray.length; s++) {
    			row.add(dataArray[s]);
    		}
			if (!"".equals(dataArray[0].trim())){
				list.add(row);
			}
    		SAOCRData.id2armor.put(dataArray[0], row);
    	}
    	reader.close();
    	
    	createFrame();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createFrame(){
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		DigitalCellRenderer digitalCell = new DigitalCellRenderer();
		HeaderCellRenderer extable_header = new HeaderCellRenderer();
		
    	tbl_armorList = new JTable(model);
    	tbl_armorList.getTableHeader().setDefaultRenderer(extable_header);
//    	tbl_extendList.setGridColor(Color.black);
    	commonFormatting(tbl_armorList);
    	tbl_armorList.setRowSorter(new TableRowSorter(model));
    	tbl_armorList.setRowSelectionAllowed(true);
    	tbl_armorList.setGridColor(Color.black);
    	tbl_armorList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    	
        model.addColumn("ID");
        model.addColumn(header_name);
        model.addColumn("str");
        model.addColumn("");
        model.addColumn("vit");
        model.addColumn("");
        model.addColumn("int");
        model.addColumn("");
        model.addColumn("men");
        model.addColumn("");
        model.addColumn(header_starttime);
        model.addColumn(header_endtime);
        
    	int[] colwidth = {80, 300, 
    			90, 90,
    			90, 90,
    			90, 90,
    			90, 90,
    			180, 180};
    	for (int i = 0; i < colwidth.length; i++){
    		tbl_armorList.getColumnModel().getColumn(i).setPreferredWidth(colwidth[i]);
    	}
    	tbl_armorList.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(2).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(3).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(4).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(5).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(6).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(7).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(8).setCellRenderer(digitalCell);
    	tbl_armorList.getColumnModel().getColumn(9).setCellRenderer(digitalCell);
    	TableRowSorter rowSorter = (TableRowSorter) tbl_armorList.getRowSorter();
        Comparator<String> numberComparator = new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                if ( o1 == null || "".equals(o1)) {  
                    return -1;  
                }  
                if ( o2 == null || "".equals(o2)) {  
                    return 1;
                }
                if (Double.parseDouble(o1) > Double.parseDouble(o2)){
                	return -1;  
                }
                if (Double.parseDouble(o1) < Double.parseDouble(o2)){
                	return 1;  
                }
                return 0;
            }  
        };
    	rowSorter.setComparator(2, numberComparator);
    	rowSorter.setComparator(3, numberComparator);
    	rowSorter.setComparator(4, numberComparator);
    	rowSorter.setComparator(5, numberComparator);
    	rowSorter.setComparator(6, numberComparator);
    	rowSorter.setComparator(7, numberComparator);
    	rowSorter.setComparator(8, numberComparator);
    	rowSorter.setComparator(9, numberComparator);
    	

    	Vector<String> receiptheaderrow = SAOCRData.id2equipmentreceipt.get(headerRow_key);
    	for (int i = 0; i < list.size(); i++){
    		Vector<Object> row = new Vector<Object>();
    		row.add(list.get(i).get(0));
    		int index = SAOCRData.id2armor.get(headerRow_key).indexOf(header_name);
    		row.add(list.get(i).get(index));
    		
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("str_min");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("str_max");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("vit_min");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("vit_max");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("int_min");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("int_max");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("men_min");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2armor.get(headerRow_key).indexOf("men_max");
    		row.add(list.get(i).get(index));
    		Vector<String> receipt = SAOCRData.id2equipmentreceipt.get(list.get(i).get(0));
    		if (receipt != null){
	    		row.add(receipt.get(receiptheaderrow.indexOf(header_starttime)));
	    		row.add(receipt.get(receiptheaderrow.indexOf(header_endtime)));
    		}
    		else{
	    		row.add("---");
	    		row.add("---");
    		}
    		model.addRow(row);
    	}
    	
    	tbl_armorList.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			int row = tbl_armorList.rowAtPoint(e.getPoint());
    			if (row >= 0 && tbl_armorList.getRowHeight(row)>1){
    				for (int i = 0; i < tbl_armorList.getColumnCount(); i++){
	    				if ("ID".equals(tbl_armorList.getColumnName(i))){
		    				String id = (String) tbl_armorList.getValueAt(row, i);
		    				new ArmorDetailFrame(id);
		        			break;
	    				}
    				}
    			}
    		}
    	});
    	
    	JScrollPane scrollPane= new JScrollPane(tbl_armorList);
    	
    	frm_armorList.setBounds(90, 100, exfmWidth, exfmHeight);
    	frm_armorList.getContentPane().setLayout(new BorderLayout());
    	
    	frm_armorList.getContentPane().add(scrollPane, BorderLayout.CENTER);
//    	frm_extendList.setVisible(true);
    	frm_armorList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void showFrame(){
		frm_armorList.setVisible(true);
		frm_armorList.setState(Frame.MAXIMIZED_BOTH);
	}
	@SuppressWarnings("unused")
	private void commonFormatting(JComponent comp, String type){
		commonFormatting(comp);
		if (TYPE_CHKBOX.equals(type)){
			((AbstractButton) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		if (TYPE_LABEL.equals(type)){
			( (JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		comp.setOpaque(false);
	}
	private void commonFormatting(JComponent comp){
		comp.setBackground(Color.black);
		comp.setForeground(Color.white);
		comp.setFont(font);
		comp.setOpaque(true);
		if (comp == tbl_armorList){
	    	tbl_armorList.setFont(extablefont);
	    	tbl_armorList.setRowHeight(tableRowHeight);
		}
	}
	
}