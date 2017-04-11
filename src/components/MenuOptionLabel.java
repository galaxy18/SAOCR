package components;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import configs.Params;

@SuppressWarnings("serial")
public class MenuOptionLabel extends JLabel implements Params{
	public boolean hover = false;
	public boolean select = false;
	
	public MenuOptionLabel(String text){
		super(text);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if (select || hover){
			g.drawImage(Menubar1.getImage(), -20, 0, null);
		    g.setColor(new Color(48,97,187));
		    g.drawString(this.getText(), 12, 30);
		}
		else{
			g.drawImage(Menubar2.getImage(), -20, 0, null);
			g.setColor(Color.white);
	    	g.drawString(this.getText(), 12, 30);
		}
//		super.paintComponent(g);
	} 
}
