package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import objects.TargaReader;

import components.ImagePanel;
import configs.Params;

public class GachaViewer implements Params{
	public JFrame frm = new JFrame();
	int count = 0;
	File[] files = null;
	ImagePanel panel = new ImagePanel();
	JLabel lbl_Text = new JLabel();
	
	public GachaViewer() throws IOException{
		File directory = new File(folder_gacha);
		if (!directory.exists()){
    		frm = null;
    		return;
    	}
		
    	files = directory.listFiles();
    	boolean containstga = false;
    	for (int i = 0; i < files.length ; i++){
    		String name = files[i].getName();
    		if (name.endsWith("tga")){
    			containstga = true;
    		}
    	}
    	if (!containstga && files.length <= 0){
    		frm = null;
    		return;
    	}
    	
		panel.isicon = true;
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(panel);

		repaint();

		frm.add(lbl_Text, BorderLayout.NORTH);
		frm.add(scrollpane, BorderLayout.CENTER);
		frm.setBounds(0, 0, 600, 600);
		frm.setSize(panel.icon.getIconWidth()+50, panel.icon.getIconHeight()+100);
//		frm.setUndecorated(true);
//		frm.setVisible(true);
		
		panel.addMouseWheelListener(new MouseWheelListener(){
    		public void mouseWheelMoved(MouseWheelEvent e){
    			try {
					count = count + e.getWheelRotation();
					if (count < 0)
						count = files.length -1;
					if (count >= files.length)
						count = 0;
    				repaint();
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		}
    	});
		
		panel.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
    			try {
    				if(e.getButton() == MouseEvent.BUTTON1){
    					count = count +1;
    				}
    				else{
    					count = count -1;
    				}
    				repaint();
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		}
    	});
	}
	private void repaint() throws IOException{
		if (count < 0)
			count = files.length -1;
		if (count >= files.length)
			count = 0;
		String ext = files[count].getName().substring(files[count].getName().length() - 4, files[count].getName().length());
		if (".png".equals(ext.toLowerCase())){
			panel.icon = new ImageIcon(files[count].getPath());
		}
		else if (".tga".equals(ext.toLowerCase())){
			panel.icon = new ImageIcon(TargaReader.getImage(files[count].getPath()));
		}
		if (files[count].getName().endsWith("_large.tga")){
			panel.imagewidth = 867.0;
			panel.imageheight = 822.0;
			panel.setPreferredSize(new Dimension(867, 822));
			frm.setSize(867+50, 822);
		}
		else{
			panel.imageheight = (double) panel.icon.getIconHeight();
			panel.setPreferredSize(new Dimension(panel.icon.getIconWidth(), panel.icon.getIconHeight()+30));
			frm.setSize(panel.icon.getIconWidth()+50, panel.icon.getIconHeight()+100);
		}
		lbl_Text.setText(files[count].getName());
		panel.repaint();
	}
}
