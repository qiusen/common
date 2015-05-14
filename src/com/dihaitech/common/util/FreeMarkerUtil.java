package com.dihaitech.common.util;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * freeMarker工具类
 * @author qiusen
 *
 */
public class FreeMarkerUtil {
	/**
	 * 预览功能
	 * 
	 * @param rootMap
	 * @param templateStr
	 * @param charSet
	 * @return
	 */
	public static String preview(Map<String, Object> rootMap, String templateStr, String charSet) {
		String result = null;
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader(templateStr));
		cfg.setDefaultEncoding(charSet);
		cfg.setOutputEncoding(charSet);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		StringWriter writer = new StringWriter();
		try {
			Template template = cfg.getTemplate("");
			template.setEncoding(charSet);
			template.process(rootMap, writer);
			result = writer.getBuffer().toString();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
