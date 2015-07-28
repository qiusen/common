package com.dihaiboyun.common.util;

/**
 * 类型工具类
 * 
 * @author nathan
 * 
 */
public class TypeUtil {

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str) {
		int i = 0;
		if (str != null && str.length() > 0) {
			try {
				i = Integer.parseInt(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return i;
	}

	/**
	 * 判断字符串是否为空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean stringIsEmpty(String str) {
		boolean r = false;

		if (str == null || str.trim().length() <= 0) {
			r = true;
		}

		return r;
	}
}
