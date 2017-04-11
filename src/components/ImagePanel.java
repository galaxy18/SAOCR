package components;

import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import configs.Params;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements Params{
	//	private JFrame frame;
	public boolean isicon = false;
	public Double imagewidth = 867.0;
	public Double imageheight = 822.0;
	public ImageIcon icon;
	
	public void seturl(String url){
		File file = new File(url);
		if (file.exists()){
			icon = new ImageIcon(url);
		}
		else{
			if (isicon)
				file = new File(url.replaceAll(".png", "1.png"));
			else
				file = new File(url.replaceAll(".png", "_large.png"));
			if (file.exists()){
				if (isicon)
					icon = new ImageIcon(url.replaceAll(".png", "1.png"));
				else
					icon = new ImageIcon(url.replaceAll(".png", "_large.png"));
			}
			else{
				if (isicon)
					icon = null;
				else{
					file = new File(image_Default);
					if (file.exists()){
						icon = new ImageIcon(image_Default);
					}
					else{
						icon = null;
					}
				}
			}
		}
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(icon!= null){
			try{
				if (imageheight == icon.getIconHeight()){
				}
				else{
					Double ratio = imageheight / icon.getIconWidth();
					Double width = icon.getIconWidth() * ratio;
					Double height = icon.getIconHeight() * ratio;
		
					icon.setImage(icon.getImage().getScaledInstance(
							width.intValue(),height.intValue(),
							java.awt.Image.SCALE_SMOOTH));
					
					imageheight = Double.parseDouble(String.valueOf(icon.getIconHeight()));
				}
				if (isicon)
					g.drawImage(icon.getImage(), 0, 0, null);
				else
					g.drawImage(icon.getImage(), (statusPanelWidth - icon.getIconWidth())/2, 0, null);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	} 
}
