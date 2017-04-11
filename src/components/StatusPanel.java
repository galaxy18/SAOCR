package components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import objects.SAOCRData;
import configs.Params;
import frames.MainFrame;


@SuppressWarnings("serial")
public class StatusPanel extends JPanel implements Params{
	private CardLayout cardManager = new CardLayout();

	private TranslucentPanel contentPane = new TranslucentPanel();
	private JPanel statusPane = new JPanel();
	private ImagePanel infoPane = new ImagePanel();
	private VoicePanel voicePanel = new VoicePanel();
	
	private JRadioButton type1 = new JRadioButton();
	private JRadioButton type2 = new JRadioButton();
	private JRadioButton type3 = new JRadioButton();
	private JRadioButton type4 = new JRadioButton();
	
	private JLabel lbl_Name = new JLabel("    UNKNOWN");
	private JLabel lbl_Status = new JLabel("0000");
	private JLabel lbl_Element = new JLabel("");
	private JLabel lbl_COST = new JLabel("    UNKNOWN");
	private JLabel lbl_WEAPON = new JLabel("    UNKNOWN");
	private ImagePanel lbl_ICON = new ImagePanel();
	private TranslucentLabel txp_description = new TranslucentLabel();
	private JCheckBox chk_awake = new JCheckBox(filter_AWAKE, true);
	private JComboBox<String> cmb_Friendship = new JComboBox<String>();
	private JComboBox<String> cmb_LimitBreak = new JComboBox<String>();
	private Vector<BasicStatusPanel> lblStatus = new Vector<BasicStatusPanel>();
	private BattleAbilityPanel pan_BA = new BattleAbilityPanel();
	private SwordSkillPanel pan_SS = new SwordSkillPanel();
	private LeaderSkillPanel pan_LS = new LeaderSkillPanel();
	
	private String id = "1";
	private String type = "1";

	private JLabel erase = new JLabel("[X]");
	private JTextField txt_code = new JTextField();
	private JLabel btn_switch = new JLabel("<- ->");
	private JLabel btn_voice = new JLabel("[V]");
	private String currentPage = page_STATUS;
	private boolean showVoice = false;
	
	public StatusPanel(){
		super();
    	
    	lblStatus.add(new BasicStatusPanel("HP min ", "HP max ", "hp_min", "hp_max"));
    	lblStatus.add(new BasicStatusPanel("STR min ", "STR max ", "str_min", "str_max"));
    	lblStatus.add(new BasicStatusPanel("VIT min ", "VIT max ", "vit_min", "vit_max"));
    	lblStatus.add(new BasicStatusPanel("INT min ", "INT max ", "int_min", "int_max"));
    	lblStatus.add(new BasicStatusPanel("MEN min ", "MEN max ", "men_min", "men_max"));
    	
    	this.setLayout(new BorderLayout());
    	JPanel btnPane = new JPanel(new BorderLayout());
    	commonFormatting(btnPane);
    	btnPane.setOpaque(true);
    	btnPane.add(erase, BorderLayout.WEST);
    	btnPane.add(txt_code, BorderLayout.CENTER);
	    	JPanel btnPaneEAST = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
	    	btnPaneEAST.add(btn_voice);
	    	btnPaneEAST.add(btn_switch);
	    	commonFormatting(btnPaneEAST);
	    btnPane.add(btnPaneEAST, BorderLayout.EAST);
    	
    	this.add(btnPane, BorderLayout.NORTH);
    	this.add(contentPane, BorderLayout.CENTER);
    	
    	contentPane.setLayout(cardManager);
    	contentPane.add(statusPane, page_STATUS);
    	contentPane.add(infoPane, page_INFO);
    	contentPane.add(voicePanel, page_VOICE);

    	statusPane.setLayout(new BorderLayout());
    	commonFormatting(statusPane);
    	
//    	JPanel basicPanel = new JPanel(new GridLayout(6,1));
    	JPanel basicPanel = new JPanel(new BorderLayout());
    	commonFormatting(basicPanel);
    	JPanel basicStaturPanel = new JPanel(new GridLayout(5,1));
    	commonFormatting(basicStaturPanel);

    	infoPane.setLayout(new BorderLayout());
    	commonFormatting(infoPane);
    	
    	txp_description.setFont(infofont);
    	SimpleAttributeSet attr = new SimpleAttributeSet();
    	attr.addAttributes(txp_description.getParagraphAttributes());
    	StyleConstants.setLineSpacing(attr, -0.4f);
    	txp_description.setParagraphAttributes(attr, true);
    	txp_description.setBackground(Color.black);
    	txp_description.setBorder(BorderFactory.createEmptyBorder(0, 5, 40, 5));
    	
    	infoPane.add(txp_description, BorderLayout.SOUTH);
    	
    	basicPanel.add(createBasicPanel(), BorderLayout.NORTH);

    	for (int i = 0; i < lblStatus.size(); i++){
    		basicStaturPanel.add(lblStatus.get(i));
    	}
    	basicPanel.add(basicStaturPanel, BorderLayout.CENTER);
    	
    	JPanel skillPanel = new JPanel(new BorderLayout());
    	commonFormatting(skillPanel);
    	skillPanel.add(pan_BA,BorderLayout.NORTH);
    	skillPanel.add(pan_SS,BorderLayout.CENTER);
    	skillPanel.add(pan_LS,BorderLayout.SOUTH);
    	pan_BA.setPreferredSize(new Dimension(statusPanelWidth, statusRowHeight * 7));
    	pan_LS.setPreferredSize(new Dimension(statusPanelWidth, statusRowHeight * 3));
    	skillPanel.setPreferredSize(new Dimension(statusPanelWidth, statusRowHeight * 10));
    	
    	JPanel TopPane = new JPanel(new BorderLayout());
    	
    	commonFormatting(erase, TYPE_LABEL);
    	
    	txt_code.setFont(digitalfont);
    	txt_code.setForeground(color_codeFore);
//    	txt_code.setBackground(color_codeBack);
    	txt_code.setOpaque(false);
    	txt_code.setBorder(BorderFactory.createMatteBorder(20,120,0,80, Color.black));
    	txt_code.setHorizontalAlignment(SwingConstants.CENTER);
    	txt_code.setColumns(9);
    	
    	txt_code.addKeyListener(new KeyAdapter(){
    		public void keyPressed(KeyEvent e) {
    			if (e.getKeyCode() == KeyEvent.VK_ENTER){
    				if (!showValue(txt_code.getText())){
    					txt_code.setForeground(color_codeError);
    				}
    				else{
    					txt_code.setForeground(color_codeApply);
    					infoPane.revalidate();
    					infoPane.repaint();
    				}
    			}
    			else if (txt_code.getForeground() != color_codeFore){
					txt_code.setForeground(color_codeFore);
    			}
    		}
    	});

    	commonFormatting(btn_switch, TYPE_LABEL);
    	
    	btn_switch.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			switchContent();
    		};
    	});
    	
    	commonFormatting(btn_voice, TYPE_LABEL);
    	btn_voice.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			if (!showVoice){
        			cardManager.show(contentPane, page_VOICE);
    			}
    			else{
        			cardManager.show(contentPane, currentPage);
    			}
    			showVoice = !showVoice;
    		};
    	});

//    	TopPane.add(erase,BorderLayout.WEST);
    	TopPane.add(createTypePanel(),BorderLayout.EAST);
    	TopPane.setPreferredSize(new Dimension(statusPanelWidth, optionRowHeight));
    	commonFormatting(TopPane);
    	
    	statusPane.add(TopPane,BorderLayout.NORTH);
    	statusPane.add(basicPanel,BorderLayout.CENTER);
    	statusPane.add(skillPanel,BorderLayout.SOUTH);
    	
    	statusPane.setPreferredSize(new Dimension(statusPanelWidth, 0));
    	statusPane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,190), 10));
    	infoPane.setPreferredSize(new Dimension(statusPanelWidth, 0));
    	infoPane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,190), 10));
		voicePanel.setPreferredSize(new Dimension(statusPanelWidth, 0));
		voicePanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,190), 10));

//    	contentPane.setBackground(Color.red);
//    	contentPane.setOpaque(true);

    	this.setBackground(Color.black);
    	this.setOpaque(false);
	}
	
	public void switchContent() {
		showVoice = false;
		if (page_INFO.equals(currentPage)){
			currentPage = page_STATUS;
		}
		else {
			currentPage = page_INFO;
		}
		cardManager.show(contentPane, currentPage);
	}

	private JPanel createTypePanel() {
		ButtonGroup btnGroup = new ButtonGroup();
		
    	JPanel pan_type = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    	pan_type.setPreferredSize(new Dimension(statusPanelWidth, optionRowHeight));
    	commonFormatting(pan_type);
    	
    	type1.addMouseListener(new MouseAdapter(){
    		 public void mousePressed(MouseEvent e) {
     			changeType("1");
    		 };
    	});
    	type2.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			changeType("2");
    		};
    	});
    	type3.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			changeType("3");
    		};
    	});
    	type4.addMouseListener(new MouseAdapter(){
    		public void mousePressed(MouseEvent e) {
    			changeType("4");
    		};
    	});
    	
    	type1.setText(typetextlist[0]);
    	type2.setText(typetextlist[1]);
    	type3.setText(typetextlist[2]);
    	type4.setText(typetextlist[3]);

    	pan_type.add(new JLabel("  "));
    	pan_type.add(type1);
    	pan_type.add(type2);
    	pan_type.add(type3);
    	pan_type.add(type4);
    	
    	btnGroup.add(type1);
    	btnGroup.add(type2);
    	btnGroup.add(type3);
    	btnGroup.add(type4);

    	commonFormatting(type1, TYPE_NAME_RADIO);
    	commonFormatting(type2, TYPE_NAME_RADIO);
    	commonFormatting(type3, TYPE_NAME_RADIO);
    	commonFormatting(type4, TYPE_NAME_RADIO);
    	type1.setForeground(color_TYPE1);
    	type2.setForeground(color_TYPE2);
    	type3.setForeground(color_TYPE3);
    	type4.setForeground(color_TYPE4);
    	
    	type1.setSelected(true);
    	type1.setForeground(color_STATUS_A);
    	
//    	this.add(pan_type);
    	return pan_type;
	}
	
	public void changeType(String arg_type){
		type1.setForeground(color_TYPE1);
		type2.setForeground(color_TYPE2);
		type3.setForeground(color_TYPE3);
		type4.setForeground(color_TYPE4);
		type=arg_type;
		String afterID = SAOCRData.id2awake.get(id);
		int status = 0;
		int status_A = 0;
    	for (int i = 0; i < lblStatus.size(); i++){
    		status += lblStatus.get(i).showValue(SAOCRData.id2data,id, type);
    		if (chk_awake.isSelected()){
    			status_A += lblStatus.get(i).showValue_a(SAOCRData.id2data,afterID, type);
    		}
    	}
    	
    	if (chk_awake.isSelected()){
    		lbl_Status.setText(String.valueOf(status_A)+" Pts");
	    	pan_BA.showValue(SAOCRData.id2data,SAOCRData.id2unitskill,afterID+type);
    	}
    	else{
    		lbl_Status.setText(String.valueOf(status)+" Pts");
	    	pan_BA.showValue(SAOCRData.id2data,SAOCRData.id2unitskill,id+type);
    	}
    	
    	if("1".equals(type)){
	    	type1.setSelected(true);
			type1.setForeground(color_STATUS_A);
    	}
    	else if("2".equals(type)){
	    	type2.setSelected(true);
			type2.setForeground(color_STATUS_A);
    	}
    	else if("3".equals(type)){
	    	type3.setSelected(true);
			type3.setForeground(color_STATUS_A);
    	}
    	else if("4".equals(type)){
	    	type4.setSelected(true);
			type4.setForeground(color_STATUS_A);
    	}
		MainFrame.saveType(this, arg_type);
	}

	private JPanel createBasicPanel() {
		JPanel pan_basicData = new JPanel();
    	pan_basicData.setLayout(new BorderLayout());
    	
    	JPanel pan_name = new JPanel(new BorderLayout());
    	commonFormatting(pan_name);
    	
    	commonFormatting(lbl_Name, TYPE_LABEL);

    	commonFormatting(lbl_Status, TYPE_DIGITAL_BTN);
		lbl_Status.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Status.setForeground(color_BA_power);
    	
    	pan_name.add(lbl_Name, BorderLayout.WEST);
    	pan_name.add(lbl_Status, BorderLayout.NORTH);
		
		JPanel pan_cost = new JPanel(new GridBagLayout());
		commonFormatting(pan_cost);

    	commonFormatting(chk_awake, TYPE_DIGITAL_BTN);
		chk_awake.setHorizontalAlignment(SwingConstants.LEFT);
		
		chk_awake.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if (!chk_awake.isEnabled())
					return;
					
				setAwake();
			};
		});

    	commonFormatting(cmb_Friendship, TYPE_DIGITAL_BTN);
    	cmb_Friendship.addItem("0");
    	for (int i = 100; i <= 200; i+=10)
    		cmb_Friendship.addItem(String.valueOf(i));
    	
    	cmb_Friendship.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String value = cmb_Friendship.getSelectedItem().toString();
		    	for (int i = 0; i < lblStatus.size(); i++){
		    		lblStatus.get(i).setFriendShip(value);
		    	}
			}
    	});
    	cmb_Friendship.setSelectedIndex(1);
    	
    	commonFormatting(cmb_LimitBreak, TYPE_DIGITAL_BTN);
    	for (int i = 0; i < 4; i++)
    		cmb_LimitBreak.addItem(String.valueOf(i));
    	
    	cmb_LimitBreak.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String value = cmb_LimitBreak.getSelectedItem().toString();
		    	for (int i = 0; i < lblStatus.size(); i++){
		    		lblStatus.get(i).setLimitBreak(value);
		    	}
			}
    	});
    	cmb_LimitBreak.setSelectedIndex(0);

    	commonFormatting(lbl_Element, TYPE_LABEL);
    	commonFormatting(lbl_WEAPON, TYPE_LABEL);
    	commonFormatting(lbl_COST, TYPE_LABEL);
		
    	GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pan_cost.add(lbl_Element, c);
		c.gridx = 1;
		pan_cost.add(lbl_WEAPON, c);
		c.gridx = 2;
		pan_cost.add(lbl_COST, c);
		c.gridx = 0;
		c.gridy = 1;
		pan_cost.add(chk_awake, c);
		c.gridx = 1;
		pan_cost.add(cmb_Friendship, c);
		c.gridx = 2;
		pan_cost.add(cmb_LimitBreak, c);
		
		commonFormatting(pan_basicData);
		pan_basicData.add(pan_name, BorderLayout.NORTH);
		pan_basicData.add(pan_cost, BorderLayout.CENTER);
		
		lbl_ICON.setPreferredSize(new Dimension(iconWidth, iconWidth));
		lbl_ICON.imageheight = Double.parseDouble(String.valueOf(iconWidth));
		lbl_ICON.isicon = true;
		lbl_ICON.setOpaque(false);
		
		JPanel panel = new JPanel(new BorderLayout());
		commonFormatting(panel);
		panel.add(pan_basicData);
		panel.add(lbl_ICON, BorderLayout.EAST);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
//		this.add(pan_basicData);
		return panel;
	}
	
	public void setAwake(boolean value) {
		chk_awake.setSelected(!value);
		setAwake();
		chk_awake.setSelected(value);
	}
	
	private void setAwake() {
		int index = SAOCRData.id2data.get(headerRow_key).indexOf(header_cost);
		String afterID = SAOCRData.id2awake.get(id);
		if (SAOCRData.id2data.get(afterID+defaultType) == null){
			chk_awake.setSelected(true);
			return;
		}
		
		if (!chk_awake.isSelected()){
			int status = 0;
	    	for (int i = 0; i < lblStatus.size(); i++){
	    		status += lblStatus.get(i).showValue_a(SAOCRData.id2data,afterID, type);
	    	}
	    	lbl_Status.setText(String.valueOf(status)+" Pts");
			lbl_COST.setText("C"+SAOCRData.id2data.get(afterID+defaultType).get(index));
			pan_BA.showValue(SAOCRData.id2data,SAOCRData.id2unitskill,afterID+type);
			pan_SS.showValue(SAOCRData.id2data, SAOCRData.id2swordskill, SAOCRData.chara2swordskill10, SAOCRData.weapon2swordskill10, afterID+defaultType);
		}
		else{
			int status = 0;
	    	for (int i = 0; i < lblStatus.size(); i++){
	    		status += lblStatus.get(i).showValue(SAOCRData.id2data,id, type);
	    	}
	    	lbl_Status.setText(String.valueOf(status)+" Pts");
    		lbl_COST.setText("C"+SAOCRData.id2data.get(id+defaultType).get(index));
			pan_BA.showValue(SAOCRData.id2data,SAOCRData.id2unitskill,id+type);
			pan_SS.showValue(SAOCRData.id2data, SAOCRData.id2swordskill, SAOCRData.chara2swordskill9, SAOCRData.weapon2swordskill9, id+defaultType);
		}
	}

	public boolean showValue(String arg_id){
		if (SAOCRData.id2data.get(arg_id+defaultType) == null){
			return false;
		}
		boolean usereplace = new File(image_replace).exists();
			
		id = arg_id;
		txt_code.setText(id);
		String afterID = "";
		chk_awake.setSelected(true);
		if (SAOCRData.awake2id.containsKey(id)){
			id = SAOCRData.awake2id.get(id);
		}
		if (SAOCRData.id2awake.containsKey(id))
			afterID = SAOCRData.id2awake.get(id);
		
		if (SAOCRData.id2data.get(afterID+defaultType) != null){
			chk_awake.setEnabled(true);
			chk_awake.setText("Awake");
		}
		else{
			chk_awake.setEnabled(false);
			chk_awake.setSelected(false);
			chk_awake.setText("Disabled");
		}
		
		int index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name_header);
		int index2 = SAOCRData.id2chara.get(headerRow_key).indexOf(header_name);
		int index3 = SAOCRData.id2chara.get(headerRow_key).indexOf(header_rarity);
		
		String rare = SAOCRData.id2chara.get(id).get(index3).substring(0,1);
		
		if (SAOCRData.id2chara.get(id).get(index) == null || "".equals(SAOCRData.id2chara.get(id).get(index))){
			lbl_Name.setText(SAOCRData.id2chara.get(id).get(index2));
		}
		else{
			lbl_Name.setText(SAOCRData.id2chara.get(id).get(index)+SAOCRData.id2chara.get(id).get(index2));
		}
		if(usereplace)
			lbl_Name.setIcon(new ImageIcon(icons.raritys[Integer.parseInt(rare)]));
		else
			lbl_Name.setText(label_AWAKE+rare+lbl_Name.getText());
		
		switch (id.charAt(0)){
    		case flag_SAO:
    			if (image_SAO.getImageLoadStatus() != java.awt.MediaTracker.ERRORED)
    				lbl_Element.setIcon(image_SAO);
    			else
    				lbl_Element.setText(world_SAO);
    			break;
    		case flag_ALO:
    			if (image_ALO.getImageLoadStatus() != java.awt.MediaTracker.ERRORED)
    				lbl_Element.setIcon(image_ALO);
    			else
    				lbl_Element.setText(world_ALO);
    			break;
    		case flag_GGO:
    			if (image_GGO.getImageLoadStatus() != java.awt.MediaTracker.ERRORED)
    				lbl_Element.setIcon(image_GGO);
    			else
    				lbl_Element.setText(world_GGO);
    			break;
    		default: 
    			lbl_Element.setIcon(null);
    			lbl_Element.setText("");
    			break;
		}
		
		index = SAOCRData.id2data.get(headerRow_key).indexOf(header_element);
		int Element = Integer.parseInt(SAOCRData.id2data.get(id+defaultType).get(index));
		lbl_Element.setText(lbl_Element.getText()+world_Padding+elementtextlist[Element-1]);
		switch (Element){
    		case 1:
    			lbl_Element.setForeground(color_ELEMENT1);
    			this.setBackground(color_ELEMENT1);
    			float[] color = color_ELEMENT1.darker().darker().getComponents(null);
    			contentPane.setColor(new Color(color[0], color[1], color[2], .9f));
    			contentPane.repaint();
    			contentPane.revalidate();
    			break;
    		case 2: 
    			lbl_Element.setForeground(color_ELEMENT2);
    			this.setBackground(color_ELEMENT2);
    			color = color_ELEMENT2.darker().darker().getComponents(null);
    			contentPane.setColor(new Color(color[0], color[1], color[2], .9f));
    			contentPane.repaint();
    			contentPane.revalidate();
    			break;
    		case 3: 
    			lbl_Element.setForeground(color_ELEMENT3);
    			this.setBackground(color_ELEMENT3);
    			color = color_ELEMENT3.darker().darker().getComponents(null);
    			contentPane.setColor(new Color(color[0], color[1], color[2], .9f));
    			contentPane.repaint();
    			contentPane.revalidate();
    			break;
		}
		lbl_Name.setForeground(lbl_Element.getForeground());
		lbl_WEAPON.setForeground(lbl_Element.getForeground());
		lbl_COST.setForeground(lbl_Element.getForeground());
		chk_awake.setForeground(lbl_Element.getForeground());
		
		index = SAOCRData.id2data.get(headerRow_key).indexOf(header_weaponcategory);
		lbl_WEAPON.setText(SAOCRData.id2weaponcategory.get(SAOCRData.id2data.get(id+defaultType).get(index)));
		lbl_WEAPON.setIcon(new ImageIcon(icons.weapons[Integer.parseInt(SAOCRData.id2data.get(id+defaultType).get(index))-1]));
		
		index = SAOCRData.id2data.get(headerRow_key).indexOf(header_cost);
		if (chk_awake.isSelected()){
			lbl_COST.setText("C"+SAOCRData.id2data.get(afterID+defaultType).get(index));
	    	pan_BA.showValue(SAOCRData.id2data,SAOCRData.id2unitskill,afterID+type);
	    	pan_SS.showValue(SAOCRData.id2data, SAOCRData.id2swordskill, SAOCRData.chara2swordskill10, SAOCRData.weapon2swordskill10, afterID+defaultType);
		}
		else{
			lbl_COST.setText("C"+SAOCRData.id2data.get(id+defaultType).get(index));
	    	pan_BA.showValue(SAOCRData.id2data,SAOCRData.id2unitskill,id+type);
	    	pan_SS.showValue(SAOCRData.id2data, SAOCRData.id2swordskill, SAOCRData.chara2swordskill9, SAOCRData.weapon2swordskill9, id+defaultType);
		}
		int status = 0;
		int status_A = 0;
    	for (int i = 0; i < lblStatus.size(); i++){
    		status += lblStatus.get(i).showValue(SAOCRData.id2data,id, type);
    		if (chk_awake.isSelected()){
    			status_A += lblStatus.get(i).showValue_a(SAOCRData.id2data,afterID, type);
    		}
    	}
    	if (chk_awake.isSelected()){
    		lbl_Status.setText(String.valueOf(status_A)+" Pts");
    	}
    	else{
    		lbl_Status.setText(String.valueOf(status)+" Pts");
    	}
    	pan_LS.showValue(SAOCRData.id2awake, SAOCRData.chara2leaderskill, SAOCRData.id2leaderskill, id);
    	
    	index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_description);
    	txp_description.setText(SAOCRData.id2chara.get(id).get(index));

    	infoPane.seturl(folder_large+id+".png");

		index = SAOCRData.id2chara.get(headerRow_key).indexOf(header_voice_name);
		voicePanel.showValue(SAOCRData.id2chara.get(id).get(index));

		File icon1 = new File(folder_icon+id+".png");
		File icon2 = new File(folder_icon+id+"1.png");
		//File icon3 = new File(folder_icon+"web_"+id+"1.png");
		if (icon1.exists()){
			lbl_ICON.seturl(folder_icon+id+".png");
			lbl_ICON.setToolTipText(folder_icon+id+".png");
		}
		else if (icon2.exists()){
			lbl_ICON.seturl(folder_icon+id+"1.png");
			lbl_ICON.setToolTipText(folder_icon+id+"1.png");
		}
		else {
			lbl_ICON.seturl(folder_icon+"web_"+id+"1.png");
			lbl_ICON.setToolTipText(folder_icon+"web_"+id+"1.png");
		}
		lbl_ICON.repaint();
		
		return true;
	}
	
	public JLabel getBtn(){
		return erase;
	}

	public void setHeight(int height) {
		this.setMaximumSize(new Dimension(statusPanelWidth,height));
		infoPane.imageheight = Double.parseDouble(String.valueOf(height));
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
		comp.setFont(namefont);
		comp.setOpaque(false);
	}

}
