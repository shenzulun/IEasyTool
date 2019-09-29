/**
 * File Name: CachUtils.java
 * Date: 2019-08-16 15:30:53
 */
package me.belucky.easytool.util;

import me.belucky.easytool.cache.CacheCenter;

/**
 * Description: 缓存工具类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class CacheUtils {
	private static CacheCenter cache = null;
	static{
		cache = CacheCenter.getInstance();
	}
	
	/**
	 * 获取缓存的值
	 * @param <T>
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCache(String key){
		return (T)cache.get(key);
	}
	/**
	 * 放入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public static Object putCache(String key, Object value){
		return cache.put(key, value);
	}
	/**
	 * 清除指定KEY
	 * @param key
	 */
	public static void removeCache(String key){
		cache.remove(key);
	}
	
	/**
	 * 清除缓存
	 */
	public static void clearCache() {
		cache.clearCache();
	}
}
