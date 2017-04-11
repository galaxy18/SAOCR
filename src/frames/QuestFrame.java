package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.DecimalFormat;
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

public class QuestFrame implements Params{
	JFrame frm_questList = new JFrame("Quest List");
	
	JTable tbl_questList;
	
	List<Vector<String>> list = new ArrayList<Vector<String>>();
	
	private int currentKey = 0;
	private String[] urlPatten1 = {
			"https://yui-production.s3.amazonaws.com/static/event/%s/q_%s_banner_detail.png",
			"https://yui-production.s3.amazonaws.com/static/event/%s/e_%s_banner_detail.png"
	};
	
	public QuestFrame() throws Exception{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path_quest), "UTF-8"));
    	String[] dataArray = reader.readNext();
		Vector<String> row = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			row.add(dataArray[s]);
		}
		SAOCRData.id2quest.put(dataArray[0], row);
		
    	while((dataArray = reader.readNext()) != null) {
    		row = new Vector<String>();
    		for(int s = 0; s < dataArray.length; s++) {
    			row.add(dataArray[s]);
    		}
			if (!"".equals(dataArray[0].trim())){
				list.add(row);
			}
    		SAOCRData.id2quest.put(dataArray[0], row);
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
		elementCell.setType(TYPE_QUEST);
		HeaderCellRenderer extable_header = new HeaderCellRenderer();
		
    	tbl_questList = new JTable(model);
    	tbl_questList.getTableHeader().setDefaultRenderer(extable_header);
//    	tbl_extendList.setGridColor(Color.black);
    	commonFormatting(tbl_questList);
    	tbl_questList.setRowSorter(new TableRowSorter(model));
    	tbl_questList.setRowSelectionAllowed(true);
    	tbl_questList.setGridColor(Color.black);
    	
    	TableRowSorter rowSorter = (TableRowSorter) tbl_questList.getRowSorter();
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
        model.addColumn("STAMINA");
        model.addColumn("EXP");
        model.addColumn("CEXP");
        model.addColumn("MONEY");
        model.addColumn("EXPRate");
        model.addColumn("CEXPRate");
        model.addColumn("MONEYRate");
        model.addColumn("ELEMENT");
        model.addColumn("GROUP");
        model.addColumn("STORY_ID");
        
    	int[] colwidth = {80, 340, 30,
    			40, 40, 50,
    			60, 60, 70,
    			50, 80, 80};
    	for (int i = 0; i < colwidth.length; i++){
    		tbl_questList.getColumnModel().getColumn(i).setPreferredWidth(colwidth[i]);
    	}
    	tbl_questList.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
    	tbl_questList.getColumnModel().getColumn(2).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(3).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(4).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(5).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(6).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(7).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(8).setCellRenderer(digital_rightCell);
    	tbl_questList.getColumnModel().getColumn(10).setCellRenderer(digitalCell);
    	rowSorter.setComparator(0, numberComparator);
    	rowSorter.setComparator(2, numberComparator);
    	rowSorter.setComparator(3, numberComparator);
    	rowSorter.setComparator(4, numberComparator);
    	rowSorter.setComparator(5, numberComparator);
    	rowSorter.setComparator(6, numberComparator);
    	rowSorter.setComparator(7, numberComparator);
    	rowSorter.setComparator(8, numberComparator);
    	rowSorter.setComparator(10, numberComparator);
    	tbl_questList.getColumnModel().getColumn(9).setCellRenderer(elementCell);
        
        DecimalFormat df = new DecimalFormat("#.00");
        
    	for (int i = 0; i < list.size(); i++){
    		Vector<Object> row = new Vector<Object>();
    		row.add(list.get(i).get(0));
    		int index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_name);
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_stamina);
    		row.add(list.get(i).get(index));
    		double stamina = Integer.parseInt(list.get(i).get(index));
    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_player_exp);
    		double player_exp = Integer.parseInt(list.get(i).get(index));
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_character_exp);
    		double chara_exp = Integer.parseInt(list.get(i).get(index));
    		row.add(list.get(i).get(index));
    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_money);
    		double money = Integer.parseInt(list.get(i).get(index));
    		row.add(list.get(i).get(index));
    		
    		if (stamina > 0){
    			row.add(df.format(player_exp/stamina));
    			row.add(df.format(chara_exp/stamina));
    			row.add(df.format(money/stamina));
    		}
    		else{
        		row.add(df.format(0));
        		row.add(df.format(0));
        		row.add(df.format(0));
    		}
    		
    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_element);
			int Element = Integer.parseInt(list.get(i).get(index));
			if (Element <= 3){
				row.add(elementtextlist[Element-1]);
			}
			else{
				row.add("");
			}
			
    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_quest_group);
    		row.add(list.get(i).get(index));

    		index = SAOCRData.id2quest.get(headerRow_key).indexOf(header_start_id);
    		int index2 = SAOCRData.id2quest.get(headerRow_key).indexOf(header_end_id);
    		double storyid1 = Integer.parseInt(list.get(i).get(index));
    		double storyid2 = Integer.parseInt(list.get(i).get(index2));
    		if (storyid1 != 0){
    			row.add(SAOCRData.id2story.get(list.get(i).get(index)));
    		}
    		else if (storyid2 != 0){
    			row.add(SAOCRData.id2story.get(list.get(i).get(index2)));
    		}
    		else{
    			row.add("");
    		}

    		model.addRow(row);
    	}
    	
    	JScrollPane scrollPane= new JScrollPane(tbl_questList);
    	
    	frm_questList.setBounds(90, 100, exfmWidth, exfmHeight);
    	frm_questList.getContentPane().setLayout(new BorderLayout());
    	
    	frm_questList.getContentPane().add(scrollPane, BorderLayout.CENTER);
//    	frm_extendList.setVisible(true);
    	frm_questList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	tbl_questList.addKeyListener(new KeyAdapter(){
    		public void keyPressed(KeyEvent e) {
    			currentKey = e.getKeyCode();
    		}
    		public void keyReleased(KeyEvent e) {
    			currentKey = 0;
    		}
    	});
    	tbl_questList.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			String id = "";
    			int row = tbl_questList.rowAtPoint(e.getPoint());
    			if (row >= 0){
    				for (int i = 0; i < tbl_questList.getColumnCount(); i++){
    					if ("GROUP".equals(tbl_questList.getColumnName(i))){
    						id = (String) tbl_questList.getValueAt(row, i);
    						break;
    					}
    				}
    	    		
    				if (currentKey == KeyEvent.VK_T){
						try {
							if (id.startsWith("401")){
								Desktop.getDesktop().browse(new URI(String.format(urlPatten1[0], id, id)));
							}
							else{
								Desktop.getDesktop().browse(new URI(String.format(urlPatten1[1], id, id)));
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
    				else{
    					try {
							MainMenu.pl2.reloadText((String) tbl_questList.getValueAt(row, 11));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    				}
    			}
    		}
    	});
	}
	public void showFrame(){
		frm_questList.setVisible(true);
		frm_questList.setState(Frame.MAXIMIZED_BOTH);
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
		if (comp == tbl_questList){
	    	tbl_questList.setFont(extablefont);
	    	tbl_questList.setRowHeight(tableRowHeight);
		}
	}
	
}