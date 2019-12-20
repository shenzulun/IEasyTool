/**
 * File Name: XmlUtils.java
 * Date: 2019-08-20 18:31:11
 */
package me.belucky.easytool.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Description: XML操作工具类，可以与 Java对象互转
 * @author shenzulun
 * @date 2019-08-20
 * @version 1.0
 */
public class XmlUtils {
	protected static Logger log = LoggerFactory.getLogger(XmlUtils.class);
	
    
    /** 
     * Java对象转换成XML
     * 默认编码UTF-8 
     * @param obj 
     * @return  
     */
    public static String convertObjectToXml(Object obj) {
    	return convertObjectToXml(obj, "UTF-8");
    }
    
    /** 
     * Java对象转换成XML
     * 默认编码UTF-8 
     * @param obj 
     * @param isFormattedOutput  是否格式化 
     * @return  
     */
    public static String convertObjectToXml(Object obj, boolean isFormattedOutput) {
    	return convertObjectToXml(obj, "UTF-8", false, isFormattedOutput);
    }

    /** 
     * Java对象转换成XML 
     * @param obj 
     * @param encoding 字符集
     * @return  
     */
    public static String convertObjectToXml(Object obj, String encoding) {
    	return convertObjectToXml(obj, encoding, false);
    }
    
    /** 
     * Java对象转换成XML,并去除XML声明部分 
     * @param obj 
     * @param encoding  
     * @return  
     */
    public static String convertObjectToXmlIgnoreHead(Object obj, String encoding) {
       return convertObjectToXml(obj, encoding, true);
    }
    
    /**
     * Java对象转换成XML
     * @param obj
     * @param encoding
     * @param isIgnoreXmlHead
     * @return
     */
    public static String convertObjectToXml(Object obj, String encoding, boolean isIgnoreXmlHead) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            if(isIgnoreXmlHead) {
            	marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            }
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
        	log.error("Object转XML出错...",e);
        }

        return result;
    }
    
    /**
     * Java对象转换成XML
     * @param obj
     * @param encoding
     * @param isIgnoreXmlHead 是否忽略头部
     * @param isFormattedOutput 是否格式化
     * @return
     */
    public static String convertObjectToXml(Object obj, String encoding, boolean isIgnoreXmlHead, boolean isFormattedOutput) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isFormattedOutput);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            if(isIgnoreXmlHead) {
            	marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            }
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
        	log.error("Object转XML出错...",e);
        }

        return result;
    }
    
    /** 
     * XML转换成Java对象 
     * @param xml 
     * @param c 
     * @return 
     */
    @SuppressWarnings("unchecked")
	public static <T> T convertXmlToObject(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            log.error("XML转Object出错...",e);
        }

        return t;
    }
    
    /**
     * 获取XML中指定域的内容
     * @param xml
     * @param attributeName
     * @return
     */
    public static String getValueOfXml(String xml, String superElementName, String attributeName) {
    	String result = null;
    	SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(new StringReader(xml));
			Element root = document.getRootElement();	
			Element header = root.element(superElementName);
			result = header.element(attributeName).getTextTrim();
		} catch (DocumentException e) {
			log.error("", e);		
		}
    	
    	return result;
    }
    
    /**
     * 获取XML中指定域的内容
     * @param xml
     * @param attributeName
     * @return
     */
    public static String[] getValuesOfXml(String xml, String superElementName, String... attributeName) {
    	String[] result = new String[attributeName.length];
    	SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(new StringReader(xml));
			Element root = document.getRootElement();	
			Element header = root.element(superElementName);
			int index = 0;
			for(String ele : attributeName) {
				result[index++] = header.element(ele).getTextTrim();
			}
		} catch (DocumentException e) {
			log.error("", e);		
		}
    	return result;
    }
}
