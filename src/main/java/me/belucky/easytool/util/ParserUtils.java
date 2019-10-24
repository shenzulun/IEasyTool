/**
 * File Name: ParserUtils.java
 * Date: 2019-08-16 11:12:10
 */
package me.belucky.easytool.util;

import java.util.Date;
import java.util.Map;
import me.belucky.easytool.parser.IParser;
import me.belucky.easytool.parser.ParserEnum;
import me.belucky.easytool.parser.ParserFactory;


/**
 * Description: 解析工具类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class ParserUtils {
		
	/**
	 * 字符串替换
	 * input: hello ${name}
	 * map: name: world
	 * output: hello world
	 * @param input
	 * @param mapCond
	 * @return
	 * @throws Exception 
	 */
	public static String replaceString(String input, Map<String, String> mapCond){
		IParser<String> parser =  ParserFactory.getParser(ParserEnum.REPLACE_STRING);
		parser.setMapCond(mapCond);
		return parser.parse(input);
	}
	
	/**
	 * 将字符串解析成数值
	 * ${1d12h30m15s}
	 * =>
	 * 24*60*60*1000+12*60*60*1000+30*60*1000+15*1000
	 * @param input
	 * @return
	 * @throws Exception 
	 */
	public static Long stringToLong(String input){
		IParser<Long> parser =  ParserFactory.getParser(ParserEnum.NUMBER);
		return parser.parse(input);
	}
	
	/**
	  * 将字符串解析成日期
	  *  支持如下格式：<ol>
	 * <li>{2016-04-01|17:00:00}</li>
	 * <li>{W4|17:00:00},往后最近的一个周四的17点</li>
	 * <li>{D3|17:00:00},3天后的17点</li>
	 * </ol>
	 * @param input
	 * @return
	 * @throws Exception 
	 */
	public static Date stringToDate(String input) {
		IParser<Date> parser =  ParserFactory.getParser(ParserEnum.DATE);
		return parser.parse(input);
	}

}
