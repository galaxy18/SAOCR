package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import others.ExtractImage;

import objects.MyFileFilter;
import objects.SAOCRData;

import tableRenderer.*;

import com.opencsv.CSVReader;
import com.sun.image.codec.jpeg.*;

import components.RareCheckBox;
import components.StatusPanel;

import configs.Params;

public class MainFrame implements Params{
	JFrame frame = new JFrame("SAOCR"+Version);
	JFrame frm_extendList = new JFrame("Select Character");
	//JFrame frm_iconList = new JFrame("Select Character");
	
	JPanel splitPane = new JPanel();
	JPanel iconPane = new JPanel();
	
	JTable tbl_List;
	JTable tbl_extendList;

	JScrollPane scrollPane2= new JScrollPane();
	static JPanel pan_charaData = new JPanel();
	JPanel pan_option = new JPanel();
	JPanel pan_exoption = new JPanel();
	
	JPanel pan_list = new JPanel();
	JPanel pan_ex = new JPanel();

	JLabel lbl_capture = new JLabel("CAPTURE");
	JPanel pan_scroll = new JPanel(new GridLayout(1,3));
	JLabel lbl_scrollLeft = new JLabel("<<");
	JLabel lbl_scrollRight = new JLabel(">>");
	JLabel lbl_scroll = new JLabel("< >");
	JLabel lbl_move = new JLabel("MOVE");
	JLabel lbl_extend = new JLabel("[+]EXT");

	JPanel pan_bar = new JPanel();
	JLabel lbl_close = new JLabel("[-]MIN");
	
//	boolean showIcon = false;
//	JCheckBox chk_showIcon = new JCheckBox("");
	
	boolean maximum = false;
	
	boolean hideWorld1 = false;
	boolean hideWorld2 = false;
	boolean hideWorld3 = false;
	boolean hideWorld4 = false;
	boolean hideNORMAL = false;
	boolean hideAWAKE = true;
	boolean hideRare1 = true;
	boolean hideRare2 = true;
	boolean hideRare3 = true;
	boolean hideRare4 = true;
	boolean hideRare5 = false;
	boolean hideRare6 = false;
	boolean hideRare7 = false;
	boolean hideRare8 = false;
	boolean hideType1 = false;
	boolean hideType2 = true;
	boolean hideType3 = true;
	boolean hideType4 = true;
	boolean hideElement1 = false;
	boolean hideElement2 = false;
	boolean hideElement3 = false;
	
	Vector<JCheckBox> chk_options = new Vector<JCheckBox>();
	JCheckBox world1 = new JCheckBox(world_SAO, !hideWorld1);
	JCheckBox world2 = new JCheckBox(world_ALO, !hideWorld2);
	JCheckBox world3 = new JCheckBox(world_GGO, !hideWorld3);
	JCheckBox world4 = new JCheckBox(world_OTHER, !hideWorld4);
	JCheckBox world1ex = new JCheckBox(world_SAO, !hideWorld1);
	JCheckBox world2ex = new JCheckBox(world_ALO, !hideWorld2);
	JCheckBox world3ex = new JCheckBox(world_GGO, !hideWorld3);
	JCheckBox world4ex = new JCheckBox(world_OTHER, !hideWorld4);
	JCheckBox chk_normal = new JCheckBox(non_awake, !hideNORMAL);
	JCheckBox chk_awake = new JCheckBox(filter_AWAKE, !hideAWAKE);

	RareCheckBox rare1 = new RareCheckBox("★1", !hideRare1);
	RareCheckBox rare2 = new RareCheckBox("★2", !hideRare2);
	RareCheckBox rare3 = new RareCheckBox("★3", !hideRare3);
	RareCheckBox rare4 = new RareCheckBox("★4", !hideRare4);
	RareCheckBox rare5 = new RareCheckBox("★5", !hideRare5);
	RareCheckBox rare6 = new RareCheckBox("★6", !hideRare6);
	RareCheckBox rare7 = new RareCheckBox("★7", !hideRare7);
	RareCheckBox rare8 = new RareCheckBox("★8", !hideRare8);
	RareCheckBox rare1ex = new RareCheckBox("★1", !hideRare1);
	RareCheckBox rare2ex = new RareCheckBox("★2", !hideRare2);
	RareCheckBox rare3ex = new RareCheckBox("★3", !hideRare3);
	RareCheckBox rare4ex = new RareCheckBox("★4", !hideRare4);
	RareCheckBox rare5ex = new RareCheckBox("★5", !hideRare5);
	RareCheckBox rare6ex = new RareCheckBox("★6", !hideRare6);
	RareCheckBox rare7ex = new RareCheckBox("★7", !hideRare7);
	RareCheckBox rare8ex = new RareCheckBox("★8", !hideRare8);

	JCheckBox type1 = new JCheckBox(typetextlist[0], !hideType1);
	JCheckBox type2 = new JCheckBox(typetextlist[1], !hideType2);
	JCheckBox type3 = new JCheckBox(typetextlist[2], !hideType3);
	JCheckBox type4 = new JCheckBox(typetextlist[3], !hideType4);
	
	JCheckBox element1 = new JCheckBox(elementtextlist[0], !hideElement1);
	JCheckBox element2 = new JCheckBox(elementtextlist[1], !hideElement2);
	JCheckBox element3 = new JCheckBox(elementtextlist[2], !hideElement3);
	
	List<Vector<String>> list = new ArrayList<Vector<String>>();
	
	private String ssSaveID = "screenshot";
//	private boolean showChara = false;
	private int currentKey = 0;
	private String[] urlPatten1 = {
			//"https://yui-production.s3.amazonaws.com/static/images/character/thumbnail/%s1.png",
			"http://yui-production.s3.amazonaws.com/static/images/character/thumbnail/web_%s1.png",
			"http://yui-production.s3.amazonaws.com/static/images/gacha/character/%s.png",
			"http://yui-production.s3.amazonaws.com/static/images/gacha/gacha_first/%2$s-%3$s/%1$s.png"};
	private String[] urlPatten2 = {
			"http://s3-ap-northeast-1.amazonaws.com/yui-production/assetbundle/android/character/%s.assetbundle"
	};
	
	String temp_id = "";
	
	public MainFrame() throws Exception{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path_charakind), "UTF-8"));
    	String[] dataArray = reader.readNext();
		Vector<String> row2 = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			row2.add(dataArray[s]);
		}
		SAOCRData.id2chara.put(dataArray[0], row2);
		int index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name_header);
		int index2 = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name);
    	while((dataArray = reader.readNext()) != null) {
    		Vector<String> row = new Vector<String>();
    		//head_name+long_name
    		row.add(dataArray[0]);
    		if (dataArray.length < index || dataArray.length < index2){
    			System.out.println(dataArray[0]+":"+dataArray);
    			row.add("");
    		}
    		else{
    			row.add(dataArray[index]+dataArray[index2]);
    		}
    		
    		row2 = new Vector<String>();
    		for(int s = 0; s < dataArray.length; s++) {
    			row2.add(dataArray[s]);
    		}
    		if (!"".equals(dataArray[0].trim())){
    			list.add(row);
    			SAOCRData.id2chara.put(dataArray[0], row2);
    		}
    	}
    	reader.close();
    	Comparator<Vector<String>> comparator = new Comparator<Vector<String>>() {
    	    public int compare(Vector<String> c1, Vector<String> c2) {
    	        return Integer.parseInt(c1.get(0)) - Integer.parseInt(c2.get(0));
    	    }
    	};

    	Collections.sort(list, comparator);
    	
    	createFrame();
		createExtendFrame();
//		showFrame();
		hideTableRow();
		
		loadData();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		if (screenSize.height-insets.bottom <= 700)
			frame.setBounds(0, 0, screenSize.width, 700);
		else if (screenSize.height-insets.bottom < 800)
			frame.setBounds(0, 0, screenSize.width, screenSize.height-insets.bottom);
		else
			frame.setBounds(0, (screenSize.height-insets.bottom-800)/2, screenSize.width, 800);
//    	splitPane.setDividerLocation(0);
	}
	
	@SuppressWarnings("unchecked")
	private void loadData() {
		Vector<Vector<String>> data = (Vector<Vector<String>>) SAOCRData.savedata.clone();
		SAOCRData.savedata.clear();
		SAOCRData.savedata.add(data.get(0));
		for (int i = data.size()-1; i > 0; i--){
			Vector<String> row = data.get(i);
			String id = row.get(0).substring(0,8);
			String type = row.get(0).substring(8,9);
			boolean showdesc = false;
			if (row.size() > 1)
				showdesc = "1".equals(row.get(1));
			StatusPanel panel = addStatusPanel(id, false);
			panel.changeType(type);
			if (showdesc)
				panel.switchContent();
			int length = pan_charaData.getComponents().length;
			for (int j=0; j < length; j++){
				if (pan_charaData.getComponents()[j] == panel){
					String id2 = SAOCRData.savedata.get(length-j).get(0).substring(0,8);
					SAOCRData.savedata.get(length-j).set(0, id2+type);
					SAOCRData.savedata.get(length-j).set(1, showdesc?"1":"0");
					break;
				}
			}
			data.remove(i);
		}
	}
	
	public static void saveType(Component panel, String type){
		int length = pan_charaData.getComponents().length;
		for (int i=0; i < length; i++){
			if (pan_charaData.getComponents()[i] == panel){
				String id2 = SAOCRData.savedata.get(length-i).get(0).substring(0,8);
				SAOCRData.savedata.get(length-i).set(0, id2+type);
			}
		}
	}

	public void createFrame(){
		@SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		HeaderCellRenderer table_header = new HeaderCellRenderer();
    	tbl_List = new JTable(model);
    	tbl_List.setGridColor(new Color(0,0,0,190));
    	tbl_List.getTableHeader().setDefaultRenderer(table_header);
    	commonFormatting(tbl_List);
    	tbl_List.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
    	
		model.addColumn("ID");
		model.addColumn("Name");
		
    	tbl_List.getColumnModel().getColumn(0).setPreferredWidth(100);
    	tbl_List.getColumnModel().getColumn(0).setMaxWidth(100);
    	tbl_List.getColumnModel().getColumn(0).setMinWidth(100);
    	
    	tbl_List.setCellSelectionEnabled(true);
    	ListSelectionModel cellSelectionModel = tbl_List.getSelectionModel();
    	cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    	tbl_List.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			int row = tbl_List.rowAtPoint(e.getPoint());
    			if (row >= 0 && tbl_List.getRowHeight(row)>1){
    				for (int i = 0; i < tbl_List.getColumnCount(); i++){
	    				if ("ID".equals(tbl_List.getColumnName(i))){
		    				String id = (String) tbl_List.getValueAt(row, i);
		    				if (e.getClickCount() == 3 || e.getButton()== MouseEvent.BUTTON3) {
		    					ssSaveID = id;
		    					addStatusPanel(id, true);
		    				}
		    				else{
			    				addStatusPanel(id, false);
		    				}
		    				break;
	    				}
    				}
    			}
    			pan_charaData.revalidate();
    			pan_charaData.repaint();
    		}
    	});

    	for (int i = 0; i < list.size(); i++){
    		if (list.get(i).get(0).charAt(list.get(i).get(0).length()-1) == '0'){
    			model.addRow(list.get(i));
    		}
    	}
//    	frame.setVisible(true);
//    	frame.setSize(fmWidth, fmHeight);
    	
    	JScrollPane scrollPane= new JScrollPane(tbl_List);
    	commonFormatting(scrollPane);
    	
    	pan_option.setLayout(new GridLayout(3,1));
    	commonFormatting(pan_option);

    	pan_ex.setLayout(new GridLayout(1,4));
    	commonFormatting(pan_ex);
    	
//    	commonFormatting(chk_showIcon, TYPE_CHKBOX);
//    	chk_showIcon.addMouseListener(new MouseAdapter(){
//    		public void mouseClicked(MouseEvent e) {
//    			if (tbl_extendList == null)
//    				return;
//    			
//    			showIcon = chk_showIcon.isSelected();
//    			if (showIcon && tbl_extendList != null){
//    				tbl_extendList.setRowHeight(extableRowHeight);
//    			}
//    			else{
//    				tbl_extendList.setRowHeight(tableRowHeight);
//    			}
//    			hideTableRow();
//    		}
//    	});
    	
    	commonFormatting(lbl_capture, TYPE_LABEL);
    	lbl_capture.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			saveImage();
    		}
    	});
    	
    	commonFormatting(pan_scroll);
    	
    	commonFormatting(lbl_scrollLeft, TYPE_LABEL);
    	lbl_scrollLeft.setHorizontalAlignment(JLabel.CENTER);
    	lbl_scrollLeft.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			Point p = scrollPane2.getViewport().getViewPosition();
    			if (p.x % statusPanelWidth == 0 && p.x >= statusPanelWidth)
    				p.x -= statusPanelWidth;
    			p.x = p.x - (p.x % statusPanelWidth);
    			scrollPane2.getViewport().setViewPosition(new Point(p.x,0));
    		}
    	});
    	commonFormatting(lbl_scrollRight, TYPE_LABEL);
    	lbl_scrollRight.setHorizontalAlignment(JLabel.CENTER);
    	lbl_scrollRight.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			Point p = scrollPane2.getViewport().getViewPosition();
    			p.x -= p.x % statusPanelWidth;
    			scrollPane2.getViewport().setViewPosition(new Point(p.x+400,0));
    		}
    	});
    	commonFormatting(lbl_scroll, TYPE_LABEL);
    	lbl_scroll.setHorizontalAlignment(JLabel.CENTER);
    	setStatusPaneDragable(lbl_scroll);
    	lbl_scroll.addMouseWheelListener(new MouseWheelListener(){
    		public void mouseWheelMoved(MouseWheelEvent e){
    			if(e.getWheelRotation() == -1){
        			Point p = scrollPane2.getViewport().getViewPosition();
        			if (p.x % statusPanelWidth == 0 && p.x >= statusPanelWidth)
        				p.x -= statusPanelWidth;
        			p.x = p.x - (p.x % statusPanelWidth);
        			scrollPane2.getViewport().setViewPosition(new Point(p.x,0));
    			}
    			else{
        			Point p = scrollPane2.getViewport().getViewPosition();
        			p.x -= p.x % statusPanelWidth;
        			scrollPane2.getViewport().setViewPosition(new Point(p.x+400,0));
    			}
    		}
    	});
    	
    	commonFormatting(lbl_move, TYPE_LABEL);
    	lbl_move.setHorizontalAlignment(JLabel.CENTER);
    	setDragable(lbl_move);
		
    	commonFormatting(lbl_extend, TYPE_LABEL);
    	lbl_extend.addMouseListener(new MouseAdapter(){
    		public void mouseClicked(MouseEvent e) {
    			splitPane.remove(pan_bar);
    			pan_scroll.removeAll();
    	    	pan_scroll.add(lbl_scrollLeft);
    	    	pan_scroll.add(lbl_scroll);
    	    	pan_scroll.add(lbl_scrollRight);
    	    	
    	    	pan_ex.removeAll();
    	    	pan_ex.add(lbl_close);
    	    	pan_ex.add(lbl_capture);
    	    	pan_ex.add(lbl_move);
    	    	pan_ex.add(pan_scroll);
    	    	
    			splitPane.add(pan_list, BorderLayout.WEST);
    			splitPane.revalidate();
    		}
    	});
    	
    	commonFormatting(lbl_close, TYPE_LABEL);
    	lbl_close.addMouseListener(new MouseAdapter(){
    		public void mouseClicked(MouseEvent e) {
    			splitPane.remove(pan_list);
    			commonFormatting(pan_bar);
    			pan_bar.setOpaque(true);
    	    	pan_bar.setLayout(new GridLayout(6,1));
    	    	pan_bar.add(lbl_extend);
    	    	pan_bar.add(lbl_capture);
    	    	pan_bar.add(lbl_move);
    	    	pan_bar.add(lbl_scrollLeft);
    	    	pan_bar.add(lbl_scroll);
    	    	pan_bar.add(lbl_scrollRight);
    			splitPane.add(pan_bar, BorderLayout.WEST);
    			splitPane.revalidate();
    		}
    	});

    	pan_scroll.add(lbl_scrollLeft);
    	pan_scroll.add(lbl_scroll);
    	pan_scroll.add(lbl_scrollRight);
    	
    	pan_ex.add(lbl_close);
    	pan_ex.add(lbl_capture);
    	pan_ex.add(lbl_move);
    	pan_ex.add(pan_scroll);
    	
    	pan_option.add(pan_ex);
    	createWorldPanel(pan_option);
    	createRarePanel(pan_option);
    	pan_list.setLayout(new BorderLayout());
    	pan_list.add(pan_option, BorderLayout.NORTH);
    	pan_list.add(scrollPane, BorderLayout.CENTER);
    	pan_list.setPreferredSize(new Dimension(statusPanelWidth, 0));
    	pan_list.setMinimumSize(new Dimension(statusPanelWidth, 0));
    	commonFormatting(pan_list);
    	
    	pan_charaData.setLayout(new BoxLayout(pan_charaData, BoxLayout.X_AXIS));
    	scrollPane2.setViewportView(pan_charaData);
    	commonFormatting(pan_charaData);
    	pan_charaData.addComponentListener(new charaComponentListner());
    	commonFormatting(scrollPane2);
    	scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	
    	splitPane = new JPanel(new BorderLayout());
    	splitPane.add(pan_list, BorderLayout.WEST);
    	splitPane.add(scrollPane2, BorderLayout.CENTER);
//    			JSplitPane.HORIZONTAL_SPLIT, pan_list, scrollPane2);
//    	splitPane.setOneTouchExpandable(true);
    	
    	frame.getContentPane().add(splitPane);
    	pan_option.setOpaque(true);
    	pan_charaData.setOpaque(false);
    	splitPane.setOpaque(false);
    	scrollPane.getViewport().setOpaque(false);
    	scrollPane2.getViewport().setOpaque(false);
    	frame.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(frame,false);
    	((JPanel)frame.getContentPane()).setOpaque(false);
//    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	scrollPane2.setBorder(null);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createExtendFrame(){
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

		ImageCellRenderer iconCell = new ImageCellRenderer();
		WorldCellRenderer worldCell = new WorldCellRenderer();
		ElementCellRenderer elementCell = new ElementCellRenderer();
		TypeCellRenderer typeCell = new TypeCellRenderer();
		SkillTitleCellRenderer skillTitleCell = new SkillTitleCellRenderer();
		SkillPowerCellRenderer skillPowerCell = new SkillPowerCellRenderer();
		SwordSkillCellRenderer SSTitleCell = new SwordSkillCellRenderer();
		HeaderCellRenderer extable_header = new HeaderCellRenderer();
		
    	tbl_extendList = new JTable(model);
    	tbl_extendList.getTableHeader().setDefaultRenderer(extable_header);
//    	tbl_extendList.setGridColor(Color.black);
    	commonFormatting(tbl_extendList);
    	tbl_extendList.setRowSorter(new TableRowSorter(model));
    	tbl_extendList.setRowSelectionAllowed(true);
    	tbl_extendList.setCellSelectionEnabled(true);
    	ListSelectionModel cellSelectionModel = tbl_extendList.getSelectionModel();
    	cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	tbl_extendList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	tbl_extendList.addKeyListener(new KeyAdapter(){
    		public void keyPressed(KeyEvent e) {
//    			System.out.println("keyPressed"+e.getKeyCode());
    			currentKey = e.getKeyCode();
    		}
    		public void keyReleased(KeyEvent e) {
//    			System.out.println("keyReleased"+e.getKeyCode());
    			currentKey = 0;
    		}
    	});
    	
    	tbl_extendList.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
//				if (e.getClickCount() == 2 && e.getButton()==MouseEvent.BUTTON1) {
    			boolean awake = false;
    			StatusPanel p = null;
    			String id = "";
    			String type = "";
    			int row = tbl_extendList.rowAtPoint(e.getPoint());
    			if (row >= 0 && tbl_extendList.getRowHeight(row)>1){
    				for (int i = 0; i < tbl_extendList.getColumnCount(); i++){
    					if ("ID".equals(tbl_extendList.getColumnName(i))){
    						id = (String) tbl_extendList.getValueAt(row, i);
    						if (!id.endsWith("0")){
    							awake = true;
    						}
    						if (!id.endsWith("0") && SAOCRData.awake2id.containsKey(id)){
    							id = SAOCRData.awake2id.get(id);
    						}
    					}
    					else if ("Type".equals(tbl_extendList.getColumnName(i))){
    						type = (String) tbl_extendList.getValueAt(row, i);
    					}
    				}
//    				System.out.println("currentKey"+currentKey);
//    				System.out.println("KeyEvent.VK_T"+KeyEvent.VK_T);
					if (currentKey == KeyEvent.VK_T){
						try {
							Calendar nowCalendar = Calendar.getInstance();
							String month = nowCalendar.get(Calendar.MONTH) < 10?
										"0"+String.valueOf(nowCalendar.get(Calendar.MONTH)+1):
											String.valueOf(nowCalendar.get(Calendar.MONTH)+1);
							for (int i = 0; i < urlPatten1.length; i++){
//								System.out.println(String.format(urlPatten1[i], id, nowCalendar.get(Calendar.YEAR), month));
								Desktop.getDesktop().browse(new URI(String.format(urlPatten1[i], id, nowCalendar.get(Calendar.YEAR), month)));
							}
						} catch (Exception ex) {
						}
					}
					else if (currentKey == KeyEvent.VK_A){
						temp_id = id;
						
						Runnable getImage = new Runnable(){
							@Override
							public void run(){
								new ExtractImage(String.format(urlPatten2[0], temp_id), temp_id);
								currentKey = 0;
							}
						};
						Thread getImageThread = new Thread(getImage);
						getImageThread.start();
					}
					else if (frame.isVisible()){
						if (e.getClickCount() == 3 || e.getButton()== MouseEvent.BUTTON3) {
							ssSaveID = id;
							addStatusPanel(id, true);

							pan_charaData.revalidate();
							pan_charaData.repaint();
						}
						else{
							ssSaveID = "screenshot";
							p = addStatusPanel(id, false);
						}

    					scrollPane2.getViewport().setViewPosition(new Point(0,0));
    					if (p == null) return;

						for(int j = 0; j < typetextlist.length; j++){
							if (typetextlist[j].equals(type)){
								p.changeType(String.valueOf(j+1));
								int length = pan_charaData.getComponents().length;
								for (int k=0; k < length; k++){
									if (pan_charaData.getComponents()[k] == p){
										String id2 = SAOCRData.savedata.get(length-k).get(0).substring(0,8);
										SAOCRData.savedata.get(length-k-1).set(0, id2+String.valueOf(j+1));
										break;
									}
								}
								break;
							}
						}
						if (!awake){
							p.setAwake(false);
						}
    					pan_charaData.revalidate();
    				}
    			}
    		}
    	});
    	
    	TableRowSorter rowSorter = (TableRowSorter) tbl_extendList.getRowSorter();
        Comparator<String> numberComparator = new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                if ( o1 == null || "".equals(o1)) {  
                    return -1;  
                }  
                if ( o2 == null || "".equals(o2)) {  
                    return 1;
                }
                if (o1.indexOf(".")> 0)
                	o1 = o1.substring(0, o1.indexOf("."));
                if (o2.indexOf(".")> 0)
                	o2 = o2.substring(0, o2.indexOf("."));
                return Integer.parseInt(o1) - Integer.parseInt(o2);  
            }  
        };
    	
    	model.addColumn("ID");
    	model.addColumn("Icon");
    	model.addColumn("ENName");
    	model.addColumn("Character");
    	model.addColumn("CardName");
    	model.addColumn("");//World
    	model.addColumn(label_AWAKE);
    	model.addColumn("Element");
    	model.addColumn("Weapon");
    	model.addColumn("Cost");
    	model.addColumn("Type");
    	model.addColumn("Hmin");
    	model.addColumn("Hmax");
    	model.addColumn("Smin");
    	model.addColumn("Smax");
    	model.addColumn("Vmin");
    	model.addColumn("Vmax");
    	model.addColumn("Imin");
    	model.addColumn("Imax");
    	model.addColumn("Mmin");
    	model.addColumn("Mmax");
    	model.addColumn("BA1");
    	model.addColumn("BA1description");
    	model.addColumn("");//BA1Power
    	model.addColumn("BA2");
    	model.addColumn("BA2description");
    	model.addColumn("");//BA2Power
    	model.addColumn("");//BA3
    	model.addColumn("");//BA3description
    	model.addColumn("");//BA3Power
    	model.addColumn("SS");
    	model.addColumn("");//SSPower
    	model.addColumn("LeaderSkill Condition");
    	model.addColumn("LeaderSkill");
    	
    	String[] typelist = {"1","2","3","4"};
    	String[] basicstatus = {"hp_min","hp_max","str_min","str_max","vit_min","vit_max","int_min","int_max","men_min","men_max"};
    	String[] ba_headers = {header_BA1_id,header_BA2_id,header_BA3_id};
    	
    	int[] colwidth = {70, 140, 130, 165, 220, 45, 35, 65, 65, 50, 75, 
    			50, 50, 50, 50, 50, 50, 50, 50, 50, 50,
    			200, 300, 50, 200, 300, 50, 20, 30, 20,
    			230, 50,
    			280, 250};
    	for (int i = 0; i < colwidth.length; i++){
        	tbl_extendList.getColumnModel().getColumn(i).setPreferredWidth(colwidth[i]);
//        	tbl_extendList.getColumnModel().getColumn(i).setMaxWidth(colwidth[i]);
//        	tbl_extendList.getColumnModel().getColumn(i).setMinWidth(colwidth[i]);
    	}
    	tbl_extendList.getColumnModel().getColumn(0).setCellRenderer(digitalCell);
    	tbl_extendList.getColumnModel().getColumn(1).setCellRenderer(iconCell);
    	tbl_extendList.getColumnModel().getColumn(2).setCellRenderer(digital_centerCell);
    	tbl_extendList.getColumnModel().getColumn(3).setCellRenderer(centerCell);
    	tbl_extendList.getColumnModel().getColumn(5).setCellRenderer(worldCell);
    	tbl_extendList.getColumnModel().getColumn(6).setCellRenderer(centerCell);
    	tbl_extendList.getColumnModel().getColumn(7).setCellRenderer(elementCell);
    	tbl_extendList.getColumnModel().getColumn(9).setCellRenderer(digital_centerCell);
    	tbl_extendList.getColumnModel().getColumn(10).setCellRenderer(typeCell);
    	for (int i = 0; i < basicstatus.length; i++){
    		tbl_extendList.getColumnModel().getColumn(11+i).setCellRenderer(digital_rightCell);
        	rowSorter.setComparator(11+i, numberComparator);
    	}
    	tbl_extendList.getColumnModel().getColumn(21).setCellRenderer(skillTitleCell);
    	tbl_extendList.getColumnModel().getColumn(23).setCellRenderer(skillPowerCell);
    	rowSorter.setComparator(23, numberComparator);
    	tbl_extendList.getColumnModel().getColumn(24).setCellRenderer(skillTitleCell);
    	tbl_extendList.getColumnModel().getColumn(26).setCellRenderer(skillPowerCell);
    	rowSorter.setComparator(26, numberComparator);
    	tbl_extendList.getColumnModel().getColumn(27).setCellRenderer(skillTitleCell);
    	tbl_extendList.getColumnModel().getColumn(29).setCellRenderer(skillPowerCell);
    	rowSorter.setComparator(29, numberComparator);
    	tbl_extendList.getColumnModel().getColumn(30).setCellRenderer(SSTitleCell);
    	tbl_extendList.getColumnModel().getColumn(31).setCellRenderer(skillPowerCell);
    	rowSorter.setComparator(31, numberComparator);
    	
    	for (int i = 0; i < list.size(); i++){
    		for (int j = 0; j < typelist.length; j++){
    			boolean awake = false;
    			Vector<Object> row = new Vector<Object>();
    			String id = list.get(i).get(0);
    			Vector<String> chara = SAOCRData.id2chara.get(id);
    			if (SAOCRData.id2data.containsKey(id+typelist[j])){
    				Vector<String> data = SAOCRData.id2data.get(id+typelist[j]);

    				row.add(id);
    				row.add("");
    				int index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name_english);
    				row.add(chara.get(index));
    				index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name);
    				row.add(chara.get(index));
    				int index2 = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name_header);
    				row.add(chara.get(index2)+chara.get(index));
    				row.add("");
    				index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_rarity);
    				String rarity = chara.get(index);
    				if (rarity.endsWith("0"))
    					row.add(rarity.substring(0,1));
    				else{
    					awake = true;
    					row.add(rarity.substring(0,1)+label_AWAKE);
    				}
    				int Element = Integer.parseInt(data.get(SAOCRData.id2data.get(headerRow_key).indexOf(header_element)));
    				row.add(elementtextlist[Element-1]);
    				index = SAOCRData.id2data.get(headerRow_key).indexOf(header_weaponcategory);
    				row.add(SAOCRData.id2weaponcategory.get(data.get(index)));
    				index = SAOCRData.id2data.get(headerRow_key).indexOf(header_cost);
    				row.add(data.get(index));
    				row.add(typetextlist[j]);
    				for (int k = 0; k < basicstatus.length; k++){
    					index = SAOCRData.id2data.get(headerRow_key).indexOf(basicstatus[k]);
    					row.add(data.get(index));
    				}

    				for (int k = 0; k < ba_headers.length; k++){
    					index = SAOCRData.id2data.get(headerRow_key).indexOf(ba_headers[k]);
    					if (data.get(index) != null && !"".equals(data.get(index))&&
    							SAOCRData.id2unitskill.containsKey(data.get(index))){
    						row.add(SAOCRData.id2unitskill.get(data.get(index)).get(
    								SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_name)));
    						row.add(SAOCRData.id2unitskill.get(data.get(index)).get(
    								SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_description)));
    						row.add(SAOCRData.id2unitskill.get(data.get(index)).get(
    								SAOCRData.id2unitskill.get(headerRow_key).indexOf(header_power)));
    					}
    					else{
    						row.add("---");
    						row.add("---");
    						row.add("");
    					}
    				}

    				String SSID = "";
    				if (!awake){
    					if (SAOCRData.chara2swordskill9.containsKey(id)){
    						SSID = SAOCRData.chara2swordskill9.get(id);
    					}
    					else{
    						index = SAOCRData.id2data.get(headerRow_key).indexOf(header_weaponcategory);
    						SSID = SAOCRData.weapon2swordskill9.get(data.get(index));
    					}
    				}
    				else{
    					if (SAOCRData.chara2swordskill10.containsKey(id)){
    						SSID = SAOCRData.chara2swordskill10.get(id);
    					}
    					else{
    						index = SAOCRData.id2data.get(headerRow_key).indexOf(header_weaponcategory);
    						SSID = SAOCRData.weapon2swordskill10.get(data.get(index));
    					}
    				}
    				index = SAOCRData.id2swordskill.get(headerRow_key).indexOf(header_name);
    				row.add(SAOCRData.id2swordskill.get(SSID).get(index));
    				index = SAOCRData.id2swordskill.get(headerRow_key).indexOf(header_power);
    				row.add(SAOCRData.id2swordskill.get(SSID).get(index));

    				if (SAOCRData.chara2leaderskill.containsKey(id)){
    					index = SAOCRData.id2leaderskill.get(headerRow_key).indexOf(header_name);
    					row.add(SAOCRData.id2leaderskill.get(SAOCRData.chara2leaderskill.get(id)).get(index).replaceAll("効果範囲：", ""));
    					index = SAOCRData.id2leaderskill.get(headerRow_key).indexOf(header_description);
    					row.add(SAOCRData.id2leaderskill.get(SAOCRData.chara2leaderskill.get(id)).get(index));
    				}
    				else if (SAOCRData.id2awake.get(id) != null && SAOCRData.chara2leaderskill.containsKey(SAOCRData.id2awake.get(id))){
    					String afterid = SAOCRData.id2awake.get(id);
    					index = SAOCRData.id2leaderskill.get(headerRow_key).indexOf(header_name);
    					row.add(SAOCRData.id2leaderskill.get(SAOCRData.chara2leaderskill.get(afterid)).get(index).replaceAll("効果範囲：", ""));
    					index = SAOCRData.id2leaderskill.get(headerRow_key).indexOf(header_description);
    					row.add(SAOCRData.id2leaderskill.get(SAOCRData.chara2leaderskill.get(afterid)).get(index));
    				}
    				else{
    					row.add("---");
    					row.add("---");
    				}

    				model.addRow(row);
    			}
    		}
    	}
    	
    	final JScrollPane scrollPane= new JScrollPane(tbl_extendList);
    	
//    	final JScrollBar bar = scrollPane.getVerticalScrollBar();
//    	bar.addAdjustmentListener(new AdjustmentListener(){
//			@Override
//			public void adjustmentValueChanged(AdjustmentEvent e) {
//				System.out.println(bar.getValue());
//			}
//    	});
//    	bar.removeNotify();
//    	scrollPane.addMouseWheelListener(new MouseWheelListener(){
//			@Override
//			public void mouseWheelMoved(MouseWheelEvent e) {
//				Point p = scrollPane.getViewport().getViewPosition();
//				if (e.getWheelRotation() < 0){
//					if (p.y < 30)
//						p.y = 30;
//					scrollPane.getViewport().setViewPosition(new Point(p.x, p.y - 30));
//				}
//				else{
//					if (p.y > scrollPane.getViewport().getViewSize().height - 30)
//						p.y = scrollPane.getViewport().getViewSize().height - 30;
//					scrollPane.getViewport().setViewPosition(new Point(p.x, p.y + 30));
//				}
//				bar.setMaximum(255);
//				bar.setVisibleAmount(15);
//			}
//    	});

    	frm_extendList.setBounds(40, 200, exfmWidth, exfmHeight);
    	frm_extendList.getContentPane().setLayout(new BorderLayout());
    	
    	pan_exoption.setLayout(new GridLayout(2,1));
    	commonFormatting(pan_exoption);
    	JPanel pan_exoption2 = new JPanel();
    	pan_exoption2.setLayout(new BorderLayout());
    	commonFormatting(pan_exoption2);
    	
    	createWorldPanel(pan_exoption);
    	createRarePanel(pan_exoption);
    	
    	pan_exoption2.add(createType_ElementPanel(), BorderLayout.EAST);
    	pan_exoption2.add(pan_exoption, BorderLayout.CENTER);
    	
    	pan_exoption2.setOpaque(true);
    	frm_extendList.getContentPane().add(pan_exoption2, BorderLayout.NORTH);
    	frm_extendList.getContentPane().add(scrollPane, BorderLayout.CENTER);
//    	frm_extendList.setVisible(true);
    	frm_extendList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
//    	frm_iconList.setBounds(40, 200, exfmWidth, exfmHeight);
//    	iconPane.setLayout(new FlowLayout());
//    	iconPane.add(new JLabel("test"));
//    	JScrollPane scrollPane3= new JScrollPane(iconPane);
//    	frm_iconList.getContentPane().add(scrollPane3, BorderLayout.CENTER);
//    	frm_iconList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frm_iconList.setVisible(true);
	}
	public void showFrame(){
		frame.setVisible(true);
	}
	public void showExtendFrame(){
		frm_extendList.setVisible(true);
		frm_extendList.setExtendedState(JFrame.MAXIMIZED_HORIZ);
	}
	@SuppressWarnings("unchecked")
	protected StatusPanel addStatusPanel(String id, boolean replace) {
		if (!replace){
			StatusPanel chara = createStatusPanel(id);
			Component[] cs = pan_charaData.getComponents();
			pan_charaData.removeAll();
			pan_charaData.add(chara);
			for (Component c: cs){
				pan_charaData.add(c);
			}
			Vector<String> row = new Vector<String>();
			row.add(id+"1");
			row.add("0");
			SAOCRData.savedata.add(row);

			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					new Thread(new Runnable(){
						public void run(){
							try{
								Thread.sleep(100);
								scrollPane2.getViewport().setViewPosition(new Point(0,0));
							}
							catch (Exception e){;}
						}
					}).start();
				}
			});
			return chara;
		}
		else{
			pan_charaData.removeAll();
			StatusPanel p0 = createStatusPanel(id);
			p0.switchContent();
			pan_charaData.add(p0);
			pan_charaData.add(createStatusPanel(id, "1"));
			pan_charaData.add(createStatusPanel(id, "2"));
			pan_charaData.add(createStatusPanel(id, "3"));
			pan_charaData.add(createStatusPanel(id, "4"));


			Vector<Vector<String>> data = (Vector<Vector<String>>) SAOCRData.savedata.clone();
			SAOCRData.savedata.clear();
			SAOCRData.savedata.add(data.get(0));
			Vector<String> row = new Vector<String>();
			row.add(id+"4");
			row.add("0");
			SAOCRData.savedata.add((Vector<String>) row.clone());
			row.set(0, id+"3");
			SAOCRData.savedata.add((Vector<String>) row.clone());
			row.set(0, id+"2");
			SAOCRData.savedata.add((Vector<String>) row.clone());
			row.set(0, id+"1");
			SAOCRData.savedata.add((Vector<String>) row.clone());
			row.set(1, "1");
			SAOCRData.savedata.add((Vector<String>) row.clone());

			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					new Thread(new Runnable(){
						public void run(){
							try{
								Thread.sleep(100);
								scrollPane2.getViewport().setViewPosition(new Point(0,0));
							}
							catch (Exception e){;}
						}
					}).start();
				}
			});
			return p0;
		}
	}
	protected StatusPanel createStatusPanel(String id, String type) {
		StatusPanel chara = createStatusPanel(id);
		chara.changeType(type);
		return chara;
	}
	protected StatusPanel createStatusPanel(String id) {
		final StatusPanel chara = new StatusPanel();
		chara.getBtn().addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				int length = pan_charaData.getComponents().length;
				for (int j=0; j < length; j++){
					if (pan_charaData.getComponents()[j] == chara){
						SAOCRData.savedata.remove(length-j);
						break;
					}
				}
				
				pan_charaData.remove(chara);
				for (MouseListener l:chara.getBtn().getMouseListeners()){
					chara.getBtn().removeMouseListener(l);
				}
    			pan_charaData.revalidate();
    			pan_charaData.repaint();
			}
		});
		chara.setMaximumSize(new Dimension(chara.getPreferredSize().width,pan_charaData.getHeight()));
		chara.showValue(id);
		
		return chara;
	}
	
	private void createWorldPanel(JPanel panel) {
    	JPanel pan_world = new JPanel();
    	pan_world.setPreferredSize(new Dimension(statusPanelWidth, optionRowHeight));
    	pan_world.setLayout(new GridLayout(1,4));
    	commonFormatting(pan_world);
    	
    	Vector<JCheckBox> chk_world = new Vector<JCheckBox>();    	
    	if (panel.equals(pan_option)){
        	chk_world.add(world1);
        	chk_world.add(world2);
        	chk_world.add(world3);
        	chk_world.add(world4);
    	}
    	else if (panel.equals(pan_exoption)){
        	chk_world.add(world1ex);
        	chk_world.add(world2ex);
        	chk_world.add(world3ex);
        	chk_world.add(world4ex);
        	chk_world.add(chk_normal);
        	chk_world.add(chk_awake);
    	}
    	chk_options.addAll(chk_world);
    	
    	for (int i = 0; i < chk_world.size(); i++){
    		commonFormatting(chk_world.get(i),TYPE_CHKBOX);
    		chk_world.get(i).addMouseListener(new optionCheckBoxMouseListener());
    		pan_world.add(chk_world.get(i));
    	}
    	
    	panel.add(pan_world);
	}
	
	private void createRarePanel(JPanel panel) {
    	JPanel pan_rare = new JPanel();
    	pan_rare.setPreferredSize(new Dimension(statusRowHeight, optionRowHeight));
    	pan_rare.setLayout(new GridLayout(1,7));
    	commonFormatting(pan_rare);
    	
    	Vector<JCheckBox> chk_rare = new Vector<JCheckBox>();
    	
    	if (panel.equals(pan_option)){
        	chk_rare.add(rare1);
        	chk_rare.add(rare2);
        	chk_rare.add(rare3);
        	chk_rare.add(rare4);
        	chk_rare.add(rare5);
        	chk_rare.add(rare6);
        	chk_rare.add(rare7);
        	chk_rare.add(rare8);
    	}
    	else if (panel.equals(pan_exoption)){
        	chk_rare.add(rare1ex);
        	chk_rare.add(rare2ex);
        	chk_rare.add(rare3ex);
        	chk_rare.add(rare4ex);
        	chk_rare.add(rare5ex);
        	chk_rare.add(rare6ex);
        	chk_rare.add(rare7ex);
        	chk_rare.add(rare8ex);
    	}
    	chk_options.addAll(chk_rare);
    	
    	for (int i = 0; i < chk_rare.size(); i++){
    		commonFormatting(chk_rare.get(i), TYPE_CHKBOX);
    		chk_rare.get(i).addMouseListener(new optionCheckBoxMouseListener());
    		pan_rare.add(chk_rare.get(i));
    	}
    	
    	panel.add(pan_rare);
	}
	
	private JPanel createType_ElementPanel() {
    	JPanel pan_type_element = new JPanel(new BorderLayout());
    	commonFormatting(pan_type_element);
    	
    	JPanel pan_type = new JPanel(new GridLayout(2,2));
    	commonFormatting(pan_type);
    	pan_type.setPreferredSize(new Dimension(250, optionRowHeight*2));
    	
    	JPanel pan_element = new JPanel(new GridLayout(1,3));
    	commonFormatting(pan_element);
    	pan_element.setPreferredSize(new Dimension(250, optionRowHeight));
    	
    	pan_type_element.add(pan_type, BorderLayout.NORTH);
    	pan_type_element.add(pan_element, BorderLayout.SOUTH);
    	
    	Vector<JCheckBox> chk_type = new Vector<JCheckBox>();
    	chk_type.add(type1);
    	chk_type.add(type2);
    	chk_type.add(type3);
    	chk_type.add(type4);
    	chk_options.addAll(chk_type);
    	
    	for (int i = 0; i < chk_type.size(); i++){
    		commonFormatting(chk_type.get(i),TYPE_CHKBOX);
    		chk_type.get(i).addMouseListener(new optionCheckBoxMouseListener());
    		pan_type.add(chk_type.get(i));
    	}
    	
    	chk_type.removeAllElements();
    	chk_type.add(element1);
    	chk_type.add(element2);
    	chk_type.add(element3);
    	chk_options.addAll(chk_type);
    	
    	for (int i = 0; i < chk_type.size(); i++){
    		commonFormatting(chk_type.get(i),TYPE_CHKBOX);
    		chk_type.get(i).addMouseListener(new optionCheckBoxMouseListener());
    		pan_element.add(chk_type.get(i));
    	}
    	
    	return pan_type_element;
	}
	
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
		comp.setBackground(new Color(0,0,0,190));
		comp.setForeground(Color.white);
		comp.setFont(font);
		comp.setOpaque(false);
		if (comp == tbl_List){
	    	tbl_List.setRowHeight(tableRowHeight);
		}
		else if (comp == tbl_extendList){
	    	tbl_extendList.setFont(extablefont);
	    	tbl_extendList.setRowHeight(extableRowHeight);
	    	tbl_extendList.setBackground(Color.black);
		}
	}
	
	class charaComponentListner implements ComponentListener {
		@Override
		public void componentResized(ComponentEvent e) {
			for (Component c:pan_charaData.getComponents()){
				((StatusPanel) c).setHeight(pan_charaData.getHeight());
			}
//			pan_charaData.revalidate();
//			pan_charaData.repaint();
		}
		@Override
		public void componentMoved(ComponentEvent e) {}
		@Override
		public void componentShown(ComponentEvent e) {}
		@Override
		public void componentHidden(ComponentEvent e) {}
	}
	
	class optionCheckBoxMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {	    	
			if (((JCheckBox) e.getSource()) == type1){
				hideType1 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == type2){
				hideType2 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == type3){
				hideType3 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == type4){
				hideType4 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == element1){
				hideElement1 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == element2){
				hideElement2 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == element3){
				hideElement3 = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == rare1){
				hideRare1 = ((JCheckBox) e.getSource()).isSelected();
				rare1ex.setSelected(!hideRare1);
			}
			else if (((JCheckBox) e.getSource()) == rare1ex){
				hideRare1 = ((JCheckBox) e.getSource()).isSelected();
				rare1.setSelected(!hideRare1);
			}
			else if (((JCheckBox) e.getSource()) == rare2){
				hideRare2 = ((JCheckBox) e.getSource()).isSelected();
				rare2ex.setSelected(!hideRare2);
			}
			else if (((JCheckBox) e.getSource()) == rare2ex){
				hideRare2 = ((JCheckBox) e.getSource()).isSelected();
				rare2.setSelected(!hideRare2);
			}
			else if (((JCheckBox) e.getSource()) == rare3){
				hideRare3 = ((JCheckBox) e.getSource()).isSelected();
				rare3ex.setSelected(!hideRare3);
			}
			else if (((JCheckBox) e.getSource()) == rare3ex){
				hideRare3 = ((JCheckBox) e.getSource()).isSelected();
				rare3.setSelected(!hideRare3);
			}
			else if (((JCheckBox) e.getSource()) == rare4){
				hideRare4 = ((JCheckBox) e.getSource()).isSelected();
				rare4ex.setSelected(!hideRare4);
			}
			else if (((JCheckBox) e.getSource()) == rare4ex){
				hideRare4 = ((JCheckBox) e.getSource()).isSelected();
				rare4.setSelected(!hideRare4);
			}
			else if (((JCheckBox) e.getSource()) == rare5){
				hideRare5 = ((JCheckBox) e.getSource()).isSelected();
				rare5ex.setSelected(!hideRare5);
			}
			else if (((JCheckBox) e.getSource()) == rare5ex){
				hideRare5 = ((JCheckBox) e.getSource()).isSelected();
				rare5.setSelected(!hideRare5);
			}
			else if (((JCheckBox) e.getSource()) == rare6){
				hideRare6 = ((JCheckBox) e.getSource()).isSelected();
				rare6ex.setSelected(!hideRare6);
			}
			else if (((JCheckBox) e.getSource()) == rare6ex){
				hideRare6 = ((JCheckBox) e.getSource()).isSelected();
				rare6.setSelected(!hideRare6);
			}
			else if (((JCheckBox) e.getSource()) == rare7){
				hideRare7 = ((JCheckBox) e.getSource()).isSelected();
				rare7ex.setSelected(!hideRare7);
			}
			else if (((JCheckBox) e.getSource()) == rare7ex){
				hideRare7 = ((JCheckBox) e.getSource()).isSelected();
				rare7.setSelected(!hideRare7);
			}
			else if (((JCheckBox) e.getSource()) == rare8){
				hideRare8 = ((JCheckBox) e.getSource()).isSelected();
				rare8ex.setSelected(!hideRare8);
			}
			else if (((JCheckBox) e.getSource()) == rare8ex){
				hideRare8 = ((JCheckBox) e.getSource()).isSelected();
				rare8.setSelected(!hideRare8);
			}
			else if (((JCheckBox) e.getSource()) == world1){
				hideWorld1 = ((JCheckBox) e.getSource()).isSelected();
				world1ex.setSelected(!hideWorld1);
			}
			else if (((JCheckBox) e.getSource()) == world1ex){
				hideWorld1 = ((JCheckBox) e.getSource()).isSelected();
				world1.setSelected(!hideWorld1);
			}
			else if (((JCheckBox) e.getSource()) == world2){
				hideWorld2 = ((JCheckBox) e.getSource()).isSelected();
				world2ex.setSelected(!hideWorld2);
			}
			else if (((JCheckBox) e.getSource()) == world2ex){
				hideWorld2 = ((JCheckBox) e.getSource()).isSelected();
				world2.setSelected(!hideWorld2);
			}
			else if (((JCheckBox) e.getSource()) == world3){
				hideWorld3 = ((JCheckBox) e.getSource()).isSelected();
				world3ex.setSelected(!hideWorld3);
			}
			else if (((JCheckBox) e.getSource()) == world3ex){
				hideWorld3 = ((JCheckBox) e.getSource()).isSelected();
				world3.setSelected(!hideWorld3);
			}
			else if (((JCheckBox) e.getSource()) == world4){
				hideWorld4 = ((JCheckBox) e.getSource()).isSelected();
				world4ex.setSelected(!hideWorld4);
			}
			else if (((JCheckBox) e.getSource()) == world4ex){
				hideWorld4 = ((JCheckBox) e.getSource()).isSelected();
				world4.setSelected(!hideWorld4);
			}
			else if (((JCheckBox) e.getSource()) == chk_normal){
				hideNORMAL = ((JCheckBox) e.getSource()).isSelected();
			}
			else if (((JCheckBox) e.getSource()) == chk_awake){
				hideAWAKE = ((JCheckBox) e.getSource()).isSelected();
			}
	    	
			hideTableRow();
		};
	}

	public void hideTableRow(){
    	if (tbl_List != null){
    		int index = 0;
    		for (int i = 0; i < tbl_List.getColumnCount(); i++){
    			if ("ID".equals(tbl_List.getColumnName(i))){
    				index = i;
    				break;
    			}
    		}
    		for (int i = 0; i < tbl_List.getRowCount(); i++){
    			boolean hide = false;

    			char world = ((String) tbl_List.getValueAt(i, index)).charAt(0);
    			switch (world){
    			case flag_SAO:
    				if (hideWorld1) hide = true; break;
    			case flag_ALO:
    				if (hideWorld2) hide = true; break;
    			case flag_GGO:
    				if (hideWorld3) hide = true; break;
    			case flag_EX:
    				if (hideWorld4) hide = true; break;
    			}
    			if (!hide){
	    			char rare = ((String) tbl_List.getValueAt(i, index)).charAt(6);
	    			switch (rare){
	    			case '1': case 1:
	    				if (hideRare1) hide = true; break;
	    			case '2': case 2:
	    				if (hideRare2) hide = true; break;
	    			case '3': case 3:
	    				if (hideRare3) hide = true; break;
	    			case '4': case 4:
	    				if (hideRare4) hide = true; break;
	    			case '5': case 5:
	    				if (hideRare5) hide = true; break;
	    			case '6': case 6:
	    				if (hideRare6) hide = true; break;
	    			case '7': case 7:
	    				if (hideRare7) hide = true; break;
	    			case '8': case 8:
	    				if (hideRare8) hide = true; break;
	    			}
    			}

    			if (hide){
    				tbl_List.setRowHeight(i, 1);
    			}
    			else{
    				tbl_List.setRowHeight(i, tableRowHeight);
    			}
    		}
    	}
    	if (tbl_extendList != null){
    		int indexID = 0;
    		int indexAWAKE = 6;
    		int indexELEMENT = 7;
    		int indexTYPE = 10;
    		for (int i = 0; i < tbl_extendList.getColumnCount(); i++){
    			if ("ID".equals(tbl_extendList.getColumnName(i))){
    				indexID = i;
    			}
    			if (label_AWAKE.equals(tbl_extendList.getColumnName(i))){
    				indexAWAKE = i;
    			}
    			if ("Element".equals(tbl_extendList.getColumnName(i))){
    				indexELEMENT = i;
    			}
    			if ("Type".equals(tbl_extendList.getColumnName(i))){
    				indexTYPE = i;
    			}
    		}
    		for(int i = 0; i < tbl_extendList.getRowCount(); i++){
    			boolean hide = false;

    			char world = ((String) tbl_extendList.getValueAt(i, indexID)).charAt(0);
    			switch (world){
    			case flag_SAO:
    				if (hideWorld1) hide = true; break;
    			case flag_ALO:
    				if (hideWorld2) hide = true; break;
    			case flag_GGO:
    				if (hideWorld3) hide = true; break;
    			case flag_EX:
    				if (hideWorld4) hide = true; break;
    			}
    			if (!hide){
    				char rare = ((String) tbl_extendList.getValueAt(i, indexID)).charAt(6);
    				switch (rare){
    				case '1': case 1:
    					if (hideRare1) hide = true; break;
    				case '2': case 2:
    					if (hideRare2) hide = true; break;
    				case '3': case 3:
    					if (hideRare3) hide = true; break;
    				case '4': case 4:
    					if (hideRare4) hide = true; break;
    				case '5': case 5:
    					if (hideRare5) hide = true; break;
    				case '6': case 6:
    					if (hideRare6) hide = true; break;
    				case '7': case 7:
    					if (hideRare7) hide = true; break;
    				case '8': case 8:
    					if (hideRare8) hide = true; break;
    				}
    				if (!hide){
    					if (hideAWAKE && ((String) tbl_extendList.getValueAt(i, indexAWAKE)).contains(label_AWAKE)){
    						hide = true;
    					}
    					if (hideNORMAL && !((String) tbl_extendList.getValueAt(i, indexAWAKE)).contains(label_AWAKE)){
    						hide = true;
    					}
    					if (!hide){
    						if (hideType1 && ((String) tbl_extendList.getValueAt(i, indexTYPE)).contains(typetextlist[0])){
    							hide = true;
    						}
    						if (hideType2 && ((String) tbl_extendList.getValueAt(i, indexTYPE)).contains(typetextlist[1])){
    							hide = true;
    						}
    						if (hideType3 && ((String) tbl_extendList.getValueAt(i, indexTYPE)).contains(typetextlist[2])){
    							hide = true;
    						}
    						if (hideType4 && ((String) tbl_extendList.getValueAt(i, indexTYPE)).contains(typetextlist[3])){
    							hide = true;
    						}
    		    			if (!hide){
    							if (hideElement1 && ((String) tbl_extendList.getValueAt(i, indexELEMENT)).contains(elementtextlist[0])){
    								hide = true;
    							}
    							if (hideElement2 && ((String) tbl_extendList.getValueAt(i, indexELEMENT)).contains(elementtextlist[1])){
    								hide = true;
    							}
    							if (hideElement3 && ((String) tbl_extendList.getValueAt(i, indexELEMENT)).contains(elementtextlist[2])){
    								hide = true;
    							}
    		    			}
    					}
    				}
    			}

    			if (hide){
    				tbl_extendList.setRowHeight(i, 1);
    			}
    			else{
//    				if (showIcon)
//    					tbl_extendList.setRowHeight(i, extableRowHeight);
//    				else
    					tbl_extendList.setRowHeight(i, tableRowHeight);
    			}
    		}
    	}

		for (int i = 0; i < chk_options.size(); i++){
			chk_options.get(i).setForeground(color_STATUS);
		}
    	type1.setForeground(color_TYPE1);
    	type2.setForeground(color_TYPE2);
    	type3.setForeground(color_TYPE3);
    	type4.setForeground(color_TYPE4);
    	element1.setForeground(color_ELEMENT1);
    	element2.setForeground(color_ELEMENT2);
    	element3.setForeground(color_ELEMENT3);
    	if (hideWorld1){
    		world1.setForeground(color_STATUS_A);
    		world1ex.setForeground(color_STATUS_A);
    	}
    	if (hideWorld2){
    		world2.setForeground(color_STATUS_A);
    		world2ex.setForeground(color_STATUS_A);
    	}
    	if (hideWorld3){
    		world3.setForeground(color_STATUS_A);
    		world3ex.setForeground(color_STATUS_A);
    	}
    	if (hideWorld4){
    		world4.setForeground(color_STATUS_A);
    		world4ex.setForeground(color_STATUS_A);
    	}
    	if (hideNORMAL){
    		chk_normal.setForeground(color_STATUS_A);
    	}
    	if (hideAWAKE){
    		chk_awake.setForeground(color_STATUS_A);
    	}
    	if (hideType1){
    		type1.setForeground(color_STATUS_A);
    	}
    	if (hideType2){
    		type2.setForeground(color_STATUS_A);
    	}
    	if (hideType3){
    		type3.setForeground(color_STATUS_A);
    	}
    	if (hideType4){
    		type4.setForeground(color_STATUS_A);
    	}
    	if (hideElement1){
    		element1.setForeground(color_STATUS_A);
    	}
    	if (hideElement2){
    		element2.setForeground(color_STATUS_A);
    	}
    	if (hideElement3){
    		element3.setForeground(color_STATUS_A);
    	}
    	if (hideRare1){
    		rare1.setForeground(color_STATUS_A);
    		rare1ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare2){
    		rare2.setForeground(color_STATUS_A);
    		rare2ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare3){
    		rare3.setForeground(color_STATUS_A);
    		rare3ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare4){
    		rare4.setForeground(color_STATUS_A);
    		rare4ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare5){
    		rare5.setForeground(color_STATUS_A);
    		rare5ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare6){
    		rare6.setForeground(color_STATUS_A);
    		rare6ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare7){
    		rare7.setForeground(color_STATUS_A);
    		rare7ex.setForeground(color_STATUS_A);
    	}
    	if (hideRare8){
    		rare8.setForeground(color_STATUS_A);
    		rare8ex.setForeground(color_STATUS_A);
    	}
	}
	
	private void saveImage(){

        try {            
            JFileChooser chooser = new JFileChooser();
    		chooser.setDialogType(JFileChooser.FILES_ONLY);
    		FileSystemView fsv=FileSystemView.getFileSystemView();
    		chooser.setCurrentDirectory(fsv.getHomeDirectory());
    		chooser.setSelectedFile(new File(ssSaveID));
    		
    		MyFileFilter pngFilter = new MyFileFilter(".png", "png 图像 (*.png)");
    		MyFileFilter jpgFilter = new MyFileFilter(".jpg", "jpg 图像 (*.jpg)");
    		chooser.removeChoosableFileFilter(chooser.getFileFilter());
    		chooser.addChoosableFileFilter(jpgFilter);
    		chooser.addChoosableFileFilter(pngFilter);
    		
    		chooser.setDialogTitle("保存截图");
    		chooser.setMultiSelectionEnabled(false);
    		chooser.showSaveDialog(chooser);
    		
    		String path = chooser.getSelectedFile().getPath();
    		MyFileFilter filter = (MyFileFilter)chooser.getFileFilter();
    		String ends = filter.getEnds();
    		if (!path.endsWith(ends.toUpperCase())) {
    			path += ends;
    		}
    		path = path.substring(0, path.length() - 4);
    		String end = path.substring(path.length() - 4, path.length());
    		int count = 0;
    		if ("_all".equals(end)){
		            Dimension size = pan_charaData.getSize();
		            BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_BGR);
		            pan_charaData.printAll(image.getGraphics());
		            
		            File file = new File(path+ends);
		            OutputStream bos = new FileOutputStream(file);
		            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
		            JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(image);
		            if (".PNG".equals(ends.toUpperCase())){
			            jep.setQuality(1f, false);
		            }
		            else{
			            jep.setQuality(0.8f, false);
		            }
		            encoder.setJPEGEncodeParam(jep);
		            encoder.encode(image);
		            ImageIO.write(image, ends, file);
		            bos.flush();
		            bos.close();
    		}
    		else{
	    		for (Component c : pan_charaData.getComponents()){
		            Dimension size = c.getSize();
		            BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_BGR);
		            c.printAll(image.getGraphics());
		            
		            File file = new File(path+"_"+String.valueOf(count)+ends);
		            OutputStream bos = new FileOutputStream(file);
		            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
		            JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(image);
		            if (".PNG".equals(ends.toUpperCase())){
			            jep.setQuality(1f, false);
		            }
		            else{
			            jep.setQuality(0.8f, false);
		            }
		            encoder.setJPEGEncodeParam(jep);
		            encoder.encode(image);
		            ImageIO.write(image, ends, file);
		            bos.flush();
		            bos.close();
		            count ++;
	    		}
    		}
        }
        catch (Exception e1){
        }
	}
	

	Point loc = null;
	Point tmp = null;
	boolean isDragged = false;
	private void setDragable(JComponent comp) {        
		comp.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				frame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		comp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if(isDragged) {
					loc = new Point(frame.getLocation().x/* + e.getX() - tmp.x*/,
							frame.getLocation().y + e.getY() - tmp.y);
					frame.setLocation(loc);
				}
			}
		});
	}
	
	private void setStatusPaneDragable(JComponent comp) {
		comp.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				frame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		comp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if(isDragged) {
					if (scrollPane2.getViewport().getViewPosition().x + e.getX() - tmp.x < 0)
						loc = new Point(0, 0);
					else
						loc = new Point(scrollPane2.getViewport().getViewPosition().x + e.getX() - tmp.x, 0);
					scrollPane2.getViewport().setViewPosition(loc);
				}
			}
		});
	}
}