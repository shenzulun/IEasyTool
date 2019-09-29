/**
 * File Name: IParser.java
 * Date: 2019-08-15 16:59:37
 */
package me.belucky.easytool.parser;

import java.util.List;
import java.util.Map;

/**
 * Description: 解析器接口
 * @author shenzulun
 * @date 2019-08-15
 * @version 1.0
 */
public interface IParser<T> {
	
	/**
	 * 解析
	 * @param input
	 * @return
	 */
	public T parse(String input) throws Exception;
	
	public void setListCond(List<String> listCond);
	
	public void setMapCond(Map<String, String> mapCond);
	
}
