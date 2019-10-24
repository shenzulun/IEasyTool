/**
 * File Name: RemoteShellExecutor.java
 * Date: 2018-1-15 下午05:55:11
 */
package me.belucky.easytool.ssh;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import me.belucky.easytool.dto.MsgDTO;

/**
 * 功能说明: shell操作工具
 * @author shenzl
 * @date 2016-7-6
 * @version 1.0
 */
public class RemoteShellExecutor {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Connection conn;
	
	private String host;
	
	private int port;
	
	private String username;
	
	private String passwd;
	
	private String charset = "UTF-8";

    private static final int TIME_OUT = 1000 * 5 * 60;
	
	public RemoteShellExecutor(String host, int port, String username, String passwd){
		this.host = host;
		this.port = port;
		this.username = username;
		this.passwd = passwd;
	}
	
	public RemoteShellExecutor(String host, String username, String passwd){
		this(host, 22, username, passwd);
	}
	
	public RemoteShellExecutor(LogonParams params){
		this(params.getHost(),params.getUsername(),params.getPasswd());
	}
	
	private boolean login() throws IOException {
		if(conn != null && conn.isAuthenticationComplete()){
			return true;
		}
        conn = new Connection(host, port);
        conn.connect();
        boolean ispass = conn.authenticateWithPassword(username, passwd);
        if(ispass){
        	log.info("远程服务器sh登陆成功...");
        }else{
        	log.info("远程服务器sh登陆失败...");
        }
        return ispass;
    }
	
	/**
	 * 执行shell命令
	 * @param cmds
	 * @return
	 */
	public MsgDTO exec(String cmds){
		return exec(cmds,"info");
	}
	
	/**
	 * 执行shell命令
	 * @param cmds	shell命令
	 * @param logLevel	日志级别	info or debug
	 * @return
	 */
	public MsgDTO exec(String cmds, String logLevel){
		InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        MsgDTO messageDto = new MsgDTO();
        int ret = -1;
        messageDto.setCode(ret + "");
        Session session = null;
        try {
            if (login()) {
                session = conn.openSession();
                session.execCommand(cmds);
                log.info("shell命令[{}]正在执行...", cmds);
                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);
                
                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);
                
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                
                if("info".equals(logLevel)){
                	log.info("执行成功: " + outStr);
                }else if("debug".equals(logLevel)){
                	log.debug("执行成功: " + outStr);
                }
                if(outErr != null && !outErr.equals("")){
                	 if("info".equals(logLevel)){
                		 log.info("执行错误: " + outErr);
                     }else if("debug".equals(logLevel)){
                    	 log.debug("执行错误: " + outErr);
                     }
                }
                
                messageDto.setMessage(outStr);
                ret = session.getExitStatus();
                messageDto.setCode(ret + "");
            } else {
            	messageDto.setMessage("未登录...");
            }
        } catch (IOException e) {
			log.error("",e);
		} catch (Exception e) {
			log.error("",e);
		} finally {
            try {
				stdOut.close();
				stdErr.close();
				session.close();
			} catch (IOException e) {
				log.error("",e);
			}
        }
        return messageDto;
	}
	
	public void logout(){
		 if (conn != null) {
             conn.close();
             log.info("远程服务器[{}:{}]登出成功...",host,username);
         }
	}
	private String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }
	
	public static void main(String[] args){
		RemoteShellExecutor shell = new RemoteShellExecutor("192.168.53.225","wasapp","wasapp");
		//System.out.println(shell.exec("/was/websphere/profiles/crmesbmgt/bin/stopServer.sh server1"));
		//System.out.println(shell.exec("/was/websphere/profiles/crmesbmgt/bin/startServer.sh server1"));
//		System.out.println(shell.exec("ls -ltr"));
		shell.exec("cd webs* && ls -ltr");
	}
	

}
