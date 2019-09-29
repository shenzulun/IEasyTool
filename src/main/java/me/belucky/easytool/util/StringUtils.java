/**
 * File Name: StringUtils.java
 * Date: 2019-09-29 16:44:46
 */
package me.belucky.easytool.util;

import java.util.Arrays;

/**
 * Description: 字符串工具类
 * @author shenzulun
 * @date 2019-09-29
 * @version 1.0
 */
public class StringUtils {
	
	/**
	 * 判断是否存在中文字符
	 * @param input
	 * @return
	 */
	public static boolean isExistChineseChar(String input){
		boolean isExist = false;
		char[] arr = input.toCharArray();
		for(char c : arr){
			if(c >= '\u4e00' && c <= '\u9fa5'){
				return true;
			}else if(c >= '\uf900' && c <= '\ufa2d'){
				return true;
			}
		}
		return isExist;
	}
	
	/**
	 * 判断是否存在英文字母
	 * @param input
	 * @return
	 */
	public static boolean isExistAlphabetChar(String input){
		boolean isExist = false;
		char[] arr = input.toCharArray();
		for(char c : arr){
			if(c >= 'a' && c <= 'z'){
				return true;
			}else if(c >= 'A' && c <= 'Z'){
				return true;
			}
		}
		return isExist;
	}
	
	/**
	 * 是否非空
	 * @param input
	 * @return
	 */
	public static boolean isNotNull(String input){
		return !isNull(input);
	}
	
	/**
	 * 是否为空
	 * @param input
	 * @return
	 */
	public static boolean isNull(String input){
		if(input == null || "".equals(input)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否非空白
	 * @param input
	 * @return
	 */
	public static boolean isNotBlank(String input){
		return !isBlank(input);
	}
	
	/**
	 * 字符串是否空白
	 * @param input
	 * @return
	 */
	public static boolean isBlank(String input){
		if(input == null || "".equals(input.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 比较两个字符串是否相同
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean compareTwoString(String source, String target){
		if(source == null || target == null){
			return false;
		}
		//遍历比对
		char[] arr1 = source.toCharArray();
		Arrays.sort(arr1);
		char[] arr2 = target.toCharArray();
		Arrays.sort(arr2);
		String s1 = String.valueOf(arr1);
		String s2 = String.valueOf(arr2);
		return s1.equals(s2);
	}
	
	/**
	  *  去除字符串的换行符
	 * @param input
	 * @return
	 */
	public static String trimLineBreak(String input) {
		if(input == null) {
			return null;
		}
		return input.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
	}
	
	/**
	 * 按指定分隔符分隔字符串
	 * @param str
	 * @param divide
	 * @return
	 */
	public static String[] split(String str, char divide){
		String[] ret = new String[count(str,divide) + 1];
		char[] arr = str.toCharArray();
		int length = arr.length;
		StringBuffer buff = new StringBuffer();
		int cnt = 0;
		int step = 0;
		for(char c : arr){
			step++;
			if(c == divide && step == length){
				ret[cnt++] = buff.toString();
				break;
			}
			if(c == divide ||  step == length){
				if(step == length){
					buff.append(c);
				}
				ret[cnt++] = buff.toString();
				buff = new StringBuffer();
			}else{
				buff.append(c);
			}
		}
		return ret;
	}
	
	/**
	 * 统计字符串内指定字符的出现个数
	 * @param str
	 * @param target
	 * @return
	 */
	public static int count(String str, char target){
		int cnt = 0;
		char[] arr = str.toCharArray();
		for(char c : arr){
			if(c == target){
				cnt++;
			}
		}
		return cnt;
	}
}