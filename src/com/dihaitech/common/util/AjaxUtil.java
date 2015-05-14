package com.dihaitech.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * AJAX工具类
 * 
 * @author nathan
 * 
 */
public class AjaxUtil {

	public static void ajaxResponse(HttpServletResponse response, String message) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			response.setContentType("text/html");
			pw.write(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}
}
