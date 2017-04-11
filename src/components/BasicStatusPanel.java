package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class BasicStatusPanel extends JPanel implements Params{	
	JLabel lbl_Title1 = new JLabel();
	JLabel lbl_min = new JLabel();
	JLabel lbl_Title2 = new JLabel();
	JLabel lbl_max = new JLabel();
	JLabel lbl_max2 = new JLabel();
	
	JLabel lbl_min_a = new JLabel();
	JLabel lbl_max_a = new JLabel();
	JLabel lbl_max2_a = new JLabel();

//	JLabel lbl_max3 = new JLabel();
	JLabel lbl_max3_a = new JLabel();
//	JLabel lbl_max4 = new JLabel();
	JLabel lbl_max4_a = new JLabel();
	
	String key_min;
	String key_max;
	
	private float pt_FriendShip = 0.1f;
	private float pt_LimitBreak = 0.3f;
	private boolean b_exist = false;
	private boolean b_break = false;
	
	public BasicStatusPanel(String title_min, String title_max, String value_min, String value_max){
		super();
		this.setLayout(new GridLayout(2,1));
		
		key_min=value_min;
		key_max=value_max;

		commonFormatting(lbl_Title1, TYPE_DIGITAL_LABEL);
		lbl_Title1.setText(title_min);

		commonFormatting(lbl_Title2, TYPE_DIGITAL_LABEL);
		lbl_Title2.setText(title_max);

		commonFormatting(lbl_min, TYPE_DIGITAL_LABEL);
		lbl_min.setForeground(color_STATUS_A);

		commonFormatting(lbl_max, TYPE_DIGITAL_LABEL);
		lbl_max.setForeground(color_STATUS_A);

		commonFormatting(lbl_max2, TYPE_DIGITAL_LABEL);
		
		commonFormatting(lbl_min_a, TYPE_DIGITAL_LABEL);
		commonFormatting(lbl_max_a, TYPE_DIGITAL_LABEL);
		commonFormatting(lbl_max2_a, TYPE_DIGITAL_LABEL);

//		commonFormatting(lbl_max3, TYPE_DIGITAL_LABEL);
//		lbl_max3.setForeground(color_STATUS_A);
		commonFormatting(lbl_max3_a, TYPE_DIGITAL_LABEL);
		lbl_max3_a.setForeground(color_STATUS_A);
//		commonFormatting(lbl_max4, TYPE_DIGITAL_LABEL);
		commonFormatting(lbl_max4_a, TYPE_DIGITAL_LABEL);
		
		lbl_Title1.setPreferredSize(new Dimension(60,statusRowHeight));
		lbl_Title2.setPreferredSize(new Dimension(60,statusRowHeight));
		JLabel lbl_Title3 = new JLabel();
		lbl_Title3.setPreferredSize(new Dimension(60,statusRowHeight));
		JLabel lbl_Title4 = new JLabel();
		lbl_Title4.setPreferredSize(new Dimension(60,statusRowHeight));
		
		lbl_min.setPreferredSize(new Dimension(50,statusRowHeight));
		lbl_max.setPreferredSize(new Dimension(50,statusRowHeight));
		lbl_max2.setPreferredSize(new Dimension(50,statusRowHeight));
		
		lbl_min_a.setPreferredSize(new Dimension(50,statusRowHeight));
		lbl_max_a.setPreferredSize(new Dimension(50,statusRowHeight));
		lbl_max2_a.setPreferredSize(new Dimension(50,statusRowHeight));
		
//		lbl_max3.setPreferredSize(new Dimension(50,statusRowHeight));
		lbl_max3_a.setPreferredSize(new Dimension(50,statusRowHeight));
//		lbl_max4.setPreferredSize(new Dimension(50,statusRowHeight));
		lbl_max4_a.setPreferredSize(new Dimension(50,statusRowHeight));
		
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		row1.add(lbl_Title1);
		row1.add(lbl_min);
		row1.add(lbl_Title2);
		row1.add(lbl_max);
		row1.add(lbl_max2);
//		row1.add(lbl_max3);
//		row1.add(lbl_max4);
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		row2.add(lbl_Title3);
		row2.add(lbl_min_a);
		row2.add(lbl_Title4);
		row2.add(lbl_max_a);
		row2.add(lbl_max2_a);
		row2.add(lbl_max3_a);
		row2.add(lbl_max4_a);
		commonFormatting(row1);
		commonFormatting(row2);
		commonFormatting(this);
		this.add(row1);
		this.add(row2);
		setToolTips();
	}
	
	public int showValue_a(Hashtable<String, Vector<String>> id2data, String id, String type){
		id = id + type;

		if (id2data.get(id) == null){
			lbl_min_a.setText("");
			lbl_max_a.setText("");
			lbl_max2_a.setText("");
			lbl_max3_a.setText("");
			lbl_max4_a.setText("");
			b_break = false;
			return 0;
		}
		
		b_break = true;
		
		lbl_max2.setForeground(color_STATUS_A);
//		lbl_max4.setForeground(color_STATUS_A);

		int index = id2data.get(headerRow_key).indexOf(key_min);
		lbl_min_a.setText(id2data.get(id).get(index));
		index = id2data.get(headerRow_key).indexOf(key_max);
		lbl_max_a.setText(id2data.get(id).get(index));
		lbl_max2_a.setText(String.valueOf(Math.round(Float.parseFloat(id2data.get(id).get(index))*(1+pt_FriendShip))));
		setLimitBreak("0");
//		lbl_max3_a.setText(String.valueOf(Math.round(Float.parseFloat(id2data.get(id).get(index))*(1+pt_LimitBreak))));
//		lbl_max4_a.setText(String.valueOf(Math.round(Float.parseFloat(id2data.get(id).get(index))*(1+pt_FriendShip+pt_LimitBreak))));
		
		return Integer.parseInt(id2data.get(id).get(index));
	}
	
	public int showValue(Hashtable<String, Vector<String>> id2data, String id, String type){
		if ("1".equals(type)){
			lbl_Title1.setForeground(color_TYPE1);
			lbl_Title2.setForeground(color_TYPE1);
		}
		else if ("2".equals(type)){
			lbl_Title1.setForeground(color_TYPE2);
			lbl_Title2.setForeground(color_TYPE2);
		}
		else if ("3".equals(type)){
			lbl_Title1.setForeground(color_TYPE3);
			lbl_Title2.setForeground(color_TYPE3);
		}
		else if ("4".equals(type)){
			lbl_Title1.setForeground(color_TYPE4);
			lbl_Title2.setForeground(color_TYPE4);
		}
		
		id = id + type;
		
		if (id2data.get(id) == null){
			lbl_min.setText("NODATA");
			lbl_max.setText("NODATA");
			lbl_max2.setText("");
//			lbl_max3.setText("");
//			lbl_max4.setText("");
			b_exist = false;
			return 0;
		}
		
		b_exist = true;
		b_break = false;
		
		int index = id2data.get(headerRow_key).indexOf(key_min);
		lbl_min.setText(id2data.get(id).get(index));
		index = id2data.get(headerRow_key).indexOf(key_max);
		lbl_max.setText(id2data.get(id).get(index));
		lbl_max2.setText(String.valueOf(Math.round(Float.parseFloat(id2data.get(id).get(index))*(1+pt_FriendShip))));
//		lbl_max3.setText(String.valueOf(Math.round(Float.parseFloat(id2data.get(id).get(index))*pt_LimitBreak)));
//		lbl_max4.setText(String.valueOf(Math.round(Float.parseFloat(id2data.get(id).get(index))*pt_FriendShip*pt_LimitBreak)));
		
		lbl_min_a.setText("");
		lbl_max_a.setText("");
		lbl_max2_a.setText("");
		lbl_max3_a.setText("");
		lbl_max4_a.setText("");
		lbl_max2.setForeground(color_STATUS);
//		lbl_max4.setForeground(color_STATUS);
		
		return Integer.parseInt(id2data.get(id).get(index));
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
			((JLabel) comp).setHorizontalAlignment(SwingConstants.RIGHT);
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
		comp.setFont(font);
		comp.setOpaque(false);
	}
	
	String friendship = "100";
	String limitbreak = "0";

	public void setFriendShip(String value) {
		float friendpt = Float.parseFloat(value);
		pt_FriendShip = friendpt / 1000.0f;
		if (b_exist){
			lbl_max2.setText(String.valueOf(Math.round(Integer.parseInt(lbl_max.getText())*(1+pt_FriendShip))));
//			lbl_max4.setText(String.valueOf(Math.round(Integer.parseInt(lbl_max3.getText())*pt_FriendShip)));
		}
		if (b_break){
			lbl_max2_a.setText(String.valueOf(Math.round(Integer.parseInt(lbl_max_a.getText())*(1+pt_FriendShip))));
			if (pt_LimitBreak == 0){
				lbl_max4_a.setText("");
			}
			else{
				lbl_max4_a.setText(String.valueOf(Math.round(Float.parseFloat(lbl_max_a.getText())*(1+pt_FriendShip+pt_LimitBreak))));
			}
		}
		friendship = value;
		setToolTips();
	}

	public void setLimitBreak(String value) {
		float limitpt = Float.parseFloat(value);
		pt_LimitBreak = limitpt / 10.0f;
//		if (!lbl_max2.getText().equals("")){
//			lbl_max3.setText(String.valueOf(Math.round(Integer.parseInt(lbl_max.getText())*pt_LimitBreak)));
//			lbl_max4.setText(String.valueOf(Math.round(Integer.parseInt(lbl_max2.getText())*pt_LimitBreak)));
//		}
		if (b_break){
			if (pt_LimitBreak == 0){
				lbl_max3_a.setText("");
				lbl_max4_a.setText("");
			}
			else{
				lbl_max3_a.setText(String.valueOf(Math.round(Integer.parseInt(lbl_max_a.getText())*(1+pt_LimitBreak))));
				lbl_max4_a.setText(String.valueOf(Math.round(Float.parseFloat(lbl_max_a.getText())*(1+pt_FriendShip+pt_LimitBreak))));
			}
			
			if (value.equals("1")){
				lbl_max4_a.setForeground(Color.yellow);
			}
			else if (value.equals("2")){
				lbl_max4_a.setForeground(Color.orange);
			}
			else if (value.equals("3")){
				lbl_max4_a.setForeground(Color.red);
			}
		}
		limitbreak = value;
		setToolTips();
	}
	
	public void setToolTips(){
		lbl_min.setToolTipText("Lv1");
		lbl_max.setToolTipText("LvMax");
		lbl_max2.setToolTipText("LvMax & 親密"+ friendship);
		lbl_min_a.setToolTipText("Lv1");
		lbl_max_a.setToolTipText("LvMax");
		lbl_max2_a.setToolTipText("LvMax & 親密"+ friendship);
		lbl_max3_a.setToolTipText("LvMax & 突破"+limitbreak);
		lbl_max4_a.setToolTipText("LvMax & 親密"+ friendship +" & 突破"+limitbreak);
	}
}
