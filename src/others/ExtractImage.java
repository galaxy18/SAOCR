package others;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import frames.ProgressFrame;

public class ExtractImage {
	public ExtractImage(String httpUrl,String arg_id){
		boolean fileExist = true;
		Process p = null;
		ProgressFrame.instance.mainFrame.setVisible(true);
		ProgressFrame.instance.addText("Starting download from "+httpUrl);
		fileExist = httpDownload(httpUrl, "resources/disunity/"+arg_id+".assetbundle");
		//debug
//		arg_id = "10010350";
		if (!fileExist){
			ProgressFrame.instance.mainFrame.setVisible(true);
			ProgressFrame.instance.addText(arg_id + " download Failed");
			return;
		}
		
		try {
			ProgressFrame.instance.mainFrame.setVisible(true);
			ProgressFrame.instance.addText(arg_id + " download Successd");
			ProcessBuilder pb = new ProcessBuilder(
					"java",
					"-jar",
					System.getProperty("user.dir")+"\\resources\\disunity\\disunity.jar",
					"extract",
					arg_id+".assetbundle");
//				pb.directory(new File(System.getProperty("user.dir")+"\\disunity\\"));
			pb.directory(new File("resources/disunity"));
			pb. redirectErrorStream(true);
		
			p = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			p.waitFor();
			ProgressFrame.instance.mainFrame.setVisible(true);
			ProgressFrame.instance.addText("finished download for "+arg_id+".assetbundle");
			
			File file = new File("resources/disunity/"+arg_id+"/");
			System.out.println(file.getPath());
			if (file.exists()){
				file = new File(file.getPath()+"/"+file.list()[0]+"/Texture2D/large.tga");
				System.out.println(file.getPath());
				if (file.exists()){
					File outputfile = new File("resources/gacha/");
					outputfile.mkdirs();
					
					file.renameTo(new File("resources/gacha",arg_id+"_large.tga"));
					
					JOptionPane.showMessageDialog(null, arg_id+" Done");
				}
			}
			else{
				System.out.println("!"+file.getPath());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static boolean httpDownload(String httpUrl,String saveFile){
	       int bytesum = 0;  
	       int byteread = 0;  
	  
	       URL url = null;  
	    try {  
	        url = new URL(httpUrl);  
	    } catch (MalformedURLException e1) {
	        e1.printStackTrace();  
	        return false;  
	    }  
	  
	       try {  
	           URLConnection conn = url.openConnection();  
	           InputStream inStream = conn.getInputStream();  
	           FileOutputStream fs = new FileOutputStream(saveFile);  
	  
	           byte[] buffer = new byte[1204];  
	           while ((byteread = inStream.read(buffer)) != -1) {  
	               bytesum += byteread;  
//	               System.out.println(bytesum);  
	               fs.write(buffer, 0, byteread);  
	           }  
	           return true;  
	       } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	           return false;  
	       } catch (IOException e) {  
	           e.printStackTrace();  
	           return false;  
	       }  
	   }  
}
