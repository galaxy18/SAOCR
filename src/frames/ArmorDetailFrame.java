package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import configs.Params;

import objects.MyFileFilter;
import objects.SAOCRData;

@SuppressWarnings("serial")
public class ArmorDetailFrame extends JFrame implements Params{
	JFrame detailfrm = new JFrame();
	JPanel mainpane = new JPanel();
	JLabel lbl_capture = new JLabel("CAPTURE");
	Vector<String> armorheaderrow = SAOCRData.id2armor.get(headerRow_key);
	int[] attrs = {
			armorheaderrow.indexOf("str_min"),
			armorheaderrow.indexOf("vit_min"),
			armorheaderrow.indexOf("int_min"),
			armorheaderrow.indexOf("men_min"),
			armorheaderrow.indexOf("str_max"),
			armorheaderrow.indexOf("vit_max"),
			armorheaderrow.indexOf("int_max"),
			armorheaderrow.indexOf("men_max")
	};
	Vector<String> receiptheaderrow = SAOCRData.id2equipmentreceipt.get(headerRow_key);
	
	String ssSaveID = "";
	
	public ArmorDetailFrame(String id){
		ssSaveID = id;
    	commonFormatting(lbl_capture);
    	lbl_capture.setOpaque(true);
    	lbl_capture.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			saveImage();
    		}
    	});
    	
		Vector<String> armor = SAOCRData.id2armor.get(id);
		
		mainpane.setLayout(new BorderLayout());
		JPanel pan_info = new JPanel(new GridLayout(4,1));
		commonFormatting(pan_info);
		pan_info.setOpaque(true);
		JPanel pan_basicInfo = new JPanel(new BorderLayout());
		commonFormatting(pan_basicInfo);
		pan_basicInfo.add(commonFormattingDigital(new JLabel(id+"  ")), BorderLayout.WEST);
		pan_basicInfo.add(commonFormatting(new JLabel(armor.get(1))), BorderLayout.CENTER);
		pan_info.add(pan_basicInfo);
		mainpane.add(pan_info, BorderLayout.NORTH);
		
//		int index = SAOCRData.id2weapongroup.get(headerRow_key).indexOf(header_weaponcategory);
//		JLabel lbl_WEAPON = new JLabel(SAOCRData.id2weaponcategory.get(armor.get(index)));
//		lbl_WEAPON.setIcon(new ImageIcon(icons.weapons[Integer.parseInt(armor.get(index))-1]));
//		commonFormatting(lbl_WEAPON);
//		pan_info.add(lbl_WEAPON);
		
		JPanel pan_statusInfo = new JPanel(new GridLayout(1,5));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("")));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("STR")));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("VIT")));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("INT")));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("MEN")));
		commonFormatting(pan_statusInfo);
		pan_info.add(pan_statusInfo);
		
		pan_statusInfo = new JPanel(new GridLayout(1,5));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("min")));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[0]))));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[1]))));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[2]))));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[3]))));
		commonFormatting(pan_statusInfo);
		pan_info.add(pan_statusInfo);
		
		pan_statusInfo = new JPanel(new GridLayout(1,5));
		pan_statusInfo.add(commonFormattingDigital(new JLabel("max")));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[4]))));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[5]))));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[6]))));
		pan_statusInfo.add(commonFormattingDigital(new JLabel(armor.get(attrs[7]))));
		commonFormatting(pan_statusInfo);
		pan_info.add(pan_statusInfo);
		
		Vector<String> receipt = SAOCRData.id2equipmentreceipt.get(id);
		if (receipt != null){
			id = receipt.get(receiptheaderrow.indexOf(headerRow_key));
			Vector<Vector<String>> receiptData = SAOCRData.id2equipmentreceipbreakdown.get(id);
			if (receiptData != null){
				JPanel pan_receipt = new JPanel(new GridLayout(receiptData.size()+2,1));
				pan_receipt.add(commonFormatting(new JLabel()));
				if (!"".equals(receipt.get(receiptheaderrow.indexOf(header_starttime)))){
					pan_receipt = new JPanel(new GridLayout(receiptData.size()+4,1));
					pan_receipt.add(commonFormatting(new JLabel()));
					pan_receipt.add(commonFormattingDigital(new JLabel(receipt.get(receiptheaderrow.indexOf(header_starttime)))), BorderLayout.CENTER);
					pan_receipt.add(commonFormattingDigital(new JLabel(receipt.get(receiptheaderrow.indexOf(header_endtime)))), BorderLayout.CENTER);
				}
				
				commonFormatting(pan_receipt);
				pan_receipt.setOpaque(true);
				mainpane.add(pan_receipt, BorderLayout.SOUTH);
				
				JPanel pan_price = new JPanel(new BorderLayout());
				pan_price.add(commonFormattingDigital(new JLabel("Price: ")), BorderLayout.WEST);
				pan_price.add(commonFormattingDigital(new JLabel(receipt.get(receiptheaderrow.indexOf(header_price)))), BorderLayout.CENTER);
				commonFormatting(pan_price);
				pan_receipt.add(pan_price);
				
				for (int i = 0; i<receiptData.size();i++){
					JPanel pan_receiptInfo = new JPanel(new GridLayout(1,2));
					pan_receiptInfo.add(commonFormatting(new JLabel(SAOCRData.id2material.get(receiptData.get(i).get(0)))));
					pan_receiptInfo.add(commonFormattingDigital(new JLabel(receiptData.get(i).get(1))));
					commonFormatting(pan_receiptInfo);
					pan_receipt.add(pan_receiptInfo);
				}
			}
		}
		mainpane.setBorder(BorderFactory.createLineBorder(Color.black, 10));
//		((JComponent) detailfrm.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.black, 15));
		detailfrm.add(mainpane);
		detailfrm.add(lbl_capture, BorderLayout.NORTH);
		detailfrm.pack();
		detailfrm.setVisible(true);
	}
	private JComponent commonFormattingDigital(JComponent comp){
		commonFormatting(comp);
		comp.setFont(digitalfont);
		return comp;
	}
	private JComponent commonFormatting(JComponent comp){
		comp.setBackground(Color.black);
		comp.setForeground(Color.white);
		comp.setFont(font);
		comp.setOpaque(false);
		return comp;
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
//    		String end = path.substring(path.length() - 4, path.length());
//    		int count = 0;
//    		if ("_all".equals(end)){
		            Dimension size = mainpane.getSize();
		            BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_BGR);
		            mainpane.printAll(image.getGraphics());
		            
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
