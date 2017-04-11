package others;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class getImage {

//	static getImageTask task;

	public static byte[] getImageFromNetByUrl(String strUrl){  
		try {  
			URL url = new URL(strUrl);  
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			conn.setRequestMethod("GET");  
			conn.setConnectTimeout(5 * 1000);  
			InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
			byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
			return btImg;  
		} catch (Exception e) {  
			//            e.printStackTrace();  
		}  
		return null;  
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
		int len = 0;  
		while( (len=inStream.read(buffer)) != -1 ){  
			outStream.write(buffer, 0, len);  
		}  
		inStream.close();  
		return outStream.toByteArray();  
	}

//	public getImage(){
//		try{
//			String str0 = "NjAwNTEx";
//
//			for (int i = 36791; i <= 1019342 && !stop; i++){
//				String str1 = Integer.toHexString(i);
//				while(str1.length()<5){
//					str1 = "0"+str1;
//				}
//				for (int j = 66670; j <= 1021008 && !stop; j++){
//					String str2 = Integer.toHexString(j);
//					while(str2.length()<5){
//						str2 = "0"+str2;
//					}
//					String url = "http://cdn4-prd.saopl.jp/enc/"+str1+str2+"aW1nL2hpZ2gvY2hhcmEvcGFydHkv"+str0+"LnBuZw.png";
//					task = new getImageTask(url);
//					task.run();
//				}
//			}
//		}
//		catch (Exception e){
//
//		}
//	}
//
//	public class getImageTask extends Thread{
//		String url;
//		public getImageTask(String arg_url){
//			url = arg_url;
//		}
//		@SuppressWarnings("deprecation")
//		public void run(){
////			while(true){
//				try{
////						String sleepTime = "900000";//config.getValue("app.alert.sleep.corp","0");
////						Thread.sleep(Integer.parseInt(sleepTime));
////						Thread.sleep(1000*60*15);
//					byte[] btImg = getImageFromNetByUrl(url);
//					if(null != btImg && btImg.length > 0){  
//						System.out.println(url+"读取到：" + btImg.length + " 字节");
//						stop = true;
//					}else{
////						System.out.println(url+"没有从该连接获得内容"); 
//					}
//				}catch(Exception e){
//				}
////			}
//			this.destroy();
//		}
//	}
//	
//	private boolean stop = false;
}
