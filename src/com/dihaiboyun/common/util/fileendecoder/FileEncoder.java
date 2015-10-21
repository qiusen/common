package com.dihaiboyun.common.util.fileendecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileEncoder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String srcFilePath = "/Users/qiusen/Desktop/1.flv";
		String tarFilePath = "/Users/qiusen/Desktop/1.flv.en";
		
		File srcFile = new File(srcFilePath);
		File tarFile = new File(tarFilePath);
		
		int length = 10240;
		byte[] srcbytes = new byte[length];
		
		FileInputStream is = null;
		FileOutputStream os = null;
				
		try {
			is = new FileInputStream(srcFile);
			os = new FileOutputStream(tarFile, true);
			
			int i = 0;
			int j = 0;
			while((i=is.read(srcbytes))>0){
				os.write(srcbytes, 0, i);
				os.write(srcbytes, 0, i);
				os.flush();
				System.out.println(j + " " + i);
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
