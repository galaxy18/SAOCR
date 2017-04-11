package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import objects.MyFileFilter;
import tableRenderer.HeaderCellRenderer;

import configs.Params;

public class PLCharaEpisodeViewer implements Params{
	public JFrame frm = new JFrame();
	int count = 0;
	Vector<File> files = new Vector<File>();
	JLabel lbl_Text = new JLabel();
	JTextField txt_Name = new JTextField(mynamereplace);
	JTable PLtable = new JTable();
	JLabel lbl_capture = new JLabel("CAPTURE");
	DefaultTableModel model = new DefaultTableModel();
	
	private boolean isPLCharaEP = true;
	
	@SuppressWarnings("serial")
	public PLCharaEpisodeViewer(boolean arg_PLCharaEP) throws IOException{
		isPLCharaEP = arg_PLCharaEP;
		if (isPLCharaEP){
			File directory = new File(folder_charaep);
			if (!directory.exists()){
	    		frm = null;
	    		return;
	    	}
			
			File[] rawfiles = directory.listFiles();
	    	for (int i = 0; i < rawfiles.length ; i++){
	    		String name = rawfiles[i].getName();
	    		if (name.endsWith("txt")){
	    			files.add(rawfiles[i]);
	    		}
	    	}
	    	if (files.size() <= 0){
	    		frm = null;
	    		return;
	    	}
		}
		else{
			File directory = new File(folder_scenario);
			if (!directory.exists()){
	    		frm = null;
	    		return;
	    	}
			
			File[] rawfiles = directory.listFiles();
	    	for (int i = 0; i < rawfiles.length ; i++){
	    		String name = rawfiles[i].getName();
	    		if (name.endsWith("tsv")){
	    			files.add(rawfiles[i]);
	    		}
	    	}
	    	if (files.size() <= 0){
	    		frm = null;
	    		return;
	    	}
		}
		
    	lbl_Text.addMouseWheelListener(new MouseWheelListener(){
    		public void mouseWheelMoved(MouseWheelEvent e){
    			count = count + e.getWheelRotation();
				try {
					reloadText();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
		
    	lbl_Text.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					count = count +1;
				}
				else{
					count = count -1;
				}
				try {
					reloadText();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
		
		HeaderCellRenderer extable_header = new HeaderCellRenderer();
		
		PLtable = new JTable(model){
			@Override
		    public void paintComponent(Graphics g){
		       super.paintComponent(g);
		       Graphics2D g2d=(Graphics2D) g;   
		       g2d.setRenderingHint(
		             RenderingHints.KEY_ANTIALIASING,
		             RenderingHints.VALUE_ANTIALIAS_ON);
		      }
		};
		PLtable.getTableHeader().setDefaultRenderer(extable_header);
		PLtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		commonFormatting(PLtable);
		
		model.addColumn("name");
        model.addColumn("serif");
        if (!isPLCharaEP){
        	 model.addColumn("");
        }
        
	    DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
	    singleclick.setClickCountToStart(1);
	    for (int i = 0; i < PLtable.getColumnCount(); i++) {
	    	PLtable.setDefaultEditor(PLtable.getColumnClass(i), singleclick);
	    }
        
        reloadText();
		
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(PLtable);

    	commonFormatting(lbl_capture);
    	lbl_capture.setOpaque(true);
    	lbl_capture.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			saveImage();
    		}
    	});
    	
    	JPanel titlePane = new JPanel(new GridLayout(1,2));
    	commonFormatting(titlePane);
    	commonFormatting(lbl_Text);
    	commonFormatting(txt_Name);
    	txt_Name.addKeyListener(new KeyAdapter(){
    		public void keyPressed(KeyEvent e) {
    			if (e.getKeyCode() == KeyEvent.VK_ENTER){
    				if (!"".equals(txt_Name.getText().trim())){
    					try {
							reloadText();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
    				}
    			}
    		}
    	});
    	
    	titlePane.add(lbl_Text, BorderLayout.WEST);
    	titlePane.add(txt_Name, BorderLayout.EAST);
    	
		frm.add(titlePane, BorderLayout.NORTH);
		frm.add(scrollpane, BorderLayout.CENTER);
		frm.add(lbl_capture, BorderLayout.SOUTH);
		frm.setBounds(0, 0, 600, 600);
		frm.setTitle("PLCharaEpisode");
	}
	
	public void reloadText(String title) throws IOException{
		if (title.equals(""))
			return;
		
		int newcount;
		for (newcount = 0; newcount < files.size(); newcount++){
			String filename = files.get(newcount).getName();
			if (filename.startsWith(String.valueOf(title))){
				count = newcount;
				reloadText();
				frm.setVisible(true);
				break;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void reloadText() throws IOException{
		if (count < 0)
			count = files.size() -1;
		if (count >= files.size())
			count = 0;

		PLtable.setFont(pltablefont);
		
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
        InputStreamReader inputStream=new InputStreamReader(new FileInputStream(files.get(count)),"UTF-8");
        lbl_Text.setText(files.get(count).getName());
		BufferedReader br = new BufferedReader(inputStream);
		String dataArray = "";
		Vector<String> rowData = new Vector<String>();
		while((dataArray = br.readLine()) != null) {
			if (dataArray.contains(mynamereplace) && !"".equals(txt_Name.getText().trim())){
				dataArray = dataArray.replaceAll(mynamereplace, txt_Name.getText().trim());
			}
			if (!dataArray.startsWith("\t")){
				if ("【】".equals(dataArray)){
					rowData.add("");
				}
				else{
					rowData.add(dataArray);
				}
			}
			else{
				if (rowData.size() <= 0){
					rowData.add("");
				}
				
				if (isPLCharaEP){
					rowData.add(dataArray.substring(1));
					model.addRow(rowData);
					
					rowData = new Vector<String>();
					rowData.add("");rowData.add("");
					model.addRow(rowData);
					rowData = new Vector<String>();
					rowData.add("");rowData.add("");
					model.addRow(rowData);
				}
				else{
					rowData.add("");
					rowData.add("");
					String[] data = dataArray.substring(1).split("\\\\n");
					for (int i = 0; i < data.length; i++){
						rowData.set(1, data[i]);
						model.addRow((Vector) rowData.clone());
						rowData.set(0, "");
					}
				}
				
				rowData = new Vector<String>();
			}
		}
		br.close();
		inputStream.close();
		
		FitTableColumns(PLtable);
	}
	
	private void FitTableColumns(JTable myTable){
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();

		Enumeration<TableColumn> columns = myTable.getColumnModel().getColumns();
		while(columns.hasMoreElements()){
			TableColumn column = (TableColumn)columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int)myTable.getTableHeader().getDefaultRenderer()
			.getTableCellRendererComponent(myTable, column.getIdentifier()
					, false, false, -1, col).getPreferredSize().getWidth();
			for(int row = 0; row<rowCount; row++){
				int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
						myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width+myTable.getIntercellSpacing().width);
		}
		if (!isPLCharaEP){
			TableColumn column = myTable.getColumnModel().getColumn(2);
			column.setWidth(myTable.getColumnModel().getColumn(1).getWidth());
		}
	}
	
	private void commonFormatting(JComponent comp){
		comp.setBackground(Color.black);
		comp.setForeground(Color.white);
		comp.setOpaque(true);
		if (comp == PLtable){
			PLtable.setRowHeight(tableRowHeight);
			PLtable.setGridColor(Color.black);
		}
		else{
			comp.setFont(font);
		}
	}
	
	private void saveImage(){

        try {            
            JFileChooser chooser = new JFileChooser();
    		chooser.setDialogType(JFileChooser.FILES_ONLY);
    		FileSystemView fsv=FileSystemView.getFileSystemView();
    		chooser.setCurrentDirectory(fsv.getHomeDirectory());
    		chooser.setSelectedFile(new File(lbl_Text.getText().replaceAll(".txt", "")));
    		
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
//    		String end = path.substring(path.length() - 4, path.length());
//    		int count = 0;
//    		if ("_all".equals(end)){
		            Dimension size = PLtable.getSize();
		            BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_BGR);
		            PLtable.printAll(image.getGraphics());
		            
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
//    		}
        }
        catch (Exception e1){
        }
	}
}
