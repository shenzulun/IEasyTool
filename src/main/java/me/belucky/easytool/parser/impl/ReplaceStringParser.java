/**
 * File Name: ReplaceStringParser.java
 * Date: 2019-08-16 08:26:10
 */
package me.belucky.easytool.parser.impl;

import me.belucky.easytool.parser.AbstractParser;

/**
 * Description: 字符串替换解析器
 * input: hello ${name}
 * map: name: world
 * output: hello world
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class ReplaceStringParser extends AbstractParser<String>{

	/**
	  *  替换类似${XX}的字符串
	 * @param input
	 * @param map
	 * @return
	 */
	public String handle(String input){
		if(mapCond == null || mapCond.size() == 0) {
			return input;
		}
		char[] arr = input.toCharArray();
		StringBuffer ret = new StringBuffer();
		int cursor = 0;
		StringBuffer patt = null;
		for(char c : arr){
			if(c == '$'){
				cursor = 1;		//准备替换标志
				continue;
			}	
			if(cursor == 1){
				if(c == '{'){
					//确定为${开始的字符
					cursor = 2;
					patt = new StringBuffer();
					continue;
				}else{
					//$后面不是跟着{,回退标志
					cursor = 0;
					ret.append("$");
				}
			}
			if(c == '}'){	
				String key = patt.toString();
				if(mapCond.containsKey(key)){
					ret.append(mapCond.get(key));
				}else{
					ret.append("{").append(key).append("}");
				}
				cursor = 0;
				continue;
			}
			if(cursor == 2){
				patt.append(c);
			}else{
				ret.append(c);
			}
		}
		return ret.toString();
	}

}
