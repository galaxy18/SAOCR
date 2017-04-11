package saocrMain;

import configs.LoadCSVs;
import configs.Params;
import frames.MainMenu;
import frames.ProgressFrame;

public class SAOCR implements Params{
	
	public static void main(String[] args) throws Exception{
//		LoadCSVs.readMazeData();
		
		LoadCSVs.readScenario(false);
		
		@SuppressWarnings("unused")
		ProgressFrame progress = new ProgressFrame();
		LoadCSVs.readFile(new LoadCSVs().extract());
		LoadCSVs.readData();
		LoadCSVs.readSaveData();
		
		LoadCSVs.readPLDATA();
		LoadCSVs.readScenarioDATA();

		@SuppressWarnings("unused")
		MainMenu menu = new MainMenu();
	}
}