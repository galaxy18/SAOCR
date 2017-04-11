package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

public class WeaponFrame implements Params{
	JFrame frm_weaponList = new JFrame("Weapon List");
	
	JTable tbl_weaponList;
	
	List<Vector<String>> list = new ArrayList<Vector<String>>();
	
	public WeaponFrame() throws Exception{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path_weapongroup), "UTF-8"));
    	String[] dataArray = reader.readNext();
		Vector<String> row = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			row.add(dataArray[s]);
		}
		SAOCRData.id2weapongroup.put(dataArray[0], row);
		
    	while((dataArray = reader.readNext()) != null) {
    		row = new Vector<String>();
    		for(int s = 0; s < dataArray.length; s++) {
    			row.add(dataArray[s]);
    		}
			if (!"".equals(dataArray[0].trim())){
				list.add(row);
			}
    		SAOCRData.id2weapongroup.put(dataArray[0], row);
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
		
    	tbl_weaponList = new JTable(model);
    	tbl_weaponList.getTableHeader().setDefaultRenderer(extable_header);
//    	tbl_extendList.setGridColor(Color.black);
    	commonFormatting(tbl_weaponList);
    	tbl_weaponList.setRowSorter(new TableRowSorter(model));
    	tbl_weaponList.setRowSelectionAllowed(true);
    	tbl_weaponList.setGridColor(Color.black);
    	tbl_weaponList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    	
        model.addColumn("ID");
        model.addColumn(header_name);
        model.addColumn("");
        model.addColumn(header_starttime);
        model.addColumn(header_endtime);
        
    	int[] colwidth = {80, 300, 90, 180, 180};
    	for (int i = 0; i < colwidth.length; i++){
    		tbl_weaponList.getColumnModel().getColumn(i).setPreferredWidth(colwidth[i]);
    	}
    	tbl_weaponList.getColumnModel().getColumn(0).setCellRenderer(digitalCell);

    	Vector<String> receiptheaderrow = SAOCRData.id2equipmentreceipt.get(headerRow_key);
    	for (int i = 0; i < list.size(); i++){
    		Vector<Object> row = new Vector<Object>();
    		row.add(list.get(i).get(0));
    		int index = SAOCRData.id2weapongroup.get(headerRow_key).indexOf(header_name);
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2weapongroup.get(headerRow_key).indexOf(header_weaponcategory);
    		row.add(SAOCRData.id2weaponcategory.get(list.get(i).get(index)));
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
    	
    	tbl_weaponList.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			int row = tbl_weaponList.rowAtPoint(e.getPoint());
    			if (row >= 0 && tbl_weaponList.getRowHeight(row)>1){
    				for (int i = 0; i < tbl_weaponList.getColumnCount(); i++){
	    				if ("ID".equals(tbl_weaponList.getColumnName(i))){
		    				String id = (String) tbl_weaponList.getValueAt(row, i);
		    				new WeaponDetailFrame(id);
		        			break;
	    				}
    				}
    			}
    		}
    	});
    	
    	JScrollPane scrollPane= new JScrollPane(tbl_weaponList);
    	
    	frm_weaponList.setBounds(90, 100, exfmWidth, exfmHeight);
    	frm_weaponList.getContentPane().setLayout(new BorderLayout());
    	
    	frm_weaponList.getContentPane().add(scrollPane, BorderLayout.CENTER);
//    	frm_extendList.setVisible(true);
    	frm_weaponList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void showFrame(){
		frm_weaponList.setVisible(true);
		frm_weaponList.setState(Frame.MAXIMIZED_BOTH);
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
		if (comp == tbl_weaponList){
	    	tbl_weaponList.setFont(extablefont);
	    	tbl_weaponList.setRowHeight(tableRowHeight);
		}
	}
	
}