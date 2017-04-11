package objects;

import java.util.Hashtable;

public class VoiceInfo {
	protected Hashtable<String, String> params;
	
	public VoiceInfo(){
		params = new Hashtable<String, String>();
	}
	
	public void setValue(String key, String value){
		params.put(key, value);
	}
	
	public String getValue(String key){
		if (!params.containsKey(key)){
//			System.out.println("Error: getValue key="+key+" is empty");
			return "";
		}
		if (params.get(key).equals(null)){
//			System.out.println("Error: getValue key="+key+" is null");
			return "";
		}
		return params.get(key);
	}
}
