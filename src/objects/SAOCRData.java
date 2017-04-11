package objects;

import java.util.Hashtable;
import java.util.Vector;

public class SAOCRData {
	public static Vector<Vector<String>> savedata = new Vector<Vector<String>>();
	public static Hashtable<String, Vector<String>> id2armor = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2chara = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2data = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2equipmentreceipt = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2leaderskill = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2monster = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2quest = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2swordskill = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2unitskill = new Hashtable<String, Vector<String>>();
	public static Hashtable<String, Vector<String>> id2weapongroup = new Hashtable<String, Vector<String>>();
	
	public static Hashtable<String, Hashtable<String, Vector<String>>> id2weapon = new Hashtable<String, Hashtable<String, Vector<String>>>();
	public static Hashtable<String, Vector<Vector<String>>> id2equipmentreceipbreakdown = new Hashtable<String, Vector<Vector<String>>>();
	
	public static Hashtable<String, String> awake2id = new Hashtable<String, String>();
	public static Hashtable<String, String> chara2leaderskill = new Hashtable<String, String>();
	public static Hashtable<String, String> chara2swordskill9 = new Hashtable<String, String>();
	public static Hashtable<String, String> chara2swordskill10 = new Hashtable<String, String>();
	public static Hashtable<String, String> id2awake = new Hashtable<String, String>();
	public static Hashtable<String, String> id2material = new Hashtable<String, String>();
	public static Hashtable<String, String> id2weaponquality = new Hashtable<String, String>();
	public static Hashtable<String, String> id2weaponcategory = new Hashtable<String, String>();
	public static Hashtable<String, String> weapon2swordskill9 = new Hashtable<String, String>();
	public static Hashtable<String, String> weapon2swordskill10 = new Hashtable<String, String>();
	public static Hashtable<String, String> id2story = new Hashtable<String, String>();
	
	public static Hashtable<String, Vector<String>> id2mazeblock = new Hashtable<String, Vector<String>>();
	public static Vector<Vector<String>> mazeterrain = new Vector<Vector<String>>();
	public static Vector<Vector<String>> mazemovable = new Vector<Vector<String>>();
}
