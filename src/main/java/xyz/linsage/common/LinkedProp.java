package xyz.linsage.common;

import com.jfinal.core.Const;
import com.jfinal.kit.LogKit;

import java.io.*;


/**
 * @author linsage
 * @create 2017-06-28  下午3:44
 */
public class LinkedProp {
    private LinkedProperties LinkedProperties = null;

    /**
     * Prop constructor.
     * @see #LinkedProp(String, String)
     */
    public LinkedProp(String fileName) {
        this(fileName, Const.DEFAULT_ENCODING);
    }

    /**
     * Prop constructor
     * <p>
     * Example:<br>
     * Prop prop = new Prop("my_config.txt", "UTF-8");<br>
     * String userName = prop.get("userName");<br><br>
     *
     * prop = new Prop("com/jfinal/file_in_sub_path_of_classpath.txt", "UTF-8");<br>
     * String value = prop.get("key");
     *
     * @param fileName the LinkedProperties file's name in classpath or the sub directory of classpath
     * @param encoding the encoding
     */
    public LinkedProp(String fileName, String encoding) {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);		// LinkedProperties.load(Prop.class.getResourceAsStream(fileName));
            if (inputStream == null) {
                throw new IllegalArgumentException("LinkedProperties file not found in classpath: " + fileName);
            }
            LinkedProperties = new LinkedProperties();
            LinkedProperties.load(new InputStreamReader(inputStream, encoding));
        } catch (IOException e) {
            throw new RuntimeException("Error loading LinkedProperties file.", e);
        }
        finally {
            if (inputStream != null) try {inputStream.close();} catch (IOException e) {
                LogKit.error(e.getMessage(), e);}
        }
    }

    /**
     * Prop constructor.
     * @see #LinkedProp(File, String)
     */
    public LinkedProp(File file) {
        this(file, Const.DEFAULT_ENCODING);
    }

    /**
     * Prop constructor
     * <p>
     * Example:<br>
     * Prop prop = new Prop(new File("/var/config/my_config.txt"), "UTF-8");<br>
     * String userName = prop.get("userName");
     *
     * @param file the LinkedProperties File object
     * @param encoding the encoding
     */
    public LinkedProp(File file, String encoding) {
        if (file == null) {
            throw new IllegalArgumentException("File can not be null.");
        }
        if (file.isFile() == false) {
            throw new IllegalArgumentException("File not found : " + file.getName());
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            LinkedProperties = new LinkedProperties();
            LinkedProperties.load(new InputStreamReader(inputStream, encoding));
        } catch (IOException e) {
            throw new RuntimeException("Error loading LinkedProperties file.", e);
        }
        finally {
            if (inputStream != null) try {inputStream.close();} catch (IOException e) {LogKit.error(e.getMessage(), e);}
        }
    }

    public String get(String key) {
        return LinkedProperties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return LinkedProperties.getProperty(key, defaultValue);
    }

    public Integer getInt(String key) {
        return getInt(key, null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        String value = LinkedProperties.getProperty(key);
        if (value != null) {
            return Integer.parseInt(value.trim());
        }
        return defaultValue;
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public Long getLong(String key, Long defaultValue) {
        String value = LinkedProperties.getProperty(key);
        if (value != null) {
            return Long.parseLong(value.trim());
        }
        return defaultValue;
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = LinkedProperties.getProperty(key);
        if (value != null) {
            value = value.toLowerCase().trim();
            if ("true".equals(value)) {
                return true;
            } else if ("false".equals(value)) {
                return false;
            }
            throw new RuntimeException("The value can not parse to Boolean : " + value);
        }
        return defaultValue;
    }

    public boolean containsKey(String key) {
        return LinkedProperties.containsKey(key);
    }

    public LinkedProperties getProperties() {
        return LinkedProperties;
    }
}
