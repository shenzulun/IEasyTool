/**
 * File Name: CommonUtils.java
 * Date: 2019-08-16 08:40:54
 */
package me.belucky.easytool.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
				Field field = getField(cls, fieldName);
				Method m = target.getClass().getMethod(methodName, field.getType());
				Object v = m.invoke(target, newValue);
				t = (T)v;
			}else if("get".equals(methodPrefix)){
				Method m = target.getClass().getMethod(methodName);
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
		}
		return t;
	}
	
	/**
	 * 获取class中的指定字段
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		Field field = null;
		Field[] fields = getAllFields(clazz);
		for(Field f : fields) {
			if(fieldName.equals(f.getName())) {
				field = f;
				break;
			}
		}
		return field;
	}
	
	/**
	 * 获取class及父类的所有字段
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllFields(Class<?> clazz){
		List<Field> fieldList = new ArrayList<Field>();
		while (clazz != null){
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
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
	
	/**
	 * 睡眠
	 * @param microSeconds
	 */
	public static void sleep(long microSeconds) {
		try {
			Thread.sleep(microSeconds);
		} catch (InterruptedException e) {
			log.error("", e);
		}
	}
	
	/**
	 * 生成流水号
	 * @return
	 */
	public static String generateSerno() {
		//年月日时分秒毫秒  17位
		String prefix = DateTimeUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS");
		//再加三位随机数
		Random rand = new Random();
		int r = rand.nextInt(1000);
		if(r == 0) {
			r = 1;
		}
		//转成3位字符串, 不足左边补0
		String suffix = StringUtils.intToStringBySpecifiedLength(r, 3, true, "0");
		return prefix + suffix;
	}
	
}
