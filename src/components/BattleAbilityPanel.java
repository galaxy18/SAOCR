package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import configs.Params;


@SuppressWarnings("serial")
public class BattleAbilityPanel extends JPanel implements Params{	
	JLabel lbl_BA1name = new JLabel();
	JLabel lbl_BA1description = new JLabel();
	JLabel lbl_BA1value = new JLabel();
	JLabel lbl_BA2name = new JLabel();
	JLabel lbl_BA2description = new JLabel();
	JLabel lbl_BA2value = new JLabel();
	JLabel lbl_BA3name = new JLabel();
	JLabel lbl_BA3description = new JLabel();
	JLabel lbl_BA3value = new JLabel();
	
	public BattleAbilityPanel(){
		super();
		this.setLayout(new GridLayout(7,1));
		JLabel lbl_BAtitle = new JLabel("■バトルアビリティ");
		commonFormatting(lbl_BAtitle,TYPE_LABEL);
		lbl_BAtitle.setForeground(color_SKILL_title);

		commonFormatting(lbl_BA1name,TYPE_NAME_LABEL);
		lbl_BA1name.setForeground(color_BA_name);

		commonFormatting(lbl_BA2name,TYPE_NAME_LABEL);
		lbl_BA2name.setForeground(color_BA_name);
		
		commonFormatting(lbl_BA3name,TYPE_NAME_LABEL);
		lbl_BA3name.setForeground(color_BA_name);

		commonFormatting(lbl_BA1description,TYPE_LABEL);
		commonFormatting(lbl_BA2description,TYPE_LABEL);
		commonFormatting(lbl_BA3description,TYPE_LABEL);

		commonFormatting(lbl_BA1value,TYPE_DIGITAL_LABEL);
		lbl_BA1value.setForeground(color_BA_power);
		commonFormatting(lbl_BA2value,TYPE_DIGITAL_LABEL);
		lbl_BA2value.setForeground(color_BA_power);
		commonFormatting(lbl_BA3value,TYPE_DIGITAL_LABEL);
		lbl_BA3value.setForeground(color_BA_power);
		
		lbl_BA1name.setIcon(new ImageIcon(icons.bas[1]));
		lbl_BA2name.setIcon(new ImageIcon(icons.bas[2]));
		lbl_BA3name.setIcon(new ImageIcon(icons.bas[3]));
		lbl_BA1description.setIcon(new ImageIcon(icons.bas[0]));
		lbl_BA2description.setIcon(new ImageIcon(icons.bas[0]));
		lbl_BA3description.setIcon(new ImageIcon(icons.bas[0]));
		lbl_BA1value.setIcon(new ImageIcon(icons.bas[0]));
		lbl_BA2value.setIcon(new ImageIcon(icons.bas[0]));
		lbl_BA3value.setIcon(new ImageIcon(icons.bas[0]));
		
		JPanel pan_BA1 = new JPanel(new BorderLayout());
		pan_BA1.add(lbl_BA1description, BorderLayout.CENTER);
		pan_BA1.add(lbl_BA1value, BorderLayout.EAST);
		JPanel pan_BA2 = new JPanel(new BorderLayout());
		pan_BA2.add(lbl_BA2description, BorderLayout.CENTER);
		pan_BA2.add(lbl_BA2value, BorderLayout.EAST);
		JPanel pan_BA3 = new JPanel(new BorderLayout());
		pan_BA3.add(lbl_BA3description, BorderLayout.CENTER);
		pan_BA3.add(lbl_BA3value, BorderLayout.EAST);
		
		this.add(lbl_BAtitle);
		this.add(lbl_BA1name);
		this.add(pan_BA1);
		this.add(lbl_BA2name);
		this.add(pan_BA2);
		this.add(lbl_BA3name);
		this.add(pan_BA3);
		commonFormatting(pan_BA1);
		commonFormatting(pan_BA2);
		commonFormatting(pan_BA3);
		commonFormatting(this);
	}
	
	public void showValue(Hashtable<String, Vector<String>> id2data, 
			Hashtable<String, Vector<String>> id2unitskill, 
			String id){
		lbl_BA1name.setText("NODATA");
		lbl_BA1description.setText("------");
		lbl_BA2name.setText("NODATA");
		lbl_BA2description.setText("------");
		lbl_BA3name.setText("NODATA");
		lbl_BA3description.setText("------");
		
		lbl_BA1name.setToolTipText(lbl_BA1name.getText());
		lbl_BA1description.setToolTipText(lbl_BA1description.getText());
		lbl_BA2name.setToolTipText(lbl_BA2name.getText());
		lbl_BA2description.setToolTipText(lbl_BA2description.getText());
		lbl_BA3name.setToolTipText(lbl_BA3name.getText());
		lbl_BA3description.setToolTipText(lbl_BA3description.getText());
		if (id2data.get(id) == null){
			return;
		}
		
		int index = id2data.get(headerRow_key).indexOf(header_BA1_id);
		int index2 = id2unitskill.get(headerRow_key).indexOf(header_name);
		if (id2unitskill.get(id2data.get(id).get(index)) == null)
			return;
		lbl_BA1name.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
		if ("".equals(lbl_BA1name.getText()))
			lbl_BA1name.setText("NODATA");
		
		index2 = id2unitskill.get(headerRow_key).indexOf(header_description);
		lbl_BA1description.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
		if ("".equals(lbl_BA1description.getText()))
			lbl_BA1description.setText("------");

		index2 = id2unitskill.get(headerRow_key).indexOf(header_healrate);
		if (id2unitskill.get(id2data.get(id).get(index)) != null){
			lbl_BA1value.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
			if (!"".equals(lbl_BA1value.getText()))
				lbl_BA1value.setText(String.valueOf((new Double(Double.parseDouble(lbl_BA1value.getText())*100)).intValue())+"%");
			index2 = id2unitskill.get(headerRow_key).indexOf(header_power);
			if (!"".equals(id2unitskill.get(id2data.get(id).get(index)).get(index2)))
				lbl_BA1value.setText(lbl_BA1value.getText()+"/"+id2unitskill.get(id2data.get(id).get(index)).get(index2));
			if (lbl_BA1value.getText().startsWith("/"))
				lbl_BA1value.setText(lbl_BA1value.getText().substring(1));
		}
		
		index = id2data.get(headerRow_key).indexOf(header_BA2_id);
		index2 = id2unitskill.get(headerRow_key).indexOf(header_name);
		if (id2unitskill.get(id2data.get(id).get(index)) != null)
		lbl_BA2name.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
		if ("".equals(lbl_BA2name.getText()))
			lbl_BA2name.setText("NODATA");
		
		index2 = id2unitskill.get(headerRow_key).indexOf(header_description);
		if (id2unitskill.get(id2data.get(id).get(index)) != null)
		lbl_BA2description.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
		if ("".equals(lbl_BA2description.getText()))
			lbl_BA2description.setText("------");
		
		index2 = id2unitskill.get(headerRow_key).indexOf(header_healrate);
		if (id2unitskill.get(id2data.get(id).get(index)) != null){
			lbl_BA2value.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
			if (!"".equals(lbl_BA2value.getText()))
				lbl_BA2value.setText(String.valueOf((new Double(Double.parseDouble(lbl_BA2value.getText())*100)).intValue())+"%");
			index2 = id2unitskill.get(headerRow_key).indexOf(header_power);
			if (!"".equals(id2unitskill.get(id2data.get(id).get(index)).get(index2)))
				lbl_BA2value.setText(lbl_BA2value.getText()+"/"+id2unitskill.get(id2data.get(id).get(index)).get(index2));
			if (lbl_BA2value.getText().startsWith("/"))
				lbl_BA2value.setText(lbl_BA2value.getText().substring(1));
		}
		
		index = id2data.get(headerRow_key).indexOf(header_BA3_id);
		index2 = id2unitskill.get(headerRow_key).indexOf(header_name);
		if (id2unitskill.get(id2data.get(id).get(index)) != null)
		lbl_BA3name.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
		if ("".equals(lbl_BA3name.getText()))
			lbl_BA3name.setText("NODATA");
		
		index2 = id2unitskill.get(headerRow_key).indexOf(header_description);
		if (id2unitskill.get(id2data.get(id).get(index)) != null)
		lbl_BA3description.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
		if ("".equals(lbl_BA3description.getText()))
			lbl_BA3description.setText("------");
		
		index2 = id2unitskill.get(headerRow_key).indexOf(header_healrate);
		if (id2unitskill.get(id2data.get(id).get(index)) != null){
			lbl_BA3value.setText(id2unitskill.get(id2data.get(id).get(index)).get(index2));
			if (!"".equals(lbl_BA3value.getText()))
				lbl_BA3value.setText(String.valueOf((new Double(Double.parseDouble(lbl_BA3value.getText())*100)).intValue())+"%");
			index2 = id2unitskill.get(headerRow_key).indexOf(header_power);
			if (!"".equals(id2unitskill.get(id2data.get(id).get(index)).get(index2)))
				lbl_BA3value.setText(lbl_BA3value.getText()+"/"+id2unitskill.get(id2data.get(id).get(index)).get(index2));
			if (lbl_BA3value.getText().startsWith("/"))
				lbl_BA3value.setText(lbl_BA3value.getText().substring(1));
		}

		lbl_BA1name.setToolTipText(lbl_BA1name.getText());
		lbl_BA1description.setToolTipText(lbl_BA1description.getText());
		lbl_BA2name.setToolTipText(lbl_BA2name.getText());
		lbl_BA2description.setToolTipText(lbl_BA2description.getText());
		lbl_BA3name.setToolTipText(lbl_BA3name.getText());
		lbl_BA3description.setToolTipText(lbl_BA3description.getText());
	}

	private void commonFormatting(JComponent comp, String type){
		commonFormatting(comp);
		if (TYPE_CHKBOX.equals(type)){
			((AbstractButton) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (TYPE_LABEL.equals(type)){
			((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (TYPE_NAME_LABEL.equals(type)){
			comp.setFont(namefont);
			((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (TYPE_DIGITAL_LABEL.equals(type)){
			comp.setFont(digitalfont);
			((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (TYPE_NAME_RADIO.equals(type)){
		}
		else if (TYPE_DIGITAL_BTN.equals(type)){
			comp.setFont(digitalfont);
		}
		comp.setOpaque(false);
	}
	private void commonFormatting(JComponent comp){
		comp.setBackground(Color.black);
		comp.setForeground(Color.white);
		comp.setFont(namefont);
		comp.setOpaque(false);
	}
}
