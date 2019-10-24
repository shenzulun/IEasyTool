/**
 * File Name: RemoteShellFactory.java
 * Date: 2018-11-20 下午06:49:58
 */
package me.belucky.easytool.ssh;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfinal.kit.PropKit;
import me.belucky.easytool.util.DateTimeUtils;
import me.belucky.easytool.util.ParserUtils;

/**
 * 功能说明: ssh工具类工厂
 * @author shenzl
 * @date 2018-11-20
 * @version 1.0
 */
public class RemoteShellFactory {
	protected static Logger log = LoggerFactory.getLogger(RemoteShellFactory.class);
	/**
	 * ssh map
	 */
	private static Map<String,RemoteShellExecutor> sshMap = new ConcurrentHashMap<String,RemoteShellExecutor>();
	/**
	 * 每个ssh客户端的最近一次操作日期
	 */
	private static Map<String,Long> lastActiveMap = new ConcurrentHashMap<String, Long>();
	
	private static long cacheTime = 0L;
	
	static {
		String cacheTimeStr = PropKit.get("ssh_cache_time", "{30m}");
		cacheTime = ParserUtils.stringToLong(cacheTimeStr);
	}
	
	/**
	 * 获取ssh客户端
	 * @param params
	 * @return
	 */
	public static RemoteShellExecutor getSSHClient(LogonParams params){
		RemoteShellExecutor shellExec = null;
		if(params == null){
			return null;
		}
		String key = params.getHost() + "_" + params.getUsername();
		if(sshMap.containsKey(key)){
			//存在缓存
			shellExec = sshMap.get(key); 
		}else{
			shellExec = new RemoteShellExecutor(params);
			sshMap.put(key, shellExec);
		}
		lastActiveMap.put(key, System.currentTimeMillis());
		return shellExec;
	}
	
	/**
	 * 清理过期连接
	 * @param isClearAll true: 清理所有连接   false: 根据是否超时来判断,超时时间:30分钟
	 */
	public static void clearSSHConnection(boolean isClearAll){
		Set<String> set = lastActiveMap.keySet();
		if(set != null && set.size() > 0){
			for(String key : set){
				Long lastActive = lastActiveMap.get(key);
				if(isClearAll || (!isClearAll && System.currentTimeMillis() - lastActive > cacheTime)){
					log.info("开始清理连接：{}",key);
					lastActiveMap.remove(key);
					sshMap.get(key).logout();
					sshMap.remove(key);
				}else{
					log.info("ssh连接[{}]未超时,最近一次连接时间[{}],暂不清理...",key,DateTimeUtils.getDateTime(new Date(lastActive)));
				}
			}
			log.info("ssh连接清理完毕...");
		}
	}

}
