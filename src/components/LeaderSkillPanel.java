package components;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import configs.Params;


@SuppressWarnings("serial")
public class LeaderSkillPanel extends JPanel implements Params{
	JLabel lbl_LSname = new JLabel();
	JLabel lbl_LSdescription = new JLabel();
	
	public LeaderSkillPanel(){
		super();
		this.setLayout(new GridLayout(3,1));
		JLabel lbl_LStitle = new JLabel("■リーダースキル");
		commonFormatting(lbl_LStitle, TYPE_LABEL);
		lbl_LStitle.setForeground(color_SKILL_title);

		commonFormatting(lbl_LSname, TYPE_LABEL);
		
		commonFormatting(lbl_LSdescription, TYPE_LABEL);

		this.add(lbl_LStitle);
		this.add(lbl_LSname);
		this.add(lbl_LSdescription);
		commonFormatting(this);
	}
	
	public void showValue(Hashtable<String, String> id2awake, 
			Hashtable<String, String> chara2leaderskill,
			Hashtable<String, Vector<String>> id2leaderskill,
			String id){
//		if (id2data.get(id+"1") == null)
//			return;

		String afterID = id2awake.get(id);
		if (afterID == null){
			if (chara2leaderskill.containsKey(id)){
				int index = id2leaderskill.get(headerRow_key).indexOf(header_name);
				lbl_LSname.setText(id2leaderskill.get(chara2leaderskill.get(id)).get(index));
				index = id2leaderskill.get(headerRow_key).indexOf(header_description);
				lbl_LSdescription.setText(id2leaderskill.get(chara2leaderskill.get(id)).get(index));
			}
			else{
				lbl_LSname.setText("");
				lbl_LSdescription.setText("");
			}
		}
		else{
			if (chara2leaderskill.containsKey(afterID)){
				int index = id2leaderskill.get(headerRow_key).indexOf(header_name);
				lbl_LSname.setText(id2leaderskill.get(chara2leaderskill.get(afterID)).get(index));
				index = id2leaderskill.get(headerRow_key).indexOf(header_description);
				lbl_LSdescription.setText(id2leaderskill.get(chara2leaderskill.get(afterID)).get(index));
			}
			else{
				lbl_LSname.setText("");
				lbl_LSdescription.setText("");
			}
		}
	}

	private void commonFormatting(JComponent comp, String type){
		commonFormatting(comp);
		if (TYPE_CHKBOX.equals(type)){
			((AbstractButton) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (TYPE_LABEL.equals(type)){
			((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (TYPE_NAME_RADIO.equals(type)){
			comp.setFont(namefont);
		}
		else if (TYPE_DIGITAL_BTN.equals(type)){
			comp.setFont(digitalfont);
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
