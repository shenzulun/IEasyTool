/**
 * File Name: CollectionUtils.java
 * Date: 2019-08-16 08:35:45
 */
package me.belucky.easytool.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 集合工具类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class CollectionUtils {
	
	/**
	 * 生成map
	 * @param <K>
	 * @param <V>
	 * @param key
	 * @param value
	 * @return
	 */
	public static <K,V> Map<K, V> buildMap(K key, V value) {
		Map<K,V> map = new HashMap<K,V>();
		map.put(key, value);
		return map;
	}

}
