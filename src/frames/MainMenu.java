package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import components.MenuOptionLabel;
import components.TranslucentPanel;

import configs.LoadCSVs;
import configs.Params;

@SuppressWarnings("serial")
public class MainMenu extends JFrame implements Params{
	JFrame mainFrame = new JFrame("MENU");
	JPanel btnPanel = new TranslucentPanel(new GridLayout(6,2));
	
	private boolean showBtn = false;
	private boolean main = false;
	private boolean extable = false;
	private boolean quest = false;
	private boolean monster = false;
	@SuppressWarnings("unused")
	private boolean gacha = false;
	private boolean charaep = false;
	private boolean scenario = false;
	private boolean weapon = false;
	private boolean armor = false;
	
	JLabel lab_Drag = new JLabel();
	JLabel lab_indicator = new JLabel();
	MenuOptionLabel lab_main = new MenuOptionLabel("Main Window");
	MenuOptionLabel lab_extendTable = new MenuOptionLabel("Extra Table");
	MenuOptionLabel lab_Quest = new MenuOptionLabel("Quest");
	MenuOptionLabel lab_Monster = new MenuOptionLabel("Monster");
	MenuOptionLabel lab_Weapon = new MenuOptionLabel("Weapon");
	MenuOptionLabel lab_Armor = new MenuOptionLabel("Armor");
	MenuOptionLabel lab_Gacha = new MenuOptionLabel("GachaBanner");
	MenuOptionLabel lab_CharaEpisode = new MenuOptionLabel("PLink");
	MenuOptionLabel lab_Scenario = new MenuOptionLabel("CRScenario");
	MenuOptionLabel lab_Exit = new MenuOptionLabel("Logout");
	
	GachaViewer gw = null;
	PLCharaEpisodeViewer pl1 = null;
	static PLCharaEpisodeViewer pl2 = null;
	
	public MainMenu() throws Exception{
		ProgressFrame.instance.setText("---------- -------");
		ProgressFrame.instance.addText("Loading gacha pictures...");
    	ProgressFrame.instance.addDigital(5.0f);
		File file = new File(folder_gacha);
		if (file.exists()){
//			btnPanel = new TranslucentPanel(new GridLayout(4,2));
			gw = new GachaViewer();
		}
		else{
			ProgressFrame.instance.appendText("not Found");
		}
		ProgressFrame.instance.addText("Loading Character Scenario...");
    	ProgressFrame.instance.addDigital(5.0f);
		File file2 = new File(folder_charaep);
		if (file2.exists()){
//			btnPanel = new TranslucentPanel(new GridLayout(4,2));
			pl1 = new PLCharaEpisodeViewer(true);
		}
		else{
			ProgressFrame.instance.appendText("not Found");
		}
		ProgressFrame.instance.addText("Loading Story Scenario...");
    	ProgressFrame.instance.addDigital(5.0f);
		File file3 = new File(folder_scenario);
		if (file3.exists()){
//			btnPanel = new TranslucentPanel(new GridLayout(4,2));
			pl2 = new PLCharaEpisodeViewer(false);
		}
		else{
			ProgressFrame.instance.appendText("not Found");
		}
		commonFormatting(btnPanel);

		lab_Drag.setIcon(Menuicon1);
		lab_Drag.setPreferredSize(new Dimension(Menuicon1.getIconWidth(), Menuicon1.getIconHeight()));
		setDragable(lab_Drag);
		lab_Drag.addMouseListener(new MouseAdapter(){
    		public void mouseClicked(MouseEvent e) {
    			if (showBtn)
    				lab_Drag.setIcon(Menuicon1);
    			else
    				lab_Drag.setIcon(Menuicon2);
				lab_indicator.setVisible(showBtn);
				btnPanel.setVisible(showBtn);
				showBtn = !showBtn;
				
    		};
		});
		
//        Runnable rotateBtn = new Runnable(){
//            private boolean flag = true;
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while(flag){
//                    try {
//                        Thread.sleep(100);
//                        if (!showBtn)
//                        	rotateIcon(10);
//                    } catch (InterruptedException e) {}
//                }
//            }
//
//        };
//        Thread rotateBtnTh = new Thread(rotateBtn);
//        rotateBtnTh.start();
		
		commonFormatting(lab_main);
		commonFormatting(lab_extendTable);
		commonFormatting(lab_Quest);
		commonFormatting(lab_Monster);
		commonFormatting(lab_Weapon);
		commonFormatting(lab_Armor);

		if (file.exists() && gw.frm != null){
//			
			commonFormatting(lab_Gacha);
		}
		else{
			btnPanel.add(new JLabel());
		}
		
		btnPanel.add(new JLabel());
		
		if (file2.exists() && pl1.frm != null){
			commonFormatting(lab_CharaEpisode);
		}
		else{
			btnPanel.add(new JLabel());
		}
		if (file3.exists() && pl2.frm != null){
			commonFormatting(lab_Scenario);
		}
		else{
			btnPanel.add(new JLabel());
		}
		
		btnPanel.add(new JLabel());
		commonFormatting(lab_Exit);
		
		lab_indicator.setOpaque(false);
		lab_indicator.setIcon(new ImageIcon("resources/indicator.png"));
		
		mainFrame.getContentPane().setLayout(new BorderLayout());
		mainFrame.getContentPane().add(lab_Drag, BorderLayout.WEST);
		mainFrame.getContentPane().add(lab_indicator, BorderLayout.CENTER);
		mainFrame.getContentPane().add(btnPanel, BorderLayout.EAST);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setAlwaysOnTop(true);

		mainFrame.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowOpaque(mainFrame,false);
		((JPanel)mainFrame.getContentPane()).setOpaque(false);
		
		mainFrame.setVisible(true);
		mainFrame.pack();
		mainFrame.setBounds(500, 300, mainFrame.getWidth(), mainFrame.getHeight());
		
		final MainFrame m = new MainFrame();
		final QuestFrame q = new QuestFrame();
		final MonsterFrame mo = new MonsterFrame();
		final WeaponFrame we = new WeaponFrame();
		final ArmorFrame ar = new ArmorFrame();

		lab_main.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			m.frame.setVisible(!main);
    			lab_main.select = !main;
    			main = !main;
    		};
    	});
		lab_extendTable.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			m.frm_extendList.setVisible(!extable);
    			lab_extendTable.select = !extable;
    			extable = !extable;
    		};
    	});
		lab_Quest.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (!quest){
    				q.showFrame();
    			}
    			else{
    				q.frm_questList.setVisible(!quest);
    			}
    			lab_Quest.select = !quest;
    			quest = !quest;
    		};
    	});
		lab_Monster.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (!monster){
    				mo.showFrame();
    			}
    			else{
    				mo.frm_monsterList.setVisible(!monster);
    			}
    			lab_Monster.select = !monster;
    			monster = !monster;
    		};
    	});
		lab_Weapon.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (!weapon){
    				we.showFrame();
    			}
    			else{
    				we.frm_weaponList.setVisible(!weapon);
    			}
    			lab_Weapon.select = !weapon;
    			weapon = !weapon;
    		};
    	});
		lab_Armor.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (!armor){
    				ar.showFrame();
    			}
    			else{
    				ar.frm_armorList.setVisible(!armor);
    			}
    			lab_Armor.select = !armor;
    			armor = !armor;
    		};
    	});
		lab_Gacha.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
//    			if (gw.frm != null){
//					gw.frm.setVisible(!gacha);
//    			}
//				lab_Gacha.select = !gacha;
//				gacha = !gacha;
    			try {
    				if (gw.frm.isVisible())
    					return;
    				
					gw = new GachaViewer();
					gw.frm.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		};
    	});
		lab_CharaEpisode.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (pl1.frm != null){
    				pl1.frm.setVisible(!charaep);
    			}
    			lab_CharaEpisode.select = !charaep;
    			charaep = !charaep;
    		};
    	});
		lab_Scenario.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (pl2.frm != null){
    				pl2.frm.setVisible(!scenario);
    			}
    			lab_Scenario.select = !scenario;
    			scenario = !scenario;
    		};
    	});
		lab_Exit.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    		    int result = JOptionPane.showConfirmDialog(mainFrame,
    		    		"SAVE CHANGES?",
    		    		"LOGOUT", 
    		    		JOptionPane.YES_NO_CANCEL_OPTION);
    		    if (result == JOptionPane.CANCEL_OPTION)
    		    	return;
    		    if (result == JOptionPane.YES_OPTION){
    				try {
						LoadCSVs.saveFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    		    }
		    	System.exit(0);
    		};
    	});
    	ProgressFrame.instance.setDigital(100.0f);
		ProgressFrame.instance.addText("Load finished.");
	}
	
	private void commonFormatting(JComponent comp){
		comp.setBackground(Color.white);
		comp.setForeground(new Color(75,75,75));
		comp.setFont(digitalfont);
		comp.setOpaque(false);
		if (comp instanceof MenuOptionLabel){
			final MenuOptionLabel lbl2 = (MenuOptionLabel) comp;
			lbl2.setHorizontalAlignment(SwingConstants.LEFT);
			lbl2.setPreferredSize(new Dimension(182,46));
			lbl2.addMouseListener(new MouseAdapter(){
	    		public void mouseEntered(MouseEvent e) {
	    			lbl2.hover = true;
	    			lbl2.repaint();
	    		};
	    		public void mouseExited(MouseEvent e) {
	    			lbl2.hover = false;
	    			lbl2.repaint();
	    		};
	    	});
			
			JPanel pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pan.setOpaque(false);
			
			pan.add(lbl2);
			btnPanel.add(pan);
		}
	}
	
	Point loc = null;    
	Point tmp = null;    
	boolean isDragged = false;    
	private void setDragable(JComponent comp) {        
		comp.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isDragged = false;
				mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mousePressed(java.awt.event.MouseEvent e) {
				tmp = new Point(e.getX(), e.getY());
				isDragged = true;
				mainFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		});
		comp.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
				if(isDragged) {
					loc = new Point(mainFrame.getLocation().x + e.getX() - tmp.x,
							mainFrame.getLocation().y + e.getY() - tmp.y);
					mainFrame.setLocation(loc);
				}
			}
		});
	}
	
	public void rotateIcon(int angle)
	{
	        int w = lab_Drag.getIcon().getIconWidth();
	        int h = lab_Drag.getIcon().getIconHeight();
	        int type = BufferedImage.TYPE_4BYTE_ABGR;

	        BufferedImage DaImage = new BufferedImage(h, w, type);
	        Graphics2D g2 = DaImage.createGraphics();
	        
	        g2.rotate(Math.toRadians(angle), w/2.0, h/2.0);
	        g2.drawImage(((ImageIcon) lab_Drag.getIcon()).getImage(), 0,0, lab_Drag);
	        g2.dispose();

	        lab_Drag.setIcon(new ImageIcon(DaImage));
	}
}
