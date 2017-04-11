package configs;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import others.ExtractImage;

import objects.SAOCRData;

import com.opencsv.CSVReader;

import frames.ProgressFrame;

public class LoadCSVs implements Params{
	public File extract(){
		File file = new File("csv.assetbundle");
		if (!file.exists()){
			file = new File("input/csv.assetbundle");
		}

		if (file.exists()){
			file.renameTo(new File("resources/disunity","csv.assetbundle"));
			ProcessBuilder pb = new ProcessBuilder(
					"java",
					"-jar",
					System.getProperty("user.dir")+"\\resources\\disunity\\disunity.jar",
					"extract-raw",
			"csv.assetbundle");
			//				pb.directory(new File(System.getProperty("user.dir")+"\\disunity\\"));
			pb.directory(new File("resources/disunity"));
			pb. redirectErrorStream(true);

			try {
				Process p = pb.start();
				WatchThread wt = new WatchThread(p);
				wt.start();
				p.waitFor();
				wt.setOver(true);
				ProgressFrame.instance.addText(file.getPath()+" extracted");
			} catch (Exception e) {
			}
		}

		file = new File("resources/disunity/csv/");
		if (file.exists()){
			File[] files = file.listFiles();
			List<File> fileList = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory())
					fileList.add(files[i]);
			}

			Collections.sort(fileList, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					Date fileDate1 = new Date(o2.lastModified());
					Date fileDate2 = new Date(o1.lastModified());
					return fileDate1.compareTo(fileDate2);
				}
			});

			file = new File("resources/disunity/csv.assetbundle");
			file.renameTo(new File("resources/disunity/csv"+file.lastModified()+".assetbundle"));

			file = new File(fileList.get(0)+"/MonoBehaviour/");
			ProgressFrame.instance.addText(file.getPath());
			return file;
		}
		return null;
	}

	public static void readFile(File path) throws IOException {
		ProgressFrame.instance.addText("Loading input File...");
		File directory = new File("input");
		File output = new File("resources/CSV/");
		output.mkdirs();

		if (path != null)
			directory = path;

		ProgressFrame.instance.addText(directory.getPath());
		String filename = "";

		File[] files = directory.listFiles();
		if (files == null || files.length <= 0){
			ProgressFrame.instance.addText("not Found");
		}
		for (int i = 0; files != null && i < files.length && files[i].getName().endsWith(".bin"); i++){
			System.out.println("processing file "+files[i].getName()+"...");
			ProgressFrame.instance.addText("processing file "+files[i].getName()+"...");
			ProgressFrame.instance.addDigital(1.0f / files.length * 20.0f);

			InputStreamReader inputStream=new InputStreamReader(new FileInputStream(files[i]),"UTF-8");
			BufferedReader br = new BufferedReader(inputStream);
			String input = br.readLine();
			Pattern pattern = Pattern.compile("\\w+");
			Matcher matcher = pattern.matcher(input);
			while (!matcher.find()){
				input = br.readLine();
				matcher = pattern.matcher(input);
			}
			filename = matcher.group();

			while (input.indexOf("\"") < 0)
				input = br.readLine();
			input = input.substring(input.indexOf("\""));

			int count = findStr(input, "\"");
			if (count % 2 == 1){
				input = input.substring(1);
				input = input.substring(input.indexOf("\""));
			}

			OutputStreamWriter char_output = new OutputStreamWriter(
					new FileOutputStream("resources/CSV/"+filename+".csv"),
					Charset.forName("UTF-8").newEncoder() 
			);
			BufferedWriter bw = new BufferedWriter(char_output);

			while (input != null) {
				bw.write(input);
				bw.newLine();
				input = br.readLine();
			}

			//    	    bw.flush();
			br.close();
			inputStream.close();
			bw.close();
			char_output.close();
			//	    	    files[i].delete();
		}
		if (path != null){
			directory.renameTo(new File(directory.getPath()+directory.lastModified()));
		}
		ProgressFrame.instance.setDigital(20.0f);
		ProgressFrame.instance.addText("Load input File Finished");
	}

	public static void readMazeData() throws IOException{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("resources/CSV/m_maze_map.csv"), "UTF-8"));
		String[] dataArray;
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			/*
			 * "ID"	"name"		"x"	"y"	"height"	"image_resource_name"	"background_image_resource_name"
			 * 1	"洞窟A_y"	20	20	20			"cave_yellow"			"area_blue"
			 */
			SAOCRData.id2mazeblock.put(dataArray[0], row);
		}
		reader = new CSVReader(new InputStreamReader(new FileInputStream("resources/CSV/m_maze_map_terrain.csv"), "UTF-8"));
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.mazeterrain.add(row);
		}
		reader = new CSVReader(new InputStreamReader(new FileInputStream("resources/CSV/m_maze_map_movable.csv"), "UTF-8"));
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.mazemovable.add(row);
		}
		JFrame frm_maze = new JFrame();
		JTabbedPane tabbedPane = new JTabbedPane();
		JScrollPane scrollPane= new JScrollPane(tabbedPane);
		Hashtable<String,JTable> mazeTable = new Hashtable<String,JTable>();
		for (int i = 1; i < 10; i++){
			@SuppressWarnings("serial")
			DefaultTableModel model = new DefaultTableModel(){
				public boolean isCellEditable(int row, int column) {
			        return false;
			    }
			};
			JTable table = new JTable(model);
			table.setRowHeight(40);
			table.setCellSelectionEnabled(true);
	    	ListSelectionModel cellSelectionModel = table.getSelectionModel();
	    	cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			for (int j = 0; j < 20; j++){
				model.addColumn(String.valueOf(j+1));
				table.getColumnModel().getColumn(j).setPreferredWidth(40);
			}
			for (int j = 0; j < 20; j++){
				Vector<String> rowdata = new Vector<String>();
				for (int k = 0; k < 20; k++){
					rowdata.add(" "+j+"_"+k+" ");
				}
				model.addRow(rowdata);
			}
			tabbedPane.addTab(String.valueOf(i), table);
	    	mazeTable.put(String.valueOf(i), table);
		}
		for (int i = 1; i < SAOCRData.mazeterrain.size(); i++){
//			if (SAOCRData.mazeterrain.get(i).get(0).equals("1")){
			if (SAOCRData.mazeterrain.get(i).size() > 3){
				if (SAOCRData.mazeterrain.get(i).get(1).startsWith("cave_floor")){
					mazeTable.get(SAOCRData.mazeterrain.get(i).get(0)).setValueAt(" ", 
							Integer.parseInt(SAOCRData.mazeterrain.get(i).get(2))-1, 
							Integer.parseInt(SAOCRData.mazeterrain.get(i).get(3))-1);
				}
				else{
					mazeTable.get(SAOCRData.mazeterrain.get(i).get(0)).setValueAt(SAOCRData.mazeterrain.get(i).get(1).replaceAll("cave_", ""), 
							Integer.parseInt(SAOCRData.mazeterrain.get(i).get(2))-1, 
							Integer.parseInt(SAOCRData.mazeterrain.get(i).get(3))-1);
				}
			}
		}
		frm_maze.setBounds(10, 100, 900, 600);
		frm_maze.getContentPane().setLayout(new BorderLayout());
		frm_maze.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frm_maze.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm_maze.setVisible(true);
	}

	public static void readData() throws IOException{
		ProgressFrame.instance.addText("---------- -------");
		ProgressFrame.instance.addText("initializing Data...");
		ProgressFrame.instance.addText("Processing"+path_character);
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path_character), "UTF-8"));
		String[] dataArray;
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.id2data.put(dataArray[0], row);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_weaponcategory);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_weaponcategory), "UTF-8"));
		int index  = 0;
		dataArray = reader.readNext();
		for (int i = 0; i < dataArray.length; i++){
			if (header_name.equals(dataArray[i])){
				index = i;
				break;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			SAOCRData.id2weaponcategory.put(dataArray[0], dataArray[index]);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_battle_ability);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_battle_ability), "UTF-8"));
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.id2unitskill.put(dataArray[0], row);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_leader_skill);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_leader_skill), "UTF-8"));
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.id2leaderskill.put(dataArray[0], row);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_ss_define);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_ss_define), "UTF-8"));
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.id2swordskill.put(dataArray[0], row);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_weapon);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_weapon), "UTF-8"));
		index = 0;
		dataArray = reader.readNext();
		Hashtable<String, Vector<String>> data = new Hashtable<String, Vector<String>>();
		Vector<String> row = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			if (header_weaponquality.equals(dataArray[s])){
				index = s;
			}
			row.add(dataArray[s]);
		}
		data.put(defaultType, row);
		SAOCRData.id2weapon.put(headerRow_key, data);
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			if (!SAOCRData.id2weapon.containsKey(dataArray[1])){
				data = new Hashtable<String, Vector<String>>();
			}
			else{
				data = SAOCRData.id2weapon.get(dataArray[1]);
			}
			data.put(row.get(index), row);
			SAOCRData.id2weapon.put(dataArray[1], data);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_equreceipt);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_equreceipt), "UTF-8"));
		index = 0;
		dataArray = reader.readNext();
		row = new Vector<String>();
		for(int s = 0; s < dataArray.length; s++) {
			if (header_equid.equals(dataArray[s])){
				index = s;
			}
			row.add(dataArray[s]);
		}
		SAOCRData.id2equipmentreceipt.put(headerRow_key, row);
		while((dataArray = reader.readNext()) != null) {
			row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			if (!"".equals(dataArray[0].trim())){
				SAOCRData.id2equipmentreceipt.put(dataArray[index], row);
			}
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_equreceiptbd);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_equreceiptbd), "UTF-8"));
		index = 0;
		Vector<Vector<String>> receiptData = new Vector<Vector<String>>();
		dataArray = reader.readNext();
		for(int s = 0; s < dataArray.length; s++) {
			if (header_equreceiptid.equals(dataArray[s])){
				index = s;
			}
		}
		while((dataArray = reader.readNext()) != null) {
			row = new Vector<String>();
			if (!"".equals(dataArray[0].trim())){
				row.add(dataArray[2]);
				row.add(dataArray[3]);
				if (!SAOCRData.id2equipmentreceipbreakdown.containsKey(dataArray[index])){
					receiptData = new Vector<Vector<String>>();
				}
				else{
					receiptData = SAOCRData.id2equipmentreceipbreakdown.get(dataArray[index]);
				}
				receiptData.add(row);
				SAOCRData.id2equipmentreceipbreakdown.put(dataArray[index], receiptData);
			}
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_awake);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_awake), "UTF-8"));
		index  = 0;
		int index2 = 0;
		dataArray = reader.readNext();
		for (int i = 0; i < dataArray.length; i++){
			if (header_awake_before.equals(dataArray[i])){
				index = i;
			}
			else if (header_awake_after.equals(dataArray[i])){
				index2 = i;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			SAOCRData.id2awake.put(dataArray[index], dataArray[index2]);
			SAOCRData.awake2id.put(dataArray[index2], dataArray[index]);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_awake_ls);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_awake_ls), "UTF-8"));
		index  = 0;
		index2 = 0;
		dataArray = reader.readNext();
		for (int i = 0; i < dataArray.length; i++){
			if (header_awake_before.equals(dataArray[i])){
				index = i;
			}
			else if (header_leader_skill.equals(dataArray[i])){
				index2 = i;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			SAOCRData.chara2leaderskill.put(dataArray[index], dataArray[index2]);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_ss_normal);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_ss_normal), "UTF-8"));
		dataArray = reader.readNext();
		int index3 = 0;
		for (int i = 0; i < dataArray.length; i++){
			if (header_weaponcategory.equals(dataArray[i])){
				index = i;
			}
			else if (header_ss_id.equals(dataArray[i])){
				index2 = i;
			}
			else if (header_ss_weaponlevel.equals(dataArray[i])){
				index3 = i;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			if ("9".equals(dataArray[index3])){
				SAOCRData.weapon2swordskill9.put(dataArray[index], dataArray[index2]);
			}
			else if ("10".equals(dataArray[index3])){
				SAOCRData.weapon2swordskill10.put(dataArray[index], dataArray[index2]);
			}
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_ss_unique);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_ss_unique), "UTF-8"));
		dataArray = reader.readNext();
		for (int i = 0; i < dataArray.length; i++){
			if (header_ss_chara.equals(dataArray[i])){
				index = i;
			}
			else if (header_ss_id.equals(dataArray[i])){
				index2 = i;
			}
			else if (header_ss_weaponlevel.equals(dataArray[i])){
				index3 = i;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			if ("9".equals(dataArray[index3])){
				SAOCRData.chara2swordskill9.put(dataArray[index], dataArray[index2]);
			}
			else if ("10".equals(dataArray[index3])){
				SAOCRData.chara2swordskill10.put(dataArray[index], dataArray[index2]);
			}
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_weaponquality);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_weaponquality), "UTF-8"));
		index  = 0;
		dataArray = reader.readNext();
		for (int i = 0; i < dataArray.length; i++){
			if (header_name.equals(dataArray[i])){
				index = i;
				break;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			SAOCRData.id2weaponquality.put(dataArray[0], dataArray[index]);
		}
		reader.close();
		ProgressFrame.instance.addDigital(1.0f / 14.0f * 20.0f);

		ProgressFrame.instance.addText("Processing"+path_material);
		reader = new CSVReader(new InputStreamReader(new FileInputStream(path_material), "UTF-8"));
		index  = 0;
		dataArray = reader.readNext();
		for (int i = 0; i < dataArray.length; i++){
			if (header_name.equals(dataArray[i])){
				index = i;
				break;
			}
		}
		while((dataArray = reader.readNext()) != null && dataArray.length > 1) {
			SAOCRData.id2material.put(dataArray[0], dataArray[index]);
		}
		reader.close();
		ProgressFrame.instance.setDigital(40.0f);
		ProgressFrame.instance.addText("Load data finished");
	}

	public static void readSaveData() throws IOException{
		ProgressFrame.instance.addText("---------- -------");
		ProgressFrame.instance.addText("Loading Save Data...");
		File file = new File("resources/save/mainFrame.csv");
		if (!file.exists()){
			ProgressFrame.instance.appendText("not Found");
			Vector<String> row = new Vector<String>();
			row.add("m_npc_character_id");
			row.add("show_description");
			SAOCRData.savedata.add(row);
			return;
		}

		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("resources/save/mainFrame.csv"), "UTF-8"));
		String[] dataArray;
		while((dataArray = reader.readNext()) != null) {
			Vector<String> row = new Vector<String>();
			for(int s = 0; s < dataArray.length; s++) {
				row.add(dataArray[s]);
			}
			SAOCRData.savedata.add(row);
		}
		reader.close();
		ProgressFrame.instance.setDigital(50.0f);
		ProgressFrame.instance.addText("Load Save Data finished");
	}

	private static int findStr(String srcText, String keyword) {  
		int count = 0;  
		Pattern p = Pattern.compile(keyword);  
		Matcher m = p.matcher(srcText);  
		while (m.find()) {  
			count++;  
		}  
		return count;  
	}  


	public static void readPLDATA() throws IOException{
		ProgressFrame.instance.addText("---------- -------");
		ProgressFrame.instance.addText("Prepareing Progress Link Scenario Files...");
		File directory = new File("input");
		if (directory.exists()){
			File[] files = directory.listFiles();
			directory = new File(folder_charaep);
			if (!directory.exists()){
				directory.mkdirs();
			}
			if (files.length <= 0){
				ProgressFrame.instance.appendText("not Found");
			}
			for (int i = 0; i < files.length && files[i].getName().endsWith(".txt") ; i++){
				ProgressFrame.instance.addDigital(1.0f / files.length * 20.0f);
				ProgressFrame.instance.addText("Processing "+files[i].getName());
				InputStreamReader inputStream=new InputStreamReader(new FileInputStream(files[i]),"UTF-8");
				BufferedReader br = new BufferedReader(inputStream);
				File output = new File(folder_charaep+files[i].getName());
				output.createNewFile();
				OutputStreamWriter char_output = new OutputStreamWriter(
						new FileOutputStream(output),Charset.forName("UTF-8").newEncoder());
				BufferedWriter bw = new BufferedWriter(char_output);
				String dataArray = "";
				String dummy = "";
				while((dummy = br.readLine()) != null) {
					dataArray += dummy;
				}
				br.close();
				inputStream.close();

				int index = dataArray.indexOf("\"storyScript\"");
				dataArray = dataArray.substring(index);
				String[] data1 = dataArray.split("\"serif\"");
				for (int j = 1; j < data1.length; j++){
					String[] data2 = data1[j].split(",");
					for (int k = 0; k < data2.length; k++){
						if (data2[k].contains("\"name\"")){
							String[] data3 = data2[k].split(":");
							//						System.out.print(data3[2].replaceAll("\"", ""));
							//						System.out.print(":");
							//						System.out.print("\n\t");
							bw.write("【");
							bw.write(data3[2].replaceAll("\"", "").replaceAll(myname, mynamereplace));
							bw.write("】");
							bw.newLine();
						}
						if (data2[k].contains("\"serif_text\"")){
							String[] data3 = data2[k].split(":");
							//						System.out.println(data3[1].replaceAll("\"", "").replaceAll("<br>", "\n\t"));
							//							data3 = data3[1].replaceAll("\"", "").split("<br>");
							//							for (int l = 0; l < data3.length; l++){
							//								bw.write("\t"+data3[l]);
							//								bw.newLine();
							//							}
							bw.write("\t"+data3[1].replaceAll("\"", "").replaceAll("<br>", " ").replaceAll(myname, mynamereplace));
							bw.newLine();
							break;
						}
					}
				}
				bw.flush();
				bw.close();
				//				files[i].delete();
			}
		}
		ProgressFrame.instance.setDigital(60.0f);
		ProgressFrame.instance.addText("Load Progress Link Scenario Files finished");
	}


	public static void readScenarioDATA() throws IOException{
		ProgressFrame.instance.addText("---------- -------");
		ProgressFrame.instance.addText("Prepareing Code Register Scenario Files...");
		File directory = new File("input");
		if (directory.exists()){
			File[] files = directory.listFiles();
			directory = new File(folder_scenario);
			if (!directory.exists()){
				directory.mkdirs();
			}

			if (files.length <= 0){
				ProgressFrame.instance.appendText("not Found");
			}
			for (int i = 0; i < files.length && files[i].getName().endsWith(".tsv") ; i++){
				ProgressFrame.instance.addDigital(1.0f / files.length * 20.0f);
				ProgressFrame.instance.addText("Processing "+files[i].getName());
				InputStreamReader inputStream=new InputStreamReader(new FileInputStream(files[i]),"UTF-8");
				BufferedReader br = new BufferedReader(inputStream);
				File output = new File(folder_scenario+files[i].getName());
				if (output.exists()){
				}
				else{
					output.createNewFile();
					OutputStreamWriter char_output = new OutputStreamWriter(
							new FileOutputStream(output),Charset.forName("UTF-8").newEncoder());
					BufferedWriter bw = new BufferedWriter(char_output);
					String dataArray = br.readLine();
					while((dataArray = br.readLine()) != null) {
						String[] data = dataArray.split("\t");
						if (data.length > 7 && !"".equals(data[7])){
							//						System.out.print(data[1]);
							//						System.out.println(data[7]);
							bw.write("【");
							bw.write(data[1]);
							bw.write("】");
							bw.newLine();
							bw.write("\t"+data[7]);
							bw.newLine();
						}
					}
					br.close();
					inputStream.close();
					bw.flush();
					bw.close();
					//				files[i].delete();
				}
			}
		}
		ProgressFrame.instance.setDigital(80.0f);
		ProgressFrame.instance.addText("Load Code Register Scenario Files finished");
	}

	public static void saveFile() throws IOException {
		File file = new File("resources/save/");
		if (!file.exists()){
			file.mkdirs();
		}
		OutputStreamWriter char_output = new OutputStreamWriter(
				new FileOutputStream("resources/save/mainFrame.csv"),
				Charset.forName("UTF-8").newEncoder() 
		);
		BufferedWriter bw = new BufferedWriter(char_output);

		bw.write("\"m_npc_character_id\",\"show_description\"");
		bw.newLine();

		for (int i = SAOCRData.savedata.size()-1; i > 0; i--) {
			bw.write(SAOCRData.savedata.get(i).get(0));
			bw.write(",");
			bw.write(SAOCRData.savedata.get(i).get(1));
			bw.newLine();
		}
		bw.close();
		char_output.close();
	}

	public static void readScenario(boolean download) throws IOException{
		CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("resources/CSV/m_quest_story.csv"), "UTF-8"));
		String[] dataArray;
		File directory = new File("input");
		if (download){
			if (!directory.exists()){
				directory.mkdir();
			}
		}
		dataArray = reader.readNext();//first line
		while((dataArray = reader.readNext()) != null && dataArray.length >= 2) {
			try{
				SAOCRData.id2story.put(dataArray[0], dataArray[2]);
				if (download){
					String httpUrl = "http://dlc.yui.gu3.jp/utage/Scenario/"+dataArray[2]+"/START.tsv";
					System.out.print("download "+httpUrl);
					boolean fileExist = ExtractImage.httpDownload(httpUrl, "input/"+dataArray[2]+"_"+dataArray[1]+".tsv");
					if (!fileExist){
						System.out.println(" failed");
					}
					else{
						System.out.println("");
					}
				}
			}
			catch (Exception e){

			}
		}
		reader.close();
	}

	class WatchThread extends Thread {
		Process p;
		boolean over;
		public WatchThread(Process p) {
			this.p = p;
			over = false;
		}

		public void run() {
			try {
				if (p == null) return;
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while (true) {
					if (p==null || over) {
						break;
					}
					while(br.readLine()!=null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void setOver(boolean over) {
			this.over = over;
		}
	}
}
