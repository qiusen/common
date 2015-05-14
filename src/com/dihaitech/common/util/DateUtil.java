package com.dihaitech.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtil {

	/**
	 * 精确到日期 yyyy-MM-dd
	 */
	public static final String DATE_FORM = "yyyy-MM-dd";

	/**
	 * 精确到秒 yyyy-MM-dd HH:mm:ss
	 */
	public static final String TIME_FORM = "yyyy-MM-dd HH:mm:ss";

	private static final String DATE_REG = "\\d{4}-\\d{2}-\\d{2}";

	private static final String TIME_REG = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";

	/**
	 * 连续格式 yyyyMMddHHmmssSSS
	 */
	public static final String DATE_TIME_CONTINUOUS_FORM = "yyyyMMddHHmmssSSS";

	/**
	 * 日期连续格式 yyyyMMdd
	 */
	public static final String DATE_CONTINUOUS_FORM = "yyyyMMdd";

	/**
	 * 时间连续格式 HHmmssSSS
	 */
	public static final String TIME_CONTINUOUS_FORM = "HHmmssSSS";
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String DATE_FORMAT_A = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd
	 */
	public static String DATE_FORMAT_B = "yyyy-MM-dd";
	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static String DATE_FORMAT_C = "yyyyMMddHHmmssSSS";
	/**
	 * yyyyMMddHHmmss
	 */
	public static String DATE_FORMAT_D = "yyyyMMddHHmmss";

	/**
	 * 对时间进行加、减操作
	 * 
	 * @param date
	 * @param pos
	 *            修改的位置：1、年；2、月；3、日；4、小时；5、分钟；6、秒
	 * @param i
	 *            要加、减的数字
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date dataPlusMinus(Date date, int pos, int i) {

		// 年
		if (pos == 1) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// System.out.println(calendar.get(Calendar.YEAR));//年
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + i);
			date = calendar.getTime();
		}
		// 月
		if (pos == 2) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// System.out.println(calendar.get(Calendar.MONTH));//月份
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);
			date = calendar.getTime();
		}
		// 日
		if (pos == 3) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//日期
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + i);// 让日期加i
			date = calendar.getTime();

		}
		// 小时
		if (pos == 4) {
			int h = date.getHours();
			// System.out.println(h);
			date.setHours(h + i);
		}
		// 分钟
		if (pos == 5) {
			int mm = date.getMinutes();
			// System.out.println(mm);
			date.setMinutes(mm + i);
		}
		// 秒
		if (pos == 6) {
			int s = date.getSeconds();
			// System.out.println(s);
			date.setSeconds(s + i);
		}
		return date;
	}

	/**
	 * 字符串转时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		try {
			if (date.matches(DATE_REG)) {
				SimpleDateFormat sf = new SimpleDateFormat(DATE_FORM);
				return sf.parse(date);
			} else if (date.matches(TIME_REG)) {
				SimpleDateFormat sf = new SimpleDateFormat(TIME_FORM);
				return sf.parse(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 字符串转日期
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String str, String format) {
		if (StringUtils.isNotBlank(str)) {
			DateTime dt = DateTimeFormat.forPattern(format).parseDateTime(str);
			return dt.toDate();
		} else {
			return null;
		}

	}
	
	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date != null) {
			String str = null;
			DateTime dt = new DateTime(date);
			str = dt.toString(format);
			return str;
		} else {
			return null;
		}
	}


	/**
	 * 获取当前时间字符串
	 * 
	 * @param form
	 * @return
	 */
	public static String getNowDateString(String form) {
		String nowDate = null;

		Calendar time = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(form);
		nowDate = format.format(time.getTime());

		return nowDate;
	}
	/**
	 * 获取当前时间，使用jodatime
	 * @return
	 */
	public static Date getCurrentTime(){
		return LocalDateTime.now().toDate();
	}

	public static void main(String[] args) {
		/*
		 * Date d = DateUtil.dataPlusMinus(new Date(), 1, -5);
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);
		 * sdf.applyPattern("yyyy年MM月dd日_HH点mm分ss秒");
		 * System.out.println(sdf.format(new Date()));
		 * System.out.println(sdf.format(d));
		 */

		System.out.println(DateUtil.getNowDateString(TIME_CONTINUOUS_FORM));
	}
}
