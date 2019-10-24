/**
 * File Name: JsonLogController.java
 * Date: 2019-10-22 16:33:12
 */
package me.belucky.easytool.jfinal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import me.belucky.easytool.dto.MsgDTO;
import me.belucky.easytool.tail.Tail;

/**
 * Description: 返回json格式的log信息
 * @author shenzulun
 * @date 2019-10-22
 * @version 1.0
 */
public abstract class JsonLogController extends Controller{
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Controller的默认Bean
	 * @return
	 */
	public abstract Class<?> getBeanClass();
	/**
	 * 默认输入 invoke(this, methodName, dto);	
	 * @param dto
	 * @param methodName
	 */
	public abstract void go(Object dto, String methodName);
	/**
	 * 请求IP
	 */
	private String remoteAddr;
	/**
	 * 返回的DTO
	 */
	private MsgDTO retDto = null;
	/**
	 * 日志文件路径
	 */
	private static String logPath = null;
	
	static {
		//暂且只支持log4j
		Prop prop = PropKit.getProp("log4j.properties");
		if(prop != null) {
			logPath =  prop.get("log4j.appender.file.File");
		}{
			//TODO
			
		}
	}
	
	public void autorun(){
		String methodName = this.getPara("methodName");
		log.debug(methodName);
		this.remoteAddr = super.getRequest().getRemoteAddr();
		log.debug("当前访问IP: {}", remoteAddr == null ? "" : remoteAddr);
		Object dto = getBean(getBeanClass());
		if(logPath != null) {
			//日志监视
			StringBuffer buff = new StringBuffer();
			Tail tail = new Tail(logPath, buff);
			go(dto, methodName);
			if(retDto == null){
				retDto = new MsgDTO();
				retDto.setMessage(tail.getBuffer().toString());
			}
		}else {
			go(dto, methodName);
		}
		renderJson(retDto);
	}
	
	public void invoke(Object target, String methodName, Object dto) {
		try {
			Method m = target.getClass().getDeclaredMethod(methodName, dto.getClass());
			m.invoke(target, dto);
		} catch (SecurityException e) {
			log.error("",e);
		} catch (NoSuchMethodException e) {
			log.error("",e);
		} catch (IllegalAccessException e) {
			log.error("",e);
		} catch (IllegalArgumentException e) {
			log.error("",e);
		} catch (InvocationTargetException e) {
			log.error("",e);
		}
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public MsgDTO getRetDto() {
		return retDto;
	}

	public void setRetDto(MsgDTO retDto) {
		this.retDto = retDto;
	}
}
