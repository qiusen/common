package com.dihaiboyun.common.util.fileendecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileDecoder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String srcFilePath = "/Users/qiusen/Desktop/1.flv.en";
		String tarFilePath = "/Users/qiusen/Desktop/1.flv.de";
		
		File srcFile = new File(srcFilePath);
		File tarFile = new File(tarFilePath);
		
		byte[] srcbytes = new byte[20480];

		FileInputStream is = null;
		FileOutputStream os = null;
				
		try {
			is = new FileInputStream(srcFile);
			os = new FileOutputStream(tarFile, true);
			
			int i = 0;
			int j = 0;
			while((i=is.read(srcbytes))>0){
				int len = i/2;
				os.write(srcbytes, 0, len);
				System.out.println(j + " " + len);
				j++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try{
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	}
	
}
