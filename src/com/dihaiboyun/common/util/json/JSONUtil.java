package com.dihaiboyun.common.util.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * JSON工具
 * 
 * @author nathan
 * 
 */
public class JSONUtil {

	/**
	 * 集合、数组转换成JSON
	 * 
	 * @param o
	 * @return
	 */
	public static String objectArrayToJson(Object o) {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new IgnoreFieldProcessorImpl(false, new String[] { "count", "start", "end", "pageNo", "pageSize" })); // 忽略掉name属性及集合对象
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); // 当输出时间格式时，采用和JS兼容的格式输出

		JSONArray json = JSONArray.fromObject(o, config);
		return json.toString();
	}

	/**
	 * DTO转换成JSON
	 * 
	 * @param o
	 * @return
	 */
	public static String objectToJson(Object o) {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new IgnoreFieldProcessorImpl(false, new String[] { "count", "start", "end", "pageNo", "pageSize" })); // 忽略掉name属性及集合对象
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); // 当输出时间格式时，采用和JS兼容的格式输出

		JSONObject json = JSONObject.fromObject(o, config);
		return json.toString();
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> jsonToList(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(jsonToMap(json2.toString()));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(jsonToMap(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

}
