/**
 * File Name: AbstractParser.java
 * Date: 2019-08-16 09:17:20
 */
package me.belucky.easytool.parser;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 解析引擎抽象类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public abstract class AbstractParser<T> implements IParser<T>{
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	protected List<String> listCond = null;
	protected Map<String, String> mapCond = null;

	public T parse(String input){
		if(input == null){
			return null;
		}
		log.debug("开始解析...");
		T resultDTO = handle(convertInput(input));
		log.debug("解析结束...");
		return resultDTO;
	}
	
	/**
	 * 解析逻辑
	 * @param input
	 * @return
	 */
	public abstract T handle(String input);
	
	/**
	 * 解析前文本适配转换
	 * @param input
	 * @return
	 */
	public String convertInput(String input){
		return input;
	}
	
	/**
	 * 设置list条件
	 * @param listCond
	 * @return
	 */
	public void setListCond(List<String> listCond) {
		this.listCond = listCond;
	}
	
	/**
	 * 设置map条件
	 * @param mapCond
	 * @return
	 */
	public void setMapCond(Map<String, String> mapCond){
		this.mapCond = mapCond;
	}
	
}
