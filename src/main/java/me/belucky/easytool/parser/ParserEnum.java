/**
 * File Name: ParserEnum.java
 * Date: 2019-08-16 09:37:15
 */
package me.belucky.easytool.parser;


/**
 * Description: 解析类型枚举类
 * @author shenzulun
 * @date 2019-08-16
 * @version 1.0
 */
public enum ParserEnum {
	
	/**
	 * NUMBER解析
	 */
	NUMBER{
		public String toString(){
			return ParserConsts.PARSER_NUMNBER;
		}
	},
	/**
	 * 字符串替换
	 */
	REPLACE_STRING{
		public String toString(){
			return ParserConsts.PARSER_REPLACE_STRING;
		}
	},
	/**
	 * 日期解析
	 */
	DATE{
		public String toString(){
			return ParserConsts.PARSER_DATE;
		}
	},
	/**
	 * json格式
	 */
	JSON{
		public String toString(){
			return ParserConsts.PARSER_JSON;
		}
	},
	/**
	 * html格式
	 */
	HTML{
		public String toString(){
			return ParserConsts.PARSER_HTML;
		}
	}

}
