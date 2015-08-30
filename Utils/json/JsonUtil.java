package cn.itcast.testmanager.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * <p>
 * json工具类
 * </p>
 * 
 */
public class JsonUtil {

	/**
	 * 
	 * <P>
	 * 把对象转为Json格式的字符串
	 * </P>
	 * 
	 * @param themes
	 *            集合数据
	 * @return
	 */
	public static String writeEntity2JSON(Object obj) {
		if (obj == null)
			return "{}";
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static String writeEntity2KeyValueJSON(String key, Object obj,
			long time) {
		Gson gson = new Gson();
		String result = "";
		if (time > 0) {
			result = "{\"" + key + "\":" + gson.toJson(obj)
					+ ",\"current_time\":" + time + "}";
		} else {
			result = "{\"" + key + "\":" + gson.toJson(obj) + "}";
		}
		return result;
	}

	public static String writeEntityList2JSONWithSuccess(Object obj) {
		Gson gson = new Gson();
		return "{\"array\":" + gson.toJson(obj) + ",\"success\":true}";
	}

	public static String writeEntityList2JSON(Object obj) {
		Gson gson = new Gson();
		return "{\"array\":" + gson.toJson(obj) + "}";
	}

	public static String writeEntityList2JSON(Object obj, long time) {
		Gson gson = new Gson();
		return "{\"array\":" + gson.toJson(obj) + ",\"current_time\":" + time
				+ "}";
	}

	/**
	 * 将对象中注解为@Expose的属性转成JSON串
	 * 
	 * @param obj
	 * @return
	 */
	public static String writeEntityOrListExcludeFields2JSON(Object obj) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		return gson.toJson(obj);
	}

	/**
	 * 将json转换成对应的实体类对象
	 * 
	 * @param json
	 * @param objEntity
	 * @return
	 */
	public static <T extends Object> T fromJSON2Entity(String json,
			Class<T> objEntity) {
		Gson gson = new Gson();
		return gson.fromJson(json, objEntity);
	}

}
