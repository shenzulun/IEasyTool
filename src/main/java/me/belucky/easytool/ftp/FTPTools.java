/**
 * File Name: FTPTools.java
 * Date: 2016-7-5 下午04:49:38
 */
package me.belucky.easytool.ftp;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 功能说明: FTP操作工具
 * @author shenzl
 * @date 2016-7-5
 * @version 1.0
 */
public class FTPTools {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private FTPConfig ftpConfig;
	
	FTPClient client = null;
	
	public FTPTools(FTPConfig ftpConfig){
		if(ftpConfig.getPort() == 0){
			ftpConfig.setPort(21);   //init
		}
		this.ftpConfig = ftpConfig;
	}
	
	public boolean isConnected(){
		boolean isConnected = false;
		try {
			if(client == null){
				client = new FTPClient();
			}
			if(!(isConnected=client.isConnected())){
				log.info("尝试连接ftp服务器...");
				client.connect(ftpConfig.getHost(),ftpConfig.getPort());
				client.login(ftpConfig.getUsername(), ftpConfig.getPassword());
				client.setType(FTPClient.TYPE_BINARY);
				isConnected = true;
				log.info("ftp登陆成功~");
			}
		} catch (IllegalStateException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		} catch (FTPIllegalReplyException e) {
			log.error("",e);
		} catch (FTPException e) {
			log.error("",e);
		}
		return isConnected;
	}
	
	public void download(File localFile, String targetFilename){
		if(isConnected()){
			try {		
				client.download(targetFilename, localFile);
				log.info("文件[{}]下载成功",targetFilename);
			} catch (IllegalStateException e) {
				log.error("",e);
			} catch (IOException e) {
				log.error("",e);
			} catch (FTPIllegalReplyException e) {
				log.error("",e);
			} catch (FTPException e) {
				log.error("",e);
			} catch (FTPDataTransferException e) {
				log.error("",e);
			} catch (FTPAbortedException e) {
				log.error("",e);
			}
		}
	}
	
	public void mkdir(String remotePath){
		if(isConnected()){
			try {
				client.changeDirectory(remotePath);			
			} catch (Exception e) {
				if(remotePath.endsWith("/")){
					remotePath = remotePath.substring(0, remotePath.length() - 1);
					log.debug(remotePath);
				}
				//路径不存在
				int ind = remotePath.lastIndexOf("/");				
				List<String> list = new ArrayList<String>();
				list.add(remotePath.substring(ind+1));
				log.info("======文件路径不存在======");
				log.info("======"+remotePath+"======");
				mkdir(remotePath.substring(0,ind),list);
			}
		}
	}
	
	protected void mkdir(String currentDir, List<String> readyToCreate){
		try {
			client.changeDirectory(currentDir);
			log.debug("远程路径[{}]切换成功", currentDir);
			mkdirs(currentDir, readyToCreate);
		} catch (Exception e) {
			int ind = currentDir.lastIndexOf("/");	
			readyToCreate.add(currentDir.substring(ind+1));
			mkdir(currentDir.substring(0,ind), readyToCreate);
		} 
	}
	
	protected void mkdirs(String currentDir, List<String> readyToCreate){
		try {
			String tmp = "";
			for(int len=readyToCreate.size(),i=len-1;i>=0;i--){
				String dir = readyToCreate.get(i);
				client.changeDirectory(currentDir + tmp);
				client.createDirectory(dir);
				tmp += "/" + dir;
				log.debug("远程文件夹[{}]创建成功",dir);
			}
		} catch (IllegalStateException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		} catch (FTPIllegalReplyException e) {
			log.error("",e);
		} catch (FTPException e) {
			log.error("",e);
		}
	}
	
	
	public void upload(File file, String remotePath){
		if(file == null){
			return;
		}
		try {
			if(isConnected()){
				mkdir(remotePath);
				client.changeDirectory(remotePath);
				log.info("======文件路径切换成功======");
				client.upload(file);
				log.info("======文件["+file.getName()+"]上传成功======");
			}
		} catch (IllegalStateException e) {
			log.error("",e);
		} catch (IOException e) {
			log.error("",e);
		} catch (FTPIllegalReplyException e) {
			log.error("",e);
		} catch (FTPException e) {
			log.error("",e);
		} catch (FTPDataTransferException e) {
			log.error("",e);
		} catch (FTPAbortedException e) {
			log.error("",e);
		}
	}
	
	
//	public List<FileListDTO> list(String folderPath, String fileSpec){
//		List<FileListDTO> fileList = new ArrayList<FileListDTO>();
//		try {
//			if(isConnected()){
//				client.changeDirectory(folderPath);
////				download(new File("C:\\Users\\tzbank\\Desktop\\bosent_framework-default.log"),"/home/cop/runtime/logs/bosent_framework-default.log");
//				FTPFile[] files = null;
//				if(fileSpec == null){
//					files = client.list();
//				}else{
//					files = client.list(fileSpec);
//				}
//				if(files != null){
//					for(FTPFile file : files){
//						FileListDTO dto = new FileListDTO();
//						dto.setFileName(file.getName());
//						Long length = file.getSize();
//						String lengthView = "";
//						if(length / 1024 / 1024 >= 1){
//							lengthView = length / 1024 / 1024 + "MB";
//						}else if(length / 1024 >= 1){
//							lengthView = length / 1024  + "KB";
//						}else{
//							lengthView = length + "B";
//						}
//						dto.setFileLength(lengthView);
//						dto.setLastModifiedTime(DateTimeUtil.getDateTime(file.getModifiedDate()));
//					}
//				}
//			}	
//		} catch (Exception e) {
//			log.error("",e);
//		}
//		return fileList; 
//	}
	
	public void logout(){
		if(client != null){
			try {
				client.logout();
				log.info("ftp登出成功~");
			} catch (IllegalStateException e) {
				log.error("",e);
			} catch (IOException e) {
				log.error("",e);
			} catch (FTPIllegalReplyException e) {
				log.error("",e);
			} catch (FTPException e) {
				log.error("",e);
			}
		}
	}

	public FTPClient getClient() {
		return client;
	}

	public void setClient(FTPClient client) {
		this.client = client;
	}
	
}
