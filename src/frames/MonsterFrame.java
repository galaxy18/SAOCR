package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import objects.SAOCRData;

import tableRenderer.DigitalCellRenderer;
import tableRenderer.ElementCellRenderer;
import tableRenderer.HeaderCellRenderer;
import com.opencsv.CSVReader;
import configs.Params;

public class MonsterFrame implements Params{
	JFrame frm_monsterList = new JFrame("Monster List");
	
	JTable tbl_monsterList;
	
	List<Vector<String>> list = new ArrayList<Vector<String>>();
	
	public MonsterFrame() throws Exception{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path_monster), "UTF-8"));
    	String[] dataArray = reader.readNext();
		Vector<String> row = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			row.add(dataArray[s]);
		}
		SAOCRData.id2monster.put(dataArray[0], row);
		
    	while((dataArray = reader.readNext()) != null) {
    		row = new Vector<String>();
    		for(int s = 0; s < dataArray.length; s++) {
    			row.add(dataArray[s]);
    		}
			if (!"".equals(dataArray[0].trim())){
				list.add(row);
			}
    		SAOCRData.id2monster.put(dataArray[0], row);
    	}
    	reader.close();
    	Comparator<Vector<String>> comparator = new Comparator<Vector<String>>() {
    	    public int compare(Vector<String> c1, Vector<String> c2) {
    	        return Integer.parseInt(c1.get(0)) - Integer.parseInt(c2.get(0));
    	    }
    	};

    	Collections.sort(list, comparator);
    	
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
        
		DefaultTableCellRenderer centerCell = new DefaultTableCellRenderer();
		centerCell.setHorizontalAlignment(JLabel.CENTER);
		DigitalCellRenderer digitalCell = new DigitalCellRenderer();
		DigitalCellRenderer digital_centerCell = new DigitalCellRenderer();
		DigitalCellRenderer digital_rightCell = new DigitalCellRenderer();
		digital_centerCell.setHorizontalAlignment(JLabel.CENTER);
		digital_rightCell.setHorizontalAlignment(JLabel.RIGHT);

		ElementCellRenderer elementCell = new ElementCellRenderer();
		elementCell.setType(TYPE_MONSTER);
		HeaderCellRenderer extable_header = new HeaderCellRenderer();
		
    	tbl_monsterList = new JTable(model);
    	tbl_monsterList.getTableHeader().setDefaultRenderer(extable_header);
//    	tbl_extendList.setGridColor(Color.black);
    	commonFormatting(tbl_monsterList);
    	tbl_monsterList.setRowSorter(new TableRowSorter(model));
    	tbl_monsterList.setRowSelectionAllowed(true);
//    	tbl_monsterList.setGridColor(Color.black);
    	tbl_monsterList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	TableRowSorter rowSorter = (TableRowSorter) tbl_monsterList.getRowSorter();
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
    	
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("ELEMENT");
        model.addColumn("HP");
        model.addColumn("STR");
        model.addColumn("VIT");
        model.addColumn("INT");
        model.addColumn("MEN");
        model.addColumn("D");				//attack_distance_type
        model.addColumn("m_unit_skill_id");
        model.addColumn("P");
        model.addColumn("Rate");
        model.addColumn("m_monster_unit_skill_change_id_1");
        model.addColumn("P");
        model.addColumn("Rate");
        model.addColumn("Num");
        model.addColumn("m_monster_unit_skill_change_id_2");
        model.addColumn("P");
        model.addColumn("Rate");
        model.addColumn("Num");
        model.addColumn("m_monster_unit_skill_change_id_3");
        model.addColumn("P");
        model.addColumn("Rate");
        model.addColumn("Num");
        model.addColumn("m_monster_unit_skill_change_id_4");
        model.addColumn("P");
        model.addColumn("Rate");
        model.addColumn("Num");
//        model.addColumn("m_monster_unit_skill_change_id_5");
//        model.addColumn("P");
//        model.addColumn("unit_skill_change_5_perform_rate");
//        model.addColumn("unit_skill_change_5_perform_num");
        
    	int[] colwidth = {80, 200, 20,
    			45, 45, 45, 45, 45,
    			10,
    			250, 50, 50,	//SS
    			250, 50, 50, 50,
    			250, 50, 50, 50,
    			250, 50, 50, 50,
    			250, 50, 50, 50,
//    			250, 50, 50, 50
    			};
    	for (int i = 0; i < colwidth.length; i++){
    		tbl_monsterList.getColumnModel().getColumn(i).setPreferredWidth(colwidth[i]);
    	}
    	tbl_monsterList.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
    	tbl_monsterList.getColumnModel().getColumn(2).setCellRenderer(elementCell);
    	tbl_monsterList.getColumnModel().getColumn(3).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(4).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(5).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(6).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(7).setCellRenderer(digital_rightCell);
    	
    	tbl_monsterList.getColumnModel().getColumn(8).setCellRenderer(digitalCell);
    	
    	tbl_monsterList.getColumnModel().getColumn(10).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(11).setCellRenderer(digital_rightCell);
    	
    	tbl_monsterList.getColumnModel().getColumn(13).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(14).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(15).setCellRenderer(digital_rightCell);
    	
    	tbl_monsterList.getColumnModel().getColumn(17).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(18).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(19).setCellRenderer(digital_rightCell);
    	
    	tbl_monsterList.getColumnModel().getColumn(21).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(22).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(23).setCellRenderer(digital_rightCell);
    	
    	tbl_monsterList.getColumnModel().getColumn(25).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(26).setCellRenderer(digital_rightCell);
    	tbl_monsterList.getColumnModel().getColumn(27).setCellRenderer(digital_rightCell);

    	rowSorter.setComparator(0, numberComparator);
    	rowSorter.setComparator(3, numberComparator);
    	rowSorter.setComparator(4, numberComparator);
    	rowSorter.setComparator(5, numberComparator);
    	rowSorter.setComparator(6, numberComparator);
    	rowSorter.setComparator(7, numberComparator);
        
		String[] ba_headers = {
				"m_monster_unit_skill_change_id_1","unit_skill_change_1_perform_rate","unit_skill_change_1_perform_num",
				"m_monster_unit_skill_change_id_2","unit_skill_change_2_perform_rate","unit_skill_change_2_perform_num",
				"m_monster_unit_skill_change_id_3","unit_skill_change_3_perform_rate","unit_skill_change_3_perform_num",
				"m_monster_unit_skill_change_id_4","unit_skill_change_4_perform_rate","unit_skill_change_4_perform_num",
//				"m_monster_unit_skill_change_id_5","unit_skill_change_5_perform_rate","unit_skill_change_5_perform_num"
				};
		
    	for (int i = 0; i < list.size(); i++){
    		Vector<Object> row = new Vector<Object>();
    		row.add(list.get(i).get(0));
    		int index = SAOCRData.id2monster.get(headerRow_key).indexOf(header_name);
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf(header_element);
			int Element = Integer.parseInt(list.get(i).get(index));
			if (Element <= 3){
				row.add(elementtextlist[Element-1]);
			}
			else{
				row.add("");
			}
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("hp");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("str");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("vit");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("int");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("men");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("attack_distance_type");
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("m_unit_skill_id");
			if (list.get(i).get(index) != null && !"".equals(list.get(i).get(index))){
				String key = list.get(i).get(index);
				if (SAOCRData.id2unitskill.containsKey(key)){
					int index2 = SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_description);
					row.add(SAOCRData.id2unitskill.get(key).get(index2));
					index2 = SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_power);
					row.add(SAOCRData.id2unitskill.get(key).get(index2));
				}
				else{
					row.add("???");
					row.add("???");
				}
			}
			else{
				row.add("---");
				row.add("---");
			}
    		index = SAOCRData.id2monster.get(headerRow_key).indexOf("unit_skill_perform_rate");
    		row.add(list.get(i).get(index));
			for (int k = 0; k < ba_headers.length; k++){
				index = SAOCRData.id2monster.get(headerRow_key).indexOf(ba_headers[k]);
				if (list.get(i).get(index) != null && !"".equals(list.get(i).get(index))){
					String key = list.get(i).get(index);
					if (SAOCRData.id2unitskill.containsKey(key)){
						int index2 = SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_description);
						row.add(SAOCRData.id2unitskill.get(key).get(index2));
						index2 = SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_power);
						row.add(SAOCRData.id2unitskill.get(key).get(index2));
					}
					else{
						row.add("???");
						row.add("???");
					}
				}
				else{
					row.add("---");
					row.add("---");
				}
				k++;
				index = SAOCRData.id2monster.get(headerRow_key).indexOf(ba_headers[k]);
				row.add(list.get(i).get(index));
				k++;
				index = SAOCRData.id2monster.get(headerRow_key).indexOf(ba_headers[k]);
				row.add(list.get(i).get(index));
			}

    		model.addRow(row);
    	}
    	
    	JScrollPane scrollPane= new JScrollPane(tbl_monsterList);
    	
    	frm_monsterList.setBounds(90, 100, exfmWidth, exfmHeight);
    	frm_monsterList.getContentPane().setLayout(new BorderLayout());
    	
    	frm_monsterList.getContentPane().add(scrollPane, BorderLayout.CENTER);
//    	frm_extendList.setVisible(true);
    	frm_monsterList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void showFrame(){
		frm_monsterList.setVisible(true);
		frm_monsterList.setState(Frame.MAXIMIZED_BOTH);
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
		if (comp == tbl_monsterList){
	    	tbl_monsterList.setFont(extablefont);
	    	tbl_monsterList.setRowHeight(tableRowHeight);
		}
	}
	
}