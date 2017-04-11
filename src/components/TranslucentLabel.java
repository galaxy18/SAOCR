package components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextPane;

import configs.Params;

@SuppressWarnings("serial")
class TranslucentLabel extends JTextPane implements Params{
	private static final Color TL = new Color(.8f, .8f, .8f, .4f);
	private static final Color BR = new Color(0f, 0f, 0f, .7f);
	private Color ssc;
	private Color bgc;
	private int r = 10;
	
	public TranslucentLabel() {
		super();
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
		int y = 10;
		int w = getWidth();
		int h = getHeight() - 10;
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Shape area = new RoundRectangle2D.Float(x, y, w - 1, h - 1, r, r);
		ssc = TL;
		bgc = BR;
		
		g2.setPaint(new GradientPaint(x, y, ssc, x, y + h, bgc, true));
//		g2.setPaint(bgc);
		
		g2.fill(area);
		g2.setPaint(BR);
		g2.draw(area);
		g2.dispose();
		super.paintComponent(g);
	}
	
//    @Override
//    public void paintComponent(Graphics g) {
//		int x = 0;
//		int y = 0;
//		int w = getWidth();
//		int h = getHeight();
//        Graphics2D g2 = (Graphics2D) g;
//
//        // アルファ値
//        AlphaComposite composite = AlphaComposite.getInstance(
//                AlphaComposite.SRC_OVER, 0.3f);
//
//        // アルファ値をセット（以後の描画は半透明になる）
//        g2.setComposite(composite);
//
//        g2.setColor(Color.black);
//        g2.fillRoundRect(x, y+10, w, h-10, 20, 20);
//        
//        super.paintComponent(g);
//    }
}