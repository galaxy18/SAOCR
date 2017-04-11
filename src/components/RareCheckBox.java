package components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class RareCheckBox extends JCheckBox{
	public RareCheckBox(){
		super();
	}
	
	public RareCheckBox (String text, boolean selected) {
        super(text, null, selected);
    }
	
	protected void paintComponent(Graphics g){
//		super.paintComponent(g);
		g.setColor(new Color(255,255,255,120));
		if (this.isSelected()){
			//g.draw3DRect(2, 2, this.getBounds().width-4, this.getBounds().height-4, false);
			g.fillRoundRect(2, 2, this.getBounds().width-4, this.getBounds().height-4, 4, 4);
			g.setColor(Color.white);
			g.drawRoundRect(2, 2, this.getBounds().width-4, this.getBounds().height-4, 4, 4);
		}
		else{
			g.setColor(new Color(255,255,255,80));
			//g.draw3DRect(2, 2, this.getBounds().width-4, this.getBounds().height-4, true);
			g.fillRoundRect(2, 2, this.getBounds().width-4, this.getBounds().height-4, 4, 4);
			//g.setColor(Color.black);
			g.drawRoundRect(2, 2, this.getBounds().width-4, this.getBounds().height-4, 4, 4);
		}
        int strWidth = g.getFontMetrics().stringWidth(this.getText());
        int strHeight = g.getFontMetrics().getHeight();
        g.drawString(this.getText(), 0 + strWidth / 2, this.getBounds().height/2+strHeight/4);//(this.getBounds().height-strHeight)/2+strHeight);// + strHeight/2);
	} 
}
