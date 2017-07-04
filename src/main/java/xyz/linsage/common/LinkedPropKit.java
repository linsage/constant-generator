package xyz.linsage.common;

import com.jfinal.core.Const;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linsage
 * @create 2017-06-28  下午3:46
 */
public class LinkedPropKit {

    private static LinkedProp prop = null;
    private static final ConcurrentHashMap<String, LinkedProp> map = new ConcurrentHashMap<String, LinkedProp>();

    private LinkedPropKit() {
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     *
     * @see #use(String, String)
     */
    public static LinkedProp use(String fileName) {
        return use(fileName, Const.DEFAULT_ENCODING);
    }

    /**
     * Using the properties file. It will loading the properties file if not loading.
     * <p>
     * Example:<br>
     * PropKit.use("config.txt", "UTF-8");<br>
     * PropKit.use("other_config.txt", "UTF-8");<br><br>
     * String userName = PropKit.get("userName");<br>
     * String password = PropKit.get("password");<br><br>
     * <p>
     * userName = PropKit.use("other_config.txt").get("userName");<br>
     * password = PropKit.use("other_config.txt").get("password");<br><br>
     * <p>
     * PropKit.use("com/jfinal/config_in_sub_directory_of_classpath.txt");
     *
     * @param fileName the properties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     */
    public static LinkedProp use(String fileName, String encoding) {
        LinkedProp result = map.get(fileName);
        if (result == null) {
            result = new LinkedProp(fileName, encoding);
            map.put(fileName, result);
            if (LinkedPropKit.prop == null) {
                LinkedPropKit.prop = result;
            }
        }
        return result;
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     *
     * @see #use(File, String)
     */
    public static LinkedProp use(File file) {
        return use(file, Const.DEFAULT_ENCODING);
    }

    /**
     * Using the properties file bye File object. It will loading the properties file if not loading.
     * <p>
     * Example:<br>
     * PropKit.use(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * Strig userName = PropKit.use("my_config.txt").get("userName");
     *
     * @param file     the properties File object
     * @param encoding the encoding
     */
    public static LinkedProp use(File file, String encoding) {
        LinkedProp result = map.get(file.getName());
        if (result == null) {
            result = new LinkedProp(file, encoding);
            map.put(file.getName(), result);
            if (LinkedPropKit.prop == null) {
                LinkedPropKit.prop = result;
            }
        }
        return result;
    }

    public static LinkedProp useless(String fileName) {
        LinkedProp previous = map.remove(fileName);
        if (LinkedPropKit.prop == previous) {
            LinkedPropKit.prop = null;
        }
        return previous;
    }

    public static void clear() {
        prop = null;
        map.clear();
    }

    public static LinkedProp getProp() {
        if (prop == null) {
            throw new IllegalStateException("Load propties file by invoking PropKit.use(String fileName) method first.");
        }
        return prop;
    }

    public static LinkedProp getProp(String fileName) {
        return map.get(fileName);
    }

    public static String get(String key) {
        return getProp().get(key);
    }

    public static String get(String key, String defaultValue) {
        return getProp().get(key, defaultValue);
    }

    public static Integer getInt(String key) {
        return getProp().getInt(key);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        return getProp().getInt(key, defaultValue);
    }

    public static Long getLong(String key) {
        return getProp().getLong(key);
    }

    public static Long getLong(String key, Long defaultValue) {
        return getProp().getLong(key, defaultValue);
    }

    public static Boolean getBoolean(String key) {
        return getProp().getBoolean(key);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return getProp().getBoolean(key, defaultValue);
    }

    public static boolean containsKey(String key) {
        return getProp().containsKey(key);
    }
}
