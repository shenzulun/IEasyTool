/**
 * File Name: CacheCenter.java
 * Date: 2019-09-29 16:12:48
 */
package me.belucky.easytool.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Description: 缓存中心
 * @author shenzulun
 * @date 2019-09-29
 * @version 1.0
 */
public class CacheCenter {
	/**
	 * 缓存map
	 */
	private ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>(256);
	
	private CacheCenter(){}
	
	public static CacheCenter getInstance(){
		return CacheCenterInstance.instance;
	}
	
	private static class CacheCenterInstance{
		protected static CacheCenter instance = new CacheCenter();
	}
	
	public Object put(String key, Object value){
		return cache.put(key, value);
	}
	
	public Object get(String key){
		return cache.get(key);
	}
	
	public void remove(String key){
		cache.remove(key);
	}
	/**
	 * 清除缓存
	 */
	public void clearCache(){
		cache.clear();
	}
}
