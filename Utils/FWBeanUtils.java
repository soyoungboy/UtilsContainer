package cn.itcast.testmanager.common.util;


import java.util.UUID;

import org.springframework.beans.BeanUtils;

public class FWBeanUtils {
	/**
	 * 生成32位UUID字符转并转成大写
	 * 
	 * @return
	 */
	public static String newEntityKey() {
		String key = UUID.randomUUID().toString();
		key = key.replace("-", "").toUpperCase();
		return key;
	}

	/**
	 * 属性拷贝
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 * @throws Exception
	 */
	public static void copyPropertis(Object source, Object target)
			throws Exception {
		if (source != null) {
			BeanUtils.copyProperties(source, target);
		}
	}
}
