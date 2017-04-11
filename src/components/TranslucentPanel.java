package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import configs.Params;

@SuppressWarnings("serial")
public
class TranslucentPanel extends JPanel implements Params{
	public Color TL = new Color(0f, 0f, 0f, .8f);
	public Color BR = new Color(0f, 0f, 0f, .9f);
	float[] dist = {  0f, .25f, .3f,  .6f, .75f, .85f, .9f, 1f};
	Color[] colors = {BR,   TL,  BR,   BR,   TL,   TL,  BR, TL};
	private int dwidth = 0;
	private int dheight = 10;
	
	public void setColor(Color arg_color){
		TL = arg_color;
		colors = new Color[]{BR,   TL,  BR,   BR,   TL,   TL,  BR, TL};
	}

	public TranslucentPanel() {
		super();
	}
	public TranslucentPanel(GridLayout gridLayout) {
		super();
		this.setLayout(gridLayout);
		TL = new Color(.5f, .8f, 1f, .6f);
		BR = new Color( 0f,  0f, 0f, .3f);
		dist = new float[]{  0f, .5f, 1f};
		colors = new Color[]{TL,  BR, TL};
		dwidth = 20;
		dheight = 0;
	}
	@Override
	public void updateUI() {
		super.updateUI();
		setOpaque(false);
		setForeground(Color.WHITE);
	}
	@Override
	protected void paintComponent(Graphics g) {
		int x = 0;
		int y = 0;
		int w = getWidth() - dwidth;
		int h = getHeight() - dheight;
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Shape area = new Rectangle2D.Float(x, y, w - 1, h - 1);
		
		g2.setPaint(new LinearGradientPaint(x, y, x, y + h, dist, colors));
		
		g2.fill(area);
		g2.setPaint(BR);
		g2.draw(area);
		g2.dispose();
		super.paintComponent(g);
	}
}