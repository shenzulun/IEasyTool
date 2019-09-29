/**
 * File Name: CommonUtils.java
 * Date: 2019-08-16 08:40:54
 */
package me.belucky.easytool.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Description: 通用工具类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class CommonUtils {
	protected static Logger log = LoggerFactory.getLogger(CommonUtils.class);
	
	/**
	 * 正则匹配
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String regexMatch(String source, String pattern){
		Pattern p1 = Pattern.compile(pattern);	
		return regexMatch(source, p1);
	}
	/**
	 * 正则匹配
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static String regexMatch(String source, Pattern pattern){
		String result = null;
		Matcher matcher = pattern.matcher(source);
		while(matcher.find()){
			result = matcher.group(1);
			break;
		}
		return result;
	}
	
	/**
	 * Java bean  get 反射
	 * @param <T>
	 * @param target
	 * @param fieldName
	 * @return
	 */
	public static <T> T invoke(Object target, String fieldName) {
		return invoke(target, fieldName, "get", null);
	}
	
	/**
	 * Java bean 反射
	 * @param <T>
	 * @param target
	 * @param fieldName
	 * @param methodPrefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invoke(Object target, String fieldName, String methodPrefix, T newValue) {
		String methodName = methodPrefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		T t = null;
		try {
			Class<?> cls = target.getClass();
			if("set".equals(methodPrefix)){
				//set方法  先获取该字段的属性
				Field field = cls.getDeclaredField(fieldName);
				Method m = target.getClass().getDeclaredMethod(methodName, field.getType());
				Object v = m.invoke(target, newValue);
				t = (T)v;
			}else if("get".equals(methodPrefix)){
				Method m = target.getClass().getDeclaredMethod(methodName);
				Object v = m.invoke(target);
				t = (T)v;
			}
		} catch (SecurityException e) {
			log.error("",e);
		} catch (NoSuchMethodException e) {
			log.error("",e);
		} catch (IllegalArgumentException e) {
			log.error("",e);
		} catch (IllegalAccessException e) {
			log.error("",e);
		} catch (InvocationTargetException e) {
			log.error("",e);
		} catch (NoSuchFieldException e) {
			log.error("",e);
		}
		return t;
	}
	
	/**
	 * 随机生成指定个数的数组
	 * @param max	最大值
	 * @param count	个数
	 * @return
	 */
	public static int[] randomInt(int max, int count){
		if(count >= max){
			int[] arr = new int[max];
			for(int i=0;i<max;i++){
				arr[i] = i;
			}
			return arr;
		}
		Set<Integer> set = new HashSet<Integer>();
		int[] arr = new int[count];
		Random rand = new Random();
		for(int i=0;i<count;i++){
			int r = rand.nextInt(max);
			while(set.contains(r)){
				r = rand.nextInt(max);
			}
			set.add(r);
			arr[i] = r;
		}
		return arr;
	}
	
	
}
