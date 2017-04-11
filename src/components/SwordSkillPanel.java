package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import configs.Params;


@SuppressWarnings("serial")
public class SwordSkillPanel extends JPanel implements Params{
	JLabel lbl_LStitle = new JLabel("UNKNOWN");
	JLabel lbl_SSname = new JLabel("UNKNOWN");
	JLabel lbl_SSpower = new JLabel("0000");
	
	public SwordSkillPanel(){
		super();
		this.setLayout(new BorderLayout());
		lbl_LStitle = new JLabel("■SS：");
		commonFormatting(lbl_LStitle, TYPE_LABEL);
		lbl_LStitle.setForeground(color_SKILL_title);
		
		commonFormatting(lbl_SSname, TYPE_LABEL);
		
		commonFormatting(lbl_SSpower, TYPE_LABEL);
		lbl_SSpower.setFont(digitalfont);
		lbl_SSpower.setForeground(color_BA_power);

		this.add(lbl_LStitle, BorderLayout.WEST);
		this.add(lbl_SSname, BorderLayout.CENTER);
		this.add(lbl_SSpower, BorderLayout.EAST);
		this.setBackground(Color.black);
		this.setOpaque(true);
	}
	
	public void showValue(Hashtable<String, Vector<String>> id2data,
			Hashtable<String, Vector<String>> id2swordskill,
			Hashtable<String, String> chara2swordskill,
			Hashtable<String, String> weapon2swordskill,
			String id){
//		if (id2data.get(id+"1") == null)
//			return;
		String SSID = "";
		
		if (chara2swordskill.containsKey(id)){
			SSID = chara2swordskill.get(id);
			lbl_LStitle.setText("■ユニークSS：");
			lbl_LStitle.setForeground(color_UniqueSS_title);
		}
		else{
			int index = id2data.get(headerRow_key).indexOf(header_weaponcategory);
			SSID = weapon2swordskill.get(id2data.get(id).get(index));
			lbl_LStitle.setText("■SS：");
			lbl_LStitle.setForeground(color_SKILL_title);
		}
		
		int index = id2swordskill.get(headerRow_key).indexOf(header_name);
		lbl_SSname.setText(id2swordskill.get(SSID).get(index));
		index = id2swordskill.get(headerRow_key).indexOf(header_power);
		lbl_SSpower.setText(id2swordskill.get(SSID).get(index));
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
		comp.setBackground(Color.black);
		comp.setForeground(Color.white);
		comp.setFont(font);
		comp.setOpaque(false);
	}
}
