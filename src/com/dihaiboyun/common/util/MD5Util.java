package com.dihaiboyun.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * 
 * @author nathan
 * 
 */
public class MD5Util {

	/**
	 * MD5加密
	 * 
	 * @param s
	 * @return
	 */
	public final static String stringToMD5(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		MessageDigest mdTemp = null;
		char str[] = null;
		try {
			byte[] strTemp = s.getBytes();
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str != null ? new String(str) : "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(MD5Util.stringToMD5("admin"));
	}

}
