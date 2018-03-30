package com.city.testobj.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);
	
	public static void printObj(Object obj){
		if (obj == null) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		try {
			Class<?> clazz = obj.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					int mod = field.getModifiers();
					if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
						continue;
					}
					field.setAccessible(true);
					Object val = field.get(obj);
					/*
					 * if (val instanceof String) { String value = (String) val;
					 * if (StringUtils.isNotEmpty(value)) { value = new
					 * String(value.getBytes("ISO8859-1"), "utf-8"); }
					 * field.set(obj, value); }
					 */
					if (val != null) {
						sb.append(field.getName() + ":" + val.toString()).append("\n");
					} else {
						sb.append(field.getName() + ":").append("\n");
					}
				}
				sb.append("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(sb.toString());
	}
}
