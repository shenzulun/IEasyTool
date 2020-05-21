/**
 * File Name: PropUtils.java
 * Date: 2019-08-22 11:14:53
 */
package me.belucky.easytool.jfinal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import me.belucky.easytool.util.FileTools;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: prop配置文件工具类
 * @author shenzulun
 * @date 2019-08-22
 * @version 1.0
 */
public class PropUtils {
	protected static Logger log = LoggerFactory.getLogger(PropUtils.class);
	private static ConcurrentMap<String, Object> configs = new ConcurrentHashMap<>();
    private static final String defalutConfigFile = "sys-config.properties";

    /**
     * 获取配置信息
     * @param prefix properties文件内属性名的前缀
     * @param clazz
     * @return
     */
    public static <T> T getConfig(String prefix, Class<T> clazz) {
    	return getConfig(prefix,defalutConfigFile,clazz);
    }

    /**
     * 获取配置信息
     * @param prefix properties文件内属性名的前缀
     * @param propFile 配置文件名
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T getConfig(String prefix, String propFile, Class<T> cls) {
       //检查前缀
    	if (!StringUtils.isNotNull(prefix)) {
    		throw new RuntimeException("prefix must not be null");
        }

        //先尝试从map中获取内容
        Object obj = configs.get(prefix);
        if (obj != null) {
            return (T) obj;
        }

        try {
			obj = cls.newInstance();
		} catch (InstantiationException e) {
			log.error("",e);
		} catch (IllegalAccessException e) {
			log.error("",e);
		}
        Prop prop =  PropKit.use(propFile);
        List<Method> setMethods = new ArrayList<>();

        Method[] methods = obj.getClass().getMethods();
        if (methods != null && methods.length > 0) {
            for (Method m : methods) {
            	String methodName = m.getName();
                if (methodName.startsWith("set") 
                		&& methodName.length() > 3 
                		&& m.getParameterCount() == 1) {
                    setMethods.add(m);
                }
            }
        }

        for (Method method : setMethods) {
        	try {
        		String key = StrKit.firstCharToLowerCase(method.getName().substring(3));
                //添加上前缀的key
                key = prefix.trim() + "." + key;      
                String value = prop.get(key);
                if (StringUtils.isNotBlank(value)) {
                	Object val = null;
                	Class<?> clz = method.getParameterTypes()[0];
                	String className = clz.getName();
                	if(clz.isAssignableFrom(Integer.class) || className.equals("int")) {
                		val = Integer.valueOf(value);
                	}else if(clz.isAssignableFrom(String.class)) {
                		val = value;
                	}else if(clz.isAssignableFrom(Long.class) || className.equals("long")) {
                		val = Long.valueOf(value);
                	}else if(clz.isAssignableFrom(Double.class) || className.equals("double")) {
                		val = Double.valueOf(value);
                	}else if(clz.isAssignableFrom(Boolean.class) || className.equals("boolean")) {
                		val = Boolean.valueOf(value);
                	}
                    method.invoke(obj, val);
                }
            } catch (Throwable e) {
            	log.error("",e);
            }
        }
        
        //设置到map里去
        configs.put(prefix, obj);
        return (T) obj;
    }
    
    /**
     * 获取配置信息
     * @param prefix properties文件内属性名的前缀
     * @param propFile 配置文件名
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T getConfigFromCache(String cacheKey, String propFile, Class<T> cls) {

        //先尝试从map中获取内容
        Object obj = configs.get(cacheKey);
        if (obj != null) {
            return (T) obj;
        }

        try {
			obj = cls.newInstance();
		} catch (InstantiationException e) {
			log.error("",e);
		} catch (IllegalAccessException e) {
			log.error("",e);
		}
        Prop prop =  PropKit.use(propFile);
        List<Method> setMethods = new ArrayList<>();

        Method[] methods = obj.getClass().getMethods();
        if (methods != null && methods.length > 0) {
            for (Method m : methods) {
            	String methodName = m.getName();
                if (methodName.startsWith("set") 
                		&& methodName.length() > 3 
                		&& m.getParameterCount() == 1) {
                    setMethods.add(m);
                }
            }
        }

        for (Method method : setMethods) {
        	try {
        		String key = StrKit.firstCharToLowerCase(method.getName().substring(3));
                String value = prop.get(key);
                if (StringUtils.isNotBlank(value)) {
                	Object val = null;
                	Class<?> clz = method.getParameterTypes()[0];
                	String className = clz.getName();
                	if(clz.isAssignableFrom(Integer.class) || className.equals("int")) {
                		val = Integer.valueOf(value);
                	}else if(clz.isAssignableFrom(String.class)) {
                		val = value;
                	}else if(clz.isAssignableFrom(Long.class) || className.equals("long")) {
                		val = Long.valueOf(value);
                	}else if(clz.isAssignableFrom(Double.class) || className.equals("double")) {
                		val = Double.valueOf(value);
                	}else if(clz.isAssignableFrom(Boolean.class) || className.equals("boolean")) {
                		val = Boolean.valueOf(value);
                	}
                    method.invoke(obj, val);
                }
            } catch (Throwable e) {
            	log.error("",e);
            }
        }
        
        //设置到map里去
        configs.put(cacheKey, obj);
        return (T) obj;
    }
    
    /**
	 * 批量初始化prop目录
	 * @param propFolderName
	 */
	public static void initProp(String... propFolderNames){
		//类似遍历resources/logAnalyse目录
		for(String propFolderName : propFolderNames){
			String path = Thread.currentThread().getContextClassLoader().getResource(propFolderName).getFile();
			String[] arr = FileTools.getFileNameArray(path, ".properties");
			for(String s : arr){
				PropKit.use(propFolderName + "/" + s);
			}
		}
	}
	
	/**
	 * 批量初始化prop目录
	 * @param propFolderName prop目录
	 * @param ignorePropName 忽略的prop文件名
	 */
	public static void initPropIgnore(String propFolderName, String ignorePropName){
		String prePath = "";
		if(!"".contentEquals(propFolderName)) {
			prePath = propFolderName + "/";
		}
		String path = Thread.currentThread().getContextClassLoader().getResource(propFolderName).getFile();
		String[] arr = FileTools.getFileNameArray(path, ".properties");
		for(String s : arr){
			if(!s.equals(ignorePropName)) {
				PropKit.use(prePath + s);
				log.info("配置文件[{}]加载成功...", s);
			}
		}
	}
	
	/**
	 * 查询数据源配置文件内的数据源(排除default)
	 * @return
	 */
	public static List<String> getDataSourceNamesFromProp() {
		Prop prop = PropKit.getProp("jdbc-config.properties");
		Set<Object> set = prop.getProperties().keySet();
		Set<String> dataSourceNameSet = new HashSet<String>();
		for(Object obj : set) {
			String name = (String)obj;
			int ind = -1;
			if(name != null && (ind=name.indexOf(".")) != -1) {
				String d = name.substring(0, ind);
				if(!"default".equals(d)) {
					dataSourceNameSet.add(d);
				}
			}
		}
		List<String> dataSourceNames = new ArrayList<String>();
		for(String dataSourceName : dataSourceNameSet) {
			dataSourceNames.add(dataSourceName);
		}
		return dataSourceNames;
	}
    
}
