package xyz.linsage.common;


import xyz.linsage.model.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * @author linsage
 * @create 2017-06-26  下午2:42
 */
public class ConfigLoader {

	private Properties properties;

	public void load(Class<?> configClass, Properties properties) {
		try {
			this.properties = properties;
			recursiveClass(configClass);
		} catch (Exception e) {
			throw new RuntimeException("Error loading configuration: " + e, e);
		}
	}


	public void recursiveClass(Class<?> configClass) throws Exception {
		for (Field field : configClass.getDeclaredFields()) {        //当前属性
			//静态属性
			if (Modifier.isStatic(field.getModifiers())) {
				field.setAccessible(true);
				Class c = field.getDeclaringClass();
				//全类名$内类名$内类名...
				String fullClassName = c.getName();
				//路径
				String path = "";
				if (fullClassName.contains("$")) {    //内部类属性
					path = fullClassName.substring(fullClassName.indexOf("$") + 1) + "$" + field.getName();
					path = path.replace("$", ".");
				} else {  //根属性
					path = field.getName();
				}
				field.set(null, getValue(properties, path, field.getType()));
			}

			for (Class item : configClass.getClasses()) {        //当前子类
				recursiveClass(item);
			}
		}
	}
/*

	private void setField(Field field) throws IllegalAccessException {
		//静态属性
		if (Modifier.isStatic(field.getModifiers())) {
			field.setAccessible(true);
			Class c = field.getDeclaringClass();
			//全类名$内类名$内类名...
			String fullClassName = c.getName();
			//路径
			String path = fullClassName.substring(fullClassName.indexOf("$") + 1) + "$" + field.getName();
			path = path.replace("$", ".");
			field.set(null, getValue(properties, path, field.getType()));
		}
	}
*/


	private static Object getValue(Properties props, String name, Class<?> type) {
		String value = props.getProperty(name);
		if (value == null)
			throw new IllegalArgumentException("Missing configuration value: " + name);
		if (type == boolean.class)
			return Boolean.parseBoolean(value);
		if (type == String.class)
			return value;
		if (type == int.class)
			return Integer.parseInt(value);
		if (type == short.class)
			return Short.parseShort(value);
		if (type == float.class)
			return Float.parseFloat(value);
		if (type == double.class)
			return Double.parseDouble(value);
		throw new IllegalArgumentException("Unknown configuration value type: " + type.getName());
	}


	public static void main(String[] args) {
		LinkedProp prop = LinkedPropKit.use("config.properties");
		ConfigLoader configLoader = new ConfigLoader();
		configLoader.load(Constant.class, prop.getProperties());

		System.out.println(Constant.name);
		System.out.println(Constant.devMode);
		System.out.println(Constant.db.url);
		System.out.println(Constant.qiniu.ins.domain);
		System.out.println(Constant.httpProxy.port);
		System.out.println("ok");
	}
}
