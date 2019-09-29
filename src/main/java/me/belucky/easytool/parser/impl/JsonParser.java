/**
 * File Name: JsonParser.java
 * Date: 2019-08-16 09:43:48
 */
package me.belucky.easytool.parser.impl;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import me.belucky.easytool.parser.AbstractParser;

/**
 * Description: Json解析器
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public class JsonParser extends AbstractParser<Object>{
	private Class<?> cls; 
	
	public JsonParser(){}
	
	public JsonParser(Class<?> cls){
		this.cls = cls;
	}

	public Object handle(String input) throws Exception{
		Object result = null;
		ObjectMapper objectMapper = new ObjectMapper();		
		try {
			result = objectMapper.readValue(input, cls);
		} catch (JsonParseException e) {
			log.error("",e);
			throw e;
		} catch (JsonMappingException e) {
			log.error("",e);
			throw e;
		} catch (IOException e) {
			log.error("",e);
			throw e;
		}
		return result;
	}
	
	@Override
	public String convertInput(String input){
		if(input == null){
			input = "";
		}
		if(input.startsWith("[{")){
			input = input.substring(1, input.length() - 1);
		}
		return input;
	}

}
