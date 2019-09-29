/**
 * File Name: HtmlParser.java
 * Date: 2019-08-16 09:23:13
 */
package me.belucky.easytool.parser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.belucky.easytool.parser.AbstractParser;

/**
 * Description: HTML解析器
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class HtmlParser extends AbstractParser<Map<String, List<String>>>{

	public Map<String, List<String>> handle(String input) {
		if(listCond == null || listCond.size() == 0){
			return null;
		}
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for(String pattern : listCond){
			List<String> list = new ArrayList<String>();
			Pattern p = Pattern.compile(pattern);
			Matcher matcher = p.matcher(input);
			while(matcher.find()){
				list.add(matcher.group(1));
			}
			result.put(pattern, list);
		}
		return result;
	}

}
