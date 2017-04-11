package configs;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

import objects.Icons;

public interface Params {
	public final String Version = "2.24";
	
	public Icons icons = new Icons();
	
	public final Font namefont = Loadfont.loadFont("resources/RodinNTLGPro-B.ttf", 14f);
	public final Font font = Loadfont.loadFont("resources/RodinNTLGPro-B.ttf", 15f);
	public final Font digitalfont = Loadfont.loadFont("resources/saofontregularv1.ttf", 18f);
	public final Font progressdigitalfont = Loadfont.loadFont("resources/saofontregularv1.ttf", 36f);
	
	public final Font infofont = Loadfont.loadFont("resources/RodinNTLGPro-B.ttf", 14f);
	
	public final Font extabledigitalfont = Loadfont.loadFont("resources/saofontregularv1.ttf", 16f);
	public final Font extableheaderfont = Loadfont.loadFont("resources/saofontregularv1.ttf", 14f);
	public final Font extablefont = Loadfont.loadFont("resources/RodinNTLGPro-B.ttf", 13f);
	public final Font extableBAnamefont = Loadfont.loadFont("resources/RodinNTLGPro-B.ttf", 11f);
	
	public final Font pltablefont = Loadfont.loadFont("resources/CNFont.ttf", 15f);

	public final String path_armor			= "resources/CSV/m_armor.csv";
	public final String path_awake 			= "resources/CSV/m_character_awake_receipt.csv";
	public final String path_awake_ls 		= "resources/CSV/m_character_awake_leader_skill.csv";
	public final String path_battle_ability = "resources/CSV/m_unit_skill.csv";
	public final String path_character 		= "resources/CSV/m_npc_character.csv";
	public final String path_charakind 		= "resources/CSV/m_character_kind.csv";
	public final String path_equreceipt		= "resources/CSV/m_equipment_receipt.csv";
	public final String path_equreceiptbd	= "resources/CSV/m_equipment_receipt_break_down.csv";
	public final String path_leader_skill 	= "resources/CSV/m_leader_skill.csv";
	public final String path_material 		= "resources/CSV/m_material.csv";
	public final String path_monster 		= "resources/CSV/m_monster.csv";
	public final String path_quest	 		= "resources/CSV/m_quest.csv";
	public final String path_ss_define 		= "resources/CSV/m_sword_skill.csv";
	public final String path_ss_normal 		= "resources/CSV/m_npc_sword_skill_proficiency.csv";
	public final String path_ss_unique 		= "resources/CSV/m_npc_unique_sword_skill_proficiency.csv";
	public final String path_weapon 		= "resources/CSV/m_weapon.csv";
	public final String path_weaponcategory = "resources/CSV/m_weapon_category.csv";
	public final String path_weapongroup	= "resources/CSV/m_weapon_generate_group.csv";
	public final String path_weaponquality	= "resources/CSV/m_weapon_quality.csv";

	public final int fmWidth = 850;
	public final int fmHeight = 650;
	public final int exfmWidth = 900;
	public final int exfmHeight = 500;
	
	public final int statusPanelWidth = 400;
	public final int optionRowHeight = 30;
	public final int tableRowHeight = 25;
	public final int statusRowHeight = 20;
	public final int extableRowHeight = 132;
	
	public final int iconWidth = 120;
	
	public final String defaultType = "1";
	
	public final String headerRow_key = "ID";

	public final String header_name = "name";
	public final String header_name_english = "name_english";
	public final String header_name_header = "head_name";
	public final String header_name_longname = "long_name";
	public final String header_voice_name = "character_voice_resource_name";
	
	public final String header_description = "description";
	public final String header_cost = "cost";
	public final String header_element = "m_elemental_id";
	public final String header_awake_before = "m_character_kind_id";
	public final String header_awake_after = "m_after_character_kind_id";
	public final String header_leader_skill = "m_leader_skill_id";
	public final String header_weaponcategory = "m_weapon_category_id";
	public final String header_BA1_id = "m_unit_skill_1_id";
	public final String header_BA2_id = "m_unit_skill_2_id";
	public final String header_BA3_id = "m_unit_skill_3_id";
	public final String header_power = "power";
	public final String header_healrate = "heal_rate";
	public final String header_rarity = "m_rarity_id";
	public final String header_ss_id = "m_sword_skill_id";
	public final String header_ss_chara = "m_npc_character_id";
	public final String header_ss_weaponlevel = "weapon_category_level";
	
	public final String header_quest_group = "m_quest_group_id";
	public final String header_stamina = "required_stamina";
	public final String header_player_exp = "gained_player_exp";
	public final String header_character_exp = "gained_character_exp";
	public final String header_money = "gained_money";
	public final String header_start_id = "m_start_story_id";
	public final String header_end_id = "m_end_story_id";
	
	public final String header_weaponquality = "m_weapon_quality";
	public final String header_equid = "m_equipment_id";
	public final String header_equreceiptid = "m_equipment_receipt_id";
	public final String header_price = "price";
	public final String header_starttime = "start_at";
	public final String header_endtime = "end_at";
	
	public final String world_SAO = "SAO";
	public final String world_ALO = "ALO";
	public final String world_GGO = "GGO";
	public final String world_OTHER = "EX";
	
	public final String filter_AWAKE = "AWAKE";
	public final String label_AWAKE = "★";
	public final String non_awake = "NORMAL";
	
	public final String page_STATUS = "page_STATUS";
	public final String page_INFO = "page_INFO";
	public final String page_VOICE = "page_VOICE";
	
	public final char flag_SAO = '1';
	public final char flag_ALO = '2';
	public final char flag_GGO = '3';
	public final char flag_EX = '4'; 
	
	public final String TYPE_CHKBOX = "chkbox";
	public final String TYPE_LABEL = "label";
	public final String TYPE_NAME_LABEL = "name_radio_label";
	public final String TYPE_DIGITAL_LABEL = "digital_label";
	public final String TYPE_DIGITAL_BTN = "digital_button";
	public final String TYPE_BUTTON = "button";
	public final String TYPE_NAME_RADIO = "name_radio_button";
	
	public final String[] typetextlist = {"バランス型","攻撃型","防御型","スキル型"};
	public final String[] elementtextlist = {"火","風","水"};
	public final String[] weaponqualitylist = {"1","2","3","4"};
	
	public final String TYPE_QUEST = "tbl_quest";
	public final String TYPE_MONSTER = "tbl_monster";

	public final Color color_SKILL_title = new Color(0x1A8193);
	public final Color color_UniqueSS_title = new Color(204, 51, 255);
	public final Color color_BA_name = new Color(0xE4FF00);
	public final Color color_BA_power = new Color(0x01CBCB);
	
	public final Color color_ELEMENT1 = new Color(0xFF4500);
	public final Color color_ELEMENT2 = new Color(0x00FF00);
	public final Color color_ELEMENT3 = new Color(0x00FFFF);

	public final Color color_STATUS = new Color(0xFFFFFF);
	public final Color color_STATUS_A = new Color(136,147,161);

	public final Color color_TYPE1 = new Color(0x00FF00);
	public final Color color_TYPE2 = new Color(0xFF4500);
	public final Color color_TYPE3 = new Color(0x00FFFF);
	public final Color color_TYPE4 = new Color(204, 51, 255);
	
	public final Color color_back = new Color(0x01CBCB);
	
	public final Color color_codeBack = new Color(0x1A8193);
	public final Color color_codeFore = new Color(0xFFFFFF);
	public final Color color_codeError = new Color(0xE4FF00);
	public final Color color_codeApply = new Color(136,147,161);

	public final String folder_large = "resources/large/";
	public final String folder_icon = "resources/icon/";
	public final String folder_gacha = "resources/gacha/";
	public final String folder_charaep = "resources/PLCharaEpisode/";
	public final String folder_scenario = "resources/Scenario/";
	
	public final ImageIcon image_SAO = new ImageIcon(icons.worlds[2]);//new ImageIcon("resources/worldSAO.png");
	public final ImageIcon image_ALO = new ImageIcon(icons.worlds[0]);//new ImageIcon("resources/worldALO.png");
	public final ImageIcon image_GGO = new ImageIcon(icons.worlds[1]);//new ImageIcon("resources/worldGGO.png");

	public ImageIcon Menuicon1 = new ImageIcon("resources/btn_normal.png");
	public ImageIcon Menuicon2 = new ImageIcon("resources/btn_press.png");
	public ImageIcon Menubar1 = new ImageIcon("resources/list_normal.png");
	public ImageIcon Menubar2 = new ImageIcon("resources/list_press.png");
	
	public final String image_Default = "resources/large/default.png";
	public final String image_icon = "resources/icon-s539e4d67d3.png";
	public final String image_replace = "resources/replace-s9066e29304.png";
	
	public final String voice_root = "resources/VO_";
	public final String voice_folder = "/voice/";
	public final String voice_conf = "/Config.xml";

	public final String LIST_BATTLE_Action = "battleActionList";
		public final String INFO_BATTLE_Action = "battleActionInfo";
	public final String LIST_COMMON_Action = "CommonActionList";
		public final String INFO_COMMON_Action = "CommonActionInfo";
	public final String LIST_NORMAL_Action = "NormalActionList";
		public final String INFO_NORMAL_Action = "NormalActionInfo";
	public final String LIST_OTHER = "OtherVoices";
		public final String INFO_OTHER = "OtherVoiceInfo";

	public final String FLAG_CR_ID = "id";
	public final String FLAG_BATTLE_ACTION_ID = "id";
	public final String FLAG_BATTLE_ACTION_NAME = "name";
	public final String FLAG_BATTLE_ACTION_TEXT = "text";
	public final String FLAG_COMMON_ACTION_ID = "id";
	public final String FLAG_COMMON_ACTION_NAME = "name";
	public final String FLAG_COMMON_ACTION_TEXT = "text";
	public final String FLAG_NORMAL_ACTION_ID = "id";
	public final String FLAG_NORMAL_ACTION_NAME = "name";
	public final String FLAG_NORMAL_ACTION_TEXT = "text";
	public final String FLAG_OTHER_ID = "id";
	public final String FLAG_OTHER_NAME = "name";
	public final String FLAG_OTHER_TEXT = "text";

	public final String name_Padding = "   ";
	public final String world_Padding = "   ";
	public final String cost_Padding = "       ";
	
	public final String myname = "Galaxy17";
	public final String mynamereplace = "%username%";
}
