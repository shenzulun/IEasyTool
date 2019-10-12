/**
 * File Name: SFTPTools.java
 * Date: 2019-08-23 16:47:23
 */
package me.belucky.easytool.ftp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import me.belucky.easytool.util.StringUtils;

/**
 * Description: SFTP操作工具类
 * @author shenzulun
 * @date 2019-08-23
 * @version 1.0
 */
public class SFTPTools {
	private transient Logger log = LoggerFactory.getLogger(this.getClass());  
    
    private ChannelSftp sftp;  
        
    private Session session;  
    /**
     * 用户名
     */   
    private String username; 
    /**
     * 密码
     */    
    private String password;  
    /**
     * 私钥
     */
    private String privateKey;  
    /**
     * IP
     */
    private String host;  
    /**
     * 端口
     */
    private int port;  
    
    /**  
     * 构造基于密码认证的sftp对象  
     */    
    public SFTPTools(String username, String password, String host, int port) {  
        this.username = username;  
        this.password = password;  
        this.host = host;  
        this.port = port;  
    } 
    
    /**  
     * 构造基于秘钥认证的sftp对象 
     */  
    public SFTPTools(String username, String host, int port, String privateKey) {  
        this.username = username;  
        this.host = host;  
        this.port = port;  
        this.privateKey = privateKey;  
    }  
    
    public SFTPTools(FTPConfig ftpConfig) {
    	this.username = ftpConfig.getUsername();
    	this.host = ftpConfig.getHost();
    	this.port = ftpConfig.getPort();
    	if(StringUtils.isNotNull(ftpConfig.getPrivateKey())) {
			//基于私钥
    		this.privateKey = ftpConfig.getPrivateKey();
		}else {
			//基于密码
			this.password = ftpConfig.getPassword();
		}
    }
    
    public SFTPTools(){}  
    
    /**
     * 判断是否已登陆
     * @return
     */
    public boolean isConnected(){
		boolean isConnected = false;
		try {
			if(sftp == null){
				sftp = new ChannelSftp();
			}
			if(!(isConnected=sftp.isConnected())){
				log.info("尝试连接sftp服务器...");
				JSch jsch = new JSch();  
	            if (privateKey != null) {  
	                jsch.addIdentity(privateKey);// 设置私钥  
	            }  
	            session = jsch.getSession(username, host, port);  
	            if (password != null) {  
	                session.setPassword(password);    
	            }  
	            Properties config = new Properties();  
	            config.put("StrictHostKeyChecking", "no");  
	                
	            session.setConfig(config);  
	            session.connect();  
	              
	            Channel channel = session.openChannel("sftp");  
	            channel.connect();  
	            sftp = (ChannelSftp) channel;  
				isConnected = true;
				log.info("ftp登陆成功~");
			}
		} catch (IllegalStateException e) {
			log.error("sftp登陆失败...",e);
		} catch (JSchException e) {
			log.error("sftp登陆失败...",e);
		} 
		return isConnected;
	}
    /** 
     * 连接sftp服务器 
     */  
    public void login(){  
        try {  
            JSch jsch = new JSch();  
            if (privateKey != null) {  
                jsch.addIdentity(privateKey);// 设置私钥  
            }  
    
            session = jsch.getSession(username, host, port);  
           
            if (password != null) {  
                session.setPassword(password);    
            }  
            Properties config = new Properties();  
            config.put("StrictHostKeyChecking", "no");  
                
            session.setConfig(config);  
            session.connect();  
              
            Channel channel = session.openChannel("sftp");  
            channel.connect();  
    
            sftp = (ChannelSftp) channel;  
        } catch (JSchException e) {  
            log.error("sftp登陆失败...",e);
        }  
    }    
    
    /** 
     * 关闭连接 server  
     */  
    public void logout(){  
        if (sftp != null) {  
            if (sftp.isConnected()) {  
                sftp.disconnect();  
            }  
        }  
        if (session != null) {  
            if (session.isConnected()) {  
                session.disconnect();  
            }  
        }  
        log.info("sftp登出成功");
    }  
 
    
    /**  
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param basePath  服务器的基础路径 
     * @param directory  上传到该目录  
     * @param sftpFileName  sftp端文件名  
     * @param in   输入流  
     */  
    public void upload(String basePath,String directory, String sftpFileName, InputStream input) throws SftpException{ 
    	if(isConnected()){
    		try {   
                sftp.cd(basePath);
                sftp.cd(directory);  
            } catch (SftpException e) { 
                //目录不存在，则创建文件夹
                String [] dirs=directory.split("/");
                String tempPath=basePath;
                for(String dir:dirs){
                	if(null== dir || "".equals(dir)) continue;
                	tempPath+="/"+dir;
                	try{ 
                		sftp.cd(tempPath);
                	}catch(SftpException ex){
                		sftp.mkdir(tempPath);
                		sftp.cd(tempPath);
                	}
                }
            }  
            sftp.put(input, sftpFileName);  //上传文件
            log.info("文件[{}]上传成功", sftpFileName);
    	}
    } 
    
    /**  
     * 将输入流的数据上传到sftp
     * @param directory	文件路径
     * @param sftpFileName  sftp端文件名  
     * @param in   输入流  
     */  
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException{ 
    	if(isConnected()){
    		try {  
    			if(directory != null && !"".equals(directory)) {
    				sftp.cd(directory);  
    			}
            } catch (SftpException e) { 
            	//目录不存在，则创建文件夹
                String [] dirs = directory.split("/");
                String tempPath = "";
                for(String dir : dirs){
                	if(null == dir || "".equals(dir)) continue;
                	tempPath += "/" + dir;
                	try{ 
                		sftp.cd(tempPath);
                	}catch(SftpException ex){
                		sftp.mkdir(tempPath);
                		sftp.cd(tempPath);
                	}
                }
            }  
    		sftp.put(input, sftpFileName);  //上传文件
    		log.info("文件[{}]上传成功", sftpFileName);
    	}
    } 
    
 
    /** 
     * 下载文件
     * @param directory 下载目录  
     * @param downloadFile 下载的文件 
     * @param saveFile 存在本地的路径 
     */    
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException{ 
    	if(isConnected()){
    		if (directory != null && !"".equals(directory)) {  
                sftp.cd(directory);  
            }  
            File file = new File(saveFile);  
            sftp.get(downloadFile, new FileOutputStream(file));  
            log.info("文件[{}]下载成功", downloadFile);
    	}
    }  
    
    /**  
     * 下载文件 
     * @param directory 下载目录 
     * @param downloadFile 下载的文件名 
     * @return 字节数组 
     */  
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException{  
    	byte[] fileData = null;
    	if(isConnected()){
    		if (directory != null && !"".equals(directory)) {  
                sftp.cd(directory);  
            }  
            InputStream is = sftp.get(downloadFile);  
            log.info("文件[{}]下载成功", downloadFile);
            fileData = IOUtils.toByteArray(is); 
    	}
        return fileData;  
    }  
    
    
    /** 
     * 删除文件 
     * @param directory 要删除文件所在目录 
     * @param deleteFile 要删除的文件 
     */  
    public void delete(String directory, String deleteFile) throws SftpException{  
    	if(isConnected()){
    		sftp.cd(directory);  
            sftp.rm(deleteFile);  
            log.info("文件[{}]删除成功", deleteFile);
    	}
    }  
    
    
    /** 
     * 列出目录下的文件 
     * @param directory 要列出的目录 
     * @param sftp 
     */  
    public Vector<?> listFiles(String directory) throws SftpException {  
    	Vector<?> result = null;
    	if(isConnected()){
    		result = sftp.ls(directory);  
    	}
    	return result;
    }  
}
