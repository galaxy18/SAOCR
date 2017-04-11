package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import objects.VoiceInfo;
import configs.ReadVoiceXML;
import configs.Params;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import tableRenderer.DigitalCellRenderer;
import tableRenderer.HeaderCellRenderer;

@SuppressWarnings("serial")
public class VoicePanel extends JPanel implements Params{
	private JPanel CRmainPanel;

	private JScrollPane btnPanel = new JScrollPane();
	
	private JTable tbl_battleAction = new JTable();
	private JTable tbl_commonAction = new JTable();
	private JTable tbl_normalAction = new JTable();
	private JTable tbl_Other = new JTable();
	
	private JLabel textField = new JLabel();
	
	private AudioStream voice;
	
	private String name = "kirito";

	public VoicePanel() {	
		commonFormatting(this);
		new ReadVoiceXML(name);
		CRinit();
	}

	public void showValue(String arg_name) {
		name = arg_name;
		
		File file = new File(voice_root + name);
		ReadVoiceXML new_conf = new ReadVoiceXML(name);
		if (file.exists()){
			new_conf.readFile();
		}
		
		DefaultTableModel model1 = (DefaultTableModel) tbl_battleAction.getModel();
		DefaultTableModel model2 = (DefaultTableModel) tbl_commonAction.getModel();
		DefaultTableModel model3 = (DefaultTableModel) tbl_normalAction.getModel();
		DefaultTableModel model4 = (DefaultTableModel) tbl_Other.getModel();
		
		while (tbl_battleAction.getRowCount() > 0){
			model1.removeRow(0);
		}
		while (tbl_commonAction.getRowCount() > 0){
			model2.removeRow(0);
		}
		while (tbl_normalAction.getRowCount() > 0){
			model3.removeRow(0);
		}
		while (tbl_Other.getRowCount() > 0){
			model4.removeRow(0);
		}
		
		File directory = new File(voice_root+name+voice_folder);
		
		ArrayList<File> files = new ArrayList<File>();  
		if (directory.isDirectory()) {  
            File[] fileArr = directory.listFiles();  
            for (int i = 0; i < fileArr.length; i++) {  
                File fileOne = fileArr[i];  
                files.add(fileOne);  
            }  
        }  
		for (int i = 0; i < files.size(); i++){
			Vector<Object> row = new Vector<Object>();
			String type = "normal";
			String id = files.get(i).getName();
			int index = id.lastIndexOf("_")+1;
			if (files.get(i).getPath().contains("_bat_")){
				index = id.indexOf("_bat_") + 5;
				type = "battle";
			}
			else if (files.get(i).getPath().contains("_com_")){
				index = id.indexOf("_com_") + 5;
				type = "common";
			}
			try{
				id = id.substring(index,index+8);
				while(id.startsWith("0") && id.length() > 1){
					id = id.substring(1);
				}
				id = String.valueOf(Integer.valueOf(id, 16));
//			CRButton loc_btn = new CRButton();

				String voiceID = Integer.toHexString(Integer.valueOf(id));
				while (voiceID.length() < 8){
					voiceID = "0"+voiceID;
				}
				row.add(voiceID);
			}
			catch (Exception e){
				type = "other";
			}
			if ("battle".equals(type)){
				VoiceInfo loc_action = ReadVoiceXML.getInstance().getBattleActionByID(id);
				if (loc_action == null){
					loc_action = new VoiceInfo();
					loc_action.setValue(FLAG_BATTLE_ACTION_ID, id);
					loc_action.setValue(FLAG_BATTLE_ACTION_NAME, "");
					loc_action.setValue(FLAG_BATTLE_ACTION_TEXT, "");
				}
				if ("".equals(loc_action.getValue(FLAG_BATTLE_ACTION_NAME)))
					row.add("Battle"+String.valueOf(id));
				else
					row.add(loc_action.getValue(FLAG_BATTLE_ACTION_NAME));
				if ("".equals(loc_action.getValue(FLAG_BATTLE_ACTION_TEXT)))
					row.add("");
				else
					row.add(loc_action.getValue(FLAG_BATTLE_ACTION_TEXT));
				model1.addRow(row);
			}
			else if ("common".equals(type)){
				VoiceInfo loc_action = ReadVoiceXML.getInstance().getCommonActionByID(id);
				if (loc_action == null){
					loc_action = new VoiceInfo();
					loc_action.setValue(FLAG_COMMON_ACTION_ID, id);
					loc_action.setValue(FLAG_COMMON_ACTION_NAME, "");
					loc_action.setValue(FLAG_COMMON_ACTION_TEXT, "");
				}
				if ("".equals(loc_action.getValue(FLAG_COMMON_ACTION_NAME)))
					row.add("Common"+String.valueOf(id));
				else
					row.add(loc_action.getValue(FLAG_COMMON_ACTION_NAME));
				if ("".equals(loc_action.getValue(FLAG_COMMON_ACTION_TEXT)))
					row.add("");
				else
					row.add(loc_action.getValue(FLAG_COMMON_ACTION_TEXT));
				model2.addRow(row);
			}
			else if ("normal".equals(type)){
				VoiceInfo loc_action = ReadVoiceXML.getInstance().getNormalActionByID(id);
				if (loc_action == null){
					loc_action = new VoiceInfo();
					loc_action.setValue(FLAG_NORMAL_ACTION_ID, id);
					loc_action.setValue(FLAG_NORMAL_ACTION_NAME, "");
					loc_action.setValue(FLAG_NORMAL_ACTION_TEXT, "");
				}
				if ("".equals(loc_action.getValue(FLAG_NORMAL_ACTION_NAME)))
					row.add("Normal"+String.valueOf(id));
				else
					row.add(loc_action.getValue(FLAG_NORMAL_ACTION_NAME));
				if ("".equals(loc_action.getValue(FLAG_NORMAL_ACTION_TEXT)))
					row.add("");
				else
					row.add(loc_action.getValue(FLAG_NORMAL_ACTION_TEXT));
				model3.addRow(row);
			}
			else{
				id = files.get(i).getName().substring(0,files.get(i).getName().length() - 4);
				VoiceInfo loc_action = ReadVoiceXML.getInstance().getOtherActionByID(id);
				if (loc_action == null){
					loc_action = new VoiceInfo();
					loc_action.setValue(FLAG_NORMAL_ACTION_ID, id);
					loc_action.setValue(FLAG_NORMAL_ACTION_NAME, "");
					loc_action.setValue(FLAG_NORMAL_ACTION_TEXT, "");
				}
				row.add(files.get(i).getName());
				if ("".equals(loc_action.getValue(FLAG_OTHER_NAME)))
					row.add("");
				else
					row.add(loc_action.getValue(FLAG_OTHER_NAME));
				if ("".equals(loc_action.getValue(FLAG_OTHER_TEXT)))
					row.add("");
				else
					row.add(loc_action.getValue(FLAG_OTHER_TEXT));
				model4.addRow(row);
			}
		}
		btnPanel.setViewportView(tbl_Other);
	}
	
	public void CRinit(){
		CRmainPanel = new JPanel(new BorderLayout());

		HeaderCellRenderer table_header = new HeaderCellRenderer();
		DefaultTableModel model1 = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		DefaultTableModel model2 = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		DefaultTableModel model3 = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		DefaultTableModel model4 = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		DigitalCellRenderer digitalCell = new DigitalCellRenderer();
		
		tbl_battleAction = new JTable(model1);
		tbl_commonAction = new JTable(model2);
		tbl_normalAction = new JTable(model3);
		tbl_Other = new JTable(model4);
		
		commonFormatting(tbl_battleAction);
		model1.addColumn("ID");
		model1.addColumn("Name");
		model1.addColumn("Text");
		tbl_battleAction.getTableHeader().setDefaultRenderer(table_header);
		tbl_battleAction.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
		
		commonFormatting(tbl_commonAction);
		model2.addColumn("ID");
		model2.addColumn("Name");
		model2.addColumn("Text");
		tbl_commonAction.getTableHeader().setDefaultRenderer(table_header);
		tbl_commonAction.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
		
		commonFormatting(tbl_normalAction);
		model3.addColumn("ID");
		model3.addColumn("Name");
		model3.addColumn("Text");
		tbl_normalAction.getTableHeader().setDefaultRenderer(table_header);
		tbl_normalAction.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
		
		commonFormatting(tbl_Other);
		model4.addColumn("ID");
		model4.addColumn("Name");
		model4.addColumn("Text");
		tbl_Other.getTableHeader().setDefaultRenderer(table_header);
		tbl_Other.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
		
		commonFormatting(btnPanel);
		
		JLabel showNormal = new JLabel("Normal Action");
		showNormal.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				btnPanel.setViewportView(tbl_normalAction);
			}
		});
		JLabel showBattle = new JLabel("Battle Action");
		showBattle.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				btnPanel.setViewportView(tbl_battleAction);
			}
		});
		JLabel showCommon = new JLabel("Common Action");
		showCommon.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				btnPanel.setViewportView(tbl_commonAction);
			}
		});
		JLabel showOther = new JLabel("Other");
		showOther.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				btnPanel.setViewportView(tbl_Other);
			}
		});

		tbl_battleAction.addMouseListener(new voiceTableListener("battleAction"));
		tbl_commonAction.addMouseListener(new voiceTableListener("commonAction"));
		tbl_normalAction.addMouseListener(new voiceTableListener("normalAction"));
		tbl_Other.addMouseListener(new voiceTableListener("Other"));
		
		JPanel pagePanel = new JPanel(new GridLayout(1,4));
		commonFormatting(showNormal);
		commonFormatting(showBattle);
		commonFormatting(showCommon);
		commonFormatting(showOther);
		pagePanel.add(showNormal);
		pagePanel.add(showBattle);
		pagePanel.add(showCommon);
		pagePanel.add(showOther);
		commonFormatting(pagePanel);
		
		btnPanel.setViewportView(tbl_battleAction);
		
		CRmainPanel.add(pagePanel, BorderLayout.NORTH);
		CRmainPanel.add(btnPanel, BorderLayout.CENTER);
		
		textField.setFont(infofont);
		textField.setForeground(Color.white);
    	textField.setText("");
    	
		this.setLayout(new BorderLayout());
		this.add(CRmainPanel, BorderLayout.CENTER);
		this.add(textField, BorderLayout.SOUTH);
	}
	
	private void playSound(String path){
		if (path == null)
			return;
		
		AudioPlayer.player.stop(voice);
		try {
			InputStream in = new FileInputStream (path);
			voice = new AudioStream (in);
			AudioPlayer.player.start(voice);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void commonFormatting(JComponent comp){
		comp.setBackground(Color.black);
		comp.setForeground(Color.white);
		comp.setFont(digitalfont);
		comp.setOpaque(true);
		if (comp instanceof JTable){
			((JTable) comp).setRowHeight(tableRowHeight);
			((JTable) comp).setFont(font);
		}
	}
	
	class voiceTableListener extends MouseAdapter{
		String page = "";
		public voiceTableListener(String arg_page){
			super();
			page = arg_page;
		}
		public void mousePressed(MouseEvent e) {
			JTable table = new JTable();
			if (page.equals("battleAction")){
				table = tbl_battleAction;
			}
			else if (page.equals("commonAction")){
				table = tbl_commonAction;
			}
			else if (page.equals("normalAction")){
				table = tbl_normalAction;
			}
			else if (page.equals("Other")){
				table = tbl_Other;
			}
			int row = table.rowAtPoint(e.getPoint());
			if (row >= 0){
				for (int i = 0; i < table.getColumnCount(); i++){
    				if ("ID".equals(table.getColumnName(i))){
	    				String voiceID = (String) table.getValueAt(row, i);
	    				String path = null;
	    				if (page.equals("battleAction")){
	    					path = voice_root+name+voice_folder+"VO_"+name+"_bat_"+voiceID+".wav";
	    				}
	    				if (page.equals("commonAction")){
	    					path = voice_root+name+voice_folder+"VO_"+name+"_com_"+voiceID+".wav";
	    				}
	    				if (page.equals("normalAction")){
	    					path = voice_root+name+voice_folder+"VO_"+name+"_"+voiceID+".wav";
	    				}
	    				if (page.equals("Other")){
	    					path = voice_root+name+voice_folder+voiceID;
	    				}
	    				playSound(path);
    				}
    				if ("Text".equals(table.getColumnName(i))){
    					textField.setText("<html><body>"+(String) table.getValueAt(row, i)+"</body></html>");
//    					textField.setText((String) table.getValueAt(row, i));
    					textField.revalidate();
    					textField.repaint();
    				}
				}
			}
		}
	}
}
