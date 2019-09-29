/**
 * File Name: ParserFactory.java
 * Date: 2019-08-16 08:21:14
 */
package me.belucky.easytool.parser;

import me.belucky.easytool.parser.impl.DateParser;
import me.belucky.easytool.parser.impl.HtmlParser;
import me.belucky.easytool.parser.impl.JsonParser;
import me.belucky.easytool.parser.impl.NumberParser;
import me.belucky.easytool.parser.impl.ReplaceStringParser;

/**
 * Description: 解析工厂类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class ParserFactory {
	
	/**
	 * 获取指定解析类,默认JSON解析
	 * @param <T>
	 * @return
	 */
	public static <T> IParser<T> getParser(){
		return getParser(ParserEnum.JSON);
	}
	
	/**
	 * 获取指定解析类
	 * @param <T>
	 * @param parserEnum
	 * @return
	 */
	public static <T> IParser<T> getParser(ParserEnum parserEnum){
		return getParser(parserEnum, null);
	}
	
	/**
	 * 获取指定解析类
	 * @param <T>
	 * @param parserEnum
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> IParser<T> getParser(ParserEnum parserEnum, Class<T> cls){
		IParser<T> parser = null;
		switch(parserEnum) {
			case JSON: 
				parser = (IParser<T>) new JsonParser(cls);
				break;
			case HTML: 
				parser = (IParser<T>) new HtmlParser();
				break;
			case NUMBER:
				parser = (IParser<T>) new NumberParser();
				break;
			case REPLACE_STRING:
				parser = (IParser<T>) new ReplaceStringParser();
				break;
			case DATE:
				parser = (IParser<T>) new DateParser();
				break;
			default:
				parser = (IParser<T>) new JsonParser(cls);
		}
		return parser;
	}

}
