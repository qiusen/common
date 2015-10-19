package com.dihaiboyun.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * 
 * @author nathan
 * 
 */
public class MD5Util {

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 字符串MD5加密
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToMD5(String s) {

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
	 * 文件MD5码
	 * 
	 * @param file
	 * @return
	 */
	public static String fileToMD5(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			// long s = System.currentTimeMillis();
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}

			byte[] b = md.digest();
			return byteToHexString(b);

			// 16位加密
			// return buf.toString().substring(8, 24);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * 
	 * @return 十六进制字符串表示形式
	 */

	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，

		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(MD5Util.stringToMD5("admin"));

		System.out.println(MD5Util.fileToMD5(new File(
				"/Users/qiusen/libevent-2.0.21-stable.tar.gz")));
	}

}
